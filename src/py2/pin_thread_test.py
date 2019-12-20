"""
The MIT License

Copyright 2019 giuliobosco.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
"""

# Test for pin thread
# -
# @author giuliobosco
# @version 1.0 (2019-04-21 - 2019-04-21)


import sys

sys.path.insert(0, '/usr/lib/python2.7/bridge')

from pin_thread import PinThread
from key_manager import KeyManager
from bridgeclient import BridgeClient
from time import sleep

bridge = BridgeClient()
key_manager = KeyManager('000000000000', '000000001234', '10.8.16.111:8080')
pin_thread = PinThread(key_manager=key_manager, pin='D6', bridge=bridge)

pin_thread.start()
sleep(10)

pin_thread.interrupt()
