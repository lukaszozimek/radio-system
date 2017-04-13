from pybass import *
from random import randint
from item import MediaItem
from scheduler import Scheduler
from auth import Auth
from time import sleep
import sys

globalPlayout = None

@DOWNLOADPROC
def onDownload(arg1, arg2, arg3):
    print '<<<<< ON DOWNLOAD >>>>>'

@SYNCPROC
def onMix(aaa, bbb, ccc, ddd):
    print '<<<<< ON MIX >>>>>'
    globalPlayout.play()

@SYNCPROC
def onEof(aaa, bbb, ccc, ddd):
    print '<<<<< ON EOF >>>>>'
    globalPlayout.play()

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

    """
    def __del__(self):
        print 'Destroying device \'{0}\''.format(self.name)
        BASS_SetDevice(self.deviceId)
        BASS_Free()
    """
    
class Deck:

    mixSync = 0
    eofSync = 0

    def __init__(self, name, device, playout):
        print 'Initializing deck \'{0}\''.format(name)
        self.name = name
        self.device = device
        globalPlayout = playout
        
    """    
    def __del__(self):
        print 'Destroying deck \'{0}\''.format(self.name)
        self.unload()
    """

    """
    def worker(self):
        while self.isActive():
            sleep(0.001)
        self.unload()   
    """

    def load(self, host, item, jwt):

        #if self.handle > 0:
        #    self.unload()
        
        url = item.url(host, jwt)
        self.handle = BASS_StreamCreateURL(url, 0, BASS_STREAM_AUTOFREE	, onDownload, None)
        err = BASS_ErrorGetCode()
        if err == 0:
            l = self.length()
            segS = item.markers.get('SEGs', 0) * 4 
            print 'Load media item /{0}/{1}/{2} (LEN: {3}, SEGs: {4}, ERR: {5})'.format(item.network, item.library, item.idx, l, segS, err)
            
            if segS > 0 and segS < l:
                # use mix if segS > 0 and segS < len
                
                self.mixSync = BASS_ChannelSetSync(self.handle, BASS_SYNC_POS|BASS_SYNC_ONETIME, segS, onMix, None)
                err = BASS_ErrorGetCode()
                print 'Set mix at cue = {0} to /{1}/{2}/{3} (err_code: {4})'.format(segS, item.network, item.library, item.idx, err)
            else:
                self.eofSync = BASS_ChannelSetSync(self.handle, BASS_SYNC_END, 0, onEof, None)
                err = BASS_ErrorGetCode()
                print 'Set EOF to /{0}/{1}/{2} (err_code: {3})'.format(item.network, item.library, item.idx, err)

    def length(self):
        return BASS_ChannelGetLength(self.handle, BASS_POS_BYTE)

    def play(self):
        print 'Play {0}'.format(self.name)
        BASS_ChannelPlay(self.handle, False)
        #t = threading.Thread(target=self.worker)
        #t.start()

    def pause(self):
        print 'Pause {0}'.format(self.name)
        BASS_ChannelPause(self.handle, False)

    def stop(self):
        print 'Stop {0}'.format(self.name)
        BASS_ChannelStop(self.handle)

    def unload(self):
        print 'Unload {0}'.format(self.name)
        BASS_ChannelRemoveSync(self.handle, self.mixSync)
        BASS_ChannelRemoveSync(self.handle, self.eofSync)
        self.mixSync = 0
        self.eofSync = 0
        if BASS_StreamFree(self.handle):
            self.name = ''

    def isActive(self):
        return BASS_ChannelIsActive(self.handle) == BASS_ACTIVE_PLAYING

class Playout:

    def __init__(self, hostname, user, password):
        self.host = hostname
        self.auth = Auth(user, password)
        self.jwt = self.auth.getJWT()
        self.scheduler = Scheduler(username=user, password=password)
        self.hooks = self.scheduler.randomBlock('test', 'tes', 30).emissions
        self.device = Device(name='DEFAULT', deviceId=-1, frequency=44100)

    def onMix(self):
        deck = Deck('FooDeck', self.device, self)
        item = MediaItem(self.hooks[randint(0, len(self.hooks) - 1)])
        deck.load(self.host, item, self.jwt)
        deck.play()
        while deck.isActive():
            sleep(0.01)
        deck.unload()

    def play(self):
        deck = Deck('FooDeck', self.device, self)
        item = MediaItem(self.hooks[randint(0, len(self.hooks) - 1)])
        deck.load(self.host, item, self.jwt)
        deck.play()
        while deck.isActive():
            sleep(0.01)
        deck.unload()

if __name__ == '__main__':
    try:
        globalPlayout = Playout('http://localhost:8080', 'admin', 'admin')
        globalPlayout.play()
        while True:
            sleep(0.1)
    except KeyboardInterrupt:
        print >> sys.stderr, '\nExiting by user request.\n'
        sys.exit(0)