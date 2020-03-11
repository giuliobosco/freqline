/**
 * ACC microphone module.
 * 
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.2 (2020-02-10 - 2020-02-17)
 */

#define MIC_PIN A2                            // Microphone input data pin
#define MIC_GND_PIN 8                         // Microphone GND pin
#define MIC_5V_PIN 7                          // Microphone VCC (5V) pin

#define MIC_COMMAND 109                       // Microphone command byte
#define MEASURE_LENGTH 64                     // Measure length
#define TOLLERANCE_VALUE 33                   // Tollerance value

int decibel = 50;                                  // decibels limit
int micStatus;                                // decibels of the microphone
boolean micStatusChanged;                     // microphone status changed
int soundSignal;                              // sound singal
int sum = 0;                                  // sum of reading

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
  sum = 0;
  
  for (int i = 0; i < MEASURE_LENGTH; i++) {
    soundSignal = analogRead(MIC_PIN);
    soundSignal = int(20 * log10(soundSignal));
    sum += soundSignal;
  }

  int val = sum / MEASURE_LENGTH;
  micStatusChanged = val > decibel;
  micStatus = val;
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
