/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package testing;

import com.mycompany.mavenprojectjunit.model.validations.UserDataValidations;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author danielgarbru
 */
public class UserDataValidationsJUnitTest {

    public UserDataValidationsJUnitTest() {
    }

//    @org.junit.jupiter.api.BeforeAll
//    public static void setUpClass() throws Exception {
//    }
//
//    @org.junit.jupiter.api.AfterAll
//    public static void tearDownClass() throws Exception {
//    }
//
//    @org.junit.jupiter.api.BeforeEach
//    public void setUp() throws Exception {
//    }
//
//    @org.junit.jupiter.api.AfterEach
//    public void tearDown() throws Exception {
//    }
    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testCheckID() {
// Tipos de documentos y casos de prueba
        int typeDoc = 1; // DNI
        String id = "84471322G";  // Correcto
        String id2 = "G84471322"; // Letra al principio
        String id3 = "784471322"; // Solo números
        String id4 = "GGGGGGGGG"; // Solo letras
        String id5 = "84471322g"; // Letra minúscula
        String id6 = "8";         // Menos caracteres
        String id7 = "844713222G"; // Más caracteres
        String id8 = "84471@22G"; // Caracteres especiales
        String id9 = " 84471322G"; // Espacios en blanco al inicio
        String id10 = "84471322 "; // Espacios en blanco al final
        String id11 = "04471322G"; // DNI con cero inicial
        String id12 = "-84471322G"; // DNI con signo negativo
        String id13 = "84471322Z"; // Formato correcto pero letra inválida 

        // Resultados esperados
        boolean expResult = true;  // Formato válido
        boolean expResult2 = false; // Formato inválido

        // Validaciones
        boolean idok = UserDataValidations.checkId(typeDoc, id);
        boolean LetraPrincipio = UserDataValidations.checkId(typeDoc, id2);
        boolean SoloNum = UserDataValidations.checkId(typeDoc, id3);
        boolean SoloLetra = UserDataValidations.checkId(typeDoc, id4);
        boolean LetraMinuscula = UserDataValidations.checkId(typeDoc, id5);
        boolean MenosCaracteres = UserDataValidations.checkId(typeDoc, id6);
        boolean MasCaracteres = UserDataValidations.checkId(typeDoc, id7);
        boolean CaracterEspecial = UserDataValidations.checkId(typeDoc, id8);
        boolean EspaciosInicio = UserDataValidations.checkId(typeDoc, id9);
        boolean EspaciosFinal = UserDataValidations.checkId(typeDoc, id10);
        boolean CeroInicial = UserDataValidations.checkId(typeDoc, id11);
        boolean SignoNegativo = UserDataValidations.checkId(typeDoc, id12);
        boolean LetraInvalida = UserDataValidations.checkId(typeDoc, id13);

        // Asserts
        assertEquals(expResult, idok, "El ID es correcto");
        assertEquals(expResult2, LetraPrincipio, "El ID no puede comenzar con letra");
        assertEquals(expResult2, SoloNum, "El ID es incorrecto, debe tener una letra al final");
        assertEquals(expResult2, SoloLetra, "El ID es incorrecto, debe contener 8 números");
        assertEquals(expResult2, LetraMinuscula, "El ID es incorrecto, la letra debe ser mayúscula");
        assertEquals(expResult2, MenosCaracteres, "El ID es incorrecto, tiene menos caracteres de los esperados");
        assertEquals(expResult2, MasCaracteres, "El ID es incorrecto, supera la longitud permitida");
        assertEquals(expResult2, CaracterEspecial, "El ID es incorrecto, no debe contener caracteres especiales");
        assertEquals(expResult2, EspaciosInicio, "El ID es incorrecto, no debe contener espacios en blanco al inicio");
        assertEquals(expResult2, EspaciosFinal, "El ID es incorrecto, no debe contener espacios en blanco al final");
        assertEquals(expResult2, CeroInicial, "El ID es correcto, los ceros iniciales son válidos");
        assertEquals(expResult2, SignoNegativo, "El ID es incorrecto, no debe tener signo negativo");
        assertEquals(expResult2, LetraInvalida, "El ID es incorrecto, la letra final no es válida");
    }

