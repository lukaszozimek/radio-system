from bass import Bass

class Deck:

    id = ''
    name = ''
    deviceId = 0
    frequency = 0
    initialized = False
    handle = 0

    def __init__(self, id, deviceId, frequency):
        print 'Initializing deck \'{0}\' with id \'{1}\' and freqency {2} Hz'.format(id, deviceId, frequency)
        self.id = id
        self.initialized = Bass.BASS_Init(deviceId, frequency, 0, 0, 0)
        if self.initialized:
            self.deviceId = deviceId
            self.frequency = frequency

    def __del__(self):
        print 'Destroying deck \'{0}\''.format(self.id)
        Bass.BASS_SetDevice(self.deviceId)
        Bass.BASS_Free()

    def load(self, networkId=None, libraryPrefix=None, mediaId=None, fileName=None):

        if fileName is not None:
            self.name = fileName
            print 'Load file {0}'.format(self.name)
            self.handle = Bass.BASS_StreamCreateFile(False, fileName, 0, 0, 0)
        else:
            self.name = '/{0}/{1}/{2}'.format(networkId, libraryPrefix, mediaId)
            print 'Load media {0}'.format(self.name)
            hostname = 'http://localhost:8080'
            jwt = 'Authorization: Bearer eyJORVRXT1JLIjp7ImlkIjoxLCJzaG9ydGN1dCI6InRlc3QiLCJuYW1lIjoidGVzdCIsImFjdGl2ZSI6dHJ1ZSwiZGVzY3JpcHRpb24iOiJ0ZXN0In0sIkNIQU5ORUwiOlt7ImlkIjoxLCJzaG9ydGN1dCI6InRlcyIsIm5hbWUiOiJ0ZXN0IiwiZGVzY3JpcHRpb24iOiJ0ZXN0IiwibmV0d29yayI6eyJpZCI6MSwic2hvcnRjdXQiOiJ0ZXN0IiwibmFtZSI6InRlc3QiLCJhY3RpdmUiOnRydWUsImRlc2NyaXB0aW9uIjoidGVzdCJ9fV0sImFsZyI6IkhTNTEyIn0.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTQ5Mzc1OTgxM30.qERm7qNUaxQxy1ys512CNqnNvcSloTViCAsy2IObptAlcFVj9qbIjicGqEsgnjseYwhLlChRkluVTtpnPCcIhg'
            url = '{0}/api/network/{1}/library/{2}/item/{3}/stream\r\n{4}'.format(hostname, networkId, libraryPrefix, mediaId, jwt)
            self.handle = Bass.BASS_StreamCreateURL(url, 0, 0, self.onDownload, 0);

    def play(self):
        print 'Play {0}'.format(self.name)
        Bass.BASS_ChannelPlay(self.handle, False)

    def pause(self):
        print 'Pause {0}'.format(self.name)
        Bass.BASS_ChannelPause(self.handle, False)

    def stop(self):
        print 'Stop {0}'.format(self.name)
        Bass.BASS_ChannelStop(self.handle)

    def unload(self):
        print 'Unload {0}'.format(self.name)
        if Bass.BASS_StreamFree(self.handle):
            self.name = ''

    def isActive(self):
        return Bass.BASS_ChannelIsActive(self.handle) == Bass.BASS_ACTIVE_PLAYING

    @Bass.DOWNLOADPROC
    def onDownload(self, foo, bar):
        print 'DownloadProc {0}, {1}'.format(foo, bar)