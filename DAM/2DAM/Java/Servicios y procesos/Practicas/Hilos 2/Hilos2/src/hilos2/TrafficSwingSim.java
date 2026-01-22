package hilos2;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;

public class TrafficSwingSim {
    // ===== Configuración =====
    static final int ROAD_LEN = 50; // Main road length
    static final int ROUNDABOUT_LEN = 30; // Roundabout length (circumference)

    static final int ENTRY1_POS = 0;
    static final int ENTRY2_POS = 10;

    // Position on Roundabout where Main Road connects
    static final int ROUNDABOUT_ENTRY_POS = 0;

    // Positions on Roundabout for Exits
    static final int EXIT1_POS = 10;
    static final int EXIT2_POS = 20;

    static final double ENTRY1_RATE_PER_MIN = 300; // Increased traffic
    static final double ENTRY2_RATE_PER_MIN = 250;
    static final double EXIT1_RATE_PER_MIN = 25;
    static final double EXIT2_RATE_PER_MIN = 20;

    static final int TICK_MS = 100; // Faster simulation
    static final int MIN_GAP = 1;

    // Accident Config
    static final double ACCIDENT_PROB = 0.001; // Probability per tick per car
    static final int MIN_CRASH_TIME = 2000; // ms
    static final int MAX_CRASH_TIME = 5000; // ms

    // ===== Modelo =====

    static class Car {
        final int id;
        int pos;
        boolean onRoundabout; // false = main road, true = roundabout
        boolean crashed;
        long crashEndTime;
        Color color;

        Car(int id, int pos, boolean onRoundabout) {
            this.id = id;
            this.pos = pos;
            this.onRoundabout = onRoundabout;
            this.color = Color.getHSBColor((id % 24) / 24f, 0.55f, 0.90f);
        }
    }

    static class TokenBucket {
        private final double ratePerMin;
        private double tokens = 0.0;

        TokenBucket(double ratePerMin) {
            this.ratePerMin = Math.max(0.0, ratePerMin);
        }

        void addTimeMs(long dtMs) {
            tokens += ratePerMin * (dtMs / 60000.0);
            tokens = Math.min(tokens, 10.0);
        }

        boolean tryConsumeOne() {
            if (tokens >= 1.0) {
                tokens -= 1.0;
                return true;
            }
            return false;
        }
    }

    // ===== Estado =====
    // Road segments
    final Car[] mainRoad = new Car[ROAD_LEN];
    final Car[] roundabout = new Car[ROUNDABOUT_LEN];

    final ConcurrentLinkedQueue<Integer> pendingE1 = new ConcurrentLinkedQueue<>();
    final ConcurrentLinkedQueue<Integer> pendingE2 = new ConcurrentLinkedQueue<>();

    long maxQueueE1 = 0;
    long maxQueueE2 = 0;
    final AtomicInteger idGen = new AtomicInteger(1);

    long exited1 = 0;
    long exited2 = 0;
    long accidents = 0;

    final TokenBucket exit1Bucket = new TokenBucket(EXIT1_RATE_PER_MIN);
    final TokenBucket exit2Bucket = new TokenBucket(EXIT2_RATE_PER_MIN);

    // Helpers
    final Random rand = new Random();

    // ===== GUI =====
    JFrame frame;
    SimPanel panel;

