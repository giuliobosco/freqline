/**
 * ACC Serial communication module.
 * 
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.2 (2020-02-10 - 2020-02-17)
 */

#define SERIAL_SPEED 9600                     // serial speed of communication (baud)
#define SERIAL_INIT 42                        // serial initialized byte
#define COMMAND_INIT 43
#define RESPONSE_INIT 45
#define SERIAL_END_OF_COMMAND 10              // End of the command byte

byte instruction;                             // instruction variable
String instructionValue;                      // instruction value variable

/**
 * Get the instruction.
 * 
 * @return Instruction.
 */
byte getInstruction() {
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
  // send communication start byte
  Serial.write(SERIAL_INIT);
}

/**
 * Read instruction from serial.
 */
boolean serialRead(byte sequenceType) {
  Serial.readStringUntil(sequenceType);
  
  // read from serial line
  String s = Serial.readStringUntil(SERIAL_END_OF_COMMAND);

  // if correctly read exists
  if (s.length() > 0) {
    // set command byte to instruction variable
    instruction = (byte) s[0];
    
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
  serialRead(COMMAND_INIT);
}

/**
 * Read response from serial.
 * 
 * @return True if response is valid.
 */
boolean serialReadResponse() {
  return serialRead(RESPONSE_INIT);
}

/**
 * Write to serial.
 * 
 * @param instruction Instruction.
 * @param value Instruction value.
 */
void serialWrite(byte sequenceType, byte instruction, String value) {
  // write sequence type to serial
  Serial.write(sequenceType);
  // write instruction to serial
  Serial.write(instruction);

  // write all bytes of the value to serial
  for (int i = 0; i < value.length(); i++) {
    Serial.write(value[i]);
  }

  // write end of message byte to serial
  Serial.write(SERIAL_END_OF_COMMAND);
}

/**
 * Write response to serial.
 * 
 * @param response Response byte to write to serial.
 * @param value value of the response to write to serial.
 */
void serialWriteResponse(byte response, String value) {
  serialWrite(RESPONSE_INIT, response, value);
}

/**
 * Write command to serial.
 * 
 * @param command Command byte to write to serial.
 * @param value Value of the command to write to write.
 */
void serialWriteCommand(byte command, String value) {
  boolean commandSent = false;

  while (!commandSent) {
    serialWrite(COMMAND_INIT, command, value);

    commandSent = serialReadResponse();
  }
  
}
