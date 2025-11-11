void setup() {
  pinMode(9, OUTPUT);//led en el pin 9

}

void loop() {
  digitalWrite(9, HIGH);//encender LED
  delay(1000);//esperar 2s
  digitalWrite(9, LOW);//apagar LED
  delay(1000);//esperar 2s
}
