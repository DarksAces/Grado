package hilos2;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
public class TrafficSwingSim {
    // ===== Configuración =====
    static final int ROAD_LEN = 70;
    static final int ENTRY1_POS = 0;
    static final int ENTRY2_POS = 12;
    static final int EXIT1_POS = 40;
    static final int EXIT2_POS = 65;
    static final double ENTRY1_RATE_PER_MIN = 200;
    static final double ENTRY2_RATE_PER_MIN = 200;
    static final double EXIT1_RATE_PER_MIN = 18;
    static final double EXIT2_RATE_PER_MIN = 10;
    static final int TICK_MS = 120;
    // Distancia mínima: número de celdas VACÍAS que deben existir por delante de un coche
    static final int MIN_GAP = 2; // prueba 1, 2, 3...
    // ===== Modelo =====
    static class Car {
        final int id;
        int pos;
        Car(int id, int pos) {
            this.id = id;
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
    final Car[] road = new Car[ROAD_LEN];
    final ConcurrentLinkedQueue<Integer> pendingE1 = new ConcurrentLinkedQueue<>();
    final ConcurrentLinkedQueue<Integer> pendingE2 = new ConcurrentLinkedQueue<>();
    long maxQueueE1 = 0;
    long maxQueueE2 = 0;
    final AtomicInteger idGen = new AtomicInteger(1);
    long exited1 = 0;
    long exited2 = 0;
    final TokenBucket exit1Bucket = new TokenBucket(EXIT1_RATE_PER_MIN);
    final TokenBucket exit2Bucket = new TokenBucket(EXIT2_RATE_PER_MIN);
    // ===== GUI =====
    JFrame frame;
    SimPanel panel;
    // ===== Scheduler =====
    final ScheduledExecutorService sched = Executors.newScheduledThreadPool(4);
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TrafficSwingSim().start());
    }
    void start() {
        frame = new JFrame("Traffic Swing Sim (gap + colas, 1 carril, 2 entradas, 2 salidas)");
        panel = new SimPanel(this);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.setSize(1100, 280);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        scheduleArrivals(pendingE1, ENTRY1_RATE_PER_MIN);
        scheduleArrivals(pendingE2, ENTRY2_RATE_PER_MIN);
        final long[] lastTick = {System.currentTimeMillis()};
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
        if (ratePerMin <= 0) {
            return;
        }
        long periodMs = Math.max(1, Math.round(60000.0 / ratePerMin));
        sched.scheduleAtFixedRate(() -> q.add(idGen.getAndIncrement()),
                0, periodMs, TimeUnit.MILLISECONDS);
    }
    // ===== Tick =====
    void tick(long dtMs) {
        exit1Bucket.addTimeMs(dtMs);
        exit2Bucket.addTimeMs(dtMs);
        processEntries();
        processMovementAndExits();
        // métricas de cola
        maxQueueE1 = Math.max(maxQueueE1, pendingE1.size());
        maxQueueE2 = Math.max(maxQueueE2, pendingE2.size());
    }
    // ¿Se puede “ocupar” pos respetando MIN_GAP por delante?
    boolean canOccupyWithGap(int pos) {
        if (pos < 0 || pos >= ROAD_LEN) {
            return false;
        }
        if (road[pos] != null) {
            return false;
        }
        // requiere MIN_GAP celdas libres por delante (si existen)
        for (int k = 1; k <= MIN_GAP; k++) {
            int ahead = pos + k;
            if (ahead >= ROAD_LEN) {
                break;
            }
            if (road[ahead] != null) {
                return false;
            }
        }
        return true;
    }
    void processEntries() {
        // Entrada 1
        Integer id1 = pendingE1.peek();
        if (id1 != null && canOccupyWithGap(ENTRY1_POS)) {
            pendingE1.poll();
            road[ENTRY1_POS] = new Car(id1, ENTRY1_POS);
        }
        // Entrada 2
        Integer id2 = pendingE2.peek();
        if (id2 != null && canOccupyWithGap(ENTRY2_POS)) {
            pendingE2.poll();
            road[ENTRY2_POS] = new Car(id2, ENTRY2_POS);
        }
    }
    void processMovementAndExits() {
        for (int i = ROAD_LEN - 1; i >= 0; i--) {
            Car c = road[i];
            if (c == null) {
                continue;
            }
            // Salidas: si está en la salida y hay token -> sale
            if (i == EXIT1_POS) {
                if (exit1Bucket.tryConsumeOne()) {
                    road[i] = null;
                    exited1++;
                    continue;
                }
                // Si no puede salir, se queda (y el gap hará que el de detrás no se pegue)
            }
            if (i == EXIT2_POS) {
                if (exit2Bucket.tryConsumeOne()) {
                    road[i] = null;
                    exited2++;
                    continue;
                }
            }
            // Al final, lo sacamos del sistema (opcional)
            if (i == ROAD_LEN - 1) {
                road[i] = null;
                continue;
            }
            // Movimiento con distancia mínima: solo avanza si al ocupar i+1 se respeta el gap
            int next = i + 1;
            if (canOccupyWithGap(next)) {
                road[i] = null;
                c.pos = next;
                road[next] = c;
            }
        }
    }
    // ===== Panel =====
    static class SimPanel extends JPanel {
        final TrafficSwingSim sim;
        SimPanel(TrafficSwingSim sim) {
            this.sim = sim;
            setBackground(Color.WHITE);
            setFont(new Font("SansSerif", Font.PLAIN, 12));
        }
        @Override
        protected void paintComponent(Graphics g0) {
            super.paintComponent(g0);
            Graphics2D g = (Graphics2D) g0.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();
            int margin = 30;
            int roadY = h / 2;
            int roadH = 46;
            int roadX = margin;
            int roadW = w - 2 * margin;
            // carretera
            g.setColor(new Color(230, 230, 230));
            g.fillRoundRect(roadX, roadY - roadH / 2, roadW, roadH, 16, 16);
            g.setColor(Color.DARK_GRAY);
            g.drawRoundRect(roadX, roadY - roadH / 2, roadW, roadH, 16, 16);
            double cellW = roadW / (double) ROAD_LEN;
            int carH = 26;
            int carY = roadY - carH / 2;
            drawMarker(g, roadX, roadY, cellW, ENTRY1_POS, "E1", new Color(70, 130, 180));
            drawMarker(g, roadX, roadY, cellW, ENTRY2_POS, "E2", new Color(70, 130, 180));
            drawMarker(g, roadX, roadY, cellW, EXIT1_POS, "X1", new Color(46, 139, 87));
            drawMarker(g, roadX, roadY, cellW, EXIT2_POS, "X2", new Color(46, 139, 87));
            // coches
            for (int i = 0; i < ROAD_LEN; i++) {
                Car c = sim.road[i];
                if (c == null) {
                    continue;
                }
                int x = (int) Math.round(roadX + i * cellW + 2);
                int cw = (int) Math.max(12, Math.round(cellW - 4));
                Color col = Color.getHSBColor((c.id % 24) / 24f, 0.55f, 0.90f);
                g.setColor(col);
                g.fillRoundRect(x, carY, cw, carH, 10, 10);
                g.setColor(Color.BLACK);
                g.drawRoundRect(x, carY, cw, carH, 10, 10);
                String label = String.valueOf(c.id);
                FontMetrics fm = g.getFontMetrics();
                int tx = x + (cw - fm.stringWidth(label)) / 2;
                int ty = carY + (carH + fm.getAscent()) / 2 - 2;
                g.drawString(label, tx, ty);
            }
            // HUD
            g.setColor(Color.BLACK);
            g.drawString(
                    "Cola E1: " + sim.pendingE1.size() + " (max " + sim.maxQueueE1 + ")"
                    + " | Cola E2: " + sim.pendingE2.size() + " (max " + sim.maxQueueE2 + ")"
                    + " | Salidos X1: " + sim.exited1
                    + " | Salidos X2: " + sim.exited2,
                    margin, 18
            );
            g.drawString(
                    String.format("E1=%.1f/min E2=%.1f/min | X1 cap=%.1f/min X2 cap=%.1f/min | Tick=%dms | MIN_GAP=%d",
                            ENTRY1_RATE_PER_MIN, ENTRY2_RATE_PER_MIN, EXIT1_RATE_PER_MIN, EXIT2_RATE_PER_MIN, TICK_MS, MIN_GAP),
                    margin, h - 12
            );
            g.dispose();
        }
        static void drawMarker(Graphics2D g, int roadX, int roadY, double cellW, int pos, String text, Color color) {
            int x = (int) Math.round(roadX + pos * cellW);
            int yTop = roadY - 48;
            g.setColor(color);
            g.fillRoundRect(x + 2, yTop, (int) Math.max(14, cellW - 4), 18, 8, 8);
            g.setColor(Color.WHITE);
            g.drawString(text, x + 6, yTop + 13);
            g.setColor(color.darker());
            g.drawLine(x + (int) (cellW / 2), yTop + 18, x + (int) (cellW / 2), roadY - 24);
        }
    }
}
