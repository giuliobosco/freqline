import sys
sys.path.insert(0, '/usr/lib/python2.7/bridge')

from time import sleep
from bridgeclient import BridgeClient as bridgeclient

value = bridgeclient()

while True:
    value.put('D13','1')
    value.put('D12','0')
    value.put('D11','1')
    sleep(0.1)
    value.put('D13','0')
    value.put('D12','1')
    value.put('D11','0')
    sleep(0.1)
    print(value.get('D6') + " " + value.get('D5') + " " + value.get('A0') + " " + value.get('A1'));
