

from threading import Lock


class BridgeGlobal:
    def __init__(self):
        """
        Create global bridge, initialize the bridge client and the lock for synchronization.
        """
        # initialize synchronization lock
        self.lock = Lock()
        self.value = 1

    def set_value(self, value):
        self.value = value

    def get(self, key):
        """
        Get from the bridge with synchronization.
        :param key: Key of the value (memory address).
        :return: Value of the key.
        """
        # acquire the synchronization lock
        self.lock.acquire()
        # release the synchronization lock
        self.lock.release()
        # return the value
        return self.value

    def put(self, key, value):
        """
        Put the value in the bridge with synchronization.
        :param key: Key of the value (memory address).
        :param value: Value to set.
        """
        # acquire the synchronization lock
        self.lock.acquire()
        # set the value
        print ("key [" + key + "] value ["+value+"]")
        # release the synchronization lock
        self.lock.release()
