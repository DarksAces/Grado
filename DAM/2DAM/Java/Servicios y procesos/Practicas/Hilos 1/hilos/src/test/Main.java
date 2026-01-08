package test;

public class Main {
	public static void main(String[] args) {
		Cliente cliente1 = new Cliente("Cliente 1 DGB", new int[] { 2, 2, 1, 5, 2, 3 });
		Cliente cliente2 = new Cliente("Cliente 2 DGB", new int[] { 1, 3, 5, 1, 1 });
		Cajera cajera1 = new Cajera("Cajera 1 DGB");
		Cajera cajera2 = new Cajera("Cajera 2 DGB");
		// Tiempo inicial de referencia
		long initialTime = System.currentTimeMillis();
		cajera1.procesarCompra(cliente1, initialTime);
		cajera2.procesarCompra(cliente2, initialTime);
	}
}