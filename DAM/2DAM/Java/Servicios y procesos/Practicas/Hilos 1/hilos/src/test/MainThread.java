package test;

public class MainThread {
	public static void main(String[] args) {
		Cliente cliente1 = new Cliente("Cliente 1 DGB", new int[] { 2, 2, 1, 5, 2, 3 });
		Cliente cliente2 = new Cliente("Cliente 2 DGB", new int[] { 1, 3, 5, 1, 1 });
		// Tiempo inicial de referencia
		long initialTime = System.currentTimeMillis();
		CajeraThread cajera1 = new CajeraThread("Cajera 1 DGB", cliente1, initialTime);
		CajeraThread cajera2 = new CajeraThread("Cajera 2 DGB", cliente2, initialTime);
		cajera1.start();
		cajera2.start();
	}
}