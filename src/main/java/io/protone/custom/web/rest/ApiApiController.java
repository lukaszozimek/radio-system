package io.protone.custom.web.rest;

import io.protone.custom.service.dto.*;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class ApiApiController implements ApiApi {

    public ResponseEntity<String> activateAccountUsingGET(@ApiParam(value = "key", required = true) @RequestParam(value = "key", required = true) String key) {
        // do some magic!
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    public ResponseEntity<Object> changePasswordUsingPOST(@ApiParam(value = "password", required = true) @RequestBody String password) {
        // do some magic!
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    public ResponseEntity<SchPlaylistPT> creatSchedulerPlaylistForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                   @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchPlaylistPT schdeulerTemplate) {
        // do some magic!
        return new ResponseEntity<SchPlaylistPT>(HttpStatus.OK);
    }

    public ResponseEntity<SchPlaylistPT> creatSchedulerPlaylistUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                         @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchPlaylistPT schdeulerTemplate) {
        // do some magic!
        return new ResponseEntity<SchPlaylistPT>(HttpStatus.OK);
    }

    public ResponseEntity<SchTemplatePT> creatSchedulerTemplatesForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                    @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchTemplatePT schdeulerTemplate) {
        // do some magic!
        return new ResponseEntity<SchTemplatePT>(HttpStatus.OK);
    }

    public ResponseEntity<SchTemplatePT> creatSchedulerTemplatesUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchTemplatePT schdeulerTemplate) {
        // do some magic!
        return new ResponseEntity<SchTemplatePT>(HttpStatus.OK);
    }

    public ResponseEntity<SchTemplatePT> creatTrafficTemplatesForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                  @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchTemplatePT schdeulerTemplate) {
        // do some magic!
        return new ResponseEntity<SchTemplatePT>(HttpStatus.OK);
    }

    public ResponseEntity<SchTemplatePT> creatTrafficTemplatesUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchTemplatePT schdeulerTemplate) {
        // do some magic!
        return new ResponseEntity<SchTemplatePT>(HttpStatus.OK);
    }

    public ResponseEntity<TraAdvertisementPT> createAdvertisementUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "advertisementDTO", required = true) @RequestBody TraAdvertisementPT advertisementDTO) {
        // do some magic!
        return new ResponseEntity<TraAdvertisementPT>(HttpStatus.OK);
    }

    public ResponseEntity<TraOrderPT> createAnOrderUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                             @ApiParam(value = "anOrderDTO", required = true) @RequestBody TraOrderPT anOrderDTO) {
        // do some magic!
        return new ResponseEntity<TraOrderPT>(HttpStatus.OK);
    }

    public ResponseEntity<CoreAreaPT> createAreaUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "personDTO", required = true) @RequestBody CoreAreaPT personDTO) {
        // do some magic!
        return new ResponseEntity<CoreAreaPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfBlockPT> createBlockTypesUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "confBlockPT", required = true) @RequestBody ConfBlockPT confBlockPT) {
        // do some magic!
        return new ResponseEntity<ConfBlockPT>(HttpStatus.OK);
    }

    public ResponseEntity<TraCampaignPT> createCampaignUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "campaignDTO", required = true) @RequestBody TraCampaignPT campaignDTO) {
        // do some magic!
        return new ResponseEntity<TraCampaignPT>(HttpStatus.OK);
    }

    public ResponseEntity<CoreChannelPT> createChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "channelDTO", required = true) @RequestBody CoreChannelPT channelDTO) {
        // do some magic!
        return new ResponseEntity<CoreChannelPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfCommercialLogPT> createCommercialLogConfigurationUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                         @ApiParam(value = "confMusicLogPT", required = true) @RequestBody ConfCommercialLogPT confMusicLogPT) {
        // do some magic!
        return new ResponseEntity<ConfCommercialLogPT>(HttpStatus.OK);
    }

    public ResponseEntity<CrmTaskPT> createContactActivityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                    @ApiParam(value = "crmActivityPT", required = true) @RequestBody CrmTaskPT crmActivityPT) {
        // do some magic!
        return new ResponseEntity<CrmTaskPT>(HttpStatus.OK);
    }

    public ResponseEntity<CrmContactPT> createContactUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "customerPT", required = true) @RequestBody CrmContactPT customerPT) {
        // do some magic!
        return new ResponseEntity<CrmContactPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfCountryPt> createCountryUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "personDTO", required = true) @RequestBody ConfCountryPt personDTO) {
        // do some magic!
        return new ResponseEntity<ConfCountryPt>(HttpStatus.OK);
    }

    public ResponseEntity<ConfCrmStagePT> createCrmStageUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "crmStage", required = true) @RequestBody ConfCrmStagePT crmStage) {
        // do some magic!
        return new ResponseEntity<ConfCrmStagePT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfCurrencyPT> createCurrencyUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "taxDTO", required = true) @RequestBody ConfCurrencyPT taxDTO) {
        // do some magic!
        return new ResponseEntity<ConfCurrencyPT>(HttpStatus.OK);
    }

    public ResponseEntity<CrmTaskPT> createCustomerActivityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                     @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                     @ApiParam(value = "crmActivityPT", required = true) @RequestBody CrmTaskPT crmActivityPT) {
        // do some magic!
        return new ResponseEntity<CrmTaskPT>(HttpStatus.OK);
    }

    public ResponseEntity<CrmAccountPT> createCustomerUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "customerPT", required = true) @RequestBody CrmAccountPT customerPT) {
        // do some magic!
        return new ResponseEntity<CrmAccountPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfIndustryPT> createIndustryUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "industryDTO", required = true) @RequestBody ConfIndustryPT industryDTO) {
        // do some magic!
        return new ResponseEntity<ConfIndustryPT>(HttpStatus.OK);
    }

    public ResponseEntity<TraInvoicePT> createInvoiceUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "invoiceDTO", required = true) @RequestBody TraInvoicePT invoiceDTO) {
        // do some magic!
        return new ResponseEntity<TraInvoicePT>(HttpStatus.OK);
    }

    public ResponseEntity<CrmTaskPT> createLeadActivityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                 @ApiParam(value = "crmActivityPT", required = true) @RequestBody CrmTaskPT crmActivityPT) {
        // do some magic!
        return new ResponseEntity<CrmTaskPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfLeadStatusPT> createLeadStatusUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "leadStatus", required = true) @RequestBody ConfLeadStatusPT leadStatus) {
        // do some magic!
        return new ResponseEntity<ConfLeadStatusPT>(HttpStatus.OK);
    }

    public ResponseEntity<CrmLeadPT> createLeadUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "crmLeadPT", required = true) @RequestBody CrmLeadPT crmLeadPT) {
        // do some magic!
        return new ResponseEntity<CrmLeadPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfLeadSourcePT> createLeadsourceUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "leadStatus", required = true) @RequestBody ConfLeadSourcePT leadStatus) {
        // do some magic!
        return new ResponseEntity<ConfLeadSourcePT>(HttpStatus.OK);
    }

    public ResponseEntity<LibraryPT> createLibraryForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                      @ApiParam(value = "library", required = true) @RequestBody LibraryPT library) {
        // do some magic!
        return new ResponseEntity<LibraryPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfLibraryProcessingConfigurationPT> createLibraryProcessingConfigurationUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                              @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                                                              @ApiParam(value = "library", required = true) @RequestBody ConfLibraryProcessingConfigurationPT library) {
        // do some magic!
        return new ResponseEntity<ConfLibraryProcessingConfigurationPT>(HttpStatus.OK);
    }

    public ResponseEntity<LibraryPT> createLibraryUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                            @ApiParam(value = "library", required = true) @RequestBody LibraryPT library) {
        // do some magic!
        return new ResponseEntity<LibraryPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfMarkerConfigurationPT> createMarkerConfigurationUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                        @ApiParam(value = "markerConfigurationPT", required = true) @RequestBody ConfMarkerConfigurationPT markerConfigurationPT) {
        // do some magic!
        return new ResponseEntity<ConfMarkerConfigurationPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfMusicLogPT> createMusicLogConfigurationUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                               @ApiParam(value = "confMusicLogPT", required = true) @RequestBody ConfMusicLogPT confMusicLogPT) {
        // do some magic!
        return new ResponseEntity<ConfMusicLogPT>(HttpStatus.OK);
    }

    public ResponseEntity<CoreNetworkPT> createNetworkUsingPOST(@ApiParam(value = "network", required = true) @RequestBody CoreNetworkPT network) {
        // do some magic!
        return new ResponseEntity<CoreNetworkPT>(HttpStatus.OK);
    }

    public ResponseEntity<CrmTaskPT> createOpportunityActivityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                        @ApiParam(value = "crmActivityPT", required = true) @RequestBody CrmTaskPT crmActivityPT) {
        // do some magic!
        return new ResponseEntity<CrmTaskPT>(HttpStatus.OK);
    }

    public ResponseEntity<CrmOpportunityPT> createOpportunityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "crmOpportunityPT", required = true) @RequestBody CrmOpportunityPT crmOpportunityPT) {
        // do some magic!
        return new ResponseEntity<CrmOpportunityPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfPersonPT> createPersonUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "personDTO", required = true) @RequestBody ConfPersonPT personDTO) {
        // do some magic!
        return new ResponseEntity<ConfPersonPT>(HttpStatus.OK);
    }

    public ResponseEntity<CoreKeyPT> createPropertyKeyUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "propertyKeyDTO", required = true) @RequestBody CoreKeyPT propertyKeyDTO) {
        // do some magic!
        return new ResponseEntity<CoreKeyPT>(HttpStatus.OK);
    }

    public ResponseEntity<CoreValuePT> createPropertyValueUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "propertyValueDTO", required = true) @RequestBody CoreValuePT propertyValueDTO) {
        // do some magic!
        return new ResponseEntity<CoreValuePT>(HttpStatus.OK);
    }

    public ResponseEntity<CoreRangePT> createRangeUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                            @ApiParam(value = "personDTO", required = true) @RequestBody CoreRangePT personDTO) {
        // do some magic!
        return new ResponseEntity<CoreRangePT>(HttpStatus.OK);
    }

    public ResponseEntity<CoreSizePT> createSizeUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "personDTO", required = true) @RequestBody CoreSizePT personDTO) {
        // do some magic!
        return new ResponseEntity<CoreSizePT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfTagPT> createTagUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "tagDTO", required = true) @RequestBody ConfTagPT tagDTO) {
        // do some magic!
        return new ResponseEntity<ConfTagPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfTaxPT> createTaxUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "taxDTO", required = true) @RequestBody ConfTaxPT taxDTO) {
        // do some magic!
        return new ResponseEntity<ConfTaxPT>(HttpStatus.OK);
    }

    public ResponseEntity<TraCustomerPT> createTrafficCustomerUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "customerVM", required = true) @RequestBody TraCustomerPT customer) {
        // do some magic!
        return new ResponseEntity<TraCustomerPT>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteAdvertisementUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "idx", required = true) @PathVariable("idx") Long idx) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteAnOrderUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteAreaUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                      @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteBlockTypeUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                           @ApiParam(value = "blockShortName", required = true) @PathVariable("blockShortName") String blockShortName) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteCampaignUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteCommercialLogConfigurationUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteContactActivityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                 @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteContactUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteCountryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteCrmStageUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteCurrencyUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteCustomerActivityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                  @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteCustomeryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                           @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteIndustryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "industryName", required = true) @PathVariable("industryName") String industryName) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteInvoiceUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteItemByNetworShortcutAndChannelShortcutAndLibraryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                  @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                                  @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteItemByNetworShortcutAndLibrarUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                               @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                               @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteItemsByNetworShortcutAndChannelShortcutAndLibraryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                   @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteLeadActivityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                              @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteLeadStatusUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                            @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteLeadUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                      @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteLeadsourceUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                            @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteLibraryForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                   @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteLibraryProcessingConfigurationUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteLibraryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteMarkerConfigurationUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                     @ApiParam(value = "markerName", required = true) @PathVariable("markerName") String markerName) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteMusicLogConfigurationUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteNetworkUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteOpportunityActivityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                     @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                     @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteOpportunityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                             @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deletePersonUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deletePropertyKeyUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                             @ApiParam(value = "keyName", required = true) @PathVariable("keyName") String keyName) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteRangeUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                       @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteSchedulerPlaylistForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                             @ApiParam(value = "date", required = true) @PathVariable("date") String date) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteSchedulerPlaylistUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "date", required = true) @PathVariable("date") String date) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteSchedulerTemplateForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                             @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteSchedulerTemplateUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteSizeUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                      @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteTaxUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                     @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteTrafficCustomerUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteTrafficTemplateForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                           @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteTrafficTemplateUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<String> finishPasswordResetUsingPOST(@ApiParam(value = "coreKeyAndPasswordPT", required = true) @RequestBody CoreKeyAndPasswordPT coreKeyAndPasswordPT) {
        // do some magic!
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    public ResponseEntity<CoreUserPT> getAccountUsingGET() {
        // do some magic!
        return new ResponseEntity<CoreUserPT>(HttpStatus.OK);
    }

    public ResponseEntity<TraAdvertisementPT> getAdvertisementUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "idx", required = true) @PathVariable("idx") Long idx) {
        // do some magic!
        return new ResponseEntity<TraAdvertisementPT>(HttpStatus.OK);
    }

    public ResponseEntity<List<TraAdvertisementPT>> getAllAdvertisementsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<TraAdvertisementPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<TraOrderPT>> getAllAnOrdersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<TraOrderPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<CoreAreaPT>> getAllAreaUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<CoreAreaPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<ConfBlockPT>> getAllBlockTypesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<ConfBlockPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<TraCampaignPT>> getAllCampaignsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<TraCampaignPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<CoreChannelPT>> getAllChannelsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<CoreChannelPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<CrmTaskPT>> getAllContactActivitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<List<CrmTaskPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<CrmContactPT>> getAllContactUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<CrmContactPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<ConfCountryPt>> getAllCountriesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<ConfCountryPt>>(HttpStatus.OK);
    }

    public ResponseEntity<List<ConfCrmStagePT>> getAllCrmStagesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<ConfCrmStagePT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<ConfCurrencyPT>> getAllCurrencyUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<ConfCurrencyPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<CrmTaskPT>> getAllCustomerActivitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                            @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<List<CrmTaskPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<TraCampaignPT>> getAllCustomerCampaignsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                               @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut) {
        // do some magic!
        return new ResponseEntity<List<TraCampaignPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<TraOrderPT>> getAllCustomerOrdersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                         @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut) {
        // do some magic!
        return new ResponseEntity<List<TraOrderPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<TraCustomerAdvertismentsPT>> getAllCustomersAdvertismentsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                 @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut) {
        // do some magic!
        return new ResponseEntity<List<TraCustomerAdvertismentsPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<CrmAccountPT>> getAllCustomersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<CrmAccountPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<ConfIndustryPT>> getAllIndustriesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<ConfIndustryPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<TraInvoicePT>> getAllInvoicesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<TraInvoicePT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<LibItemPT>> getAllItemsByNetworShortcutAndLibraryPrefixUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                               @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix) {
        // do some magic!
        return new ResponseEntity<List<LibItemPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<CrmTaskPT>> getAllLeadActivitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<List<CrmTaskPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<ConfLeadStatusPT>> getAllLeadStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<ConfLeadStatusPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<CrmLeadPT>> getAllLeadsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<CrmLeadPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<ConfLeadSourcePT>> getAllLeadsourceUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<ConfLeadSourcePT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<LibraryPT>> getAllLibrariesForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut) {
        // do some magic!
        return new ResponseEntity<List<LibraryPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<LibraryPT>> getAllLibrariesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<LibraryPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<ConfMarkerConfigurationPT>> getAllMarkerConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<ConfMarkerConfigurationPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<CoreNetworkPT>> getAllNetworksUsingGET() {
        // do some magic!
        return new ResponseEntity<List<CoreNetworkPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<CoreNotificationsPT>> getAllNotificationForUserUsingGET(@ApiParam(value = "mail", required = true) @RequestBody String mail) {
        // do some magic!
        return new ResponseEntity<List<CoreNotificationsPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<CrmOpportunityPT>> getAllOpportunitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<CrmOpportunityPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<CrmTaskPT>> getAllOpportunityActivitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                               @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<List<CrmTaskPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<ConfPersonPT>> getAllPeopleUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<ConfPersonPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<CoreKeyPT>> getAllPropertyKeysUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<CoreKeyPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<CoreValuePT>> getAllPropertyValuesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<CoreValuePT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<CoreRangePT>> getAllRangeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<CoreRangePT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<SchPlaylistPT>> getAllSchedulerPlaylistForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut) {
        // do some magic!
        return new ResponseEntity<List<SchPlaylistPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<SchPlaylistPT>> getAllSchedulerPlaylistUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<SchPlaylistPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<SchTemplatePT>> getAllSchedulerTemplatesForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut) {
        // do some magic!
        return new ResponseEntity<List<SchTemplatePT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<SchTemplatePT>> getAllSchedulerTemplatesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<SchTemplatePT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<CoreSizePT>> getAllSizeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<CoreSizePT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<ConfTagPT>> getAllTagsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<ConfTagPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<ConfTaxPT>> getAllTaxesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<ConfTaxPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<TraCustomerPT>> getAllTrafficCustomersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<TraCustomerPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<TraCustomerOrdersPT>> getAllTrafficInvoicesForCustomerGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                         @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut) {
        // do some magic!
        return new ResponseEntity<List<TraCustomerOrdersPT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<SchTemplatePT>> getAllTrafficTemplatesForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut) {
        // do some magic!
        return new ResponseEntity<List<SchTemplatePT>>(HttpStatus.OK);
    }

    public ResponseEntity<List<SchTemplatePT>> getAllTrafficTemplatesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<List<SchTemplatePT>>(HttpStatus.OK);
    }

    public ResponseEntity<TraOrderPT> getAnOrderUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<TraOrderPT>(HttpStatus.OK);
    }

    public ResponseEntity<CoreAreaPT> getAreaUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                      @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<CoreAreaPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfBlockPT> getBlockTypeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                            @ApiParam(value = "blockShortName", required = true) @PathVariable("blockShortName") String blockShortName) {
        // do some magic!
        return new ResponseEntity<ConfBlockPT>(HttpStatus.OK);
    }

    public ResponseEntity<TraCampaignPT> getCampaignUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                             @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<TraCampaignPT>(HttpStatus.OK);
    }

    public ResponseEntity<CoreChannelPT> getChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut) {
        // do some magic!
        return new ResponseEntity<CoreChannelPT>(HttpStatus.OK);
    }

    public ResponseEntity<Object> getCommercialLogConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    public ResponseEntity<CrmTaskPT> getContactActivityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<CrmTaskPT>(HttpStatus.OK);
    }

    public ResponseEntity<CrmContactPT> getContactUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                           @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<CrmContactPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfCountryPt> getCountryUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                            @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<ConfCountryPt>(HttpStatus.OK);
    }

    public ResponseEntity<ConfCrmStagePT> getCrmStageUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<ConfCrmStagePT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfCurrencyPT> getCurrencyUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<ConfCurrencyPT>(HttpStatus.OK);
    }

    public ResponseEntity<CrmTaskPT> getCustomerActivityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                 @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<CrmTaskPT>(HttpStatus.OK);
    }

    public ResponseEntity<CrmAccountPT> getCustomerUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                            @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<CrmAccountPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfIndustryPT> getIndustryUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "industryName", required = true) @PathVariable("industryName") String industryName) {
        // do some magic!
        return new ResponseEntity<ConfIndustryPT>(HttpStatus.OK);
    }

    public ResponseEntity<TraInvoicePT> getInvoiceUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                           @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<TraInvoicePT>(HttpStatus.OK);
    }

    public ResponseEntity<LibItemPT> getItemByNetworShortcutAndChannelShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                                @ApiParam(value = "libraryPrefix", required = true) @PathVariable("idx") String idx) {
        // do some magic!
        return new ResponseEntity<LibItemPT>(HttpStatus.OK);
    }

    public ResponseEntity<LibItemPT> getItemByNetworShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                              @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                              @ApiParam(value = "libraryPrefix", required = true) @PathVariable("idx") String idx) {
        // do some magic!
        return new ResponseEntity<LibItemPT>(HttpStatus.OK);
    }

    public ResponseEntity<LibResponseEntity> getItemStreamByNetworShortcutAndChannelShortcutAndLibraryUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                               @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                                               @ApiParam(value = "libraryPrefix", required = true) @PathVariable("idx") String idx) {
        // do some magic!
        return new ResponseEntity<LibResponseEntity>(HttpStatus.OK);
    }

    public ResponseEntity<LibResponseEntity> getItemStreamByNetworShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                            @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                            @ApiParam(value = "libraryPrefix", required = true) @PathVariable("idx") String idx) {
        // do some magic!
        return new ResponseEntity<LibResponseEntity>(HttpStatus.OK);
    }

    public ResponseEntity<LibItemPT> getItemsByNetworShortcutAndChannelShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                 @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix) {
        // do some magic!
        return new ResponseEntity<LibItemPT>(HttpStatus.OK);
    }

    public ResponseEntity<CrmTaskPT> getLeadActivityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                             @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                             @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<CrmTaskPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfLeadSourcePT> getLeadSourceUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<ConfLeadSourcePT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfLeadStatusPT> getLeadStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<ConfLeadStatusPT>(HttpStatus.OK);
    }

    public ResponseEntity<CrmLeadPT> getLeadUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                     @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<CrmLeadPT>(HttpStatus.OK);
    }

    public ResponseEntity<LibraryPT> getLibraryForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                  @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix) {
        // do some magic!
        return new ResponseEntity<LibraryPT>(HttpStatus.OK);
    }

    public ResponseEntity<Object> getLibraryProcessingConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                            @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    public ResponseEntity<LibraryPT> getLibraryUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix) {
        // do some magic!
        return new ResponseEntity<LibraryPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfMarkerConfigurationPT> getMarkerConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                    @ApiParam(value = "markerName", required = true) @PathVariable("markerName") String markerName) {
        // do some magic!
        return new ResponseEntity<ConfMarkerConfigurationPT>(HttpStatus.OK);
    }

    public ResponseEntity<Object> getMusicLogConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    public ResponseEntity<CoreNetworkPT> getNetworkUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        // do some magic!
        return new ResponseEntity<CoreNetworkPT>(HttpStatus.OK);
    }

    public ResponseEntity<CrmTaskPT> getOpportunityActivityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                    @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<CrmTaskPT>(HttpStatus.OK);
    }

    public ResponseEntity<CrmOpportunityPT> getOpportunityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<CrmOpportunityPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfPersonPT> getPersonUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<ConfPersonPT>(HttpStatus.OK);
    }

    public ResponseEntity<CoreKeyPT> getPropertyKeyUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                            @ApiParam(value = "keyName", required = true) @PathVariable("keyName") String keyName) {
        // do some magic!
        return new ResponseEntity<CoreKeyPT>(HttpStatus.OK);
    }

    public ResponseEntity<CoreRangePT> getRangeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<CoreRangePT>(HttpStatus.OK);
    }

    public ResponseEntity<SchPlaylistPT> getSchedulerPlaylistForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                @ApiParam(value = "date", required = true) @PathVariable("date") String date) {
        // do some magic!
        return new ResponseEntity<SchPlaylistPT>(HttpStatus.OK);
    }

    public ResponseEntity<SchPlaylistPT> getSchedulerPlaylistUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "date", required = true) @PathVariable("date") String date) {
        // do some magic!
        return new ResponseEntity<SchPlaylistPT>(HttpStatus.OK);
    }

    public ResponseEntity<SchTemplatePT> getSchedulerTemplateForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<SchTemplatePT>(HttpStatus.OK);
    }

    public ResponseEntity<SchTemplatePT> getSchedulerTemplateUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<SchTemplatePT>(HttpStatus.OK);
    }

    public ResponseEntity<CoreSizePT> getSizeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                      @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<CoreSizePT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfTaxPT> getTaxUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                    @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<ConfTaxPT>(HttpStatus.OK);
    }

    public ResponseEntity<TraCustomerPT> getTrafficCustomerUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut) {
        // do some magic!
        return new ResponseEntity<TraCustomerPT>(HttpStatus.OK);
    }

    public ResponseEntity<SchTemplatePT> getTrafficTemplateForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                              @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<SchTemplatePT>(HttpStatus.OK);
    }

    public ResponseEntity<SchTemplatePT> getTrafficTemplateUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<SchTemplatePT>(HttpStatus.OK);
    }

    public ResponseEntity<Void> loadPlaylistUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                      @ApiParam(value = "date", required = true) @PathVariable("date") String date) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> notifyAboutUnpaidInvoiceCustomerUsingGET(@ApiParam(value = "cutomerId", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                         @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut,
                                                                         @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> notifyAboutUnpaidInvoiceUsingGET(@ApiParam(value = "cutomerId", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<TraOrderPT> notifyCustomerAboutUnpaidOrderUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                              @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<TraOrderPT>(HttpStatus.OK);
    }

    public ResponseEntity<Void> pauseDeckUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                   @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> pauseShotUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                   @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> playDeckUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                  @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> playShotUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                  @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> refreshPlaylistUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                         @ApiParam(value = "date", required = true) @PathVariable("date") String date) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Object> requestPasswordResetUsingPOST(@ApiParam(value = "mail", required = true) @RequestBody String mail) {
        // do some magic!
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    public ResponseEntity<String> saveAccountUsingPOST(@ApiParam(value = "coreUserPT", required = true) @RequestBody CoreUserPT coreUserPT) {
        // do some magic!
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    public ResponseEntity<Void> stopDeckUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                  @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> stopShotUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                  @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> switchPlayoutModeUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                           @ApiParam(value = "modeType", required = true) @PathVariable("modeType") String modeType) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> unloadDeckUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                    @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> unloadShotUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                    @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<TraAdvertisementPT> updateAdvertisementUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "advertisementDTO", required = true) @RequestBody TraAdvertisementPT advertisementDTO) {
        // do some magic!
        return new ResponseEntity<TraAdvertisementPT>(HttpStatus.OK);
    }

    public ResponseEntity<TraOrderPT> updateAnOrderUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                            @ApiParam(value = "anOrderDTO", required = true) @RequestBody TraOrderPT anOrderDTO) {
        // do some magic!
        return new ResponseEntity<TraOrderPT>(HttpStatus.OK);
    }

    public ResponseEntity<CoreAreaPT> updateAreaUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "personDTO", required = true) @RequestBody CoreAreaPT personDTO) {
        // do some magic!
        return new ResponseEntity<CoreAreaPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfBlockPT> updateBlockTypesUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "confBlockPT", required = true) @RequestBody ConfBlockPT confBlockPT) {
        // do some magic!
        return new ResponseEntity<ConfBlockPT>(HttpStatus.OK);
    }

    public ResponseEntity<TraCampaignPT> updateCampaignUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "campaignDTO", required = true) @RequestBody TraCampaignPT campaignDTO) {
        // do some magic!
        return new ResponseEntity<TraCampaignPT>(HttpStatus.OK);
    }

    public ResponseEntity<CoreChannelPT> updateChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "channelDTO", required = true) @RequestBody CoreChannelPT channelDTO) {
        // do some magic!
        return new ResponseEntity<CoreChannelPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfCommercialLogPT> updateCommercialLogConfigurationUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                        @ApiParam(value = "confMusicLogPT", required = true) @RequestBody ConfCommercialLogPT confMusicLogPT) {
        // do some magic!
        return new ResponseEntity<ConfCommercialLogPT>(HttpStatus.OK);
    }

    public ResponseEntity<CrmTaskPT> updateContactActivityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                   @ApiParam(value = "crmActivityPT", required = true) @RequestBody CrmTaskPT crmActivityPT) {
        // do some magic!
        return new ResponseEntity<CrmTaskPT>(HttpStatus.OK);
    }

    public ResponseEntity<CrmContactPT> updateContactUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "customerPT", required = true) @RequestBody CrmContactPT customeryPT) {
        // do some magic!
        return new ResponseEntity<CrmContactPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfCountryPt> updateCountryUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "personDTO", required = true) @RequestBody ConfCountryPt personDTO) {
        // do some magic!
        return new ResponseEntity<ConfCountryPt>(HttpStatus.OK);
    }

    public ResponseEntity<ConfCrmStagePT> updateCrmStageUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "crmStage", required = true) @RequestBody ConfCrmStagePT crmStage) {
        // do some magic!
        return new ResponseEntity<ConfCrmStagePT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfCurrencyPT> updateCurrencyUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "taxDTO", required = true) @RequestBody ConfCurrencyPT taxDTO) {
        // do some magic!
        return new ResponseEntity<ConfCurrencyPT>(HttpStatus.OK);
    }

    public ResponseEntity<CrmTaskPT> updateCustomerActivityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                    @ApiParam(value = "crmActivityPT", required = true) @RequestBody CrmTaskPT crmActivityPT) {
        // do some magic!
        return new ResponseEntity<CrmTaskPT>(HttpStatus.OK);
    }

    public ResponseEntity<CrmAccountPT> updateCustomerUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "customerPT", required = true) @RequestBody CrmAccountPT customeryPT) {
        // do some magic!
        return new ResponseEntity<CrmAccountPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfIndustryPT> updateIndustryUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "industryDTO", required = true) @RequestBody ConfIndustryPT industryDTO) {
        // do some magic!
        return new ResponseEntity<ConfIndustryPT>(HttpStatus.OK);
    }

    public ResponseEntity<TraInvoicePT> updateInvoiceUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "invoiceDTO", required = true) @RequestBody TraInvoicePT invoiceDTO) {
        // do some magic!
        return new ResponseEntity<TraInvoicePT>(HttpStatus.OK);
    }

    public ResponseEntity<LibItemPT> updateItemByNetworShortcutAndLibraryPrefixUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                        @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                        @ApiParam(value = "mediaItem", required = true) @RequestBody LibItemPT mediaItem) {
        // do some magic!
        return new ResponseEntity<LibItemPT>(HttpStatus.OK);
    }

    public ResponseEntity<CrmTaskPT> updateLeadActivityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                @ApiParam(value = "crmActivityPT", required = true) @RequestBody CrmTaskPT crmActivityPT) {
        // do some magic!
        return new ResponseEntity<CrmTaskPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfLeadSourcePT> updateLeadSourceUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                     @ApiParam(value = "leadStatus", required = true) @RequestBody ConfLeadSourcePT leadStatus) {
        // do some magic!
        return new ResponseEntity<ConfLeadSourcePT>(HttpStatus.OK);
    }

    public ResponseEntity<CrmLeadPT> updateLeadUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "crmLeadPT", required = true) @RequestBody CrmLeadPT crmLeadPT) {
        // do some magic!
        return new ResponseEntity<CrmLeadPT>(HttpStatus.OK);
    }

    public ResponseEntity<LibraryPT> updateLibraryForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                     @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                     @ApiParam(value = "library", required = true) @RequestBody LibraryPT library) {
        // do some magic!
        return new ResponseEntity<LibraryPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfLibraryProcessingConfigurationPT> updateLibraryProcessingConfigurationUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                             @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                                                             @ApiParam(value = "library", required = true) @RequestBody ConfLibraryProcessingConfigurationPT library) {
        // do some magic!
        return new ResponseEntity<ConfLibraryProcessingConfigurationPT>(HttpStatus.OK);
    }

    public ResponseEntity<LibraryPT> updateLibraryUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                           @ApiParam(value = "library", required = true) @RequestBody LibraryPT library) {
        // do some magic!
        return new ResponseEntity<LibraryPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfMarkerConfigurationPT> updateMarkerConfigurationUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                       @ApiParam(value = "markerConfigurationDTO", required = true) @RequestBody ConfMarkerConfigurationPT markerConfigurationDTO) {
        // do some magic!
        return new ResponseEntity<ConfMarkerConfigurationPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfMusicLogPT> updateMusicLogConfigurationUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                              @ApiParam(value = "confMusicLogPT", required = true) @RequestBody ConfMusicLogPT confMusicLogPT) {
        // do some magic!
        return new ResponseEntity<ConfMusicLogPT>(HttpStatus.OK);
    }

    public ResponseEntity<CoreNetworkPT> updateNetworkUsingPUT(@ApiParam(value = "network", required = true) @RequestBody CoreNetworkPT network) {
        // do some magic!
        return new ResponseEntity<CoreNetworkPT>(HttpStatus.OK);
    }

    public ResponseEntity<CoreNetworkPT> updateNotoficationUsingPUT(@ApiParam(value = "notification", required = true) @RequestBody CoreNotificationsPT notification) {
        // do some magic!
        return new ResponseEntity<CoreNetworkPT>(HttpStatus.OK);
    }

    public ResponseEntity<CrmTaskPT> updateOpportunityActivityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                       @ApiParam(value = "crmActivityPT", required = true) @RequestBody CrmTaskPT crmActivityPT) {
        // do some magic!
        return new ResponseEntity<CrmTaskPT>(HttpStatus.OK);
    }

    public ResponseEntity<CrmOpportunityPT> updateOpportunityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "crmOpportunityPT", required = true) @RequestBody CrmOpportunityPT crmOpportunityPT) {
        // do some magic!
        return new ResponseEntity<CrmOpportunityPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfPersonPT> updatePersonUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                             @ApiParam(value = "personDTO", required = true) @RequestBody ConfPersonPT personDTO) {
        // do some magic!
        return new ResponseEntity<ConfPersonPT>(HttpStatus.OK);
    }

    public ResponseEntity<CoreKeyPT> updatePropertyKeyUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "propertyKeyDTO", required = true) @RequestBody CoreKeyPT propertyKeyDTO) {
        // do some magic!
        return new ResponseEntity<CoreKeyPT>(HttpStatus.OK);
    }

    public ResponseEntity<CoreValuePT> updatePropertyValueUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "propertyValueDTO", required = true) @RequestBody CoreValuePT propertyValueDTO) {
        // do some magic!
        return new ResponseEntity<CoreValuePT>(HttpStatus.OK);
    }

    public ResponseEntity<CoreRangePT> updateRangeUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                           @ApiParam(value = "personDTO", required = true) @RequestBody CoreRangePT personDTO) {
        // do some magic!
        return new ResponseEntity<CoreRangePT>(HttpStatus.OK);
    }

    public ResponseEntity<SchPlaylistPT> updateSchedulerPlaylisForChanneltUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                   @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchPlaylistPT schdeulerTemplate) {
        // do some magic!
        return new ResponseEntity<SchPlaylistPT>(HttpStatus.OK);
    }

    public ResponseEntity<SchPlaylistPT> updateSchedulerPlaylistUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                         @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchPlaylistPT schdeulerTemplate) {
        // do some magic!
        return new ResponseEntity<SchPlaylistPT>(HttpStatus.OK);
    }

    public ResponseEntity<SchTemplatePT> updateSchedulerTemplatesForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                    @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchTemplatePT schdeulerTemplate) {
        // do some magic!
        return new ResponseEntity<SchTemplatePT>(HttpStatus.OK);
    }

    public ResponseEntity<SchTemplatePT> updateSchedulerTemplatesUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchTemplatePT schdeulerTemplate) {
        // do some magic!
        return new ResponseEntity<SchTemplatePT>(HttpStatus.OK);
    }

    public ResponseEntity<CoreSizePT> updateSizeUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "personDTO", required = true) @RequestBody CoreSizePT personDTO) {
        // do some magic!
        return new ResponseEntity<CoreSizePT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfTagPT> updateTagUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                       @ApiParam(value = "tagDTO", required = true) @RequestBody ConfTagPT tagDTO) {
        // do some magic!
        return new ResponseEntity<ConfTagPT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfTaxPT> updateTaxUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                       @ApiParam(value = "taxDTO", required = true) @RequestBody ConfTaxPT taxDTO) {
        // do some magic!
        return new ResponseEntity<ConfTaxPT>(HttpStatus.OK);
    }

    public ResponseEntity<TraCustomerPT> updateTrafficCustomerUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "CustomerVM", required = true) @RequestBody TraCustomerPT customer) {
        // do some magic!
        return new ResponseEntity<TraCustomerPT>(HttpStatus.OK);
    }

    public ResponseEntity<SchTemplatePT> updateTrafficTemplatesForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                  @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchTemplatePT schdeulerTemplate) {
        // do some magic!
        return new ResponseEntity<SchTemplatePT>(HttpStatus.OK);
    }

    public ResponseEntity<SchTemplatePT> updateTrafficTemplatesUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchTemplatePT schdeulerTemplate) {
        // do some magic!
        return new ResponseEntity<SchTemplatePT>(HttpStatus.OK);
    }

    public ResponseEntity<ConfLeadStatusPT> updateleadStatusUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                     @ApiParam(value = "leadStatus", required = true) @RequestBody ConfLeadStatusPT leadStatus) {
        // do some magic!
        return new ResponseEntity<ConfLeadStatusPT>(HttpStatus.OK);
    }

}
