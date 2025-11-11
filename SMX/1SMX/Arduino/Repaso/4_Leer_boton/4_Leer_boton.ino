int boton = 10;
void setup() {
  pinMode(boton, INPUT);
  Serial.begin(9600);

}

void loop() {
  Serial.println(digitalRead(boton));
}
