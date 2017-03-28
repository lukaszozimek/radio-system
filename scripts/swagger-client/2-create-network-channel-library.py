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
        
def rebelia_network():
    network = CoreNetworkPT()
    network.name = 'REBELIA'
    network.shortcut = 'RR'
    network.description = 'Radio Rebelia'
    return network

def rebelia_channel(net=CoreNetworkPT):
    channel = CoreChannelPT()
    channel.name = 'Radio Rebelia'
    channel.shortcut = 'CHA'
    channel.description = 'Radio Rebelia'
    channel.network_id = net.id
    return channel

def rebelia_library(net=CoreNetworkPT, cha=CoreChannelPT):
    library = LibraryPT()

    library.counter = 0
    library.counter_type = 'CT_COUNTER'
    library.library_type = 'OT_AUDIO'
    library.index_length = 6
    library.shortcut = 'LIB'
    library.prefix = 'L'
    library.name = 'Library'
    library.description = 'Sample Library'
    library.network_id = net.id
    library.channels = [cha]

    return library

try: 
    # creating network
    print 'Creating network'
    network = net.create_network_using_post_using_post(rebelia_network())
    log('Network created', network)
except ApiException as e:
    print 'Network creation failed:\n%s\n' % e

try: 
    # creating channel
    print 'Creating channel'
    channel = cha.create_channel_using_post_using_post(network.shortcut, rebelia_channel(net=network))
    log('Channel created', channel)
except ApiException as e:
    print 'Channel creation failed:\n%s\n' % e

try: 
    # creating library
    print 'Creating library'
    library = lib.create_library_using_post_using_post(network.shortcut, rebelia_library(net=network, cha=channel))
    log('Library created', library)
except ApiException as e:
    print 'Library creation failed:\n%s\n' % e

print 'Bye...'