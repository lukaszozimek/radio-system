package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.TraOrderStatus;

import io.protone.repository.TraOrderStatusRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.TraOrderStatusDTO;
import io.protone.service.mapper.TraOrderStatusMapper;
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
 * REST controller for managing TraOrderStatus.
 */
@RestController
@RequestMapping("/api")
public class TraOrderStatusResource {

    private final Logger log = LoggerFactory.getLogger(TraOrderStatusResource.class);

    private static final String ENTITY_NAME = "traOrderStatus";
        
    private final TraOrderStatusRepository traOrderStatusRepository;

    private final TraOrderStatusMapper traOrderStatusMapper;

    public TraOrderStatusResource(TraOrderStatusRepository traOrderStatusRepository, TraOrderStatusMapper traOrderStatusMapper) {
        this.traOrderStatusRepository = traOrderStatusRepository;
        this.traOrderStatusMapper = traOrderStatusMapper;
    }

    /**
     * POST  /tra-order-statuses : Create a new traOrderStatus.
     *
     * @param traOrderStatusDTO the traOrderStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new traOrderStatusDTO, or with status 400 (Bad Request) if the traOrderStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tra-order-statuses")
    @Timed
    public ResponseEntity<TraOrderStatusDTO> createTraOrderStatus(@RequestBody TraOrderStatusDTO traOrderStatusDTO) throws URISyntaxException {
        log.debug("REST request to save TraOrderStatus : {}", traOrderStatusDTO);
        if (traOrderStatusDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new traOrderStatus cannot already have an ID")).body(null);
        }
        TraOrderStatus traOrderStatus = traOrderStatusMapper.traOrderStatusDTOToTraOrderStatus(traOrderStatusDTO);
        traOrderStatus = traOrderStatusRepository.save(traOrderStatus);
        TraOrderStatusDTO result = traOrderStatusMapper.traOrderStatusToTraOrderStatusDTO(traOrderStatus);
        return ResponseEntity.created(new URI("/api/tra-order-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tra-order-statuses : Updates an existing traOrderStatus.
     *
     * @param traOrderStatusDTO the traOrderStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated traOrderStatusDTO,
     * or with status 400 (Bad Request) if the traOrderStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the traOrderStatusDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tra-order-statuses")
    @Timed
    public ResponseEntity<TraOrderStatusDTO> updateTraOrderStatus(@RequestBody TraOrderStatusDTO traOrderStatusDTO) throws URISyntaxException {
        log.debug("REST request to update TraOrderStatus : {}", traOrderStatusDTO);
        if (traOrderStatusDTO.getId() == null) {
            return createTraOrderStatus(traOrderStatusDTO);
        }
        TraOrderStatus traOrderStatus = traOrderStatusMapper.traOrderStatusDTOToTraOrderStatus(traOrderStatusDTO);
        traOrderStatus = traOrderStatusRepository.save(traOrderStatus);
        TraOrderStatusDTO result = traOrderStatusMapper.traOrderStatusToTraOrderStatusDTO(traOrderStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, traOrderStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tra-order-statuses : get all the traOrderStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of traOrderStatuses in body
     */
    @GetMapping("/tra-order-statuses")
    @Timed
    public List<TraOrderStatusDTO> getAllTraOrderStatuses() {
        log.debug("REST request to get all TraOrderStatuses");
        List<TraOrderStatus> traOrderStatuses = traOrderStatusRepository.findAll();
        return traOrderStatusMapper.traOrderStatusesToTraOrderStatusDTOs(traOrderStatuses);
    }

    /**
     * GET  /tra-order-statuses/:id : get the "id" traOrderStatus.
     *
     * @param id the id of the traOrderStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the traOrderStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tra-order-statuses/{id}")
    @Timed
    public ResponseEntity<TraOrderStatusDTO> getTraOrderStatus(@PathVariable Long id) {
        log.debug("REST request to get TraOrderStatus : {}", id);
        TraOrderStatus traOrderStatus = traOrderStatusRepository.findOne(id);
        TraOrderStatusDTO traOrderStatusDTO = traOrderStatusMapper.traOrderStatusToTraOrderStatusDTO(traOrderStatus);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(traOrderStatusDTO));
    }

    /**
     * DELETE  /tra-order-statuses/:id : delete the "id" traOrderStatus.
     *
     * @param id the id of the traOrderStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tra-order-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteTraOrderStatus(@PathVariable Long id) {
        log.debug("REST request to delete TraOrderStatus : {}", id);
        traOrderStatusRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
