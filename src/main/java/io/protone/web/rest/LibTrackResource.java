package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LibTrack;

import io.protone.repository.LibTrackRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.LibTrackDTO;
import io.protone.service.mapper.LibTrackMapper;
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
 * REST controller for managing LibTrack.
 */
@RestController
@RequestMapping("/api")
public class LibTrackResource {

    private final Logger log = LoggerFactory.getLogger(LibTrackResource.class);

    private static final String ENTITY_NAME = "libTrack";

    private final LibTrackRepository libTrackRepository;

    private final LibTrackMapper libTrackMapper;

    public LibTrackResource(LibTrackRepository libTrackRepository, LibTrackMapper libTrackMapper) {
        this.libTrackRepository = libTrackRepository;
        this.libTrackMapper = libTrackMapper;
    }

    /**
     * POST  /lib-tracks : Create a new libTrack.
     *
     * @param libTrackDTO the libTrackDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new libTrackDTO, or with status 400 (Bad Request) if the libTrack has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lib-tracks")
    @Timed
    public ResponseEntity<LibTrackDTO> createLibTrack(@Valid @RequestBody LibTrackDTO libTrackDTO) throws URISyntaxException {
        log.debug("REST request to save LibTrack : {}", libTrackDTO);
        if (libTrackDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new libTrack cannot already have an ID")).body(null);
        }
        LibTrack libTrack = libTrackMapper.libTrackDTOToLibTrack(libTrackDTO);
        libTrack = libTrackRepository.save(libTrack);
        LibTrackDTO result = libTrackMapper.libTrackToLibTrackDTO(libTrack);
        return ResponseEntity.created(new URI("/api/lib-tracks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lib-tracks : Updates an existing libTrack.
     *
     * @param libTrackDTO the libTrackDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated libTrackDTO,
     * or with status 400 (Bad Request) if the libTrackDTO is not valid,
     * or with status 500 (Internal Server Error) if the libTrackDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lib-tracks")
    @Timed
    public ResponseEntity<LibTrackDTO> updateLibTrack(@Valid @RequestBody LibTrackDTO libTrackDTO) throws URISyntaxException {
        log.debug("REST request to update LibTrack : {}", libTrackDTO);
        if (libTrackDTO.getId() == null) {
            return createLibTrack(libTrackDTO);
        }
        LibTrack libTrack = libTrackMapper.libTrackDTOToLibTrack(libTrackDTO);
        libTrack = libTrackRepository.save(libTrack);
        LibTrackDTO result = libTrackMapper.libTrackToLibTrackDTO(libTrack);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, libTrackDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lib-tracks : get all the libTracks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of libTracks in body
     */
    @GetMapping("/lib-tracks")
    @Timed
    public List<LibTrackDTO> getAllLibTracks() {
        log.debug("REST request to get all LibTracks");
        List<LibTrack> libTracks = libTrackRepository.findAll();
        return libTrackMapper.libTracksToLibTrackDTOs(libTracks);
    }

    /**
     * GET  /lib-tracks/:id : get the "id" libTrack.
     *
     * @param id the id of the libTrackDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the libTrackDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lib-tracks/{id}")
    @Timed
    public ResponseEntity<LibTrackDTO> getLibTrack(@PathVariable Long id) {
        log.debug("REST request to get LibTrack : {}", id);
        LibTrack libTrack = libTrackRepository.findOne(id);
        LibTrackDTO libTrackDTO = libTrackMapper.libTrackToLibTrackDTO(libTrack);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(libTrackDTO));
    }

    /**
     * DELETE  /lib-tracks/:id : delete the "id" libTrack.
     *
     * @param id the id of the libTrackDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lib-tracks/{id}")
    @Timed
    public ResponseEntity<Void> deleteLibTrack(@PathVariable Long id) {
        log.debug("REST request to delete LibTrack : {}", id);
        libTrackRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
