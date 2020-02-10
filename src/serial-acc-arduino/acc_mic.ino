#define MIC_COMMAND 'm'

boolean isMicStatusChanged() {
  return true;
}

int getMicValue() {
  return 0;
}

void sendMic() {
  serialWriteCommand(MIC_COMMAND, String(getMicValue()));
}
