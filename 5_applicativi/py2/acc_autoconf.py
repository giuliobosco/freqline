from urllib2 import urlopen
import json

from key_manager import KeyManager


def acc_autoconf(server_address):
    try:
        # generate the http get request for the auto configuration
        request = "http://" + server_address + "/acc?key_c=AAAAAA&action=conf&content=1"
        # execute the http get request
        response = urlopen(request).read()
        # load the http response as json
        data = json.loads(response)
        # if the json response contains the right parameters
        if 'decibel' in data:
            # return decibel
            return data['decibel']
    except:
        pass
    # if something goes wrong, create the key manager, with the id
    return 10
