package hilos2;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TrafficSwingSim {

    // ===== Configuración =====
    static final int ROUND_LEN = 140;
    static final int ENTRY_LEN = 40;
    static final int EXIT_LEN = 40;

    // Configuración de Entradas y Salidas
    // Soportamos N entradas y M salidas.
    // Vamos a poner 4 entradas y 4 salidas intercaladas simétricamente.
    // Round len 140.
    // Entradas en: 0, 35, 70, 105.
    // Salidas en: 17, 52, 87, 122. (Aprox mitad de cuadrante)

    static final int[] ENTRY_NODES = { 0, 35, 70, 105 };
    static final int[] EXIT_NODES = { 17, 52, 87, 122 };

    static final double ENTRY_RATE_PER_MIN = 20.0; // por cada entrada
    static final double OUT_RATE_PER_MIN = 60.0; // por cada salida

    static final int TICK_MS = 100;
    static final int MIN_GAP = 1;

    // ===== Modelo =====
    static class Car {
        final int id;
        final int destExitIndex; // Índice en el array de exits (0..NUM_EXITS-1)
        int pos;

        Car(int id, int destExitIndex, int pos) {
            this.id = id;
            this.destExitIndex = destExitIndex;
            this.pos = pos;
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
    final Car[] roundabout = new Car[ROUND_LEN];

    // Arrays de Arrays para Entradas y Salidas
    final List<Car[]> entries = new ArrayList<>();
    final List<Car[]> exits = new ArrayList<>();

    // Colas de espera externas
    final List<ConcurrentLinkedQueue<Integer>> worldQueues = new ArrayList<>();

    // Stats
    final long[] exitedCounts;

    // Sinks
    final List<TokenBucket> exitBuckets = new ArrayList<>();

    final AtomicInteger idGen = new AtomicInteger(1);

    // ===== GUI =====
    JFrame frame;
    SimPanel panel;

    // ===== Scheduler =====
    final ScheduledExecutorService sched = Executors.newScheduledThreadPool(8);

    public TrafficSwingSim() {
        // Inicializar estructuras dinámicas
        for (int i = 0; i < ENTRY_NODES.length; i++) {
            entries.add(new Car[ENTRY_LEN]);
            worldQueues.add(new ConcurrentLinkedQueue<>());
        }

        exitedCounts = new long[EXIT_NODES.length];
        for (int i = 0; i < EXIT_NODES.length; i++) {
            exits.add(new Car[EXIT_LEN]);
            exitBuckets.add(new TokenBucket(OUT_RATE_PER_MIN));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TrafficSwingSim().start());
    }

    void start() {
        frame = new JFrame("Traffic Sim - 4 Entradas / 4 Salidas");
        panel = new SimPanel(this);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Generadores de tráfico
        for (ConcurrentLinkedQueue<Integer> q : worldQueues) {
            scheduleArrivals(q, ENTRY_RATE_PER_MIN);
        }

        // Loop de simulación
        final long[] lastTick = { System.currentTimeMillis() };
        sched.scheduleAtFixedRate(() -> {
            long now = System.currentTimeMillis();
            long dt = now - lastTick[0];
            lastTick[0] = now;
            tick(dt);
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
        if (ratePerMin <= 0)
            return;
        long periodMs = Math.max(1, Math.round(60000.0 / ratePerMin));
        sched.scheduleAtFixedRate(() -> q.add(idGen.getAndIncrement()),
                0, periodMs, TimeUnit.MILLISECONDS);
    }

    // ===== Lógica Principal del Tick =====
    void tick(long dtMs) {
        // Actualizar buckets
        for (TokenBucket tb : exitBuckets) {
            tb.addTimeMs(dtMs);
        }

        // 1. Mover coches en carreteras de SALIDA
        for (int i = 0; i < exits.size(); i++) {
            moveOnExitRoad(exits.get(i), i);
        }

        // 2. Transferir de Rotonda -> Salida
        for (int i = 0; i < EXIT_NODES.length; i++) {
            tryExitRoundabout(EXIT_NODES[i], exits.get(i), i);
        }

        // 3. Mover coches DENTRO de la rotonda
        moveOnRoundabout();

        // 4. Transferir de Entrada -> Rotonda
        for (int i = 0; i < ENTRY_NODES.length; i++) {
            tryEnterRoundabout(ENTRY_NODES[i], entries.get(i));
        }

        // 5. Mover coches en carreteras de ENTRADA
        for (int i = 0; i < entries.size(); i++) {
            moveOnEntryRoad(entries.get(i), worldQueues.get(i));
        }
    }

    // --- Lógica de Movimiento ---

    void moveOnExitRoad(Car[] road, int exitIndex) {
        for (int i = road.length - 1; i >= 0; i--) {
            if (road[i] == null)
                continue;

            // Salir del mapa
            if (i == road.length - 1) {
                if (exitBuckets.get(exitIndex).tryConsumeOne()) {
                    road[i] = null;
                    exitedCounts[exitIndex]++;
                }
                continue;
            }

            // Avanzar
            int next = i + 1;
            if (isGapFreeLinear(road, next)) {
                road[next] = road[i];
                road[next].pos = next;
                road[i] = null;
            }
        }
    }

    void moveOnEntryRoad(Car[] road, ConcurrentLinkedQueue<Integer> queue) {
        // 1. Mover existentes
        for (int i = road.length - 2; i >= 0; i--) {
            if (road[i] == null)
                continue;
            int next = i + 1;
            if (road[next] == null) {
                road[next] = road[i];
                road[next].pos = next;
                road[i] = null;
            }
        }

        // 2. Meter nuevos
        if (road[0] == null && !queue.isEmpty()) {
            Integer id = queue.poll();
            if (id != null) {
                // Asignar destino aleatorio (índice de salida 0..N-1)
                int dest = ThreadLocalRandom.current().nextInt(EXIT_NODES.length);
                road[0] = new Car(id, dest, 0);
            }
        }
    }

    void tryEnterRoundabout(int nodeIdx, Car[] entryRoad) {
        int lastEntryIdx = entryRoad.length - 1;
        Car c = entryRoad[lastEntryIdx];
        if (c == null)
            return;

        if (roundabout[nodeIdx] == null) {
            boolean safe = true;
            for (int k = 1; k <= MIN_GAP + 2; k++) {
                int prev = (nodeIdx - k + ROUND_LEN) % ROUND_LEN;
                if (roundabout[prev] != null) {
                    safe = false;
                    break;
                }
            }
            if (safe) {
                entryRoad[lastEntryIdx] = null;
                c.pos = nodeIdx;
                roundabout[nodeIdx] = c;
            }
        }
    }

    void tryExitRoundabout(int nodeIdx, Car[] exitRoad, int exitIndex) {
        Car c = roundabout[nodeIdx];
        if (c == null)
            return;

        if (c.destExitIndex == exitIndex) {
            if (exitRoad[0] == null) {
                roundabout[nodeIdx] = null;
                c.pos = 0;
                exitRoad[0] = c;
            }
        }
    }

    void moveOnRoundabout() {
        Car[] nextRoundabout = new Car[ROUND_LEN];

        for (int i = 0; i < ROUND_LEN; i++) {
            Car c = roundabout[i];
            if (c == null)
                continue;

            int nextPos = (i + 1) % ROUND_LEN;
            boolean canMove = true;

            if (roundabout[nextPos] != null) {
                canMove = false;
            } else {
                for (int k = 1; k <= MIN_GAP; k++) {
                    int check = (nextPos + k) % ROUND_LEN;
                    if (roundabout[check] != null) {
                        canMove = false;
                        break;
                    }
                }
            }

            if (canMove) {
                c.pos = nextPos;
                nextRoundabout[nextPos] = c;
            } else {
                c.pos = i;
                nextRoundabout[i] = c;
            }
        }
        System.arraycopy(nextRoundabout, 0, roundabout, 0, ROUND_LEN);
    }

    boolean isGapFreeLinear(Car[] road, int targetPos) {
        if (targetPos >= road.length)
            return false;
        if (road[targetPos] != null)
            return false;
        for (int k = 1; k <= MIN_GAP; k++) {
            int p = targetPos + k;
            if (p < road.length && road[p] != null)
                return false;
        }
        return true;
    }

    // ===== Panel Visualización =====
    static class SimPanel extends JPanel {
        final TrafficSwingSim sim;

        SimPanel(TrafficSwingSim sim) {
            this.sim = sim;
            setBackground(new Color(245, 250, 245));
        }

        @Override
        protected void paintComponent(Graphics g0) {
            super.paintComponent(g0);
            Graphics2D g = (Graphics2D) g0.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int cx = w / 2;
            int cy = h / 2;
            int rRadius = 250;

            // Dibujar carreteras
            for (int i = 0; i < sim.entries.size(); i++) {
                double ang = getAngle(TrafficSwingSim.ENTRY_NODES[i]);
                drawRadialRoad(g, cx, cy, rRadius, ang, TrafficSwingSim.ENTRY_LEN, true, "E" + (i + 1));
            }

            for (int i = 0; i < sim.exits.size(); i++) {
                double ang = getAngle(TrafficSwingSim.EXIT_NODES[i]);
                drawRadialRoad(g, cx, cy, rRadius, ang, TrafficSwingSim.EXIT_LEN, false, "S" + (i + 1));
            }

            // Rotonda
            g.setStroke(new BasicStroke(26, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
            g.setColor(Color.LIGHT_GRAY);
            g.drawOval(cx - rRadius, cy - rRadius, rRadius * 2, rRadius * 2);
            g.setStroke(new BasicStroke(1f));
            g.setColor(Color.WHITE);
            g.drawOval(cx - rRadius - 13, cy - rRadius - 13, (rRadius + 13) * 2, (rRadius + 13) * 2);
            g.drawOval(cx - rRadius + 13, cy - rRadius + 13, (rRadius - 13) * 2, (rRadius - 13) * 2);

            // Coches
            for (int i = 0; i < sim.entries.size(); i++) {
                double ang = getAngle(TrafficSwingSim.ENTRY_NODES[i]);
                paintLineCars(g, sim.entries.get(i), cx, cy, rRadius, ang, true);
            }
            for (int i = 0; i < sim.exits.size(); i++) {
                double ang = getAngle(TrafficSwingSim.EXIT_NODES[i]);
                paintLineCars(g, sim.exits.get(i), cx, cy, rRadius, ang, false);
            }
            paintRoundCars(g, sim.roundabout, cx, cy, rRadius);

            // Stats
            g.setColor(Color.BLACK);
            g.setFont(new Font("Monospaced", Font.PLAIN, 12));
            int yText = 20;
            for (int i = 0; i < sim.worldQueues.size(); i++) {
                g.drawString("Cola E" + (i + 1) + ": " + sim.worldQueues.get(i).size(), 20, yText);
                yText += 16;
            }
            yText = 20;
            for (int i = 0; i < sim.exitedCounts.length; i++) {
                g.drawString("Salidas S" + (i + 1) + ": " + sim.exitedCounts[i], w - 120, yText);
                yText += 16;
            }

            g.dispose();
        }

        private double getAngle(int nodeIdx) {
            return -Math.PI / 2.0 + (2.0 * Math.PI * nodeIdx) / ROUND_LEN;
        }

        void drawRadialRoad(Graphics2D g, int cx, int cy, int rRadius, double angle, int cells, boolean isEntry,
                String label) {
            double cellLen = 14.0;
            double roadLenPx = cells * cellLen;
            double ux = Math.cos(angle);
            double uy = Math.sin(angle);
            double xStart = cx + ux * rRadius;
            double yStart = cy + uy * rRadius;
            double xEnd = xStart + ux * (roadLenPx + 20);
            double yEnd = yStart + uy * (roadLenPx + 20);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setStroke(new BasicStroke(24, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
            g2.setColor(Color.GRAY);
            g2.draw(new Line2D.Double(xStart, yStart, xEnd, yEnd));
            // Linea amarilla
            g2.setStroke(new BasicStroke(1f));
            g2.setColor(Color.YELLOW);
            g2.draw(new Line2D.Double(xStart, yStart, xEnd, yEnd));
            // Texto
            g2.setColor(Color.BLACK);
            g2.drawString(label, (int) xEnd, (int) yEnd);
            g2.dispose();
        }

        void paintLineCars(Graphics2D g, Car[] road, int cx, int cy, int rRadius, double angle, boolean isEntry) {
            double cellLen = 14.0;
            double ux = Math.cos(angle);
            double uy = Math.sin(angle);

            for (int i = 0; i < road.length; i++) {
                Car c = road[i];
                if (c == null)
                    continue;
                double distFromCircle;
                if (isEntry) {
                    distFromCircle = rRadius + 15 + (road.length - 1 - i) * cellLen;
                } else {
                    distFromCircle = rRadius + 15 + i * cellLen;
                }
                double carX = cx + ux * distFromCircle;
                double carY = cy + uy * distFromCircle;
                double carAngle = angle + (isEntry ? Math.PI : 0);
                drawCar(g, c, carX, carY, carAngle);
            }
        }

        void paintRoundCars(Graphics2D g, Car[] road, int cx, int cy, int rRadius) {
            for (int i = 0; i < road.length; i++) {
                Car c = road[i];
                if (c == null)
                    continue;
                double ang = getAngle(i);
                double carX = cx + Math.cos(ang) * rRadius;
                double carY = cy + Math.sin(ang) * rRadius;
                drawCar(g, c, carX, carY, ang + Math.PI / 2);
            }
        }

        void drawCar(Graphics2D g, Car c, double x, double y, double angleRad) {
            AffineTransform old = g.getTransform();
            g.translate(x, y);
            g.rotate(angleRad);

            // Color por destino (Cyclic colors)
            // HSB basado en index
            float h = (c.destExitIndex * 0.25f) % 1.0f;
            g.setColor(Color.getHSBColor(h, 0.7f, 0.9f));

            g.fillRoundRect(-10, -5, 20, 10, 4, 4);
            g.setColor(Color.BLACK);
            g.drawRoundRect(-10, -5, 20, 10, 4, 4);
            // Id text
            // g.setFont(new Font("Arial", Font.PLAIN, 9));
            // String s = String.valueOf(c.id);
            // g.drawString(s, -5, 2);

            // Dest text (S1, S2...)
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 9));
            g.drawString("S" + (c.destExitIndex + 1), -6, 3);

            g.setTransform(old);
        }
    }
}
