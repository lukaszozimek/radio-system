import swagger_client
from swagger_client import CoreNetworkPT
from swagger_client import CoreChannelPT
from swagger_client import LibraryPT
from swagger_client import LibItemPT
from swagger_client.rest import ApiException
from swagger_client.json_utils import log
from pprint import pprint

client = swagger_client.ApiClient(username='admin', password='admin') 
net = swagger_client.NETWORKApi(api_client=client)
cha = swagger_client.CHANNELApi(api_client=client)
lib = swagger_client.LIBRARYApi(api_client=client)
cor = swagger_client.COREApi(api_client=client)

def wipe(network_shortcut):
    network = net.get_network_using_get_using_get(network_shortcut)
    if network is None:
        return

    print 'Wiping network {0}({1}):'.format(network.name, network.shortcut)

    try:
        channels = cha.get_all_channels_using_get_using_get(network_shortcut)
        for channel in channels:
            print '\t-wiping channel {0}({1})...'.format(channel.name, channel.shortcut)
            cha.delete_channel_using_delete_using_delete(network_shortcut, channel.shortcut)
    except ApiException as e:
        print '\t-wiping channel is failed...'

    try:
        libraries = lib.get_all_libraries_using_get_using_get(network_shortcut)
        for library in libraries:
            print '\t-wiping library {0}({1})...'.format(library.name, library.shortcut)
            lib.delete_library_using_delete_using_delete(network_shortcut, library.shortcut)
    except ApiException as e:
        print '\t-wiping libraries is failed...'

    net.delete_network_using_delete_using_delete(network_shortcut)
        
def rebelia_network():
    network = CoreNetworkPT()
    network.name = 'REBELIA'
    network.shortcut = 'RR'
    network.description = 'Radio Rebelia'
    return network

def rebelia_channel(net=CoreNetworkPT):
    channel = CoreChannelPT()
    channel.name = 'Radio Rebelia'
    channel.shortcut = 'RR'
    channel.description = 'Radio Rebelia'
    channel.network_id = net.id
    return channel

def rebelia_library(net=CoreNetworkPT, cha=CoreChannelPT):
    library = LibraryPT()

    library.counter = 0
    library.counter_type = 'CT_COUNTER'
    library.library_type = 'OT_AUDIO'
    library.index_length = 6
    library.shortcut = 'MUS'
    library.prefix = 'M'
    library.name = 'Muzyka'
    library.description = 'Rebelia Library'
    library.network_id = net.id
    library.channels = [cha]

    return library
"""
try:
    # wiping network
    wipe('RR')
except ApiException as e:
    print 'Nothing to wipe...'
"""

"""
try: 
    # creating network
    print 'Creating network'
    network = net.create_network_using_post_using_post(rebelia_network())
    log('Network created', network)
except ApiException as e:
    print 'Network creation failed:\n%s\n' % e
"""
def test_network():
    try:
        network = net.get_network_using_get_using_get('test')
        return network
    except ApiException as e:
        print 'Test network not found' % e 
        return None       

"""
try: 
    # creating channel
    print 'Creating channel'
    channel = cha.create_channel_using_post_using_post(network.shortcut, rebelia_channel(net=network))
    log('Channel created', channel)
except ApiException as e:
    print 'Channel creation failed:\n%s\n' % e
"""

def test_channel():
    try:
        channel = cha.get_channel_using_get_using_get('test', 'tes')
        return channel
    except ApiException as e:
        print 'Test channel not found' % e
        return None        

"""
try: 
    # creating library
    print 'Creating library'
    library = lib.create_library_using_post_using_post(network.shortcut, rebelia_library(net=network, cha=channel))
    log('Library created', library)
except ApiException as e:
    print 'Library creation failed:\n%s\n' % e
"""

def test_library():
    try:
        library = cor.get_library_using_get_using_get('test', 'TES')
        return library
    except ApiException as e:
        print 'Test library not found' % e     
        return None  

"""
try: 
    # uploading file
    print 'Uploading file'
    item = lib.upload_items_by_networ_shortcut_and_library_prefix_using_post(network.shortcut, library.shortcut, '/Users/gk/Desktop/test.mp3')
    print 'File uploaded: {0}'.format(item)
except ApiException as e:
    print 'File upload failed:\n%s\n' % e
"""

nnn = test_network()
ccc = test_channel()
lll = test_library()

print 'Bye...'