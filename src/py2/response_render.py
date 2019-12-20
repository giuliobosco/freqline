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

# ACC-Client response render
# -
# @author giuliobosco
# @version 1.3 (2019-04-17 - 2019-04-21)

from datetime import datetime
from thermistor import get_celsius
from lights import Lights


class ResponseRender:
    # error string
    error = "ERROR"
    # ok string
    ok = "OK"
    # ok message string
    ok_msg = "ok"
    # warring string
    warring = "WARRING"

    def __init__(self, key_manager, bridge):
        """
        Create the response render with global bridge client or bridge client and KeyManager
        :param key_manager:
        :param bridge:
        """
        # set KeyManager
        self.key_manager = key_manager
        # set key
        self.key = ''
        # set pin
        self.pin = ''
        # set value
        self.value = ''
        # set get request true
        self.get = True
        # set the bridge client or global bridge client
        self.bridge = bridge

    def acc(self):
        if not self.key_manager.check_key(self.key):
            # if the key is wrong returns error
            return self.build(self.error, "Wrong key")
        try:
            self.check_parameters()
        except Exception as e:
            return self.build(self.error, e.message)

        if self.get:
            pin = self.get_pin()
            if pin == 'A1':
                voltage = self.bridge.get(pin)
                return self.build(self.ok, str(get_celsius(voltage)))

            value = self.bridge.get(pin)

            return self.build(self.ok, str(value))

        Lights(self.bridge).light_set(self.get_pin(), self.value)

        return self.build(self.ok, self.value)

    def check_parameters(self):
        if not len(self.pin) > 0:
            # if there's no pin raise exception "no pin"
            raise Exception("No pin in request")
        if not self.is_pin(self.pin):
            # if it's not a pin raise exception "no valid pin"
            raise Exception("Pin not valid")
        if not self.get:
            if not len(self.value) > 0:
                # if get request returns no value raise exception
                raise Exception("No value to set in request")

    def get_pin(self):
        """
        Get the pin.
        Check if the pin has right format.
        :return: Pin.
        """
        # string pin
        pin = str(self.pin)
        # pin to upper case
        pin = pin.upper()
        if not pin.startswith('A'):
            # if the pin doesn't start with the letter "A" add "D" in front of the pin
            pin = 'D' + pin
        return pin

    def is_pin(self, pin):
        """
        Check if the pin is valid.
        :param pin: Pin to check.
        :return: True if pin is valid.
        """
        try:
            # upper case string pin
            pin = str(pin).upper()
            if pin.startswith("A") and len(pin) == 2:
                # if pin starts with "A" and the lenght equals 2 remove "A".
                pin = pin.replace("A", "")
                pin = int(pin)
                if 0 <= pin <= 5:
                    # check if analog pin is valid
                    return True
                else:
                    return False
            pin = int(pin)
            if 0 <= pin <= 13:
                # check if digital pin is valid
                return True
        except Exception as e:
            return False
        return False

    def alive(self):
        """
        Return if alive.
        :return: Json response.
        """
        # data to insert in the response
        other = [("date", str(datetime.now())), ("id", self.key_manager.id)]
        # build response with ok, ok_msg and other informations
        return self.build(self.ok, self.ok_msg, other)

    def not_found(self, path):
        """
        Build not found json response.
        :param path: Not found path.
        :return: Json response.
        """
        # build response
        return self.build("ERROR", "Page " + path + " not found")

    def build(self, status, message, other=[]):
        """
        Build response.
        :param status: Status of the response.
        :param message: Message of the response.
        :param other: Other paramteres of the response.
        :return: Json response.
        """
        # create the response, with status
        string = "{\"status\":\"" + status + "\""
        # add message to the response
        string += ",\"message\":\"" + message + "\""
        # add other items to the response
        for item in other:
            if len(item) == 2:
                string += ",\"" + item[0] + "\":\"" + item[1] + "\""
        # close the response
        string += "}"
        # return bytes of the response
        return bytes(string)
