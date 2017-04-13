import __future__
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

class Auth:
    def __init__(self, username, password):
        print 'Initializing Auth \'{0}\''.format(username)
        self.userApi = swagger_client.UserjwtcontrollerApi()
        self.login_vm = swagger_client.LoginVM()
        self.login_vm.username = username
        self.login_vm.password = password
    
    def getJWT(self):
        try: 
            response = self.userApi.authorize_using_post(self.login_vm)
            return response['id_token']
        except ApiException as e:
            return 'Error occired creating JWT token...'