    // ===== Scheduler =====
    final ScheduledExecutorService sched = Executors.newScheduledThreadPool(4);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TrafficSwingSim().start());
    }

    void start() {
        frame = new JFrame("Traffic Swing Sim [DGB] - Roundabout & Accidents");
        panel = new SimPanel(this);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.setSize(1200, 600); // Larger window for roundabout
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        scheduleArrivals(pendingE1, ENTRY1_RATE_PER_MIN);
        scheduleArrivals(pendingE2, ENTRY2_RATE_PER_MIN);

        final long[] lastTick = { System.currentTimeMillis() };
        sched.scheduleAtFixedRate(() -> {
            long now = System.currentTimeMillis();
            long dt = now - lastTick[0];
            lastTick[0] = now;
            tick(dt, now);
            SwingUtilities.invokeLater(panel::repaint);
        }, 0, TICK_MS, TimeUnit.MILLISECONDS);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                sched.shutdownNow();
            }
        });
    }

    void scheduleArrivals(ConcurrentLinkedQueue<Integer> q, double ratePerMin) {
        if (ratePerMin <= 0) {
            return;
        }
        long periodMs = Math.max(1, Math.round(60000.0 / ratePerMin));
        sched.scheduleAtFixedRate(() -> q.add(idGen.getAndIncrement()),
                0, periodMs, TimeUnit.MILLISECONDS);
    }

    // ===== Tick =====
    void tick(long dtMs, long now) {
        exit1Bucket.addTimeMs(dtMs);
        exit2Bucket.addTimeMs(dtMs);

        processEntries();
        processAccidents(now);
        processMovement(now);

        // métricas de cola
        maxQueueE1 = Math.max(maxQueueE1, pendingE1.size());
        maxQueueE2 = Math.max(maxQueueE2, pendingE2.size());
    }

    // Check if cell is free and has gap safely
    boolean canOccupyWithGap(Car[] roadSegment, int pos, int roadLen) {
        if (pos < 0 || pos >= roadLen)
            return false;
        if (roadSegment[pos] != null)
            return false;

        // Check ahead for MIN_GAP
        for (int k = 1; k <= MIN_GAP; k++) {
            int ahead = (pos + k) % roadLen; // Circular check safe for straight road if logic handles bounds, but here
                                             // separate arrays
            // For straight road, modulo wraparound is wrong, but our movement logic handles
            // end of array.
            // Wait, for mainRoad (straight), ahead >= len means off road (safe).
            // For roundabout (circular), ahead % len is correct.

            // However, to keep this generic method simple, we assume 'pos' is valid.
            // If roadSegment is mainRoad, we shouldn't wrap.
            if (roadSegment == mainRoad) {
                if (pos + k >= roadLen)
                    break; // End of road is free space essentially
                if (roadSegment[pos + k] != null)
                    return false;
            } else {
                // Roundabout
                if (roadSegment[ahead] != null)
                    return false;
            }
        }
        return true;
    }

    void processEntries() {
        // Entry 1
        Integer id1 = pendingE1.peek();
        if (id1 != null && canOccupyWithGap(mainRoad, ENTRY1_POS, ROAD_LEN)) {
            pendingE1.poll();
            mainRoad[ENTRY1_POS] = new Car(id1, ENTRY1_POS, false);
        }
        // Entry 2
        Integer id2 = pendingE2.peek();
        if (id2 != null && canOccupyWithGap(mainRoad, ENTRY2_POS, ROAD_LEN)) {
            pendingE2.poll();
            mainRoad[ENTRY2_POS] = new Car(id2, ENTRY2_POS, false);
        }
    }

    void processAccidents(long now) {
        // Check main road
        for (Car c : mainRoad) {
            if (c != null && !c.crashed) {
                if (rand.nextDouble() < ACCIDENT_PROB) {
                    c.crashed = true;
                    c.crashEndTime = now + MIN_CRASH_TIME + rand.nextInt(MAX_CRASH_TIME - MIN_CRASH_TIME);
                    accidents++;
                }
            }
        }
        // Check roundabout
        for (Car c : roundabout) {
            if (c != null && !c.crashed) {
                if (rand.nextDouble() < ACCIDENT_PROB) {
                    c.crashed = true;
                    c.crashEndTime = now + MIN_CRASH_TIME + rand.nextInt(MAX_CRASH_TIME - MIN_CRASH_TIME);
                    accidents++;
                }
            }
        }
    }

    void processMovement(long now) {
        // 1. Exit Logic (Roundabout -> Exit)
        // We process roundabout in reverse to avoiding double moves? Or just carefully.
        // It's easier to process from "front" to "back" to avoid moving same car twice
        // if index increases.
        // But for circular, it's tricky. We'll use a 'moved' flag or simple copy buffer
        // if needed.
        // For simplicity: Iterate backwards on mainRoad, and arbitrary start on
        // roundabout?
        // Let's use a "nextState" array approach or just careful ordering.
        // Backwards iteration is standard for cellular automata on 1D arrays.

        // --- Roundabout Movement ---
        // We need to be careful about circular dependencies.
        // Let's try: identify cars that CaN move, then move them.

        // Copy existing stationary/blocked cars? No, that's hard.
        // Let's do in-place but careful? No, nextRoundabout is safer.
        // To handle blockages properly, we need to know if the target cell is free *in
        // current tick*.
        // Simple approach: Iterate through all roundabout cells.
        // We need to order them to allow "chain" movement.
        // Actually, for swing sim, let's just do single pass, if gap exists in CURRENT
        // state, move.
        // To prevent one car moving multiple times, we can track processed IDs.

        // Let's stick to the "Backwards" iteration for Main Road.

        // --- Main Road to Roundabout ---
        // Process Main Road backwards.
        for (int i = ROAD_LEN - 1; i >= 0; i--) {
            Car c = mainRoad[i];
            if (c == null)
                continue;

            // Manage accident recovery
            if (c.crashed) {
                if (now >= c.crashEndTime) {
                    c.crashed = false; // Recovered
                } else {
                    continue; // Stuck
                }
            }

            // End of main road -> Try enter roundabout
            if (i == ROAD_LEN - 1) {
                // Try to enter roundabout at ROUNDABOUT_ENTRY_POS
                if (canOccupyWithGap(roundabout, ROUNDABOUT_ENTRY_POS, ROUNDABOUT_LEN)) {
                    // Enter!
                    mainRoad[i] = null;
                    c.onRoundabout = true;
                    c.pos = ROUNDABOUT_ENTRY_POS;
                    roundabout[ROUNDABOUT_ENTRY_POS] = c;
                }
            } else {
                // Normal move forward
                int next = i + 1;
                if (canOccupyWithGap(mainRoad, next, ROAD_LEN)) {
                    mainRoad[i] = null;
                    c.pos = next;
                    mainRoad[next] = c;
                }
            }
        }

        // --- Roundabout Movement & Exits ---
        // For circular road, simple backward iteration doesn't work perfectly for the
        // "wrap around" point.
        // But we can approximate.
        // Let's snapshot the roundabout to separate "current state" from "next state"

        // We simply check for every car in current 'roundabout', where it wants to go.
        // If it stays or moves, we put it in 'nextR'.
        // Collision handling: checking 'nextR' usage?
        // This is tricky without strict ordering.
        // fallback: Minimal movement logic.
        // We will iterate i from 0 to LEN: if car at i can move to (i+1)%LEN, move it.
        // BUT if we process i=0 then i=1, i=0 moves to 1, then we process 1... same car
        // moves again?
        // YES. So we must use a 'moved' set.
        java.util.HashSet<Integer> movedIds = new java.util.HashSet<>();

        // To allow flow irrespective of array index, maybe random order? or process
        // against traffic flow?
        // Roundabout flows "Counter Clockwise" usually, or just index + 1.
        // If flows index +, we should iterate REVERSE (index - 1) so we move a car into
        // empty space, then process the car behind it.
        // Wrap around handled manually.

        // Order: (LEN-1) -> 0.
        // Special case: Moving (LEN-1) to 0.
        // Then (LEN-2) to (LEN-1)...

        // Let's do strict reverse iteration for indices 0 to LEN-1.
        // But we need to handle the wrap (LEN-1 -> 0) FIRST or LAST?
        // If 0 is empty, LEN-1 can move.
        // If we process LEN-1 first, check 0. If 0 free, move.
        // Then process LEN-2. check LEN-1. Now LEN-1 is empty (because car moved to 0).
        // So LEN-2 moves. Good.

        for (int i = ROUNDABOUT_LEN - 1; i >= -1; i--) {
            // We do an extra step to handle the wrap comfortably?
            // Actually, just standard loop i=LEN-1 downto 0.
            int currIdx = (i + ROUNDABOUT_LEN) % ROUNDABOUT_LEN;

            // We need to visit everyone once.
            if (i == -1)
                break; // Just loop LEN-1 to 0.

            Car c = roundabout[currIdx];
            if (c == null)
                continue;
            if (movedIds.contains(c.id))
                continue;

            // Manage accident recovery
            if (c.crashed) {
                if (now >= c.crashEndTime) {
                    c.crashed = false;
                } else {
                    continue; // Stuck
                }
            }

            // Check Exits
            boolean exited = false;
            if (currIdx == EXIT1_POS && exit1Bucket.tryConsumeOne()) {
                // 50% chance to exit? Or mostly exit?
                if (c.id % 2 == 0) { // Evens take Exit 1
                    roundabout[currIdx] = null;
                    exited1++;
                    exited = true;
                }
            } else if (currIdx == EXIT2_POS && exit2Bucket.tryConsumeOne()) {
                if (c.id % 2 != 0) { // Odds take Exit 2
                    roundabout[currIdx] = null;
                    exited2++;
                    exited = true;
                }
            }

            if (exited)
                continue;

            // Move around circle
            int nextIdx = (currIdx + 1) % ROUNDABOUT_LEN;
            // Check if next spot is free in CURRENT array (roundabout)
            // Wait, if we move cars in-place reverse, we verify against CURRENT array.
            // If car at 'nextIdx' hasn't been processed yet (it's behind in iteration
            // order?), it is still there.
            // If it HAS been processed (it was at nextIdx and moved to nextIdx+1), then
            // nextIdx is now null.
            // Exception: nextIdx = 0. We haven't processed 0 yet (last in loop).
            // So if we are at LEN-1, looking at 0. If 0 is occupied, we wait.
            // But what if 0 moves later? Then LEN-1 missed its chance.
            // This suggests we should process the gap-creators first?

            // Standard approach: 2 passes or 'nextState' array.
            // Let's use 'nextState' array for Roundabout to be robust.
            // No, 'nextState' has read-write conflicts.

            // Let's stick to simple "Try Move" with "Double Buffer" logic implicitly?
            // No, just check bounds. Traffic jams are okay.

            if (roundabout[nextIdx] == null) {
                // Also check min gap? Roundabouts are tight? Let's check gap of 1.
                // If oneMore is occupied by a car that hasn't moved yet...
                // Complexity is high. Let's start basic: if next is null, move.
                roundabout[currIdx] = null;
                c.pos = nextIdx;
                roundabout[nextIdx] = c;
                movedIds.add(c.id);
            }
        }
    }

    // ===== Panel =====
    static class SimPanel extends JPanel {
        final TrafficSwingSim sim;

        SimPanel(TrafficSwingSim sim) {
            this.sim = sim;
            setBackground(new Color(50, 50, 50)); // Dark asphalt background
            setFont(new Font("Consolas", Font.BOLD, 12));
        }

        @Override
        protected void paintComponent(Graphics g0) {
            super.paintComponent(g0);
            Graphics2D g = (Graphics2D) g0.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            int cx = w / 2 + 100;
            int cy = h / 2;
            int r = 150; // Radius of roundabout centerline

            // Draw Main Road
            int roadY = cy;
            int roadW = cx - r - 50;
            int startX = 50;

            // Main Road visuals
            g.setColor(Color.GRAY);
            g.fillRect(startX, roadY - 20, roadW, 40);
            g.setColor(Color.WHITE);
            g.drawRect(startX, roadY - 20, roadW, 40);

            // Draw Main Road Cars
            double cellW = roadW / (double) ROAD_LEN;
            for (int i = 0; i < ROAD_LEN; i++) {
                Car c = sim.mainRoad[i];
                if (c != null) {
                    drawCar(g, startX + (int) (i * cellW), roadY, (int) cellW - 2, c);
                }
            }

            // Draw Roundabout
            g.setColor(Color.GRAY);
            g.setStroke(new BasicStroke(40));
            g.drawOval(cx - r, cy - r, 2 * r, 2 * r);
            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(2));
            g.drawOval(cx - r - 20, cy - r - 20, 2 * r + 40, 2 * r + 40); // Outer
            g.drawOval(cx - r + 20, cy - r + 20, 2 * r - 40, 2 * r - 40); // Inner

            // Draw Roundabout Cars
            double angleStep = 2 * Math.PI / ROUNDABOUT_LEN;
            for (int i = 0; i < ROUNDABOUT_LEN; i++) {
                Car c = sim.roundabout[i];
                // Angle: Start from West (PI) where main road enters?
                // Visual alignment: Main road comes from Left. So Entry is at Angle PI.
                // Our simulation logic: Index 0 is Entry.
                // So Index 0 should represent Angle PI.
                // Index increases... Clockwise or Counter?
                // Usually Traffic is Counter-Clockwise (Right hand traffic).
                // So Index + => Angle decreases?
                // Let's say Index 0 = PI. Index 1 = PI - step.

                // Exits: EXIT1=10, EXIT2=20.
                // If 0 is West (180 deg).
                // Total 30 slots. 10 slots approx 1/3 circle (120 deg).
                // 180 - 120 = 60 deg (North East).
                // 180 - 240 = -60 deg (South East).
                // Seems reasonable.

                double angle = Math.PI - (i * angleStep);

                int carX = cx + (int) (r * Math.cos(angle));
                int carY = cy - (int) (r * Math.sin(angle)); // Y is updated down

                // Draw Markers for Exits
                if (i == EXIT1_POS || i == EXIT2_POS) {
                    g.setColor(new Color(0, 100, 0, 100));
                    g.fillOval(carX - 25, carY - 25, 50, 50);
                    g.setColor(Color.WHITE);
                    g.drawString(i == EXIT1_POS ? "X1" : "X2", carX - 10, carY + 5);
                }

                if (c != null) {
                    drawCar(g, carX - 10, carY, 20, c);
                }
            }

            // HUD
            g.setColor(Color.WHITE);
            g.drawString("Traffic Sim [DGB] | Accidents: " + sim.accidents, 20, 20);
            g.drawString("Queue E1: " + sim.pendingE1.size(), 20, 40);
            g.drawString("Queue E2: " + sim.pendingE2.size(), 20, 60);

            g.dispose();
        }

        void drawCar(Graphics2D g, int x, int y, int size, Car c) {
            if (c.crashed) {
                g.setColor(Color.RED);
            } else {
                g.setColor(c.color);
            }
            g.fillRect(x, y - 10, size, 20);
            g.setColor(Color.BLACK);
            g.drawRect(x, y - 10, size, 20);
            g.drawString("" + c.id, x + 2, y + 5);
            if (c.crashed) {
                g.setColor(Color.YELLOW);
                g.drawString("!", x + size / 2 - 2, y - 12);
            }
        }
    }
}
