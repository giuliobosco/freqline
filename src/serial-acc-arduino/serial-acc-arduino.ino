// setup arduino
void setup() {
  // open channel at 9600 bit/s
  Serial.begin(9600);
  // send communication start char
  Serial.write(42);
}

// loop of the arduino
void loop() {
  // Read from serial
  String s = Serial.readStringUntil('\n');
  // wirte on serial each character readed before
  for (int i = 0; i < s.length(); i++) {
    Serial.write(s[i]);
  }
  // write return character
  Serial.write('\n');
}
