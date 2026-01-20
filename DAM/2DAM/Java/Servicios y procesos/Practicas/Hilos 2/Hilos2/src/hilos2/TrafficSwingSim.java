package hilos2;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

// Ejecucion: java -cp bin hilos2.TrafficSwingSim
public class TrafficSwingSim {
    // ===== Configuración Visual =====
    static final int TICK_MS = 60;
    static final int ARRIVAL_RATE = 200; // ms

    // ===== Layout Fisico (Nodos) =====
    // 1. Rotonda OESTE
    static final int ROT_W_LEN = 20;
    // 2. Rotonda ESTE
    static final int ROT_E_LEN = 20;
    // 3. Autopista Connector (West -> East)
    static final int LINK_LEN = 40;
    // 4. Entradas/Salidas varias
    static final int ROAD_LEN = 30;

    // Model Classes
    static class Car {
        int id;
        Color color;
        // Navigation goal
        int destination; // 0=Local Exit, 1=Cross to other Roundabout

        // Location
        int zone; // 0=RoadIn, 1=RotWest, 2=Link, 3=RotEast, 4=ExitRoad
        int pos;

        Car(int id) {
            this.id = id;
            this.destination = (id % 2); // 50% stay local, 50% go far
            // Color logic
            if (destination == 0)
                this.color = new Color(50, 205, 50); // Green (Local)
            else
                this.color = new Color(255, 140, 0); // Orange (Transit)
        }
    }

    // Estructuras (Simplificadas a listas/arrays)
    // Usaremos Arrays para simular slots físicos
    Car[] rotWest = new Car[ROT_W_LEN];
    Car[] rotEast = new Car[ROT_E_LEN];
    Car[] link = new Car[LINK_LEN]; // W -> E
    Car[] entryW = new Car[ROAD_LEN]; // Feeder for West
    Car[] entryE = new Car[ROAD_LEN]; // Feeder for East (from top?)

    // Indices clave
    // RotWest: Entry at 0, Link Out at 10, Local Exit at 15
    // RotEast: Entry from Link at 0, Local Exit at 10

    final ConcurrentLinkedQueue<Car> spawnQueue = new ConcurrentLinkedQueue<>();
    final AtomicInteger idGen = new AtomicInteger(1);
    final Random rnd = new Random();

