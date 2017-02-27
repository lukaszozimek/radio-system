package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CrmContactStatus;

import io.protone.repository.CrmContactStatusRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CrmContactStatusDTO;
import io.protone.service.mapper.CrmContactStatusMapper;
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
 * REST controller for managing CrmContactStatus.
 */
@RestController
@RequestMapping("/api")
public class CrmContactStatusResource {

    private final Logger log = LoggerFactory.getLogger(CrmContactStatusResource.class);

    private static final String ENTITY_NAME = "crmContactStatus";

    private final CrmContactStatusRepository crmContactStatusRepository;

    private final CrmContactStatusMapper crmContactStatusMapper;

    public CrmContactStatusResource(CrmContactStatusRepository crmContactStatusRepository, CrmContactStatusMapper crmContactStatusMapper) {
        this.crmContactStatusRepository = crmContactStatusRepository;
        this.crmContactStatusMapper = crmContactStatusMapper;
    }

    /**
     * POST  /crm-contact-statuses : Create a new crmContactStatus.
     *
     * @param crmContactStatusDTO the crmContactStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new crmContactStatusDTO, or with status 400 (Bad Request) if the crmContactStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/crm-contact-statuses")
    @Timed
    public ResponseEntity<CrmContactStatusDTO> createCrmContactStatus(@RequestBody CrmContactStatusDTO crmContactStatusDTO) throws URISyntaxException {
        log.debug("REST request to save CrmContactStatus : {}", crmContactStatusDTO);
        if (crmContactStatusDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new crmContactStatus cannot already have an ID")).body(null);
        }
        CrmContactStatus crmContactStatus = crmContactStatusMapper.crmContactStatusDTOToCrmContactStatus(crmContactStatusDTO);
        crmContactStatus = crmContactStatusRepository.save(crmContactStatus);
        CrmContactStatusDTO result = crmContactStatusMapper.crmContactStatusToCrmContactStatusDTO(crmContactStatus);
        return ResponseEntity.created(new URI("/api/crm-contact-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /crm-contact-statuses : Updates an existing crmContactStatus.
     *
     * @param crmContactStatusDTO the crmContactStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated crmContactStatusDTO,
     * or with status 400 (Bad Request) if the crmContactStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the crmContactStatusDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/crm-contact-statuses")
    @Timed
    public ResponseEntity<CrmContactStatusDTO> updateCrmContactStatus(@RequestBody CrmContactStatusDTO crmContactStatusDTO) throws URISyntaxException {
        log.debug("REST request to update CrmContactStatus : {}", crmContactStatusDTO);
        if (crmContactStatusDTO.getId() == null) {
            return createCrmContactStatus(crmContactStatusDTO);
        }
        CrmContactStatus crmContactStatus = crmContactStatusMapper.crmContactStatusDTOToCrmContactStatus(crmContactStatusDTO);
        crmContactStatus = crmContactStatusRepository.save(crmContactStatus);
        CrmContactStatusDTO result = crmContactStatusMapper.crmContactStatusToCrmContactStatusDTO(crmContactStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, crmContactStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /crm-contact-statuses : get all the crmContactStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of crmContactStatuses in body
     */
    @GetMapping("/crm-contact-statuses")
    @Timed
    public List<CrmContactStatusDTO> getAllCrmContactStatuses() {
        log.debug("REST request to get all CrmContactStatuses");
        List<CrmContactStatus> crmContactStatuses = crmContactStatusRepository.findAll();
        return crmContactStatusMapper.crmContactStatusesToCrmContactStatusDTOs(crmContactStatuses);
    }

    /**
     * GET  /crm-contact-statuses/:id : get the "id" crmContactStatus.
     *
     * @param id the id of the crmContactStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the crmContactStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/crm-contact-statuses/{id}")
    @Timed
    public ResponseEntity<CrmContactStatusDTO> getCrmContactStatus(@PathVariable Long id) {
        log.debug("REST request to get CrmContactStatus : {}", id);
        CrmContactStatus crmContactStatus = crmContactStatusRepository.findOne(id);
        CrmContactStatusDTO crmContactStatusDTO = crmContactStatusMapper.crmContactStatusToCrmContactStatusDTO(crmContactStatus);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(crmContactStatusDTO));
    }

    /**
     * DELETE  /crm-contact-statuses/:id : delete the "id" crmContactStatus.
     *
     * @param id the id of the crmContactStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/crm-contact-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCrmContactStatus(@PathVariable Long id) {
        log.debug("REST request to delete CrmContactStatus : {}", id);
        crmContactStatusRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
