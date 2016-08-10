#include <SoftwareSerial.h>

SoftwareSerial blueSerial(2, 3);
void setup() {
  // put your setup code here, to run once:
  blueSerial.begin(9600);
  Serial.begin(9600);
}


void loop() {
  // put your main code here, to run repeatedly:
  int dataA0 = analogRead(A0);
  int dataA1 = analogRead(A1);
  int dataA2 = analogRead(A2);
  int dataA3 = analogRead(A3);
  int dataA4 = analogRead(A4);
  int dataA5 = analogRead(A5);

  //data send
  sendData(dataA0);
  sendData(dataA1);
  sendData(dataA2);
  sendData(dataA3);
  sendData(dataA4);
  sendData(dataA5);
  /*
  //블루투스 테스트를 위한 코드
  if(blueSerial.available()){
    Serial.write(blueSerial.read());
  }
  if(Serial.available()){
    blueSerial.write(Serial.read());
  }
  */
  delay(2000);
}

//원래 압력센서의 값이 0~1023인것을 0~127로 변경하여 byte에 한번에 들어가도록 한다.

void sendData(int data){
  byte byteData = map(data, 0 , 1023, 0, 127);
  Serial.println("data. origin: " + String(data) + ", after: " + String(byteData));
  blueSerial.write(byteData);
}
/*
void sendData(byte alph, int data){
  blueSerial.write(alph);
  blueSerial.write((data/100));
  blueSerial.write((data%100)/10);
  blueSerial.write(data%10);
}
*/

