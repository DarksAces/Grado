#include <DHT.h>
#include <DHT_U.h>
#include <LiquidCrystal.h>
#define DHTTYPE DHT11
int boton = 6;
int botstatus = 1;
LiquidCrystal lcd(12, 11, 5, 4, 3, 2);


DHT dht(13, DHTTYPE);

void setup() {
  pinMode(13, INPUT);
  Serial.begin(9600);
  dht.begin();
  lcd.begin(16,2);
  pinMode(7, OUTPUT);


}

void loop() {
  if (digitalRead(boton) == HIGH) {
    botstatus = botstatus + 1;
    delay(200);
  }
  if (botstatus > 1){
    botstatus = 0;
  }
  if (botstatus == 1){
    digitalWrite(7, HIGH);
  }else{
    digitalWrite(7, LOW);
  }
  Serial.println(botstatus);
  float h = dht.readHumidity();
  float t = dht.readTemperature();
  lcd.setCursor(0, 0);

  lcd.clear();
  lcd.print("Tem: ");
  lcd.print(t);
  lcd.setCursor(0, 1);
  lcd.print("Hum: ");
  lcd.print(h);
  delay(2000);
}

