package com.mycompany.mavenprojectjunit.model.validations;

import java.util.Scanner;
// Ejerciocio 3 imports
import java.time.LocalDate;

/**
 * Validaciones de datos del usuario
 *
 * @author danielgarbru
 */
public class UserDataValidations {

    static Scanner sc = new Scanner(System.in);

    public static boolean checkId(int typeDoc, String id) {
        boolean idOk = false;

        // Verificar longitud del ID
        if (id.length() != 9) {
            System.out.println("");
            return idOk;
        }

        // Extraer el último carácter y los primeros ocho caracteres
        char lastChar = id.charAt(id.length() - 1);
        String substring = id.substring(0, 8);

        // Verificar si el último carácter es una letra
        if (!Character.isLetter(lastChar)) {
            System.out.println("");
            return idOk;
        }

        // Verificar si los primeros ocho caracteres son numéricos
        if (!isNumeric(substring)) {
            System.out.println("");
            return idOk;
        }

        int comprobar = Integer.parseInt(substring);
        int comprobarletra = comprobar % 23;

        // La cadena de letras según el índice de DNI
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        char expectedChar = letras.charAt(comprobarletra);

        // Comparar la letra calculada con la letra del ID
        if (lastChar == expectedChar) {
            idOk = true;
            System.out.println("");
        } else {
            System.out.println("");
        }

        return idOk;
    }

    // Método para verificar si una cadena es numérica

    public static boolean CalculateAge(String date) {
    boolean dateOk = false;

  

    // Comprobamos que la longitud sea 10 caracteres (en formato dd/mm/yyyy)
    if (date.length() != 10) {
        System.out.println("La longitud de la fecha es incorrecta.");
        return false;
    
    } else{
       String[] separado = date.split("/"); 
        if (separado.length != 3) {
        System.out.println("La fecha no tiene el formato adecuado.");
        return dateOk;
    }

    // Validamos que las partes no estén vacías
    for (String part : separado) {
        if (part.trim().isEmpty()) {
            System.out.println("Una de las partes de la fecha está vacía.");
            return dateOk;
        }
    }

    try {
        // Convertimos las partes en enteros
        int day = Integer.parseInt(separado[0].trim());
        int month = Integer.parseInt(separado[1].trim());
        int year = Integer.parseInt(separado[2].trim());

        // Comprobamos que el mes esté en el rango 1-12
        if (month < 1 || month > 12) {
            System.out.println("El mes introducido es incorrecto.");
            return dateOk;
        }

        // Comprobamos que el día sea válido según el mes
        if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
            System.out.println("El mes tiene solo 30 días.");
            return dateOk;
        }

        // Comprobamos febrero
        if (month == 2) {
            if (Bisiesto(year)) {
                if (day > 29) {
                    System.out.println("Febrero solo tiene 29 días en un año bisiesto.");
                    return dateOk;
                }
            } else {
                if (day > 28) {
                    System.out.println("Febrero solo tiene 28 días en un año no bisiesto.");
                    return dateOk;
                }
            }
        }

        // Comprobamos los meses con 31 días
        if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && day > 31) {
            System.out.println("El mes tiene solo 31 días.");
            return dateOk;
        }

        // Si pasamos todas las comprobaciones, la fecha es válida
        dateOk = true;

    } catch (NumberFormatException e) {
        System.out.println("El formato de la fecha es incorrecto. Asegúrese de que todos los valores sean números válidos.");
    }
       
       
    }

    return dateOk;
}

