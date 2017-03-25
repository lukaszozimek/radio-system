# swagger_client.SCHEDULERApi

All URIs are relative to *https://localhost:8080/*

Method | HTTP request | Description
------------- | ------------- | -------------
[**creat_scheduler_playlist_for_channel_using_post_using_post**](SCHEDULERApi.md#creat_scheduler_playlist_for_channel_using_post_using_post) | **POST** /api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/playlist | createSchedulerForChannelPlaylist
[**creat_scheduler_templates_for_channel_using_post_using_post**](SCHEDULERApi.md#creat_scheduler_templates_for_channel_using_post_using_post) | **POST** /api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/templates | createSchedulerTemplatesForChannel
[**delete_scheduler_playlist_for_channel_using_delete_using_delete**](SCHEDULERApi.md#delete_scheduler_playlist_for_channel_using_delete_using_delete) | **DELETE** /api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/playlist/{date} | deleteSchedulerPlaylistForChannel
[**delete_scheduler_template_for_channel_using_delete_using_delete**](SCHEDULERApi.md#delete_scheduler_template_for_channel_using_delete_using_delete) | **DELETE** /api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/templates/{shortName} | deleteSchedulerTemplateForChannel
[**get_all_scheduler_playlist_for_channel_using_get_using_get**](SCHEDULERApi.md#get_all_scheduler_playlist_for_channel_using_get_using_get) | **GET** /api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/playlist | getAllSchedulerPlaylistForChannel
[**get_all_scheduler_playlist_using_get_using_get**](SCHEDULERApi.md#get_all_scheduler_playlist_using_get_using_get) | **GET** /api/network/{networkShortcut}/scheduler/playlist | getAllSchedulerPlaylist
[**get_all_scheduler_templates_for_channel_using_get_using_get**](SCHEDULERApi.md#get_all_scheduler_templates_for_channel_using_get_using_get) | **GET** /api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/templates | getAllSchedulerTemplatesForChannel
[**get_all_scheduler_templates_using_get_using_get**](SCHEDULERApi.md#get_all_scheduler_templates_using_get_using_get) | **GET** /api/network/{networkShortcut}/scheduler/templates | getAllSchedulerTemplates
[**get_scheduler_playlist_for_channel_using_get_using_get**](SCHEDULERApi.md#get_scheduler_playlist_for_channel_using_get_using_get) | **GET** /api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/playlist/{date} | getSchedulerPlaylistForChannel
[**get_scheduler_template_for_channel_using_get_using_get**](SCHEDULERApi.md#get_scheduler_template_for_channel_using_get_using_get) | **GET** /api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/templates/{shortName} | getSchedulerTemplateForChannel
[**update_scheduler_playlis_for_channelt_using_put_using_put**](SCHEDULERApi.md#update_scheduler_playlis_for_channelt_using_put_using_put) | **PUT** /api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/playlist | updateSchedulerPlaylistForChannel
[**update_scheduler_templates_for_channel_using_put_using_put**](SCHEDULERApi.md#update_scheduler_templates_for_channel_using_put_using_put) | **PUT** /api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/templates | updateSchedulerTemplatesForChannel


# **creat_scheduler_playlist_for_channel_using_post_using_post**
> SchPlaylistPT creat_scheduler_playlist_for_channel_using_post_using_post(network_shortcut, channel_shortcut, playlist)

createSchedulerForChannelPlaylist

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.SCHEDULERApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
channel_shortcut = 'channel_shortcut_example' # str | channelShortcut
playlist = swagger_client.SchPlaylistPT() # SchPlaylistPT | schdeulerTemplate

try: 
    # createSchedulerForChannelPlaylist
    api_response = api_instance.creat_scheduler_playlist_for_channel_using_post_using_post(network_shortcut, channel_shortcut, playlist)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling SCHEDULERApi->creat_scheduler_playlist_for_channel_using_post_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **channel_shortcut** | **str**| channelShortcut | 
 **playlist** | [**SchPlaylistPT**](SchPlaylistPT.md)| schdeulerTemplate | 

### Return type

[**SchPlaylistPT**](SchPlaylistPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **creat_scheduler_templates_for_channel_using_post_using_post**
> SchTemplatePT creat_scheduler_templates_for_channel_using_post_using_post(network_shortcut, channel_shortcut, template)

createSchedulerTemplatesForChannel

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.SCHEDULERApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
channel_shortcut = 'channel_shortcut_example' # str | channelShortcut
template = swagger_client.SchTemplatePT() # SchTemplatePT | schdeulerTemplate

try: 
    # createSchedulerTemplatesForChannel
    api_response = api_instance.creat_scheduler_templates_for_channel_using_post_using_post(network_shortcut, channel_shortcut, template)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling SCHEDULERApi->creat_scheduler_templates_for_channel_using_post_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **channel_shortcut** | **str**| channelShortcut | 
 **template** | [**SchTemplatePT**](SchTemplatePT.md)| schdeulerTemplate | 

### Return type

[**SchTemplatePT**](SchTemplatePT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **delete_scheduler_playlist_for_channel_using_delete_using_delete**
> delete_scheduler_playlist_for_channel_using_delete_using_delete(network_shortcut, channel_shortcut, date)

deleteSchedulerPlaylistForChannel

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.SCHEDULERApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
channel_shortcut = 'channel_shortcut_example' # str | channelShortcut
date = 'date_example' # str | date

try: 
    # deleteSchedulerPlaylistForChannel
    api_instance.delete_scheduler_playlist_for_channel_using_delete_using_delete(network_shortcut, channel_shortcut, date)
except ApiException as e:
    print("Exception when calling SCHEDULERApi->delete_scheduler_playlist_for_channel_using_delete_using_delete: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **channel_shortcut** | **str**| channelShortcut | 
 **date** | **str**| date | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **delete_scheduler_template_for_channel_using_delete_using_delete**
> delete_scheduler_template_for_channel_using_delete_using_delete(network_shortcut, channel_shortcut, short_name)

deleteSchedulerTemplateForChannel

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.SCHEDULERApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
channel_shortcut = 'channel_shortcut_example' # str | channelShortcut
short_name = 'short_name_example' # str | shortName

try: 
    # deleteSchedulerTemplateForChannel
    api_instance.delete_scheduler_template_for_channel_using_delete_using_delete(network_shortcut, channel_shortcut, short_name)
except ApiException as e:
    print("Exception when calling SCHEDULERApi->delete_scheduler_template_for_channel_using_delete_using_delete: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **channel_shortcut** | **str**| channelShortcut | 
 **short_name** | **str**| shortName | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_scheduler_playlist_for_channel_using_get_using_get**
> SchPlaylistPT get_all_scheduler_playlist_for_channel_using_get_using_get(network_shortcut, channel_shortcut)

getAllSchedulerPlaylistForChannel

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.SCHEDULERApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
channel_shortcut = 'channel_shortcut_example' # str | channelShortcut

try: 
    # getAllSchedulerPlaylistForChannel
    api_response = api_instance.get_all_scheduler_playlist_for_channel_using_get_using_get(network_shortcut, channel_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling SCHEDULERApi->get_all_scheduler_playlist_for_channel_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **channel_shortcut** | **str**| channelShortcut | 

### Return type

[**SchPlaylistPT**](SchPlaylistPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_scheduler_playlist_using_get_using_get**
> SchPlaylistPT get_all_scheduler_playlist_using_get_using_get(network_shortcut)

getAllSchedulerPlaylist

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.SCHEDULERApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut

try: 
    # getAllSchedulerPlaylist
    api_response = api_instance.get_all_scheduler_playlist_using_get_using_get(network_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling SCHEDULERApi->get_all_scheduler_playlist_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 

### Return type

[**SchPlaylistPT**](SchPlaylistPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_scheduler_templates_for_channel_using_get_using_get**
> SchTemplatePT get_all_scheduler_templates_for_channel_using_get_using_get(network_shortcut, channel_shortcut)

getAllSchedulerTemplatesForChannel

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.SCHEDULERApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
channel_shortcut = 'channel_shortcut_example' # str | channelShortcut

try: 
    # getAllSchedulerTemplatesForChannel
    api_response = api_instance.get_all_scheduler_templates_for_channel_using_get_using_get(network_shortcut, channel_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling SCHEDULERApi->get_all_scheduler_templates_for_channel_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **channel_shortcut** | **str**| channelShortcut | 

### Return type

[**SchTemplatePT**](SchTemplatePT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_scheduler_templates_using_get_using_get**
> SchTemplatePT get_all_scheduler_templates_using_get_using_get(network_shortcut)

getAllSchedulerTemplates

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.SCHEDULERApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut

try: 
    # getAllSchedulerTemplates
    api_response = api_instance.get_all_scheduler_templates_using_get_using_get(network_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling SCHEDULERApi->get_all_scheduler_templates_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 

### Return type

[**SchTemplatePT**](SchTemplatePT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_scheduler_playlist_for_channel_using_get_using_get**
> SchPlaylistPT get_scheduler_playlist_for_channel_using_get_using_get(network_shortcut, channel_shortcut, date, random=random)

getSchedulerPlaylistForChannel

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.SCHEDULERApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
channel_shortcut = 'channel_shortcut_example' # str | channelShortcut
date = 'date_example' # str | date
random = true # bool | random (optional)

try: 
    # getSchedulerPlaylistForChannel
    api_response = api_instance.get_scheduler_playlist_for_channel_using_get_using_get(network_shortcut, channel_shortcut, date, random=random)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling SCHEDULERApi->get_scheduler_playlist_for_channel_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **channel_shortcut** | **str**| channelShortcut | 
 **date** | **str**| date | 
 **random** | **bool**| random | [optional] 

### Return type

[**SchPlaylistPT**](SchPlaylistPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_scheduler_template_for_channel_using_get_using_get**
> SchTemplatePT get_scheduler_template_for_channel_using_get_using_get(network_shortcut, channel_shortcut, short_name, random=random)

getSchedulerTemplateForChannel

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.SCHEDULERApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
channel_shortcut = 'channel_shortcut_example' # str | channelShortcut
short_name = 'short_name_example' # str | shortName
random = true # bool | random (optional)

try: 
    # getSchedulerTemplateForChannel
    api_response = api_instance.get_scheduler_template_for_channel_using_get_using_get(network_shortcut, channel_shortcut, short_name, random=random)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling SCHEDULERApi->get_scheduler_template_for_channel_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **channel_shortcut** | **str**| channelShortcut | 
 **short_name** | **str**| shortName | 
 **random** | **bool**| random | [optional] 

### Return type

[**SchTemplatePT**](SchTemplatePT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **update_scheduler_playlis_for_channelt_using_put_using_put**
> SchPlaylistPT update_scheduler_playlis_for_channelt_using_put_using_put(network_shortcut, channel_shortcut, playlist)

updateSchedulerPlaylistForChannel

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.SCHEDULERApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
channel_shortcut = 'channel_shortcut_example' # str | channelShortcut
playlist = swagger_client.SchPlaylistPT() # SchPlaylistPT | schdeulerTemplate

try: 
    # updateSchedulerPlaylistForChannel
    api_response = api_instance.update_scheduler_playlis_for_channelt_using_put_using_put(network_shortcut, channel_shortcut, playlist)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling SCHEDULERApi->update_scheduler_playlis_for_channelt_using_put_using_put: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **channel_shortcut** | **str**| channelShortcut | 
 **playlist** | [**SchPlaylistPT**](SchPlaylistPT.md)| schdeulerTemplate | 

### Return type

[**SchPlaylistPT**](SchPlaylistPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **update_scheduler_templates_for_channel_using_put_using_put**
> SchTemplatePT update_scheduler_templates_for_channel_using_put_using_put(network_shortcut, channel_shortcut, template)

updateSchedulerTemplatesForChannel

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.SCHEDULERApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
channel_shortcut = 'channel_shortcut_example' # str | channelShortcut
template = swagger_client.SchTemplatePT() # SchTemplatePT | schdeulerTemplate

try: 
    # updateSchedulerTemplatesForChannel
    api_response = api_instance.update_scheduler_templates_for_channel_using_put_using_put(network_shortcut, channel_shortcut, template)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling SCHEDULERApi->update_scheduler_templates_for_channel_using_put_using_put: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **channel_shortcut** | **str**| channelShortcut | 
 **template** | [**SchTemplatePT**](SchTemplatePT.md)| schdeulerTemplate | 

### Return type

[**SchTemplatePT**](SchTemplatePT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

