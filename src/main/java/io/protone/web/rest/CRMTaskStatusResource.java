package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CRMTaskStatus;

import io.protone.repository.CRMTaskStatusRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CRMTaskStatusDTO;
import io.protone.service.mapper.CRMTaskStatusMapper;

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
 * REST controller for managing CRMTaskStatus.
 */
@RestController
@RequestMapping("/api")
public class CRMTaskStatusResource {

    private final Logger log = LoggerFactory.getLogger(CRMTaskStatusResource.class);
        
    @Inject
    private CRMTaskStatusRepository cRMTaskStatusRepository;

    @Inject
    private CRMTaskStatusMapper cRMTaskStatusMapper;

    /**
     * POST  /c-rm-task-statuses : Create a new cRMTaskStatus.
     *
     * @param cRMTaskStatusDTO the cRMTaskStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cRMTaskStatusDTO, or with status 400 (Bad Request) if the cRMTaskStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-rm-task-statuses")
    @Timed
    public ResponseEntity<CRMTaskStatusDTO> createCRMTaskStatus(@RequestBody CRMTaskStatusDTO cRMTaskStatusDTO) throws URISyntaxException {
        log.debug("REST request to save CRMTaskStatus : {}", cRMTaskStatusDTO);
        if (cRMTaskStatusDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cRMTaskStatus", "idexists", "A new cRMTaskStatus cannot already have an ID")).body(null);
        }
        CRMTaskStatus cRMTaskStatus = cRMTaskStatusMapper.cRMTaskStatusDTOToCRMTaskStatus(cRMTaskStatusDTO);
        cRMTaskStatus = cRMTaskStatusRepository.save(cRMTaskStatus);
        CRMTaskStatusDTO result = cRMTaskStatusMapper.cRMTaskStatusToCRMTaskStatusDTO(cRMTaskStatus);
        return ResponseEntity.created(new URI("/api/c-rm-task-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cRMTaskStatus", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-rm-task-statuses : Updates an existing cRMTaskStatus.
     *
     * @param cRMTaskStatusDTO the cRMTaskStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cRMTaskStatusDTO,
     * or with status 400 (Bad Request) if the cRMTaskStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the cRMTaskStatusDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-rm-task-statuses")
    @Timed
    public ResponseEntity<CRMTaskStatusDTO> updateCRMTaskStatus(@RequestBody CRMTaskStatusDTO cRMTaskStatusDTO) throws URISyntaxException {
        log.debug("REST request to update CRMTaskStatus : {}", cRMTaskStatusDTO);
        if (cRMTaskStatusDTO.getId() == null) {
            return createCRMTaskStatus(cRMTaskStatusDTO);
        }
        CRMTaskStatus cRMTaskStatus = cRMTaskStatusMapper.cRMTaskStatusDTOToCRMTaskStatus(cRMTaskStatusDTO);
        cRMTaskStatus = cRMTaskStatusRepository.save(cRMTaskStatus);
        CRMTaskStatusDTO result = cRMTaskStatusMapper.cRMTaskStatusToCRMTaskStatusDTO(cRMTaskStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cRMTaskStatus", cRMTaskStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-rm-task-statuses : get all the cRMTaskStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cRMTaskStatuses in body
     */
    @GetMapping("/c-rm-task-statuses")
    @Timed
    public List<CRMTaskStatusDTO> getAllCRMTaskStatuses() {
        log.debug("REST request to get all CRMTaskStatuses");
        List<CRMTaskStatus> cRMTaskStatuses = cRMTaskStatusRepository.findAll();
        return cRMTaskStatusMapper.cRMTaskStatusesToCRMTaskStatusDTOs(cRMTaskStatuses);
    }

    /**
     * GET  /c-rm-task-statuses/:id : get the "id" cRMTaskStatus.
     *
     * @param id the id of the cRMTaskStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cRMTaskStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/c-rm-task-statuses/{id}")
    @Timed
    public ResponseEntity<CRMTaskStatusDTO> getCRMTaskStatus(@PathVariable Long id) {
        log.debug("REST request to get CRMTaskStatus : {}", id);
        CRMTaskStatus cRMTaskStatus = cRMTaskStatusRepository.findOne(id);
        CRMTaskStatusDTO cRMTaskStatusDTO = cRMTaskStatusMapper.cRMTaskStatusToCRMTaskStatusDTO(cRMTaskStatus);
        return Optional.ofNullable(cRMTaskStatusDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-rm-task-statuses/:id : delete the "id" cRMTaskStatus.
     *
     * @param id the id of the cRMTaskStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-rm-task-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCRMTaskStatus(@PathVariable Long id) {
        log.debug("REST request to delete CRMTaskStatus : {}", id);
        cRMTaskStatusRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cRMTaskStatus", id.toString())).build();
    }

}
