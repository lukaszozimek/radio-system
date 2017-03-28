import os
import swagger_client
from swagger_client import CoreNetworkPT
from swagger_client import CoreChannelPT
from swagger_client import LibraryPT
from swagger_client import LibItemPT
from swagger_client.rest import ApiException
from swagger_client.json_utils import log

client = swagger_client.ApiClient(username='admin', password='admin') 
net = swagger_client.NETWORKApi(api_client=client)
cha = swagger_client.CHANNELApi(api_client=client)
lib = swagger_client.LIBRARYApi(api_client=client)
cor = swagger_client.COREApi(api_client=client)

def get_filepaths(directory):
    file_paths = []  # List which will store all of the full filepaths.

    # Walk the tree.
    for root, directories, files in os.walk(directory):
        for filename in files:
            # Join the two strings in order to form the full filepath.
            filepath = os.path.join(root, filename)
            file_paths.append(filepath)  # Add it to the list.

    return file_paths  # Self-explanatory.

# uploading file
print 'Uploading files'

files = get_filepaths('/Users/gk/RC_AvVol1/HOO')
    
for file in files:
    try: 
        print 'Uploading file {0}...'.format(file)
        item = lib.upload_items_by_networ_shortcut_and_library_prefix_using_post('RR', 'LIB', file)
        print 'File uploaded: {0}'.format(item)
    except ApiException as e:
        print 'File upload failed:\n%s\n' % e

print 'Bye...'