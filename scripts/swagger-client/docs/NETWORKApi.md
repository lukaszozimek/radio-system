# swagger_client.NETWORKApi

All URIs are relative to *https://localhost:8080/*

Method | HTTP request | Description
------------- | ------------- | -------------
[**create_network_using_post_using_post**](NETWORKApi.md#create_network_using_post_using_post) | **POST** /api/network | createNetwork
[**delete_network_using_delete_using_delete**](NETWORKApi.md#delete_network_using_delete_using_delete) | **DELETE** /api/network/{networkShortcut} | deleteNetwork
[**get_all_networks_using_get_using_get**](NETWORKApi.md#get_all_networks_using_get_using_get) | **GET** /api/network | getAllNetworks
[**get_network_using_get_using_get**](NETWORKApi.md#get_network_using_get_using_get) | **GET** /api/network/{networkShortcut} | getNetwork
[**update_network_using_put_using_put**](NETWORKApi.md#update_network_using_put_using_put) | **PUT** /api/network | updateNetwork


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
api_instance = swagger_client.NETWORKApi()
network = swagger_client.CoreNetworkPT() # CoreNetworkPT | network

try: 
    # createNetwork
    api_response = api_instance.create_network_using_post_using_post(network)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling NETWORKApi->create_network_using_post_using_post: %s\n" % e)
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
api_instance = swagger_client.NETWORKApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut

try: 
    # deleteNetwork
    api_instance.delete_network_using_delete_using_delete(network_shortcut)
except ApiException as e:
    print("Exception when calling NETWORKApi->delete_network_using_delete_using_delete: %s\n" % e)
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
api_instance = swagger_client.NETWORKApi()

try: 
    # getAllNetworks
    api_response = api_instance.get_all_networks_using_get_using_get()
    pprint(api_response)
except ApiException as e:
    print("Exception when calling NETWORKApi->get_all_networks_using_get_using_get: %s\n" % e)
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
api_instance = swagger_client.NETWORKApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut

try: 
    # getNetwork
    api_response = api_instance.get_network_using_get_using_get(network_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling NETWORKApi->get_network_using_get_using_get: %s\n" % e)
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
api_instance = swagger_client.NETWORKApi()
network = swagger_client.CoreNetworkPT() # CoreNetworkPT | network

try: 
    # updateNetwork
    api_response = api_instance.update_network_using_put_using_put(network)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling NETWORKApi->update_network_using_put_using_put: %s\n" % e)
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

