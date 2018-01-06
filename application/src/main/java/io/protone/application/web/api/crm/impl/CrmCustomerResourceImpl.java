package io.protone.application.web.api.crm.impl;


import io.protone.application.web.api.crm.CrmCustomerResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.crm.api.dto.CrmAccountDTO;
import io.protone.crm.api.dto.thin.CrmAccountThinDTO;
import io.protone.crm.domain.CrmAccount;
import io.protone.crm.mapper.CrmAccountMapper;
import io.protone.crm.service.CrmCustomerService;
import io.swagger.annotations.ApiParam;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
public class CrmCustomerResourceImpl implements CrmCustomerResource {
    private final Logger log = LoggerFactory.getLogger(CrmCustomerResourceImpl.class);

    @Inject
    private CrmCustomerService crmCustomerService;

    @Inject
    private CorChannelService corChannelService;

    @Inject
    private CrmAccountMapper crmAccountMapper;

    @Override
    public ResponseEntity<CrmAccountDTO> updateCustomerWithoutAvatarUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                             @ApiParam(value = "crmAccountDTO", required = true) @Valid @RequestBody CrmAccountDTO crmAccountDTO) throws URISyntaxException, TikaException, IOException, SAXException {
        log.debug("REST request to update CrmAccount : {}, for Network: {}", crmAccountDTO, organizationShortcut);

        if (crmAccountDTO.getId() == null) {
            return createCustomerUsingPOST(organizationShortcut, channelShortcut, crmAccountDTO, null);
        }
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        CrmAccount crmAccount = crmAccountMapper.DTO2DB(crmAccountDTO, corChannel);
        CrmAccount entity = crmCustomerService.saveCustomer(crmAccount);
        CrmAccountDTO response = crmAccountMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<CrmAccountDTO> updateCustomerWithAvatarUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                           @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                           @ApiParam(value = "crmAccountDTO", required = true) @Valid @RequestPart("crmAccountDTO") CrmAccountDTO crmAccountDTO,
                                                                           @ApiParam(value = "avatar", required = true) @RequestPart("avatar") MultipartFile avatar) throws URISyntaxException, TikaException, IOException, SAXException {
        log.debug("REST request to update CrmAccount : {}, for Network: {}", crmAccountDTO, organizationShortcut);

        if (crmAccountDTO.getId() == null) {
            return createCustomerUsingPOST(organizationShortcut, channelShortcut, crmAccountDTO, null);
        }
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        CrmAccount crmAccount = crmAccountMapper.DTO2DB(crmAccountDTO, corChannel);
        CrmAccount entity = crmCustomerService.saveCustomerWithImage(crmAccount, avatar);
        CrmAccountDTO response = crmAccountMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<CrmAccountDTO> createCustomerUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                 @ApiParam(value = "crmAccountDTO", required = true) @Valid @RequestPart("crmAccountDTO") CrmAccountDTO crmAccountDTO,
                                                                 @ApiParam(value = "avatar", required = true) @RequestPart("avatar") MultipartFile avatar) throws URISyntaxException, TikaException, IOException, SAXException {
        log.debug("REST request to saveCorContact CrmAccount : {}, for Network: {}", crmAccountDTO, organizationShortcut);
        if (crmAccountDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmAccount", "idexists", "A new CrmAccount cannot already have an ID")).body(null);
        }
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        CrmAccount crmAccount = crmAccountMapper.DTO2DB(crmAccountDTO, corChannel);
        CrmAccount entity = crmCustomerService.saveCustomerWithImage(crmAccount, avatar);
        CrmAccountDTO response = crmAccountMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/organization/" + organizationShortcut + "/crm/customer/" + crmAccount.getShortName()))
                .body(response);
    }

    @Override
    public ResponseEntity<List<CrmAccountThinDTO>> getAllCustomersUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                           @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CrmAccount, for Network: {}", organizationShortcut);
        Slice<CrmAccount> entity = crmCustomerService.getAllCustomers(organizationShortcut, channelShortcut, pagable);
        List<CrmAccountThinDTO> response = crmAccountMapper.DBs2ThinDTOs(entity.getContent());

        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entity), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CrmAccountDTO> getCustomerUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                             @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get CrmAccount : {}, for Network: {}", shortName, organizationShortcut);
        CrmAccount entity = crmCustomerService.getCustomer(shortName, organizationShortcut, channelShortcut);
        CrmAccountDTO response = crmAccountMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deleteCustomeryUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                           @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to delete CrmAccount : {}, for Network: {}", shortName, organizationShortcut);
        crmCustomerService.deleteCustomer(shortName, organizationShortcut, channelShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CrmAccount", shortName.toString())).build();
    }
}
