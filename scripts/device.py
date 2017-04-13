from pybass import *

class Device:

    name = ''
    deviceId = 0
    frequency = 0
    initialized = False
    handle = 0

    def __init__(self, name, deviceId, frequency):
        print 'Initializing device \'{0}\' with id {1} and freqency {2} Hz'.format(name, deviceId, frequency)
        self.name = name
        self.initialized = BASS_Init(deviceId, frequency, 0, 0, 0)
        if self.initialized:
            self.deviceId = deviceId
            self.frequency = frequency

    def __del__(self):
        print 'Destroying device \'{0}\''.format(self.name)
        BASS_SetDevice(self.deviceId)
        BASS_Free()