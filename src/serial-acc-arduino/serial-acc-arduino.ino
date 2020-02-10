
// setup arduino
void setup() {
  // open channel at 9600 bit/s
  Serial.begin(9600);
  // send communication start char
  Serial.write(42);
}

// loop of the arduino
void loop() {
  String s = Serial.readStringUntil('\n');
  for (int i = 0; i < s.length(); i++) {
    Serial.write(s[i]);
  }
  Serial.write('\n');
}

void serialWriteCmd(char command, int value) {
  Serial.write("CIAO");
}
