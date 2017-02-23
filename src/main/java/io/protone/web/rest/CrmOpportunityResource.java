package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CrmOpportunity;

import io.protone.repository.CrmOpportunityRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CrmOpportunityDTO;
import io.protone.service.mapper.CrmOpportunityMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing CrmOpportunity.
 */
@RestController
@RequestMapping("/api")
public class CrmOpportunityResource {

    private final Logger log = LoggerFactory.getLogger(CrmOpportunityResource.class);

    private static final String ENTITY_NAME = "crmOpportunity";
        
    private final CrmOpportunityRepository crmOpportunityRepository;

    private final CrmOpportunityMapper crmOpportunityMapper;

    public CrmOpportunityResource(CrmOpportunityRepository crmOpportunityRepository, CrmOpportunityMapper crmOpportunityMapper) {
        this.crmOpportunityRepository = crmOpportunityRepository;
        this.crmOpportunityMapper = crmOpportunityMapper;
    }

    /**
     * POST  /crm-opportunities : Create a new crmOpportunity.
     *
     * @param crmOpportunityDTO the crmOpportunityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new crmOpportunityDTO, or with status 400 (Bad Request) if the crmOpportunity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/crm-opportunities")
    @Timed
    public ResponseEntity<CrmOpportunityDTO> createCrmOpportunity(@RequestBody CrmOpportunityDTO crmOpportunityDTO) throws URISyntaxException {
        log.debug("REST request to save CrmOpportunity : {}", crmOpportunityDTO);
        if (crmOpportunityDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new crmOpportunity cannot already have an ID")).body(null);
        }
        CrmOpportunity crmOpportunity = crmOpportunityMapper.crmOpportunityDTOToCrmOpportunity(crmOpportunityDTO);
        crmOpportunity = crmOpportunityRepository.save(crmOpportunity);
        CrmOpportunityDTO result = crmOpportunityMapper.crmOpportunityToCrmOpportunityDTO(crmOpportunity);
        return ResponseEntity.created(new URI("/api/crm-opportunities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /crm-opportunities : Updates an existing crmOpportunity.
     *
     * @param crmOpportunityDTO the crmOpportunityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated crmOpportunityDTO,
     * or with status 400 (Bad Request) if the crmOpportunityDTO is not valid,
     * or with status 500 (Internal Server Error) if the crmOpportunityDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/crm-opportunities")
    @Timed
    public ResponseEntity<CrmOpportunityDTO> updateCrmOpportunity(@RequestBody CrmOpportunityDTO crmOpportunityDTO) throws URISyntaxException {
        log.debug("REST request to update CrmOpportunity : {}", crmOpportunityDTO);
        if (crmOpportunityDTO.getId() == null) {
            return createCrmOpportunity(crmOpportunityDTO);
        }
        CrmOpportunity crmOpportunity = crmOpportunityMapper.crmOpportunityDTOToCrmOpportunity(crmOpportunityDTO);
        crmOpportunity = crmOpportunityRepository.save(crmOpportunity);
        CrmOpportunityDTO result = crmOpportunityMapper.crmOpportunityToCrmOpportunityDTO(crmOpportunity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, crmOpportunityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /crm-opportunities : get all the crmOpportunities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of crmOpportunities in body
     */
    @GetMapping("/crm-opportunities")
    @Timed
    public List<CrmOpportunityDTO> getAllCrmOpportunities() {
        log.debug("REST request to get all CrmOpportunities");
        List<CrmOpportunity> crmOpportunities = crmOpportunityRepository.findAll();
        return crmOpportunityMapper.crmOpportunitiesToCrmOpportunityDTOs(crmOpportunities);
    }

    /**
     * GET  /crm-opportunities/:id : get the "id" crmOpportunity.
     *
     * @param id the id of the crmOpportunityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the crmOpportunityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/crm-opportunities/{id}")
    @Timed
    public ResponseEntity<CrmOpportunityDTO> getCrmOpportunity(@PathVariable Long id) {
        log.debug("REST request to get CrmOpportunity : {}", id);
        CrmOpportunity crmOpportunity = crmOpportunityRepository.findOne(id);
        CrmOpportunityDTO crmOpportunityDTO = crmOpportunityMapper.crmOpportunityToCrmOpportunityDTO(crmOpportunity);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(crmOpportunityDTO));
    }

    /**
     * DELETE  /crm-opportunities/:id : delete the "id" crmOpportunity.
     *
     * @param id the id of the crmOpportunityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/crm-opportunities/{id}")
    @Timed
    public ResponseEntity<Void> deleteCrmOpportunity(@PathVariable Long id) {
        log.debug("REST request to delete CrmOpportunity : {}", id);
        crmOpportunityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
