# swagger_client.ProfileinforesourceApi

All URIs are relative to *https://localhost:8080/*

Method | HTTP request | Description
------------- | ------------- | -------------
[**get_active_profiles_using_get**](ProfileinforesourceApi.md#get_active_profiles_using_get) | **GET** /api/profile-info | getActiveProfiles


# **get_active_profiles_using_get**
> ProfileInfoVM get_active_profiles_using_get()

getActiveProfiles

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.ProfileinforesourceApi()

try: 
    # getActiveProfiles
    api_response = api_instance.get_active_profiles_using_get()
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProfileinforesourceApi->get_active_profiles_using_get: %s\n" % e)
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**ProfileInfoVM**](ProfileInfoVM.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

