package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.TRACampaign;

import io.protone.repository.TRACampaignRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.TRACampaignDTO;
import io.protone.service.mapper.TRACampaignMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing TRACampaign.
 */
@RestController
@RequestMapping("/api")
public class TRACampaignResource {

    private final Logger log = LoggerFactory.getLogger(TRACampaignResource.class);
        
    @Inject
    private TRACampaignRepository tRACampaignRepository;

    @Inject
    private TRACampaignMapper tRACampaignMapper;

    /**
     * POST  /t-ra-campaigns : Create a new tRACampaign.
     *
     * @param tRACampaignDTO the tRACampaignDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tRACampaignDTO, or with status 400 (Bad Request) if the tRACampaign has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-ra-campaigns")
    @Timed
    public ResponseEntity<TRACampaignDTO> createTRACampaign(@Valid @RequestBody TRACampaignDTO tRACampaignDTO) throws URISyntaxException {
        log.debug("REST request to save TRACampaign : {}", tRACampaignDTO);
        if (tRACampaignDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tRACampaign", "idexists", "A new tRACampaign cannot already have an ID")).body(null);
        }
        TRACampaign tRACampaign = tRACampaignMapper.tRACampaignDTOToTRACampaign(tRACampaignDTO);
        tRACampaign = tRACampaignRepository.save(tRACampaign);
        TRACampaignDTO result = tRACampaignMapper.tRACampaignToTRACampaignDTO(tRACampaign);
        return ResponseEntity.created(new URI("/api/t-ra-campaigns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tRACampaign", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-ra-campaigns : Updates an existing tRACampaign.
     *
     * @param tRACampaignDTO the tRACampaignDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tRACampaignDTO,
     * or with status 400 (Bad Request) if the tRACampaignDTO is not valid,
     * or with status 500 (Internal Server Error) if the tRACampaignDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-ra-campaigns")
    @Timed
    public ResponseEntity<TRACampaignDTO> updateTRACampaign(@Valid @RequestBody TRACampaignDTO tRACampaignDTO) throws URISyntaxException {
        log.debug("REST request to update TRACampaign : {}", tRACampaignDTO);
        if (tRACampaignDTO.getId() == null) {
            return createTRACampaign(tRACampaignDTO);
        }
        TRACampaign tRACampaign = tRACampaignMapper.tRACampaignDTOToTRACampaign(tRACampaignDTO);
        tRACampaign = tRACampaignRepository.save(tRACampaign);
        TRACampaignDTO result = tRACampaignMapper.tRACampaignToTRACampaignDTO(tRACampaign);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tRACampaign", tRACampaignDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-ra-campaigns : get all the tRACampaigns.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tRACampaigns in body
     */
    @GetMapping("/t-ra-campaigns")
    @Timed
    public List<TRACampaignDTO> getAllTRACampaigns() {
        log.debug("REST request to get all TRACampaigns");
        List<TRACampaign> tRACampaigns = tRACampaignRepository.findAll();
        return tRACampaignMapper.tRACampaignsToTRACampaignDTOs(tRACampaigns);
    }

    /**
     * GET  /t-ra-campaigns/:id : get the "id" tRACampaign.
     *
     * @param id the id of the tRACampaignDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tRACampaignDTO, or with status 404 (Not Found)
     */
    @GetMapping("/t-ra-campaigns/{id}")
    @Timed
    public ResponseEntity<TRACampaignDTO> getTRACampaign(@PathVariable Long id) {
        log.debug("REST request to get TRACampaign : {}", id);
        TRACampaign tRACampaign = tRACampaignRepository.findOne(id);
        TRACampaignDTO tRACampaignDTO = tRACampaignMapper.tRACampaignToTRACampaignDTO(tRACampaign);
        return Optional.ofNullable(tRACampaignDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /t-ra-campaigns/:id : delete the "id" tRACampaign.
     *
     * @param id the id of the tRACampaignDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-ra-campaigns/{id}")
    @Timed
    public ResponseEntity<Void> deleteTRACampaign(@PathVariable Long id) {
        log.debug("REST request to delete TRACampaign : {}", id);
        tRACampaignRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tRACampaign", id.toString())).build();
    }

}
