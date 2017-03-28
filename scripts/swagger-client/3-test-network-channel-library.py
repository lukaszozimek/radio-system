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

def test_network():
    try:
        network = net.get_network_using_get_using_get('RR')
        return network
    except ApiException as e:
        print 'Test network not found' % e 
        return None       

def test_channel():
    try:
        channel = cha.get_channel_using_get_using_get('RR', 'CHA')
        return channel
    except ApiException as e:
        print 'Test channel not found' % e
        return None        

def test_library():
    try:
        library = cor.get_library_using_get_using_get('RR', 'LIB')
        return library
    except ApiException as e:
        print 'Test library not found' % e     
        return None  

nnn = test_network()
ccc = test_channel()
lll = test_library()

print 'Bye...'