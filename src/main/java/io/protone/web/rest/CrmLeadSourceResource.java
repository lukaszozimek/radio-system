package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CrmLeadSource;

import io.protone.repository.CrmLeadSourceRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CrmLeadSourceDTO;
import io.protone.service.mapper.CrmLeadSourceMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CrmLeadSource.
 */
@RestController
@RequestMapping("/api")
public class CrmLeadSourceResource {

    private final Logger log = LoggerFactory.getLogger(CrmLeadSourceResource.class);

    private static final String ENTITY_NAME = "crmLeadSource";

    private final CrmLeadSourceRepository crmLeadSourceRepository;

    private final CrmLeadSourceMapper crmLeadSourceMapper;

    public CrmLeadSourceResource(CrmLeadSourceRepository crmLeadSourceRepository, CrmLeadSourceMapper crmLeadSourceMapper) {
        this.crmLeadSourceRepository = crmLeadSourceRepository;
        this.crmLeadSourceMapper = crmLeadSourceMapper;
    }

    /**
     * POST  /crm-lead-sources : Create a new crmLeadSource.
     *
     * @param crmLeadSourceDTO the crmLeadSourceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new crmLeadSourceDTO, or with status 400 (Bad Request) if the crmLeadSource has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/crm-lead-sources")
    @Timed
    public ResponseEntity<CrmLeadSourceDTO> createCrmLeadSource(@RequestBody CrmLeadSourceDTO crmLeadSourceDTO) throws URISyntaxException {
        log.debug("REST request to save CrmLeadSource : {}", crmLeadSourceDTO);
        if (crmLeadSourceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new crmLeadSource cannot already have an ID")).body(null);
        }
        CrmLeadSource crmLeadSource = crmLeadSourceMapper.crmLeadSourceDTOToCrmLeadSource(crmLeadSourceDTO);
        crmLeadSource = crmLeadSourceRepository.save(crmLeadSource);
        CrmLeadSourceDTO result = crmLeadSourceMapper.crmLeadSourceToCrmLeadSourceDTO(crmLeadSource);
        return ResponseEntity.created(new URI("/api/crm-lead-sources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /crm-lead-sources : Updates an existing crmLeadSource.
     *
     * @param crmLeadSourceDTO the crmLeadSourceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated crmLeadSourceDTO,
     * or with status 400 (Bad Request) if the crmLeadSourceDTO is not valid,
     * or with status 500 (Internal Server Error) if the crmLeadSourceDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/crm-lead-sources")
    @Timed
    public ResponseEntity<CrmLeadSourceDTO> updateCrmLeadSource(@RequestBody CrmLeadSourceDTO crmLeadSourceDTO) throws URISyntaxException {
        log.debug("REST request to update CrmLeadSource : {}", crmLeadSourceDTO);
        if (crmLeadSourceDTO.getId() == null) {
            return createCrmLeadSource(crmLeadSourceDTO);
        }
        CrmLeadSource crmLeadSource = crmLeadSourceMapper.crmLeadSourceDTOToCrmLeadSource(crmLeadSourceDTO);
        crmLeadSource = crmLeadSourceRepository.save(crmLeadSource);
        CrmLeadSourceDTO result = crmLeadSourceMapper.crmLeadSourceToCrmLeadSourceDTO(crmLeadSource);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, crmLeadSourceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /crm-lead-sources : get all the crmLeadSources.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of crmLeadSources in body
     */
    @GetMapping("/crm-lead-sources")
    @Timed
    public List<CrmLeadSourceDTO> getAllCrmLeadSources() {
        log.debug("REST request to get all CrmLeadSources");
        List<CrmLeadSource> crmLeadSources = crmLeadSourceRepository.findAll();
        return crmLeadSourceMapper.crmLeadSourcesToCrmLeadSourceDTOs(crmLeadSources);
    }

    /**
     * GET  /crm-lead-sources/:id : get the "id" crmLeadSource.
     *
     * @param id the id of the crmLeadSourceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the crmLeadSourceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/crm-lead-sources/{id}")
    @Timed
    public ResponseEntity<CrmLeadSourceDTO> getCrmLeadSource(@PathVariable Long id) {
        log.debug("REST request to get CrmLeadSource : {}", id);
        CrmLeadSource crmLeadSource = crmLeadSourceRepository.findOne(id);
        CrmLeadSourceDTO crmLeadSourceDTO = crmLeadSourceMapper.crmLeadSourceToCrmLeadSourceDTO(crmLeadSource);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(crmLeadSourceDTO));
    }

    /**
     * DELETE  /crm-lead-sources/:id : delete the "id" crmLeadSource.
     *
     * @param id the id of the crmLeadSourceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/crm-lead-sources/{id}")
    @Timed
    public ResponseEntity<Void> deleteCrmLeadSource(@PathVariable Long id) {
        log.debug("REST request to delete CrmLeadSource : {}", id);
        crmLeadSourceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
