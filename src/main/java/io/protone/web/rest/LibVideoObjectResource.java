package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LibVideoObject;

import io.protone.repository.LibVideoObjectRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.LibVideoObjectDTO;
import io.protone.service.mapper.LibVideoObjectMapper;
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
 * REST controller for managing LibVideoObject.
 */
@RestController
@RequestMapping("/api")
public class LibVideoObjectResource {

    private final Logger log = LoggerFactory.getLogger(LibVideoObjectResource.class);

    private static final String ENTITY_NAME = "libVideoObject";
        
    private final LibVideoObjectRepository libVideoObjectRepository;

    private final LibVideoObjectMapper libVideoObjectMapper;

    public LibVideoObjectResource(LibVideoObjectRepository libVideoObjectRepository, LibVideoObjectMapper libVideoObjectMapper) {
        this.libVideoObjectRepository = libVideoObjectRepository;
        this.libVideoObjectMapper = libVideoObjectMapper;
    }

    /**
     * POST  /lib-video-objects : Create a new libVideoObject.
     *
     * @param libVideoObjectDTO the libVideoObjectDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new libVideoObjectDTO, or with status 400 (Bad Request) if the libVideoObject has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lib-video-objects")
    @Timed
    public ResponseEntity<LibVideoObjectDTO> createLibVideoObject(@Valid @RequestBody LibVideoObjectDTO libVideoObjectDTO) throws URISyntaxException {
        log.debug("REST request to save LibVideoObject : {}", libVideoObjectDTO);
        if (libVideoObjectDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new libVideoObject cannot already have an ID")).body(null);
        }
        LibVideoObject libVideoObject = libVideoObjectMapper.libVideoObjectDTOToLibVideoObject(libVideoObjectDTO);
        libVideoObject = libVideoObjectRepository.save(libVideoObject);
        LibVideoObjectDTO result = libVideoObjectMapper.libVideoObjectToLibVideoObjectDTO(libVideoObject);
        return ResponseEntity.created(new URI("/api/lib-video-objects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lib-video-objects : Updates an existing libVideoObject.
     *
     * @param libVideoObjectDTO the libVideoObjectDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated libVideoObjectDTO,
     * or with status 400 (Bad Request) if the libVideoObjectDTO is not valid,
     * or with status 500 (Internal Server Error) if the libVideoObjectDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lib-video-objects")
    @Timed
    public ResponseEntity<LibVideoObjectDTO> updateLibVideoObject(@Valid @RequestBody LibVideoObjectDTO libVideoObjectDTO) throws URISyntaxException {
        log.debug("REST request to update LibVideoObject : {}", libVideoObjectDTO);
        if (libVideoObjectDTO.getId() == null) {
            return createLibVideoObject(libVideoObjectDTO);
        }
        LibVideoObject libVideoObject = libVideoObjectMapper.libVideoObjectDTOToLibVideoObject(libVideoObjectDTO);
        libVideoObject = libVideoObjectRepository.save(libVideoObject);
        LibVideoObjectDTO result = libVideoObjectMapper.libVideoObjectToLibVideoObjectDTO(libVideoObject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, libVideoObjectDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lib-video-objects : get all the libVideoObjects.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of libVideoObjects in body
     */
    @GetMapping("/lib-video-objects")
    @Timed
    public List<LibVideoObjectDTO> getAllLibVideoObjects() {
        log.debug("REST request to get all LibVideoObjects");
        List<LibVideoObject> libVideoObjects = libVideoObjectRepository.findAll();
        return libVideoObjectMapper.libVideoObjectsToLibVideoObjectDTOs(libVideoObjects);
    }

    /**
     * GET  /lib-video-objects/:id : get the "id" libVideoObject.
     *
     * @param id the id of the libVideoObjectDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the libVideoObjectDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lib-video-objects/{id}")
    @Timed
    public ResponseEntity<LibVideoObjectDTO> getLibVideoObject(@PathVariable Long id) {
        log.debug("REST request to get LibVideoObject : {}", id);
        LibVideoObject libVideoObject = libVideoObjectRepository.findOne(id);
        LibVideoObjectDTO libVideoObjectDTO = libVideoObjectMapper.libVideoObjectToLibVideoObjectDTO(libVideoObject);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(libVideoObjectDTO));
    }

    /**
     * DELETE  /lib-video-objects/:id : delete the "id" libVideoObject.
     *
     * @param id the id of the libVideoObjectDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lib-video-objects/{id}")
    @Timed
    public ResponseEntity<Void> deleteLibVideoObject(@PathVariable Long id) {
        log.debug("REST request to delete LibVideoObject : {}", id);
        libVideoObjectRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
