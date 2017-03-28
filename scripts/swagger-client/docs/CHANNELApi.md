# swagger_client.CHANNELApi

All URIs are relative to *https://localhost:8080/*

Method | HTTP request | Description
------------- | ------------- | -------------
[**create_channel_using_post_using_post**](CHANNELApi.md#create_channel_using_post_using_post) | **POST** /api/network/{networkShortcut}/channel | createChannel
[**delete_channel_using_delete_using_delete**](CHANNELApi.md#delete_channel_using_delete_using_delete) | **DELETE** /api/network/{networkShortcut}/channel/{channelShortcut} | deleteChannel
[**get_all_channels_using_get_using_get**](CHANNELApi.md#get_all_channels_using_get_using_get) | **GET** /api/network/{networkShortcut}/channel | getAllChannels
[**get_channel_using_get_using_get**](CHANNELApi.md#get_channel_using_get_using_get) | **GET** /api/network/{networkShortcut}/channel/{channelShortcut} | getChannel
[**update_channel_using_put_using_put**](CHANNELApi.md#update_channel_using_put_using_put) | **PUT** /api/network/{networkShortcut}/channel | updateChannel


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
api_instance = swagger_client.CHANNELApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
channel_dto = swagger_client.CoreChannelPT() # CoreChannelPT | channelDTO

try: 
    # createChannel
    api_response = api_instance.create_channel_using_post_using_post(network_shortcut, channel_dto)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling CHANNELApi->create_channel_using_post_using_post: %s\n" % e)
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
api_instance = swagger_client.CHANNELApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
channel_shortcut = 'channel_shortcut_example' # str | channelShortcut

try: 
    # deleteChannel
    api_instance.delete_channel_using_delete_using_delete(network_shortcut, channel_shortcut)
except ApiException as e:
    print("Exception when calling CHANNELApi->delete_channel_using_delete_using_delete: %s\n" % e)
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
api_instance = swagger_client.CHANNELApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut

try: 
    # getAllChannels
    api_response = api_instance.get_all_channels_using_get_using_get(network_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling CHANNELApi->get_all_channels_using_get_using_get: %s\n" % e)
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
api_instance = swagger_client.CHANNELApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
channel_shortcut = 'channel_shortcut_example' # str | channelShortcut

try: 
    # getChannel
    api_response = api_instance.get_channel_using_get_using_get(network_shortcut, channel_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling CHANNELApi->get_channel_using_get_using_get: %s\n" % e)
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
api_instance = swagger_client.CHANNELApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
channel_dto = swagger_client.CoreChannelPT() # CoreChannelPT | channelDTO

try: 
    # updateChannel
    api_response = api_instance.update_channel_using_put_using_put(network_shortcut, channel_dto)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling CHANNELApi->update_channel_using_put_using_put: %s\n" % e)
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

