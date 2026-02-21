package hilos2;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Traffic Sim DGB: CAOS REFINADO V2.
 * Inmunidad al entrar, colisiones robustas y frenado inteligente.
 */
public class TrafficSwingSim {

    // ===== Configuración Global (Experimentos DGB) =====
    static final double ENTRY_RATE_PER_MIN = 60.0;
    static final int TICK_MS = 80;
    static final int MIN_GAP = 1;

    static final int NUM_ROUNDABOUTS = 100000;
    static final int ROUND_SIZE = 40;
    static final int ROAD_LENGTH = 10;

    static final float MAX_SAFE_SPEED = 0.85f;
    static final long CRASH_DURATION_MS = 5000;
    static final long IMMUNITY_DURATION_MS = 3000; // Prolongamos un poco DGB
    static final float ROUNDABOUT_SPEED_BOOST = 0.25f; // Más turbo DGB

    final AtomicInteger arrivedCount = new AtomicInteger(0);
    final AtomicInteger deathCount = new AtomicInteger(0);

    // ===== Componentes del Modelo =====
    static class Car {
        final int id;
        final Color color;
        final float speed;
        final boolean wrongWay;
        long entryTime = -1; // Se activa al entrar DGB

        boolean isCrashed = false;
        long crashTime = 0;
        Point deathLocation = null;
        float currentBrakeFactor = 1.0f;

        Car(int id) {
            this.id = id;
            float h = ThreadLocalRandom.current().nextFloat();
            this.color = Color.getHSBColor(h, 0.8f, 1.0f);
            this.speed = 0.4f + ThreadLocalRandom.current().nextFloat() * 0.6f;
            this.wrongWay = ThreadLocalRandom.current().nextFloat() < 0.05;
        }

        void setEntry() {
            this.entryTime = System.currentTimeMillis();
        }

        boolean isImmune() {
            if (entryTime == -1)
                return true; // Inmune mientras espera DGB
            return (System.currentTimeMillis() - entryTime) < IMMUNITY_DURATION_MS;
        }

        boolean shouldMove(float extraBoost) {
            if (isCrashed)
                return false;
            float finalSpeed = Math.min(1.0f, (speed + extraBoost) * currentBrakeFactor);
            return ThreadLocalRandom.current().nextFloat() < finalSpeed;
        }
    }

    static class Road {
        final String name;
        final Car[] cells;
        final int x1, y1, x2, y2;

        Road(String name, int len, int x1, int y1, int x2, int y2) {
            this.name = name;
            this.cells = new Car[len];
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        void tick() {
            for (int i = 0; i < cells.length; i++) {
                if (cells[i] == null || cells[i].isCrashed)
                    continue;
                cells[i].currentBrakeFactor = calculateBrakeFactor(i);
            }

            for (int i = cells.length - 2; i >= 0; i--) {
                if (cells[i] != null && !cells[i].isCrashed) {
                    Car car = cells[i];
                    if (cells[i + 1] == null) {
                        if (car.shouldMove(0)) {
                            cells[i + 1] = car;
                            cells[i] = null;
                        }
                    } else if (cells[i + 1].isCrashed) {
                        // Inmunidad Ghost DGB
                        if (!car.isImmune() && car.shouldMove(0)) {
                            car.isCrashed = true;
                            car.crashTime = System.currentTimeMillis();
                        }
                    }
                }
            }
        }

        private float calculateBrakeFactor(int pos) {
            for (int k = 1; k <= 3; k++) {
                int look = pos + k;
                if (look < cells.length && cells[look] != null && cells[look].isCrashed)
                    return 0.2f;
            }
            return 1.0f;
        }
    }

    static class Roundabout {
        final String name;
        final Car[] cells;
        final int cx, cy, radius;

        Roundabout(String name, int size, int cx, int cy, int radius) {
            this.name = name;
            this.cells = new Car[size];
            this.cx = cx;
            this.cy = cy;
            this.radius = radius;
        }

