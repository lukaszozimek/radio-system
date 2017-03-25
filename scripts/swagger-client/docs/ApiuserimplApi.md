# swagger_client.ApiuserimplApi

All URIs are relative to *https://localhost:8080/*

Method | HTTP request | Description
------------- | ------------- | -------------
[**activate_account_using_get**](ApiuserimplApi.md#activate_account_using_get) | **GET** /api/user/activate | activateAccount
[**change_password_using_post**](ApiuserimplApi.md#change_password_using_post) | **POST** /api/user/account/change_password | changePassword
[**finish_password_reset_using_post**](ApiuserimplApi.md#finish_password_reset_using_post) | **POST** /api/user/account/reset_password/finish | finishPasswordReset
[**get_account_using_get**](ApiuserimplApi.md#get_account_using_get) | **GET** /api/user/account | getAccount
[**is_authenticated_using_get**](ApiuserimplApi.md#is_authenticated_using_get) | **GET** /api/user/authenticate | isAuthenticated
[**register_account_using_post**](ApiuserimplApi.md#register_account_using_post) | **POST** /api/user/register | registerAccount
[**request_password_reset_using_post**](ApiuserimplApi.md#request_password_reset_using_post) | **POST** /api/user/account/reset_password/init | requestPasswordReset
[**save_account_using_post**](ApiuserimplApi.md#save_account_using_post) | **POST** /api/user/account | saveAccount


# **activate_account_using_get**
> str activate_account_using_get(key)

activateAccount

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.ApiuserimplApi()
key = 'key_example' # str | key

try: 
    # activateAccount
    api_response = api_instance.activate_account_using_get(key)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ApiuserimplApi->activate_account_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **key** | **str**| key | 

### Return type

**str**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **change_password_using_post**
> ResponseEntity change_password_using_post(password)

changePassword

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.ApiuserimplApi()
password = 'password_example' # str | password

try: 
    # changePassword
    api_response = api_instance.change_password_using_post(password)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ApiuserimplApi->change_password_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **password** | **str**| password | 

### Return type

[**ResponseEntity**](ResponseEntity.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/plain

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **finish_password_reset_using_post**
> str finish_password_reset_using_post(key_and_password)

finishPasswordReset

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.ApiuserimplApi()
key_and_password = swagger_client.KeyAndPasswordVM() # KeyAndPasswordVM | keyAndPassword

try: 
    # finishPasswordReset
    api_response = api_instance.finish_password_reset_using_post(key_and_password)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ApiuserimplApi->finish_password_reset_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **key_and_password** | [**KeyAndPasswordVM**](KeyAndPasswordVM.md)| keyAndPassword | 

### Return type

**str**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/plain

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_account_using_get**
> CoreUserPT get_account_using_get()

getAccount

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.ApiuserimplApi()

try: 
    # getAccount
    api_response = api_instance.get_account_using_get()
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ApiuserimplApi->get_account_using_get: %s\n" % e)
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**CoreUserPT**](CoreUserPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **is_authenticated_using_get**
> str is_authenticated_using_get()

isAuthenticated

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.ApiuserimplApi()

try: 
    # isAuthenticated
    api_response = api_instance.is_authenticated_using_get()
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ApiuserimplApi->is_authenticated_using_get: %s\n" % e)
```

### Parameters
This endpoint does not need any parameter.

### Return type

**str**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **register_account_using_post**
> ResponseEntity register_account_using_post(managed_user_vm)

registerAccount

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.ApiuserimplApi()
managed_user_vm = swagger_client.CoreManagedUserPT() # CoreManagedUserPT | managedUserVM

try: 
    # registerAccount
    api_response = api_instance.register_account_using_post(managed_user_vm)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ApiuserimplApi->register_account_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **managed_user_vm** | [**CoreManagedUserPT**](CoreManagedUserPT.md)| managedUserVM | 

### Return type

[**ResponseEntity**](ResponseEntity.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, text/plain

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **request_password_reset_using_post**
> ResponseEntity request_password_reset_using_post(mail)

requestPasswordReset

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.ApiuserimplApi()
mail = 'mail_example' # str | mail

try: 
    # requestPasswordReset
    api_response = api_instance.request_password_reset_using_post(mail)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ApiuserimplApi->request_password_reset_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **mail** | **str**| mail | 

### Return type

[**ResponseEntity**](ResponseEntity.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/plain

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **save_account_using_post**
> str save_account_using_post(user_dto)

saveAccount

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.ApiuserimplApi()
user_dto = swagger_client.UserDTO() # UserDTO | userDTO

try: 
    # saveAccount
    api_response = api_instance.save_account_using_post(user_dto)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ApiuserimplApi->save_account_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **user_dto** | [**UserDTO**](UserDTO.md)| userDTO | 

### Return type

**str**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

