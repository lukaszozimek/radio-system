package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LibLibrary;

import io.protone.repository.LibLibraryRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.LibLibraryDTO;
import io.protone.service.mapper.LibLibraryMapper;
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
 * REST controller for managing LibLibrary.
 */
@RestController
@RequestMapping("/api")
public class LibLibraryResource {

    private final Logger log = LoggerFactory.getLogger(LibLibraryResource.class);

    private static final String ENTITY_NAME = "libLibrary";

    private final LibLibraryRepository libLibraryRepository;

    private final LibLibraryMapper libLibraryMapper;

    public LibLibraryResource(LibLibraryRepository libLibraryRepository, LibLibraryMapper libLibraryMapper) {
        this.libLibraryRepository = libLibraryRepository;
        this.libLibraryMapper = libLibraryMapper;
    }

    /**
     * POST  /lib-libraries : Create a new libLibrary.
     *
     * @param libLibraryDTO the libLibraryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new libLibraryDTO, or with status 400 (Bad Request) if the libLibrary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lib-libraries")
    @Timed
    public ResponseEntity<LibLibraryDTO> createLibLibrary(@Valid @RequestBody LibLibraryDTO libLibraryDTO) throws URISyntaxException {
        log.debug("REST request to save LibLibrary : {}", libLibraryDTO);
        if (libLibraryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new libLibrary cannot already have an ID")).body(null);
        }
        LibLibrary libLibrary = libLibraryMapper.libLibraryDTOToLibLibrary(libLibraryDTO);
        libLibrary = libLibraryRepository.save(libLibrary);
        LibLibraryDTO result = libLibraryMapper.libLibraryToLibLibraryDTO(libLibrary);
        return ResponseEntity.created(new URI("/api/lib-libraries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lib-libraries : Updates an existing libLibrary.
     *
     * @param libLibraryDTO the libLibraryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated libLibraryDTO,
     * or with status 400 (Bad Request) if the libLibraryDTO is not valid,
     * or with status 500 (Internal Server Error) if the libLibraryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lib-libraries")
    @Timed
    public ResponseEntity<LibLibraryDTO> updateLibLibrary(@Valid @RequestBody LibLibraryDTO libLibraryDTO) throws URISyntaxException {
        log.debug("REST request to update LibLibrary : {}", libLibraryDTO);
        if (libLibraryDTO.getId() == null) {
            return createLibLibrary(libLibraryDTO);
        }
        LibLibrary libLibrary = libLibraryMapper.libLibraryDTOToLibLibrary(libLibraryDTO);
        libLibrary = libLibraryRepository.save(libLibrary);
        LibLibraryDTO result = libLibraryMapper.libLibraryToLibLibraryDTO(libLibrary);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, libLibraryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lib-libraries : get all the libLibraries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of libLibraries in body
     */
    @GetMapping("/lib-libraries")
    @Timed
    public List<LibLibraryDTO> getAllLibLibraries() {
        log.debug("REST request to get all LibLibraries");
        List<LibLibrary> libLibraries = libLibraryRepository.findAll();
        return libLibraryMapper.libLibrariesToLibLibraryDTOs(libLibraries);
    }

    /**
     * GET  /lib-libraries/:id : get the "id" libLibrary.
     *
     * @param id the id of the libLibraryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the libLibraryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lib-libraries/{id}")
    @Timed
    public ResponseEntity<LibLibraryDTO> getLibLibrary(@PathVariable Long id) {
        log.debug("REST request to get LibLibrary : {}", id);
        LibLibrary libLibrary = libLibraryRepository.findOne(id);
        LibLibraryDTO libLibraryDTO = libLibraryMapper.libLibraryToLibLibraryDTO(libLibrary);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(libLibraryDTO));
    }

    /**
     * DELETE  /lib-libraries/:id : delete the "id" libLibrary.
     *
     * @param id the id of the libLibraryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lib-libraries/{id}")
    @Timed
    public ResponseEntity<Void> deleteLibLibrary(@PathVariable Long id) {
        log.debug("REST request to delete LibLibrary : {}", id);
        libLibraryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
