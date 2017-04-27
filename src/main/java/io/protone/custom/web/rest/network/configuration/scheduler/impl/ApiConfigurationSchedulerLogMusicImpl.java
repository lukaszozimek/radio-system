package io.protone.custom.web.rest.network.configuration.scheduler.impl;


import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.dto.ConfMusicLogPT;
import io.protone.service.mapper.ConfMusicLogMapper;
import io.protone.custom.web.rest.network.configuration.scheduler.ApiConfigurationSchedulerLogMusic;
import io.protone.domain.CfgExternalSystemLog;
import io.protone.domain.CorNetwork;
import io.protone.domain.enumeration.CfgLogTypeEnum;
import io.protone.repository.CfgExternalSystemLogRepository;
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
import java.util.List;
import java.util.Optional;

@RestController
public class ApiConfigurationSchedulerLogMusicImpl implements ApiConfigurationSchedulerLogMusic {
    private final Logger log = LoggerFactory.getLogger(ApiConfigurationSchedulerLogMusicImpl.class);

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private ConfMusicLogMapper confCommercialLogMapper;

    @Inject
    private CfgExternalSystemLogRepository cfgExternalSystemLogRepository;

    @Override
    public ResponseEntity<ConfMusicLogPT> updateMusicLogConfigurationUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "confMusicLogPT", required = true) @RequestBody ConfMusicLogPT confMusicLogPT) {
        log.debug("REST request to update CfgExternalSystemLog : {}", confMusicLogPT);
        if (confMusicLogPT.getId() == null) {
            return createMusicLogConfigurationUsingPOST(networkShortcut, confMusicLogPT);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CfgExternalSystemLog cfgExternalSystemLog = confCommercialLogMapper.DTO2DB(confMusicLogPT);
        cfgExternalSystemLog.setNetwork(corNetwork);

        cfgExternalSystemLog.setLogColumn(CfgLogTypeEnum.LT_MUSIC);
        cfgExternalSystemLog = cfgExternalSystemLogRepository.save(cfgExternalSystemLog);
        ConfMusicLogPT result = confCommercialLogMapper.DB2DTO(cfgExternalSystemLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("CfgExternalSystemLog", result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<ConfMusicLogPT> createMusicLogConfigurationUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "confMusicLogPT", required = true) @RequestBody ConfMusicLogPT confMusicLogPT) {
        log.debug("REST request to save CfgExternalSystemLog : {}", confMusicLogPT);
        if (confMusicLogPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CfgExternalSystemLog", "idexists", "A new CfgExternalSystemLog cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CfgExternalSystemLog cfgExternalSystemLog = confCommercialLogMapper.DTO2DB(confMusicLogPT);

        cfgExternalSystemLog.setNetwork(corNetwork);
        cfgExternalSystemLog.setLogColumn(CfgLogTypeEnum.LT_MUSIC);
        cfgExternalSystemLog = cfgExternalSystemLogRepository.save(cfgExternalSystemLog);
        ConfMusicLogPT result = confCommercialLogMapper.DB2DTO(cfgExternalSystemLog);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Void> deleteMusicLogConfigurationUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CfgExternalSystemLog : {}", id);
        cfgExternalSystemLogRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CfgExternalSystemLog", id.toString())).build();

    }

    @Override
    public ResponseEntity<List<ConfMusicLogPT>> getAllMusicLogConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                    @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get CfgExternalSystemLog : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        List<CfgExternalSystemLog> cfgExternalSystemLog = cfgExternalSystemLogRepository.findByNetworkAndLogColumn(corNetwork, CfgLogTypeEnum.LT_MUSIC);
        List<ConfMusicLogPT> confCurrencyPTS = confCommercialLogMapper.DBs2DTOs(cfgExternalSystemLog);
        return Optional.ofNullable(confCurrencyPTS)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ConfMusicLogPT> getMusicLogConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CfgExternalSystemLog : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CfgExternalSystemLog traCampaingStatus = cfgExternalSystemLogRepository.findOneByIdAndNetworkAndLogColumn(id, corNetwork, CfgLogTypeEnum.LT_MUSIC);
        ConfMusicLogPT confCampaingStatusPT = confCommercialLogMapper.DB2DTO(traCampaingStatus);
        return Optional.ofNullable(confCampaingStatusPT)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
