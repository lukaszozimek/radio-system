package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.TraInvoiceStatus;

import io.protone.repository.TraInvoiceStatusRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.TraInvoiceStatusDTO;
import io.protone.service.mapper.TraInvoiceStatusMapper;
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
 * REST controller for managing TraInvoiceStatus.
 */
@RestController
@RequestMapping("/api")
public class TraInvoiceStatusResource {

    private final Logger log = LoggerFactory.getLogger(TraInvoiceStatusResource.class);

    private static final String ENTITY_NAME = "traInvoiceStatus";

    private final TraInvoiceStatusRepository traInvoiceStatusRepository;

    private final TraInvoiceStatusMapper traInvoiceStatusMapper;

    public TraInvoiceStatusResource(TraInvoiceStatusRepository traInvoiceStatusRepository, TraInvoiceStatusMapper traInvoiceStatusMapper) {
        this.traInvoiceStatusRepository = traInvoiceStatusRepository;
        this.traInvoiceStatusMapper = traInvoiceStatusMapper;
    }

    /**
     * POST  /tra-invoice-statuses : Create a new traInvoiceStatus.
     *
     * @param traInvoiceStatusDTO the traInvoiceStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new traInvoiceStatusDTO, or with status 400 (Bad Request) if the traInvoiceStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tra-invoice-statuses")
    @Timed
    public ResponseEntity<TraInvoiceStatusDTO> createTraInvoiceStatus(@RequestBody TraInvoiceStatusDTO traInvoiceStatusDTO) throws URISyntaxException {
        log.debug("REST request to save TraInvoiceStatus : {}", traInvoiceStatusDTO);
        if (traInvoiceStatusDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new traInvoiceStatus cannot already have an ID")).body(null);
        }
        TraInvoiceStatus traInvoiceStatus = traInvoiceStatusMapper.traInvoiceStatusDTOToTraInvoiceStatus(traInvoiceStatusDTO);
        traInvoiceStatus = traInvoiceStatusRepository.save(traInvoiceStatus);
        TraInvoiceStatusDTO result = traInvoiceStatusMapper.traInvoiceStatusToTraInvoiceStatusDTO(traInvoiceStatus);
        return ResponseEntity.created(new URI("/api/tra-invoice-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tra-invoice-statuses : Updates an existing traInvoiceStatus.
     *
     * @param traInvoiceStatusDTO the traInvoiceStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated traInvoiceStatusDTO,
     * or with status 400 (Bad Request) if the traInvoiceStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the traInvoiceStatusDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tra-invoice-statuses")
    @Timed
    public ResponseEntity<TraInvoiceStatusDTO> updateTraInvoiceStatus(@RequestBody TraInvoiceStatusDTO traInvoiceStatusDTO) throws URISyntaxException {
        log.debug("REST request to update TraInvoiceStatus : {}", traInvoiceStatusDTO);
        if (traInvoiceStatusDTO.getId() == null) {
            return createTraInvoiceStatus(traInvoiceStatusDTO);
        }
        TraInvoiceStatus traInvoiceStatus = traInvoiceStatusMapper.traInvoiceStatusDTOToTraInvoiceStatus(traInvoiceStatusDTO);
        traInvoiceStatus = traInvoiceStatusRepository.save(traInvoiceStatus);
        TraInvoiceStatusDTO result = traInvoiceStatusMapper.traInvoiceStatusToTraInvoiceStatusDTO(traInvoiceStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, traInvoiceStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tra-invoice-statuses : get all the traInvoiceStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of traInvoiceStatuses in body
     */
    @GetMapping("/tra-invoice-statuses")
    @Timed
    public List<TraInvoiceStatusDTO> getAllTraInvoiceStatuses() {
        log.debug("REST request to get all TraInvoiceStatuses");
        List<TraInvoiceStatus> traInvoiceStatuses = traInvoiceStatusRepository.findAll();
        return traInvoiceStatusMapper.traInvoiceStatusesToTraInvoiceStatusDTOs(traInvoiceStatuses);
    }

    /**
     * GET  /tra-invoice-statuses/:id : get the "id" traInvoiceStatus.
     *
     * @param id the id of the traInvoiceStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the traInvoiceStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tra-invoice-statuses/{id}")
    @Timed
    public ResponseEntity<TraInvoiceStatusDTO> getTraInvoiceStatus(@PathVariable Long id) {
        log.debug("REST request to get TraInvoiceStatus : {}", id);
        TraInvoiceStatus traInvoiceStatus = traInvoiceStatusRepository.findOne(id);
        TraInvoiceStatusDTO traInvoiceStatusDTO = traInvoiceStatusMapper.traInvoiceStatusToTraInvoiceStatusDTO(traInvoiceStatus);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(traInvoiceStatusDTO));
    }

    /**
     * DELETE  /tra-invoice-statuses/:id : delete the "id" traInvoiceStatus.
     *
     * @param id the id of the traInvoiceStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tra-invoice-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteTraInvoiceStatus(@PathVariable Long id) {
        log.debug("REST request to delete TraInvoiceStatus : {}", id);
        traInvoiceStatusRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