// Método para comprobar si es un año bisiesto
public static boolean Bisiesto(int year) {
    return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
}


    public static int calculateAge(String birthDate) {
        //LoclaDate import para saber la fecha actual
        LocalDate currentDate = LocalDate.now();
        //Le decimos que queremos aislar el año en una variable
        int añoActual = currentDate.getYear();
        //Le decimos que queremos aislar el mes en una variable
        int mesActual = currentDate.getMonthValue();
        //Le decimos que queremos aislar el dia en una variable
        int diaActual = currentDate.getDayOfMonth();
        //Separamos los datos del usuario
        String[] separadoNacimiento = birthDate.split("/");
        int añoNacimiento = Integer.parseInt(separadoNacimiento[2]);
        int mesNacimiento = Integer.parseInt(separadoNacimiento[1]);
        int diaNacimiento = Integer.parseInt(separadoNacimiento[0]);

        if (!CalculateAge(birthDate)) {
            System.out.println("Introduce datos validos");
            return -1;
        }

        if ((añoNacimiento < 1908) || (añoNacimiento > añoActual)) {
            System.out.println("Introduce datos validos");
            return -1;

            //Comprobamos possibilidades bisiesto
        } else if (separadoNacimiento[1].equals("2")) {
            if (Bisiesto(mesNacimiento)) {
                if (diaNacimiento > 29) {

                    int edad = añoActual - añoNacimiento;
                    if ((mesActual < mesNacimiento) || (mesActual == mesNacimiento && diaActual < diaNacimiento)) {
                        edad--;
                    }
                    return edad;
                }
            }
            //en caso de no bisiesto
        } else {
            int edad = añoActual - añoNacimiento;
            if ((mesActual < mesNacimiento) || (mesActual == mesNacimiento && diaActual < diaNacimiento)) {
                edad--;
            }
            return edad;
        }
        return -1;

    }

    public static boolean checkPostalCode(String zip) {
        boolean zipOk = false;
        int numero = Integer.parseInt(zip);
        if (zip.length() != 5) {
            System.out.println("El numero debe de ser de 5 digitos");
            return zipOk;
            //revisamos que el numero zip sea de España
        } else if ((numero < 01000) || (numero > 52999)) {
            System.out.println("El Codigo postal no es valido");
        } else if ((numero >= 01000) && (numero <= 1999)) {
            System.out.println("Eres de Pais Vasco, Alava");
            return true;
        } else if ((numero >= 02000) && (numero <= 2999)) {
            System.out.println("Eres de Castilla La Mancha, Albacete");
            return true;
        } else if ((numero >= 03000) && (numero <= 3999)) {
            System.out.println("Eres de la comunidad Valenciana, Alicante");
            return true;
        } else if ((numero >= 04000) && (numero <= 4999)) {
            System.out.println("Eres de Andalucia, Almeria");
            return true;
        } else if ((numero >= 05000) && (numero <= 5999)) {
            System.out.println("Eres de Andalucia, Avila");
            return true;
        } else if ((numero >= 06000) && (numero <= 6999)) {
            System.out.println("Eres de Extremadura, Badajoz");
            return true;
        } else if ((numero >= 07000) && (numero <= 7999)) {
            System.out.println("Eres de las islas baleares");
            return true;
        } else if ((numero >= 8000) && (numero <= 8999)) {
            System.out.println("Eres de Catalunya, Barcelona");
            return true;
        } else if ((numero >= 9000) && (numero <= 9999)) {
            System.out.println("Eres de Castilla y Leon, Burgos");
            return true;
        } else if ((numero >= 10000) && (numero <= 10999)) {
            System.out.println("Eres de Extremadura, Caceres");
            return true;
        } else if ((numero >= 11000) && (numero <= 11999)) {
            System.out.println("Eres de Andalucia, Cadiz");
            return true;
        } else if ((numero >= 12000) && (numero <= 12999)) {
            System.out.println("Eres de la comunidad Valenciana, Castellon");
            return true;
        } else if ((numero >= 13000) && (numero <= 13999)) {
            System.out.println("Eres de Castilla la Mancha, Ciudad Real");
            return true;
        } else if ((numero >= 14000) && (numero <= 14999)) {
            System.out.println("Eres de Andalucia, Cordoba");
            return true;
        } else if ((numero >= 15000) && (numero <= 15999)) {
            System.out.println("Eres de Galicia, La Corunya");
            return true;
        } else if ((numero >= 16000) && (numero <= 16999)) {
            System.out.println("Eres de Castilla la Mancha, Cuenca");
            return true;
        } else if ((numero >= 17000) && (numero <= 17999)) {
            System.out.println("Eres de Catalunya, Girona");
            return true;
        } else if ((numero >= 18000) && (numero <= 18999)) {
            System.out.println("Eres de Andalucia, Granada");
            return true;
        } else if ((numero >= 19000) && (numero <= 19999)) {
            System.out.println("Eres de Castilla la Mancha, Guadalajara");
            return true;
        } else if ((numero >= 20000) && (numero <= 20999)) {
            System.out.println("Eres de Pais Vasco, Gipuzkoa");
            return true;
        } else if ((numero >= 21000) && (numero <= 21999)) {
            System.out.println("Eres de Andalucia, Huelva");
            return true;
        } else if ((numero >= 22000) && (numero <= 22999)) {
            System.out.println("Eres de Aragon, Huesca");
            return true;
        } else if ((numero >= 23000) && (numero <= 23999)) {
            System.out.println("Eres de Andalucia, Jaen");
            return true;
        } else if ((numero >= 24000) && (numero <= 24999)) {
            System.out.println("Eres de Castilla y Leon, Leon");
            return true;
        } else if ((numero >= 25000) && (numero <= 25999)) {
            System.out.println("Eres de Catalunya, Lleida");
            return true;
        } else if ((numero >= 26000) && (numero <= 26999)) {
            System.out.println("Eres de la Rioja");
            return true;
        } else if ((numero >= 27000) && (numero <= 27999)) {
            System.out.println("Eres de Galicia, Lugo");
            return true;
        } else if ((numero >= 28000) && (numero <= 28999)) {
            System.out.println("Eres de Madrid, Comunidad de Madrid");
            return true;
        } else if ((numero >= 29000) && (numero <= 29999)) {
            System.out.println("Eres de Andalucia, Malaga");
            return true;
        } else if ((numero >= 30000) && (numero <= 30999)) {
            System.out.println("Eres de la region de Murcia");
            return true;
        } else if ((numero >= 31000) && (numero <= 31999)) {
            System.out.println("Eres de Navarra");
            return true;
        } else if ((numero >= 32000) && (numero <= 32999)) {
            System.out.println("Eres de Galicia, Ourense");
            return true;
        } else if ((numero >= 33000) && (numero <= 33999)) {
            System.out.println("Eres de Asturias");
            return true;
        } else if ((numero >= 34000) && (numero <= 34999)) {
            System.out.println("Eres de Castilla y Leon, Palencia");
            return true;
        } else if ((numero >= 35000) && (numero <= 35999)) {
            System.out.println("Eres de Canarias, Las Palmas");
            return true;
        } else if ((numero >= 36000) && (numero <= 36999)) {
            System.out.println("Eres de Galicia, Pontevedra");
            return true;
        } else if ((numero >= 37000) && (numero <= 37999)) {
            System.out.println("Eres de Castilla y Leon, Salamanca");
            return true;
        } else if ((numero >= 38000) && (numero <= 38999)) {
            System.out.println("Eres de Canarias, Santa Cruz de Tenerife");
            return true;
        } else if ((numero >= 39000) && (numero <= 39999)) {
            System.out.println("Eres de Cantabria");
            return true;
        } else if ((numero >= 40000) && (numero <= 40999)) {
            System.out.println("Eres de Castilla y Leon, Segovia");
            return true;
        } else if ((numero >= 41000) && (numero <= 41999)) {
            System.out.println("Eres de Andalucia, Sevilla");
            return true;
        } else if ((numero >= 42000) && (numero <= 42999)) {
            System.out.println("Eres de Castilla y Leon, Soria");
            return true;
        } else if ((numero >= 44000) && (numero <= 44999)) {
            System.out.println("Eres de Aragon, Teruel");
            return true;
        } else if ((numero >= 45000) && (numero <= 45999)) {
            System.out.println("Eres de Castilla la Mancha, Toledo");
            return true;
        } else if ((numero >= 46000) && (numero <= 46999)) {
            System.out.println("Eres de la comunidad Valenciana, Valencia");
            return true;
        } else if ((numero >= 47000) && (numero <= 47999)) {
            System.out.println("Eres de Castilla y Leon, Valladolid");
            return true;
        } else if ((numero >= 48000) && (numero <= 48999)) {
            System.out.println("Eres de Pais Vasco, Bizkaia");
            return true;
        } else if ((numero >= 49000) && (numero <= 49999)) {
            System.out.println("Eres de Castilla y Leon, Zamora");
            return true;
        } else if ((numero >= 50000) && (numero <= 50999)) {
            System.out.println("Eres de Aragon, Zaragoza");
            return true;
        } else if ((numero >= 51000) && (numero <= 51999)) {
            System.out.println("Eres de Ceuta");
            return true;
        } else if ((numero >= 52000) && (numero <= 52999)) {
            System.out.println("Eres de Melilla");
            return true;
        }

        return zipOk;   

    }

    public static boolean isNumeric(String str) {
        boolean numericOk = true;
        for (char comprobante : str.toCharArray()) {
            if (!Character.isDigit(comprobante)) {
                numericOk = false;
                break;
            }
        }
        return numericOk;
    }

    public static boolean isAlphabetic(String str) {
        boolean alphabeticOk = true;
        for (char comprobante : str.toCharArray()) {
            if (!Character.isAlphabetic(comprobante)) {
                alphabeticOk = false;
                break;
            }
        }
        return alphabeticOk;
    }

    public static boolean checkEmail(String email) {
        boolean emailOk = false;
        String[] noArroba = email.split("@");
        String[] despuesPunto = email.split("\\.");
        String[] partesArroba = email.split("@");
        if (partesArroba.length != 2 || partesArroba[0].isEmpty() || partesArroba[1].isEmpty()) {

            return emailOk;
        } else if ((noArroba.length == 2) && (despuesPunto.length == 2)) {
            return true;
        } else {

            return emailOk;
        }
    }

    public static boolean checkName(String name) {
        boolean nameOk = false;
        if ((name.length() <= 1) || (name.length() > 20)) {
            return nameOk;

        }

        for (char comprobante : name.toCharArray()) {
            if (!Character.isAlphabetic(comprobante)) {
                return nameOk;

            }
        }

        return true;
    }
    

 
}
