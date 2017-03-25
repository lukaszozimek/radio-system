# swagger_client.TRAFFICApi

All URIs are relative to *https://localhost:8080/*

Method | HTTP request | Description
------------- | ------------- | -------------
[**approve_shuffeling_commercial_using_post_using_post**](TRAFFICApi.md#approve_shuffeling_commercial_using_post_using_post) | **POST** /api/network/{networkShortcut}/traffic/advertisement/shuffle/ | updateAdvertisement
[**creat_traffic_templates_using_post_using_post**](TRAFFICApi.md#creat_traffic_templates_using_post_using_post) | **POST** /api/network/{networkShortcut}/traffic/templates | createTrafficTemplates
[**create_advertisement_using_post_using_post**](TRAFFICApi.md#create_advertisement_using_post_using_post) | **POST** /api/network/{networkShortcut}/traffic/advertisement | createAdvertisement
[**create_an_order_using_post_using_post**](TRAFFICApi.md#create_an_order_using_post_using_post) | **POST** /api/network/{networkShortcut}/traffic/order | createAnOrder
[**create_campaign_using_post_using_post**](TRAFFICApi.md#create_campaign_using_post_using_post) | **POST** /api/network/{networkShortcut}/traffic/campaign | createCampaign
[**create_contact_activity_using_post_using_post**](TRAFFICApi.md#create_contact_activity_using_post_using_post) | **POST** /api/network/{networkShortcut}/crm/contact/{shortName}/task | createContactActivity
[**create_contact_using_post_using_post**](TRAFFICApi.md#create_contact_using_post_using_post) | **POST** /api/network/{networkShortcut}/crm/contact | createContact
[**create_customer_activity_using_post_using_post**](TRAFFICApi.md#create_customer_activity_using_post_using_post) | **POST** /api/network/{networkShortcut}/crm/customer/{shortName}/task | createCustomerActivity
[**create_customer_using_post_using_post**](TRAFFICApi.md#create_customer_using_post_using_post) | **POST** /api/network/{networkShortcut}/crm/customer | createCustomer
[**create_invoice_using_post_using_post**](TRAFFICApi.md#create_invoice_using_post_using_post) | **POST** /api/network/{networkShortcut}/traffic/invoice | createInvoice
[**create_lead_activity_using_post_using_post**](TRAFFICApi.md#create_lead_activity_using_post_using_post) | **POST** /api/network/{networkShortcut}/crm/lead/{shortName}/task | createLeadActivity
[**create_lead_using_post_using_post**](TRAFFICApi.md#create_lead_using_post_using_post) | **POST** /api/network/{networkShortcut}/crm/lead | createLead
[**create_opportunity_activity_using_post_using_post**](TRAFFICApi.md#create_opportunity_activity_using_post_using_post) | **POST** /api/network/{networkShortcut}/crm/opportunity/{shortName}/task | createOpportunityActivity
[**create_opportunity_using_post_using_post**](TRAFFICApi.md#create_opportunity_using_post_using_post) | **POST** /api/network/{networkShortcut}/crm/opportunity | createOpportunity
[**create_traffic_customer_using_post_using_post**](TRAFFICApi.md#create_traffic_customer_using_post_using_post) | **POST** /api/network/{networkShortcut}/traffic/customer/ | createTrafficCustomerUsingPOST
[**delete_advertisement_using_delete_using_delete**](TRAFFICApi.md#delete_advertisement_using_delete_using_delete) | **DELETE** /api/network/{networkShortcut}/traffic/advertisement/{idx} | deleteAdvertisement
[**delete_an_order_using_delete_using_delete**](TRAFFICApi.md#delete_an_order_using_delete_using_delete) | **DELETE** /api/network/{networkShortcut}/traffic/order/{id} | deleteAnOrder
[**delete_campaign_using_delete_using_delete**](TRAFFICApi.md#delete_campaign_using_delete_using_delete) | **DELETE** /api/network/{networkShortcut}/traffic/campaign/{shortName} | deleteCampaign
[**delete_contact_activity_using_delete_using_delete**](TRAFFICApi.md#delete_contact_activity_using_delete_using_delete) | **DELETE** /api/network/{networkShortcut}/crm/contact/{shortName}/task/{id} | deleteContactActivityActivity
[**delete_contact_using_delete_using_delete**](TRAFFICApi.md#delete_contact_using_delete_using_delete) | **DELETE** /api/network/{networkShortcut}/crm/contact/{shortName} | deleteContact
[**delete_customer_activity_using_delete_using_delete**](TRAFFICApi.md#delete_customer_activity_using_delete_using_delete) | **DELETE** /api/network/{networkShortcut}/crm/customer/{shortName}/task/{id} | deleteCustomerActivityActivity
[**delete_customery_using_delete_using_delete**](TRAFFICApi.md#delete_customery_using_delete_using_delete) | **DELETE** /api/network/{networkShortcut}/crm/customer/{shortName} | deleteCustomer
[**delete_invoice_using_delete_using_delete**](TRAFFICApi.md#delete_invoice_using_delete_using_delete) | **DELETE** /api/network/{networkShortcut}/traffic/invoice/{id} | deleteInvoice
[**delete_lead_activity_using_delete_using_delete**](TRAFFICApi.md#delete_lead_activity_using_delete_using_delete) | **DELETE** /api/network/{networkShortcut}/crm/lead/{shortName}/task/{id} | deleteLeadActivity
[**delete_lead_using_delete_using_delete**](TRAFFICApi.md#delete_lead_using_delete_using_delete) | **DELETE** /api/network/{networkShortcut}/crm/lead/{shortName} | deleteLead
[**delete_opportunity_activity_using_delete_using_delete**](TRAFFICApi.md#delete_opportunity_activity_using_delete_using_delete) | **DELETE** /api/network/{networkShortcut}/crm/opportunity/{shortName}/task/{id} | deleteOpportunityActivity
[**delete_opportunity_using_delete_using_delete**](TRAFFICApi.md#delete_opportunity_using_delete_using_delete) | **DELETE** /api/network/{networkShortcut}/crm/opportunity/{shortName} | deleteOpportunity
[**delete_traffic_customer_using_delete_using_delete**](TRAFFICApi.md#delete_traffic_customer_using_delete_using_delete) | **DELETE** /api/network/{networkShortcut}/traffic/customer/{customerShortcut} | deleteTrafficCustomerUsingDELETE
[**delete_traffic_template_using_delete_using_delete**](TRAFFICApi.md#delete_traffic_template_using_delete_using_delete) | **DELETE** /api/network/{networkShortcut}/traffic/templates/{shortName} | deleteTrafficTemplate
[**get_advertisement_using_get_using_get**](TRAFFICApi.md#get_advertisement_using_get_using_get) | **GET** /api/network/{networkShortcut}/traffic/advertisement/{idx} | getAdvertisement
[**get_all_advertisements_using_get_using_get**](TRAFFICApi.md#get_all_advertisements_using_get_using_get) | **GET** /api/network/{networkShortcut}/traffic/advertisement | getAllAdvertisements
[**get_all_an_orders_using_get_using_get**](TRAFFICApi.md#get_all_an_orders_using_get_using_get) | **GET** /api/network/{networkShortcut}/traffic/order | getAllAnOrders
[**get_all_campaigns_using_get_using_get**](TRAFFICApi.md#get_all_campaigns_using_get_using_get) | **GET** /api/network/{networkShortcut}/traffic/campaign | getAllCampaigns
[**get_all_contact_activities_using_get_using_get**](TRAFFICApi.md#get_all_contact_activities_using_get_using_get) | **GET** /api/network/{networkShortcut}/crm/contact/{shortName}/task | getAllContactActivities
[**get_all_contact_using_get_using_get**](TRAFFICApi.md#get_all_contact_using_get_using_get) | **GET** /api/network/{networkShortcut}/crm/contact | getAllContact
[**get_all_customer_activities_using_get_using_get**](TRAFFICApi.md#get_all_customer_activities_using_get_using_get) | **GET** /api/network/{networkShortcut}/crm/customer/{shortName}/task | getAllCustomerActivities
[**get_all_customer_campaigns_using_get_using_get**](TRAFFICApi.md#get_all_customer_campaigns_using_get_using_get) | **GET** /api/network/{networkShortcut}/traffic/customer/{customerShortcut}/campaign | getAllCustomerCampaigns
[**get_all_customer_orders_using_get_using_get**](TRAFFICApi.md#get_all_customer_orders_using_get_using_get) | **GET** /api/network/{networkShortcut}/traffic/customer/{customerShortcut}/order | getAllCustomerOrders
[**get_all_customers_advertisments_using_get_using_get**](TRAFFICApi.md#get_all_customers_advertisments_using_get_using_get) | **GET** /api/network/{networkShortcut}/traffic/customer/{customerShortcut}/advertisement | getAllCustomersAdvertismentsUsingGET
[**get_all_customers_using_get_using_get**](TRAFFICApi.md#get_all_customers_using_get_using_get) | **GET** /api/network/{networkShortcut}/crm/customer | getAllCustomers
[**get_all_invoices_using_get_using_get**](TRAFFICApi.md#get_all_invoices_using_get_using_get) | **GET** /api/network/{networkShortcut}/traffic/invoice | getAllInvoices
[**get_all_lead_activities_using_get_using_get**](TRAFFICApi.md#get_all_lead_activities_using_get_using_get) | **GET** /api/network/{networkShortcut}/crm/lead/{shortName}/task | getAllLeadActivities
[**get_all_leads_using_get_using_get**](TRAFFICApi.md#get_all_leads_using_get_using_get) | **GET** /api/network/{networkShortcut}/crm/lead | getAllLeads
[**get_all_opportunities_using_get_using_get**](TRAFFICApi.md#get_all_opportunities_using_get_using_get) | **GET** /api/network/{networkShortcut}/crm/opportunity | getAllOpportunities
[**get_all_opportunity_activities_using_get_using_get**](TRAFFICApi.md#get_all_opportunity_activities_using_get_using_get) | **GET** /api/network/{networkShortcut}/crm/opportunity/{shortName}/task | getAllOpportunityActivities
[**get_all_traffic_customers_using_get_using_get**](TRAFFICApi.md#get_all_traffic_customers_using_get_using_get) | **GET** /api/network/{networkShortcut}/traffic/customer/ | getAllTrafficCustomersUsingGET
[**get_all_traffic_invoices_for_customer_get_using_get**](TRAFFICApi.md#get_all_traffic_invoices_for_customer_get_using_get) | **GET** /api/network/{networkShortcut}/traffic/customer/{customerShortcut}/invoice | getAllInvoicesForCustomer
[**get_all_traffic_templates_using_get_using_get**](TRAFFICApi.md#get_all_traffic_templates_using_get_using_get) | **GET** /api/network/{networkShortcut}/traffic/templates | getAllTrafficTemplates
[**get_an_order_using_get_using_get**](TRAFFICApi.md#get_an_order_using_get_using_get) | **GET** /api/network/{networkShortcut}/traffic/order/{id} | getAnOrder
[**get_campaign_using_get_using_get**](TRAFFICApi.md#get_campaign_using_get_using_get) | **GET** /api/network/{networkShortcut}/traffic/campaign/{shortName} | getCampaign
[**get_contact_activity_using_get_using_get**](TRAFFICApi.md#get_contact_activity_using_get_using_get) | **GET** /api/network/{networkShortcut}/crm/contact/{shortName}/task/{id} | getContactActivity
[**get_contact_using_get_using_get**](TRAFFICApi.md#get_contact_using_get_using_get) | **GET** /api/network/{networkShortcut}/crm/contact/{shortName} | getContact
[**get_customer_activity_using_get_using_get**](TRAFFICApi.md#get_customer_activity_using_get_using_get) | **GET** /api/network/{networkShortcut}/crm/customer/{shortName}/task/{id} | getCustomerActivity
[**get_customer_using_get_using_get**](TRAFFICApi.md#get_customer_using_get_using_get) | **GET** /api/network/{networkShortcut}/crm/customer/{shortName} | getCustomer
[**get_invoice_using_get_using_get**](TRAFFICApi.md#get_invoice_using_get_using_get) | **GET** /api/network/{networkShortcut}/traffic/invoice/{id} | getInvoice
[**get_lead_activity_using_get_using_get**](TRAFFICApi.md#get_lead_activity_using_get_using_get) | **GET** /api/network/{networkShortcut}/crm/lead/{shortName}/task/{id} | getLead
[**get_lead_using_get_using_get**](TRAFFICApi.md#get_lead_using_get_using_get) | **GET** /api/network/{networkShortcut}/crm/lead/{shortName} | getLead
[**get_opportunity_activity_using_get_using_get**](TRAFFICApi.md#get_opportunity_activity_using_get_using_get) | **GET** /api/network/{networkShortcut}/crm/opportunity/{shortName}/task/{id} | getOpportunityActivity
[**get_opportunity_using_get_using_get**](TRAFFICApi.md#get_opportunity_using_get_using_get) | **GET** /api/network/{networkShortcut}/crm/opportunity/{shortName} | getOpportunity
[**get_traffic_customer_using_get_using_get**](TRAFFICApi.md#get_traffic_customer_using_get_using_get) | **GET** /api/network/{networkShortcut}/traffic/customer/{customerShortcut} | getTrafficCustomerUsingGET
[**get_traffic_template_using_get_using_get**](TRAFFICApi.md#get_traffic_template_using_get_using_get) | **GET** /api/network/{networkShortcut}/traffic/templates/{shortName} | getTrafficTemplate
[**notify_about_unpaid_invoice_customer_using_get_using_post**](TRAFFICApi.md#notify_about_unpaid_invoice_customer_using_get_using_post) | **POST** /api/network/{networkShortcut}/traffic/customer/{customerShortcut}/invoice/{id}/notify | notifyAboutUnpaidInvoiceCustomer
[**notify_about_unpaid_invoice_using_get_using_post**](TRAFFICApi.md#notify_about_unpaid_invoice_using_get_using_post) | **POST** /api/network/{networkShortcut}/traffic/invoice/{id}/notify | notifyAboutUnpaidInvoice
[**notify_customer_about_unpaid_order_using_post_using_post**](TRAFFICApi.md#notify_customer_about_unpaid_order_using_post_using_post) | **POST** /api/network/{networkShortcut}/traffic/order/{id}/notify | notifyCustomerAboutUnpaidOrder
[**shuffle_commercial_using_get_using_get**](TRAFFICApi.md#shuffle_commercial_using_get_using_get) | **GET** /api/network/{networkShortcut}/traffic/advertisement/shuffle | updateAdvertisement
[**update_advertisement_using_put_using_put**](TRAFFICApi.md#update_advertisement_using_put_using_put) | **PUT** /api/network/{networkShortcut}/traffic/advertisement | updateAdvertisement
[**update_an_order_using_put_using_put**](TRAFFICApi.md#update_an_order_using_put_using_put) | **PUT** /api/network/{networkShortcut}/traffic/order | updateAnOrder
[**update_campaign_using_put_using_put**](TRAFFICApi.md#update_campaign_using_put_using_put) | **PUT** /api/network/{networkShortcut}/traffic/campaign | updateCampaign
[**update_contact_activity_using_put_using_put**](TRAFFICApi.md#update_contact_activity_using_put_using_put) | **PUT** /api/network/{networkShortcut}/crm/contact/{shortName}/task | updateContactActivity
[**update_contact_using_put_using_put**](TRAFFICApi.md#update_contact_using_put_using_put) | **PUT** /api/network/{networkShortcut}/crm/contact | updateContact
[**update_customer_activity_using_put_using_put**](TRAFFICApi.md#update_customer_activity_using_put_using_put) | **PUT** /api/network/{networkShortcut}/crm/customer/{shortName}/task | updateCustomerActivity
[**update_customer_using_put_using_put**](TRAFFICApi.md#update_customer_using_put_using_put) | **PUT** /api/network/{networkShortcut}/crm/customer | updateCustomer
[**update_invoice_using_put_using_put**](TRAFFICApi.md#update_invoice_using_put_using_put) | **PUT** /api/network/{networkShortcut}/traffic/invoice | updateInvoice
[**update_lead_activity_using_put_using_put**](TRAFFICApi.md#update_lead_activity_using_put_using_put) | **PUT** /api/network/{networkShortcut}/crm/lead/{shortName}/task | updateLeadActivity
[**update_lead_using_put_using_put**](TRAFFICApi.md#update_lead_using_put_using_put) | **PUT** /api/network/{networkShortcut}/crm/lead | updateLead
[**update_opportunity_activity_using_put_using_put**](TRAFFICApi.md#update_opportunity_activity_using_put_using_put) | **PUT** /api/network/{networkShortcut}/crm/opportunity/{shortName}/task | updateOpportunityActivity
[**update_opportunity_using_put_using_put**](TRAFFICApi.md#update_opportunity_using_put_using_put) | **PUT** /api/network/{networkShortcut}/crm/opportunity | updateOpportunity
[**update_traffic_customer_using_put_using_put**](TRAFFICApi.md#update_traffic_customer_using_put_using_put) | **PUT** /api/network/{networkShortcut}/traffic/customer/ | updateTrafficCustomerUsingPUT
[**update_traffic_templates_using_put_using_put**](TRAFFICApi.md#update_traffic_templates_using_put_using_put) | **PUT** /api/network/{networkShortcut}/traffic/templates | updateTrafficTemplates


# **approve_shuffeling_commercial_using_post_using_post**
> TraAdvertisementPT approve_shuffeling_commercial_using_post_using_post(network_shortcut, tra_shuffle_advertisment_pt)

updateAdvertisement

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
tra_shuffle_advertisment_pt = swagger_client.TraShuffleAdvertisementPT() # TraShuffleAdvertisementPT | traShuffleAdvertismentPT

try: 
    # updateAdvertisement
    api_response = api_instance.approve_shuffeling_commercial_using_post_using_post(network_shortcut, tra_shuffle_advertisment_pt)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->approve_shuffeling_commercial_using_post_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **tra_shuffle_advertisment_pt** | [**TraShuffleAdvertisementPT**](TraShuffleAdvertisementPT.md)| traShuffleAdvertismentPT | 

### Return type

[**TraAdvertisementPT**](TraAdvertisementPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **creat_traffic_templates_using_post_using_post**
> SchTemplatePT creat_traffic_templates_using_post_using_post(network_shortcut, schdeuler_template)

createTrafficTemplates

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
schdeuler_template = swagger_client.SchTemplatePT() # SchTemplatePT | schdeulerTemplate

try: 
    # createTrafficTemplates
    api_response = api_instance.creat_traffic_templates_using_post_using_post(network_shortcut, schdeuler_template)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->creat_traffic_templates_using_post_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **schdeuler_template** | [**SchTemplatePT**](SchTemplatePT.md)| schdeulerTemplate | 

### Return type

[**SchTemplatePT**](SchTemplatePT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **create_advertisement_using_post_using_post**
> TraAdvertisementPT create_advertisement_using_post_using_post(network_shortcut, advertisement_dto)

createAdvertisement

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
advertisement_dto = swagger_client.TraAdvertisementPT() # TraAdvertisementPT | advertisementDTO

try: 
    # createAdvertisement
    api_response = api_instance.create_advertisement_using_post_using_post(network_shortcut, advertisement_dto)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->create_advertisement_using_post_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **advertisement_dto** | [**TraAdvertisementPT**](TraAdvertisementPT.md)| advertisementDTO | 

### Return type

[**TraAdvertisementPT**](TraAdvertisementPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **create_an_order_using_post_using_post**
> TraOrderPT create_an_order_using_post_using_post(network_shortcut, an_order_dto)

createAnOrder

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
an_order_dto = swagger_client.TraOrderPT() # TraOrderPT | anOrderDTO

try: 
    # createAnOrder
    api_response = api_instance.create_an_order_using_post_using_post(network_shortcut, an_order_dto)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->create_an_order_using_post_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **an_order_dto** | [**TraOrderPT**](TraOrderPT.md)| anOrderDTO | 

### Return type

[**TraOrderPT**](TraOrderPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **create_campaign_using_post_using_post**
> TraCampaignPT create_campaign_using_post_using_post(network_shortcut, campaign_dto)

createCampaign

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
campaign_dto = swagger_client.TraCampaignPT() # TraCampaignPT | campaignDTO

try: 
    # createCampaign
    api_response = api_instance.create_campaign_using_post_using_post(network_shortcut, campaign_dto)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->create_campaign_using_post_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **campaign_dto** | [**TraCampaignPT**](TraCampaignPT.md)| campaignDTO | 

### Return type

[**TraCampaignPT**](TraCampaignPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **create_contact_activity_using_post_using_post**
> CrmTaskPT create_contact_activity_using_post_using_post(network_shortcut, short_name, crm_activity_pt)

createContactActivity

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName
crm_activity_pt = swagger_client.CrmTaskPT() # CrmTaskPT | crmActivityPT

try: 
    # createContactActivity
    api_response = api_instance.create_contact_activity_using_post_using_post(network_shortcut, short_name, crm_activity_pt)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->create_contact_activity_using_post_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 
 **crm_activity_pt** | [**CrmTaskPT**](CrmTaskPT.md)| crmActivityPT | 

### Return type

[**CrmTaskPT**](CrmTaskPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **create_contact_using_post_using_post**
> CrmContactPT create_contact_using_post_using_post(network_shortcut, customer_pt)

createContact

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
customer_pt = swagger_client.CrmContactPT() # CrmContactPT | customerPT

try: 
    # createContact
    api_response = api_instance.create_contact_using_post_using_post(network_shortcut, customer_pt)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->create_contact_using_post_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **customer_pt** | [**CrmContactPT**](CrmContactPT.md)| customerPT | 

### Return type

[**CrmContactPT**](CrmContactPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **create_customer_activity_using_post_using_post**
> CrmTaskPT create_customer_activity_using_post_using_post(network_shortcut, short_name, crm_activity_pt)

createCustomerActivity

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName
crm_activity_pt = swagger_client.CrmTaskPT() # CrmTaskPT | crmActivityPT

try: 
    # createCustomerActivity
    api_response = api_instance.create_customer_activity_using_post_using_post(network_shortcut, short_name, crm_activity_pt)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->create_customer_activity_using_post_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 
 **crm_activity_pt** | [**CrmTaskPT**](CrmTaskPT.md)| crmActivityPT | 

### Return type

[**CrmTaskPT**](CrmTaskPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **create_customer_using_post_using_post**
> CrmAccountPT create_customer_using_post_using_post(network_shortcut, customer_pt)

createCustomer

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
customer_pt = swagger_client.CrmAccountPT() # CrmAccountPT | customerPT

try: 
    # createCustomer
    api_response = api_instance.create_customer_using_post_using_post(network_shortcut, customer_pt)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->create_customer_using_post_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **customer_pt** | [**CrmAccountPT**](CrmAccountPT.md)| customerPT | 

### Return type

[**CrmAccountPT**](CrmAccountPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **create_invoice_using_post_using_post**
> TraInvoicePT create_invoice_using_post_using_post(network_shortcut, invoice_dto)

createInvoice

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
invoice_dto = swagger_client.TraInvoicePT() # TraInvoicePT | invoiceDTO

try: 
    # createInvoice
    api_response = api_instance.create_invoice_using_post_using_post(network_shortcut, invoice_dto)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->create_invoice_using_post_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **invoice_dto** | [**TraInvoicePT**](TraInvoicePT.md)| invoiceDTO | 

### Return type

[**TraInvoicePT**](TraInvoicePT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **create_lead_activity_using_post_using_post**
> CrmTaskPT create_lead_activity_using_post_using_post(network_shortcut, short_name, crm_activity_pt)

createLeadActivity

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName
crm_activity_pt = swagger_client.CrmTaskPT() # CrmTaskPT | crmActivityPT

try: 
    # createLeadActivity
    api_response = api_instance.create_lead_activity_using_post_using_post(network_shortcut, short_name, crm_activity_pt)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->create_lead_activity_using_post_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 
 **crm_activity_pt** | [**CrmTaskPT**](CrmTaskPT.md)| crmActivityPT | 

### Return type

[**CrmTaskPT**](CrmTaskPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **create_lead_using_post_using_post**
> CrmLeadPT create_lead_using_post_using_post(network_shortcut, crm_lead_pt)

createLead

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
crm_lead_pt = swagger_client.CrmLeadPT() # CrmLeadPT | crmLeadPT

try: 
    # createLead
    api_response = api_instance.create_lead_using_post_using_post(network_shortcut, crm_lead_pt)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->create_lead_using_post_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **crm_lead_pt** | [**CrmLeadPT**](CrmLeadPT.md)| crmLeadPT | 

### Return type

[**CrmLeadPT**](CrmLeadPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **create_opportunity_activity_using_post_using_post**
> CrmTaskPT create_opportunity_activity_using_post_using_post(network_shortcut, short_name, crm_activity_pt)

createOpportunityActivity

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName
crm_activity_pt = swagger_client.CrmTaskPT() # CrmTaskPT | crmActivityPT

try: 
    # createOpportunityActivity
    api_response = api_instance.create_opportunity_activity_using_post_using_post(network_shortcut, short_name, crm_activity_pt)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->create_opportunity_activity_using_post_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 
 **crm_activity_pt** | [**CrmTaskPT**](CrmTaskPT.md)| crmActivityPT | 

### Return type

[**CrmTaskPT**](CrmTaskPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **create_opportunity_using_post_using_post**
> CrmOpportunityPT create_opportunity_using_post_using_post(network_shortcut, crm_opportunity_pt)

createOpportunity

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
crm_opportunity_pt = swagger_client.CrmOpportunityPT() # CrmOpportunityPT | crmOpportunityPT

try: 
    # createOpportunity
    api_response = api_instance.create_opportunity_using_post_using_post(network_shortcut, crm_opportunity_pt)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->create_opportunity_using_post_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **crm_opportunity_pt** | [**CrmOpportunityPT**](CrmOpportunityPT.md)| crmOpportunityPT | 

### Return type

[**CrmOpportunityPT**](CrmOpportunityPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **create_traffic_customer_using_post_using_post**
> TraCustomerPT create_traffic_customer_using_post_using_post(network_shortcut, customer)

createTrafficCustomerUsingPOST

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
customer = swagger_client.TraCustomerPT() # TraCustomerPT | customerVM

try: 
    # createTrafficCustomerUsingPOST
    api_response = api_instance.create_traffic_customer_using_post_using_post(network_shortcut, customer)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->create_traffic_customer_using_post_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **customer** | [**TraCustomerPT**](TraCustomerPT.md)| customerVM | 

### Return type

[**TraCustomerPT**](TraCustomerPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **delete_advertisement_using_delete_using_delete**
> delete_advertisement_using_delete_using_delete(network_shortcut, idx)

deleteAdvertisement

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
idx = 789 # int | idx

try: 
    # deleteAdvertisement
    api_instance.delete_advertisement_using_delete_using_delete(network_shortcut, idx)
except ApiException as e:
    print("Exception when calling TRAFFICApi->delete_advertisement_using_delete_using_delete: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **idx** | **int**| idx | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **delete_an_order_using_delete_using_delete**
> delete_an_order_using_delete_using_delete(network_shortcut, id)

deleteAnOrder

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
id = 789 # int | id

try: 
    # deleteAnOrder
    api_instance.delete_an_order_using_delete_using_delete(network_shortcut, id)
except ApiException as e:
    print("Exception when calling TRAFFICApi->delete_an_order_using_delete_using_delete: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **id** | **int**| id | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **delete_campaign_using_delete_using_delete**
> delete_campaign_using_delete_using_delete(network_shortcut, short_name)

deleteCampaign

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName

try: 
    # deleteCampaign
    api_instance.delete_campaign_using_delete_using_delete(network_shortcut, short_name)
except ApiException as e:
    print("Exception when calling TRAFFICApi->delete_campaign_using_delete_using_delete: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **delete_contact_activity_using_delete_using_delete**
> delete_contact_activity_using_delete_using_delete(network_shortcut, short_name, id)

deleteContactActivityActivity

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName
id = 789 # int | id

try: 
    # deleteContactActivityActivity
    api_instance.delete_contact_activity_using_delete_using_delete(network_shortcut, short_name, id)
except ApiException as e:
    print("Exception when calling TRAFFICApi->delete_contact_activity_using_delete_using_delete: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 
 **id** | **int**| id | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **delete_contact_using_delete_using_delete**
> delete_contact_using_delete_using_delete(network_shortcut, short_name)

deleteContact

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName

try: 
    # deleteContact
    api_instance.delete_contact_using_delete_using_delete(network_shortcut, short_name)
except ApiException as e:
    print("Exception when calling TRAFFICApi->delete_contact_using_delete_using_delete: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **delete_customer_activity_using_delete_using_delete**
> delete_customer_activity_using_delete_using_delete(network_shortcut, short_name, id)

deleteCustomerActivityActivity

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName
id = 789 # int | id

try: 
    # deleteCustomerActivityActivity
    api_instance.delete_customer_activity_using_delete_using_delete(network_shortcut, short_name, id)
except ApiException as e:
    print("Exception when calling TRAFFICApi->delete_customer_activity_using_delete_using_delete: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 
 **id** | **int**| id | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **delete_customery_using_delete_using_delete**
> delete_customery_using_delete_using_delete(network_shortcut, short_name)

deleteCustomer

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName

try: 
    # deleteCustomer
    api_instance.delete_customery_using_delete_using_delete(network_shortcut, short_name)
except ApiException as e:
    print("Exception when calling TRAFFICApi->delete_customery_using_delete_using_delete: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **delete_invoice_using_delete_using_delete**
> delete_invoice_using_delete_using_delete(network_shortcut, id)

deleteInvoice

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
id = 789 # int | id

try: 
    # deleteInvoice
    api_instance.delete_invoice_using_delete_using_delete(network_shortcut, id)
except ApiException as e:
    print("Exception when calling TRAFFICApi->delete_invoice_using_delete_using_delete: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **id** | **int**| id | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **delete_lead_activity_using_delete_using_delete**
> delete_lead_activity_using_delete_using_delete(network_shortcut, short_name, id)

deleteLeadActivity

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName
id = 789 # int | id

try: 
    # deleteLeadActivity
    api_instance.delete_lead_activity_using_delete_using_delete(network_shortcut, short_name, id)
except ApiException as e:
    print("Exception when calling TRAFFICApi->delete_lead_activity_using_delete_using_delete: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 
 **id** | **int**| id | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **delete_lead_using_delete_using_delete**
> delete_lead_using_delete_using_delete(network_shortcut, short_name)

deleteLead

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName

try: 
    # deleteLead
    api_instance.delete_lead_using_delete_using_delete(network_shortcut, short_name)
except ApiException as e:
    print("Exception when calling TRAFFICApi->delete_lead_using_delete_using_delete: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **delete_opportunity_activity_using_delete_using_delete**
> delete_opportunity_activity_using_delete_using_delete(network_shortcut, short_name, id)

deleteOpportunityActivity

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName
id = 789 # int | id

try: 
    # deleteOpportunityActivity
    api_instance.delete_opportunity_activity_using_delete_using_delete(network_shortcut, short_name, id)
except ApiException as e:
    print("Exception when calling TRAFFICApi->delete_opportunity_activity_using_delete_using_delete: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 
 **id** | **int**| id | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **delete_opportunity_using_delete_using_delete**
> delete_opportunity_using_delete_using_delete(network_shortcut, short_name)

deleteOpportunity

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName

try: 
    # deleteOpportunity
    api_instance.delete_opportunity_using_delete_using_delete(network_shortcut, short_name)
except ApiException as e:
    print("Exception when calling TRAFFICApi->delete_opportunity_using_delete_using_delete: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **delete_traffic_customer_using_delete_using_delete**
> delete_traffic_customer_using_delete_using_delete(network_shortcut, customer_shortcut)

deleteTrafficCustomerUsingDELETE

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
customer_shortcut = 'customer_shortcut_example' # str | customerShortcut

try: 
    # deleteTrafficCustomerUsingDELETE
    api_instance.delete_traffic_customer_using_delete_using_delete(network_shortcut, customer_shortcut)
except ApiException as e:
    print("Exception when calling TRAFFICApi->delete_traffic_customer_using_delete_using_delete: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **customer_shortcut** | **str**| customerShortcut | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **delete_traffic_template_using_delete_using_delete**
> delete_traffic_template_using_delete_using_delete(network_shortcut, short_name)

deleteTrafficTemplate

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName

try: 
    # deleteTrafficTemplate
    api_instance.delete_traffic_template_using_delete_using_delete(network_shortcut, short_name)
except ApiException as e:
    print("Exception when calling TRAFFICApi->delete_traffic_template_using_delete_using_delete: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_advertisement_using_get_using_get**
> TraAdvertisementPT get_advertisement_using_get_using_get(network_shortcut, idx)

getAdvertisement

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
idx = 789 # int | idx

try: 
    # getAdvertisement
    api_response = api_instance.get_advertisement_using_get_using_get(network_shortcut, idx)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_advertisement_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **idx** | **int**| idx | 

### Return type

[**TraAdvertisementPT**](TraAdvertisementPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_advertisements_using_get_using_get**
> TraAdvertisementPT get_all_advertisements_using_get_using_get(network_shortcut)

getAllAdvertisements

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut

try: 
    # getAllAdvertisements
    api_response = api_instance.get_all_advertisements_using_get_using_get(network_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_all_advertisements_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 

### Return type

[**TraAdvertisementPT**](TraAdvertisementPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_an_orders_using_get_using_get**
> TraOrderPT get_all_an_orders_using_get_using_get(network_shortcut)

getAllAnOrders

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut

try: 
    # getAllAnOrders
    api_response = api_instance.get_all_an_orders_using_get_using_get(network_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_all_an_orders_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 

### Return type

[**TraOrderPT**](TraOrderPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_campaigns_using_get_using_get**
> TraCampaignPT get_all_campaigns_using_get_using_get(network_shortcut)

getAllCampaigns

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut

try: 
    # getAllCampaigns
    api_response = api_instance.get_all_campaigns_using_get_using_get(network_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_all_campaigns_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 

### Return type

[**TraCampaignPT**](TraCampaignPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_contact_activities_using_get_using_get**
> CrmTaskPT get_all_contact_activities_using_get_using_get(network_shortcut, short_name)

getAllContactActivities

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName

try: 
    # getAllContactActivities
    api_response = api_instance.get_all_contact_activities_using_get_using_get(network_shortcut, short_name)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_all_contact_activities_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 

### Return type

[**CrmTaskPT**](CrmTaskPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_contact_using_get_using_get**
> CrmContactPT get_all_contact_using_get_using_get(network_shortcut)

getAllContact

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut

try: 
    # getAllContact
    api_response = api_instance.get_all_contact_using_get_using_get(network_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_all_contact_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 

### Return type

[**CrmContactPT**](CrmContactPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_customer_activities_using_get_using_get**
> CrmTaskPT get_all_customer_activities_using_get_using_get(network_shortcut, short_name)

getAllCustomerActivities

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName

try: 
    # getAllCustomerActivities
    api_response = api_instance.get_all_customer_activities_using_get_using_get(network_shortcut, short_name)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_all_customer_activities_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 

### Return type

[**CrmTaskPT**](CrmTaskPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_customer_campaigns_using_get_using_get**
> TraCampaignPT get_all_customer_campaigns_using_get_using_get(network_shortcut, customer_shortcut)

getAllCustomerCampaigns

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
customer_shortcut = 'customer_shortcut_example' # str | customerShortcut

try: 
    # getAllCustomerCampaigns
    api_response = api_instance.get_all_customer_campaigns_using_get_using_get(network_shortcut, customer_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_all_customer_campaigns_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **customer_shortcut** | **str**| customerShortcut | 

### Return type

[**TraCampaignPT**](TraCampaignPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_customer_orders_using_get_using_get**
> TraOrderPT get_all_customer_orders_using_get_using_get(network_shortcut, customer_shortcut)

getAllCustomerOrders

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
customer_shortcut = 'customer_shortcut_example' # str | customerShortcut

try: 
    # getAllCustomerOrders
    api_response = api_instance.get_all_customer_orders_using_get_using_get(network_shortcut, customer_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_all_customer_orders_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **customer_shortcut** | **str**| customerShortcut | 

### Return type

[**TraOrderPT**](TraOrderPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_customers_advertisments_using_get_using_get**
> TraCustomerAdvertismentsPT get_all_customers_advertisments_using_get_using_get(network_shortcut, customer_shortcut)

getAllCustomersAdvertismentsUsingGET

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
customer_shortcut = 'customer_shortcut_example' # str | customerShortcut

try: 
    # getAllCustomersAdvertismentsUsingGET
    api_response = api_instance.get_all_customers_advertisments_using_get_using_get(network_shortcut, customer_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_all_customers_advertisments_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **customer_shortcut** | **str**| customerShortcut | 

### Return type

[**TraCustomerAdvertismentsPT**](TraCustomerAdvertismentsPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_customers_using_get_using_get**
> CrmAccountPT get_all_customers_using_get_using_get(network_shortcut)

getAllCustomers

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut

try: 
    # getAllCustomers
    api_response = api_instance.get_all_customers_using_get_using_get(network_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_all_customers_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 

### Return type

[**CrmAccountPT**](CrmAccountPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_invoices_using_get_using_get**
> TraInvoicePT get_all_invoices_using_get_using_get(network_shortcut)

getAllInvoices

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut

try: 
    # getAllInvoices
    api_response = api_instance.get_all_invoices_using_get_using_get(network_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_all_invoices_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 

### Return type

[**TraInvoicePT**](TraInvoicePT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_lead_activities_using_get_using_get**
> CrmTaskPT get_all_lead_activities_using_get_using_get(network_shortcut, short_name)

getAllLeadActivities

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName

try: 
    # getAllLeadActivities
    api_response = api_instance.get_all_lead_activities_using_get_using_get(network_shortcut, short_name)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_all_lead_activities_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 

### Return type

[**CrmTaskPT**](CrmTaskPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_leads_using_get_using_get**
> CrmLeadPT get_all_leads_using_get_using_get(network_shortcut)

getAllLeads

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut

try: 
    # getAllLeads
    api_response = api_instance.get_all_leads_using_get_using_get(network_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_all_leads_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 

### Return type

[**CrmLeadPT**](CrmLeadPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_opportunities_using_get_using_get**
> CrmOpportunityPT get_all_opportunities_using_get_using_get(network_shortcut)

getAllOpportunities

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut

try: 
    # getAllOpportunities
    api_response = api_instance.get_all_opportunities_using_get_using_get(network_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_all_opportunities_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 

### Return type

[**CrmOpportunityPT**](CrmOpportunityPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_opportunity_activities_using_get_using_get**
> CrmTaskPT get_all_opportunity_activities_using_get_using_get(network_shortcut, short_name)

getAllOpportunityActivities

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName

try: 
    # getAllOpportunityActivities
    api_response = api_instance.get_all_opportunity_activities_using_get_using_get(network_shortcut, short_name)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_all_opportunity_activities_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 

### Return type

[**CrmTaskPT**](CrmTaskPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_traffic_customers_using_get_using_get**
> TraCustomerPT get_all_traffic_customers_using_get_using_get(network_shortcut)

getAllTrafficCustomersUsingGET

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut

try: 
    # getAllTrafficCustomersUsingGET
    api_response = api_instance.get_all_traffic_customers_using_get_using_get(network_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_all_traffic_customers_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 

### Return type

[**TraCustomerPT**](TraCustomerPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_traffic_invoices_for_customer_get_using_get**
> TraCustomerOrdersPT get_all_traffic_invoices_for_customer_get_using_get(network_shortcut, customer_shortcut)

getAllInvoicesForCustomer

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
customer_shortcut = 'customer_shortcut_example' # str | customerShortcut

try: 
    # getAllInvoicesForCustomer
    api_response = api_instance.get_all_traffic_invoices_for_customer_get_using_get(network_shortcut, customer_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_all_traffic_invoices_for_customer_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **customer_shortcut** | **str**| customerShortcut | 

### Return type

[**TraCustomerOrdersPT**](TraCustomerOrdersPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_all_traffic_templates_using_get_using_get**
> SchTemplatePT get_all_traffic_templates_using_get_using_get(network_shortcut)

getAllTrafficTemplates

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut

try: 
    # getAllTrafficTemplates
    api_response = api_instance.get_all_traffic_templates_using_get_using_get(network_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_all_traffic_templates_using_get_using_get: %s\n" % e)
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

# **get_an_order_using_get_using_get**
> TraOrderPT get_an_order_using_get_using_get(network_shortcut, id)

getAnOrder

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
id = 789 # int | id

try: 
    # getAnOrder
    api_response = api_instance.get_an_order_using_get_using_get(network_shortcut, id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_an_order_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **id** | **int**| id | 

### Return type

[**TraOrderPT**](TraOrderPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_campaign_using_get_using_get**
> TraCampaignPT get_campaign_using_get_using_get(network_shortcut, short_name)

getCampaign

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName

try: 
    # getCampaign
    api_response = api_instance.get_campaign_using_get_using_get(network_shortcut, short_name)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_campaign_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 

### Return type

[**TraCampaignPT**](TraCampaignPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_contact_activity_using_get_using_get**
> CrmTaskPT get_contact_activity_using_get_using_get(network_shortcut, short_name, id)

getContactActivity

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName
id = 789 # int | id

try: 
    # getContactActivity
    api_response = api_instance.get_contact_activity_using_get_using_get(network_shortcut, short_name, id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_contact_activity_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 
 **id** | **int**| id | 

### Return type

[**CrmTaskPT**](CrmTaskPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_contact_using_get_using_get**
> CrmContactPT get_contact_using_get_using_get(network_shortcut, short_name)

getContact

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName

try: 
    # getContact
    api_response = api_instance.get_contact_using_get_using_get(network_shortcut, short_name)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_contact_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 

### Return type

[**CrmContactPT**](CrmContactPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_customer_activity_using_get_using_get**
> CrmTaskPT get_customer_activity_using_get_using_get(network_shortcut, short_name, id)

getCustomerActivity

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName
id = 789 # int | id

try: 
    # getCustomerActivity
    api_response = api_instance.get_customer_activity_using_get_using_get(network_shortcut, short_name, id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_customer_activity_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 
 **id** | **int**| id | 

### Return type

[**CrmTaskPT**](CrmTaskPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_customer_using_get_using_get**
> CrmAccountPT get_customer_using_get_using_get(network_shortcut, short_name)

getCustomer

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName

try: 
    # getCustomer
    api_response = api_instance.get_customer_using_get_using_get(network_shortcut, short_name)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_customer_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 

### Return type

[**CrmAccountPT**](CrmAccountPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_invoice_using_get_using_get**
> TraInvoicePT get_invoice_using_get_using_get(network_shortcut, id)

getInvoice

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
id = 789 # int | id

try: 
    # getInvoice
    api_response = api_instance.get_invoice_using_get_using_get(network_shortcut, id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_invoice_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **id** | **int**| id | 

### Return type

[**TraInvoicePT**](TraInvoicePT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_lead_activity_using_get_using_get**
> CrmTaskPT get_lead_activity_using_get_using_get(network_shortcut, short_name, id)

getLead

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName
id = 789 # int | id

try: 
    # getLead
    api_response = api_instance.get_lead_activity_using_get_using_get(network_shortcut, short_name, id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_lead_activity_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 
 **id** | **int**| id | 

### Return type

[**CrmTaskPT**](CrmTaskPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_lead_using_get_using_get**
> CrmLeadPT get_lead_using_get_using_get(network_shortcut, short_name)

getLead

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName

try: 
    # getLead
    api_response = api_instance.get_lead_using_get_using_get(network_shortcut, short_name)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_lead_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 

### Return type

[**CrmLeadPT**](CrmLeadPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_opportunity_activity_using_get_using_get**
> CrmTaskPT get_opportunity_activity_using_get_using_get(network_shortcut, short_name, id)

getOpportunityActivity

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName
id = 789 # int | id

try: 
    # getOpportunityActivity
    api_response = api_instance.get_opportunity_activity_using_get_using_get(network_shortcut, short_name, id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_opportunity_activity_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 
 **id** | **int**| id | 

### Return type

[**CrmTaskPT**](CrmTaskPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_opportunity_using_get_using_get**
> CrmOpportunityPT get_opportunity_using_get_using_get(network_shortcut, short_name)

getOpportunity

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName

try: 
    # getOpportunity
    api_response = api_instance.get_opportunity_using_get_using_get(network_shortcut, short_name)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_opportunity_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 

### Return type

[**CrmOpportunityPT**](CrmOpportunityPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_traffic_customer_using_get_using_get**
> TraCustomerPT get_traffic_customer_using_get_using_get(network_shortcut, customer_shortcut)

getTrafficCustomerUsingGET

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
customer_shortcut = 'customer_shortcut_example' # str | customerShortcut

try: 
    # getTrafficCustomerUsingGET
    api_response = api_instance.get_traffic_customer_using_get_using_get(network_shortcut, customer_shortcut)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_traffic_customer_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **customer_shortcut** | **str**| customerShortcut | 

### Return type

[**TraCustomerPT**](TraCustomerPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_traffic_template_using_get_using_get**
> SchTemplatePT get_traffic_template_using_get_using_get(network_shortcut, short_name)

getTrafficTemplate

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName

try: 
    # getTrafficTemplate
    api_response = api_instance.get_traffic_template_using_get_using_get(network_shortcut, short_name)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->get_traffic_template_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 

### Return type

[**SchTemplatePT**](SchTemplatePT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **notify_about_unpaid_invoice_customer_using_get_using_post**
> notify_about_unpaid_invoice_customer_using_get_using_post(network_shortcut, customer_shortcut, id)

notifyAboutUnpaidInvoiceCustomer

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | cutomerId
customer_shortcut = 'customer_shortcut_example' # str | customerShortcut
id = 789 # int | id

try: 
    # notifyAboutUnpaidInvoiceCustomer
    api_instance.notify_about_unpaid_invoice_customer_using_get_using_post(network_shortcut, customer_shortcut, id)
except ApiException as e:
    print("Exception when calling TRAFFICApi->notify_about_unpaid_invoice_customer_using_get_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| cutomerId | 
 **customer_shortcut** | **str**| customerShortcut | 
 **id** | **int**| id | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **notify_about_unpaid_invoice_using_get_using_post**
> notify_about_unpaid_invoice_using_get_using_post(network_shortcut, id)

notifyAboutUnpaidInvoice

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | cutomerId
id = 789 # int | id

try: 
    # notifyAboutUnpaidInvoice
    api_instance.notify_about_unpaid_invoice_using_get_using_post(network_shortcut, id)
except ApiException as e:
    print("Exception when calling TRAFFICApi->notify_about_unpaid_invoice_using_get_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| cutomerId | 
 **id** | **int**| id | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **notify_customer_about_unpaid_order_using_post_using_post**
> TraOrderPT notify_customer_about_unpaid_order_using_post_using_post(network_shortcut, id)

notifyCustomerAboutUnpaidOrder

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
id = 789 # int | id

try: 
    # notifyCustomerAboutUnpaidOrder
    api_response = api_instance.notify_customer_about_unpaid_order_using_post_using_post(network_shortcut, id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->notify_customer_about_unpaid_order_using_post_using_post: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **id** | **int**| id | 

### Return type

[**TraOrderPT**](TraOrderPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **shuffle_commercial_using_get_using_get**
> TraAdvertisementPT shuffle_commercial_using_get_using_get(network_shortcut, tra_shuffle_advertisment_pt)

updateAdvertisement

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
tra_shuffle_advertisment_pt = swagger_client.TraShuffleAdvertisementPT() # TraShuffleAdvertisementPT | traShuffleAdvertismentPT

try: 
    # updateAdvertisement
    api_response = api_instance.shuffle_commercial_using_get_using_get(network_shortcut, tra_shuffle_advertisment_pt)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->shuffle_commercial_using_get_using_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **tra_shuffle_advertisment_pt** | [**TraShuffleAdvertisementPT**](TraShuffleAdvertisementPT.md)| traShuffleAdvertismentPT | 

### Return type

[**TraAdvertisementPT**](TraAdvertisementPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **update_advertisement_using_put_using_put**
> TraAdvertisementPT update_advertisement_using_put_using_put(network_shortcut, advertisement_dto)

updateAdvertisement

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
advertisement_dto = swagger_client.TraAdvertisementPT() # TraAdvertisementPT | advertisementDTO

try: 
    # updateAdvertisement
    api_response = api_instance.update_advertisement_using_put_using_put(network_shortcut, advertisement_dto)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->update_advertisement_using_put_using_put: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **advertisement_dto** | [**TraAdvertisementPT**](TraAdvertisementPT.md)| advertisementDTO | 

### Return type

[**TraAdvertisementPT**](TraAdvertisementPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **update_an_order_using_put_using_put**
> TraOrderPT update_an_order_using_put_using_put(network_shortcut, an_order_dto)

updateAnOrder

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
an_order_dto = swagger_client.TraOrderPT() # TraOrderPT | anOrderDTO

try: 
    # updateAnOrder
    api_response = api_instance.update_an_order_using_put_using_put(network_shortcut, an_order_dto)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->update_an_order_using_put_using_put: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **an_order_dto** | [**TraOrderPT**](TraOrderPT.md)| anOrderDTO | 

### Return type

[**TraOrderPT**](TraOrderPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **update_campaign_using_put_using_put**
> TraCampaignPT update_campaign_using_put_using_put(network_shortcut, campaign_dto)

updateCampaign

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
campaign_dto = swagger_client.TraCampaignPT() # TraCampaignPT | campaignDTO

try: 
    # updateCampaign
    api_response = api_instance.update_campaign_using_put_using_put(network_shortcut, campaign_dto)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->update_campaign_using_put_using_put: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **campaign_dto** | [**TraCampaignPT**](TraCampaignPT.md)| campaignDTO | 

### Return type

[**TraCampaignPT**](TraCampaignPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **update_contact_activity_using_put_using_put**
> CrmTaskPT update_contact_activity_using_put_using_put(network_shortcut, short_name, crm_activity_pt)

updateContactActivity

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName
crm_activity_pt = swagger_client.CrmTaskPT() # CrmTaskPT | crmActivityPT

try: 
    # updateContactActivity
    api_response = api_instance.update_contact_activity_using_put_using_put(network_shortcut, short_name, crm_activity_pt)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->update_contact_activity_using_put_using_put: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 
 **crm_activity_pt** | [**CrmTaskPT**](CrmTaskPT.md)| crmActivityPT | 

### Return type

[**CrmTaskPT**](CrmTaskPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **update_contact_using_put_using_put**
> CrmContactPT update_contact_using_put_using_put(network_shortcut, customery_pt)

updateContact

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
customery_pt = swagger_client.CrmContactPT() # CrmContactPT | customerPT

try: 
    # updateContact
    api_response = api_instance.update_contact_using_put_using_put(network_shortcut, customery_pt)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->update_contact_using_put_using_put: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **customery_pt** | [**CrmContactPT**](CrmContactPT.md)| customerPT | 

### Return type

[**CrmContactPT**](CrmContactPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **update_customer_activity_using_put_using_put**
> CrmTaskPT update_customer_activity_using_put_using_put(network_shortcut, short_name, crm_activity_pt)

updateCustomerActivity

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName
crm_activity_pt = swagger_client.CrmTaskPT() # CrmTaskPT | crmActivityPT

try: 
    # updateCustomerActivity
    api_response = api_instance.update_customer_activity_using_put_using_put(network_shortcut, short_name, crm_activity_pt)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->update_customer_activity_using_put_using_put: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 
 **crm_activity_pt** | [**CrmTaskPT**](CrmTaskPT.md)| crmActivityPT | 

### Return type

[**CrmTaskPT**](CrmTaskPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **update_customer_using_put_using_put**
> CrmAccountPT update_customer_using_put_using_put(network_shortcut, customery_pt)

updateCustomer

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
customery_pt = swagger_client.CrmAccountPT() # CrmAccountPT | customerPT

try: 
    # updateCustomer
    api_response = api_instance.update_customer_using_put_using_put(network_shortcut, customery_pt)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->update_customer_using_put_using_put: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **customery_pt** | [**CrmAccountPT**](CrmAccountPT.md)| customerPT | 

### Return type

[**CrmAccountPT**](CrmAccountPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **update_invoice_using_put_using_put**
> TraInvoicePT update_invoice_using_put_using_put(network_shortcut, invoice_dto)

updateInvoice

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
invoice_dto = swagger_client.TraInvoicePT() # TraInvoicePT | invoiceDTO

try: 
    # updateInvoice
    api_response = api_instance.update_invoice_using_put_using_put(network_shortcut, invoice_dto)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->update_invoice_using_put_using_put: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **invoice_dto** | [**TraInvoicePT**](TraInvoicePT.md)| invoiceDTO | 

### Return type

[**TraInvoicePT**](TraInvoicePT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **update_lead_activity_using_put_using_put**
> CrmTaskPT update_lead_activity_using_put_using_put(network_shortcut, short_name, crm_activity_pt)

updateLeadActivity

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName
crm_activity_pt = swagger_client.CrmTaskPT() # CrmTaskPT | crmActivityPT

try: 
    # updateLeadActivity
    api_response = api_instance.update_lead_activity_using_put_using_put(network_shortcut, short_name, crm_activity_pt)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->update_lead_activity_using_put_using_put: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 
 **crm_activity_pt** | [**CrmTaskPT**](CrmTaskPT.md)| crmActivityPT | 

### Return type

[**CrmTaskPT**](CrmTaskPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **update_lead_using_put_using_put**
> CrmLeadPT update_lead_using_put_using_put(network_shortcut, crm_lead_pt)

updateLead

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
crm_lead_pt = swagger_client.CrmLeadPT() # CrmLeadPT | crmLeadPT

try: 
    # updateLead
    api_response = api_instance.update_lead_using_put_using_put(network_shortcut, crm_lead_pt)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->update_lead_using_put_using_put: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **crm_lead_pt** | [**CrmLeadPT**](CrmLeadPT.md)| crmLeadPT | 

### Return type

[**CrmLeadPT**](CrmLeadPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **update_opportunity_activity_using_put_using_put**
> CrmTaskPT update_opportunity_activity_using_put_using_put(network_shortcut, short_name, crm_activity_pt)

updateOpportunityActivity

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
short_name = 'short_name_example' # str | shortName
crm_activity_pt = swagger_client.CrmTaskPT() # CrmTaskPT | crmActivityPT

try: 
    # updateOpportunityActivity
    api_response = api_instance.update_opportunity_activity_using_put_using_put(network_shortcut, short_name, crm_activity_pt)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->update_opportunity_activity_using_put_using_put: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **short_name** | **str**| shortName | 
 **crm_activity_pt** | [**CrmTaskPT**](CrmTaskPT.md)| crmActivityPT | 

### Return type

[**CrmTaskPT**](CrmTaskPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **update_opportunity_using_put_using_put**
> CrmOpportunityPT update_opportunity_using_put_using_put(network_shortcut, crm_opportunity_pt)

updateOpportunity

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
crm_opportunity_pt = swagger_client.CrmOpportunityPT() # CrmOpportunityPT | crmOpportunityPT

try: 
    # updateOpportunity
    api_response = api_instance.update_opportunity_using_put_using_put(network_shortcut, crm_opportunity_pt)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->update_opportunity_using_put_using_put: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **crm_opportunity_pt** | [**CrmOpportunityPT**](CrmOpportunityPT.md)| crmOpportunityPT | 

### Return type

[**CrmOpportunityPT**](CrmOpportunityPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **update_traffic_customer_using_put_using_put**
> TraCustomerPT update_traffic_customer_using_put_using_put(network_shortcut, customer)

updateTrafficCustomerUsingPUT

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
customer = swagger_client.TraCustomerPT() # TraCustomerPT | CustomerVM

try: 
    # updateTrafficCustomerUsingPUT
    api_response = api_instance.update_traffic_customer_using_put_using_put(network_shortcut, customer)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->update_traffic_customer_using_put_using_put: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **customer** | [**TraCustomerPT**](TraCustomerPT.md)| CustomerVM | 

### Return type

[**TraCustomerPT**](TraCustomerPT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **update_traffic_templates_using_put_using_put**
> SchTemplatePT update_traffic_templates_using_put_using_put(network_shortcut, schdeuler_template)

updateTrafficTemplates

### Example 
```python
from __future__ import print_statement
import time
import swagger_client
from swagger_client.rest import ApiException
from pprint import pprint

# create an instance of the API class
api_instance = swagger_client.TRAFFICApi()
network_shortcut = 'network_shortcut_example' # str | networkShortcut
schdeuler_template = swagger_client.SchTemplatePT() # SchTemplatePT | schdeulerTemplate

try: 
    # updateTrafficTemplates
    api_response = api_instance.update_traffic_templates_using_put_using_put(network_shortcut, schdeuler_template)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TRAFFICApi->update_traffic_templates_using_put_using_put: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **network_shortcut** | **str**| networkShortcut | 
 **schdeuler_template** | [**SchTemplatePT**](SchTemplatePT.md)| schdeulerTemplate | 

### Return type

[**SchTemplatePT**](SchTemplatePT.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

