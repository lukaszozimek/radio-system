package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LibAlbum;

import io.protone.repository.LibAlbumRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.LibAlbumDTO;
import io.protone.service.mapper.LibAlbumMapper;
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
 * REST controller for managing LibAlbum.
 */
@RestController
@RequestMapping("/api")
public class LibAlbumResource {

    private final Logger log = LoggerFactory.getLogger(LibAlbumResource.class);

    private static final String ENTITY_NAME = "libAlbum";

    private final LibAlbumRepository libAlbumRepository;

    private final LibAlbumMapper libAlbumMapper;

    public LibAlbumResource(LibAlbumRepository libAlbumRepository, LibAlbumMapper libAlbumMapper) {
        this.libAlbumRepository = libAlbumRepository;
        this.libAlbumMapper = libAlbumMapper;
    }

    /**
     * POST  /lib-albums : Create a new libAlbum.
     *
     * @param libAlbumDTO the libAlbumDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new libAlbumDTO, or with status 400 (Bad Request) if the libAlbum has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lib-albums")
    @Timed
    public ResponseEntity<LibAlbumDTO> createLibAlbum(@Valid @RequestBody LibAlbumDTO libAlbumDTO) throws URISyntaxException {
        log.debug("REST request to save LibAlbum : {}", libAlbumDTO);
        if (libAlbumDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new libAlbum cannot already have an ID")).body(null);
        }
        LibAlbum libAlbum = libAlbumMapper.libAlbumDTOToLibAlbum(libAlbumDTO);
        libAlbum = libAlbumRepository.save(libAlbum);
        LibAlbumDTO result = libAlbumMapper.libAlbumToLibAlbumDTO(libAlbum);
        return ResponseEntity.created(new URI("/api/lib-albums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lib-albums : Updates an existing libAlbum.
     *
     * @param libAlbumDTO the libAlbumDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated libAlbumDTO,
     * or with status 400 (Bad Request) if the libAlbumDTO is not valid,
     * or with status 500 (Internal Server Error) if the libAlbumDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lib-albums")
    @Timed
    public ResponseEntity<LibAlbumDTO> updateLibAlbum(@Valid @RequestBody LibAlbumDTO libAlbumDTO) throws URISyntaxException {
        log.debug("REST request to update LibAlbum : {}", libAlbumDTO);
        if (libAlbumDTO.getId() == null) {
            return createLibAlbum(libAlbumDTO);
        }
        LibAlbum libAlbum = libAlbumMapper.libAlbumDTOToLibAlbum(libAlbumDTO);
        libAlbum = libAlbumRepository.save(libAlbum);
        LibAlbumDTO result = libAlbumMapper.libAlbumToLibAlbumDTO(libAlbum);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, libAlbumDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lib-albums : get all the libAlbums.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of libAlbums in body
     */
    @GetMapping("/lib-albums")
    @Timed
    public List<LibAlbumDTO> getAllLibAlbums() {
        log.debug("REST request to get all LibAlbums");
        List<LibAlbum> libAlbums = libAlbumRepository.findAll();
        return libAlbumMapper.libAlbumsToLibAlbumDTOs(libAlbums);
    }

    /**
     * GET  /lib-albums/:id : get the "id" libAlbum.
     *
     * @param id the id of the libAlbumDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the libAlbumDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lib-albums/{id}")
    @Timed
    public ResponseEntity<LibAlbumDTO> getLibAlbum(@PathVariable Long id) {
        log.debug("REST request to get LibAlbum : {}", id);
        LibAlbum libAlbum = libAlbumRepository.findOne(id);
        LibAlbumDTO libAlbumDTO = libAlbumMapper.libAlbumToLibAlbumDTO(libAlbum);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(libAlbumDTO));
    }

    /**
     * DELETE  /lib-albums/:id : delete the "id" libAlbum.
     *
     * @param id the id of the libAlbumDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lib-albums/{id}")
    @Timed
    public ResponseEntity<Void> deleteLibAlbum(@PathVariable Long id) {
        log.debug("REST request to delete LibAlbum : {}", id);
        libAlbumRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
