package Array2D;

package javaapplication26;

/**
 *
 * @author danielgarbru
 */
static final int MAX_PAISES = 7;
public class JavaApplication26 {
    public static void main(String[] args){
//        int[] miArray1D = {1, 2, 3};
//        int[][] miArray2D = {{1,2,3,4} ,{5,6,7,8} ,{9,10,11,12} };
String[] paises = {"ANDORRA", "SPAIN", "PERU", "MEXICO", "USA", "ECUADOR", "UK"};
String[] medallas = {"ORO", "PLATA", "BRONCE"};
//int[][] contadorMedallas = {{0,2,3},{15,16,0},{10,0,0},{3,5,8},{8,23,0},{0,1,0},{3,3,3}};
        System.out.printf("%-10s", "PAISES: ");
        for (String medalla : medallas) {
            System.out.printf("%-8s",medalla);  
        }
        System.out.println("");
        for (int i = 0; i < MAX_PAISES; i++) {
            System.out.printf("%-10s: ", paises[i]);
            for (int j = 0; j < MAX_MEDALLAS; j++) {
                System.out.printf("%3d",contadorMedallas[i][j]);
            }
        }
    }
}
