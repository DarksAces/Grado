int status = 9;
int a = 2;
int b = 3;
int c = 6;
int d = 7;
int e = 8;
int f = 4;
int g = 5;
int button = 11;

void setup() {
  pinMode(a, OUTPUT);
  pinMode(b, OUTPUT);
  pinMode(c, OUTPUT);
  pinMode(d, OUTPUT);
  pinMode(e, OUTPUT);
  pinMode(f, OUTPUT);
  pinMode(g, OUTPUT);
  pinMode(button, INPUT);
}

void loop() {
  if (status == 9){
    if (digitalRead(11) == HIGH){
      delay (200);
      status = (status - 1);
    }//0
    digitalWrite(a, LOW);
    digitalWrite(b, LOW); 
    digitalWrite(c, LOW); 
    digitalWrite(d, LOW); 
    digitalWrite(e, LOW); 
    digitalWrite(f, LOW); 
    digitalWrite(g, HIGH); 
  }else{
    if (status == 8){
      if (digitalRead(11) == HIGH){
        delay (200);
        status = (status - 1);
      }//1
      digitalWrite(a, HIGH);
      digitalWrite(b, LOW); 
      digitalWrite(c, LOW); 
      digitalWrite(d, HIGH); 
      digitalWrite(e, HIGH); 
      digitalWrite(f, HIGH); 
      digitalWrite(g, HIGH);
    }else{
      if (status == 7){
        if (digitalRead(11) == HIGH){
          delay (200);
          status = (status - 1);
        }//2
        digitalWrite(a, LOW);
        digitalWrite(b, LOW); 
        digitalWrite(c, HIGH); 
        digitalWrite(d, LOW); 
        digitalWrite(e, LOW); 
        digitalWrite(f, HIGH); 
        digitalWrite(g, LOW);
      }else{
        if (status == 6){
          if (digitalRead(11) == HIGH){
            delay (200);
            status = (status - 1);
            }//3
          digitalWrite(a, LOW);
          digitalWrite(b, LOW); 
          digitalWrite(c, LOW); 
          digitalWrite(d, LOW); 
          digitalWrite(e, HIGH); 
          digitalWrite(f, HIGH); 
          digitalWrite(g, LOW);
        }else{
          if (status == 5){
            if (digitalRead(11) == HIGH){
              delay (200);
              status = (status - 1);
             }//4
              digitalWrite(a, HIGH);
              digitalWrite(b, LOW); 
              digitalWrite(c, LOW); 
              digitalWrite(d, HIGH); 
              digitalWrite(e, HIGH); 
              digitalWrite(f, LOW); 
              digitalWrite(g, LOW);
          }else{
            if (status == 4){
              if (digitalRead(11) == HIGH){
             delay (200);
              status = (status - 1);
             }//5
              digitalWrite(a, LOW);
              digitalWrite(b, HIGH); 
              digitalWrite(c, LOW); 
              digitalWrite(d, LOW); 
              digitalWrite(e, HIGH); 
              digitalWrite(f, LOW); 
              digitalWrite(g, LOW);
            }else{
              if (status == 3){
                if (digitalRead(11) == HIGH){
                delay (200);
               status = (status - 1);
                }//6
                digitalWrite(a, LOW);
                digitalWrite(b, HIGH); 
                digitalWrite(c, LOW); 
                digitalWrite(d, LOW); 
                digitalWrite(e, LOW); 
                digitalWrite(f, LOW); 
                digitalWrite(g, LOW);
              }else{
                if (status == 2){
                  if (digitalRead(11) == HIGH){
                  delay (200);
                  status = (status - 1);
                  }//7
                  digitalWrite(a, LOW);
                  digitalWrite(b, LOW); 
                  digitalWrite(c, LOW); 
                  digitalWrite(d, HIGH); 
                  digitalWrite(e, HIGH); 
                  digitalWrite(f, HIGH); 
                  digitalWrite(g, HIGH);
                }else{
                  if (status == 1){
                    if (digitalRead(11) == HIGH){
                      delay (200);
                      status = (status - 1);
                    }//8
                    digitalWrite(a, LOW);
                    digitalWrite(b, LOW); 
                    digitalWrite(c, LOW); 
                    digitalWrite(d, LOW); 
                    digitalWrite(e, LOW); 
                    digitalWrite(f, LOW); 
                    digitalWrite(g, LOW);
                  }else{
                    if (status == 0){
                      if (digitalRead(11) == HIGH){
                        delay (200);
                        status = (status - 1);
                      }//9
                      digitalWrite(a, LOW);
                      digitalWrite(b, LOW); 
                      digitalWrite(c, LOW); 
                      digitalWrite(d, HIGH); 
                      digitalWrite(e, HIGH); 
                      digitalWrite(f, LOW); 
                      digitalWrite(g, LOW);
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