        void tick() {
            Car[] next = new Car[cells.length];
            // Conservar estrellados e inmóviles DGB
            for (int i = 0; i < cells.length; i++)
                if (cells[i] != null && cells[i].isCrashed)
                    next[i] = cells[i];

            for (int i = 0; i < cells.length; i++) {
                Car car = cells[i];
                if (car == null || car.isCrashed)
                    continue;

                // Derrape (No para inmunes DGB)
                if (!car.isImmune() && car.speed > MAX_SAFE_SPEED && ThreadLocalRandom.current().nextFloat() < 0.05) {
                    car.isCrashed = true;
                    car.crashTime = System.currentTimeMillis();
                    next[i] = car;
                    continue;
                }

                int step = car.wrongWay ? -1 : 1;
                int nextIdx = (i + step + cells.length) % cells.length;

                // Colisión robusta: chequear celda actual y celda "destino" DGB
                if (cells[nextIdx] == null && next[nextIdx] == null) {
                    if (car.shouldMove(ROUNDABOUT_SPEED_BOOST)) {
                        next[nextIdx] = car;
                        // No nulificamos 'next[i]' aquí porque 'cells' se barre secuencialmente
                    } else {
                        next[i] = car;
                    }
                } else {
                    // Hay un obstáculo (estrellado o moviéndose ahí) DGB
                    Car obstacle = (cells[nextIdx] != null) ? cells[nextIdx] : next[nextIdx];
                    if (obstacle != null && (obstacle.isCrashed || obstacle.wrongWay != car.wrongWay)) {
                        if (!car.isImmune() && car.shouldMove(ROUNDABOUT_SPEED_BOOST)) {
                            car.isCrashed = true;
                            car.crashTime = System.currentTimeMillis();
                            next[i] = car;
                            if (obstacle != null && !obstacle.isImmune() && !obstacle.isCrashed) {
                                obstacle.isCrashed = true;
                                obstacle.crashTime = car.crashTime;
                            }
                        } else {
                            next[i] = car; // Se queda parado DGB
                        }
                    } else {
                        next[i] = car; // Cola DGB
                    }
                }
            }
            System.arraycopy(next, 0, cells, 0, cells.length);
        }
    }

    // ===== Estado =====
    final List<Roundabout> roundabouts = new ArrayList<>();
    final List<Road> roads = new ArrayList<>();
    final List<Car> cemetery = new CopyOnWriteArrayList<>();
    final ConcurrentLinkedQueue<Car> entryQueue = new ConcurrentLinkedQueue<>();
    final AtomicInteger idGen = new AtomicInteger(1);
    final ScheduledExecutorService sched = Executors.newScheduledThreadPool(4);

    public TrafficSwingSim() {
        double b = 60;
        for (int i = 0; i < NUM_ROUNDABOUTS; i++) {
            double theta = i * 0.42;
            double r = b * theta;
            int cx = (int) (r * Math.cos(theta));
            int cy = (int) (r * Math.sin(theta));
            int rad = 25 + (int) (i * 0.8);
            roundabouts.add(new Roundabout("R" + i + " DGB", ROUND_SIZE, cx, cy, rad));
        }
        for (int i = NUM_ROUNDABOUTS - 1; i > 0; i--) {
            Roundabout outer = roundabouts.get(i);
            Roundabout inner = roundabouts.get(i - 1);
            roads.add(new Road("C" + i, ROAD_LENGTH, outer.cx, outer.cy, inner.cx, inner.cy));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TrafficSwingSim().start());
    }

    void start() {
        JFrame frame = new JFrame("MODO CAOS REFINADO V2 DGB");
        SimPanel panel = new SimPanel(this);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        long period = (long) (60000.0 / ENTRY_RATE_PER_MIN);
        sched.scheduleAtFixedRate(() -> entryQueue.add(new Car(idGen.getAndIncrement())), 0, period,
                TimeUnit.MILLISECONDS);

        sched.scheduleAtFixedRate(() -> {
            tick();
            panel.repaint();
        }, 0, TICK_MS, TimeUnit.MILLISECONDS);
    }

