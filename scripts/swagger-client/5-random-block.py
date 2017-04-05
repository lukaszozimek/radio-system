import os
import swagger_client
from swagger_client.rest import ApiException
from swagger_client.json_utils import log

client = swagger_client.ApiClient(username='admin', password='admin') 
lib = swagger_client.LIBRARYApi(api_client=client)
    
try: 
    print 'Getting random block...'
    block = lib.get_random_block_from_selected_library_using_get('test', 'tes', size=5)
    print 'Block contents: {0}'.format(block)
except ApiException as e:
    print 'Getting random block failed:\n%s\n' % e

print 'Bye...'