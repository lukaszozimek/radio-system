package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CorStatus;

import io.protone.repository.CorStatusRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CorStatusDTO;
import io.protone.service.mapper.CorStatusMapper;
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
 * REST controller for managing CorStatus.
 */
@RestController
@RequestMapping("/api")
public class CorStatusResource {

    private final Logger log = LoggerFactory.getLogger(CorStatusResource.class);

    private static final String ENTITY_NAME = "corStatus";
        
    private final CorStatusRepository corStatusRepository;

    private final CorStatusMapper corStatusMapper;

    public CorStatusResource(CorStatusRepository corStatusRepository, CorStatusMapper corStatusMapper) {
        this.corStatusRepository = corStatusRepository;
        this.corStatusMapper = corStatusMapper;
    }

    /**
     * POST  /cor-statuses : Create a new corStatus.
     *
     * @param corStatusDTO the corStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new corStatusDTO, or with status 400 (Bad Request) if the corStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cor-statuses")
    @Timed
    public ResponseEntity<CorStatusDTO> createCorStatus(@RequestBody CorStatusDTO corStatusDTO) throws URISyntaxException {
        log.debug("REST request to save CorStatus : {}", corStatusDTO);
        if (corStatusDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new corStatus cannot already have an ID")).body(null);
        }
        CorStatus corStatus = corStatusMapper.corStatusDTOToCorStatus(corStatusDTO);
        corStatus = corStatusRepository.save(corStatus);
        CorStatusDTO result = corStatusMapper.corStatusToCorStatusDTO(corStatus);
        return ResponseEntity.created(new URI("/api/cor-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cor-statuses : Updates an existing corStatus.
     *
     * @param corStatusDTO the corStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated corStatusDTO,
     * or with status 400 (Bad Request) if the corStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the corStatusDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cor-statuses")
    @Timed
    public ResponseEntity<CorStatusDTO> updateCorStatus(@RequestBody CorStatusDTO corStatusDTO) throws URISyntaxException {
        log.debug("REST request to update CorStatus : {}", corStatusDTO);
        if (corStatusDTO.getId() == null) {
            return createCorStatus(corStatusDTO);
        }
        CorStatus corStatus = corStatusMapper.corStatusDTOToCorStatus(corStatusDTO);
        corStatus = corStatusRepository.save(corStatus);
        CorStatusDTO result = corStatusMapper.corStatusToCorStatusDTO(corStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, corStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cor-statuses : get all the corStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of corStatuses in body
     */
    @GetMapping("/cor-statuses")
    @Timed
    public List<CorStatusDTO> getAllCorStatuses() {
        log.debug("REST request to get all CorStatuses");
        List<CorStatus> corStatuses = corStatusRepository.findAll();
        return corStatusMapper.corStatusesToCorStatusDTOs(corStatuses);
    }

    /**
     * GET  /cor-statuses/:id : get the "id" corStatus.
     *
     * @param id the id of the corStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the corStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cor-statuses/{id}")
    @Timed
    public ResponseEntity<CorStatusDTO> getCorStatus(@PathVariable Long id) {
        log.debug("REST request to get CorStatus : {}", id);
        CorStatus corStatus = corStatusRepository.findOne(id);
        CorStatusDTO corStatusDTO = corStatusMapper.corStatusToCorStatusDTO(corStatus);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(corStatusDTO));
    }

    /**
     * DELETE  /cor-statuses/:id : delete the "id" corStatus.
     *
     * @param id the id of the corStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cor-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorStatus(@PathVariable Long id) {
        log.debug("REST request to delete CorStatus : {}", id);
        corStatusRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
