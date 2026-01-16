package test;

public class EjemploGruposHilos {
    // Tarea genérica para los hilos
    static class Tarea implements Runnable {
        private final String nombreTarea;

        public Tarea(String nombreTarea) {
            this.nombreTarea = nombreTarea;
        }

        @Override
        public void run() {
            Thread hiloActual = Thread.currentThread();
            ThreadGroup grupo = hiloActual.getThreadGroup();
            for (int i = 1; i <= 5; i++) {
                System.out.println(
                        "[" + grupo.getName() + "] "
                                + hiloActual.getName()
                                + " - " + nombreTarea
                                + " - iteración " + i + " DGB");
                try {
                    Thread.sleep(400); // un poco de tiempo para ver el intercalado
                } catch (InterruptedException e) {
                    System.out.println(hiloActual.getName() + " interrumpido DGB");
                    return;
                }
            }
            System.out.println("[" + grupo.getName() + "] " + hiloActual.getName() + " ha terminado. DGB");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // ====== 1. Creación de grupos ======
        ThreadGroup grupoPadre = new ThreadGroup("Grupo Padre");
        ThreadGroup subgrupo = new ThreadGroup(grupoPadre, "Grupo Hijo");
        System.out.println("Grupo padre: " + grupoPadre.getName() + " DGB");
        System.out.println("Subgrupo: " + subgrupo.getName() + " DGB");
        System.out.println("Padre del subgrupo: " + subgrupo.getParent().getName() + " DGB");
        // ====== 2. Creación de hilos dentro de los grupos ======
        Thread hilo1 = new Thread(grupoPadre, new Tarea("Tarea A"), "Hilo-A (padre)");
        Thread hilo2 = new Thread(grupoPadre, new Tarea("Tarea B"), "Hilo-B (padre)");
        Thread hilo3 = new Thread(subgrupo, new Tarea("Tarea C"), "Hilo-C (hijo)");
        // (Ejemplo con lambda equivalente, por si quieres enseñarlo)
        // Thread hiloLambda = new Thread(grupoPadre, () -> {
        // System.out.println("Soy un hilo con lambda en " +
        // Thread.currentThread().getThreadGroup().getName());
        // }, "Hilo-Lambda");
        // ====== 3. Arrancar los hilos ======
        hilo1.start();
        hilo2.start();
        hilo3.start();
        // hiloLambda.start();

        Thread hiloDefault = new Thread(new Tarea("Tarea Default"), "Hilo-Default");
        hiloDefault.start();
        System.out.println("Grupo de Hilo-Default: " + hiloDefault.getThreadGroup().getName() + " DGB");
        // Esperamos un poco para asegurarnos de que ya han arrancado
        Thread.sleep(200);
        // ====== 4. Información de grupos mientras los hilos están activos ======
        System.out.println("\n--- Info de grupos mientras los hilos están activos --- DGB");
        System.out.println("Hilos activos en grupoPadre (solo ese grupo): " + grupoPadre.activeCount() + " DGB");
        System.out.println("Subgrupos activos dentro de grupoPadre: " + grupoPadre.activeGroupCount() + " DGB");
        // Lista de hilos dentro de grupoPadre (sin recorrer subgrupos)
        Thread[] listaHilos = new Thread[grupoPadre.activeCount()];
        int numHilos = grupoPadre.enumerate(listaHilos, true); // true = incluir subgrupos
        System.out.println("\nHilos encontrados en grupoPadre (CON subgrupos): DGB");
        for (int i = 0; i < numHilos; i++) {
            Thread t = listaHilos[i];
            System.out.println("- " + t.getName() + " (grupo: " + t.getThreadGroup().getName() + ") DGB");
        }
        // Lista de subgrupos dentro de grupoPadre
        ThreadGroup[] listaGrupos = new ThreadGroup[grupoPadre.activeGroupCount()];
        int numGrupos = grupoPadre.enumerate(listaGrupos, false);
        System.out.println("\nSubgrupos dentro de grupoPadre: DGB");
        for (int i = 0; i < numGrupos; i++) {
            ThreadGroup tg = listaGrupos[i];
            System.out.println("- " + tg.getName() + " DGB");
        }
        // ====== 5. Prioridades a nivel de grupo ======
        System.out.println("\n--- Prioridades y grupos --- DGB");
        System.out.println("Prioridad máxima inicial del subgrupo: " + subgrupo.getMaxPriority() + " DGB");
        subgrupo.setMaxPriority(4); // fijamos prioridad máxima del subgrupo en 4
        System.out.println("Nueva prioridad máxima del subgrupo: " + subgrupo.getMaxPriority() + " DGB");
        Thread hiloPrioridad = new Thread(subgrupo, new Tarea("Tarea Prioridad"), "Hilo-Prioridad");
        // Intentamos ponerle prioridad máxima global
        hiloPrioridad.setPriority(Thread.MAX_PRIORITY); // 10
        System.out.println("Prioridad solicitada para Hilo-Prioridad: " + Thread.MAX_PRIORITY + " DGB");
        System.out.println("Prioridad real de Hilo-Prioridad (limitada por el grupo): "
                + hiloPrioridad.getPriority() + " DGB");
        hiloPrioridad.start();
        // ====== 6. Esperar a que terminen los hilos ======
        hilo1.join();
        hilo2.join();
        hilo3.join();
        hiloPrioridad.join();
        // hiloLambda.join();
        System.out.println("\n--- Info de grupos tras terminar los hilos --- DGB");
        System.out.println("Hilos activos en grupoPadre: " + grupoPadre.activeCount() + " DGB");
        System.out.println("Subgrupos activos en grupoPadre: " + grupoPadre.activeGroupCount() + " DGB");
        System.out.println("\nFin de main(). DGB");
    }
}
