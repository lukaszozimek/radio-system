from pybass import *
from random import randint
from item import MediaItem
from scheduler import Scheduler
from auth import Auth
from time import sleep
import threading
import sys

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

    mixSync = 0
    eofSync = 0
    playout = None

    def __init__(self, name, device):
        print 'Initializing deck \'{0}\''.format(name)
        self.name = name
        self.device = device
        
    def worker(self):
        while self.isActive():
            sleep(0.001)
        self.stop()   
        BASS_ChannelSetPosition(self.handle, 0, BASS_POS_BYTE)

    def load(self, host, item, jwt, nextDeck):
        
        url = item.url(host, jwt)
        self.handle = BASS_StreamCreateURL(url, 0, 0, onDownload, 0)
        err = BASS_ErrorGetCode()
        if err == 0:
            l = self.length()
            segS = item.markers.get('SEGs', 0) * 4 
            print 'Load media item /{0}/{1}/{2} (LEN: {3}, SEGs: {4}, ERR: {5})'.format(item.network, item.library, item.idx, l, segS, err)
            
            if segS > 0 and segS < l:
                # use mix if segS > 0 and segS < len
                print 'Set mix at cue = {0} to /{1}/{2}/{3} id(self)={4}'.format(segS, item.network, item.library, item.idx, id(nextDeck))
                print (id(self))
                self.mixSync = BASS_ChannelSetSync(self.handle, BASS_SYNC_POS, segS, onMix, id(nextDeck))
                print 'MIX_SYNC={0}'.format(self.mixSync)
            
            #self.eofSync = BASS_ChannelSetSync(self.handle, BASS_SYNC_END|BASS_SYNC_ONETIME, 0, self.onEof, 0)

    def length(self):
        return BASS_ChannelGetLength(self.handle, BASS_POS_BYTE)

    def play(self):
        print 'Play {0}'.format(self.name)
        BASS_ChannelPlay(self.handle, False)
        t = threading.Thread(target=self.worker)
        t.start()

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

    def hello(self):
        print 'HELLO FROM MIX CALLBACK :)'
        self.play()

@DOWNLOADPROC
def onDownload(aaa, bbb, ccc):
    #print '<<<<< ON DOWNLOAD:{0}:{1}:{2} >>>>>'.format(aaa,bbb,ccc)
    a = True

@SYNCPROC
def onMix(aaa, bbb, ccc, ddd):
    print '<<<<< ON MIX:{0}:{1}:{2}:{3} >>>>>'.format(aaa,bbb,ccc,ddd)
    deckInstance = ctypes.cast(ddd, ctypes.py_object).value
    deckInstance.hello()

@SYNCPROC
def onEof(aaa, bbb, ccc, ddd):
    print '<<<<< ON EOF:{0}:{1}:{2}:{3}:{4} >>>>>'.format(aaa,bbb,ccc,ddd)
    #globalPlayout.play()

if __name__ == '__main__':
    try:
        host = 'http://localhost:8080'
        user = 'admin'
        passwd='admin'
        auth = Auth(user, passwd)
        jwt = auth.getJWT()
        scheduler = Scheduler(username=user, password=passwd)
        allHooks = scheduler.randomBlock('test', 'tes', 50).emissions
        hooks = []
        for e in allHooks:
            item = MediaItem(e)
            segS = item.markers.get('SEGs', 0) * 4 
            if segS > 0:
                hooks.append(e)   

        device = Device(name='DEFAULT', deviceId=-1, frequency=44100)
        
        aaa = Deck('AAA', device)
        bbb = Deck('BBB', device)
        ccc = Deck('CCC', device)    
        ddd = Deck('DDD', device)
        eee = Deck('EEE', device)

        aaa.load(host, MediaItem(hooks[randint(0, len(hooks) - 1)]), jwt, bbb)
        bbb.load(host, MediaItem(hooks[randint(0, len(hooks) - 1)]), jwt, ccc)
        ccc.load(host, MediaItem(hooks[randint(0, len(hooks) - 1)]), jwt, ddd)
        ddd.load(host, MediaItem(hooks[randint(0, len(hooks) - 1)]), jwt, eee)
        eee.load(host, MediaItem(hooks[randint(0, len(hooks) - 1)]), jwt, aaa)
        
        aaa.play()

        while True:
            sleep(0.1)
    except KeyboardInterrupt:
        print >> sys.stderr, '\nExiting by user request.\n'
        sys.exit(0)