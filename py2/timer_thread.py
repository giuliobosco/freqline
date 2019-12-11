import threading
import time

from urllib2 import urlopen


class TimerThread(threading.Thread):
    def __init__(self, seconds):
        super(T, self).__init__()
        self._stop_even = threading.Event()
        self.seconds = seconds
        self.end_time = seconds

    def is_interrupted(self):
        return self._stop_even.is_set()

    def interrupt(self):
        self._stop_even.set()

    def run(self):
        self.end_time = int(time.time()) + self.end_time
        while not self.is_interrupted() and self.end_time > int(time.time()):
            time.sleep(.9)
        request = "http://" + self.host + "/acc?key_c=AAAAAA&action=remote&content=0"
        urlopen(request).read()
