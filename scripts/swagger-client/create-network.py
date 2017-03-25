import __future__
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
the_client = swagger_client.ApiClient(username='admin', password='admin') 
api_instance = swagger_client.NETWORKApi(api_client=the_client)

try: 
    # createNetwork
    #for x in range(0, 100):
    x=666
    network = swagger_client.CoreNetworkPT() # CoreNetworkPT | network
    network.name = 'Sample Network ' + str(x)
    network.shortcut = 'SAM' + str(x)
    network.description = 'blah' + str(x)
    api_response = api_instance.create_network_using_post_using_post(network)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling NETWORKApi->create_network_using_post_using_post: %s\n" % e)