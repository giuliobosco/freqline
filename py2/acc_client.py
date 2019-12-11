from bridge_global_l import BridgeGlobal
from BaseHTTPServer import HTTPServer
import time

from http_server import HttpServer
from mic_thread import MicThread
from remote_thread import RemoteThread

# server host name (accessible from any network)
hostName = "0.0.0.0"
# server port
hostPort = 8080


class AccClient:
    @staticmethod
    def start(decibel, address):
        """
        Start the acc client with the key manager.
        :param key_manager: Key manager for configure acc.
        """
        server = None
        try:
            # create http server with HttpServer
            # create the global bridge to arduino
            bridge = BridgeGlobal()

            threads = []

            mic_thread = MicThread(bridge, decibel, address)
            mic_thread.start()
            threads.append(mic_thread)

            remote_thread = RemoteThread(bridge, address)
            remote_thread.start()
            threads.append(remote_thread)

            print(time.asctime(), "Server Starts - %s:%s" % (hostName, hostPort))
            # start the http server
            server = HTTPServer((hostName, hostPort), HttpServer)
            server.bridge = bridge
            server.mic_thread = mic_thread
            server.serve_forever()
        except KeyboardInterrupt:
            # on key board interrupt
            # stop http server
            if server is not None:
                server.server_close()

            # stop all threads
            for thread in threads:
                thread.interrupt()
            pass

