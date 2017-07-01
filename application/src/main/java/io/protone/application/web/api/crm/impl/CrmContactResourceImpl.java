package io.protone.application.web.api.crm.impl;


import io.protone.application.web.api.crm.CrmContactResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorNetworkService;
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
    private CorNetworkService corNetworkService;

    @Inject
    private CrmContactMapper crmContactMapper;


    @Override
    public ResponseEntity<CrmContactDTO> updateContactWithoutAvatarUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmContactDTO", required = true) @Valid @RequestBody CrmContactDTO crmContactDTO) throws URISyntaxException, TikaException, IOException, SAXException {
        log.debug("REST request to update CrmContact : {}, for Network: {}", crmContactDTO, networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        if (crmContactDTO.getId() == null) {
            return createContactUsingPOST(networkShortcut, crmContactDTO, null);
        }
        CrmContact contact = crmContactMapper.DTO2DB(crmContactDTO, corNetwork);
        CrmContact crmContact = crmContactService.saveContact(contact);
        CrmContactDTO response = crmContactMapper.DB2DTO(crmContact);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<CrmContactDTO> updateContactWithAvatarUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                          @ApiParam(value = "crmContactDTO", required = true) @Valid @RequestPart("crmContactDTO") CrmContactDTO crmContactDTO,
                                                                          @ApiParam(value = "avatar", required = true) @RequestPart("avatar") MultipartFile avatar) throws URISyntaxException, TikaException, IOException, SAXException {
        log.debug("REST request to saveCorContact CrmContact : {}, for Network: {}", crmContactDTO, networkShortcut);
        if (crmContactDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmContact", "idexists", "A new CrmContact cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmContact contact = crmContactMapper.DTO2DB(crmContactDTO, corNetwork);
        contact = crmContactService.saveContactWithImage(contact, avatar);
        CrmContactDTO response = crmContactMapper.DB2DTO(contact);
        return ResponseEntity.ok().body(response);

    }

    @Override
    public ResponseEntity<CrmContactDTO> createContactUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "crmContactDTO", required = true) @Valid @RequestPart("crmContactDTO") CrmContactDTO crmContactDTO,
                                                                @ApiParam(value = "avatar", required = true) @RequestPart("avatar") MultipartFile avatar) throws URISyntaxException, TikaException, IOException, SAXException {
        log.debug("REST request to saveCorContact CrmContact : {}, for Network: {}", crmContactDTO, networkShortcut);
        if (crmContactDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmContact", "idexists", "A new CrmContact cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmContact contact = crmContactMapper.DTO2DB(crmContactDTO, corNetwork);
        contact = crmContactService.saveContactWithImage(contact, avatar);
        CrmContactDTO response = crmContactMapper.DB2DTO(contact);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/crm/contact/" + crmContactDTO.getShortName()))
                .body(response);

    }

    @Override
    public ResponseEntity<List<CrmContactThinDTO>> getAllContactUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                         @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CrmContact, for Network: {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        List<CrmContactThinDTO> response = crmContactMapper.DBs2ThinDTOs(crmContactService.getAllContact(corNetwork.getShortcut(), pagable));
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @Override
    public ResponseEntity<CrmContactDTO> getContactUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get CrmContact : {}, for Network: {}", shortName, networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmContactDTO response = crmContactMapper.DB2DTO(crmContactService.getContact(shortName, corNetwork.getShortcut()));
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deleteContactUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to delete CrmContact : {}, for Network: {}", shortName, networkShortcut);
        crmContactService.deleteContact(shortName, networkShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CrmContact", shortName.toString())).build();
    }
}
