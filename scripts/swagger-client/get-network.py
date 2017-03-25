import __future__
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class

the_client = swagger_client.ApiClient(username='admin', password='admin') 
api_instance = swagger_client.NETWORKApi(api_client=the_client)

try: 
    # get ONE Network
    api_response = api_instance.get_network_using_get_using_get('SAM666')
    pprint(api_response)
except ApiException as e:
    print("Exception when calling NETWORKApi->get_all_networks_using_get_using_get: %s\n" % e)