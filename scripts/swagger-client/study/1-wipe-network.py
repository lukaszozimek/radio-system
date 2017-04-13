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

def wipe():

    network_shortcut = 'RR'

    network = net.get_network_using_get_using_get(network_shortcut)
    if network is None:
        return

    print 'Wiping network {0}({1}):'.format(network.name, network_shortcut)

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


print 'Wiping has finished...'