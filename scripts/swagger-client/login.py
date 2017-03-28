import __future__
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.UserjwtcontrollerApi()
login_vm = swagger_client.LoginVM()
login_vm.username = 'admin'
login_vm.password = 'admin'

try: 
    # getAllNetworks
    api_response = api_instance.authorize_using_post(login_vm)
    print 'JWT token is: ' + api_response['id_token']
except ApiException as e:
    print("Exception when calling UserjwtcontrollerApi->authorize_using_post: %s\n" % e)