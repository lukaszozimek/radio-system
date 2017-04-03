from bass import Bass

class Playout:

    self.device = 0
    self.frequency = 0
    self.initialized = False

    def __init__(self, device, frequency):
        print 'Initializing device {0} with {1}Hz'.format(device, frequency)
        self.initialized = Bass.BASS_Init(device, frequency, 0, 0, 0)
        if self.initialized:
            self.device = device
            self.frequency = frequency

    def __del__(self):
        print 'Destroying device {0}'.format(device)
        Bass.BASS_Free(self.device)
