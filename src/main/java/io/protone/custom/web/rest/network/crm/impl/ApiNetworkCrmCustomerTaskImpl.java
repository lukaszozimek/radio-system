package io.protone.custom.web.rest.network.crm.impl;

import io.protone.custom.service.CrmCustomerService;
import io.protone.domain.CrmTask;
import io.protone.service.cor.CorNetworkService;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.custom.web.rest.network.crm.ApiNetworkCrmCustomerTask;
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
public class ApiNetworkCrmCustomerTaskImpl implements ApiNetworkCrmCustomerTask {
    private final Logger log = LoggerFactory.getLogger(ApiNetworkCrmCustomerTask.class);

    @Inject
    private CrmCustomerService crmCustomerService;

    @Inject
    private CorNetworkService networkService;
    @Inject
    private CrmTaskMapper crmTaskMapper;

    @Override
    public ResponseEntity<List<CrmTaskPT>> getAllCustomerActivitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                            @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                            @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CrmAccount CrmTask, for CrmAccount: {} and Network: {}", shortName, networkShortcut);
        List<CrmTask> entities = crmCustomerService.getTasksAssociatedWithContact(shortName, networkShortcut, pagable);
        List<CrmTaskPT> response = crmTaskMapper.DBs2DTOs(entities);
        return ResponseEntity.ok().body(response);

    }

    @Override
    public ResponseEntity<CrmTaskPT> updateCustomerActivityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmTaskPT", required = true) @RequestBody CrmTaskPT crmTaskPT) {
        log.debug("REST request to update  CrmAccount CrmTask : {}, for CrmAccount: {} and Network: {}", crmTaskPT, shortName, networkShortcut);
        if (crmTaskPT.getId() == null) {
            return createCustomerActivityUsingPOST(networkShortcut, shortName, crmTaskPT);
        }
        CrmTask requestEnitity = crmTaskMapper.DTO2DB(crmTaskPT);
        CrmTask entity = crmCustomerService.saveOrUpdateTaskAssociatiedWithAccount(requestEnitity, shortName, networkShortcut);
        CrmTaskPT response = crmTaskMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<CrmTaskPT> createCustomerActivityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmTaskPT", required = true) @RequestBody CrmTaskPT crmTaskPT) {
        log.debug("REST request to save CrmAccount CrmTask : {}, for CrmAccount: {} and Network: {}", crmTaskPT, shortName, networkShortcut);
        if (crmTaskPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmTask", "idexists", "A new CrmTask cannot already have an ID")).body(null);
        }
        CrmTask requestEnitity = crmTaskMapper.DTO2DB(crmTaskPT);
        CrmTask entity = crmCustomerService.saveOrUpdateTaskAssociatiedWithAccount(requestEnitity, shortName, networkShortcut);
        CrmTaskPT response = crmTaskMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);

    }

    @Override
    public ResponseEntity<Void> deleteCustomerActivityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CrmAccount CrmTask : {}, for CrmAccount: {} and Network: {}", id, shortName, networkShortcut);
        crmCustomerService.deleteCustomerTask(shortName, id, networkShortcut);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CrmTaskPT> getCustomerActivityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CrmAccount CrmTask : {}, for CrmAccount: {} and Network: {}", id, shortName, networkShortcut);

        CrmTask entity = crmCustomerService.getTaskAssociatedWithContact(id, networkShortcut);
        CrmTaskPT response = crmTaskMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);

    }
}
