#include <IRremote.h>
int a = 6;
int b = 7;
int c = 4;
int d = 9;
int e = 8;
int f = 2;
int g = 3;
int valor = 0;
int IRpin = 13;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  IrReceiver.begin(IRpin);
  pinMode(a, OUTPUT);
  pinMode(b, OUTPUT);
  pinMode(c, OUTPUT);
  pinMode(d, OUTPUT);
  pinMode(e, OUTPUT);
  pinMode(f, OUTPUT);
  pinMode(g, OUTPUT);
}

void loop() {
  while (IrReceiver.decode() == 0) {

  }
  IrReceiver.resume();
  if (IrReceiver.decodedIRData.command == 7){
    valor = (valor - 1);
  }
  if (IrReceiver.decodedIRData.command == 21){
    valor = (valor + 1);
  }
  if (IrReceiver.decodedIRData.command == 22 || valor == 0){
    Serial.println("0");
    valor = 0;
    digitalWrite(a, LOW);//0
    digitalWrite(b, LOW); 
    digitalWrite(c, LOW); 
    digitalWrite(d, LOW); 
    digitalWrite(e, LOW); 
    digitalWrite(f, LOW); 
    digitalWrite(g, HIGH);
  }
  if (IrReceiver.decodedIRData.command == 12 || valor == 1){
    Serial.println("1");
    valor = 1;
    digitalWrite(a, HIGH);//1
    digitalWrite(b, LOW); 
    digitalWrite(c, LOW); 
    digitalWrite(d, HIGH); 
    digitalWrite(e, HIGH); 
    digitalWrite(f, HIGH); 
    digitalWrite(g, HIGH);
    }
  if (IrReceiver.decodedIRData.command == 24 || valor == 2){
    Serial.println("2");
    valor = 2;
    digitalWrite(a, LOW);//2
    digitalWrite(b, LOW); 
    digitalWrite(c, HIGH); 
    digitalWrite(d, LOW); 
    digitalWrite(e, LOW); 
    digitalWrite(f, HIGH); 
    digitalWrite(g, LOW);
  }
  if (IrReceiver.decodedIRData.command == 94 || valor == 3){
    Serial.println("3");
    valor = 3;
    digitalWrite(a, LOW);//3
    digitalWrite(b, LOW); 
    digitalWrite(c, LOW); 
    digitalWrite(d, LOW); 
    digitalWrite(e, HIGH); 
    digitalWrite(f, HIGH); 
    digitalWrite(g, LOW);
  }
  if (IrReceiver.decodedIRData.command == 8 || valor == 4){
    Serial.println("4");
    valor = 4;
    digitalWrite(a, HIGH);//4
    digitalWrite(b, LOW); 
    digitalWrite(c, LOW); 
    digitalWrite(d, HIGH); 
    digitalWrite(e, HIGH); 
    digitalWrite(f, LOW); 
    digitalWrite(g, LOW);
  }
  if (IrReceiver.decodedIRData.command == 28 || valor == 5){
    Serial.println("5");
    valor = 5;
    digitalWrite(a, LOW);//5
    digitalWrite(b, HIGH); 
    digitalWrite(c, LOW); 
    digitalWrite(d, LOW); 
    digitalWrite(e, HIGH); 
    digitalWrite(f, LOW); 
    digitalWrite(g, LOW);
  }
  if (IrReceiver.decodedIRData.command == 90 || valor == 6){
    Serial.println("6");
    valor = 6;
    digitalWrite(a, LOW);//6
    digitalWrite(b, HIGH); 
    digitalWrite(c, LOW); 
    digitalWrite(d, LOW); 
    digitalWrite(e, LOW); 
    digitalWrite(f, LOW); 
    digitalWrite(g, LOW);
}
  if (IrReceiver.decodedIRData.command == 66 || valor == 7){
    Serial.println("7");
    valor = 7;
    digitalWrite(a, LOW);//7
    digitalWrite(b, LOW); 
    digitalWrite(c, LOW); 
    digitalWrite(d, HIGH); 
    digitalWrite(e, HIGH); 
    digitalWrite(f, HIGH); 
    digitalWrite(g, HIGH);
  }
  if (IrReceiver.decodedIRData.command == 82 || valor == 8){
    Serial.println("8");
    valor = 8;
    digitalWrite(a, LOW);
    digitalWrite(b, LOW); 
    digitalWrite(c, LOW); 
    digitalWrite(d, LOW); 
    digitalWrite(e, LOW); 
    digitalWrite(f, LOW); 
    digitalWrite(g, LOW);
  }
  if (IrReceiver.decodedIRData.command == 74 || valor == 9){
    Serial.println("9");
    valor = 9;
    digitalWrite(a, LOW);
    digitalWrite(b, LOW); 
    digitalWrite(c, LOW); 
    digitalWrite(d, HIGH); 
    digitalWrite(e, HIGH); 
    digitalWrite(f, LOW); 
    digitalWrite(g, LOW); 
  }
}