package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CFGMarkerConfiguration;

import io.protone.repository.CFGMarkerConfigurationRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CFGMarkerConfigurationDTO;
import io.protone.service.mapper.CFGMarkerConfigurationMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing CFGMarkerConfiguration.
 */
@RestController
@RequestMapping("/api")
public class CFGMarkerConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(CFGMarkerConfigurationResource.class);
        
    @Inject
    private CFGMarkerConfigurationRepository cFGMarkerConfigurationRepository;

    @Inject
    private CFGMarkerConfigurationMapper cFGMarkerConfigurationMapper;

    /**
     * POST  /c-fg-marker-configurations : Create a new cFGMarkerConfiguration.
     *
     * @param cFGMarkerConfigurationDTO the cFGMarkerConfigurationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cFGMarkerConfigurationDTO, or with status 400 (Bad Request) if the cFGMarkerConfiguration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-fg-marker-configurations")
    @Timed
    public ResponseEntity<CFGMarkerConfigurationDTO> createCFGMarkerConfiguration(@Valid @RequestBody CFGMarkerConfigurationDTO cFGMarkerConfigurationDTO) throws URISyntaxException {
        log.debug("REST request to save CFGMarkerConfiguration : {}", cFGMarkerConfigurationDTO);
        if (cFGMarkerConfigurationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cFGMarkerConfiguration", "idexists", "A new cFGMarkerConfiguration cannot already have an ID")).body(null);
        }
        CFGMarkerConfiguration cFGMarkerConfiguration = cFGMarkerConfigurationMapper.cFGMarkerConfigurationDTOToCFGMarkerConfiguration(cFGMarkerConfigurationDTO);
        cFGMarkerConfiguration = cFGMarkerConfigurationRepository.save(cFGMarkerConfiguration);
        CFGMarkerConfigurationDTO result = cFGMarkerConfigurationMapper.cFGMarkerConfigurationToCFGMarkerConfigurationDTO(cFGMarkerConfiguration);
        return ResponseEntity.created(new URI("/api/c-fg-marker-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cFGMarkerConfiguration", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-fg-marker-configurations : Updates an existing cFGMarkerConfiguration.
     *
     * @param cFGMarkerConfigurationDTO the cFGMarkerConfigurationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cFGMarkerConfigurationDTO,
     * or with status 400 (Bad Request) if the cFGMarkerConfigurationDTO is not valid,
     * or with status 500 (Internal Server Error) if the cFGMarkerConfigurationDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-fg-marker-configurations")
    @Timed
    public ResponseEntity<CFGMarkerConfigurationDTO> updateCFGMarkerConfiguration(@Valid @RequestBody CFGMarkerConfigurationDTO cFGMarkerConfigurationDTO) throws URISyntaxException {
        log.debug("REST request to update CFGMarkerConfiguration : {}", cFGMarkerConfigurationDTO);
        if (cFGMarkerConfigurationDTO.getId() == null) {
            return createCFGMarkerConfiguration(cFGMarkerConfigurationDTO);
        }
        CFGMarkerConfiguration cFGMarkerConfiguration = cFGMarkerConfigurationMapper.cFGMarkerConfigurationDTOToCFGMarkerConfiguration(cFGMarkerConfigurationDTO);
        cFGMarkerConfiguration = cFGMarkerConfigurationRepository.save(cFGMarkerConfiguration);
        CFGMarkerConfigurationDTO result = cFGMarkerConfigurationMapper.cFGMarkerConfigurationToCFGMarkerConfigurationDTO(cFGMarkerConfiguration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cFGMarkerConfiguration", cFGMarkerConfigurationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-fg-marker-configurations : get all the cFGMarkerConfigurations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cFGMarkerConfigurations in body
     */
    @GetMapping("/c-fg-marker-configurations")
    @Timed
    public List<CFGMarkerConfigurationDTO> getAllCFGMarkerConfigurations() {
        log.debug("REST request to get all CFGMarkerConfigurations");
        List<CFGMarkerConfiguration> cFGMarkerConfigurations = cFGMarkerConfigurationRepository.findAll();
        return cFGMarkerConfigurationMapper.cFGMarkerConfigurationsToCFGMarkerConfigurationDTOs(cFGMarkerConfigurations);
    }

    /**
     * GET  /c-fg-marker-configurations/:id : get the "id" cFGMarkerConfiguration.
     *
     * @param id the id of the cFGMarkerConfigurationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cFGMarkerConfigurationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/c-fg-marker-configurations/{id}")
    @Timed
    public ResponseEntity<CFGMarkerConfigurationDTO> getCFGMarkerConfiguration(@PathVariable Long id) {
        log.debug("REST request to get CFGMarkerConfiguration : {}", id);
        CFGMarkerConfiguration cFGMarkerConfiguration = cFGMarkerConfigurationRepository.findOne(id);
        CFGMarkerConfigurationDTO cFGMarkerConfigurationDTO = cFGMarkerConfigurationMapper.cFGMarkerConfigurationToCFGMarkerConfigurationDTO(cFGMarkerConfiguration);
        return Optional.ofNullable(cFGMarkerConfigurationDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-fg-marker-configurations/:id : delete the "id" cFGMarkerConfiguration.
     *
     * @param id the id of the cFGMarkerConfigurationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-fg-marker-configurations/{id}")
    @Timed
    public ResponseEntity<Void> deleteCFGMarkerConfiguration(@PathVariable Long id) {
        log.debug("REST request to delete CFGMarkerConfiguration : {}", id);
        cFGMarkerConfigurationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cFGMarkerConfiguration", id.toString())).build();
    }

}
