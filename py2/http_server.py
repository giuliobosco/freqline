from BaseHTTPServer import BaseHTTPRequestHandler
from urlparse import parse_qsl

from response_render import ResponseRender

from timer_thread import TimerThread


class HttpServer(BaseHTTPRequestHandler):
    def send_header_json(self):
        """
        Send to the client the json header, response 200, content-type
        """
        # send response code 200
        self.send_response(200)
        # send contenty-type: text/json
        self.send_header("Content-type", "text/json")
        # end the http header
        self.end_headers()

    def do_GET(self):
        """
        Do at get http request.
        """
        if "/acc?" in self.path:
            # if ACC request execute self.acc()
            self.acc()
        elif ("/alive" == self.path) or ("/" == self.path):
            # if alive request execute self.alive()
            self.alive()
        else:
            # otherwise 404 file not found
            # send response 404
            self.send_response(404, "page not found")
            # end the http header
            self.end_headers()
            #  write return response not found
            self.wfile.write(ResponseRender(self.server.key_manager, self.server.bridge).not_found(self.path))

    def acc(self):
        """
        Render acc response.
        """
        # send to client json header
        self.send_header_json()
        # explode path, http get reqeust attributes
        attributes = parse_qsl(self.path.split("?")[1])

        key_c = None
        decibel = None
        frequence = None
        generator = None
        timer = None
        for attribute in attributes:
            if attribute[0] == "key_c":
                key_c = attribute[1]
            if attribute[0] == "decibel":
                decibel = attribute[1]
            if attribute[0] == "frequence":
                frequence = attribute[1]
            if attribute[0] == "generator":
                generator = attribute[1]
            if attribute[0] == "timer":
                generator = attribute[1]

        if key_c == "AAAAAA":
            if decibel is not None:
                self.server.mic_thread.set_decibel(decibel)

            if frequence is not None and generator is not None:
                if timer is not None:
                    self.timer(timer)
                self.generator(generator, frequence)

    def timer(self, timer):
        timer_thread = TimerThread(timer)
        timer_thread.start()

    def generator(self, generator, frequence):
        self.server.bridge.put("g", generator)
        self.server.bridge.put("f", frequence)

    def alive(self):
        """
        Render alive response
        """
        # send to client json header
        self.send_header_json()
        # send to client alive response rendered from ResponseRender
        self.wfile.write(ResponseRender(self.server.key_manager, self.server.bridge).alive())