    @Test
    public void checkFormatDate() {
        // Fechas de prueba
        String date = "11/1/22";       // Formato incorrecto (año incompleto)
        String date2 = "11/01/2022";  // Fecha correcta
        String date3 = "29/02/2003";  // Año no bisiesto
        String date4 = "29/02/2004";  // Año bisiesto
        String date5 = "0/00/2000";   // Día y mes inválidos
        String date6 = "32/12/2025";  // Día inválido
        String date7 = "31/11/2025";  // Día inválido (noviembre no tiene 31 días)
        String date8 = "12/13/2022";  // Mes fuera de rango
        String date9 = "12/12/22";    // Año incompleto
        String date10 = "12-12-2022"; // Separador incorrecto
        String date11 = "abcd/ef/gh"; // Caracteres no válidos
        String date12 = "29/02/1900"; // Año no bisiesto (divisible por 100 pero no por 400)
        String date13 = "29/02/2000"; // Año bisiesto (divisible por 400)
        String date14 = "12/2022";        // Faltan día
        String date15 = "12/";           // Faltan mes y año
        String date16 = "31/02/2024";    // Día fuera de rango en febrero bisiesto
        String date17 = "00/01/2022";    // Día igual a 0
        String date18 = "01/00/2022";    // Mes igual a 0
        String date19 = "31/04/2022";    // Día fuera de rango (abril tiene 30 días)
        String date20 = "01/01/1916";    // Año menor al permitido
        String date21 = "31/12/2030";    // Año mayor al permitido
        String date22 = " ";             // Cadena vacía
        String date23 = "12 /12/2022";   // Espacios intermedios
        String date24 = "12-12-2022";    // Separador incorrecto
        String date25 = "12.12.2022";    // Separador incorrecto

        boolean expResult = true;  // Fecha válida
        boolean expResult2 = false; // Fecha inválida

        // Validaciones
        boolean malFormato = UserDataValidations.CalculateAge(date);
        boolean dateok = UserDataValidations.CalculateAge(date2);
        boolean noBisiesto = UserDataValidations.CalculateAge(date3);
        boolean bisiesto = UserDataValidations.CalculateAge(date4);
        boolean dia0 = UserDataValidations.CalculateAge(date5);
        boolean diaMayor = UserDataValidations.CalculateAge(date6);
        boolean dia31Mal = UserDataValidations.CalculateAge(date7);
        boolean mesMayor = UserDataValidations.CalculateAge(date8);
        boolean anioIncompleto = UserDataValidations.CalculateAge(date9);
        boolean separadorIncorrecto = UserDataValidations.CalculateAge(date10);
        boolean caracteresInvalidos = UserDataValidations.CalculateAge(date11);
        boolean noBisiesto1900 = UserDataValidations.CalculateAge(date12);
        boolean bisiesto2000 = UserDataValidations.CalculateAge(date13);
        boolean faltanDia = UserDataValidations.CalculateAge(date14);
        boolean faltanMesAnio = UserDataValidations.CalculateAge(date15);
        boolean diaFueraFebreroBisiesto = UserDataValidations.CalculateAge(date16);
        boolean diaFueraRangoAbril = UserDataValidations.CalculateAge(date17);
        boolean anioMenorPermitido = UserDataValidations.CalculateAge(date18);
        boolean anioMayorPermitido = UserDataValidations.CalculateAge(date19);
        boolean espacioInicio = UserDataValidations.CalculateAge(date20);
        boolean espacioFinal = UserDataValidations.CalculateAge(date21);
        boolean espaciosIntermedios = UserDataValidations.CalculateAge(date22);
        boolean noBisiesto2100 = UserDataValidations.CalculateAge(date23);
        boolean Separador_incorrecto1 = UserDataValidations.CalculateAge(date24);
        boolean Separador_incorrecto2 = UserDataValidations.CalculateAge(date25);

        assertEquals(expResult2, malFormato, "El formato de la fecha es incorrecto: DD/MM/YYYY esperado");
        assertEquals(expResult, dateok, "La fecha es correcta");
        assertEquals(expResult2, noBisiesto, "Este año no es bisiesto");
        assertEquals(expResult, bisiesto, "La fecha es correcta");
        assertEquals(expResult2, dia0, "La fecha no puede ser 0");
        assertEquals(expResult2, diaMayor, "La fecha no puede ser la introducida");
        assertEquals(expResult2, dia31Mal, "La fecha no puede ser la introducida");
        assertEquals(expResult2, mesMayor, "El mes no puede ser mayor a 12");
        assertEquals(expResult2, anioIncompleto, "El año debe tener 4 dígitos");
        assertEquals(expResult2, separadorIncorrecto, "El separador debe ser '/'");
        assertEquals(expResult2, caracteresInvalidos, "La fecha contiene caracteres no válidos");
        assertEquals(expResult2, noBisiesto1900, "El año 1900 no es bisiesto");
        assertEquals(expResult, bisiesto2000, "El año 2000 es bisiesto");
        assertEquals(expResult2, faltanDia, "Faltan el día");
        assertEquals(expResult2, faltanMesAnio, "Faltan mes y año");
        assertEquals(expResult2, diaFueraFebreroBisiesto, "Día fuera de rango en febrero bisiesto");
        assertEquals(expResult, diaFueraRangoAbril, "Día fuera de rango en abril");
        assertEquals(expResult2, anioMenorPermitido, "Año menor al permitido");
        assertEquals(expResult2, anioMayorPermitido, "Año mayor al permitido");
        assertEquals(expResult, espacioInicio, "Espacio al inicio");
        assertEquals(expResult, espacioFinal, "Espacio al final");
        assertEquals(expResult2, espaciosIntermedios, "Espacios intermedios");
        assertEquals(expResult2, noBisiesto2100, "Año no bisiesto divisible por 100 pero no por 400");
        assertEquals(expResult2, Separador_incorrecto1, "Primera fecha válida del año actual");
        assertEquals(expResult2, Separador_incorrecto2, "Última fecha válida del año actual");
    }

