from pybass import *
from random import randint
from item import MediaItem
from scheduler import Scheduler
from auth import Auth
from time import sleep
import threading
import sys

host = 'http://localhost:8080'
user = 'admin'
passwd='admin'

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

class Deck:

    handle = 0
    mixSync = 0
    eofSync = 0
    hooks = []

    def __init__(self, name, device, host, jwt, hooks):
        print 'Initializing deck \'{0}\''.format(name)
        self.name = name
        self.device = device
        self.host = host
        self.jwt = jwt
        self.hooks = hooks

    def setMixDeck(self, mixDeck):
        self.mixDeck = mixDeck
        
    def worker(self):
        while self.isActive():
            sleep(0.001)
        BASS_ChannelStop(self.handle)
        BASS_ChannelSetPosition(self.handle, 0, BASS_POS_BYTE)
        self.load()

    def load(self, item=None):

        if self.handle > 0:
            self.unload()

        if item is not None:
            self.item = item
        else:
            self.item = MediaItem(self.hooks[randint(0, len(self.hooks) - 1)])

        url = self.item.url(host, jwt)
        print '[{0}] LOAD /{1}/{2}/{3}'.format(self.name, self.item.network, self.item.library, self.item.idx)
        self.handle = BASS_StreamCreateURL(url, 0, 0, onDownload, 0)

        segS = self.item.markers.get('SEGs', 0) * 4 
        if segS > 0:
            self.mixSync = BASS_ChannelSetSync(self.handle, BASS_SYNC_POS, segS, onMix, id(self))

        self.eofSync = BASS_ChannelSetSync(self.handle, BASS_SYNC_END, 0, onEof, id(self))

    def length(self):
        return BASS_ChannelGetLength(self.handle, BASS_POS_BYTE)

    def play(self):
        print '[{0}] PLAY /{1}/{2}/{3}'.format(self.name, self.item.network, self.item.library, self.item.idx)
        BASS_ChannelPlay(self.handle, False)
        t = threading.Thread(target=self.worker)
        t.start()

    def pause(self):
        print '[{0}] PAUSE /{1}/{2}/{3}'.format(self.name, self.item.network, self.item.library, self.item.idx)
        BASS_ChannelPause(self.handle, False)

    def stop(self):
        print '[{0}] STOP /{1}/{2}/{3}'.format(self.name, self.item.network, self.item.library, self.item.idx)
        BASS_ChannelStop(self.handle)

    def unload(self):
        # print '[{0}] UNLOAD /{1}/{2}/{3}'.format(self.name, self.item.network, self.item.library, self.item.idx)
        BASS_ChannelRemoveSync(self.handle, self.mixSync)
        BASS_ChannelRemoveSync(self.handle, self.eofSync)
        self.mixSync = 0
        self.eofSync = 0
        BASS_StreamFree(self.handle)

    def isActive(self):
        return BASS_ChannelIsActive(self.handle) == BASS_ACTIVE_PLAYING

    def onMix(self):
        print '[{0}] ON MIX /{1}/{2}/{3}'.format(self.name, self.item.network, self.item.library, self.item.idx)
        self.mixDeck.play()

    def onEof(self):
        print '[{0}] ON EOF /{1}/{2}/{3}'.format(self.name, self.item.network, self.item.library, self.item.idx)
        self.load()

@DOWNLOADPROC
def onDownload(aaa, bbb, ccc):
    #print '<<<<< ON DOWNLOAD:{0}:{1}:{2} >>>>>'.format(aaa,bbb,ccc)
    a = True

@SYNCPROC
def onMix(aaa, bbb, ccc, ddd):
    deckInstance = ctypes.cast(ddd, ctypes.py_object).value
    deckInstance.onMix()

@SYNCPROC
def onEof(aaa, bbb, ccc, ddd):
    deckInstance = ctypes.cast(ddd, ctypes.py_object).value
    deckInstance.onEof()

if __name__ == '__main__':
    try:
        auth = Auth(user, passwd)
        jwt = auth.getJWT()
        scheduler = Scheduler(username=user, password=passwd)
        allHooks = scheduler.randomBlock('test', 'tes', 100).emissions
        hooks = []
        for e in allHooks:
            item = MediaItem(e)
            segS = item.markers.get('SEGs', 0) * 4 
            if segS > 0:
                hooks.append(e)   
        print 'Size of hooks: {}'.format(len(hooks))

        device = Device(name='DEFAULT', deviceId=-1, frequency=44100)
        
        aaa = Deck('AAA', device, host, jwt, hooks)
        bbb = Deck('BBB', device, host, jwt, hooks)
        ccc = Deck('CCC', device, host, jwt, hooks) 
        ddd = Deck('DDD', device, host, jwt, hooks)
        eee = Deck('EEE', device, host, jwt, hooks)

        aaa.setMixDeck(bbb)
        bbb.setMixDeck(ccc)
        ccc.setMixDeck(ddd)
        ddd.setMixDeck(eee)
        eee.setMixDeck(aaa)

        aaa.load()
        bbb.load()
        ccc.load()
        ddd.load()
        eee.load()
        
        aaa.play()

        while True:
            sleep(0.1)
    except KeyboardInterrupt:
        print >> sys.stderr, '\nExiting by user request.\n'
        sys.exit(0)