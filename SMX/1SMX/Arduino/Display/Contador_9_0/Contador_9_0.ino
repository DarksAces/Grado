int a = 2;
int b = 3;
int c = 6;
int d = 7;
int e = 8;
int f = 4;
int g = 5;

void setup() {
  pinMode(a, OUTPUT);
  pinMode(b, OUTPUT);
  pinMode(c, OUTPUT);
  pinMode(d, OUTPUT);
  pinMode(e, OUTPUT);
  pinMode(f, OUTPUT);
  pinMode(g, OUTPUT);
}

void loop() {
  digitalWrite(a, LOW);//9
  digitalWrite(b, LOW); 
  digitalWrite(c, LOW); 
  digitalWrite(d, HIGH); 
  digitalWrite(e, HIGH); 
  digitalWrite(f, LOW); 
  digitalWrite(g, LOW); 
  delay(500);
  digitalWrite(a, LOW);//8
  digitalWrite(b, LOW); 
  digitalWrite(c, LOW); 
  digitalWrite(d, LOW); 
  digitalWrite(e, LOW); 
  digitalWrite(f, LOW); 
  digitalWrite(g, LOW);
  delay(500);  
  digitalWrite(a, LOW);//7
  digitalWrite(b, LOW); 
  digitalWrite(c, LOW); 
  digitalWrite(d, HIGH); 
  digitalWrite(e, HIGH); 
  digitalWrite(f, HIGH); 
  digitalWrite(g, HIGH);
  delay(500);  
  digitalWrite(a, LOW);//6
  digitalWrite(b, HIGH); 
  digitalWrite(c, LOW); 
  digitalWrite(d, LOW); 
  digitalWrite(e, LOW); 
  digitalWrite(f, LOW); 
  digitalWrite(g, LOW);
  delay(500);  
  digitalWrite(a, LOW);//5
  digitalWrite(b, HIGH); 
  digitalWrite(c, LOW); 
  digitalWrite(d, LOW); 
  digitalWrite(e, HIGH); 
  digitalWrite(f, LOW); 
  digitalWrite(g, LOW);
  delay(500); 
  digitalWrite(a, HIGH);//4
  digitalWrite(b, LOW); 
  digitalWrite(c, LOW); 
  digitalWrite(d, HIGH); 
  digitalWrite(e, HIGH); 
  digitalWrite(f, LOW); 
  digitalWrite(g, LOW);
  delay(500);  
  digitalWrite(a, LOW);//3
  digitalWrite(b, LOW); 
  digitalWrite(c, LOW); 
  digitalWrite(d, LOW); 
  digitalWrite(e, HIGH); 
  digitalWrite(f, HIGH); 
  digitalWrite(g, LOW);
  delay(500);  
  digitalWrite(a, LOW);//2
  digitalWrite(b, LOW); 
  digitalWrite(c, HIGH); 
  digitalWrite(d, LOW); 
  digitalWrite(e, LOW); 
  digitalWrite(f, HIGH); 
  digitalWrite(g, LOW);
  delay(500);  
  digitalWrite(a, HIGH);//1
  digitalWrite(b, LOW); 
  digitalWrite(c, LOW); 
  digitalWrite(d, HIGH); 
  digitalWrite(e, HIGH); 
  digitalWrite(f, HIGH); 
  digitalWrite(g, HIGH);
  delay(500);  
  digitalWrite(a, LOW);//0
  digitalWrite(b, LOW); 
  digitalWrite(c, LOW); 
  digitalWrite(d, LOW); 
  digitalWrite(e, LOW); 
  digitalWrite(f, LOW); 
  digitalWrite(g, HIGH);
  delay(500);  
}
