package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.TraAdvertisement;

import io.protone.repository.TraAdvertisementRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.TraAdvertisementDTO;
import io.protone.service.mapper.TraAdvertisementMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing TraAdvertisement.
 */
@RestController
@RequestMapping("/api")
public class TraAdvertisementResource {

    private final Logger log = LoggerFactory.getLogger(TraAdvertisementResource.class);

    private static final String ENTITY_NAME = "traAdvertisement";
        
    private final TraAdvertisementRepository traAdvertisementRepository;

    private final TraAdvertisementMapper traAdvertisementMapper;

    public TraAdvertisementResource(TraAdvertisementRepository traAdvertisementRepository, TraAdvertisementMapper traAdvertisementMapper) {
        this.traAdvertisementRepository = traAdvertisementRepository;
        this.traAdvertisementMapper = traAdvertisementMapper;
    }

    /**
     * POST  /tra-advertisements : Create a new traAdvertisement.
     *
     * @param traAdvertisementDTO the traAdvertisementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new traAdvertisementDTO, or with status 400 (Bad Request) if the traAdvertisement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tra-advertisements")
    @Timed
    public ResponseEntity<TraAdvertisementDTO> createTraAdvertisement(@Valid @RequestBody TraAdvertisementDTO traAdvertisementDTO) throws URISyntaxException {
        log.debug("REST request to save TraAdvertisement : {}", traAdvertisementDTO);
        if (traAdvertisementDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new traAdvertisement cannot already have an ID")).body(null);
        }
        TraAdvertisement traAdvertisement = traAdvertisementMapper.traAdvertisementDTOToTraAdvertisement(traAdvertisementDTO);
        traAdvertisement = traAdvertisementRepository.save(traAdvertisement);
        TraAdvertisementDTO result = traAdvertisementMapper.traAdvertisementToTraAdvertisementDTO(traAdvertisement);
        return ResponseEntity.created(new URI("/api/tra-advertisements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tra-advertisements : Updates an existing traAdvertisement.
     *
     * @param traAdvertisementDTO the traAdvertisementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated traAdvertisementDTO,
     * or with status 400 (Bad Request) if the traAdvertisementDTO is not valid,
     * or with status 500 (Internal Server Error) if the traAdvertisementDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tra-advertisements")
    @Timed
    public ResponseEntity<TraAdvertisementDTO> updateTraAdvertisement(@Valid @RequestBody TraAdvertisementDTO traAdvertisementDTO) throws URISyntaxException {
        log.debug("REST request to update TraAdvertisement : {}", traAdvertisementDTO);
        if (traAdvertisementDTO.getId() == null) {
            return createTraAdvertisement(traAdvertisementDTO);
        }
        TraAdvertisement traAdvertisement = traAdvertisementMapper.traAdvertisementDTOToTraAdvertisement(traAdvertisementDTO);
        traAdvertisement = traAdvertisementRepository.save(traAdvertisement);
        TraAdvertisementDTO result = traAdvertisementMapper.traAdvertisementToTraAdvertisementDTO(traAdvertisement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, traAdvertisementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tra-advertisements : get all the traAdvertisements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of traAdvertisements in body
     */
    @GetMapping("/tra-advertisements")
    @Timed
    public List<TraAdvertisementDTO> getAllTraAdvertisements() {
        log.debug("REST request to get all TraAdvertisements");
        List<TraAdvertisement> traAdvertisements = traAdvertisementRepository.findAll();
        return traAdvertisementMapper.traAdvertisementsToTraAdvertisementDTOs(traAdvertisements);
    }

    /**
     * GET  /tra-advertisements/:id : get the "id" traAdvertisement.
     *
     * @param id the id of the traAdvertisementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the traAdvertisementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tra-advertisements/{id}")
    @Timed
    public ResponseEntity<TraAdvertisementDTO> getTraAdvertisement(@PathVariable Long id) {
        log.debug("REST request to get TraAdvertisement : {}", id);
        TraAdvertisement traAdvertisement = traAdvertisementRepository.findOne(id);
        TraAdvertisementDTO traAdvertisementDTO = traAdvertisementMapper.traAdvertisementToTraAdvertisementDTO(traAdvertisement);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(traAdvertisementDTO));
    }

    /**
     * DELETE  /tra-advertisements/:id : delete the "id" traAdvertisement.
     *
     * @param id the id of the traAdvertisementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tra-advertisements/{id}")
    @Timed
    public ResponseEntity<Void> deleteTraAdvertisement(@PathVariable Long id) {
        log.debug("REST request to delete TraAdvertisement : {}", id);
        traAdvertisementRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
