package io.protone.custom.web.rest.network.crm.impl;

import io.protone.service.crm.CrmContactService;
import io.protone.domain.CrmTask;
import io.protone.service.cor.CorNetworkService;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.custom.web.rest.network.configuration.library.impl.ApiConfigurationLibraryMarkerImpl;
import io.protone.domain.CorNetwork;
import io.protone.service.mapper.CrmTaskMapper;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ApiNetworkCrmContactTaskImpl implements io.protone.custom.web.rest.network.crm.ApiNetworkCrmContactTask {
    private final Logger log = LoggerFactory.getLogger(ApiConfigurationLibraryMarkerImpl.class);

    @Inject
    private CrmContactService crmContactService;

    @Inject
    private CorNetworkService networkService;

    @Inject
    private CrmTaskMapper crmTaskMapper;

    @Override
    public ResponseEntity<List<CrmTaskPT>> getAllContactActivitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                           @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CrmContact CrmTask,for CrmContact: {} and Network: {}", shortName, networkShortcut);
        List<CrmTask> reposesEntity = crmContactService.getTasksAssociatedWithContact(shortName, networkShortcut, pagable);
        List<CrmTaskPT> response = crmTaskMapper.DBs2DTOs(reposesEntity);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<CrmTaskPT> updateContactActivityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                   @ApiParam(value = "crmTaskPT", required = true) @RequestBody CrmTaskPT crmTaskPT) {
        log.debug("REST request to update CrmContact CrmTask : {}, for CrmContact: {} and Network: {}", crmTaskPT, shortName, networkShortcut);
        if (crmTaskPT.getId() == null) {
            return createContactActivityUsingPOST(networkShortcut, shortName, crmTaskPT);
        }
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        CrmTask crmTask = crmTaskMapper.DTO2DB(crmTaskPT);
        CrmTask reposesEntity = crmContactService.saveOrUpdateTaskAssociatiedWithAccount(crmTask, shortName, networkShortcut);
        CrmTaskPT response = crmTaskMapper.DB2DTO(reposesEntity);
        return ResponseEntity.ok().body(response);

    }

    @Override
    public ResponseEntity<CrmTaskPT> createContactActivityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                    @ApiParam(value = "crmTaskPT", required = true) @RequestBody CrmTaskPT crmTaskPT) {
        log.debug("REST request to save CrmContact CrmTask : {}, for CrmContact: {} and Network: {}", crmTaskPT, shortName, networkShortcut);
        if (crmTaskPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmTask", "idexists", "A new CrmTask cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        CrmTask crmTask = crmTaskMapper.DTO2DB(crmTaskPT);
        CrmTask reposesEntity = crmContactService.saveOrUpdateTaskAssociatiedWithAccount(crmTask, shortName, corNetwork.getShortcut());
        CrmTaskPT response = crmTaskMapper.DB2DTO(reposesEntity);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<Void> deleteContactActivityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                 @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CrmContact CrmTask : {}, for CrmContact: {} and Network: {}", id, shortName, networkShortcut);
        crmContactService.deleteContactTask(shortName, id, networkShortcut);
        return ResponseEntity.ok().build();

    }

    @Override
    public ResponseEntity<CrmTaskPT> getContactActivityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CrmContact CrmTask : {}, for CrmContact: {} and Network: {}", id, shortName, networkShortcut);
        CrmTask reposesEntity = crmContactService.getTaskAssociatedWithContact(id, networkShortcut);
        CrmTaskPT response = crmTaskMapper.DB2DTO(reposesEntity);
        return ResponseEntity.ok().body(response);
    }
}
