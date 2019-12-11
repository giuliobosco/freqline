import sys
sys.path.insert(0, '/usr/lib/python2.7/bridge')

from threading import Lock

from bridgeclient import BridgeClient


class BridgeGlobal:
    def __init__(self):
        """
        Create global bridge, initialize the bridge client and the lock for synchronization.
        """
        # initialize bridge
        self.bridge = BridgeClient()
        # initialize synchronization lock
        self.lock = Lock()

    def get(self, key):
        """
        Get from the bridge with synchronization.
        :param key: Key of the value (memory address).
        :return: Value of the key.
        """
        # acquire the synchronization lock
        self.lock.acquire()
        # get the value
        value = self.bridge.get(key)
        # release the synchronization lock
        self.lock.release()
        # return the value
        return value

    def put(self, key, value):
        """
        Put the value in the bridge with synchronization.
        :param key: Key of the value (memory address).
        :param value: Value to set.
        """
        # acquire the synchronization lock
        self.lock.acquire()
        # set the value
        self.bridge.put(key, value)
        # release the synchronization lock
        self.lock.release()
