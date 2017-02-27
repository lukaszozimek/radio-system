package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LibArtist;

import io.protone.repository.LibArtistRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.LibArtistDTO;
import io.protone.service.mapper.LibArtistMapper;
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
 * REST controller for managing LibArtist.
 */
@RestController
@RequestMapping("/api")
public class LibArtistResource {

    private final Logger log = LoggerFactory.getLogger(LibArtistResource.class);

    private static final String ENTITY_NAME = "libArtist";

    private final LibArtistRepository libArtistRepository;

    private final LibArtistMapper libArtistMapper;

    public LibArtistResource(LibArtistRepository libArtistRepository, LibArtistMapper libArtistMapper) {
        this.libArtistRepository = libArtistRepository;
        this.libArtistMapper = libArtistMapper;
    }

    /**
     * POST  /lib-artists : Create a new libArtist.
     *
     * @param libArtistDTO the libArtistDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new libArtistDTO, or with status 400 (Bad Request) if the libArtist has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lib-artists")
    @Timed
    public ResponseEntity<LibArtistDTO> createLibArtist(@Valid @RequestBody LibArtistDTO libArtistDTO) throws URISyntaxException {
        log.debug("REST request to save LibArtist : {}", libArtistDTO);
        if (libArtistDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new libArtist cannot already have an ID")).body(null);
        }
        LibArtist libArtist = libArtistMapper.libArtistDTOToLibArtist(libArtistDTO);
        libArtist = libArtistRepository.save(libArtist);
        LibArtistDTO result = libArtistMapper.libArtistToLibArtistDTO(libArtist);
        return ResponseEntity.created(new URI("/api/lib-artists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lib-artists : Updates an existing libArtist.
     *
     * @param libArtistDTO the libArtistDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated libArtistDTO,
     * or with status 400 (Bad Request) if the libArtistDTO is not valid,
     * or with status 500 (Internal Server Error) if the libArtistDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lib-artists")
    @Timed
    public ResponseEntity<LibArtistDTO> updateLibArtist(@Valid @RequestBody LibArtistDTO libArtistDTO) throws URISyntaxException {
        log.debug("REST request to update LibArtist : {}", libArtistDTO);
        if (libArtistDTO.getId() == null) {
            return createLibArtist(libArtistDTO);
        }
        LibArtist libArtist = libArtistMapper.libArtistDTOToLibArtist(libArtistDTO);
        libArtist = libArtistRepository.save(libArtist);
        LibArtistDTO result = libArtistMapper.libArtistToLibArtistDTO(libArtist);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, libArtistDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lib-artists : get all the libArtists.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of libArtists in body
     */
    @GetMapping("/lib-artists")
    @Timed
    public List<LibArtistDTO> getAllLibArtists() {
        log.debug("REST request to get all LibArtists");
        List<LibArtist> libArtists = libArtistRepository.findAll();
        return libArtistMapper.libArtistsToLibArtistDTOs(libArtists);
    }

    /**
     * GET  /lib-artists/:id : get the "id" libArtist.
     *
     * @param id the id of the libArtistDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the libArtistDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lib-artists/{id}")
    @Timed
    public ResponseEntity<LibArtistDTO> getLibArtist(@PathVariable Long id) {
        log.debug("REST request to get LibArtist : {}", id);
        LibArtist libArtist = libArtistRepository.findOne(id);
        LibArtistDTO libArtistDTO = libArtistMapper.libArtistToLibArtistDTO(libArtist);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(libArtistDTO));
    }

    /**
     * DELETE  /lib-artists/:id : delete the "id" libArtist.
     *
     * @param id the id of the libArtistDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lib-artists/{id}")
    @Timed
    public ResponseEntity<Void> deleteLibArtist(@PathVariable Long id) {
        log.debug("REST request to delete LibArtist : {}", id);
        libArtistRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
