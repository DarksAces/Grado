#include <LiquidCrystal.h>
LiquidCrystal lcd(12, 11, 5, 4, 3, 2);
int numeroAleatorio;
int numeroEnviado;




void setup() {

  // Inicializa la comunicación serial

  Serial.begin(9600);

  // Inicializa la pantalla LCD

  lcd.begin(16, 2);

 

  // Genera un número aleatorio entre 1 y 100

  randomSeed(analogRead(A0));

  numeroAleatorio = random(1, 101);

 

  // Muestra el mensaje de bienvenida en la pantalla LCD

  lcd.print("Adivina el numero");

  lcd.setCursor(0, 1);

  lcd.print("entre 1 y 100");

  delay(2000);

  lcd.clear();

}




void loop() {

  // Espera a que se envíe un número desde el jugador a través del monitor serial

  if (Serial.available() > 0) {

    // Lee el número enviado

    numeroEnviado = Serial.parseInt();

   

    // Verifica si el número enviado es menor, mayor o igual al número aleatorio

    if (numeroEnviado < numeroAleatorio) {

      lcd.clear();

      lcd.print("El numero enviado");

      lcd.setCursor(0, 1);

      lcd.print("es inferior");

    } else if (numeroEnviado > numeroAleatorio) {

      lcd.clear();

      lcd.print("El numero enviado");

      lcd.setCursor(0, 1);

      lcd.print("es superior");

    }else{

      lcd.clear();

      lcd.print("¡Felicitaciones!");

      lcd.setCursor(0, 1);

      lcd.print("Lo adivinaste");

      delay(200000);

      lcd.clear();

     

      // Genera un nuevo número aleatorio para el siguiente juego

      numeroAleatorio = random(1, 101);
    }
  }
}