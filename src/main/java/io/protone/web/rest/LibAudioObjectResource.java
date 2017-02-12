package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LibAudioObject;

import io.protone.repository.LibAudioObjectRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.LibAudioObjectDTO;
import io.protone.service.mapper.LibAudioObjectMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing LibAudioObject.
 */
@RestController
@RequestMapping("/api")
public class LibAudioObjectResource {

    private final Logger log = LoggerFactory.getLogger(LibAudioObjectResource.class);

    private static final String ENTITY_NAME = "libAudioObject";
        
    private final LibAudioObjectRepository libAudioObjectRepository;

    private final LibAudioObjectMapper libAudioObjectMapper;

    public LibAudioObjectResource(LibAudioObjectRepository libAudioObjectRepository, LibAudioObjectMapper libAudioObjectMapper) {
        this.libAudioObjectRepository = libAudioObjectRepository;
        this.libAudioObjectMapper = libAudioObjectMapper;
    }

    /**
     * POST  /lib-audio-objects : Create a new libAudioObject.
     *
     * @param libAudioObjectDTO the libAudioObjectDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new libAudioObjectDTO, or with status 400 (Bad Request) if the libAudioObject has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lib-audio-objects")
    @Timed
    public ResponseEntity<LibAudioObjectDTO> createLibAudioObject(@Valid @RequestBody LibAudioObjectDTO libAudioObjectDTO) throws URISyntaxException {
        log.debug("REST request to save LibAudioObject : {}", libAudioObjectDTO);
        if (libAudioObjectDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new libAudioObject cannot already have an ID")).body(null);
        }
        LibAudioObject libAudioObject = libAudioObjectMapper.libAudioObjectDTOToLibAudioObject(libAudioObjectDTO);
        libAudioObject = libAudioObjectRepository.save(libAudioObject);
        LibAudioObjectDTO result = libAudioObjectMapper.libAudioObjectToLibAudioObjectDTO(libAudioObject);
        return ResponseEntity.created(new URI("/api/lib-audio-objects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lib-audio-objects : Updates an existing libAudioObject.
     *
     * @param libAudioObjectDTO the libAudioObjectDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated libAudioObjectDTO,
     * or with status 400 (Bad Request) if the libAudioObjectDTO is not valid,
     * or with status 500 (Internal Server Error) if the libAudioObjectDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lib-audio-objects")
    @Timed
    public ResponseEntity<LibAudioObjectDTO> updateLibAudioObject(@Valid @RequestBody LibAudioObjectDTO libAudioObjectDTO) throws URISyntaxException {
        log.debug("REST request to update LibAudioObject : {}", libAudioObjectDTO);
        if (libAudioObjectDTO.getId() == null) {
            return createLibAudioObject(libAudioObjectDTO);
        }
        LibAudioObject libAudioObject = libAudioObjectMapper.libAudioObjectDTOToLibAudioObject(libAudioObjectDTO);
        libAudioObject = libAudioObjectRepository.save(libAudioObject);
        LibAudioObjectDTO result = libAudioObjectMapper.libAudioObjectToLibAudioObjectDTO(libAudioObject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, libAudioObjectDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lib-audio-objects : get all the libAudioObjects.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of libAudioObjects in body
     */
    @GetMapping("/lib-audio-objects")
    @Timed
    public List<LibAudioObjectDTO> getAllLibAudioObjects() {
        log.debug("REST request to get all LibAudioObjects");
        List<LibAudioObject> libAudioObjects = libAudioObjectRepository.findAll();
        return libAudioObjectMapper.libAudioObjectsToLibAudioObjectDTOs(libAudioObjects);
    }

    /**
     * GET  /lib-audio-objects/:id : get the "id" libAudioObject.
     *
     * @param id the id of the libAudioObjectDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the libAudioObjectDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lib-audio-objects/{id}")
    @Timed
    public ResponseEntity<LibAudioObjectDTO> getLibAudioObject(@PathVariable Long id) {
        log.debug("REST request to get LibAudioObject : {}", id);
        LibAudioObject libAudioObject = libAudioObjectRepository.findOne(id);
        LibAudioObjectDTO libAudioObjectDTO = libAudioObjectMapper.libAudioObjectToLibAudioObjectDTO(libAudioObject);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(libAudioObjectDTO));
    }

    /**
     * DELETE  /lib-audio-objects/:id : delete the "id" libAudioObject.
     *
     * @param id the id of the libAudioObjectDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lib-audio-objects/{id}")
    @Timed
    public ResponseEntity<Void> deleteLibAudioObject(@PathVariable Long id) {
        log.debug("REST request to delete LibAudioObject : {}", id);
        libAudioObjectRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
