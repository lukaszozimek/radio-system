package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.TraCampaingStatus;

import io.protone.repository.TraCampaingStatusRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.TraCampaingStatusDTO;
import io.protone.service.mapper.TraCampaingStatusMapper;
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
 * REST controller for managing TraCampaingStatus.
 */
@RestController
@RequestMapping("/api")
public class TraCampaingStatusResource {

    private final Logger log = LoggerFactory.getLogger(TraCampaingStatusResource.class);

    private static final String ENTITY_NAME = "traCampaingStatus";
        
    private final TraCampaingStatusRepository traCampaingStatusRepository;

    private final TraCampaingStatusMapper traCampaingStatusMapper;

    public TraCampaingStatusResource(TraCampaingStatusRepository traCampaingStatusRepository, TraCampaingStatusMapper traCampaingStatusMapper) {
        this.traCampaingStatusRepository = traCampaingStatusRepository;
        this.traCampaingStatusMapper = traCampaingStatusMapper;
    }

    /**
     * POST  /tra-campaing-statuses : Create a new traCampaingStatus.
     *
     * @param traCampaingStatusDTO the traCampaingStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new traCampaingStatusDTO, or with status 400 (Bad Request) if the traCampaingStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tra-campaing-statuses")
    @Timed
    public ResponseEntity<TraCampaingStatusDTO> createTraCampaingStatus(@RequestBody TraCampaingStatusDTO traCampaingStatusDTO) throws URISyntaxException {
        log.debug("REST request to save TraCampaingStatus : {}", traCampaingStatusDTO);
        if (traCampaingStatusDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new traCampaingStatus cannot already have an ID")).body(null);
        }
        TraCampaingStatus traCampaingStatus = traCampaingStatusMapper.traCampaingStatusDTOToTraCampaingStatus(traCampaingStatusDTO);
        traCampaingStatus = traCampaingStatusRepository.save(traCampaingStatus);
        TraCampaingStatusDTO result = traCampaingStatusMapper.traCampaingStatusToTraCampaingStatusDTO(traCampaingStatus);
        return ResponseEntity.created(new URI("/api/tra-campaing-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tra-campaing-statuses : Updates an existing traCampaingStatus.
     *
     * @param traCampaingStatusDTO the traCampaingStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated traCampaingStatusDTO,
     * or with status 400 (Bad Request) if the traCampaingStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the traCampaingStatusDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tra-campaing-statuses")
    @Timed
    public ResponseEntity<TraCampaingStatusDTO> updateTraCampaingStatus(@RequestBody TraCampaingStatusDTO traCampaingStatusDTO) throws URISyntaxException {
        log.debug("REST request to update TraCampaingStatus : {}", traCampaingStatusDTO);
        if (traCampaingStatusDTO.getId() == null) {
            return createTraCampaingStatus(traCampaingStatusDTO);
        }
        TraCampaingStatus traCampaingStatus = traCampaingStatusMapper.traCampaingStatusDTOToTraCampaingStatus(traCampaingStatusDTO);
        traCampaingStatus = traCampaingStatusRepository.save(traCampaingStatus);
        TraCampaingStatusDTO result = traCampaingStatusMapper.traCampaingStatusToTraCampaingStatusDTO(traCampaingStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, traCampaingStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tra-campaing-statuses : get all the traCampaingStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of traCampaingStatuses in body
     */
    @GetMapping("/tra-campaing-statuses")
    @Timed
    public List<TraCampaingStatusDTO> getAllTraCampaingStatuses() {
        log.debug("REST request to get all TraCampaingStatuses");
        List<TraCampaingStatus> traCampaingStatuses = traCampaingStatusRepository.findAll();
        return traCampaingStatusMapper.traCampaingStatusesToTraCampaingStatusDTOs(traCampaingStatuses);
    }

    /**
     * GET  /tra-campaing-statuses/:id : get the "id" traCampaingStatus.
     *
     * @param id the id of the traCampaingStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the traCampaingStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tra-campaing-statuses/{id}")
    @Timed
    public ResponseEntity<TraCampaingStatusDTO> getTraCampaingStatus(@PathVariable Long id) {
        log.debug("REST request to get TraCampaingStatus : {}", id);
        TraCampaingStatus traCampaingStatus = traCampaingStatusRepository.findOne(id);
        TraCampaingStatusDTO traCampaingStatusDTO = traCampaingStatusMapper.traCampaingStatusToTraCampaingStatusDTO(traCampaingStatus);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(traCampaingStatusDTO));
    }

    /**
     * DELETE  /tra-campaing-statuses/:id : delete the "id" traCampaingStatus.
     *
     * @param id the id of the traCampaingStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tra-campaing-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteTraCampaingStatus(@PathVariable Long id) {
        log.debug("REST request to delete TraCampaingStatus : {}", id);
        traCampaingStatusRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
