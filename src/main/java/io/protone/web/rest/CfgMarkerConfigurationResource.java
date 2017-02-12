package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CfgMarkerConfiguration;

import io.protone.repository.CfgMarkerConfigurationRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CfgMarkerConfigurationDTO;
import io.protone.service.mapper.CfgMarkerConfigurationMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing CfgMarkerConfiguration.
 */
@RestController
@RequestMapping("/api")
public class CfgMarkerConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(CfgMarkerConfigurationResource.class);

    private static final String ENTITY_NAME = "cfgMarkerConfiguration";
        
    private final CfgMarkerConfigurationRepository cfgMarkerConfigurationRepository;

    private final CfgMarkerConfigurationMapper cfgMarkerConfigurationMapper;

    public CfgMarkerConfigurationResource(CfgMarkerConfigurationRepository cfgMarkerConfigurationRepository, CfgMarkerConfigurationMapper cfgMarkerConfigurationMapper) {
        this.cfgMarkerConfigurationRepository = cfgMarkerConfigurationRepository;
        this.cfgMarkerConfigurationMapper = cfgMarkerConfigurationMapper;
    }

    /**
     * POST  /cfg-marker-configurations : Create a new cfgMarkerConfiguration.
     *
     * @param cfgMarkerConfigurationDTO the cfgMarkerConfigurationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cfgMarkerConfigurationDTO, or with status 400 (Bad Request) if the cfgMarkerConfiguration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cfg-marker-configurations")
    @Timed
    public ResponseEntity<CfgMarkerConfigurationDTO> createCfgMarkerConfiguration(@Valid @RequestBody CfgMarkerConfigurationDTO cfgMarkerConfigurationDTO) throws URISyntaxException {
        log.debug("REST request to save CfgMarkerConfiguration : {}", cfgMarkerConfigurationDTO);
        if (cfgMarkerConfigurationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cfgMarkerConfiguration cannot already have an ID")).body(null);
        }
        CfgMarkerConfiguration cfgMarkerConfiguration = cfgMarkerConfigurationMapper.cfgMarkerConfigurationDTOToCfgMarkerConfiguration(cfgMarkerConfigurationDTO);
        cfgMarkerConfiguration = cfgMarkerConfigurationRepository.save(cfgMarkerConfiguration);
        CfgMarkerConfigurationDTO result = cfgMarkerConfigurationMapper.cfgMarkerConfigurationToCfgMarkerConfigurationDTO(cfgMarkerConfiguration);
        return ResponseEntity.created(new URI("/api/cfg-marker-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cfg-marker-configurations : Updates an existing cfgMarkerConfiguration.
     *
     * @param cfgMarkerConfigurationDTO the cfgMarkerConfigurationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cfgMarkerConfigurationDTO,
     * or with status 400 (Bad Request) if the cfgMarkerConfigurationDTO is not valid,
     * or with status 500 (Internal Server Error) if the cfgMarkerConfigurationDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cfg-marker-configurations")
    @Timed
    public ResponseEntity<CfgMarkerConfigurationDTO> updateCfgMarkerConfiguration(@Valid @RequestBody CfgMarkerConfigurationDTO cfgMarkerConfigurationDTO) throws URISyntaxException {
        log.debug("REST request to update CfgMarkerConfiguration : {}", cfgMarkerConfigurationDTO);
        if (cfgMarkerConfigurationDTO.getId() == null) {
            return createCfgMarkerConfiguration(cfgMarkerConfigurationDTO);
        }
        CfgMarkerConfiguration cfgMarkerConfiguration = cfgMarkerConfigurationMapper.cfgMarkerConfigurationDTOToCfgMarkerConfiguration(cfgMarkerConfigurationDTO);
        cfgMarkerConfiguration = cfgMarkerConfigurationRepository.save(cfgMarkerConfiguration);
        CfgMarkerConfigurationDTO result = cfgMarkerConfigurationMapper.cfgMarkerConfigurationToCfgMarkerConfigurationDTO(cfgMarkerConfiguration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cfgMarkerConfigurationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cfg-marker-configurations : get all the cfgMarkerConfigurations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cfgMarkerConfigurations in body
     */
    @GetMapping("/cfg-marker-configurations")
    @Timed
    public List<CfgMarkerConfigurationDTO> getAllCfgMarkerConfigurations() {
        log.debug("REST request to get all CfgMarkerConfigurations");
        List<CfgMarkerConfiguration> cfgMarkerConfigurations = cfgMarkerConfigurationRepository.findAll();
        return cfgMarkerConfigurationMapper.cfgMarkerConfigurationsToCfgMarkerConfigurationDTOs(cfgMarkerConfigurations);
    }

    /**
     * GET  /cfg-marker-configurations/:id : get the "id" cfgMarkerConfiguration.
     *
     * @param id the id of the cfgMarkerConfigurationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cfgMarkerConfigurationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cfg-marker-configurations/{id}")
    @Timed
    public ResponseEntity<CfgMarkerConfigurationDTO> getCfgMarkerConfiguration(@PathVariable Long id) {
        log.debug("REST request to get CfgMarkerConfiguration : {}", id);
        CfgMarkerConfiguration cfgMarkerConfiguration = cfgMarkerConfigurationRepository.findOne(id);
        CfgMarkerConfigurationDTO cfgMarkerConfigurationDTO = cfgMarkerConfigurationMapper.cfgMarkerConfigurationToCfgMarkerConfigurationDTO(cfgMarkerConfiguration);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cfgMarkerConfigurationDTO));
    }

    /**
     * DELETE  /cfg-marker-configurations/:id : delete the "id" cfgMarkerConfiguration.
     *
     * @param id the id of the cfgMarkerConfigurationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cfg-marker-configurations/{id}")
    @Timed
    public ResponseEntity<Void> deleteCfgMarkerConfiguration(@PathVariable Long id) {
        log.debug("REST request to delete CfgMarkerConfiguration : {}", id);
        cfgMarkerConfigurationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
