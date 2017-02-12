package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LibCloudObject;

import io.protone.repository.LibCloudObjectRepository;
import io.protone.web.rest.util.HeaderUtil;
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
 * REST controller for managing LibCloudObject.
 */
@RestController
@RequestMapping("/api")
public class LibCloudObjectResource {

    private final Logger log = LoggerFactory.getLogger(LibCloudObjectResource.class);

    private static final String ENTITY_NAME = "libCloudObject";
        
    private final LibCloudObjectRepository libCloudObjectRepository;

    public LibCloudObjectResource(LibCloudObjectRepository libCloudObjectRepository) {
        this.libCloudObjectRepository = libCloudObjectRepository;
    }

    /**
     * POST  /lib-cloud-objects : Create a new libCloudObject.
     *
     * @param libCloudObject the libCloudObject to create
     * @return the ResponseEntity with status 201 (Created) and with body the new libCloudObject, or with status 400 (Bad Request) if the libCloudObject has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lib-cloud-objects")
    @Timed
    public ResponseEntity<LibCloudObject> createLibCloudObject(@Valid @RequestBody LibCloudObject libCloudObject) throws URISyntaxException {
        log.debug("REST request to save LibCloudObject : {}", libCloudObject);
        if (libCloudObject.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new libCloudObject cannot already have an ID")).body(null);
        }
        LibCloudObject result = libCloudObjectRepository.save(libCloudObject);
        return ResponseEntity.created(new URI("/api/lib-cloud-objects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lib-cloud-objects : Updates an existing libCloudObject.
     *
     * @param libCloudObject the libCloudObject to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated libCloudObject,
     * or with status 400 (Bad Request) if the libCloudObject is not valid,
     * or with status 500 (Internal Server Error) if the libCloudObject couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lib-cloud-objects")
    @Timed
    public ResponseEntity<LibCloudObject> updateLibCloudObject(@Valid @RequestBody LibCloudObject libCloudObject) throws URISyntaxException {
        log.debug("REST request to update LibCloudObject : {}", libCloudObject);
        if (libCloudObject.getId() == null) {
            return createLibCloudObject(libCloudObject);
        }
        LibCloudObject result = libCloudObjectRepository.save(libCloudObject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, libCloudObject.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lib-cloud-objects : get all the libCloudObjects.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of libCloudObjects in body
     */
    @GetMapping("/lib-cloud-objects")
    @Timed
    public List<LibCloudObject> getAllLibCloudObjects() {
        log.debug("REST request to get all LibCloudObjects");
        List<LibCloudObject> libCloudObjects = libCloudObjectRepository.findAll();
        return libCloudObjects;
    }

    /**
     * GET  /lib-cloud-objects/:id : get the "id" libCloudObject.
     *
     * @param id the id of the libCloudObject to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the libCloudObject, or with status 404 (Not Found)
     */
    @GetMapping("/lib-cloud-objects/{id}")
    @Timed
    public ResponseEntity<LibCloudObject> getLibCloudObject(@PathVariable Long id) {
        log.debug("REST request to get LibCloudObject : {}", id);
        LibCloudObject libCloudObject = libCloudObjectRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(libCloudObject));
    }

    /**
     * DELETE  /lib-cloud-objects/:id : delete the "id" libCloudObject.
     *
     * @param id the id of the libCloudObject to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lib-cloud-objects/{id}")
    @Timed
    public ResponseEntity<Void> deleteLibCloudObject(@PathVariable Long id) {
        log.debug("REST request to delete LibCloudObject : {}", id);
        libCloudObjectRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
