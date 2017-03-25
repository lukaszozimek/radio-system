import __future__
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
the_client = swagger_client.ApiClient(host='http://localhost:8080', username='admin', password='admin') 
api_instance = swagger_client.NETWORKApi(api_client=the_client)

try: 
    #get all networks
    api_instance.delete_network_using_delete_using_delete('SAM666')
except ApiException as e:
    print("Exception when calling NETWORKApi->delete_network_using_delete_using_delete: %s\n" % e)