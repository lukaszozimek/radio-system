package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CrmTaskStatus;

import io.protone.repository.CrmTaskStatusRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CrmTaskStatusDTO;
import io.protone.service.mapper.CrmTaskStatusMapper;
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
 * REST controller for managing CrmTaskStatus.
 */
@RestController
@RequestMapping("/api")
public class CrmTaskStatusResource {

    private final Logger log = LoggerFactory.getLogger(CrmTaskStatusResource.class);

    private static final String ENTITY_NAME = "crmTaskStatus";
        
    private final CrmTaskStatusRepository crmTaskStatusRepository;

    private final CrmTaskStatusMapper crmTaskStatusMapper;

    public CrmTaskStatusResource(CrmTaskStatusRepository crmTaskStatusRepository, CrmTaskStatusMapper crmTaskStatusMapper) {
        this.crmTaskStatusRepository = crmTaskStatusRepository;
        this.crmTaskStatusMapper = crmTaskStatusMapper;
    }

    /**
     * POST  /crm-task-statuses : Create a new crmTaskStatus.
     *
     * @param crmTaskStatusDTO the crmTaskStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new crmTaskStatusDTO, or with status 400 (Bad Request) if the crmTaskStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/crm-task-statuses")
    @Timed
    public ResponseEntity<CrmTaskStatusDTO> createCrmTaskStatus(@RequestBody CrmTaskStatusDTO crmTaskStatusDTO) throws URISyntaxException {
        log.debug("REST request to save CrmTaskStatus : {}", crmTaskStatusDTO);
        if (crmTaskStatusDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new crmTaskStatus cannot already have an ID")).body(null);
        }
        CrmTaskStatus crmTaskStatus = crmTaskStatusMapper.crmTaskStatusDTOToCrmTaskStatus(crmTaskStatusDTO);
        crmTaskStatus = crmTaskStatusRepository.save(crmTaskStatus);
        CrmTaskStatusDTO result = crmTaskStatusMapper.crmTaskStatusToCrmTaskStatusDTO(crmTaskStatus);
        return ResponseEntity.created(new URI("/api/crm-task-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /crm-task-statuses : Updates an existing crmTaskStatus.
     *
     * @param crmTaskStatusDTO the crmTaskStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated crmTaskStatusDTO,
     * or with status 400 (Bad Request) if the crmTaskStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the crmTaskStatusDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/crm-task-statuses")
    @Timed
    public ResponseEntity<CrmTaskStatusDTO> updateCrmTaskStatus(@RequestBody CrmTaskStatusDTO crmTaskStatusDTO) throws URISyntaxException {
        log.debug("REST request to update CrmTaskStatus : {}", crmTaskStatusDTO);
        if (crmTaskStatusDTO.getId() == null) {
            return createCrmTaskStatus(crmTaskStatusDTO);
        }
        CrmTaskStatus crmTaskStatus = crmTaskStatusMapper.crmTaskStatusDTOToCrmTaskStatus(crmTaskStatusDTO);
        crmTaskStatus = crmTaskStatusRepository.save(crmTaskStatus);
        CrmTaskStatusDTO result = crmTaskStatusMapper.crmTaskStatusToCrmTaskStatusDTO(crmTaskStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, crmTaskStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /crm-task-statuses : get all the crmTaskStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of crmTaskStatuses in body
     */
    @GetMapping("/crm-task-statuses")
    @Timed
    public List<CrmTaskStatusDTO> getAllCrmTaskStatuses() {
        log.debug("REST request to get all CrmTaskStatuses");
        List<CrmTaskStatus> crmTaskStatuses = crmTaskStatusRepository.findAll();
        return crmTaskStatusMapper.crmTaskStatusesToCrmTaskStatusDTOs(crmTaskStatuses);
    }

    /**
     * GET  /crm-task-statuses/:id : get the "id" crmTaskStatus.
     *
     * @param id the id of the crmTaskStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the crmTaskStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/crm-task-statuses/{id}")
    @Timed
    public ResponseEntity<CrmTaskStatusDTO> getCrmTaskStatus(@PathVariable Long id) {
        log.debug("REST request to get CrmTaskStatus : {}", id);
        CrmTaskStatus crmTaskStatus = crmTaskStatusRepository.findOne(id);
        CrmTaskStatusDTO crmTaskStatusDTO = crmTaskStatusMapper.crmTaskStatusToCrmTaskStatusDTO(crmTaskStatus);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(crmTaskStatusDTO));
    }

    /**
     * DELETE  /crm-task-statuses/:id : delete the "id" crmTaskStatus.
     *
     * @param id the id of the crmTaskStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/crm-task-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCrmTaskStatus(@PathVariable Long id) {
        log.debug("REST request to delete CrmTaskStatus : {}", id);
        crmTaskStatusRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
