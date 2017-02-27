package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LibImageObject;

import io.protone.repository.LibImageObjectRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.LibImageObjectDTO;
import io.protone.service.mapper.LibImageObjectMapper;
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
 * REST controller for managing LibImageObject.
 */
@RestController
@RequestMapping("/api")
public class LibImageObjectResource {

    private final Logger log = LoggerFactory.getLogger(LibImageObjectResource.class);

    private static final String ENTITY_NAME = "libImageObject";

    private final LibImageObjectRepository libImageObjectRepository;

    private final LibImageObjectMapper libImageObjectMapper;

    public LibImageObjectResource(LibImageObjectRepository libImageObjectRepository, LibImageObjectMapper libImageObjectMapper) {
        this.libImageObjectRepository = libImageObjectRepository;
        this.libImageObjectMapper = libImageObjectMapper;
    }

    /**
     * POST  /lib-image-objects : Create a new libImageObject.
     *
     * @param libImageObjectDTO the libImageObjectDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new libImageObjectDTO, or with status 400 (Bad Request) if the libImageObject has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lib-image-objects")
    @Timed
    public ResponseEntity<LibImageObjectDTO> createLibImageObject(@Valid @RequestBody LibImageObjectDTO libImageObjectDTO) throws URISyntaxException {
        log.debug("REST request to save LibImageObject : {}", libImageObjectDTO);
        if (libImageObjectDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new libImageObject cannot already have an ID")).body(null);
        }
        LibImageObject libImageObject = libImageObjectMapper.libImageObjectDTOToLibImageObject(libImageObjectDTO);
        libImageObject = libImageObjectRepository.save(libImageObject);
        LibImageObjectDTO result = libImageObjectMapper.libImageObjectToLibImageObjectDTO(libImageObject);
        return ResponseEntity.created(new URI("/api/lib-image-objects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lib-image-objects : Updates an existing libImageObject.
     *
     * @param libImageObjectDTO the libImageObjectDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated libImageObjectDTO,
     * or with status 400 (Bad Request) if the libImageObjectDTO is not valid,
     * or with status 500 (Internal Server Error) if the libImageObjectDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lib-image-objects")
    @Timed
    public ResponseEntity<LibImageObjectDTO> updateLibImageObject(@Valid @RequestBody LibImageObjectDTO libImageObjectDTO) throws URISyntaxException {
        log.debug("REST request to update LibImageObject : {}", libImageObjectDTO);
        if (libImageObjectDTO.getId() == null) {
            return createLibImageObject(libImageObjectDTO);
        }
        LibImageObject libImageObject = libImageObjectMapper.libImageObjectDTOToLibImageObject(libImageObjectDTO);
        libImageObject = libImageObjectRepository.save(libImageObject);
        LibImageObjectDTO result = libImageObjectMapper.libImageObjectToLibImageObjectDTO(libImageObject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, libImageObjectDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lib-image-objects : get all the libImageObjects.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of libImageObjects in body
     */
    @GetMapping("/lib-image-objects")
    @Timed
    public List<LibImageObjectDTO> getAllLibImageObjects() {
        log.debug("REST request to get all LibImageObjects");
        List<LibImageObject> libImageObjects = libImageObjectRepository.findAll();
        return libImageObjectMapper.libImageObjectsToLibImageObjectDTOs(libImageObjects);
    }

    /**
     * GET  /lib-image-objects/:id : get the "id" libImageObject.
     *
     * @param id the id of the libImageObjectDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the libImageObjectDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lib-image-objects/{id}")
    @Timed
    public ResponseEntity<LibImageObjectDTO> getLibImageObject(@PathVariable Long id) {
        log.debug("REST request to get LibImageObject : {}", id);
        LibImageObject libImageObject = libImageObjectRepository.findOne(id);
        LibImageObjectDTO libImageObjectDTO = libImageObjectMapper.libImageObjectToLibImageObjectDTO(libImageObject);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(libImageObjectDTO));
    }

    /**
     * DELETE  /lib-image-objects/:id : delete the "id" libImageObject.
     *
     * @param id the id of the libImageObjectDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lib-image-objects/{id}")
    @Timed
    public ResponseEntity<Void> deleteLibImageObject(@PathVariable Long id) {
        log.debug("REST request to delete LibImageObject : {}", id);
        libImageObjectRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
