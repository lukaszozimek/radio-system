# swagger_client.LIBRARYApi

All URIs are relative to *https://localhost:8080/*

Method | HTTP request | Description
------------- | ------------- | -------------
[**create_library_using_post_using_post**](LIBRARYApi.md#create_library_using_post_using_post) | **POST** /api/network/{networkShortcut}/library | createLibrary
[**delete_item_by_networ_shortcut_and_librar_using_delete_using_delete**](LIBRARYApi.md#delete_item_by_networ_shortcut_and_librar_using_delete_using_delete) | **DELETE** /api/network/{networkShortcut}/library/{libraryPrefix}/item/{idx} | deleteItemByNetworShortcutAndLibrar
[**delete_library_using_delete_using_delete**](LIBRARYApi.md#delete_library_using_delete_using_delete) | **DELETE** /api/network/{networkShortcut}/library/{libraryPrefix} | deleteLibrary
[**get_all_items_by_networ_shortcut_and_library_prefix_using_get_using_get**](LIBRARYApi.md#get_all_items_by_networ_shortcut_and_library_prefix_using_get_using_get) | **GET** /api/network/{networkShortcut}/library/{libraryPrefix}/item | getAllItemsByNetworShortcutAndLibraryPrefix
[**get_all_libraries_using_get_using_get**](LIBRARYApi.md#get_all_libraries_using_get_using_get) | **GET** /api/network/{networkShortcut}/library | getAllLibraries
[**get_item_by_networ_shortcut_and_librar_using_get_using_get**](LIBRARYApi.md#get_item_by_networ_shortcut_and_librar_using_get_using_get) | **GET** /api/network/{networkShortcut}/library/{libraryPrefix}/item/{idx} | getItemByNetworShortcutAndLibrar
[**get_library_using_get_using_get**](LIBRARYApi.md#get_library_using_get_using_get) | **GET** /api/network/{networkShortcut}/library/{libraryPrefix} | getLibrary
[**stream_item_by_networ_shortcut_and_librar_using_get_using_get**](LIBRARYApi.md#stream_item_by_networ_shortcut_and_librar_using_get_using_get) | **GET** /api/network/{networkShortcut}/library/{libraryPrefix}/item/{idx}/stream | getItemStreamByNetworShortcutAndLibrar
[**update_item_by_networ_shortcut_and_library_prefix_using_put_using_put**](LIBRARYApi.md#update_item_by_networ_shortcut_and_library_prefix_using_put_using_put) | **PUT** /api/network/{networkShortcut}/library/{libraryPrefix}/item | updateItemByNetworShortcutAndLibraryPrefix
[**update_library_using_put_using_put**](LIBRARYApi.md#update_library_using_put_using_put) | **PUT** /api/network/{networkShortcut}/library | updateLibrary
[**upload_items_by_networ_shortcut_and_library_prefix_using_post**](LIBRARYApi.md#upload_items_by_networ_shortcut_and_library_prefix_using_post) | **POST** /api/network/{networkShortcut}/library/{libraryPrefix}/item | uploadItemsByNetworShortcutAndLibraryPrefix


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
api_instance = swagger_client.LIBRARYApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
library = swagger_client.LibraryPT() # LibraryPT | library

try: 
    # createLibrary
    api_response = api_instance.create_library_using_post_using_post(network_shortcut, library)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling LIBRARYApi->create_library_using_post_using_post: %s\n" % e)
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

# **delete_item_by_networ_shortcut_and_librar_using_delete_using_delete**
> delete_item_by_networ_shortcut_and_librar_using_delete_using_delete(network_shortcut, library_prefix, idx)

deleteItemByNetworShortcutAndLibrar

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.LIBRARYApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
library_prefix = 'library_prefix_example' # str | libraryPrefix
idx = 'idx_example' # str | idx

try: 
    # deleteItemByNetworShortcutAndLibrar
    api_instance.delete_item_by_networ_shortcut_and_librar_using_delete_using_delete(network_shortcut, library_prefix, idx)
except ApiException as e:
    print("Exception when calling LIBRARYApi->delete_item_by_networ_shortcut_and_librar_using_delete_using_delete: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **library_prefix** | **str**| libraryPrefix | 
 **idx** | **str**| idx | 

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
api_instance = swagger_client.LIBRARYApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
library_prefix = 'library_prefix_example' # str | libraryPrefix

try: 
    # deleteLibrary
    api_instance.delete_library_using_delete_using_delete(network_shortcut, library_prefix)
except ApiException as e:
    print("Exception when calling LIBRARYApi->delete_library_using_delete_using_delete: %s\n" % e)
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

# **get_all_items_by_networ_shortcut_and_library_prefix_using_get_using_get**
> LibItemPT get_all_items_by_networ_shortcut_and_library_prefix_using_get_using_get(network_shortcut, library_prefix)

getAllItemsByNetworShortcutAndLibraryPrefix

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.LIBRARYApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
library_prefix = 'library_prefix_example' # str | libraryPrefix

try: 
    # getAllItemsByNetworShortcutAndLibraryPrefix
    api_response = api_instance.get_all_items_by_networ_shortcut_and_library_prefix_using_get_using_get(network_shortcut, library_prefix)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling LIBRARYApi->get_all_items_by_networ_shortcut_and_library_prefix_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **library_prefix** | **str**| libraryPrefix | 

### Return type

[**LibItemPT**](LibItemPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: */*
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
api_instance = swagger_client.LIBRARYApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut

try: 
    # getAllLibraries
    api_response = api_instance.get_all_libraries_using_get_using_get(network_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling LIBRARYApi->get_all_libraries_using_get_using_get: %s\n" % e)
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

# **get_item_by_networ_shortcut_and_librar_using_get_using_get**
> LibItemPT get_item_by_networ_shortcut_and_librar_using_get_using_get(network_shortcut, library_prefix, idx)

getItemByNetworShortcutAndLibrar

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.LIBRARYApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
library_prefix = 'library_prefix_example' # str | libraryPrefix
idx = 'idx_example' # str | libraryPrefix

try: 
    # getItemByNetworShortcutAndLibrar
    api_response = api_instance.get_item_by_networ_shortcut_and_librar_using_get_using_get(network_shortcut, library_prefix, idx)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling LIBRARYApi->get_item_by_networ_shortcut_and_librar_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **library_prefix** | **str**| libraryPrefix | 
 **idx** | **str**| libraryPrefix | 

### Return type

[**LibItemPT**](LibItemPT.md)

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
api_instance = swagger_client.LIBRARYApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
library_prefix = 'library_prefix_example' # str | libraryPrefix

try: 
    # getLibrary
    api_response = api_instance.get_library_using_get_using_get(network_shortcut, library_prefix)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling LIBRARYApi->get_library_using_get_using_get: %s\n" % e)
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

# **stream_item_by_networ_shortcut_and_librar_using_get_using_get**
> LibResponseEntity stream_item_by_networ_shortcut_and_librar_using_get_using_get(network_shortcut, library_prefix, idx)

getItemStreamByNetworShortcutAndLibrar

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.LIBRARYApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
library_prefix = 'library_prefix_example' # str | libraryPrefix
idx = 'idx_example' # str | libraryPrefix

try: 
    # getItemStreamByNetworShortcutAndLibrar
    api_response = api_instance.stream_item_by_networ_shortcut_and_librar_using_get_using_get(network_shortcut, library_prefix, idx)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling LIBRARYApi->stream_item_by_networ_shortcut_and_librar_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **library_prefix** | **str**| libraryPrefix | 
 **idx** | **str**| libraryPrefix | 

### Return type

[**LibResponseEntity**](LibResponseEntity.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **update_item_by_networ_shortcut_and_library_prefix_using_put_using_put**
> LibItemPT update_item_by_networ_shortcut_and_library_prefix_using_put_using_put(network_shortcut, library_prefix, media_item)

updateItemByNetworShortcutAndLibraryPrefix

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.LIBRARYApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
library_prefix = 'library_prefix_example' # str | libraryPrefix
media_item = swagger_client.LibItemPT() # LibItemPT | mediaItem

try: 
    # updateItemByNetworShortcutAndLibraryPrefix
    api_response = api_instance.update_item_by_networ_shortcut_and_library_prefix_using_put_using_put(network_shortcut, library_prefix, media_item)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling LIBRARYApi->update_item_by_networ_shortcut_and_library_prefix_using_put_using_put: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **library_prefix** | **str**| libraryPrefix | 
 **media_item** | [**LibItemPT**](LibItemPT.md)| mediaItem | 

### Return type

[**LibItemPT**](LibItemPT.md)

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
api_instance = swagger_client.LIBRARYApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
library = swagger_client.LibraryPT() # LibraryPT | library

try: 
    # updateLibrary
    api_response = api_instance.update_library_using_put_using_put(network_shortcut, library)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling LIBRARYApi->update_library_using_put_using_put: %s\n" % e)
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

# **upload_items_by_networ_shortcut_and_library_prefix_using_post**
> LibItemPT upload_items_by_networ_shortcut_and_library_prefix_using_post(network_shortcut, library_prefix, files)

uploadItemsByNetworShortcutAndLibraryPrefix

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.LIBRARYApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
library_prefix = 'library_prefix_example' # str | libraryPrefix
files = '/path/to/file.txt' # file | 

try: 
    # uploadItemsByNetworShortcutAndLibraryPrefix
    api_response = api_instance.upload_items_by_networ_shortcut_and_library_prefix_using_post(network_shortcut, library_prefix, files)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling LIBRARYApi->upload_items_by_networ_shortcut_and_library_prefix_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **library_prefix** | **str**| libraryPrefix | 
 **files** | **file**|  | 

### Return type

[**LibItemPT**](LibItemPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

