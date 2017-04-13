import os
import swagger_client
from swagger_client.rest import ApiException
from swagger_client.json_utils import log

client = swagger_client.ApiClient(username='admin', password='admin') 
lib = swagger_client.LIBRARYApi(api_client=client)
    
def extract(input):
    return input.encode('ascii', 'ignore').encode('utf-8').strip()

try: 
    print 'Getting random block...'
    block = lib.get_random_block_from_selected_library_using_get('test', 'com', size=3)
    for emission in block.emissions:
        item = emission.media_item
        url = '/{0}/{1}/{2}'.format('test', 'com', item.idx)
        print '{0}: {1}'.format(url, extract(item.name))
        markers = {}
        for marker in item.markers: 
            markers[extract(marker.name)] = marker.start_time
        print '   markers: {0}'.format(markers)
except ApiException as e:
    print 'Getting random block failed:\n%s\n' % e

print 'Bye...'