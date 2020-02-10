#define SERIAL_SPEED 9600                     // serial speed of communication (baud)
#define SERIAL_INIT 42                        // serial initialized char
#define SERIAL_END_OF_COMMAND '\n'            // End of the command char

char instruction;                             // instruction variable
String instructionValue;                      // instruction value variable

/**
 * Get the instruction.
 * 
 * @return Instruction.
 */
char getInstruction() {
  return instruction;
}

/**
 * Get the instruction value.
 * 
 * @return Instruction value.
 */
String getInstructionValue() {
  return instructionValue;
}

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
 * Read instruction from serial.
 */
boolean serialRead() {
  // read from serial line
  String s = Serial.readStringUntil(SERIAL_END_OF_COMMAND);

  // if correctly read exists
  if (s.length() > 0) {
    // set command char to instruction variable
    instruction = s[0];
    // set command value to instruction value variable
    instructionValue = s.substring(1);
    return true;
  }

  return false;
}

/**
 * Read command from serial.
 */
void serialReadCommand() {
  serialRead();
}

/**
 * Read response from serial.
 * 
 * @return True if response is valid.
 */
boolean serialReadResponse() {
  return serialRead();
}

/**
 * Write to serial.
 * 
 * @param instruction Instruction.
 * @param value Instruction value.
 */
void serialWrite(char instruction, String value) {
  // write instruction to serial
  Serial.write(instruction);

  // write all chars of the value to serial
  for (int i = 0; i < value.length(); i++) {
    Serial.write(value[i]);
  }

  // write end of message char to serial
  Serial.write(SERIAL_END_OF_COMMAND);
}

/**
 * Write response to serial.
 * 
 * @param response Response char to write to serial.
 * @param value value of the response to write to serial.
 */
void serialWriteResponse(char response, String value) {
  serialWrite(response, value);
}

/**
 * Write command to serial.
 * 
 * @param command Command char to write to serial.
 * @param value Value of the command to write to write.
 */
void serialWriteCommand(char command, String value) {
  boolean commandSent = false;

  while (!commandSent) {
    serialWrite(command, value);

    commandSent = serialReadResponse();
  }
  
}