    void tick() {
        for (Road r : roads)
            r.tick();
        for (Roundabout rb : roundabouts)
            rb.tick();

        long now = System.currentTimeMillis();
        processCrashesInCells(now);

        Roundabout outerRb = roundabouts.get(NUM_ROUNDABOUTS - 1);
        if (!entryQueue.isEmpty() && outerRb.cells[0] == null) {
            if (isSafe(outerRb, 0)) {
                Car car = entryQueue.poll();
                car.setEntry(); // Activar inmunidad AQUÍ DGB
                outerRb.cells[0] = car;
            }
        }

        for (int k = 0; k < roads.size(); k++) {
            int fromIdx = NUM_ROUNDABOUTS - 1 - k;
            int toIdx = NUM_ROUNDABOUTS - 2 - k;
            Roundabout from = roundabouts.get(fromIdx);
            Road road = roads.get(k);
            Roundabout to = roundabouts.get(toIdx);

            if (from.cells[10] != null && !from.cells[10].isCrashed) {
                if (road.cells[0] == null) {
                    if (from.cells[10].shouldMove(0)) {
                        road.cells[0] = from.cells[10];
                        from.cells[10] = null;
                    }
                } else if (!from.cells[10].isImmune() && road.cells[0].isCrashed) {
                    if (from.cells[10].shouldMove(0)) {
                        from.cells[10].isCrashed = true;
                        from.cells[10].crashTime = now;
                    }
                }
            }

            int last = road.cells.length - 1;
            if (road.cells[last] != null && !road.cells[last].isCrashed) {
                if (to.cells[20] == null) {
                    if (road.cells[last].shouldMove(ROUNDABOUT_SPEED_BOOST)) {
                        if (!road.cells[last].isImmune() && road.cells[last].speed > MAX_SAFE_SPEED
                                && ThreadLocalRandom.current().nextFloat() < 0.2) {
                            Car deadCar = road.cells[last];
                            deadCar.isCrashed = true;
                            deadCar.crashTime = now;
                        } else if (isSafe(to, 20)) {
                            to.cells[20] = road.cells[last];
                            road.cells[last] = null;
                        }
                    }
                } else if (!road.cells[last].isImmune() && to.cells[20].isCrashed) {
                    if (road.cells[last].shouldMove(ROUNDABOUT_SPEED_BOOST)) {
                        road.cells[last].isCrashed = true;
                        road.cells[last].crashTime = now;
                    }
                }
            }
        }

        Roundabout centerRb = roundabouts.get(0);
        if (centerRb.cells[5] != null && !centerRb.cells[5].isCrashed) {
            if (centerRb.cells[5].shouldMove(ROUNDABOUT_SPEED_BOOST)) {
                arrivedCount.incrementAndGet();
                centerRb.cells[5] = null;
            }
        }
    }

    private void processCrashesInCells(long now) {
        for (Road r : roads) {
            for (int i = 0; i < r.cells.length; i++) {
                if (r.cells[i] != null && r.cells[i].isCrashed && (now - r.cells[i].crashTime > CRASH_DURATION_MS)) {
                    moveToCemetery(r.cells[i]);
                    r.cells[i] = null;
                }
            }
        }
        for (Roundabout rb : roundabouts) {
            for (int i = 0; i < rb.cells.length; i++) {
                if (rb.cells[i] != null && rb.cells[i].isCrashed && (now - rb.cells[i].crashTime > CRASH_DURATION_MS)) {
                    moveToCemetery(rb.cells[i]);
                    rb.cells[i] = null;
                }
            }
        }
    }

    void moveToCemetery(Car car) {
        car.deathLocation = new Point(0, 0);
        cemetery.add(car);
        deathCount.incrementAndGet();
        if (cemetery.size() > 400)
            cemetery.remove(0);
    }

    boolean isSafe(Roundabout rb, int node) {
        for (int k = 1; k <= MIN_GAP + 1; k++) {
            int p = (node - k + rb.cells.length) % rb.cells.length;
            if (rb.cells[p] != null)
                return false;
        }
        return true;
    }

    // ===== Vista HUD CAOS REFINADO V2 DGB =====
    static class SimPanel extends JPanel {
        final TrafficSwingSim sim;

