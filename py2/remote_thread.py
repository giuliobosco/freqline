import threading
from time import sleep
from urllib2 import urlopen


class RemoteThread(threading.Thread):
    def __init__(self, bridge, host):
        self._stop_event = threading.Event()
        self.bridge = bridge
        self.host = host
        self.status = None

    def is_interrupted(self):
        return self._stop_event.is_set()

    def interrupt(self):
        self._stop_event.set()

    def is_status_changed(self):
        status = self.bridge.get("r")
        if not status == self.status:
            self.status = status
            return True
        return False

    def execute_http_req(self):
        request = "http://" + self.host + "/acc?key_c=AAAAAA&action=remote=content=t"
        return urlopen(request).read()

    def run(self):
        while not self.is_interrupted():
            if self.is_status_changed():
                response = self.execute_http_req()
                print(response)
            sleep(0.1)
