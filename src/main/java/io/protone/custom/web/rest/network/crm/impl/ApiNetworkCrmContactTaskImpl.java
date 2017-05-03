package io.protone.custom.web.rest.network.crm.impl;

import io.protone.service.crm.CrmContactService;
import io.protone.domain.CrmTask;
import io.protone.service.cor.CorNetworkService;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.web.rest.api.library.impl.LibraryMarkerConfigurationResourceImpl;
import io.protone.domain.CorNetwork;
import io.protone.web.rest.mapper.CrmTaskMapper;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
public class ApiNetworkCrmContactTaskImpl implements io.protone.custom.web.rest.network.crm.ApiNetworkCrmContactTask {
    private final Logger log = LoggerFactory.getLogger(LibraryMarkerConfigurationResourceImpl.class);

    @Inject
    private CrmContactService crmContactService;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CrmTaskMapper crmTaskMapper;

    @Override
    public ResponseEntity<List<CrmTaskPT>> getAllContactActivitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                           @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CrmContact CrmTask,for CrmContact: {} and Network: {}", shortName, networkShortcut);
        List<CrmTask> reposesEntity = crmContactService.getTasksAssociatedWithContact(shortName, networkShortcut, pagable);
        List<CrmTaskPT> response = crmTaskMapper.DBs2DTOs(reposesEntity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CrmTaskPT> updateContactActivityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                   @ApiParam(value = "crmTaskPT", required = true) @RequestBody CrmTaskPT crmTaskPT) throws URISyntaxException {
        log.debug("REST request to update CrmContact CrmTask : {}, for CrmContact: {} and Network: {}", crmTaskPT, shortName, networkShortcut);
        if (crmTaskPT.getId() == null) {
            return createContactActivityUsingPOST(networkShortcut, shortName, crmTaskPT);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmTask crmTask = crmTaskMapper.DTO2DB(crmTaskPT, corNetwork);
        CrmTask reposesEntity = crmContactService.saveOrUpdateTaskAssociatiedWithAccount(crmTask, shortName, networkShortcut);
        CrmTaskPT response = crmTaskMapper.DB2DTO(reposesEntity);
        return ResponseEntity.ok().body(response);

    }

    @Override
    public ResponseEntity<CrmTaskPT> createContactActivityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                    @ApiParam(value = "crmTaskPT", required = true) @RequestBody CrmTaskPT crmTaskPT) throws URISyntaxException {
        log.debug("REST request to save CrmContact CrmTask : {}, for CrmContact: {} and Network: {}", crmTaskPT, shortName, networkShortcut);
        if (crmTaskPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmTask", "idexists", "A new CrmTask cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmTask crmTask = crmTaskMapper.DTO2DB(crmTaskPT, corNetwork);
        CrmTask reposesEntity = crmContactService.saveOrUpdateTaskAssociatiedWithAccount(crmTask, shortName, corNetwork.getShortcut());
        CrmTaskPT response = crmTaskMapper.DB2DTO(reposesEntity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/crm/contact/" + shortName + "/task/" + crmTask.getId()))
            .body(response);
    }

    @Override
    public ResponseEntity<Void> deleteContactActivityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                 @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CrmContact CrmTask : {}, for CrmContact: {} and Network: {}", id, shortName, networkShortcut);
        crmContactService.deleteContactTask(shortName, id, networkShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CrmTask", id.toString())).build();

    }

    @Override
    public ResponseEntity<CrmTaskPT> getContactActivityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CrmContact CrmTask : {}, for CrmContact: {} and Network: {}", id, shortName, networkShortcut);
        CrmTask reposesEntity = crmContactService.getTaskAssociatedWithContact(id, networkShortcut);
        CrmTaskPT response = crmTaskMapper.DB2DTO(reposesEntity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