        SimPanel(TrafficSwingSim sim) {
            this.sim = sim;
            setBackground(new Color(15, 10, 20));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE;
            double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
            for (Roundabout rb : sim.roundabouts) {
                minX = Math.min(minX, rb.cx - rb.radius);
                maxX = Math.max(maxX, rb.cx + rb.radius);
                minY = Math.min(minY, rb.cy - rb.radius);
                maxY = Math.max(maxY, rb.cy + rb.radius);
            }

            double cemWidth = 350;
            double cemMargin = 100;
            double totalW = (maxX - minX) + cemMargin + cemWidth;
            double totalH = (maxY - minY);

            double scale = Math.min(getWidth() / (totalW * 1.1), (getHeight() - 80) / (totalH * 1.1));
            g2.scale(scale, scale);

            double transX = -minX + (getWidth() / scale - totalW) / 2;
            double transY = -minY + ((getHeight() - 80) / scale - totalH) / 2 + 80 / scale;
            g2.translate(transX, transY);

            // Cementerio DGB
            double cemX = maxX + cemMargin;
            g2.setColor(new Color(30, 25, 35));
            g2.fillRect((int) cemX, (int) minY, (int) cemWidth, (int) totalH);
            g2.setColor(Color.RED);
            g2.drawRect((int) cemX, (int) minY, (int) cemWidth, (int) totalH);
            g2.setFont(new Font("Monospaced", Font.BOLD, 30));
            g2.drawString("CAMPOSANTO DGB", (int) cemX + 10, (int) minY - 20);

            int slot = 0;
            for (Car dead : sim.cemetery) {
                int cols = 10;
                int dx = (int) (cemX + 20 + (slot % cols) * 30);
                int dy = (int) (minY + 20 + (slot / cols) * 30);
                g2.setColor(new Color(100, 100, 120));
                g2.fillOval(dx, dy, 12, 12);
                slot++;
                if (dy > maxY - 20)
                    break;
            }

            // Carreteras y Rotondas DGB
            g2.setStroke(new BasicStroke(8));
            for (Road r : sim.roads) {
                g2.setColor(new Color(35, 35, 50));
                g2.drawLine(r.x1, r.y1, r.x2, r.y2);
                for (int i = 0; i < r.cells.length; i++) {
                    if (r.cells[i] != null)
                        drawCar(g2, r.cells[i], (int) (r.x1 + (r.x2 - r.x1) * ((double) i / r.cells.length)),
                                (int) (r.y1 + (r.y2 - r.y1) * ((double) i / r.cells.length)));
                }
            }

            for (Roundabout rb : sim.roundabouts) {
                g2.setStroke(new BasicStroke(6));
                g2.setColor(rb == sim.roundabouts.get(0) ? Color.RED : new Color(70, 70, 90));
                g2.drawOval(rb.cx - rb.radius, rb.cy - rb.radius, rb.radius * 2, rb.radius * 2);
                for (int i = 0; i < rb.cells.length; i++) {
                    if (rb.cells[i] != null) {
                        double ang = (2.0 * Math.PI * i) / rb.cells.length;
                        drawCar(g2, rb.cells[i], (int) (rb.cx + Math.cos(ang) * rb.radius),
                                (int) (rb.cy + Math.sin(ang) * rb.radius));
                    }
                }
            }

            // HUD
            g2.setTransform(new AffineTransform());
            g2.setColor(new Color(0, 0, 0, 240));
            g2.fillRect(0, 0, getWidth(), 65);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Monospaced", Font.BOLD, 22));
            g2.drawString("INMUNIDAD AL ENTRAR + TURBO DGB | EXITOS: " + sim.arrivedCount.get() + " | MUERTES: "
                    + sim.deathCount.get(), 20, 40);
        }

        private void drawCar(Graphics2D g2, Car car, int x, int y) {
            if (car.isCrashed) {
                g2.setColor((System.currentTimeMillis() / 250) % 2 == 0 ? Color.YELLOW : Color.RED);
                g2.fillRect(x - 12, y - 12, 24, 24);
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Arial", Font.BOLD, 14));
                g2.drawString("X", x - 4, y + 5);
            } else {
                g2.setColor(car.color);
                g2.fillOval(x - 10, y - 10, 20, 20);

                if (car.isImmune()) {
                    g2.setColor(Color.CYAN);
                    g2.setStroke(new BasicStroke(3));
                    g2.drawOval(x - 14, y - 14, 28, 28);
                } else if (car.wrongWay) {
                    g2.setColor(Color.WHITE);
                    g2.setStroke(new BasicStroke(2));
                    g2.drawOval(x - 14, y - 14, 28, 28);
                }
            }
        }
    }
}