    // GUI
    JFrame frame;
    SimPanel panel;
    final ScheduledExecutorService sched = Executors.newScheduledThreadPool(4);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TrafficSwingSim().start());
    }

    void start() {
        frame = new JFrame("Complex Network: Dual Roundabouts & Highway");
        panel = new SimPanel(this);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.setSize(1100, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Spawner
        sched.scheduleAtFixedRate(() -> {
            spawnQueue.add(new Car(idGen.getAndIncrement()));
        }, 0, ARRIVAL_RATE, TimeUnit.MILLISECONDS);

        // Loop
        sched.scheduleAtFixedRate(() -> {
            tick();
            SwingUtilities.invokeLater(panel::repaint);
        }, 0, TICK_MS, TimeUnit.MILLISECONDS);
    }

    void tick() {
        // Proc Entries
        if (!spawnQueue.isEmpty()) {
            // Randomly pick West or East entry
            if (rnd.nextBoolean() && entryW[0] == null) {
                Car c = spawnQueue.poll();
                c.zone = 0;
                c.pos = 0;
                entryW[0] = c;
            } else if (!spawnQueue.isEmpty() && entryE[0] == null) {
                Car c = spawnQueue.poll();
                c.zone = 5;
                c.pos = 0; // Zone 5 logic for EntryE? Let's just use EntryE array
                entryE[0] = c;
            }
        }

        // Move flows (Reverse order of dependency usually better, or double buffer)
        // Here we do straightforward logic order:

        moveRotWest();
        moveRotEast();
        moveLink();
        moveEntries();
    }

    void moveEntries() {
        moveLinearRoad(entryW, 100); // 100 = Target Zone ID (Logic handled below)
        moveLinearRoad(entryE, 101);
    }

    // Special move logic for feeders
    void moveLinearRoad(Car[] road, int targetType) {
        for (int i = ROAD_LEN - 1; i >= 0; i--) {
            if (road[i] == null)
                continue;
            Car c = road[i];

            if (i == ROAD_LEN - 1) {
                // Try enter target
                if (targetType == 100) { // EntryW -> RotWest[0]
                    if (rotWest[0] == null) {
                        road[i] = null;
                        c.zone = 1;
                        c.pos = 0;
                        rotWest[0] = c;
                    }
                } else if (targetType == 101) { // EntryE -> RotEast[10] (Opposite side)
                    if (rotEast[10] == null) {
                        road[i] = null;
                        c.zone = 3;
                        c.pos = 10;
                        rotEast[10] = c;
                    }
                }
                continue;
            }
            if (road[i + 1] == null) {
                road[i] = null;
                c.pos = i + 1;
                road[i + 1] = c;
            }
        }
    }

    void moveLink() { // Connects RotWest -> RotEast
        for (int i = LINK_LEN - 1; i >= 0; i--) {
            if (link[i] == null)
                continue;
            Car c = link[i];
            if (i == LINK_LEN - 1) {
                // Enters RotEast at 0
                if (rotEast[0] == null) {
                    link[i] = null;
                    c.zone = 3;
                    c.pos = 0;
                    rotEast[0] = c;
                }
                continue;
            }
            if (link[i + 1] == null) {
                link[i] = null;
                c.pos = i + 1;
                link[i + 1] = c;
            }
        }
    }

    void moveRotWest() {
        Car[] next = new Car[ROT_W_LEN];
        for (int i = 0; i < ROT_W_LEN; i++) {
            if (rotWest[i] != null) {
                Car c = rotWest[i];
                boolean moved = false;

                // Exits?
                // If Dest=1 (Transit) try exit at 10 (Link)
                if (c.destination == 1 && i == 10) {
                    if (link[0] == null) {
                        c.zone = 2;
                        c.pos = 0;
                        link[0] = c;
                        moved = true;
                    }
                }
                // If Dest=0 (Local) try exit at 15
                else if (c.destination == 0 && i == 15) {
                    // Just vanish (Exit City)
                    moved = true;
                }

                if (!moved) {
                    int nPos = (i + 1) % ROT_W_LEN;
                    if (rotWest[nPos] == null && next[nPos] == null) {
                        next[nPos] = rotWest[i];
                        next[nPos].pos = nPos;
                    } else {
                        next[i] = rotWest[i];
                    }
                }
            }
        }
        for (int k = 0; k < ROT_W_LEN; k++)
            rotWest[k] = next[k];
    }

    void moveRotEast() {
        Car[] next = new Car[ROT_E_LEN];
        for (int i = 0; i < ROT_E_LEN; i++) {
            if (rotEast[i] != null) {
                Car c = rotEast[i];
                boolean moved = false;

                // Exits? All exit at 5 (South) or 15 (North)?
                // Let's say all East traffic exits at 15
                if (i == 15) {
                    // Exit
                    moved = true;
                }

                if (!moved) {
                    int nPos = (i + 1) % ROT_E_LEN;
                    if (rotEast[nPos] == null && next[nPos] == null) {
                        next[nPos] = rotEast[i];
                        next[nPos].pos = nPos;
                    } else {
                        next[i] = rotEast[i];
                    }
                }
            }
        }
        for (int k = 0; k < ROT_E_LEN; k++)
            rotEast[k] = next[k];
    }

    // ===== VISUAL =====
    static class SimPanel extends JPanel {
        TrafficSwingSim sim;

        SimPanel(TrafficSwingSim sim) {
            this.sim = sim;
            setBackground(new Color(30, 100, 30)); // Green field
        }

        @Override
        protected void paintComponent(Graphics g0) {
            super.paintComponent(g0);
            Graphics2D g = (Graphics2D) g0;
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int yC = 250;
            int xRotW = 300;
            int xRotE = 800;
            int rRad = 60;

            // Draw Roads
            g.setColor(Color.GRAY);
            // EntryW
            g.fillRect(0, yC - 20 - 45, xRotW, 40); // Diagonal-ish visual fix? No, just straight box
            // Actually let's draw EntryW coming from Left
            g.fillRect(50, yC - 20, 250, 40);

            // Link
            g.fillRect(xRotW, yC - 20, xRotE - xRotW, 40);

            // EntryE (from Top into East Rot)
            g.fillRect(xRotE - 20, 50, 40, 200);

            // Exits (Visual Stubs)
            g.fillRect(xRotW, yC + 40, 40, 100); // South Exit W
            g.fillRect(xRotE, yC + 40, 40, 100); // South Exit E

            // Roundabouts
            drawRot(g, xRotW, yC, rRad);
            drawRot(g, xRotE, yC, rRad);

            // Cars
            // Entry W (Horizontal)
            for (int i = 0; i < ROAD_LEN; i++) {
                if (sim.entryW[i] != null)
                    drawCar(g, 50 + i * 8, yC - 10, sim.entryW[i]);
            }
            // Entry E (Vertical) from top (inverted index logic for visual flow down)
            for (int i = 0; i < ROAD_LEN; i++) {
                if (sim.entryE[i] != null)
                    drawCar(g, xRotE - 10, 50 + i * 6, sim.entryE[i]);
            }
            // Link
            for (int i = 0; i < LINK_LEN; i++) {
                if (sim.link[i] != null)
                    drawCar(g, xRotW + 60 + i * 10, yC - 10, sim.link[i]);
            }

            // Rot West
            drawRotCars(g, xRotW, yC, rRad, sim.rotWest);
            // Rot East
            drawRotCars(g, xRotE, yC, rRad, sim.rotEast);

            // Labels
            g.setColor(Color.WHITE);
            g.drawString("WEST ROUNDABOUT", xRotW - 50, yC - 80);
            g.drawString("EAST ROUNDABOUT", xRotE - 50, yC - 80);
            g.drawString("HIGHWAY", (xRotW + xRotE) / 2 - 20, yC - 30);
        }

        void drawRot(Graphics2D g, int x, int y, int r) {
            g.setColor(Color.DARK_GRAY);
            g.fillOval(x - r, y - r, r * 2, r * 2);
            g.setColor(new Color(30, 100, 30));
            g.fillOval(x - r / 2, y - r / 2, r, r);
        }

        void drawRotCars(Graphics2D g, int cx, int cy, int r, Car[] rot) {
            int len = rot.length;
            for (int i = 0; i < len; i++) {
                if (rot[i] != null) {
                    double ang = -Math.PI + (2 * Math.PI * i) / len; // Start left
                    int rad = (int) (r * 0.75);
                    int x = cx + (int) (rad * Math.cos(ang));
                    int y = cy + (int) (rad * Math.sin(ang));
                    drawCar(g, x - 8, y - 8, rot[i]);
                }
            }
        }

        void drawCar(Graphics2D g, int x, int y, Car c) {
            g.setColor(c.color);
            g.fillRoundRect(x, y, 16, 16, 6, 6);
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(2));
            g.drawRoundRect(x, y, 16, 16, 6, 6);

            g.setFont(new Font("Arial", Font.BOLD, 10));
            g.drawString("" + c.id, x + 2, y + 12);
        }
    }
}
