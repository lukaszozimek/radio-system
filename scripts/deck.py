from pybass import *
import threading
from time import sleep
import ctypes

global globalPlayout

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

class Deck:

    handle = 0
    playNextSync = 0
    name = 'Sample Deck'

    def __init__(self, name, device, playout):
        print 'Initializing deck \'{0}\''.format(name)
        self.name = name
        self.device = device
        globalPlayout = playout
        
    def __del__(self):
        print 'Destroying deck \'{0}\''.format(self.name)
        self.unload()

    """
    def worker(self):
        while self.isActive():
            sleep(0.001)
        self.unload()   
    """

    def load(self, host, item, jwt):

        if self.handle > 0:
            self.unload()
        
        url = item.url(host, jwt)
        self.handle = BASS_StreamCreateURL(url, 0, 0, onDownload, None)
        err = BASS_ErrorGetCode()
        if err == 0:
            l = self.length()
            segS = item.markers.get('SEGs', 0) * 4 
            print 'Load media item /{0}/{1}/{2} (LEN: {3}, SEGs: {4}, ERR: {5})'.format(item.network, item.library, item.idx, l, segS, err)
            
            if segS > 0 and segS < l:
                # use mix if segS > 0 and segS < len
                print 'Set mix at cue = {0} to /{1}/{2}/{3}'.format(segS, item.network, item.library, item.idx)
                self.playNextSync = BASS_ChannelSetSync(self.handle, BASS_SYNC_POS, segS, onMix, None)
            else:
                self.eofSync = BASS_ChannelSetSync(self.handle, BASS_SYNC_END, 0, onEof, None)

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
        if BASS_StreamFree(self.handle):
            self.name = ''

    def isActive(self):
        return BASS_ChannelIsActive(self.handle) == BASS_ACTIVE_PLAYING