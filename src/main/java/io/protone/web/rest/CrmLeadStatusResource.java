package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CrmLeadStatus;

import io.protone.repository.CrmLeadStatusRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CrmLeadStatusDTO;
import io.protone.service.mapper.CrmLeadStatusMapper;
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
 * REST controller for managing CrmLeadStatus.
 */
@RestController
@RequestMapping("/api")
public class CrmLeadStatusResource {

    private final Logger log = LoggerFactory.getLogger(CrmLeadStatusResource.class);

    private static final String ENTITY_NAME = "crmLeadStatus";
        
    private final CrmLeadStatusRepository crmLeadStatusRepository;

    private final CrmLeadStatusMapper crmLeadStatusMapper;

    public CrmLeadStatusResource(CrmLeadStatusRepository crmLeadStatusRepository, CrmLeadStatusMapper crmLeadStatusMapper) {
        this.crmLeadStatusRepository = crmLeadStatusRepository;
        this.crmLeadStatusMapper = crmLeadStatusMapper;
    }

    /**
     * POST  /crm-lead-statuses : Create a new crmLeadStatus.
     *
     * @param crmLeadStatusDTO the crmLeadStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new crmLeadStatusDTO, or with status 400 (Bad Request) if the crmLeadStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/crm-lead-statuses")
    @Timed
    public ResponseEntity<CrmLeadStatusDTO> createCrmLeadStatus(@RequestBody CrmLeadStatusDTO crmLeadStatusDTO) throws URISyntaxException {
        log.debug("REST request to save CrmLeadStatus : {}", crmLeadStatusDTO);
        if (crmLeadStatusDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new crmLeadStatus cannot already have an ID")).body(null);
        }
        CrmLeadStatus crmLeadStatus = crmLeadStatusMapper.crmLeadStatusDTOToCrmLeadStatus(crmLeadStatusDTO);
        crmLeadStatus = crmLeadStatusRepository.save(crmLeadStatus);
        CrmLeadStatusDTO result = crmLeadStatusMapper.crmLeadStatusToCrmLeadStatusDTO(crmLeadStatus);
        return ResponseEntity.created(new URI("/api/crm-lead-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /crm-lead-statuses : Updates an existing crmLeadStatus.
     *
     * @param crmLeadStatusDTO the crmLeadStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated crmLeadStatusDTO,
     * or with status 400 (Bad Request) if the crmLeadStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the crmLeadStatusDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/crm-lead-statuses")
    @Timed
    public ResponseEntity<CrmLeadStatusDTO> updateCrmLeadStatus(@RequestBody CrmLeadStatusDTO crmLeadStatusDTO) throws URISyntaxException {
        log.debug("REST request to update CrmLeadStatus : {}", crmLeadStatusDTO);
        if (crmLeadStatusDTO.getId() == null) {
            return createCrmLeadStatus(crmLeadStatusDTO);
        }
        CrmLeadStatus crmLeadStatus = crmLeadStatusMapper.crmLeadStatusDTOToCrmLeadStatus(crmLeadStatusDTO);
        crmLeadStatus = crmLeadStatusRepository.save(crmLeadStatus);
        CrmLeadStatusDTO result = crmLeadStatusMapper.crmLeadStatusToCrmLeadStatusDTO(crmLeadStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, crmLeadStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /crm-lead-statuses : get all the crmLeadStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of crmLeadStatuses in body
     */
    @GetMapping("/crm-lead-statuses")
    @Timed
    public List<CrmLeadStatusDTO> getAllCrmLeadStatuses() {
        log.debug("REST request to get all CrmLeadStatuses");
        List<CrmLeadStatus> crmLeadStatuses = crmLeadStatusRepository.findAll();
        return crmLeadStatusMapper.crmLeadStatusesToCrmLeadStatusDTOs(crmLeadStatuses);
    }

    /**
     * GET  /crm-lead-statuses/:id : get the "id" crmLeadStatus.
     *
     * @param id the id of the crmLeadStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the crmLeadStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/crm-lead-statuses/{id}")
    @Timed
    public ResponseEntity<CrmLeadStatusDTO> getCrmLeadStatus(@PathVariable Long id) {
        log.debug("REST request to get CrmLeadStatus : {}", id);
        CrmLeadStatus crmLeadStatus = crmLeadStatusRepository.findOne(id);
        CrmLeadStatusDTO crmLeadStatusDTO = crmLeadStatusMapper.crmLeadStatusToCrmLeadStatusDTO(crmLeadStatus);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(crmLeadStatusDTO));
    }

    /**
     * DELETE  /crm-lead-statuses/:id : delete the "id" crmLeadStatus.
     *
     * @param id the id of the crmLeadStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/crm-lead-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCrmLeadStatus(@PathVariable Long id) {
        log.debug("REST request to delete CrmLeadStatus : {}", id);
        crmLeadStatusRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
