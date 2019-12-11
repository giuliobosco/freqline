import threading
from time import sleep
from urllib2 import urlopen


class MicThread(threading.Thread):
    def __init__(self, bridge, decibel, host):
        super(MicThread, self).__init__()
        self._stop_event = threading.Event()
        self.bridge = bridge
        self.decibel = decibel
        self.host = host

    def is_interrupted(self):
        return self._stop_event.is_set()

    def interrupt(self):
        self._stop_event.set()

    def check_status(self):
        status = self.bridge.get("m")
        if status > self.decibel:
            return True
        return False

    def execute_http_req(self):
        request = "http://" + self.host + "/acc?key_c=AAAAAA&action=mic&content=1"
        return urlopen(request).read()

    def run(self):
        while not self.is_interrupted():
            if self.check_status():
                response = self.execute_http_req()
                print (response)
                sleep(0.1)