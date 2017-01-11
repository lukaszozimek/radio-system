package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LIBTrack;

import io.protone.repository.LIBTrackRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.LIBTrackDTO;
import io.protone.service.mapper.LIBTrackMapper;

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
 * REST controller for managing LIBTrack.
 */
@RestController
@RequestMapping("/api")
public class LIBTrackResource {

    private final Logger log = LoggerFactory.getLogger(LIBTrackResource.class);
        
    @Inject
    private LIBTrackRepository lIBTrackRepository;

    @Inject
    private LIBTrackMapper lIBTrackMapper;

    /**
     * POST  /l-ib-tracks : Create a new lIBTrack.
     *
     * @param lIBTrackDTO the lIBTrackDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lIBTrackDTO, or with status 400 (Bad Request) if the lIBTrack has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/l-ib-tracks")
    @Timed
    public ResponseEntity<LIBTrackDTO> createLIBTrack(@Valid @RequestBody LIBTrackDTO lIBTrackDTO) throws URISyntaxException {
        log.debug("REST request to save LIBTrack : {}", lIBTrackDTO);
        if (lIBTrackDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lIBTrack", "idexists", "A new lIBTrack cannot already have an ID")).body(null);
        }
        LIBTrack lIBTrack = lIBTrackMapper.lIBTrackDTOToLIBTrack(lIBTrackDTO);
        lIBTrack = lIBTrackRepository.save(lIBTrack);
        LIBTrackDTO result = lIBTrackMapper.lIBTrackToLIBTrackDTO(lIBTrack);
        return ResponseEntity.created(new URI("/api/l-ib-tracks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lIBTrack", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /l-ib-tracks : Updates an existing lIBTrack.
     *
     * @param lIBTrackDTO the lIBTrackDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lIBTrackDTO,
     * or with status 400 (Bad Request) if the lIBTrackDTO is not valid,
     * or with status 500 (Internal Server Error) if the lIBTrackDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/l-ib-tracks")
    @Timed
    public ResponseEntity<LIBTrackDTO> updateLIBTrack(@Valid @RequestBody LIBTrackDTO lIBTrackDTO) throws URISyntaxException {
        log.debug("REST request to update LIBTrack : {}", lIBTrackDTO);
        if (lIBTrackDTO.getId() == null) {
            return createLIBTrack(lIBTrackDTO);
        }
        LIBTrack lIBTrack = lIBTrackMapper.lIBTrackDTOToLIBTrack(lIBTrackDTO);
        lIBTrack = lIBTrackRepository.save(lIBTrack);
        LIBTrackDTO result = lIBTrackMapper.lIBTrackToLIBTrackDTO(lIBTrack);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lIBTrack", lIBTrackDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /l-ib-tracks : get all the lIBTracks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lIBTracks in body
     */
    @GetMapping("/l-ib-tracks")
    @Timed
    public List<LIBTrackDTO> getAllLIBTracks() {
        log.debug("REST request to get all LIBTracks");
        List<LIBTrack> lIBTracks = lIBTrackRepository.findAll();
        return lIBTrackMapper.lIBTracksToLIBTrackDTOs(lIBTracks);
    }

    /**
     * GET  /l-ib-tracks/:id : get the "id" lIBTrack.
     *
     * @param id the id of the lIBTrackDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lIBTrackDTO, or with status 404 (Not Found)
     */
    @GetMapping("/l-ib-tracks/{id}")
    @Timed
    public ResponseEntity<LIBTrackDTO> getLIBTrack(@PathVariable Long id) {
        log.debug("REST request to get LIBTrack : {}", id);
        LIBTrack lIBTrack = lIBTrackRepository.findOne(id);
        LIBTrackDTO lIBTrackDTO = lIBTrackMapper.lIBTrackToLIBTrackDTO(lIBTrack);
        return Optional.ofNullable(lIBTrackDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /l-ib-tracks/:id : delete the "id" lIBTrack.
     *
     * @param id the id of the lIBTrackDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/l-ib-tracks/{id}")
    @Timed
    public ResponseEntity<Void> deleteLIBTrack(@PathVariable Long id) {
        log.debug("REST request to delete LIBTrack : {}", id);
        lIBTrackRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lIBTrack", id.toString())).build();
    }

}
