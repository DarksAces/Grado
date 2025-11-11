int integer = 2006;
bool booleano = true;
String texto = "Hello, World!";

void setup() {
  Serial.begin(9600);


}

void loop() {
  Serial.println(integer);
  delay(200);
  Serial.println(booleano);
  delay(200);
  Serial.println(texto);
  delay(200);

}
