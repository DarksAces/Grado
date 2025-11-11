int Status = 0;

void setup()
{
  pinMode(2, INPUT);
  pinMode(3, OUTPUT);

  Status = 0;
}

void loop()
{
  if (digitalRead(2) == HIGH) {
    if (Status == 0) {
      Status = 1;
    } else {
      Status = 0;
    }
  }
  if (Status == 1) {
    digitalWrite(3, HIGH);
  } else {
    digitalWrite(3, LOW);
  }
  delay(200); // Wait for 100 millisecond(s)
}