package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.TraIndustry;

import io.protone.repository.TraIndustryRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.TraIndustryDTO;
import io.protone.service.mapper.TraIndustryMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TraIndustry.
 */
@RestController
@RequestMapping("/api")
public class TraIndustryResource {

    private final Logger log = LoggerFactory.getLogger(TraIndustryResource.class);

    private static final String ENTITY_NAME = "traIndustry";

    private final TraIndustryRepository traIndustryRepository;

    private final TraIndustryMapper traIndustryMapper;

    public TraIndustryResource(TraIndustryRepository traIndustryRepository, TraIndustryMapper traIndustryMapper) {
        this.traIndustryRepository = traIndustryRepository;
        this.traIndustryMapper = traIndustryMapper;
    }

    /**
     * POST  /tra-industries : Create a new traIndustry.
     *
     * @param traIndustryDTO the traIndustryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new traIndustryDTO, or with status 400 (Bad Request) if the traIndustry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tra-industries")
    @Timed
    public ResponseEntity<TraIndustryDTO> createTraIndustry(@Valid @RequestBody TraIndustryDTO traIndustryDTO) throws URISyntaxException {
        log.debug("REST request to save TraIndustry : {}", traIndustryDTO);
        if (traIndustryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new traIndustry cannot already have an ID")).body(null);
        }
        TraIndustry traIndustry = traIndustryMapper.traIndustryDTOToTraIndustry(traIndustryDTO);
        traIndustry = traIndustryRepository.save(traIndustry);
        TraIndustryDTO result = traIndustryMapper.traIndustryToTraIndustryDTO(traIndustry);
        return ResponseEntity.created(new URI("/api/tra-industries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tra-industries : Updates an existing traIndustry.
     *
     * @param traIndustryDTO the traIndustryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated traIndustryDTO,
     * or with status 400 (Bad Request) if the traIndustryDTO is not valid,
     * or with status 500 (Internal Server Error) if the traIndustryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tra-industries")
    @Timed
    public ResponseEntity<TraIndustryDTO> updateTraIndustry(@Valid @RequestBody TraIndustryDTO traIndustryDTO) throws URISyntaxException {
        log.debug("REST request to update TraIndustry : {}", traIndustryDTO);
        if (traIndustryDTO.getId() == null) {
            return createTraIndustry(traIndustryDTO);
        }
        TraIndustry traIndustry = traIndustryMapper.traIndustryDTOToTraIndustry(traIndustryDTO);
        traIndustry = traIndustryRepository.save(traIndustry);
        TraIndustryDTO result = traIndustryMapper.traIndustryToTraIndustryDTO(traIndustry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, traIndustryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tra-industries : get all the traIndustries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of traIndustries in body
     */
    @GetMapping("/tra-industries")
    @Timed
    public List<TraIndustryDTO> getAllTraIndustries() {
        log.debug("REST request to get all TraIndustries");
        List<TraIndustry> traIndustries = traIndustryRepository.findAll();
        return traIndustryMapper.traIndustriesToTraIndustryDTOs(traIndustries);
    }

    /**
     * GET  /tra-industries/:id : get the "id" traIndustry.
     *
     * @param id the id of the traIndustryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the traIndustryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tra-industries/{id}")
    @Timed
    public ResponseEntity<TraIndustryDTO> getTraIndustry(@PathVariable Long id) {
        log.debug("REST request to get TraIndustry : {}", id);
        TraIndustry traIndustry = traIndustryRepository.findOne(id);
        TraIndustryDTO traIndustryDTO = traIndustryMapper.traIndustryToTraIndustryDTO(traIndustry);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(traIndustryDTO));
    }

    /**
     * DELETE  /tra-industries/:id : delete the "id" traIndustry.
     *
     * @param id the id of the traIndustryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tra-industries/{id}")
    @Timed
    public ResponseEntity<Void> deleteTraIndustry(@PathVariable Long id) {
        log.debug("REST request to delete TraIndustry : {}", id);
        traIndustryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
