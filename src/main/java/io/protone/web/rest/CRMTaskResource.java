package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CRMTask;

import io.protone.repository.CRMTaskRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CRMTaskDTO;
import io.protone.service.mapper.CRMTaskMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing CRMTask.
 */
@RestController
@RequestMapping("/api")
public class CRMTaskResource {

    private final Logger log = LoggerFactory.getLogger(CRMTaskResource.class);
        
    @Inject
    private CRMTaskRepository cRMTaskRepository;

    @Inject
    private CRMTaskMapper cRMTaskMapper;

    /**
     * POST  /c-rm-tasks : Create a new cRMTask.
     *
     * @param cRMTaskDTO the cRMTaskDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cRMTaskDTO, or with status 400 (Bad Request) if the cRMTask has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-rm-tasks")
    @Timed
    public ResponseEntity<CRMTaskDTO> createCRMTask(@RequestBody CRMTaskDTO cRMTaskDTO) throws URISyntaxException {
        log.debug("REST request to save CRMTask : {}", cRMTaskDTO);
        if (cRMTaskDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cRMTask", "idexists", "A new cRMTask cannot already have an ID")).body(null);
        }
        CRMTask cRMTask = cRMTaskMapper.cRMTaskDTOToCRMTask(cRMTaskDTO);
        cRMTask = cRMTaskRepository.save(cRMTask);
        CRMTaskDTO result = cRMTaskMapper.cRMTaskToCRMTaskDTO(cRMTask);
        return ResponseEntity.created(new URI("/api/c-rm-tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cRMTask", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-rm-tasks : Updates an existing cRMTask.
     *
     * @param cRMTaskDTO the cRMTaskDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cRMTaskDTO,
     * or with status 400 (Bad Request) if the cRMTaskDTO is not valid,
     * or with status 500 (Internal Server Error) if the cRMTaskDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-rm-tasks")
    @Timed
    public ResponseEntity<CRMTaskDTO> updateCRMTask(@RequestBody CRMTaskDTO cRMTaskDTO) throws URISyntaxException {
        log.debug("REST request to update CRMTask : {}", cRMTaskDTO);
        if (cRMTaskDTO.getId() == null) {
            return createCRMTask(cRMTaskDTO);
        }
        CRMTask cRMTask = cRMTaskMapper.cRMTaskDTOToCRMTask(cRMTaskDTO);
        cRMTask = cRMTaskRepository.save(cRMTask);
        CRMTaskDTO result = cRMTaskMapper.cRMTaskToCRMTaskDTO(cRMTask);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cRMTask", cRMTaskDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-rm-tasks : get all the cRMTasks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cRMTasks in body
     */
    @GetMapping("/c-rm-tasks")
    @Timed
    public List<CRMTaskDTO> getAllCRMTasks() {
        log.debug("REST request to get all CRMTasks");
        List<CRMTask> cRMTasks = cRMTaskRepository.findAll();
        return cRMTaskMapper.cRMTasksToCRMTaskDTOs(cRMTasks);
    }

    /**
     * GET  /c-rm-tasks/:id : get the "id" cRMTask.
     *
     * @param id the id of the cRMTaskDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cRMTaskDTO, or with status 404 (Not Found)
     */
    @GetMapping("/c-rm-tasks/{id}")
    @Timed
    public ResponseEntity<CRMTaskDTO> getCRMTask(@PathVariable Long id) {
        log.debug("REST request to get CRMTask : {}", id);
        CRMTask cRMTask = cRMTaskRepository.findOne(id);
        CRMTaskDTO cRMTaskDTO = cRMTaskMapper.cRMTaskToCRMTaskDTO(cRMTask);
        return Optional.ofNullable(cRMTaskDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-rm-tasks/:id : delete the "id" cRMTask.
     *
     * @param id the id of the cRMTaskDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-rm-tasks/{id}")
    @Timed
    public ResponseEntity<Void> deleteCRMTask(@PathVariable Long id) {
        log.debug("REST request to delete CRMTask : {}", id);
        cRMTaskRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cRMTask", id.toString())).build();
    }

}
