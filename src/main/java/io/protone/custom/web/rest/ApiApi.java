package io.protone.custom.web.rest;

import io.protone.custom.service.dto.*;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiApi {

    @ApiOperation(value = "activateAccount", notes = "", response = String.class, tags={ "CORE","SECURITY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = String.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = String.class),
        @ApiResponse(code = 403, message = "Forbidden", response = String.class),
        @ApiResponse(code = 404, message = "Not Found", response = String.class) })
    @RequestMapping(value = "/api/activate",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<String> activateAccountUsingGET(@ApiParam(value = "key", required = true) @RequestParam(value = "key", required = true) String key);


    @ApiOperation(value = "changePassword", notes = "", response = Object.class, tags={ "CORE","SECURITY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Object.class),
        @ApiResponse(code = 201, message = "Created", response = Object.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Object.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Object.class),
        @ApiResponse(code = 404, message = "Not Found", response = Object.class) })
    @RequestMapping(value = "/api/account/change_password",
        produces = { "text/plain" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Object> changePasswordUsingPOST(@ApiParam(value = "password" ,required=true ) @RequestBody String password);


    @ApiOperation(value = "createSchedulerForChannelPlaylist", notes = "", response = SchPlaylistPT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchPlaylistPT.class),
        @ApiResponse(code = 201, message = "Created", response = SchPlaylistPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchPlaylistPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchPlaylistPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchPlaylistPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/playlist",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<SchPlaylistPT> creatSchedulerPlaylistForChannelUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "schdeulerTemplate" ,required=true ) @RequestBody SchPlaylistPT schdeulerTemplate);


    @ApiOperation(value = "createSchedulerPlaylist", notes = "", response = SchPlaylistPT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchPlaylistPT.class),
        @ApiResponse(code = 201, message = "Created", response = SchPlaylistPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchPlaylistPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchPlaylistPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchPlaylistPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/scheduler/playlist",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<SchPlaylistPT> creatSchedulerPlaylistUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "schdeulerTemplate" ,required=true ) @RequestBody SchPlaylistPT schdeulerTemplate);


    @ApiOperation(value = "createSchedulerTemplatesForChannel", notes = "", response = SchTemplatePT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 201, message = "Created", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/templates",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<SchTemplatePT> creatSchedulerTemplatesForChannelUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "schdeulerTemplate" ,required=true ) @RequestBody SchTemplatePT schdeulerTemplate);


    @ApiOperation(value = "createSchedulerTemplates", notes = "", response = SchTemplatePT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 201, message = "Created", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/scheduler/templates",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<SchTemplatePT> creatSchedulerTemplatesUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "schdeulerTemplate" ,required=true ) @RequestBody SchTemplatePT schdeulerTemplate);


    @ApiOperation(value = "createTrafficTemplatesForChannel", notes = "", response = SchTemplatePT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 201, message = "Created", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/traffic/templates",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<SchTemplatePT> creatTrafficTemplatesForChannelUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "schdeulerTemplate" ,required=true ) @RequestBody SchTemplatePT schdeulerTemplate);


    @ApiOperation(value = "createTrafficTemplates", notes = "", response = SchTemplatePT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 201, message = "Created", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/templates",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<SchTemplatePT> creatTrafficTemplatesUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "schdeulerTemplate" ,required=true ) @RequestBody SchTemplatePT schdeulerTemplate);


    @ApiOperation(value = "createAdvertisement", notes = "", response = TraAdvertisementPT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraAdvertisementPT.class),
        @ApiResponse(code = 201, message = "Created", response = TraAdvertisementPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraAdvertisementPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraAdvertisementPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraAdvertisementPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/advertisement",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<TraAdvertisementPT> createAdvertisementUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "advertisementDTO" ,required=true ) @RequestBody TraAdvertisementPT advertisementDTO);


    @ApiOperation(value = "createAnOrder", notes = "", response = TraOrderPT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraOrderPT.class),
        @ApiResponse(code = 201, message = "Created", response = TraOrderPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraOrderPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraOrderPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraOrderPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/order",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<TraOrderPT> createAnOrderUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "anOrderDTO" ,required=true ) @RequestBody TraOrderPT anOrderDTO);


    @ApiOperation(value = "createArea", notes = "", response = CoreAreaPT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreAreaPT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreAreaPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreAreaPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreAreaPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreAreaPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/area",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<CoreAreaPT> createAreaUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "personDTO" ,required=true ) @RequestBody CoreAreaPT personDTO);


    @ApiOperation(value = "createIndustry", notes = "", response = ConfBlockPT.class, tags={ "CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfBlockPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfBlockPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfBlockPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfBlockPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfBlockPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/dictionary/block",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<ConfBlockPT> createBlockTypesUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "confBlockPT" ,required=true ) @RequestBody ConfBlockPT confBlockPT);


    @ApiOperation(value = "createCampaign", notes = "", response = TraCampaignPT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCampaignPT.class),
        @ApiResponse(code = 201, message = "Created", response = TraCampaignPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCampaignPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCampaignPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCampaignPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/campaign",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<TraCampaignPT> createCampaignUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "campaignDTO" ,required=true ) @RequestBody TraCampaignPT campaignDTO);


    @ApiOperation(value = "createChannel", notes = "", response = CoreChannelPT.class, tags={ "CHANNEL","CORE", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreChannelPT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreChannelPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreChannelPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreChannelPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreChannelPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<CoreChannelPT> createChannelUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelDTO" ,required=true ) @RequestBody CoreChannelPT channelDTO);


    @ApiOperation(value = "createCommercialLogConfiguration", notes = "", response = ConfCommercialLogPT.class, tags={ "CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCommercialLogPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfCommercialLogPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCommercialLogPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCommercialLogPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCommercialLogPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/log/commercial",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<ConfCommercialLogPT> createCommercialLogConfigurationUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "confMusicLogPT" ,required=true ) @RequestBody ConfCommercialLogPT confMusicLogPT);


    @ApiOperation(value = "createContactActivity", notes = "", response = CrmTaskPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmTaskPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/contact/{shortName}/task",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<CrmTaskPT> createContactActivityUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName,
        @ApiParam(value = "crmActivityPT" ,required=true ) @RequestBody CrmTaskPT crmActivityPT);


    @ApiOperation(value = "createContact", notes = "", response = CrmContactPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmContactPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmContactPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmContactPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmContactPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmContactPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/contact",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<CrmContactPT> createContactUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "customerPT" ,required=true ) @RequestBody CrmContactPT customerPT);


    @ApiOperation(value = "createCountry", notes = "", response = ConfCountryPt.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCountryPt.class),
        @ApiResponse(code = 201, message = "Created", response = ConfCountryPt.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCountryPt.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCountryPt.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCountryPt.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/country",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<ConfCountryPt> createCountryUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "personDTO" ,required=true ) @RequestBody ConfCountryPt personDTO);


    @ApiOperation(value = "createCrmStage", notes = "", response = ConfCrmStagePT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCrmStagePT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfCrmStagePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCrmStagePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCrmStagePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCrmStagePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/stage/",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<ConfCrmStagePT> createCrmStageUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "crmStage" ,required=true ) @RequestBody ConfCrmStagePT crmStage);


    @ApiOperation(value = "createCurrency", notes = "", response = ConfCurrencyPT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCurrencyPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfCurrencyPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCurrencyPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCurrencyPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCurrencyPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/currency",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<ConfCurrencyPT> createCurrencyUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "taxDTO" ,required=true ) @RequestBody ConfCurrencyPT taxDTO);


    @ApiOperation(value = "createCustomerActivity", notes = "", response = CrmTaskPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmTaskPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/customer/{shortName}/task",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<CrmTaskPT> createCustomerActivityUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName,
        @ApiParam(value = "crmActivityPT" ,required=true ) @RequestBody CrmTaskPT crmActivityPT);


    @ApiOperation(value = "createCustomer", notes = "", response = CrmAccountPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmAccountPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmAccountPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmAccountPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmAccountPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmAccountPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/customer",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<CrmAccountPT> createCustomerUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "customerPT" ,required=true ) @RequestBody CrmAccountPT customerPT);


    @ApiOperation(value = "createIndustry", notes = "", response = ConfIndustryPT.class, tags={ "CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfIndustryPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfIndustryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfIndustryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfIndustryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfIndustryPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/industry",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<ConfIndustryPT> createIndustryUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "industryDTO" ,required=true ) @RequestBody ConfIndustryPT industryDTO);


    @ApiOperation(value = "createInvoice", notes = "", response = TraInvoicePT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraInvoicePT.class),
        @ApiResponse(code = 201, message = "Created", response = TraInvoicePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraInvoicePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraInvoicePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraInvoicePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/invoice",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<TraInvoicePT> createInvoiceUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "invoiceDTO" ,required=true ) @RequestBody TraInvoicePT invoiceDTO);


    @ApiOperation(value = "createLeadActivity", notes = "", response = CrmTaskPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmTaskPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/lead/{shortName}/task",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<CrmTaskPT> createLeadActivityUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName,
        @ApiParam(value = "crmActivityPT" ,required=true ) @RequestBody CrmTaskPT crmActivityPT);


    @ApiOperation(value = "createLeadStatus", notes = "", response = ConfLeadStatusPT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfLeadStatusPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/leadstatus/",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<ConfLeadStatusPT> createLeadStatusUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "leadStatus" ,required=true ) @RequestBody ConfLeadStatusPT leadStatus);


    @ApiOperation(value = "createLead", notes = "", response = CrmLeadPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmLeadPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmLeadPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmLeadPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmLeadPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmLeadPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/lead",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<CrmLeadPT> createLeadUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "crmLeadPT" ,required=true ) @RequestBody CrmLeadPT crmLeadPT);


    @ApiOperation(value = "createLeadsource", notes = "", response = ConfLeadSourcePT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfLeadSourcePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/leadsource/",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<ConfLeadSourcePT> createLeadsourceUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "leadStatus" ,required=true ) @RequestBody ConfLeadSourcePT leadStatus);


    @ApiOperation(value = "createLibraryForChannel", notes = "", response = LibraryPT.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibraryPT.class),
        @ApiResponse(code = 201, message = "Created", response = LibraryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibraryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibraryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibraryPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/library",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<LibraryPT> createLibraryForChannelUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "library" ,required=true ) @RequestBody LibraryPT library);


    @ApiOperation(value = "createLibraryProcessingConfiguration", notes = "", response = ConfLibraryProcessingConfigurationPT.class, tags={ "LIBRARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfLibraryProcessingConfigurationPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfLibraryProcessingConfigurationPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfLibraryProcessingConfigurationPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfLibraryProcessingConfigurationPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfLibraryProcessingConfigurationPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/library/{shortName}/processing",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<ConfLibraryProcessingConfigurationPT> createLibraryProcessingConfigurationUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName,
        @ApiParam(value = "library" ,required=true ) @RequestBody ConfLibraryProcessingConfigurationPT library);


    @ApiOperation(value = "createLibrary", notes = "", response = LibraryPT.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibraryPT.class),
        @ApiResponse(code = 201, message = "Created", response = LibraryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibraryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibraryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibraryPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/library",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<LibraryPT> createLibraryUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "library" ,required=true ) @RequestBody LibraryPT library);


    @ApiOperation(value = "createMarkerConfiguration", notes = "", response = ConfMarkerConfigurationPT.class, tags={ "CONFIGURATION","CORE", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfMarkerConfigurationPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/library/marker",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<ConfMarkerConfigurationPT> createMarkerConfigurationUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "markerConfigurationPT" ,required=true ) @RequestBody ConfMarkerConfigurationPT markerConfigurationPT);


    @ApiOperation(value = "createMusicLogConfiguration", notes = "", response = ConfMusicLogPT.class, tags={ "CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfMusicLogPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfMusicLogPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfMusicLogPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfMusicLogPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfMusicLogPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/log/music",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<ConfMusicLogPT> createMusicLogConfigurationUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "confMusicLogPT" ,required=true ) @RequestBody ConfMusicLogPT confMusicLogPT);


    @ApiOperation(value = "createNetwork", notes = "", response = CoreNetworkPT.class, tags={ "NETWORK","CORE", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreNetworkPT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreNetworkPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreNetworkPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreNetworkPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreNetworkPT.class) })
    @RequestMapping(value = "/api/network",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<CoreNetworkPT> createNetworkUsingPOST(@ApiParam(value = "network" ,required=true ) @RequestBody CoreNetworkPT network);


    @ApiOperation(value = "createOpportunityActivity", notes = "", response = CrmTaskPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmTaskPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/opportunity/{shortName}/task",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<CrmTaskPT> createOpportunityActivityUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName,
        @ApiParam(value = "crmActivityPT" ,required=true ) @RequestBody CrmTaskPT crmActivityPT);


    @ApiOperation(value = "createOpportunity", notes = "", response = CrmOpportunityPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmOpportunityPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmOpportunityPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmOpportunityPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmOpportunityPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmOpportunityPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/opportunity",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<CrmOpportunityPT> createOpportunityUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "crmOpportunityPT" ,required=true ) @RequestBody CrmOpportunityPT crmOpportunityPT);


    @ApiOperation(value = "createPerson", notes = "", response = ConfPersonPT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfPersonPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfPersonPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfPersonPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfPersonPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfPersonPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/people",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<ConfPersonPT> createPersonUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "personDTO" ,required=true ) @RequestBody ConfPersonPT personDTO);


    @ApiOperation(value = "createPropertyKey", notes = "", response = CoreKeyPT.class, tags={ "CONFIGURATION","DICTIONARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreKeyPT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreKeyPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreKeyPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreKeyPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreKeyPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/property/key",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<CoreKeyPT> createPropertyKeyUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "propertyKeyDTO" ,required=true ) @RequestBody CoreKeyPT propertyKeyDTO);


    @ApiOperation(value = "createPropertyValue", notes = "", response = CoreValuePT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreValuePT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreValuePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreValuePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreValuePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreValuePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/property/value",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<CoreValuePT> createPropertyValueUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "propertyValueDTO" ,required=true ) @RequestBody CoreValuePT propertyValueDTO);


    @ApiOperation(value = "createRange", notes = "", response = CoreRangePT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreRangePT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreRangePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreRangePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreRangePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreRangePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/range",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<CoreRangePT> createRangeUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "personDTO" ,required=true ) @RequestBody CoreRangePT personDTO);


    @ApiOperation(value = "createSize", notes = "", response = CoreSizePT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreSizePT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreSizePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreSizePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreSizePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreSizePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/size",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<CoreSizePT> createSizeUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "personDTO" ,required=true ) @RequestBody CoreSizePT personDTO);


    @ApiOperation(value = "createTag", notes = "", response = ConfTagPT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfTagPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfTagPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfTagPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfTagPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfTagPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/tag",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<ConfTagPT> createTagUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "tagDTO" ,required=true ) @RequestBody ConfTagPT tagDTO);


    @ApiOperation(value = "createTax", notes = "", response = ConfTaxPT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfTaxPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfTaxPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfTaxPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfTaxPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfTaxPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/tax",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<ConfTaxPT> createTaxUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "taxDTO" ,required=true ) @RequestBody ConfTaxPT taxDTO);


    @ApiOperation(value = "createTrafficCustomerUsingPOST", notes = "", response = TraCustomerPT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCustomerPT.class),
        @ApiResponse(code = 201, message = "Created", response = TraCustomerPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCustomerPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCustomerPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCustomerPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/customer/",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<TraCustomerPT> createTrafficCustomerUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "customerVM" ,required=true ) @RequestBody TraCustomerPT customer);


    @ApiOperation(value = "deleteAdvertisement", notes = "", response = Void.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/advertisement/{idx}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteAdvertisementUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "idx",required=true ) @PathVariable("idx") Long idx);


    @ApiOperation(value = "deleteAnOrder", notes = "", response = Void.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/order/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteAnOrderUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "deleteArea", notes = "", response = Void.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/area/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteAreaUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "deleteBlockType", notes = "", response = Void.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/dictionary/block/{blockShortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteBlockTypeUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "blockShortName",required=true ) @PathVariable("blockShortName") String blockShortName);


    @ApiOperation(value = "deleteCampaign", notes = "", response = Void.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/campaign/{shortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteCampaignUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "deleteChannel", notes = "", response = Void.class, tags={ "CHANNEL","CORE", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteChannelUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut);


    @ApiOperation(value = "deleteCommercialLogConfiguration", notes = "", response = Void.class, tags={ "CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/log/commercial",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteCommercialLogConfigurationUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "deleteContactActivityActivity", notes = "", response = Void.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/contact/{shortName}/task/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteContactActivityUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "deleteContact", notes = "", response = Void.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/contact/{shortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteContactUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "deletePerson", notes = "", response = Void.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/country/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteCountryUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "deleteCrmStage", notes = "", response = Void.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/stage/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteCrmStageUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "deleteCurrency", notes = "", response = Void.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/currency/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteCurrencyUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "deleteCustomerActivityActivity", notes = "", response = Void.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/customer/{shortName}/task/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteCustomerActivityUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "deleteCustomer", notes = "", response = Void.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/customer/{shortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteCustomeryUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "deleteIndustry", notes = "", response = Void.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/industry/{industryName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteIndustryUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "industryName",required=true ) @PathVariable("industryName") String industryName);


    @ApiOperation(value = "deleteInvoice", notes = "", response = Void.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/invoice/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteInvoiceUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "deleteItemByNetworShortcutAndChannelShortcutAndLibrary", notes = "", response = Void.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/library/{libraryPrefix}/item/{idx}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteItemByNetworShortcutAndChannelShortcutAndLibraryUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "libraryPrefix",required=true ) @PathVariable("libraryPrefix") String libraryPrefix,
        @ApiParam(value = "idx",required=true ) @PathVariable("idx") String idx);


    @ApiOperation(value = "deleteItemByNetworShortcutAndLibrar", notes = "", response = Void.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/library/{libraryPrefix}/item/{idx}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteItemByNetworShortcutAndLibrarUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "libraryPrefix",required=true ) @PathVariable("libraryPrefix") String libraryPrefix,
        @ApiParam(value = "idx",required=true ) @PathVariable("idx") String idx);


    @ApiOperation(value = "deleteItemsByNetworShortcutAndChannelShortcutAndLibrary", notes = "", response = Void.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/library/{libraryPrefix}/item",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteItemsByNetworShortcutAndChannelShortcutAndLibraryUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "libraryPrefix",required=true ) @PathVariable("libraryPrefix") String libraryPrefix);


    @ApiOperation(value = "deleteLeadActivity", notes = "", response = Void.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/lead/{shortName}/task/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteLeadActivityUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "deleteLeadstatus", notes = "", response = Void.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/leadstatus/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteLeadStatusUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "deleteLead", notes = "", response = Void.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/lead/{shortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteLeadUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "deleteLeadsource", notes = "", response = Void.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/leadsource/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteLeadsourceUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "deleteLibraryForChannel", notes = "", response = Void.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/library/{libraryPrefix}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteLibraryForChannelUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "libraryPrefix",required=true ) @PathVariable("libraryPrefix") String libraryPrefix);


    @ApiOperation(value = "deleteLibraryProcessingConfiguration", notes = "", response = Void.class, tags={ "LIBRARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/library/{shortName}/processing",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteLibraryProcessingConfigurationUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "deleteLibrary", notes = "", response = Void.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/library/{libraryPrefix}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteLibraryUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "libraryPrefix",required=true ) @PathVariable("libraryPrefix") String libraryPrefix);


    @ApiOperation(value = "deleteMarkerConfiguration", notes = "", response = Void.class, tags={ "CONFIGURATION","CORE", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/library/marker/{markerName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteMarkerConfigurationUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "markerName",required=true ) @PathVariable("markerName") String markerName);


    @ApiOperation(value = "deleteMusicLogConfiguration", notes = "", response = Void.class, tags={ "CONFIGURATION","DICTIONARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/log/music",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteMusicLogConfigurationUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "deleteNetwork", notes = "", response = Void.class, tags={ "NETWORK","CORE", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteNetworkUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "deleteOpportunityActivity", notes = "", response = Void.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/opportunity/{shortName}/task/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteOpportunityActivityUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "deleteOpportunity", notes = "", response = Void.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/opportunity/{shortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteOpportunityUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "deletePerson", notes = "", response = Void.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/people/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deletePersonUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "deletePropertyKey", notes = "", response = Void.class, tags={ "CONFIGURATION","DICTIONARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/property/key/{keyName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deletePropertyKeyUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "keyName",required=true ) @PathVariable("keyName") String keyName);


    @ApiOperation(value = "deleteRange", notes = "", response = Void.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/range/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteRangeUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "deleteSchedulerPlaylistForChannel", notes = "", response = Void.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/playlist/{date}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSchedulerPlaylistForChannelUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "date",required=true ) @PathVariable("date") String date);


    @ApiOperation(value = "deleteSchedulerPlaylist", notes = "", response = Void.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/scheduler/playlist/{date}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSchedulerPlaylistUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "date",required=true ) @PathVariable("date") String date);


    @ApiOperation(value = "deleteSchedulerTemplateForChannel", notes = "", response = Void.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/templates/{shortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSchedulerTemplateForChannelUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "deleteSchedulerTemplate", notes = "", response = Void.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/scheduler/templates/{shortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSchedulerTemplateUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "deleteSize", notes = "", response = Void.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/size/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSizeUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "deleteTax", notes = "", response = Void.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/tax/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteTaxUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "deleteTrafficCustomerUsingDELETE", notes = "", response = Void.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/customer/{customerShortcut}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteTrafficCustomerUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "customerShortcut",required=true ) @PathVariable("customerShortcut") String customerShortcut);


    @ApiOperation(value = "deleteTrafficTemplateForChannel", notes = "", response = Void.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/traffic/templates/{shortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteTrafficTemplateForChannelUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "deleteTrafficTemplate", notes = "", response = Void.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/templates/{shortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteTrafficTemplateUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "finishPasswordReset", notes = "", response = String.class, tags={ "CORE","SECURITY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = String.class),
        @ApiResponse(code = 201, message = "Created", response = String.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = String.class),
        @ApiResponse(code = 403, message = "Forbidden", response = String.class),
        @ApiResponse(code = 404, message = "Not Found", response = String.class) })
    @RequestMapping(value = "/api/account/reset_password/finish",
        produces = { "text/plain" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<String> finishPasswordResetUsingPOST(@ApiParam(value = "coreKeyAndPasswordPT" ,required=true ) @RequestBody CoreKeyAndPasswordPT coreKeyAndPasswordPT);


    @ApiOperation(value = "getAccount", notes = "", response = CoreUserPT.class, tags={ "CORE","SECURITY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreUserPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreUserPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreUserPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreUserPT.class) })
    @RequestMapping(value = "/api/account",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<CoreUserPT> getAccountUsingGET();


    @ApiOperation(value = "getAdvertisement", notes = "", response = TraAdvertisementPT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraAdvertisementPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraAdvertisementPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraAdvertisementPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraAdvertisementPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/advertisement/{idx}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<TraAdvertisementPT> getAdvertisementUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "idx",required=true ) @PathVariable("idx") Long idx);


    @ApiOperation(value = "getAllAdvertisements", notes = "", response = TraAdvertisementPT.class, responseContainer = "List", tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraAdvertisementPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraAdvertisementPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraAdvertisementPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraAdvertisementPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/advertisement",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<TraAdvertisementPT>> getAllAdvertisementsUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllAnOrders", notes = "", response = TraOrderPT.class, responseContainer = "List", tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraOrderPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraOrderPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraOrderPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraOrderPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/order",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<TraOrderPT>> getAllAnOrdersUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllArea", notes = "", response = CoreAreaPT.class, responseContainer = "List", tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreAreaPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreAreaPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreAreaPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreAreaPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/area",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<CoreAreaPT>> getAllAreaUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllIndustries", notes = "", response = ConfBlockPT.class, responseContainer = "List", tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfBlockPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfBlockPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfBlockPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfBlockPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/dictionary/block",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<ConfBlockPT>> getAllBlockTypesUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllCampaigns", notes = "", response = TraCampaignPT.class, responseContainer = "List", tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCampaignPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCampaignPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCampaignPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCampaignPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/campaign",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<TraCampaignPT>> getAllCampaignsUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllChannels", notes = "", response = CoreChannelPT.class, responseContainer = "List", tags={ "CHANNEL","CORE", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreChannelPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreChannelPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreChannelPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreChannelPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<CoreChannelPT>> getAllChannelsUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllContactActivities", notes = "", response = CrmTaskPT.class, responseContainer = "List", tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/contact/{shortName}/task",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<CrmTaskPT>> getAllContactActivitiesUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "getAllContact", notes = "", response = CrmContactPT.class, responseContainer = "List", tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmContactPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmContactPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmContactPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmContactPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/contact",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<CrmContactPT>> getAllContactUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllCountries", notes = "", response = ConfCountryPt.class, responseContainer = "List", tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCountryPt.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCountryPt.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCountryPt.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCountryPt.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/country",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<ConfCountryPt>> getAllCountriesUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllCrmStages", notes = "", response = ConfCrmStagePT.class, responseContainer = "List", tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCrmStagePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCrmStagePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCrmStagePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCrmStagePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/stage/",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<ConfCrmStagePT>> getAllCrmStagesUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllCurrency", notes = "", response = ConfCurrencyPT.class, responseContainer = "List", tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCurrencyPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCurrencyPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCurrencyPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCurrencyPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/currency",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<ConfCurrencyPT>> getAllCurrencyUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllCustomerActivities", notes = "", response = CrmTaskPT.class, responseContainer = "List", tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/customer/{shortName}/task",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<CrmTaskPT>> getAllCustomerActivitiesUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "getAllCustomerCampaigns", notes = "", response = TraCampaignPT.class, responseContainer = "List", tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCampaignPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCampaignPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCampaignPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCampaignPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/customer/{customerShortcut}/campaign",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<TraCampaignPT>> getAllCustomerCampaignsUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "customerShortcut",required=true ) @PathVariable("customerShortcut") String customerShortcut);


    @ApiOperation(value = "getAllCustomerOrders", notes = "", response = TraOrderPT.class, responseContainer = "List", tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraOrderPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraOrderPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraOrderPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraOrderPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/customer/{customerShortcut}/order",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<TraOrderPT>> getAllCustomerOrdersUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "customerShortcut",required=true ) @PathVariable("customerShortcut") String customerShortcut);


    @ApiOperation(value = "getAllCustomersAdvertismentsUsingGET", notes = "", response = TraCustomerAdvertismentsPT.class, responseContainer = "List", tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCustomerAdvertismentsPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCustomerAdvertismentsPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCustomerAdvertismentsPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCustomerAdvertismentsPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/customer/{customerShortcut}/advertisement",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<TraCustomerAdvertismentsPT>> getAllCustomersAdvertismentsUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "customerShortcut",required=true ) @PathVariable("customerShortcut") String customerShortcut);


    @ApiOperation(value = "getAllCustomers", notes = "", response = CrmAccountPT.class, responseContainer = "List", tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmAccountPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmAccountPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmAccountPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmAccountPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/customer",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<CrmAccountPT>> getAllCustomersUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllIndustries", notes = "", response = ConfIndustryPT.class, responseContainer = "List", tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfIndustryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfIndustryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfIndustryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfIndustryPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/industry",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<ConfIndustryPT>> getAllIndustriesUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllInvoices", notes = "", response = TraInvoicePT.class, responseContainer = "List", tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraInvoicePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraInvoicePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraInvoicePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraInvoicePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/invoice",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<TraInvoicePT>> getAllInvoicesUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllItemsByNetworShortcutAndLibraryPrefix", notes = "", response = LibItemPT.class, responseContainer = "List", tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibItemPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibItemPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibItemPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibItemPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/library/{libraryPrefix}/item",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<LibItemPT>> getAllItemsByNetworShortcutAndLibraryPrefixUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "libraryPrefix",required=true ) @PathVariable("libraryPrefix") String libraryPrefix);


    @ApiOperation(value = "getAllLeadActivities", notes = "", response = CrmTaskPT.class, responseContainer = "List", tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/lead/{shortName}/task",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<CrmTaskPT>> getAllLeadActivitiesUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "getAllLeadStatus", notes = "", response = ConfLeadStatusPT.class, responseContainer = "List", tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfLeadStatusPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/leadstatus/",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<ConfLeadStatusPT>> getAllLeadStatusUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllLeads", notes = "", response = CrmLeadPT.class, responseContainer = "List", tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmLeadPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmLeadPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmLeadPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmLeadPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/lead",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<CrmLeadPT>> getAllLeadsUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllLeadSource", notes = "", response = ConfLeadSourcePT.class, responseContainer = "List", tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfLeadSourcePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/leadsource/",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<ConfLeadSourcePT>> getAllLeadsourceUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllLibrariesForChannel", notes = "", response = LibraryPT.class, responseContainer = "List", tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibraryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibraryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibraryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibraryPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/library",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<LibraryPT>> getAllLibrariesForChannelUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut);


    @ApiOperation(value = "getAllLibraries", notes = "", response = LibraryPT.class, responseContainer = "List", tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibraryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibraryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibraryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibraryPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/library",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<LibraryPT>> getAllLibrariesUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllMarkerConfiguration", notes = "", response = ConfMarkerConfigurationPT.class, responseContainer = "List", tags={ "CONFIGURATION","CORE", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfMarkerConfigurationPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/library/marker",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<ConfMarkerConfigurationPT>> getAllMarkerConfigurationUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllNetworks", notes = "", response = CoreNetworkPT.class, responseContainer = "List", tags={ "NETWORK","CORE", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreNetworkPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreNetworkPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreNetworkPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreNetworkPT.class) })
    @RequestMapping(value = "/api/network",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<CoreNetworkPT>> getAllNetworksUsingGET();


    @ApiOperation(value = "getAllNotificationForUser", notes = "", response = CoreNotificationsPT.class, responseContainer = "List", tags={ "CORE", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreNotificationsPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreNotificationsPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreNotificationsPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreNotificationsPT.class) })
    @RequestMapping(value = "/api/account/notification",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<CoreNotificationsPT>> getAllNotificationForUserUsingGET(@ApiParam(value = "mail" ,required=true ) @RequestBody String mail);


    @ApiOperation(value = "getAllOpportunities", notes = "", response = CrmOpportunityPT.class, responseContainer = "List", tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmOpportunityPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmOpportunityPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmOpportunityPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmOpportunityPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/opportunity",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<CrmOpportunityPT>> getAllOpportunitiesUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllOpportunityActivities", notes = "", response = CrmTaskPT.class, responseContainer = "List", tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/opportunity/{shortName}/task",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<CrmTaskPT>> getAllOpportunityActivitiesUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "getAllPeople", notes = "", response = ConfPersonPT.class, responseContainer = "List", tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfPersonPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfPersonPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfPersonPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfPersonPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/people",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<ConfPersonPT>> getAllPeopleUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllPropertyKeys", notes = "", response = CoreKeyPT.class, responseContainer = "List", tags={ "CONFIGURATION","DICTIONARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreKeyPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreKeyPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreKeyPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreKeyPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/property/key",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<CoreKeyPT>> getAllPropertyKeysUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllPropertyValues", notes = "", response = CoreValuePT.class, responseContainer = "List", tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreValuePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreValuePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreValuePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreValuePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/property/value",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<CoreValuePT>> getAllPropertyValuesUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllRange", notes = "", response = CoreRangePT.class, responseContainer = "List", tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreRangePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreRangePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreRangePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreRangePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/range",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<CoreRangePT>> getAllRangeUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllSchedulerPlaylistForChannel", notes = "", response = SchPlaylistPT.class, responseContainer = "List", tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchPlaylistPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchPlaylistPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchPlaylistPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchPlaylistPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/playlist",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<SchPlaylistPT>> getAllSchedulerPlaylistForChannelUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut);


    @ApiOperation(value = "getAllSchedulerPlaylist", notes = "", response = SchPlaylistPT.class, responseContainer = "List", tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchPlaylistPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchPlaylistPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchPlaylistPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchPlaylistPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/scheduler/playlist",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<SchPlaylistPT>> getAllSchedulerPlaylistUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllSchedulerTemplatesForChannel", notes = "", response = SchTemplatePT.class, responseContainer = "List", tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/templates",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<SchTemplatePT>> getAllSchedulerTemplatesForChannelUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut);


    @ApiOperation(value = "getAllSchedulerTemplates", notes = "", response = SchTemplatePT.class, responseContainer = "List", tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/scheduler/templates",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<SchTemplatePT>> getAllSchedulerTemplatesUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllSize", notes = "", response = CoreSizePT.class, responseContainer = "List", tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreSizePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreSizePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreSizePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreSizePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/size",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<CoreSizePT>> getAllSizeUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllTags", notes = "", response = ConfTagPT.class, responseContainer = "List", tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfTagPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfTagPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfTagPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfTagPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/tag",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<ConfTagPT>> getAllTagsUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllTaxes", notes = "", response = ConfTaxPT.class, responseContainer = "List", tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfTaxPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfTaxPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfTaxPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfTaxPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/tax",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<ConfTaxPT>> getAllTaxesUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllTrafficCustomersUsingGET", notes = "", response = TraCustomerPT.class, responseContainer = "List", tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCustomerPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCustomerPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCustomerPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCustomerPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/customer/",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<TraCustomerPT>> getAllTrafficCustomersUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAllInvoicesForCustomer", notes = "", response = TraCustomerOrdersPT.class, responseContainer = "List", tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCustomerOrdersPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCustomerOrdersPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCustomerOrdersPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCustomerOrdersPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/customer/{customerShortcut}/invoice",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<TraCustomerOrdersPT>> getAllTrafficInvoicesForCustomerGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "customerShortcut",required=true ) @PathVariable("customerShortcut") String customerShortcut);


    @ApiOperation(value = "getAllTrafficTemplatesForChannel", notes = "", response = SchTemplatePT.class, responseContainer = "List", tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/traffic/templates",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<SchTemplatePT>> getAllTrafficTemplatesForChannelUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut);


    @ApiOperation(value = "getAllTrafficTemplates", notes = "", response = SchTemplatePT.class, responseContainer = "List", tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/templates",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<SchTemplatePT>> getAllTrafficTemplatesUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAnOrder", notes = "", response = TraOrderPT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraOrderPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraOrderPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraOrderPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraOrderPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/order/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<TraOrderPT> getAnOrderUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "getArea", notes = "", response = CoreAreaPT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreAreaPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreAreaPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreAreaPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreAreaPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/area/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<CoreAreaPT> getAreaUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "getBlockType", notes = "", response = ConfBlockPT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfBlockPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfBlockPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfBlockPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfBlockPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/dictionary/block/{blockShortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<ConfBlockPT> getBlockTypeUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "blockShortName",required=true ) @PathVariable("blockShortName") String blockShortName);


    @ApiOperation(value = "getCampaign", notes = "", response = TraCampaignPT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCampaignPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCampaignPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCampaignPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCampaignPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/campaign/{shortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<TraCampaignPT> getCampaignUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "getChannel", notes = "", response = CoreChannelPT.class, tags={ "CHANNEL","CORE", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreChannelPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreChannelPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreChannelPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreChannelPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<CoreChannelPT> getChannelUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut);


    @ApiOperation(value = "getCommercialLogConfiguration", notes = "", response = Object.class, tags={ "CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Object.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Object.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Object.class),
        @ApiResponse(code = 404, message = "Not Found", response = Object.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/log/commercial",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<Object> getCommercialLogConfigurationUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getContactActivity", notes = "", response = CrmTaskPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/contact/{shortName}/task/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<CrmTaskPT> getContactActivityUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "getContact", notes = "", response = CrmContactPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmContactPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmContactPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmContactPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmContactPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/contact/{shortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<CrmContactPT> getContactUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "getPerson", notes = "", response = ConfCountryPt.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCountryPt.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCountryPt.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCountryPt.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCountryPt.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/country/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<ConfCountryPt> getCountryUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "getCrmStage", notes = "", response = ConfCrmStagePT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCrmStagePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCrmStagePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCrmStagePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCrmStagePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/stage/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<ConfCrmStagePT> getCrmStageUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "getCurrency", notes = "", response = ConfCurrencyPT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCurrencyPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCurrencyPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCurrencyPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCurrencyPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/currency/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<ConfCurrencyPT> getCurrencyUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "getCustomerActivity", notes = "", response = CrmTaskPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/customer/{shortName}/task/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<CrmTaskPT> getCustomerActivityUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "getCustomer", notes = "", response = CrmAccountPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmAccountPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmAccountPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmAccountPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmAccountPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/customer/{shortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<CrmAccountPT> getCustomerUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "getIndustry", notes = "", response = ConfIndustryPT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfIndustryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfIndustryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfIndustryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfIndustryPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/industry/{industryName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<ConfIndustryPT> getIndustryUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "industryName",required=true ) @PathVariable("industryName") String industryName);


    @ApiOperation(value = "getInvoice", notes = "", response = TraInvoicePT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraInvoicePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraInvoicePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraInvoicePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraInvoicePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/invoice/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<TraInvoicePT> getInvoiceUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "getItemByNetworShortcutAndChannelShortcutAndLibrary", notes = "", response = LibItemPT.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibItemPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibItemPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibItemPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibItemPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/library/{libraryPrefix}/item/{idx}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<LibItemPT> getItemByNetworShortcutAndChannelShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "libraryPrefix",required=true ) @PathVariable("libraryPrefix") String libraryPrefix,
        @ApiParam(value = "libraryPrefix",required=true ) @PathVariable("idx") String idx);


    @ApiOperation(value = "getItemByNetworShortcutAndLibrar", notes = "", response = LibItemPT.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibItemPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibItemPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibItemPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibItemPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/library/{libraryPrefix}/item/{idx}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<LibItemPT> getItemByNetworShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "libraryPrefix",required=true ) @PathVariable("libraryPrefix") String libraryPrefix,
        @ApiParam(value = "libraryPrefix",required=true ) @PathVariable("idx") String idx);


    @ApiOperation(value = "getItemStreamByNetworShortcutAndChannelShortcutAndLibrary", notes = "", response = LibResponseEntity.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibResponseEntity.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibResponseEntity.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibResponseEntity.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibResponseEntity.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/library/{libraryPrefix}/item/{idx}/stream",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<LibResponseEntity> getItemStreamByNetworShortcutAndChannelShortcutAndLibraryUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "libraryPrefix",required=true ) @PathVariable("libraryPrefix") String libraryPrefix,
        @ApiParam(value = "libraryPrefix",required=true ) @PathVariable("idx") String idx);


    @ApiOperation(value = "getItemStreamByNetworShortcutAndLibrar", notes = "", response = LibResponseEntity.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibResponseEntity.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibResponseEntity.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibResponseEntity.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibResponseEntity.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/library/{libraryPrefix}/item/{idx}/stream",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<LibResponseEntity> getItemStreamByNetworShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "libraryPrefix",required=true ) @PathVariable("libraryPrefix") String libraryPrefix,
        @ApiParam(value = "libraryPrefix",required=true ) @PathVariable("idx") String idx);


    @ApiOperation(value = "getItemsByNetworShortcutAndChannelShortcutAndLibrar", notes = "", response = LibItemPT.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibItemPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibItemPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibItemPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibItemPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/library/{libraryPrefix}/item",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<LibItemPT> getItemsByNetworShortcutAndChannelShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "libraryPrefix",required=true ) @PathVariable("libraryPrefix") String libraryPrefix);


    @ApiOperation(value = "getLead", notes = "", response = CrmTaskPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/lead/{shortName}/task/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<CrmTaskPT> getLeadActivityUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "getLeadStatus", notes = "", response = ConfLeadSourcePT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfLeadSourcePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/leadsource/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<ConfLeadSourcePT> getLeadSourceUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "getLeadStatus", notes = "", response = ConfLeadStatusPT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfLeadStatusPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/leadstatus/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<ConfLeadStatusPT> getLeadStatusUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "getLead", notes = "", response = CrmLeadPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmLeadPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmLeadPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmLeadPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmLeadPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/lead/{shortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<CrmLeadPT> getLeadUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "getLibraryForChannel", notes = "", response = LibraryPT.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibraryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibraryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibraryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibraryPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/library/{libraryPrefix}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<LibraryPT> getLibraryForChannelUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "libraryPrefix",required=true ) @PathVariable("libraryPrefix") String libraryPrefix);


    @ApiOperation(value = "getLibraryProcessingConfiguration", notes = "", response = Object.class, tags={ "LIBRARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Object.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Object.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Object.class),
        @ApiResponse(code = 404, message = "Not Found", response = Object.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/library/{shortName}/processing",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<Object> getLibraryProcessingConfigurationUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "getLibrary", notes = "", response = LibraryPT.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibraryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibraryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibraryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibraryPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/library/{libraryPrefix}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<LibraryPT> getLibraryUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "libraryPrefix",required=true ) @PathVariable("libraryPrefix") String libraryPrefix);


    @ApiOperation(value = "getMarkerConfiguration", notes = "", response = ConfMarkerConfigurationPT.class, tags={ "CONFIGURATION","CORE", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfMarkerConfigurationPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/library/marker/{markerName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<ConfMarkerConfigurationPT> getMarkerConfigurationUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "markerName",required=true ) @PathVariable("markerName") String markerName);


    @ApiOperation(value = "getMusicLogConfiguration", notes = "", response = Object.class, tags={ "CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Object.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Object.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Object.class),
        @ApiResponse(code = 404, message = "Not Found", response = Object.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/log/music",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<Object> getMusicLogConfigurationUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getNetwork", notes = "", response = CoreNetworkPT.class, tags={ "NETWORK","CORE", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreNetworkPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreNetworkPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreNetworkPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreNetworkPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<CoreNetworkPT> getNetworkUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getOpportunityActivity", notes = "", response = CrmTaskPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/opportunity/{shortName}/task/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<CrmTaskPT> getOpportunityActivityUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "getOpportunity", notes = "", response = CrmOpportunityPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmOpportunityPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmOpportunityPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmOpportunityPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmOpportunityPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/opportunity/{shortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<CrmOpportunityPT> getOpportunityUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "getPerson", notes = "", response = ConfPersonPT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfPersonPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfPersonPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfPersonPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfPersonPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/people/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<ConfPersonPT> getPersonUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "getPropertyKey", notes = "", response = CoreKeyPT.class, tags={ "CONFIGURATION","DICTIONARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreKeyPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreKeyPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreKeyPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreKeyPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/property/key/{keyName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<CoreKeyPT> getPropertyKeyUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "keyName",required=true ) @PathVariable("keyName") String keyName);


    @ApiOperation(value = "getRange", notes = "", response = CoreRangePT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreRangePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreRangePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreRangePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreRangePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/range/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<CoreRangePT> getRangeUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "getSchedulerPlaylistForChannel", notes = "", response = SchPlaylistPT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchPlaylistPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchPlaylistPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchPlaylistPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchPlaylistPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/playlist/{date}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<SchPlaylistPT> getSchedulerPlaylistForChannelUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "date",required=true ) @PathVariable("date") String date);


    @ApiOperation(value = "getSchedulerPlaylist", notes = "", response = SchPlaylistPT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchPlaylistPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchPlaylistPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchPlaylistPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchPlaylistPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/scheduler/playlist/{date}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<SchPlaylistPT> getSchedulerPlaylistUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "date",required=true ) @PathVariable("date") String date);


    @ApiOperation(value = "getSchedulerTemplateForChannel", notes = "", response = SchTemplatePT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/templates/{shortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<SchTemplatePT> getSchedulerTemplateForChannelUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "getSchedulerTemplate", notes = "", response = SchTemplatePT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/scheduler/templates/{shortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<SchTemplatePT> getSchedulerTemplateUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "getSize", notes = "", response = CoreSizePT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreSizePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreSizePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreSizePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreSizePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/size/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<CoreSizePT> getSizeUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "getTax", notes = "", response = ConfTaxPT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfTaxPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfTaxPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfTaxPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfTaxPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/tax/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<ConfTaxPT> getTaxUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "getTrafficCustomerUsingGET", notes = "", response = TraCustomerPT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCustomerPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCustomerPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCustomerPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCustomerPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/customer/{customerShortcut}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<TraCustomerPT> getTrafficCustomerUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "customerShortcut",required=true ) @PathVariable("customerShortcut") String customerShortcut);


    @ApiOperation(value = "getTrafficTemplateForChannel", notes = "", response = SchTemplatePT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/traffic/templates/{shortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<SchTemplatePT> getTrafficTemplateForChannelUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "getTrafficTemplate", notes = "", response = SchTemplatePT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/templates/{shortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<SchTemplatePT> getTrafficTemplateUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "loadPlaylist", notes = "", response = Void.class, tags={ "PLAYOUT", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 201, message = "Created", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/playout/playlist/{date}/load",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> loadPlaylistUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "date",required=true ) @PathVariable("date") String date);


    @ApiOperation(value = "notifyAboutUnpaidInvoiceCustomer", notes = "", response = Void.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/customer/{customerShortcut}/invoice/{id}/notify",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> notifyAboutUnpaidInvoiceCustomerUsingGET(@ApiParam(value = "cutomerId",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "customerShortcut",required=true ) @PathVariable("customerShortcut") String customerShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "notifyAboutUnpaidInvoice", notes = "", response = Void.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/invoice/{id}/notify",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> notifyAboutUnpaidInvoiceUsingGET(@ApiParam(value = "cutomerId",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "notifyCustomerAboutUnpaidOrder", notes = "", response = TraOrderPT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraOrderPT.class),
        @ApiResponse(code = 201, message = "Created", response = TraOrderPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraOrderPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraOrderPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraOrderPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/order/{id}/notify",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<TraOrderPT> notifyCustomerAboutUnpaidOrderUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "pauseDeck", notes = "", response = Void.class, tags={ "PLAYOUT", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 201, message = "Created", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/playout/deck/{shortName}/pause",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> pauseDeckUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "pauseShot", notes = "", response = Void.class, tags={ "PLAYOUT", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 201, message = "Created", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/playout/shot/{shortName}/pause",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> pauseShotUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "playDeck", notes = "", response = Void.class, tags={ "PLAYOUT", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 201, message = "Created", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/playout/deck/{shortName}/play",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> playDeckUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "playShot", notes = "", response = Void.class, tags={ "PLAYOUT", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 201, message = "Created", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/playout/shot/{shortName}/play",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> playShotUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "refreshPlaylist", notes = "", response = Void.class, tags={ "PLAYOUT", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 201, message = "Created", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/playout/playlist/{date}/refresh",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> refreshPlaylistUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "date",required=true ) @PathVariable("date") String date);


    @ApiOperation(value = "requestPasswordReset", notes = "", response = Object.class, tags={ "CORE","SECURITY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Object.class),
        @ApiResponse(code = 201, message = "Created", response = Object.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Object.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Object.class),
        @ApiResponse(code = 404, message = "Not Found", response = Object.class) })
    @RequestMapping(value = "/api/account/reset_password/init",
        produces = { "text/plain" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Object> requestPasswordResetUsingPOST(@ApiParam(value = "mail" ,required=true ) @RequestBody String mail);


    @ApiOperation(value = "saveAccount", notes = "", response = String.class, tags={ "CORE","SECURITY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = String.class),
        @ApiResponse(code = 201, message = "Created", response = String.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = String.class),
        @ApiResponse(code = 403, message = "Forbidden", response = String.class),
        @ApiResponse(code = 404, message = "Not Found", response = String.class) })
    @RequestMapping(value = "/api/account",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<String> saveAccountUsingPOST(@ApiParam(value = "coreUserPT" ,required=true ) @RequestBody CoreUserPT coreUserPT);


    @ApiOperation(value = "stopDeck", notes = "", response = Void.class, tags={ "PLAYOUT", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 201, message = "Created", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/playout/deck/{shortName}/stop",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> stopDeckUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "stopShot", notes = "", response = Void.class, tags={ "PLAYOUT", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 201, message = "Created", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/playout/shot/{shortName}/stop",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> stopShotUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "switchPlayoutMode", notes = "", response = Void.class, tags={ "PLAYOUT", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 201, message = "Created", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/playout/mode/{modeType}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> switchPlayoutModeUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "modeType",required=true ) @PathVariable("modeType") String modeType);


    @ApiOperation(value = "unloadDeck", notes = "", response = Void.class, tags={ "PLAYOUT", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 201, message = "Created", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/playout/deck/{shortName}/unload",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> unloadDeckUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "unloadShot", notes = "", response = Void.class, tags={ "PLAYOUT", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 201, message = "Created", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/playout/shot/{shortName}/unload",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> unloadShotUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "updateAdvertisement", notes = "", response = TraAdvertisementPT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraAdvertisementPT.class),
        @ApiResponse(code = 201, message = "Created", response = TraAdvertisementPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraAdvertisementPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraAdvertisementPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraAdvertisementPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/advertisement",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<TraAdvertisementPT> updateAdvertisementUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "advertisementDTO" ,required=true ) @RequestBody TraAdvertisementPT advertisementDTO);


    @ApiOperation(value = "updateAnOrder", notes = "", response = TraOrderPT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraOrderPT.class),
        @ApiResponse(code = 201, message = "Created", response = TraOrderPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraOrderPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraOrderPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraOrderPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/order",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<TraOrderPT> updateAnOrderUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "anOrderDTO" ,required=true ) @RequestBody TraOrderPT anOrderDTO);


    @ApiOperation(value = "updateArea", notes = "", response = CoreAreaPT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreAreaPT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreAreaPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreAreaPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreAreaPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreAreaPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/area",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<CoreAreaPT> updateAreaUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "personDTO" ,required=true ) @RequestBody CoreAreaPT personDTO);


    @ApiOperation(value = "updateBlockTypes", notes = "", response = ConfBlockPT.class, tags={ "CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfBlockPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfBlockPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfBlockPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfBlockPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfBlockPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/dictionary/block",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<ConfBlockPT> updateBlockTypesUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "confBlockPT" ,required=true ) @RequestBody ConfBlockPT confBlockPT);


    @ApiOperation(value = "updateCampaign", notes = "", response = TraCampaignPT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCampaignPT.class),
        @ApiResponse(code = 201, message = "Created", response = TraCampaignPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCampaignPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCampaignPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCampaignPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/campaign",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<TraCampaignPT> updateCampaignUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "campaignDTO" ,required=true ) @RequestBody TraCampaignPT campaignDTO);


    @ApiOperation(value = "updateChannel", notes = "", response = CoreChannelPT.class, tags={ "CHANNEL","CORE", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreChannelPT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreChannelPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreChannelPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreChannelPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreChannelPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<CoreChannelPT> updateChannelUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelDTO" ,required=true ) @RequestBody CoreChannelPT channelDTO);


    @ApiOperation(value = "updateCommercialLogConfiguration", notes = "", response = ConfCommercialLogPT.class, tags={ "CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCommercialLogPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfCommercialLogPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCommercialLogPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCommercialLogPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCommercialLogPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/log/commercial",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<ConfCommercialLogPT> updateCommercialLogConfigurationUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "confMusicLogPT" ,required=true ) @RequestBody ConfCommercialLogPT confMusicLogPT);


    @ApiOperation(value = "updateContactActivity", notes = "", response = CrmTaskPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmTaskPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/contact/{shortName}/task",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<CrmTaskPT> updateContactActivityUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName,
        @ApiParam(value = "crmActivityPT" ,required=true ) @RequestBody CrmTaskPT crmActivityPT);


    @ApiOperation(value = "updateContact", notes = "", response = CrmContactPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmContactPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmContactPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmContactPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmContactPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmContactPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/contact",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<CrmContactPT> updateContactUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "customerPT" ,required=true ) @RequestBody CrmContactPT customeryPT);


    @ApiOperation(value = "updateCountry", notes = "", response = ConfCountryPt.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCountryPt.class),
        @ApiResponse(code = 201, message = "Created", response = ConfCountryPt.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCountryPt.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCountryPt.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCountryPt.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/country",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<ConfCountryPt> updateCountryUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "personDTO" ,required=true ) @RequestBody ConfCountryPt personDTO);


    @ApiOperation(value = "updateCrmStage", notes = "", response = ConfCrmStagePT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCrmStagePT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfCrmStagePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCrmStagePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCrmStagePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCrmStagePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/stage/",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<ConfCrmStagePT> updateCrmStageUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "crmStage" ,required=true ) @RequestBody ConfCrmStagePT crmStage);


    @ApiOperation(value = "updateCurrency", notes = "", response = ConfCurrencyPT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCurrencyPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfCurrencyPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCurrencyPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCurrencyPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCurrencyPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/currency",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<ConfCurrencyPT> updateCurrencyUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "taxDTO" ,required=true ) @RequestBody ConfCurrencyPT taxDTO);


    @ApiOperation(value = "updateCustomerActivity", notes = "", response = CrmTaskPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmTaskPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/customer/{shortName}/task",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<CrmTaskPT> updateCustomerActivityUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName,
        @ApiParam(value = "crmActivityPT" ,required=true ) @RequestBody CrmTaskPT crmActivityPT);


    @ApiOperation(value = "updateCustomer", notes = "", response = CrmAccountPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmAccountPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmAccountPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmAccountPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmAccountPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmAccountPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/customer",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<CrmAccountPT> updateCustomerUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "customerPT" ,required=true ) @RequestBody CrmAccountPT customeryPT);


    @ApiOperation(value = "updateIndustry", notes = "", response = ConfIndustryPT.class, tags={ "CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfIndustryPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfIndustryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfIndustryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfIndustryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfIndustryPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/industry",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<ConfIndustryPT> updateIndustryUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "industryDTO" ,required=true ) @RequestBody ConfIndustryPT industryDTO);


    @ApiOperation(value = "updateInvoice", notes = "", response = TraInvoicePT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraInvoicePT.class),
        @ApiResponse(code = 201, message = "Created", response = TraInvoicePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraInvoicePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraInvoicePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraInvoicePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/invoice",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<TraInvoicePT> updateInvoiceUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "invoiceDTO" ,required=true ) @RequestBody TraInvoicePT invoiceDTO);


    @ApiOperation(value = "updateItemByNetworShortcutAndLibraryPrefix", notes = "", response = LibItemPT.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibItemPT.class),
        @ApiResponse(code = 201, message = "Created", response = LibItemPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibItemPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibItemPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibItemPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/library/{libraryPrefix}/item",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<LibItemPT> updateItemByNetworShortcutAndLibraryPrefixUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "libraryPrefix",required=true ) @PathVariable("libraryPrefix") String libraryPrefix,
        @ApiParam(value = "mediaItem" ,required=true ) @RequestBody LibItemPT mediaItem);


    @ApiOperation(value = "updateLeadActivity", notes = "", response = CrmTaskPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmTaskPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/lead/{shortName}/task",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<CrmTaskPT> updateLeadActivityUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName,
        @ApiParam(value = "crmActivityPT" ,required=true ) @RequestBody CrmTaskPT crmActivityPT);


    @ApiOperation(value = "updateLeadSource", notes = "", response = ConfLeadSourcePT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfLeadSourcePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/leadsource/",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<ConfLeadSourcePT> updateLeadSourceUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "leadStatus" ,required=true ) @RequestBody ConfLeadSourcePT leadStatus);


    @ApiOperation(value = "updateLead", notes = "", response = CrmLeadPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmLeadPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmLeadPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmLeadPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmLeadPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmLeadPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/lead",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<CrmLeadPT> updateLeadUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "crmLeadPT" ,required=true ) @RequestBody CrmLeadPT crmLeadPT);


    @ApiOperation(value = "updateLibraryForChannel", notes = "", response = LibraryPT.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibraryPT.class),
        @ApiResponse(code = 201, message = "Created", response = LibraryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibraryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibraryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibraryPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/library",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<LibraryPT> updateLibraryForChannelUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "library" ,required=true ) @RequestBody LibraryPT library);


    @ApiOperation(value = "updateLibraryProcessingConfiguration", notes = "", response = ConfLibraryProcessingConfigurationPT.class, tags={ "LIBRARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfLibraryProcessingConfigurationPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfLibraryProcessingConfigurationPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfLibraryProcessingConfigurationPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfLibraryProcessingConfigurationPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfLibraryProcessingConfigurationPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/library/{shortName}/processing",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<ConfLibraryProcessingConfigurationPT> updateLibraryProcessingConfigurationUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName,
        @ApiParam(value = "library" ,required=true ) @RequestBody ConfLibraryProcessingConfigurationPT library);


    @ApiOperation(value = "updateLibrary", notes = "", response = LibraryPT.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibraryPT.class),
        @ApiResponse(code = 201, message = "Created", response = LibraryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibraryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibraryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibraryPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/library",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<LibraryPT> updateLibraryUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "library" ,required=true ) @RequestBody LibraryPT library);


    @ApiOperation(value = "updateMarkerConfiguration", notes = "", response = ConfMarkerConfigurationPT.class, tags={ "CONFIGURATION","CORE", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfMarkerConfigurationPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/library/marker",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<ConfMarkerConfigurationPT> updateMarkerConfigurationUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "markerConfigurationDTO" ,required=true ) @RequestBody ConfMarkerConfigurationPT markerConfigurationDTO);


    @ApiOperation(value = "updateMusicLogConfiguration", notes = "", response = ConfMusicLogPT.class, tags={ "CONFIGURATION","DICTIONARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfMusicLogPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfMusicLogPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfMusicLogPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfMusicLogPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfMusicLogPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/log/music",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<ConfMusicLogPT> updateMusicLogConfigurationUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "confMusicLogPT" ,required=true ) @RequestBody ConfMusicLogPT confMusicLogPT);


    @ApiOperation(value = "updateNetwork", notes = "", response = CoreNetworkPT.class, tags={ "NETWORK","CORE", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreNetworkPT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreNetworkPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreNetworkPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreNetworkPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreNetworkPT.class) })
    @RequestMapping(value = "/api/network",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<CoreNetworkPT> updateNetworkUsingPUT(@ApiParam(value = "network" ,required=true ) @RequestBody CoreNetworkPT network);


    @ApiOperation(value = "updateNotification", notes = "", response = CoreNetworkPT.class, tags={ "CORE", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreNetworkPT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreNetworkPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreNetworkPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreNetworkPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreNetworkPT.class) })
    @RequestMapping(value = "/api/account/notification",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<CoreNetworkPT> updateNotoficationUsingPUT(@ApiParam(value = "notification" ,required=true ) @RequestBody CoreNotificationsPT notification);


    @ApiOperation(value = "updateOpportunityActivity", notes = "", response = CrmTaskPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmTaskPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/opportunity/{shortName}/task",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<CrmTaskPT> updateOpportunityActivityUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName,
        @ApiParam(value = "crmActivityPT" ,required=true ) @RequestBody CrmTaskPT crmActivityPT);


    @ApiOperation(value = "updateOpportunity", notes = "", response = CrmOpportunityPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmOpportunityPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmOpportunityPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmOpportunityPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmOpportunityPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmOpportunityPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/opportunity",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<CrmOpportunityPT> updateOpportunityUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "crmOpportunityPT" ,required=true ) @RequestBody CrmOpportunityPT crmOpportunityPT);


    @ApiOperation(value = "updatePerson", notes = "", response = ConfPersonPT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfPersonPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfPersonPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfPersonPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfPersonPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfPersonPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/people",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<ConfPersonPT> updatePersonUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "personDTO" ,required=true ) @RequestBody ConfPersonPT personDTO);


    @ApiOperation(value = "updatePropertyKey", notes = "", response = CoreKeyPT.class, tags={ "CONFIGURATION","DICTIONARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreKeyPT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreKeyPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreKeyPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreKeyPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreKeyPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/property/key",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<CoreKeyPT> updatePropertyKeyUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "propertyKeyDTO" ,required=true ) @RequestBody CoreKeyPT propertyKeyDTO);


    @ApiOperation(value = "updatePropertyValue", notes = "", response = CoreValuePT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreValuePT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreValuePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreValuePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreValuePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreValuePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/property/value",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<CoreValuePT> updatePropertyValueUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "propertyValueDTO" ,required=true ) @RequestBody CoreValuePT propertyValueDTO);


    @ApiOperation(value = "updateRange", notes = "", response = CoreRangePT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreRangePT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreRangePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreRangePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreRangePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreRangePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/range",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<CoreRangePT> updateRangeUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "personDTO" ,required=true ) @RequestBody CoreRangePT personDTO);


    @ApiOperation(value = "updateSchedulerPlaylistForChannel", notes = "", response = SchPlaylistPT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchPlaylistPT.class),
        @ApiResponse(code = 201, message = "Created", response = SchPlaylistPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchPlaylistPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchPlaylistPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchPlaylistPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/playlist",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<SchPlaylistPT> updateSchedulerPlaylisForChanneltUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "schdeulerTemplate" ,required=true ) @RequestBody SchPlaylistPT schdeulerTemplate);


    @ApiOperation(value = "updateSchedulerPlaylist", notes = "", response = SchPlaylistPT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchPlaylistPT.class),
        @ApiResponse(code = 201, message = "Created", response = SchPlaylistPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchPlaylistPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchPlaylistPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchPlaylistPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/scheduler/playlist",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<SchPlaylistPT> updateSchedulerPlaylistUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "schdeulerTemplate" ,required=true ) @RequestBody SchPlaylistPT schdeulerTemplate);


    @ApiOperation(value = "updateSchedulerTemplatesForChannel", notes = "", response = SchTemplatePT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 201, message = "Created", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/templates",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<SchTemplatePT> updateSchedulerTemplatesForChannelUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "schdeulerTemplate" ,required=true ) @RequestBody SchTemplatePT schdeulerTemplate);


    @ApiOperation(value = "updateSchedulerTemplates", notes = "", response = SchTemplatePT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 201, message = "Created", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/scheduler/templates",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<SchTemplatePT> updateSchedulerTemplatesUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "schdeulerTemplate" ,required=true ) @RequestBody SchTemplatePT schdeulerTemplate);


    @ApiOperation(value = "updateSize", notes = "", response = CoreSizePT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreSizePT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreSizePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreSizePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreSizePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreSizePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/size",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<CoreSizePT> updateSizeUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "personDTO" ,required=true ) @RequestBody CoreSizePT personDTO);


    @ApiOperation(value = "updateTag", notes = "", response = ConfTagPT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfTagPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfTagPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfTagPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfTagPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfTagPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/core/dictionary/tag",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<ConfTagPT> updateTagUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "tagDTO" ,required=true ) @RequestBody ConfTagPT tagDTO);


    @ApiOperation(value = "updateTax", notes = "", response = ConfTaxPT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfTaxPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfTaxPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfTaxPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfTaxPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfTaxPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/tax",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<ConfTaxPT> updateTaxUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "taxDTO" ,required=true ) @RequestBody ConfTaxPT taxDTO);


    @ApiOperation(value = "updateTrafficCustomerUsingPUT", notes = "", response = TraCustomerPT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCustomerPT.class),
        @ApiResponse(code = 201, message = "Created", response = TraCustomerPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCustomerPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCustomerPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCustomerPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/customer/",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<TraCustomerPT> updateTrafficCustomerUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "CustomerVM" ,required=true ) @RequestBody TraCustomerPT customer);


    @ApiOperation(value = "updateTrafficTemplatesForChannel", notes = "", response = SchTemplatePT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 201, message = "Created", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/traffic/templates",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<SchTemplatePT> updateTrafficTemplatesForChannelUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
        @ApiParam(value = "schdeulerTemplate" ,required=true ) @RequestBody SchTemplatePT schdeulerTemplate);


    @ApiOperation(value = "updateTrafficTemplates", notes = "", response = SchTemplatePT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 201, message = "Created", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/templates",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<SchTemplatePT> updateTrafficTemplatesUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "schdeulerTemplate" ,required=true ) @RequestBody SchTemplatePT schdeulerTemplate);


    @ApiOperation(value = "updateLeadStatus", notes = "", response = ConfLeadStatusPT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfLeadStatusPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/leadstatus/",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<ConfLeadStatusPT> updateleadStatusUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "leadStatus" ,required=true ) @RequestBody ConfLeadStatusPT leadStatus);

}
