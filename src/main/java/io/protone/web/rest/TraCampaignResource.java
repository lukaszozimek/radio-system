package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.TraCampaign;

import io.protone.repository.TraCampaignRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.TraCampaignDTO;
import io.protone.service.mapper.TraCampaignMapper;
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
 * REST controller for managing TraCampaign.
 */
@RestController
@RequestMapping("/api")
public class TraCampaignResource {

    private final Logger log = LoggerFactory.getLogger(TraCampaignResource.class);

    private static final String ENTITY_NAME = "traCampaign";

    private final TraCampaignRepository traCampaignRepository;

    private final TraCampaignMapper traCampaignMapper;

    public TraCampaignResource(TraCampaignRepository traCampaignRepository, TraCampaignMapper traCampaignMapper) {
        this.traCampaignRepository = traCampaignRepository;
        this.traCampaignMapper = traCampaignMapper;
    }

    /**
     * POST  /tra-campaigns : Create a new traCampaign.
     *
     * @param traCampaignDTO the traCampaignDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new traCampaignDTO, or with status 400 (Bad Request) if the traCampaign has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tra-campaigns")
    @Timed
    public ResponseEntity<TraCampaignDTO> createTraCampaign(@Valid @RequestBody TraCampaignDTO traCampaignDTO) throws URISyntaxException {
        log.debug("REST request to save TraCampaign : {}", traCampaignDTO);
        if (traCampaignDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new traCampaign cannot already have an ID")).body(null);
        }
        TraCampaign traCampaign = traCampaignMapper.traCampaignDTOToTraCampaign(traCampaignDTO);
        traCampaign = traCampaignRepository.save(traCampaign);
        TraCampaignDTO result = traCampaignMapper.traCampaignToTraCampaignDTO(traCampaign);
        return ResponseEntity.created(new URI("/api/tra-campaigns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tra-campaigns : Updates an existing traCampaign.
     *
     * @param traCampaignDTO the traCampaignDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated traCampaignDTO,
     * or with status 400 (Bad Request) if the traCampaignDTO is not valid,
     * or with status 500 (Internal Server Error) if the traCampaignDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tra-campaigns")
    @Timed
    public ResponseEntity<TraCampaignDTO> updateTraCampaign(@Valid @RequestBody TraCampaignDTO traCampaignDTO) throws URISyntaxException {
        log.debug("REST request to update TraCampaign : {}", traCampaignDTO);
        if (traCampaignDTO.getId() == null) {
            return createTraCampaign(traCampaignDTO);
        }
        TraCampaign traCampaign = traCampaignMapper.traCampaignDTOToTraCampaign(traCampaignDTO);
        traCampaign = traCampaignRepository.save(traCampaign);
        TraCampaignDTO result = traCampaignMapper.traCampaignToTraCampaignDTO(traCampaign);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, traCampaignDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tra-campaigns : get all the traCampaigns.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of traCampaigns in body
     */
    @GetMapping("/tra-campaigns")
    @Timed
    public List<TraCampaignDTO> getAllTraCampaigns() {
        log.debug("REST request to get all TraCampaigns");
        List<TraCampaign> traCampaigns = traCampaignRepository.findAll();
        return traCampaignMapper.traCampaignsToTraCampaignDTOs(traCampaigns);
    }

    /**
     * GET  /tra-campaigns/:id : get the "id" traCampaign.
     *
     * @param id the id of the traCampaignDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the traCampaignDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tra-campaigns/{id}")
    @Timed
    public ResponseEntity<TraCampaignDTO> getTraCampaign(@PathVariable Long id) {
        log.debug("REST request to get TraCampaign : {}", id);
        TraCampaign traCampaign = traCampaignRepository.findOne(id);
        TraCampaignDTO traCampaignDTO = traCampaignMapper.traCampaignToTraCampaignDTO(traCampaign);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(traCampaignDTO));
    }

    /**
     * DELETE  /tra-campaigns/:id : delete the "id" traCampaign.
     *
     * @param id the id of the traCampaignDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tra-campaigns/{id}")
    @Timed
    public ResponseEntity<Void> deleteTraCampaign(@PathVariable Long id) {
        log.debug("REST request to delete TraCampaign : {}", id);
        traCampaignRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
