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
        self.status = 0
        self.last = False

    def set_decibel(self, decibel):
        self.decibel = decibel

    def is_interrupted(self):
        return self._stop_event.is_set()

    def interrupt(self):
        self._stop_event.set()

    def check_status(self):
        status = self.bridge.get("m")
        self.status = status
        if status > self.decibel:
            if not self.last:
                self.last = True
                self.execute_http_req()
        else:
            self.last = False

    def execute_http_req(self):
        request = "http://" + self.host + "/acc?key_c=AAAAAA&action=mic&content=1"
        return urlopen(request).read()

    def run(self):
        while not self.is_interrupted():
            self.check_status()

            sleep(0.1)
