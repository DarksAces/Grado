int red = 8;
int blue = 9;
int green = 10;
void setup() {
  pinMode(red, OUTPUT);
  pinMode(green, OUTPUT);
  pinMode(blue, OUTPUT);

}

void loop() {
  digitalWrite(blue, LOW);//apagar azul
  digitalWrite(red, LOW);//encender rojo
  delay(1000);
  digitalWrite(red, HIGH);//apagar rojo
  digitalWrite(green, HIGH);//encender verde
  delay(1000);
  digitalWrite(green, LOW);//apagar verde
  digitalWrite(blue, HIGH);//encender verde
  delay(2000);

}
