
// setup arduino
void setup() {
  // open channel at 9600 bit/s
  Serial.begin(9600);
  // send communication start char
  Serial.write(42);
}

// loop of the arduino
void loop() {
  Serial.write("HelloWorld!\n");
}

void serialWriteCmd(char command, int value) {
  Serial.write("CIAO");
}