    @Test
    public void calculateAge() {
        String date = "11/1/22";       // Formato incorrecto (año incompleto)
        String date2 = "11/01/2022";  // Fecha correcta
        String date3 = "29/02/2003";  // Año no bisiesto
        String date4 = "29/02/2004";  // Año bisiesto
        String date5 = "0/00/2000";   // Día y mes inválidos
        String date6 = "32/12/2025";  // Día inválido
        String date7 = "31/11/2025";  // Día inválido (noviembre no tiene 31 días)
        String date8 = "12/13/2022";  // Mes fuera de rango
        String date9 = "12/12/22";    // Año incompleto
        String date10 = "12-12-2022"; // Separador incorrecto
        String date11 = "abcd/ef/gh"; // Caracteres no válidos
        String date12 = "29/02/1900"; // Año no bisiesto (divisible por 100 pero no por 400)
        String date13 = "29/02/2000"; // Año bisiesto (divisible por 400)
        String date14 = "12/2022";        // Faltan día
        String date15 = "12/";           // Faltan mes y año
        String date16 = "31/02/2024";    // Día fuera de rango en febrero bisiesto
        String date17 = "00/01/2022";    // Día igual a 0
        String date18 = "01/00/2022";    // Mes igual a 0
        String date19 = "31/04/2022";    // Día fuera de rango (abril tiene 30 días)
        String date20 = "01/01/1916";    // Año menor al permitido
        String date21 = "31/12/2030";    // Año mayor al permitido
        String date22 = " ";             // Cadena vacía
        String date23 = "12 /12/2022";   // Espacios intermedios
        String date24 = "12-12-2022";    // Separador incorrecto
        String date25 = "12.12.2022";    // Separador incorrecto

        boolean expResult = true;  // Fecha válida
        boolean expResult2 = false; // Fecha inválida

        // Validaciones
        boolean malFormato = UserDataValidations.CalculateAge(date);
        boolean dateok = UserDataValidations.CalculateAge(date2);
        boolean noBisiesto = UserDataValidations.CalculateAge(date3);
        boolean bisiesto = UserDataValidations.CalculateAge(date4);
        boolean dia0 = UserDataValidations.CalculateAge(date5);
        boolean diaMayor = UserDataValidations.CalculateAge(date6);
        boolean dia31Mal = UserDataValidations.CalculateAge(date7);
        boolean mesMayor = UserDataValidations.CalculateAge(date8);
        boolean anioIncompleto = UserDataValidations.CalculateAge(date9);
        boolean separadorIncorrecto = UserDataValidations.CalculateAge(date10);
        boolean caracteresInvalidos = UserDataValidations.CalculateAge(date11);
        boolean noBisiesto1900 = UserDataValidations.CalculateAge(date12);
        boolean bisiesto2000 = UserDataValidations.CalculateAge(date13);
        boolean faltanDia = UserDataValidations.CalculateAge(date14);
        boolean faltanMesAnio = UserDataValidations.CalculateAge(date15);
        boolean diaFueraFebreroBisiesto = UserDataValidations.CalculateAge(date16);
        boolean diaFueraRangoAbril = UserDataValidations.CalculateAge(date17);
        boolean anioMenorPermitido = UserDataValidations.CalculateAge(date18);
        boolean anioMayorPermitido = UserDataValidations.CalculateAge(date19);
        boolean espacioInicio = UserDataValidations.CalculateAge(date20);
        boolean espacioFinal = UserDataValidations.CalculateAge(date21);
        boolean espaciosIntermedios = UserDataValidations.CalculateAge(date22);
        boolean noBisiesto2100 = UserDataValidations.CalculateAge(date23);
        boolean Separador_incorrecto1 = UserDataValidations.CalculateAge(date24);
        boolean Separador_incorrecto2 = UserDataValidations.CalculateAge(date25);

        assertEquals(expResult2, malFormato, "El formato de la fecha es incorrecto: DD/MM/YYYY esperado");
        assertEquals(expResult, dateok, "La fecha es correcta");
        assertEquals(expResult2, noBisiesto, "Este año no es bisiesto");
        assertEquals(expResult, bisiesto, "La fecha es correcta");
        assertEquals(expResult2, dia0, "La fecha no puede ser 0");
        assertEquals(expResult2, diaMayor, "La fecha no puede ser la introducida");
        assertEquals(expResult2, dia31Mal, "La fecha no puede ser la introducida");
        assertEquals(expResult2, mesMayor, "El mes no puede ser mayor a 12");
        assertEquals(expResult2, anioIncompleto, "El año debe tener 4 dígitos");
        assertEquals(expResult2, separadorIncorrecto, "El separador debe ser '/'");
        assertEquals(expResult2, caracteresInvalidos, "La fecha contiene caracteres no válidos");
        assertEquals(expResult2, noBisiesto1900, "El año 1900 no es bisiesto");
        assertEquals(expResult, bisiesto2000, "El año 2000 es bisiesto");
        assertEquals(expResult2, faltanDia, "Faltan el día");
        assertEquals(expResult2, faltanMesAnio, "Faltan mes y año");
        assertEquals(expResult2, diaFueraFebreroBisiesto, "Día fuera de rango en febrero bisiesto");
        assertEquals(expResult, diaFueraRangoAbril, "Día fuera de rango en abril");
        assertEquals(expResult2, anioMenorPermitido, "Año menor al permitido");
        assertEquals(expResult2, anioMayorPermitido, "Año mayor al permitido");
        assertEquals(expResult, espacioInicio, "Espacio al inicio");
        assertEquals(expResult, espacioFinal, "Espacio al final");
        assertEquals(expResult2, espaciosIntermedios, "Espacios intermedios");
        assertEquals(expResult2, noBisiesto2100, "Año no bisiesto divisible por 100 pero no por 400");
        assertEquals(expResult2, Separador_incorrecto1, "Primera fecha válida del año actual");
        assertEquals(expResult2, Separador_incorrecto2, "Última fecha válida del año actual");
    }

