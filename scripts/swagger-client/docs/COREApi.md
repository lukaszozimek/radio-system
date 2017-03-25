# swagger_client.COREApi

All URIs are relative to *https://localhost:8080/*

Method | HTTP request | Description
------------- | ------------- | -------------
[**create_channel_using_post_using_post**](COREApi.md#create_channel_using_post_using_post) | **POST** /api/network/{networkShortcut}/channel | createChannel
[**create_library_using_post_using_post**](COREApi.md#create_library_using_post_using_post) | **POST** /api/network/{networkShortcut}/library | createLibrary
[**create_marker_configuration_using_post_using_post**](COREApi.md#create_marker_configuration_using_post_using_post) | **POST** /api/network/{networkShortcut}/configuration/library/marker | createMarkerConfiguration
[**create_network_using_post_using_post**](COREApi.md#create_network_using_post_using_post) | **POST** /api/network | createNetwork
[**delete_channel_using_delete_using_delete**](COREApi.md#delete_channel_using_delete_using_delete) | **DELETE** /api/network/{networkShortcut}/channel/{channelShortcut} | deleteChannel
[**delete_library_using_delete_using_delete**](COREApi.md#delete_library_using_delete_using_delete) | **DELETE** /api/network/{networkShortcut}/library/{libraryPrefix} | deleteLibrary
[**delete_marker_configuration_using_delete_using_delete**](COREApi.md#delete_marker_configuration_using_delete_using_delete) | **DELETE** /api/network/{networkShortcut}/configuration/library/marker/{id} | deleteMarkerConfiguration
[**delete_network_using_delete_using_delete**](COREApi.md#delete_network_using_delete_using_delete) | **DELETE** /api/network/{networkShortcut} | deleteNetwork
[**get_all_channels_using_get_using_get**](COREApi.md#get_all_channels_using_get_using_get) | **GET** /api/network/{networkShortcut}/channel | getAllChannels
[**get_all_libraries_using_get_using_get**](COREApi.md#get_all_libraries_using_get_using_get) | **GET** /api/network/{networkShortcut}/library | getAllLibraries
[**get_all_marker_configuration_using_get_using_get**](COREApi.md#get_all_marker_configuration_using_get_using_get) | **GET** /api/network/{networkShortcut}/configuration/library/marker | getAllMarkerConfiguration
[**get_all_networks_using_get_using_get**](COREApi.md#get_all_networks_using_get_using_get) | **GET** /api/network | getAllNetworks
[**get_channel_using_get_using_get**](COREApi.md#get_channel_using_get_using_get) | **GET** /api/network/{networkShortcut}/channel/{channelShortcut} | getChannel
[**get_library_using_get_using_get**](COREApi.md#get_library_using_get_using_get) | **GET** /api/network/{networkShortcut}/library/{libraryPrefix} | getLibrary
[**get_marker_configuration_using_get_using_get**](COREApi.md#get_marker_configuration_using_get_using_get) | **GET** /api/network/{networkShortcut}/configuration/library/marker/{id} | getMarkerConfiguration
[**get_network_using_get_using_get**](COREApi.md#get_network_using_get_using_get) | **GET** /api/network/{networkShortcut} | getNetwork
[**update_channel_using_put_using_put**](COREApi.md#update_channel_using_put_using_put) | **PUT** /api/network/{networkShortcut}/channel | updateChannel
[**update_library_using_put_using_put**](COREApi.md#update_library_using_put_using_put) | **PUT** /api/network/{networkShortcut}/library | updateLibrary
[**update_marker_configuration_using_put_using_put**](COREApi.md#update_marker_configuration_using_put_using_put) | **PUT** /api/network/{networkShortcut}/configuration/library/marker | updateMarkerConfiguration
[**update_network_using_put_using_put**](COREApi.md#update_network_using_put_using_put) | **PUT** /api/network | updateNetwork


# **create_channel_using_post_using_post**
> CoreChannelPT create_channel_using_post_using_post(network_shortcut, channel_dto)

createChannel

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.COREApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
channel_dto = swagger_client.CoreChannelPT() # CoreChannelPT | channelDTO

try: 
    # createChannel
    api_response = api_instance.create_channel_using_post_using_post(network_shortcut, channel_dto)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling COREApi->create_channel_using_post_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **channel_dto** | [**CoreChannelPT**](CoreChannelPT.md)| channelDTO | 

### Return type

[**CoreChannelPT**](CoreChannelPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **create_library_using_post_using_post**
> LibraryPT create_library_using_post_using_post(network_shortcut, library)

createLibrary

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.COREApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
library = swagger_client.LibraryPT() # LibraryPT | library

try: 
    # createLibrary
    api_response = api_instance.create_library_using_post_using_post(network_shortcut, library)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling COREApi->create_library_using_post_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **library** | [**LibraryPT**](LibraryPT.md)| library | 

### Return type

[**LibraryPT**](LibraryPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **create_marker_configuration_using_post_using_post**
> ConfMarkerConfigurationPT create_marker_configuration_using_post_using_post(network_shortcut, marker_configuration_pt)

createMarkerConfiguration

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.COREApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
marker_configuration_pt = swagger_client.ConfMarkerConfigurationPT() # ConfMarkerConfigurationPT | markerConfigurationPT

try: 
    # createMarkerConfiguration
    api_response = api_instance.create_marker_configuration_using_post_using_post(network_shortcut, marker_configuration_pt)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling COREApi->create_marker_configuration_using_post_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **marker_configuration_pt** | [**ConfMarkerConfigurationPT**](ConfMarkerConfigurationPT.md)| markerConfigurationPT | 

### Return type

[**ConfMarkerConfigurationPT**](ConfMarkerConfigurationPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **create_network_using_post_using_post**
> CoreNetworkPT create_network_using_post_using_post(network)

createNetwork

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.COREApi()
network = swagger_client.CoreNetworkPT() # CoreNetworkPT | network

try: 
    # createNetwork
    api_response = api_instance.create_network_using_post_using_post(network)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling COREApi->create_network_using_post_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network** | [**CoreNetworkPT**](CoreNetworkPT.md)| network | 

### Return type

[**CoreNetworkPT**](CoreNetworkPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **delete_channel_using_delete_using_delete**
> delete_channel_using_delete_using_delete(network_shortcut, channel_shortcut)

deleteChannel

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.COREApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
channel_shortcut = 'channel_shortcut_example' # str | channelShortcut

try: 
    # deleteChannel
    api_instance.delete_channel_using_delete_using_delete(network_shortcut, channel_shortcut)
except ApiException as e:
    print("Exception when calling COREApi->delete_channel_using_delete_using_delete: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **channel_shortcut** | **str**| channelShortcut | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **delete_library_using_delete_using_delete**
> delete_library_using_delete_using_delete(network_shortcut, library_prefix)

deleteLibrary

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.COREApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
library_prefix = 'library_prefix_example' # str | libraryPrefix

try: 
    # deleteLibrary
    api_instance.delete_library_using_delete_using_delete(network_shortcut, library_prefix)
except ApiException as e:
    print("Exception when calling COREApi->delete_library_using_delete_using_delete: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **library_prefix** | **str**| libraryPrefix | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **delete_marker_configuration_using_delete_using_delete**
> delete_marker_configuration_using_delete_using_delete(network_shortcut, id)

deleteMarkerConfiguration

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.COREApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
id = 'id_example' # str | markerName

try: 
    # deleteMarkerConfiguration
    api_instance.delete_marker_configuration_using_delete_using_delete(network_shortcut, id)
except ApiException as e:
    print("Exception when calling COREApi->delete_marker_configuration_using_delete_using_delete: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **id** | **str**| markerName | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **delete_network_using_delete_using_delete**
> delete_network_using_delete_using_delete(network_shortcut)

deleteNetwork

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.COREApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut

try: 
    # deleteNetwork
    api_instance.delete_network_using_delete_using_delete(network_shortcut)
except ApiException as e:
    print("Exception when calling COREApi->delete_network_using_delete_using_delete: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_channels_using_get_using_get**
> CoreChannelPT get_all_channels_using_get_using_get(network_shortcut)

getAllChannels

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.COREApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut

try: 
    # getAllChannels
    api_response = api_instance.get_all_channels_using_get_using_get(network_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling COREApi->get_all_channels_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 

### Return type

[**CoreChannelPT**](CoreChannelPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_libraries_using_get_using_get**
> LibraryPT get_all_libraries_using_get_using_get(network_shortcut)

getAllLibraries

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.COREApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut

try: 
    # getAllLibraries
    api_response = api_instance.get_all_libraries_using_get_using_get(network_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling COREApi->get_all_libraries_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 

### Return type

[**LibraryPT**](LibraryPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_marker_configuration_using_get_using_get**
> ConfMarkerConfigurationPT get_all_marker_configuration_using_get_using_get(network_shortcut)

getAllMarkerConfiguration

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.COREApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut

try: 
    # getAllMarkerConfiguration
    api_response = api_instance.get_all_marker_configuration_using_get_using_get(network_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling COREApi->get_all_marker_configuration_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 

### Return type

[**ConfMarkerConfigurationPT**](ConfMarkerConfigurationPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_networks_using_get_using_get**
> CoreNetworkPT get_all_networks_using_get_using_get()

getAllNetworks

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.COREApi()

try: 
    # getAllNetworks
    api_response = api_instance.get_all_networks_using_get_using_get()
    pprint(api_response)
except ApiException as e:
    print("Exception when calling COREApi->get_all_networks_using_get_using_get: %s\n" % e)
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**CoreNetworkPT**](CoreNetworkPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_channel_using_get_using_get**
> CoreChannelPT get_channel_using_get_using_get(network_shortcut, channel_shortcut)

getChannel

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.COREApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
channel_shortcut = 'channel_shortcut_example' # str | channelShortcut

try: 
    # getChannel
    api_response = api_instance.get_channel_using_get_using_get(network_shortcut, channel_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling COREApi->get_channel_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **channel_shortcut** | **str**| channelShortcut | 

### Return type

[**CoreChannelPT**](CoreChannelPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_library_using_get_using_get**
> LibraryPT get_library_using_get_using_get(network_shortcut, library_prefix)

getLibrary

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.COREApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
library_prefix = 'library_prefix_example' # str | libraryPrefix

try: 
    # getLibrary
    api_response = api_instance.get_library_using_get_using_get(network_shortcut, library_prefix)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling COREApi->get_library_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **library_prefix** | **str**| libraryPrefix | 

### Return type

[**LibraryPT**](LibraryPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_marker_configuration_using_get_using_get**
> ConfMarkerConfigurationPT get_marker_configuration_using_get_using_get(network_shortcut, id)

getMarkerConfiguration

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.COREApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
id = 'id_example' # str | markerName

try: 
    # getMarkerConfiguration
    api_response = api_instance.get_marker_configuration_using_get_using_get(network_shortcut, id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling COREApi->get_marker_configuration_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **id** | **str**| markerName | 

### Return type

[**ConfMarkerConfigurationPT**](ConfMarkerConfigurationPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_network_using_get_using_get**
> CoreNetworkPT get_network_using_get_using_get(network_shortcut)

getNetwork

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.COREApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut

try: 
    # getNetwork
    api_response = api_instance.get_network_using_get_using_get(network_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling COREApi->get_network_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 

### Return type

[**CoreNetworkPT**](CoreNetworkPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **update_channel_using_put_using_put**
> CoreChannelPT update_channel_using_put_using_put(network_shortcut, channel_dto)

updateChannel

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.COREApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
channel_dto = swagger_client.CoreChannelPT() # CoreChannelPT | channelDTO

try: 
    # updateChannel
    api_response = api_instance.update_channel_using_put_using_put(network_shortcut, channel_dto)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling COREApi->update_channel_using_put_using_put: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **channel_dto** | [**CoreChannelPT**](CoreChannelPT.md)| channelDTO | 

### Return type

[**CoreChannelPT**](CoreChannelPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **update_library_using_put_using_put**
> LibraryPT update_library_using_put_using_put(network_shortcut, library)

updateLibrary

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.COREApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
library = swagger_client.LibraryPT() # LibraryPT | library

try: 
    # updateLibrary
    api_response = api_instance.update_library_using_put_using_put(network_shortcut, library)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling COREApi->update_library_using_put_using_put: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **library** | [**LibraryPT**](LibraryPT.md)| library | 

### Return type

[**LibraryPT**](LibraryPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **update_marker_configuration_using_put_using_put**
> ConfMarkerConfigurationPT update_marker_configuration_using_put_using_put(network_shortcut, marker_configuration_dto)

updateMarkerConfiguration

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.COREApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
marker_configuration_dto = swagger_client.ConfMarkerConfigurationPT() # ConfMarkerConfigurationPT | markerConfigurationDTO

try: 
    # updateMarkerConfiguration
    api_response = api_instance.update_marker_configuration_using_put_using_put(network_shortcut, marker_configuration_dto)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling COREApi->update_marker_configuration_using_put_using_put: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **marker_configuration_dto** | [**ConfMarkerConfigurationPT**](ConfMarkerConfigurationPT.md)| markerConfigurationDTO | 

### Return type

[**ConfMarkerConfigurationPT**](ConfMarkerConfigurationPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **update_network_using_put_using_put**
> CoreNetworkPT update_network_using_put_using_put(network)

updateNetwork

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.COREApi()
network = swagger_client.CoreNetworkPT() # CoreNetworkPT | network

try: 
    # updateNetwork
    api_response = api_instance.update_network_using_put_using_put(network)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling COREApi->update_network_using_put_using_put: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network** | [**CoreNetworkPT**](CoreNetworkPT.md)| network | 

### Return type

[**CoreNetworkPT**](CoreNetworkPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

