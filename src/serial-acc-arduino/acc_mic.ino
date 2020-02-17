/**
 * ACC microphone module.
 * 
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.2 (2020-02-10 - 2020-02-17)
 */

#define MIC_PIN A2                            // Microphone input data pin

#define MIC_COMMAND 109                       // Microphone command byte

int decibel;                                  // decibels limit
int micStatus;                                // decibels of the microphone
boolean micStatusChanged;                     // microphone status changed

/**
 * Set the decibles limit.
 * 
 * @param decibelString decibels limit as string.
 */
void setDecibel(String decibelString) {
  decibel = decibelString.toInt();
}

/**
 * Get the decibels limit.
 * 
 * @return Decibels limit.
 */
int getDecibel() {
  return decibel;
}

/**
 * Check decibels of microphone.
 */
void checkDecibel() {
  int value = digitalRead(MIC_PIN);
  value = 20*log10(value);

  micStatusChanged = micStatus != value;

  micStatus = value;
}

/**
 * Get is microphone status changed.
 * 
 * @return Is microphone status changed.
 */
boolean isMicStatusChanged() {
  return micStatusChanged;
}

/**
 * Get the microphone value.
 * 
 * @return Microhpone value.
 */
int getMicValue() {
  return micStatus;
}

/**
 * Send microphone command.
 */
void sendMic() {
  serialWriteCommand(MIC_COMMAND, String(getMicValue()));
}
