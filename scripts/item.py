from utils import extract

class MediaItem:
    
    def __init__(self, emission):
        item = emission.media_item
        self.network = emission.network
        self.library = item.library.shortcut
        self.idx = item.idx
        self.name = extract(item.name)
        self.markers = {} 
        for marker in item.markers: 
            self.markers[extract(marker.name)] = marker.start_time     

    def url(self, hostname, jwt):
        authHeader = 'Authorization: Bearer {0}'.format(jwt) 
        return '{0}/api/network/{1}/library/{2}/item/{3}/stream\r\n{4}'.format(hostname, self.network, self.library, self.idx, authHeader)