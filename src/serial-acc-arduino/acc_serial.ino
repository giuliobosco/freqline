#define SERIAL_SPEED 9600                     // serial speed of communication (baud)
#define SERIAL_INIT 42                        // serial initialized char
#define SERIAL_END_OF_COMMAND '\n'            // End of the command char

/**
 * Initialization of serial communication.
 */
void serialSetup() {
  // open serial channel
  Serial.begin(SERIAL_SPEED);
  // send communication start char
  Serial.write(SERIAL_INIT);
}

/**
 * Read command from serial.
 */
void readCommand() {
  // read command from serial line
  String s = Serial.readStringUntil(SERIAL_END_OF_COMMAND);

  // if command exists
  if (s.length() > 0) {
    // set command char to command variable
    command = s[0];
    // set command value to command value variable
    commandValue = s.substring(1);
  }
}

/**
 * Write command to serial.
 * 
 * @param command command char to write to serial
 * @param value value of the command to wirte to serial
 */
void writeCommand(char command, String value) {
  // write command to serial
  Serial.write(command);

  // write all chars of the value to serial
  for (int i = 0; i < value.length(); i++) {
    Serial.write(value[i]);
  }

  // write end of command char to serial
  Serial.write(SERIAL_END_OF_COMMAND);
}
