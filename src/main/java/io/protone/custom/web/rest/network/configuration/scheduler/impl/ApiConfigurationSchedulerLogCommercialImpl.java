package io.protone.custom.web.rest.network.configuration.scheduler.impl;

import java.util.List;
import java.util.Optional;

import io.protone.service.cor.CorNetworkService;
import io.protone.custom.service.dto.ConfCommercialLogPT;
import io.protone.web.rest.mapper.ConfCommercialLogMapper;
import io.protone.custom.web.rest.network.configuration.scheduler.ApiConfigurationSchedulerLogCommercial;
import io.protone.domain.CfgExternalSystemLog;
import io.protone.domain.CorNetwork;
import io.protone.domain.enumeration.CfgLogTypeEnum;
import io.protone.repository.cfg.CfgExternalSystemLogRepository;
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

@RestController
public class ApiConfigurationSchedulerLogCommercialImpl implements ApiConfigurationSchedulerLogCommercial {
    private final Logger log = LoggerFactory.getLogger(ApiConfigurationSchedulerLogCommercialImpl.class);

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private ConfCommercialLogMapper confCommercialLogMapper;

    @Inject
    private CfgExternalSystemLogRepository cfgExternalSystemLogRepository;

    @Override
    public ResponseEntity<ConfCommercialLogPT> updateCommercialLogConfigurationUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "confMusicLogPT", required = true) @RequestBody ConfCommercialLogPT confMusicLogPT) {
        log.debug("REST request to update CfgExternalSystemLog : {}", confMusicLogPT);
        if (confMusicLogPT.getId() == null) {
            return createCommercialLogConfigurationUsingPOST(networkShortcut, confMusicLogPT);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CfgExternalSystemLog cfgExternalSystemLog = confCommercialLogMapper.DTO2DB(confMusicLogPT,corNetwork);
        cfgExternalSystemLog.setLogColumn(CfgLogTypeEnum.LT_MUSIC);
        cfgExternalSystemLog = cfgExternalSystemLogRepository.save(cfgExternalSystemLog);
        ConfCommercialLogPT result = confCommercialLogMapper.DB2DTO(cfgExternalSystemLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("CfgExternalSystemLog", result.getId().toString()))
            .body(result);

    }

    @Override
    public ResponseEntity<ConfCommercialLogPT> createCommercialLogConfigurationUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "confMusicLogPT", required = true) @RequestBody ConfCommercialLogPT confMusicLogPT) {
        log.debug("REST request to save CfgExternalSystemLog : {}", confMusicLogPT);
        if (confMusicLogPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CfgExternalSystemLog", "idexists", "A new CfgExternalSystemLog cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CfgExternalSystemLog cfgExternalSystemLog = confCommercialLogMapper.DTO2DB(confMusicLogPT,corNetwork);

        cfgExternalSystemLog.setNetwork(corNetwork);
        cfgExternalSystemLog.setLogColumn(CfgLogTypeEnum.LT_COMMERCIAL);
        cfgExternalSystemLog = cfgExternalSystemLogRepository.save(cfgExternalSystemLog);
        ConfCommercialLogPT result = confCommercialLogMapper.DB2DTO(cfgExternalSystemLog);
        return ResponseEntity.ok().body(result);

    }

    @Override
    public ResponseEntity<Void> deleteCommercialLogConfigurationUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CfgExternalSystemLog : {}", id);
        cfgExternalSystemLogRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CfgExternalSystemLog", id.toString())).build();

    }

    @Override
    public ResponseEntity<List<ConfCommercialLogPT>> getAllCommercialLogConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                              @ApiParam(value = "pagable", required = true)  Pageable pagable) {
        log.debug("REST request to get CfgExternalSystemLog : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        List<CfgExternalSystemLog> cfgExternalSystemLog = cfgExternalSystemLogRepository.findByNetworkAndLogColumn(corNetwork, CfgLogTypeEnum.LT_COMMERCIAL);
        List<ConfCommercialLogPT> confCurrencyPTS = confCommercialLogMapper.DBs2DTOs(cfgExternalSystemLog);
        return Optional.ofNullable(confCurrencyPTS)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ConfCommercialLogPT> getCommercialLogConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CfgExternalSystemLog : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CfgExternalSystemLog traCampaingStatus = cfgExternalSystemLogRepository.findOneByIdAndNetworkAndLogColumn(id, corNetwork, CfgLogTypeEnum.LT_COMMERCIAL);
        ConfCommercialLogPT confCampaingStatusPT = confCommercialLogMapper.DB2DTO(traCampaingStatus);
        return Optional.ofNullable(confCampaingStatusPT)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
