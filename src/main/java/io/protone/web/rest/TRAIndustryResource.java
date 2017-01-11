package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.TRAIndustry;

import io.protone.repository.TRAIndustryRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.TRAIndustryDTO;
import io.protone.service.mapper.TRAIndustryMapper;

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
 * REST controller for managing TRAIndustry.
 */
@RestController
@RequestMapping("/api")
public class TRAIndustryResource {

    private final Logger log = LoggerFactory.getLogger(TRAIndustryResource.class);
        
    @Inject
    private TRAIndustryRepository tRAIndustryRepository;

    @Inject
    private TRAIndustryMapper tRAIndustryMapper;

    /**
     * POST  /t-ra-industries : Create a new tRAIndustry.
     *
     * @param tRAIndustryDTO the tRAIndustryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tRAIndustryDTO, or with status 400 (Bad Request) if the tRAIndustry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-ra-industries")
    @Timed
    public ResponseEntity<TRAIndustryDTO> createTRAIndustry(@Valid @RequestBody TRAIndustryDTO tRAIndustryDTO) throws URISyntaxException {
        log.debug("REST request to save TRAIndustry : {}", tRAIndustryDTO);
        if (tRAIndustryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tRAIndustry", "idexists", "A new tRAIndustry cannot already have an ID")).body(null);
        }
        TRAIndustry tRAIndustry = tRAIndustryMapper.tRAIndustryDTOToTRAIndustry(tRAIndustryDTO);
        tRAIndustry = tRAIndustryRepository.save(tRAIndustry);
        TRAIndustryDTO result = tRAIndustryMapper.tRAIndustryToTRAIndustryDTO(tRAIndustry);
        return ResponseEntity.created(new URI("/api/t-ra-industries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tRAIndustry", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-ra-industries : Updates an existing tRAIndustry.
     *
     * @param tRAIndustryDTO the tRAIndustryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tRAIndustryDTO,
     * or with status 400 (Bad Request) if the tRAIndustryDTO is not valid,
     * or with status 500 (Internal Server Error) if the tRAIndustryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-ra-industries")
    @Timed
    public ResponseEntity<TRAIndustryDTO> updateTRAIndustry(@Valid @RequestBody TRAIndustryDTO tRAIndustryDTO) throws URISyntaxException {
        log.debug("REST request to update TRAIndustry : {}", tRAIndustryDTO);
        if (tRAIndustryDTO.getId() == null) {
            return createTRAIndustry(tRAIndustryDTO);
        }
        TRAIndustry tRAIndustry = tRAIndustryMapper.tRAIndustryDTOToTRAIndustry(tRAIndustryDTO);
        tRAIndustry = tRAIndustryRepository.save(tRAIndustry);
        TRAIndustryDTO result = tRAIndustryMapper.tRAIndustryToTRAIndustryDTO(tRAIndustry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tRAIndustry", tRAIndustryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-ra-industries : get all the tRAIndustries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tRAIndustries in body
     */
    @GetMapping("/t-ra-industries")
    @Timed
    public List<TRAIndustryDTO> getAllTRAIndustries() {
        log.debug("REST request to get all TRAIndustries");
        List<TRAIndustry> tRAIndustries = tRAIndustryRepository.findAll();
        return tRAIndustryMapper.tRAIndustriesToTRAIndustryDTOs(tRAIndustries);
    }

    /**
     * GET  /t-ra-industries/:id : get the "id" tRAIndustry.
     *
     * @param id the id of the tRAIndustryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tRAIndustryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/t-ra-industries/{id}")
    @Timed
    public ResponseEntity<TRAIndustryDTO> getTRAIndustry(@PathVariable Long id) {
        log.debug("REST request to get TRAIndustry : {}", id);
        TRAIndustry tRAIndustry = tRAIndustryRepository.findOne(id);
        TRAIndustryDTO tRAIndustryDTO = tRAIndustryMapper.tRAIndustryToTRAIndustryDTO(tRAIndustry);
        return Optional.ofNullable(tRAIndustryDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /t-ra-industries/:id : delete the "id" tRAIndustry.
     *
     * @param id the id of the tRAIndustryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-ra-industries/{id}")
    @Timed
    public ResponseEntity<Void> deleteTRAIndustry(@PathVariable Long id) {
        log.debug("REST request to delete TRAIndustry : {}", id);
        tRAIndustryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tRAIndustry", id.toString())).build();
    }

}
