package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.TRAAdvertisement;

import io.protone.repository.TRAAdvertisementRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.TRAAdvertisementDTO;
import io.protone.service.mapper.TRAAdvertisementMapper;

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
 * REST controller for managing TRAAdvertisement.
 */
@RestController
@RequestMapping("/api")
public class TRAAdvertisementResource {

    private final Logger log = LoggerFactory.getLogger(TRAAdvertisementResource.class);
        
    @Inject
    private TRAAdvertisementRepository tRAAdvertisementRepository;

    @Inject
    private TRAAdvertisementMapper tRAAdvertisementMapper;

    /**
     * POST  /t-ra-advertisements : Create a new tRAAdvertisement.
     *
     * @param tRAAdvertisementDTO the tRAAdvertisementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tRAAdvertisementDTO, or with status 400 (Bad Request) if the tRAAdvertisement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-ra-advertisements")
    @Timed
    public ResponseEntity<TRAAdvertisementDTO> createTRAAdvertisement(@Valid @RequestBody TRAAdvertisementDTO tRAAdvertisementDTO) throws URISyntaxException {
        log.debug("REST request to save TRAAdvertisement : {}", tRAAdvertisementDTO);
        if (tRAAdvertisementDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tRAAdvertisement", "idexists", "A new tRAAdvertisement cannot already have an ID")).body(null);
        }
        TRAAdvertisement tRAAdvertisement = tRAAdvertisementMapper.tRAAdvertisementDTOToTRAAdvertisement(tRAAdvertisementDTO);
        tRAAdvertisement = tRAAdvertisementRepository.save(tRAAdvertisement);
        TRAAdvertisementDTO result = tRAAdvertisementMapper.tRAAdvertisementToTRAAdvertisementDTO(tRAAdvertisement);
        return ResponseEntity.created(new URI("/api/t-ra-advertisements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tRAAdvertisement", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-ra-advertisements : Updates an existing tRAAdvertisement.
     *
     * @param tRAAdvertisementDTO the tRAAdvertisementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tRAAdvertisementDTO,
     * or with status 400 (Bad Request) if the tRAAdvertisementDTO is not valid,
     * or with status 500 (Internal Server Error) if the tRAAdvertisementDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-ra-advertisements")
    @Timed
    public ResponseEntity<TRAAdvertisementDTO> updateTRAAdvertisement(@Valid @RequestBody TRAAdvertisementDTO tRAAdvertisementDTO) throws URISyntaxException {
        log.debug("REST request to update TRAAdvertisement : {}", tRAAdvertisementDTO);
        if (tRAAdvertisementDTO.getId() == null) {
            return createTRAAdvertisement(tRAAdvertisementDTO);
        }
        TRAAdvertisement tRAAdvertisement = tRAAdvertisementMapper.tRAAdvertisementDTOToTRAAdvertisement(tRAAdvertisementDTO);
        tRAAdvertisement = tRAAdvertisementRepository.save(tRAAdvertisement);
        TRAAdvertisementDTO result = tRAAdvertisementMapper.tRAAdvertisementToTRAAdvertisementDTO(tRAAdvertisement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tRAAdvertisement", tRAAdvertisementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-ra-advertisements : get all the tRAAdvertisements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tRAAdvertisements in body
     */
    @GetMapping("/t-ra-advertisements")
    @Timed
    public List<TRAAdvertisementDTO> getAllTRAAdvertisements() {
        log.debug("REST request to get all TRAAdvertisements");
        List<TRAAdvertisement> tRAAdvertisements = tRAAdvertisementRepository.findAll();
        return tRAAdvertisementMapper.tRAAdvertisementsToTRAAdvertisementDTOs(tRAAdvertisements);
    }

    /**
     * GET  /t-ra-advertisements/:id : get the "id" tRAAdvertisement.
     *
     * @param id the id of the tRAAdvertisementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tRAAdvertisementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/t-ra-advertisements/{id}")
    @Timed
    public ResponseEntity<TRAAdvertisementDTO> getTRAAdvertisement(@PathVariable Long id) {
        log.debug("REST request to get TRAAdvertisement : {}", id);
        TRAAdvertisement tRAAdvertisement = tRAAdvertisementRepository.findOne(id);
        TRAAdvertisementDTO tRAAdvertisementDTO = tRAAdvertisementMapper.tRAAdvertisementToTRAAdvertisementDTO(tRAAdvertisement);
        return Optional.ofNullable(tRAAdvertisementDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /t-ra-advertisements/:id : delete the "id" tRAAdvertisement.
     *
     * @param id the id of the tRAAdvertisementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-ra-advertisements/{id}")
    @Timed
    public ResponseEntity<Void> deleteTRAAdvertisement(@PathVariable Long id) {
        log.debug("REST request to delete TRAAdvertisement : {}", id);
        tRAAdvertisementRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tRAAdvertisement", id.toString())).build();
    }

}
