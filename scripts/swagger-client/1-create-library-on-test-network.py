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

def get_network(network_shortcut):
    try:
        network = net.get_network_using_get_using_get(network_shortcut)
        if network is None:
            print 'Network \'{}\' not found...'.format(network.shortcut)
            return None
        else:
            print 'Found network \'{}\'...'.format(network.shortcut)
            return network
    except ApiException as e:
        print 'Network \'{}\' not found...'.format(network_shortcut)
        return None

def get_channel(network_shortcut, channel_shortcut):
    try:
        channel = cha.get_channel_using_get_using_get(network_shortcut, channel_shortcut)
        if channel is None:
            print 'Channel \'{}\' not found...'.format(channel.shortcut)
            return None
        else:
            print 'Found channel \'{}\'...'.format(channel.shortcut)
            return channel
    except ApiException as e:
        print 'Channel \'{}\' not found...'.format(channel_shortcut)
        return None

def get_library(network_shortcut, library_shortcut):
    try:
        library = lib.get_library_using_get_using_get(network_shortcut, library_shortcut)
        if library is None:
            print 'Library \'{}\' not found...'.format(library.shortcut)
            return None
        else:
            print 'Found library \'{}\'...'.format(library.shortcut)
            return library
    except ApiException as e:
        print 'Library \'{}\' not found...'.format(library_shortcut)
        return None

def library_factory(network_id, prefix, shortcut, name, description=''):
    
    library = LibraryPT()
    library.counter = 0
    library.counter_type = 'CT_COUNTER'
    library.library_type = 'OT_AUDIO'
    library.index_length = 6
    library.shortcut = shortcut
    library.prefix = prefix
    library.name = name
    library.description = description
    library.network_id = network_id
    library.channels = []

    return library

def create_library(network_shortcut, prefix, shortcut, name):
    try:
        library = get_library(network_shortcut, shortcut)
        if library is not None:
            print 'Library \'{}\' already existing...'.format(shortcut)

        network = get_network(network_shortcut)
        if network is None:
            print 'Network \'{}\' not found...'.format(network_shortcut)
            return None

        library = library_factory(network.id, prefix, shortcut, name)
        
        library = lib.create_library_using_post_using_post(network_shortcut, library)

        if library is not None:
            print 'Library \'{}\' created...'.format(shortcut)
        else:
            print 'Library creation \'{}\' failed...'.format(shortcut)
        
        return library
    except ApiException as e:
        print 'Library creation \'{}\' failed...'.format(shortcut)
        return None

get_library('test', 'rr')
#create_library('test', 'r', 'rr', 'Rebelia')