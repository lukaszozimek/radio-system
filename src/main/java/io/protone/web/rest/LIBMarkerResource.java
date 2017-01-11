package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LIBMarker;

import io.protone.repository.LIBMarkerRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.LIBMarkerDTO;
import io.protone.service.mapper.LIBMarkerMapper;

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
 * REST controller for managing LIBMarker.
 */
@RestController
@RequestMapping("/api")
public class LIBMarkerResource {

    private final Logger log = LoggerFactory.getLogger(LIBMarkerResource.class);
        
    @Inject
    private LIBMarkerRepository lIBMarkerRepository;

    @Inject
    private LIBMarkerMapper lIBMarkerMapper;

    /**
     * POST  /l-ib-markers : Create a new lIBMarker.
     *
     * @param lIBMarkerDTO the lIBMarkerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lIBMarkerDTO, or with status 400 (Bad Request) if the lIBMarker has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/l-ib-markers")
    @Timed
    public ResponseEntity<LIBMarkerDTO> createLIBMarker(@Valid @RequestBody LIBMarkerDTO lIBMarkerDTO) throws URISyntaxException {
        log.debug("REST request to save LIBMarker : {}", lIBMarkerDTO);
        if (lIBMarkerDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lIBMarker", "idexists", "A new lIBMarker cannot already have an ID")).body(null);
        }
        LIBMarker lIBMarker = lIBMarkerMapper.lIBMarkerDTOToLIBMarker(lIBMarkerDTO);
        lIBMarker = lIBMarkerRepository.save(lIBMarker);
        LIBMarkerDTO result = lIBMarkerMapper.lIBMarkerToLIBMarkerDTO(lIBMarker);
        return ResponseEntity.created(new URI("/api/l-ib-markers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lIBMarker", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /l-ib-markers : Updates an existing lIBMarker.
     *
     * @param lIBMarkerDTO the lIBMarkerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lIBMarkerDTO,
     * or with status 400 (Bad Request) if the lIBMarkerDTO is not valid,
     * or with status 500 (Internal Server Error) if the lIBMarkerDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/l-ib-markers")
    @Timed
    public ResponseEntity<LIBMarkerDTO> updateLIBMarker(@Valid @RequestBody LIBMarkerDTO lIBMarkerDTO) throws URISyntaxException {
        log.debug("REST request to update LIBMarker : {}", lIBMarkerDTO);
        if (lIBMarkerDTO.getId() == null) {
            return createLIBMarker(lIBMarkerDTO);
        }
        LIBMarker lIBMarker = lIBMarkerMapper.lIBMarkerDTOToLIBMarker(lIBMarkerDTO);
        lIBMarker = lIBMarkerRepository.save(lIBMarker);
        LIBMarkerDTO result = lIBMarkerMapper.lIBMarkerToLIBMarkerDTO(lIBMarker);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lIBMarker", lIBMarkerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /l-ib-markers : get all the lIBMarkers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lIBMarkers in body
     */
    @GetMapping("/l-ib-markers")
    @Timed
    public List<LIBMarkerDTO> getAllLIBMarkers() {
        log.debug("REST request to get all LIBMarkers");
        List<LIBMarker> lIBMarkers = lIBMarkerRepository.findAll();
        return lIBMarkerMapper.lIBMarkersToLIBMarkerDTOs(lIBMarkers);
    }

    /**
     * GET  /l-ib-markers/:id : get the "id" lIBMarker.
     *
     * @param id the id of the lIBMarkerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lIBMarkerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/l-ib-markers/{id}")
    @Timed
    public ResponseEntity<LIBMarkerDTO> getLIBMarker(@PathVariable Long id) {
        log.debug("REST request to get LIBMarker : {}", id);
        LIBMarker lIBMarker = lIBMarkerRepository.findOne(id);
        LIBMarkerDTO lIBMarkerDTO = lIBMarkerMapper.lIBMarkerToLIBMarkerDTO(lIBMarker);
        return Optional.ofNullable(lIBMarkerDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /l-ib-markers/:id : delete the "id" lIBMarker.
     *
     * @param id the id of the lIBMarkerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/l-ib-markers/{id}")
    @Timed
    public ResponseEntity<Void> deleteLIBMarker(@PathVariable Long id) {
        log.debug("REST request to delete LIBMarker : {}", id);
        lIBMarkerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lIBMarker", id.toString())).build();
    }

}
