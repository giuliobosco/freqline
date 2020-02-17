/**
 * Freqline start.
 * 
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.2 (2020-02-10 - 2020-02-17)
 */

// response bytes
#define OK_RESPONSE 111                     // ok response byte
#define ERROR_RESPONSE 101                  // error response byte
// response strings
#define NOT_SUPPORTED_MESSAGE "not supported"

// null command bytes
#define NULL_COMMAND 110                    // null command byte
#define NULL_COMMAND_VALUE "null"           // null command value

// echo command bytes
#define ECHO_COMMAND 69                     // echo command byte

// decibel command
#define DECIBEL_COMMAND 100                 // decibel command byte

// frequence command
#define FREQUENCE_COMMAND 102               // frequence update command byte
#define GENERATOR_OFF_COMMAND 103           // generator turn off command byte
#define GENERATOR_ON_COMMAND 71             // generator turn on command byte

// send command control constants
#define SENT_NULL 0                         // last sent null command
#define SENT_MIC 1                          // last sent microphone command
#define SENT_REMOTE 2                       // last sent remote command

byte lastSent = 0;                          // last command sent

/**
 * Setup arduino.
 * - serial
 * - remote
 * - frequence
 */
void setup() {
  // setup serial communication
  serialSetup();

  // setup remote
  remoteSetup();

  // setup frequence generator
  frequenceSetup();
}

/**
 * Loop of the arduino.
 * - read command (write response)
 * - send command (read response)
 * - frequence loop (update frequence on AD9833)
 */
void loop() {
  readCommand();
  sendCommand();

  frequencLoop();
}

/**
 * Read command execution (write response).
 */
void readCommand() {
  // read command from serial
  serialReadCommand();

  switch (getInstruction()) {
    case ECHO_COMMAND:
      serialWriteResponse(OK_RESPONSE, getInstructionValue());
      break;
    case DECIBEL_COMMAND:
      setDecibel(getInstructionValue());
      serialWriteResponse(OK_RESPONSE, "");
      break;
    case FREQUENCE_COMMAND:
      setFrequence(getInstructionValue());
      serialWriteResponse(OK_RESPONSE, "");
      break;
    case GENERATOR_OFF_COMMAND:
      turnOff();
      serialWriteResponse(OK_RESPONSE, "");
      break;
    case GENERATOR_ON_COMMAND:
      turnOn();
      serialWriteResponse(OK_RESPONSE, "");
      break;
    default:
      serialWriteResponse(ERROR_RESPONSE, NOT_SUPPORTED_MESSAGE);
      break;
  }
}

/**
 * Send command (read respnose).
 */
void sendCommand() {
  checkRemoteStatus();
  checkDecibel();
  
  if (isMicStatusChanged() && isRemoteStatusChanged()) {
    if (lastSent == SENT_MIC) {
      sendRemote();
      lastSent = SENT_REMOTE;
    } else {
      sendMic();
      lastSent = SENT_MIC;
    }
  } else if (isMicStatusChanged()) {
    sendMic();
    lastSent = SENT_MIC;
  } else if (isRemoteStatusChanged()) {
    sendRemote();
    lastSent = SENT_REMOTE;
  } else {
    sendNull();
    lastSent = SENT_NULL;
  }
}

/**
 * Send null command.
 */
void sendNull() {
  serialWriteCommand(NULL_COMMAND, NULL_COMMAND_VALUE);
}
