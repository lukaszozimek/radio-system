package io.protone.custom.web.rest.network.configuration.traffic.impl;

import io.protone.custom.service.NetworkService;
import io.protone.custom.service.dto.ConfIndustryPT;
import io.protone.custom.service.mapper.CustomTRAIndustryMapper;
import io.protone.custom.web.rest.network.configuration.traffic.ApiConfigurationTrafficDictionaryIndustry;
import io.protone.domain.TRAIndustry;
import io.protone.repository.TRAIndustryRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@RestController
public class ApiConfigurationTrafficDictionaryIndustryImpl implements ApiConfigurationTrafficDictionaryIndustry {

    private final Logger log = LoggerFactory.getLogger(ApiConfigurationTrafficDictionaryIndustryImpl.class);

    @Inject
    private TRAIndustryRepository tRAIndustryRepository;
    @Inject
    private NetworkService networkService;
    @Inject
    private CustomTRAIndustryMapper tRAIndustryMapper;


    @Override
    public ResponseEntity<ConfIndustryPT> getIndustryUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "industryName", required = true) @PathVariable("id") String id) {
        log.debug("REST request to get TRAIndustry : {}", id);
        TRAIndustry tRAIndustry = tRAIndustryRepository.findOne(Long.parseLong(id));
        ConfIndustryPT tRAIndustryDTO = tRAIndustryMapper.tRAIndustryToTRAIndustryDTO(tRAIndustry);
        return Optional.ofNullable(tRAIndustryDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @Override
    public ResponseEntity<ConfIndustryPT> updateIndustryUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "industryDTO", required = true) @RequestBody ConfIndustryPT industryDTO) {
        log.debug("REST request to update TRAIndustry : {}", industryDTO);
        if (industryDTO.getId() == null) {
            return createIndustryUsingPOST(networkShortcut,industryDTO);
        }
        TRAIndustry tRAIndustry = tRAIndustryMapper.tRAIndustryDTOToTRAIndustry(industryDTO);
        tRAIndustry = tRAIndustryRepository.save(tRAIndustry);
        ConfIndustryPT result = tRAIndustryMapper.tRAIndustryToTRAIndustryDTO(tRAIndustry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tRAIndustry", industryDTO.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<ConfIndustryPT> createIndustryUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "industryDTO", required = true) @RequestBody ConfIndustryPT industryDTO) {
        log.debug("REST request to save TRAIndustry : {}", industryDTO);
        if (industryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tRAIndustry", "idexists", "A new tRAIndustry cannot already have an ID")).body(null);
        }
        TRAIndustry tRAIndustry = tRAIndustryMapper.tRAIndustryDTOToTRAIndustry(industryDTO);
        tRAIndustry = tRAIndustryRepository.save(tRAIndustry);
        ConfIndustryPT result = tRAIndustryMapper.tRAIndustryToTRAIndustryDTO(tRAIndustry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityCreationAlert("tRAIndustry", result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<Void> deleteIndustryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "industryName", required = true) @PathVariable("id") String id) {
        log.debug("REST request to delete TRAIndustry : {}", id);
        tRAIndustryRepository.delete(Long.parseLong(id));
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tRAIndustry", id.toString())).build();

    }

    @Override
    public ResponseEntity<List<ConfIndustryPT>> getAllIndustriesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get all TRAIndustries");
        List<TRAIndustry> tRAIndustries = tRAIndustryRepository.findAll();
        return ResponseEntity.ok().body(tRAIndustryMapper.tRAIndustriesToTRAIndustryDTOs(tRAIndustries));

    }
}
