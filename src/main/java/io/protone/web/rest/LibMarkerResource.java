package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LibMarker;

import io.protone.repository.LibMarkerRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.LibMarkerDTO;
import io.protone.service.mapper.LibMarkerMapper;
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
 * REST controller for managing LibMarker.
 */
@RestController
@RequestMapping("/api")
public class LibMarkerResource {

    private final Logger log = LoggerFactory.getLogger(LibMarkerResource.class);

    private static final String ENTITY_NAME = "libMarker";

    private final LibMarkerRepository libMarkerRepository;

    private final LibMarkerMapper libMarkerMapper;

    public LibMarkerResource(LibMarkerRepository libMarkerRepository, LibMarkerMapper libMarkerMapper) {
        this.libMarkerRepository = libMarkerRepository;
        this.libMarkerMapper = libMarkerMapper;
    }

    /**
     * POST  /lib-markers : Create a new libMarker.
     *
     * @param libMarkerDTO the libMarkerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new libMarkerDTO, or with status 400 (Bad Request) if the libMarker has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lib-markers")
    @Timed
    public ResponseEntity<LibMarkerDTO> createLibMarker(@Valid @RequestBody LibMarkerDTO libMarkerDTO) throws URISyntaxException {
        log.debug("REST request to save LibMarker : {}", libMarkerDTO);
        if (libMarkerDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new libMarker cannot already have an ID")).body(null);
        }
        LibMarker libMarker = libMarkerMapper.libMarkerDTOToLibMarker(libMarkerDTO);
        libMarker = libMarkerRepository.save(libMarker);
        LibMarkerDTO result = libMarkerMapper.libMarkerToLibMarkerDTO(libMarker);
        return ResponseEntity.created(new URI("/api/lib-markers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lib-markers : Updates an existing libMarker.
     *
     * @param libMarkerDTO the libMarkerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated libMarkerDTO,
     * or with status 400 (Bad Request) if the libMarkerDTO is not valid,
     * or with status 500 (Internal Server Error) if the libMarkerDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lib-markers")
    @Timed
    public ResponseEntity<LibMarkerDTO> updateLibMarker(@Valid @RequestBody LibMarkerDTO libMarkerDTO) throws URISyntaxException {
        log.debug("REST request to update LibMarker : {}", libMarkerDTO);
        if (libMarkerDTO.getId() == null) {
            return createLibMarker(libMarkerDTO);
        }
        LibMarker libMarker = libMarkerMapper.libMarkerDTOToLibMarker(libMarkerDTO);
        libMarker = libMarkerRepository.save(libMarker);
        LibMarkerDTO result = libMarkerMapper.libMarkerToLibMarkerDTO(libMarker);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, libMarkerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lib-markers : get all the libMarkers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of libMarkers in body
     */
    @GetMapping("/lib-markers")
    @Timed
    public List<LibMarkerDTO> getAllLibMarkers() {
        log.debug("REST request to get all LibMarkers");
        List<LibMarker> libMarkers = libMarkerRepository.findAll();
        return libMarkerMapper.libMarkersToLibMarkerDTOs(libMarkers);
    }

    /**
     * GET  /lib-markers/:id : get the "id" libMarker.
     *
     * @param id the id of the libMarkerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the libMarkerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lib-markers/{id}")
    @Timed
    public ResponseEntity<LibMarkerDTO> getLibMarker(@PathVariable Long id) {
        log.debug("REST request to get LibMarker : {}", id);
        LibMarker libMarker = libMarkerRepository.findOne(id);
        LibMarkerDTO libMarkerDTO = libMarkerMapper.libMarkerToLibMarkerDTO(libMarker);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(libMarkerDTO));
    }

    /**
     * DELETE  /lib-markers/:id : delete the "id" libMarker.
     *
     * @param id the id of the libMarkerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lib-markers/{id}")
    @Timed
    public ResponseEntity<Void> deleteLibMarker(@PathVariable Long id) {
        log.debug("REST request to delete LibMarker : {}", id);
        libMarkerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
