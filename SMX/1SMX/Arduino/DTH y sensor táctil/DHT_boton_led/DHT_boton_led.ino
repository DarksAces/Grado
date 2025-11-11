#include <DHT.h>
#define DHTTYPE DHT11

DHT dht(13, DHTTYPE);
int bot = 12;
int led = 11;

void setup() {
  pinMode(bot, INPUT);
  pinMode(led, OUTPUT);
  pinMode(13, INPUT);
  Serial.begin(9600);
  dht.begin();
}

void loop() {
  float h = dht.readHumidity();
  float t = dht.readTemperature();
  float f = dht.readTemperature(true);

  if (digitalRead(bot) == HIGH){
    digitalWrite(led, HIGH);
    Serial.println("Temperatura:");
    Serial.println(t);
    delay(2000);
  }else{
    digitalWrite(led, LOW);
  }
}