    @Test
    public void checkPostalCode() {
        String zip1 = "01000";
        String zip2 = "00100";
        String zip3 = "52999";
        String zip4 = "53000";
        String zip5 = "5300";
        String zip6 = "530088";
        boolean primeroPermitido = UserDataValidations.checkPostalCode(zip1);
        boolean zipInexistenteBajo = UserDataValidations.checkPostalCode(zip2);
        boolean ultimoPermitido = UserDataValidations.checkPostalCode(zip3);
        boolean zipInexistenteAlto = UserDataValidations.checkPostalCode(zip4);
        boolean menosDigitos = UserDataValidations.checkPostalCode(zip5);
        boolean masDigitos = UserDataValidations.checkPostalCode(zip6);

        boolean expResult = true;
        boolean expResult2 = false;

        assertEquals(expResult, primeroPermitido, "Oki");
        assertEquals(expResult2, zipInexistenteBajo, "Zip inexistente");
        assertEquals(expResult, ultimoPermitido, "Oki");
        assertEquals(expResult2, zipInexistenteAlto, "Zip inexistente");
        assertEquals(expResult2, menosDigitos, "Menos digitos de los permitidos");
        assertEquals(expResult2, masDigitos, "Mas digitos de los permitidos");
    }

    @Test
    public void isNumeric() {
        String numeric1 = "01000";
        String numeric2 = "a00100";
        String numeric3 = "52999b";
        String numeric4 = "530c00";
        String numeric5 = "A5300A";
        String numeric6 = "ABAasda";
        String numeric7 = " ";
        String numeric8 = "0,2 ";
        boolean todoNum = UserDataValidations.isNumeric(numeric1);
        boolean letraInicio = UserDataValidations.isNumeric(numeric2);
        boolean letraFin = UserDataValidations.isNumeric(numeric3);
        boolean letraMedio = UserDataValidations.isNumeric(numeric4);
        boolean letraIniFin = UserDataValidations.isNumeric(numeric5);
        boolean soloLetras = UserDataValidations.isNumeric(numeric6);
        boolean nada = UserDataValidations.isNumeric(numeric7);
        boolean decimales = UserDataValidations.isNumeric(numeric8);

        boolean expResult = true;
        boolean expResult2 = false;

        assertEquals(expResult, todoNum, "Oki");
        assertEquals(expResult2, letraInicio, "Contiene letras");
        assertEquals(expResult2, letraFin, "Contiene letras");
        assertEquals(expResult2, letraMedio, "Contiene letras");
        assertEquals(expResult2, letraIniFin, "Contiene letras");
        assertEquals(expResult2, soloLetras, "Contiene letras");
        assertEquals(expResult2, nada, "vacio?");
        assertEquals(expResult2, decimales, "hay un caracter especial");
    }

    @Test
    public void isAlphabetic() {
        String alphabetic = "agua";
        String alphabetic2 = "Agua Bendita";
        String alphabetic3 = " ";
        String alphabetic4 = "H0la";
        String alphabetic5 = "Agua_Bendita";
        String alphabetic6 = "ABAasda";
        boolean todoletras = UserDataValidations.isAlphabetic(alphabetic);
        boolean espacio = UserDataValidations.isAlphabetic(alphabetic2);
        boolean nada = UserDataValidations.isAlphabetic(alphabetic3);
        boolean numero = UserDataValidations.isAlphabetic(alphabetic4);
        boolean caracterEspecial = UserDataValidations.isAlphabetic(alphabetic5);
        boolean random = UserDataValidations.isAlphabetic(alphabetic6);

        boolean expResult = true;
        boolean expResult2 = false;

        assertEquals(expResult, todoletras, "Oki");
        assertEquals(expResult2, espacio, "pon algo");
        assertEquals(expResult2, nada, "tiene numeros");
        assertEquals(expResult2, numero, "Contiene numeros");
        assertEquals(expResult2, caracterEspecial, "Contiene algun caracter especial");
        assertEquals(expResult, random, "oki");
    }

}
