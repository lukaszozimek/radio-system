package io.protone.application.web.api.crm.impl;


import io.protone.application.web.api.crm.CrmContactResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.crm.api.dto.CrmContactDTO;
import io.protone.crm.api.dto.thin.CrmContactThinDTO;
import io.protone.crm.domain.CrmContact;
import io.protone.crm.mapper.CrmContactMapper;
import io.protone.crm.service.CrmContactService;
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
public class CrmContactResourceImpl implements CrmContactResource {
    private final Logger log = LoggerFactory.getLogger(CrmContactResourceImpl.class);

    @Inject
    private CrmContactService crmContactService;

    @Inject
    private CorChannelService corChannelService;

    @Inject
    private CrmContactMapper crmContactMapper;


    @Override
    public ResponseEntity<CrmContactDTO> updateContactWithoutAvatarUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                            @ApiParam(value = "crmContactDTO", required = true) @Valid @RequestBody CrmContactDTO crmContactDTO) throws URISyntaxException, TikaException, IOException, SAXException {
        log.debug("REST request to update CrmContact : {}, for Network: {}", crmContactDTO, organizationShortcut);
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        if (crmContactDTO.getId() == null) {
            return createContactUsingPOST(organizationShortcut, channelShortcut, crmContactDTO, null);
        }
        CrmContact contact = crmContactMapper.DTO2DB(crmContactDTO, corChannel);
        CrmContact crmContact = crmContactService.saveContact(contact);
        CrmContactDTO response = crmContactMapper.DB2DTO(crmContact);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<CrmContactDTO> updateContactWithAvatarUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                          @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                          @ApiParam(value = "crmContactDTO", required = true) @Valid @RequestPart("crmContactDTO") CrmContactDTO crmContactDTO,
                                                                          @ApiParam(value = "avatar", required = true) @RequestPart("avatar") MultipartFile avatar) throws URISyntaxException, TikaException, IOException, SAXException {
        log.debug("REST request to saveCorContact CrmContact : {}, for Network: {}", crmContactDTO, organizationShortcut);
        if (crmContactDTO.getId() == null) {
            return createContactUsingPOST(organizationShortcut, channelShortcut, crmContactDTO, null);
        }
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        CrmContact contact = crmContactMapper.DTO2DB(crmContactDTO, corChannel);
        contact = crmContactService.saveContactWithImage(contact, avatar);
        CrmContactDTO response = crmContactMapper.DB2DTO(contact);
        return ResponseEntity.ok().body(response);

    }

    @Override
    public ResponseEntity<CrmContactDTO> createContactUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                @ApiParam(value = "crmContactDTO", required = true) @Valid @RequestPart("crmContactDTO") CrmContactDTO crmContactDTO,
                                                                @ApiParam(value = "avatar", required = true) @RequestPart("avatar") MultipartFile avatar) throws URISyntaxException, TikaException, IOException, SAXException {
        log.debug("REST request to saveCorContact CrmContact : {}, for Network: {}", crmContactDTO, organizationShortcut);
        if (crmContactDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmContact", "idexists", "A new CrmContact cannot already have an ID")).body(null);
        }
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        CrmContact contact = crmContactMapper.DTO2DB(crmContactDTO, corChannel);
        contact = crmContactService.saveContactWithImage(contact, avatar);
        CrmContactDTO response = crmContactMapper.DB2DTO(contact);
        return ResponseEntity.created(new URI("/api/v1/organization/" + organizationShortcut + "/crm/contact/" + crmContactDTO.getShortName()))
                .body(response);

    }

    @Override
    public ResponseEntity<List<CrmContactThinDTO>> getAllContactUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                         @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CrmContact, for Network: {}", organizationShortcut);
        Slice<CrmContact> crmContactSlice = crmContactService.getAllContact(organizationShortcut, channelShortcut, pagable);
        List<CrmContactThinDTO> response = crmContactMapper.DBs2ThinDTOs(crmContactSlice.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(crmContactSlice),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(PaginationUtil.generateSliceHttpHeaders(crmContactSlice), HttpStatus.NOT_FOUND));

    }

    @Override
    public ResponseEntity<CrmContactDTO> getContactUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                            @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get CrmContact : {}, for Network: {}", shortName, organizationShortcut);
        CrmContactDTO response = crmContactMapper.DB2DTO(crmContactService.getContact(shortName, organizationShortcut, channelShortcut));
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deleteContactUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                         @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to delete CrmContact : {}, for Network: {}", shortName, organizationShortcut);
        crmContactService.deleteContact(shortName, organizationShortcut, channelShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CrmContact", shortName.toString())).build();
    }
}
