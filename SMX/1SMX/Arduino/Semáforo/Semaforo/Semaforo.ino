int rojo = 0;

int naranja = 0;

int verde = 0;

void setup()
{
  pinMode(7, OUTPUT);
  pinMode(2, INPUT);
  pinMode(6, OUTPUT);
  pinMode(5, OUTPUT);

  rojo = 1;
  naranja = 0;
  verde = 0;
  digitalWrite(7, HIGH);
}

void loop()
{
  if (digitalRead(2) == HIGH && rojo == 1) {
    delay(1000); // Wait for 1000 millisecond(s)
    rojo = 0;
    naranja = 1;
    digitalWrite(6, HIGH);
    digitalWrite(7, LOW);
  } else {
    if (digitalRead(2) == HIGH && naranja == 1) {
      delay(1000); // Wait for 1000 millisecond(s)
      naranja = 0;
      verde = 1;
      digitalWrite(5, HIGH);
      digitalWrite(6, LOW);
    } else {
      if (digitalRead(2) == HIGH && verde == 1) {
        delay(1000); // Wait for 1000 millisecond(s)
        verde = 0;
        rojo = 1;
        digitalWrite(7, HIGH);
        digitalWrite(5, LOW);
      } else {
      }
    }
  }
}