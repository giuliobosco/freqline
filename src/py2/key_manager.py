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


# ACC-Client key manager
# -
# @author giuliobosco
# @version 1.0.3 (2019-04-17 - 2019-04-21)


class KeyManager:
    def __init__(self, id, key='', server_address='localhost'):
        """
        Create the KeyManager with the id, key (optional) and server_address (optional), if it's created only with the id
        no key will be requested to send requests to ACC-Client.
        :param id: Id of the ACC-Client.
        :param key: Key of communication between ACC-Server and ACC-Client.
        :param server_address: Domotics server ip address (ACC-Server).
        """
        self.key = key
        self.id = id
        self.server_address = server_address

    def check_key(self, key):
        """
        Check if the key is correct, if it has no key or no ip address, it will be always true.
        :param key: Key to check.
        :return: True if the key is the same as the saved in KeyManager, or no key or ip.
        """
        if len(self.key) > 0 and len(self.server_address) > 0:
            return self.key == key
        else:
            return True

    def get_key(self):
        """
        Get the key in KeyManager.
        :return: Key in KeyManager.
        """
        return self.key
