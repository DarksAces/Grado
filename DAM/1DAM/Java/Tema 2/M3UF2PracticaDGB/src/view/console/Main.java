package view.console;

import java.util.Scanner;
import model.validations.UserDataValidations;

/**
 *
 * @author danielgarbru
 */
public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String opcion = "";
        do {
            System.out.println("Menu");
            System.out.println("1.- Tester checkID");
            System.out.println("2.- Tester checkFormatDate");
            System.out.println("3.- Tester CalculateAge");
            System.out.println("4.- Tester CheckPostalCode");
            System.out.println("5.- Tester isNumeric");
            System.out.println("6.- Tester isAlphabetic");
            System.out.println("7.- Tester checkEmail");
            System.out.println("8.- Tester checkName");
            System.out.println("Z.- Salir");

            System.out.print("Option: ");
            opcion = sc.next();

            switch (opcion) {
                case "1":
                    testCheckID();
                    break;
                case "2":
                    testCheckFormatDate();
                    break;
                case "3":
                    testCalculateAge();
                    break;
                case "4":
                    testCheckPostalCode();
                    break;
                case "5":
                    testIsNumeric();
                    break;
                case "6":
                    testIsAlphabetic();
                    break;
                case "7":
                    testCheckEmail();
                    break;
                case "8":
                    testCheckName();
                    break;
                case "Z":
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Incorrect option");
            }
        } while (!opcion.equals("Z")); 

    }

    static void testCheckID() {
        System.out.println("NIF Validator");
        System.out.print("Introduce tu id: ");
        String id = sc.next().toUpperCase();
        boolean idOk = UserDataValidations.checkId(1, id);
        if (idOk) {
            System.out.println("Correct Id");
        } else {
            System.out.println("Incorrect Id");
        }
    }

    static void testCheckFormatDate() {
        System.out.println("Date Validator");
        System.out.println("Introduzca una fecha DD/MM/AAAA");
        String date = sc.next();
        boolean dateOk = UserDataValidations.checkFormatDate(date);
        if (dateOk) {
            System.out.println("La fecha es correcta");
        } else {
            System.out.println("La fecha es incorrecta");
        }
    }

    static void testCalculateAge() {
        System.out.println("Age calculator");
        System.out.println("Introduce tu fecha de nacimiento DD/MM/AAAA ");
        String birthDate = sc.next();
        int age = UserDataValidations.calculateAge(birthDate);
        if (age == -1) {
            System.out.println("La fecha de nacimiento introducida no es valida.");
        } else {
            System.out.println("Tu edad es: " + age);
        }
    }

    static void testCheckPostalCode() {
        System.out.println("Introduce tu codigo postal");
        String zip = sc.next();
        boolean zipOk = UserDataValidations.checkPostalCode(zip);
        if (zipOk) {
            System.out.println("El codigo postal es bueno");

        } else {
            System.out.println("El codigo postal introducido no es valido");
        }
    }

    static void testIsNumeric() {
        System.out.println("Introduce numeros");
        String str = sc.next();
        boolean numericOk = UserDataValidations.isNumeric(str);
        if (numericOk) {
            System.out.println("Todo son numeros");
        } else {
            System.out.println("Hay algun elemento que no es un numero");
        }
    }

    static void testIsAlphabetic() {
        System.out.println("Introduce letras");
        String str = sc.next();
        boolean alphabeticOk = UserDataValidations.isAlphabetic(str);
        if (alphabeticOk) {
            System.out.println("Todo son letras");
        } else {
            System.out.println("Hay algun elemento que no es una letra");
        }
    }

    static void testCheckEmail() {
        System.out.println("Introduce tu email");
        String email = sc.next();
        boolean emailOk = UserDataValidations.checkEmail(email);
        if (emailOk) {
            System.out.println(" El email es aceptado");
        } else {
            System.out.println("Algo de tu email es incorrecto ");
        }
    }

    static void testCheckName() {
        System.out.println("Introduce tu nombre");
        String name = sc.next();
        boolean nameOk = UserDataValidations.checkName(name);
        if (nameOk) {
            System.out.println(" El nombre es correcto");
        } else {    
            System.out.println("Algo de tu nombre no es correcto ");
        }
    }
}
