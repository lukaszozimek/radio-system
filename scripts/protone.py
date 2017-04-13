import threading

class Playout(threading.Thread):

    def __init__(self, *args, **kwargs):
        super(Playout, self).__init__(*args, **kwargs)
        self._stop = threading.Event()

    def stop(self):
        self._stop.set()

    def stopped(self):
        return self._stop.isSet()

