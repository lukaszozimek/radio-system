package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CrmTask;

import io.protone.repository.CrmTaskRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CrmTaskDTO;
import io.protone.service.mapper.CrmTaskMapper;
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
 * REST controller for managing CrmTask.
 */
@RestController
@RequestMapping("/api")
public class CrmTaskResource {

    private final Logger log = LoggerFactory.getLogger(CrmTaskResource.class);

    private static final String ENTITY_NAME = "crmTask";

    private final CrmTaskRepository crmTaskRepository;

    private final CrmTaskMapper crmTaskMapper;

    public CrmTaskResource(CrmTaskRepository crmTaskRepository, CrmTaskMapper crmTaskMapper) {
        this.crmTaskRepository = crmTaskRepository;
        this.crmTaskMapper = crmTaskMapper;
    }

    /**
     * POST  /crm-tasks : Create a new crmTask.
     *
     * @param crmTaskDTO the crmTaskDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new crmTaskDTO, or with status 400 (Bad Request) if the crmTask has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/crm-tasks")
    @Timed
    public ResponseEntity<CrmTaskDTO> createCrmTask(@RequestBody CrmTaskDTO crmTaskDTO) throws URISyntaxException {
        log.debug("REST request to save CrmTask : {}", crmTaskDTO);
        if (crmTaskDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new crmTask cannot already have an ID")).body(null);
        }
        CrmTask crmTask = crmTaskMapper.crmTaskDTOToCrmTask(crmTaskDTO);
        crmTask = crmTaskRepository.save(crmTask);
        CrmTaskDTO result = crmTaskMapper.crmTaskToCrmTaskDTO(crmTask);
        return ResponseEntity.created(new URI("/api/crm-tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /crm-tasks : Updates an existing crmTask.
     *
     * @param crmTaskDTO the crmTaskDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated crmTaskDTO,
     * or with status 400 (Bad Request) if the crmTaskDTO is not valid,
     * or with status 500 (Internal Server Error) if the crmTaskDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/crm-tasks")
    @Timed
    public ResponseEntity<CrmTaskDTO> updateCrmTask(@RequestBody CrmTaskDTO crmTaskDTO) throws URISyntaxException {
        log.debug("REST request to update CrmTask : {}", crmTaskDTO);
        if (crmTaskDTO.getId() == null) {
            return createCrmTask(crmTaskDTO);
        }
        CrmTask crmTask = crmTaskMapper.crmTaskDTOToCrmTask(crmTaskDTO);
        crmTask = crmTaskRepository.save(crmTask);
        CrmTaskDTO result = crmTaskMapper.crmTaskToCrmTaskDTO(crmTask);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, crmTaskDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /crm-tasks : get all the crmTasks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of crmTasks in body
     */
    @GetMapping("/crm-tasks")
    @Timed
    public List<CrmTaskDTO> getAllCrmTasks() {
        log.debug("REST request to get all CrmTasks");
        List<CrmTask> crmTasks = crmTaskRepository.findAll();
        return crmTaskMapper.crmTasksToCrmTaskDTOs(crmTasks);
    }

    /**
     * GET  /crm-tasks/:id : get the "id" crmTask.
     *
     * @param id the id of the crmTaskDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the crmTaskDTO, or with status 404 (Not Found)
     */
    @GetMapping("/crm-tasks/{id}")
    @Timed
    public ResponseEntity<CrmTaskDTO> getCrmTask(@PathVariable Long id) {
        log.debug("REST request to get CrmTask : {}", id);
        CrmTask crmTask = crmTaskRepository.findOne(id);
        CrmTaskDTO crmTaskDTO = crmTaskMapper.crmTaskToCrmTaskDTO(crmTask);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(crmTaskDTO));
    }

    /**
     * DELETE  /crm-tasks/:id : delete the "id" crmTask.
     *
     * @param id the id of the crmTaskDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/crm-tasks/{id}")
    @Timed
    public ResponseEntity<Void> deleteCrmTask(@PathVariable Long id) {
        log.debug("REST request to delete CrmTask : {}", id);
        crmTaskRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
