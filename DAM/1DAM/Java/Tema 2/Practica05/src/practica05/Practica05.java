/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practica05;

import java.util.Scanner;

/**
 *
 * @author danielgarbru
 */
public class Practica05 {

    /**
     * @param args the command line arguments
     */
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        sc.useDelimiter("\n");

        System.out.print("Please select an exercise (1-5): ");
        String opcion = sc.next();

        switch (opcion) {
            case "1":
                ejercicio1();
                break;
            case "2":
                ejercicio2();
                break;
            case "3":
                ejercicio3();
                break;
            case "4":
                ejercicio4();
                break;
            case "5":
                ejercicio5();
                break;

            default:
                System.out.println("Opcion Incorrecta");
        }

    }//Fin Main

    static void ejercicio1() {
        System.out.print("Enter a day of the week: ");
        String dia = sc.next();

        switch (dia) {
            case "Monday","MONDAY","monday":
                System.out.print("The first subject of the day is PROG.");
                break;
            case "Tuesday":
            case "tuesday":
            case "TUESDAY":
                System.out.print("The first subject of the day is ENT.");
                break;
            case "Wednesday":
            case "wednesday":
            case "WEDNSDAY":
                System.out.print("The first subject of the day is HTML.");
                break;
            case "Thursday":
            case "thursday":
            case "THURSDAY":
                System.out.print("The first subject of the day is SIST.");
                break;
            case "Friday":
            case "FRIDAY":
            case "friday":

                System.out.print("The first subject of the day is BBDD.");
                break;
            default:
                System.out.print("El valor introducido es erroneo");
                break;

        }
    }

    static void ejercicio2() {
        System.out.print("Enter the hour: ");
        int hora = sc.nextInt();

        if ((hora >= 6) && (hora <= 12)) {
            System.out.print("Good morning");
        } else if ((hora <= 20) && (hora > 12)) {
            System.out.print("Good afternoon");
        } else if ((hora <= 24) && (hora > 20)) {
            System.out.print("Good evening");
        } else if ((hora <= 5) && (hora > 0)) {
            System.out.print("Good evening");
        } else {
            System.out.print("El valor introducido es erroneo");
        }
    }

    static void ejercicio3() {
        System.out.print("Enter a number (1-7): ");
        String dia = sc.next();

        switch (dia) {
            case "1":
                System.out.print("The corresponding day is Monday.");
                break;
            case "2":
                System.out.print("The corresponding day is Tuesday.");
                break;
            case "3":
                System.out.print("The corresponding day is Wednesday.");
                break;
            case "4":
                System.out.print("The corresponding day is Thursday.");
                break;
            case "5":
                System.out.print("The corresponding day is Friday.");
                break;
            case "6":
                System.out.print("The corresponding day is Saturday.");
                break;
            case "7":
                System.out.print("The corresponding day is Sunday.");
                break;
            default:
                System.out.print("El valor introducido es erroneo");
                break;
        }

    }

    static void ejercicio4() {

        final int TARIFA_NORMAL = 10;
        final int TARIFA_EXTRA = 12;

        System.out.print("Enter the number of hours worked: ");
        int horas = sc.nextInt();

        if (horas > 0 && horas <= 40) {
            System.out.print("The total weekly wage is " + (horas * TARIFA_NORMAL) + ".");
        } else if (horas > 40) {
            int horasExtra = horas - 40;
            int sueldoTotal = (40 * TARIFA_NORMAL) + (horasExtra * TARIFA_EXTRA);
            System.out.print("The total weekly wage is " + sueldoTotal + ".");
        }
    }

    static void ejercicio5() {

        System.out.print("Enter the day of birth: ");
        int Dia = sc.nextInt();

        System.out.print("Enter the month of birth: ");
        String Mes = sc.next();

        String horoscope = "";

        if ((Mes.equals("March") && Dia >= 21) || (Mes.equals("April") && Dia <= 19)) {
            horoscope = "Aries";
        } else if ((Mes.equals("April") && Dia >= 20) || (Mes.equals("May") && Dia <= 20)) {
            horoscope = "Taurus";
        } else if ((Mes.equals("May") && Dia >= 21) || (Mes.equals("June") && Dia <= 20)) {
            horoscope = "Gemini";
        } else if ((Mes.equals("June") && Dia >= 21) || (Mes.equals("July") && Dia <= 22)) {
            horoscope = "Cancer";
        } else if ((Mes.equals("July") && Dia >= 23) || (Mes.equals("August") && Dia <= 22)) {
            horoscope = "Leo";
        } else if ((Mes.equals("August") && Dia >= 23) || (Mes.equals("September") && Dia <= 22)) {
            horoscope = "Virgo";
        } else if ((Mes.equals("September") && Dia >= 23) || (Mes.equals("October") && Dia <= 22)) {
            horoscope = "Libra";
        } else if ((Mes.equals("October") && Dia >= 23) || (Mes.equals("November") && Dia <= 21)) {
            horoscope = "Scorpio";
        } else if ((Mes.equals("November") && Dia >= 22) || (Mes.equals("December") && Dia <= 21)) {
            horoscope = "Sagittarius";
        } else if ((Mes.equals("December") && Dia >= 22) || (Mes.equals("January") && Dia <= 19)) {
            horoscope = "Capricorn";
        } else if ((Mes.equals("January") && Dia >= 20) || (Mes.equals("February") && Dia <= 18)) {
            horoscope = "Aquarius";
        } else if ((Mes.equals("February") && Dia >= 19) || (Mes.equals("March") && Dia <= 20)) {
            horoscope = "Pisces";
        } else {
            horoscope = "Invalid date";
        }

        System.out.print("Your horoscope is " + horoscope + ".");
    }
}
