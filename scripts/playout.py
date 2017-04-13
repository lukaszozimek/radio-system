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

    streamLength = -1
    mixFired = False
    eofFired = False
    handle = 0
    mixSync = 0
    eofSync = 0
    hooks = []
    currPos = -1
    thd = None

    def __init__(self, name, device, host, jwt, hooks):
        print 'Initializing deck \'{0}\''.format(name)
        self.name = name
        self.device = device
        self.host = host
        self.jwt = jwt
        self.hooks = hooks

    def setMixDeck(self, mixDeck):
        self.mixDeck = mixDeck

    def watchDog(self):
        raiseError = False
        while True: 
            sleep(0.9)
            pos = BASS_ChannelGetPosition(self.handle, BASS_POS_BYTE) 
            if pos != self.currPos and pos > self.currPos:
                self.currPos = pos
            elif  pos >= self.streamLength:
                raiseError = False
                break
            else:
                raiseError = True
                break
        if raiseError:
                self.errEvent()

    def load(self, item=None):
        if self.handle > 0:
            self.unload()

        if item is not None:
            self.item = item
        else:
            self.item = MediaItem(self.hooks[randint(0, len(self.hooks) - 1)])

        url = self.item.url(host, jwt)
        print '[{0}] LOAD /{1}/{2}/{3} - {4}'.format(self.name, self.item.network, self.item.library, self.item.idx, self.item.name)
        self.handle = BASS_StreamCreateURL(url, 0, 0, onDownload, 0)
        
        if self.handle > 0:
            self.streamLength = BASS_ChannelGetLength(self.handle, BASS_POS_BYTE)
        
            #FIXME: calc segS based on stream type MONO/STEREO/OTHER and channel resolution 8/16/32 bit
            segS = self.item.markers.get('SEGs', 0) * 4 
            if segS > 0 and segS < self.streamLength:
                self.mixSync = BASS_ChannelSetSync(self.handle, BASS_SYNC_POS, segS, onMix, id(self))

            self.eofSync = BASS_ChannelSetSync(self.handle, BASS_SYNC_END, 0, onEof, id(self))

            self.currPos = -1

            self.mixFired = False
            self.eofFired = False

            return True
        else:
            return False

    def loadForced(self):
        result = False
        while result == False:
            result = self.load()

    def length(self):
        return BASS_ChannelGetLength(self.handle, BASS_POS_BYTE)

    def play(self):
        print '[{0}] PLAY /{1}/{2}/{3} - {4}'.format(self.name, self.item.network, self.item.library, self.item.idx, self.item.name)
        BASS_ChannelPlay(self.handle, False)
        self.thd = threading.Thread(target=self.watchDog)
        #self.thd.name = '/{0}/{1}/{2}/{3}'.format(self.name, self.item.network, self.item.library, self.item.idx)
        self.thd.start()

    def pause(self):
        print '[{0}] PAUSE /{1}/{2}/{3} - {4}'.format(self.name, self.item.network, self.item.library, self.item.idx, self.item.name)
        BASS_ChannelPause(self.handle, False)

    def stop(self):
        print '[{0}] STOP /{1}/{2}/{3} - {4}'.format(self.name, self.item.network, self.item.library, self.item.idx, self.item.name)
        BASS_ChannelStop(self.handle)

    def unload(self):
        if self.mixSync > 0:
            BASS_ChannelRemoveSync(self.handle, self.mixSync)
            self.mixSync = 0

        if self.eofSync > 0:
            BASS_ChannelRemoveSync(self.handle, self.eofSync)
            self.eofSync = 0

        BASS_StreamFree(self. handle)
        self.handle = 0
        self.currPos = -1
        self.streamLength = -1

    def isActive(self):
        return BASS_ChannelIsActive(self.handle) == BASS_ACTIVE_PLAYING

    def mixEvent(self):
        print '>>>>>>>>>> {0} MIX:'.format(self.name)
        print '[{0}] ON MIX /{1}/{2}/{3} - {4}'.format(self.name, self.item.network, self.item.library, self.item.idx, self.item.name)
        self.mixDeck.play()
        self.mixFired = True

    def eofEvent(self):
        print '>>>>>>>>>> {0} EOF(MIX):'.format(self.name)
        if self.mixFired == False:
            print '[{0}] ON EOFMIX /{1}/{2}/{3} - {4}'.format(self.name, self.item.network, self.item.library, self.item.idx, self.item.name)
            self.mixDeck.play()
        else:
            print '[{0}] ON EOF /{1}/{2}/{3} - {4}'.format(self.name, self.item.network, self.item.library, self.item.idx, self.item.name)    
        self.eofFired = True
        self.load()
        currPos = -1

    def errEvent(self):
        if self.mixFired == False and self.eofFired == False:
            print '>>>>>>>>>> {0} ERR:'.format(self.name)
            print '[{0}] ON ERRMIX /{1}/{2}/{3} - {4}'.format(self.name, self.item.network, self.item.library, self.item.idx, self.item.name)
            sleep(0.1)
            self.mixDeck.play()
            self.load()

@DOWNLOADPROC
def onDownload(aaa, bbb, ccc):
    a = True

@SYNCPROC
def onMix(aaa, bbb, ccc, ddd):
    foo = ctypes.cast(ddd, ctypes.py_object).value
    foo.mixEvent()

@SYNCPROC
def onEof(aaa, bbb, ccc, ddd):
    bar = ctypes.cast(ddd, ctypes.py_object).value
    bar.eofEvent()

if __name__ == '__main__':
    try:
        auth = Auth(user, passwd)
        jwt = auth.getJWT()
        scheduler = Scheduler(username=user, password=passwd)
        hooks = scheduler.randomBlock('test', 'tes', 100).emissions
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

        aaa.loadForced()
        bbb.loadForced()
        ccc.loadForced()
        ddd.loadForced()
        eee.loadForced()
        
        aaa.play()

        while True:
            sleep(0.1)
    except KeyboardInterrupt:
        print >> sys.stderr, '\nExiting by user request.\n'
        sys.exit(0)