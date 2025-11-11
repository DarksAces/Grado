int verde = 2;
int rojo = 3;
int azul = 4;
void setup()
{
  pinMode(0, INPUT);
  Serial.begin(9600);
}

void loop()
{
  if (analogRead(0) >= 700){
    digitalWrite(verde, HIGH);
    digitalWrite(rojo, LOW);
    digitalWrite(azul, LOW);
  }else{
    if (analogRead(0) < 700 && analogRead(0) >= 400){
      digitalWrite(verde, LOW);
      digitalWrite(rojo, HIGH);
      digitalWrite(azul, LOW);
    }else{
      if (analogRead(0) < 400){
        digitalWrite(verde, LOW);
        digitalWrite(rojo, LOW);
        digitalWrite(azul, HIGH);
      }
    }
  }
}