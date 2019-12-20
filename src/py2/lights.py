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

# Domotics lights module
# -
# @author giuliobosco
# @version 1.0.1 (2019-04-12 - 2019-04-12)


class Lights:
    def __init__(self, bridge):
        """
        ACC-Client light controller
        :param bridge: BridgeClient, connection to arduino.
        """
        self.bridge = bridge

    def light_set(self, pin='D13', value='0'):
        """
        Set status to the light.
        :param pin: Pin of the light on arduino.
        :param value: Status of the light.
        """
        self.bridge.put(str(pin), str(value))

    def light_on(self, pin='D13'):
        """
        Turn on the light on arduino.
        :param pin: Pin to turn on.
        """
        self.light_set(pin, '1')

    def light_off(self, pin='D13'):
        """
        Turn off the light on arduino.
        :param pin: Pin to turn off.
        :return:
        """
        self.light_set(pin, '0')
