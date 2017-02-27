package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CrmLead;

import io.protone.repository.CrmLeadRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CrmLeadDTO;
import io.protone.service.mapper.CrmLeadMapper;
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
 * REST controller for managing CrmLead.
 */
@RestController
@RequestMapping("/api")
public class CrmLeadResource {

    private final Logger log = LoggerFactory.getLogger(CrmLeadResource.class);

    private static final String ENTITY_NAME = "crmLead";

    private final CrmLeadRepository crmLeadRepository;

    private final CrmLeadMapper crmLeadMapper;

    public CrmLeadResource(CrmLeadRepository crmLeadRepository, CrmLeadMapper crmLeadMapper) {
        this.crmLeadRepository = crmLeadRepository;
        this.crmLeadMapper = crmLeadMapper;
    }

    /**
     * POST  /crm-leads : Create a new crmLead.
     *
     * @param crmLeadDTO the crmLeadDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new crmLeadDTO, or with status 400 (Bad Request) if the crmLead has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/crm-leads")
    @Timed
    public ResponseEntity<CrmLeadDTO> createCrmLead(@RequestBody CrmLeadDTO crmLeadDTO) throws URISyntaxException {
        log.debug("REST request to save CrmLead : {}", crmLeadDTO);
        if (crmLeadDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new crmLead cannot already have an ID")).body(null);
        }
        CrmLead crmLead = crmLeadMapper.crmLeadDTOToCrmLead(crmLeadDTO);
        crmLead = crmLeadRepository.save(crmLead);
        CrmLeadDTO result = crmLeadMapper.crmLeadToCrmLeadDTO(crmLead);
        return ResponseEntity.created(new URI("/api/crm-leads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /crm-leads : Updates an existing crmLead.
     *
     * @param crmLeadDTO the crmLeadDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated crmLeadDTO,
     * or with status 400 (Bad Request) if the crmLeadDTO is not valid,
     * or with status 500 (Internal Server Error) if the crmLeadDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/crm-leads")
    @Timed
    public ResponseEntity<CrmLeadDTO> updateCrmLead(@RequestBody CrmLeadDTO crmLeadDTO) throws URISyntaxException {
        log.debug("REST request to update CrmLead : {}", crmLeadDTO);
        if (crmLeadDTO.getId() == null) {
            return createCrmLead(crmLeadDTO);
        }
        CrmLead crmLead = crmLeadMapper.crmLeadDTOToCrmLead(crmLeadDTO);
        crmLead = crmLeadRepository.save(crmLead);
        CrmLeadDTO result = crmLeadMapper.crmLeadToCrmLeadDTO(crmLead);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, crmLeadDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /crm-leads : get all the crmLeads.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of crmLeads in body
     */
    @GetMapping("/crm-leads")
    @Timed
    public List<CrmLeadDTO> getAllCrmLeads() {
        log.debug("REST request to get all CrmLeads");
        List<CrmLead> crmLeads = crmLeadRepository.findAll();
        return crmLeadMapper.crmLeadsToCrmLeadDTOs(crmLeads);
    }

    /**
     * GET  /crm-leads/:id : get the "id" crmLead.
     *
     * @param id the id of the crmLeadDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the crmLeadDTO, or with status 404 (Not Found)
     */
    @GetMapping("/crm-leads/{id}")
    @Timed
    public ResponseEntity<CrmLeadDTO> getCrmLead(@PathVariable Long id) {
        log.debug("REST request to get CrmLead : {}", id);
        CrmLead crmLead = crmLeadRepository.findOne(id);
        CrmLeadDTO crmLeadDTO = crmLeadMapper.crmLeadToCrmLeadDTO(crmLead);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(crmLeadDTO));
    }

    /**
     * DELETE  /crm-leads/:id : delete the "id" crmLead.
     *
     * @param id the id of the crmLeadDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/crm-leads/{id}")
    @Timed
    public ResponseEntity<Void> deleteCrmLead(@PathVariable Long id) {
        log.debug("REST request to delete CrmLead : {}", id);
        crmLeadRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
