#define MIC_COMMAND 109

boolean isMicStatusChanged() {
  return false;
}

int getMicValue() {
  return 0;
}

void sendMic() {
  serialWriteCommand(MIC_COMMAND, String(getMicValue()));
}
