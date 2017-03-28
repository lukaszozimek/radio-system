# swagger_client.UserjwtcontrollerApi

All URIs are relative to *https://localhost:8080/*

Method | HTTP request | Description
------------- | ------------- | -------------
[**authorize_using_post**](UserjwtcontrollerApi.md#authorize_using_post) | **POST** /api/user/authenticate | authorize


# **authorize_using_post**
> object authorize_using_post(login_vm)

authorize

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.UserjwtcontrollerApi()
login_vm = swagger_client.LoginVM() # LoginVM | loginVM

try: 
    # authorize
    api_response = api_instance.authorize_using_post(login_vm)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling UserjwtcontrollerApi->authorize_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login_vm** | [**LoginVM**](LoginVM.md)| loginVM | 

### Return type

**object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

