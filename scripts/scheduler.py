import os
import swagger_client
from swagger_client.rest import ApiException
from swagger_client.json_utils import log

class Scheduler:      
    def __init__(self, username, password):
        self.client = swagger_client.ApiClient(username=username, password=password) 
        self.lib = swagger_client.LIBRARYApi(api_client=self.client)

    def randomBlock(self, network, library, size):
        result = self.lib.get_random_block_from_selected_library_using_get(network, library, size=size)
        for emission in result.emissions:
            emission.network = network
        return result
