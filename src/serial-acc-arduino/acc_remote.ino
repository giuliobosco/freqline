#define REMOTE_PIN 5                          // input pin of the remote
#define REMOTE_5V_PIN 3                       // pin used as Power (5V) for remote

#define REMOTE_COMMAND 114                    // remote command

int remoteStatus = 0;                         // remoe status
boolean remoteStatusChanged;                  // remote changed status

/**
 * Setup the remote.
 * Input pin of the remote as INPUT
 * Power output pin set as OUTPUT
 * Power output pin set to HIGH
 */
void remoteSetup() {
  pinMode(REMOTE_PIN, INPUT);
  pinMode(REMOTE_5V_PIN, OUTPUT);
  digitalWrite(REMOTE_5V_PIN, HIGH);
}

/**
 * Check the remote status.
 */
void checkRemoteStatus() {
  int value = digitalRead(REMOTE_PIN);
  remoteStatusChanged = remoteStatus != value;

  remoteStatus = value;
}

/**
 * Get the remote status changed.
 * 
 * @return Remote status changed.
 */
boolean isRemoteStatusChanged() {
  return remoteStatusChanged;
}

/**
 * Get the remote status.
 * 
 * @return Remote status.
 */
boolean getRemoteStatus() {
  return remoteStatus;
}

/**
 * Send remote command.
 */
void sendRemote() {
  serialWriteCommand(REMOTE_COMMAND, String(getRemoteStatus()));
}
