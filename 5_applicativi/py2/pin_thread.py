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

import threading
from time import sleep
from urllib2 import urlopen


# ACC-Client key manager
# -
# @author giuliobosco
# @version 1.1 (2019-04-21 - 2019-04-21)


class PinThread(threading.Thread):
    TOGGLE_MODE = 'toggle'
    SWITCH_MODE = 'switch'
    ONCLICK_MODE = 'click'
    ONCHANGE_MODE = 'change'

    def __init__(self, key_manager, pin, bridge):
        """
        Create pin thread with key manager, pin to check and the global bridge client.
        Require global bridge client because is synchronized.
        :param key_manager: ACC KeyManager.
        :param pin: Pin to check.
        :param bridge: Global bridge client.
        """
        # excute super class contructor.
        super(PinThread, self).__init__()
        # create _stop_event
        self._stop_event = threading.Event()
        # set pin thread KeyManager
        self.key_manager = key_manager
        # Set pin thread pin
        self.pin = pin
        # set status of the pin
        self.status = None
        # set the global bridge client
        self.bridge = bridge
        self.on = self.ONCHANGE_MODE
        self.send = self.TOGGLE_MODE

    def interrupt(self):
        """
        Interrupt thread.
        """
        self._stop_event.set()

    def is_interrupted(self):
        """
        Check if the thead is interrupted.
        :return: True if the thread is interrupted.
        """
        return self._stop_event.is_set()

    def is_status_changed(self):
        """
        Check if the status of the pin is changed.
        :return: True if the status of the pin is changed.
        """
        # get the status of the pin (from the global bridge client)
        status = self.bridge.get(self.pin)
        if not status == self.status:
            # if the statuses are different update the status on the thread, and return true
            self.status = status
            return True

        # return false if the status is the same
        return False

    def execute_http_req(self):
        """
        Execute http request for update status on ACC-Server.
        :return: Return response of the request.
        """
        if len(self.key_manager.key) > 0:
            # execute the request if arduino has key
            # set host of the request
            host = self.key_manager.server_address
            # create request with host and key
            request = "http://" + host + "/acc?key=" + self.key_manager.key
            # add to the request pin and value to set
            request += "&pin=" + self.pin + "&set=toggle"  # + self.status
            # execute the request and return the response.
            return urlopen(request).read()

    def run(self):
        """
        Check the status of the request.
        """
        while not self.is_interrupted():
            if self.is_status_changed() and self.status == '1':
		print(self.status)
                # if status changed execute reqeust
                response = self.execute_http_req()
		print(response)
        sleep(0.1)
