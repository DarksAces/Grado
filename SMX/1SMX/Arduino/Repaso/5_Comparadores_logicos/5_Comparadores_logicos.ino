int a = 1;
int b = 1;
void setup() {
  Serial.begin(9600);

}

void loop() {
  if (a != b){
    Serial.println("Prueba finalizada con exito (!=)");
  }
  if (a < b){
    Serial.println("Prueba finalizada con exito (<)");
  }
  if (a <= b){
    Serial.println("Prueba finalizada con exito (<=)");
  }
  if (a == b){
    Serial.println("Prueba finalizada con exito (==)");
  }
  if (a > b){
    Serial.println("Prueba finalizada con exito (>)");
  }
  if (a >= b){
    Serial.println("Prueba finalizada con exito (>=)");
  }
}
