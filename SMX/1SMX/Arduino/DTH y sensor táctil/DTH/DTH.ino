#include <DHT.h>
#define DHTTYPE DHT11

DHT dht(2, DHTTYPE);

void setup() {
  pinMode(13, INPUT);
  Serial.begin(9600);
  dht.begin();
}

void loop() {
  float h = dht.readHumidity();
  float t = dht.readTemperature();
  float f = dht.readTemperature(true);
  Serial.println("Temperatura:");
  Serial.println(t);
  Serial.println("Humedad:");
  Serial.println(h);
  delay(2000);
}
