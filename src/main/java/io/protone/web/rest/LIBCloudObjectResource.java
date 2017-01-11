package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LIBCloudObject;

import io.protone.repository.LIBCloudObjectRepository;
import io.protone.web.rest.util.HeaderUtil;

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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LIBCloudObject.
 */
@RestController
@RequestMapping("/api")
public class LIBCloudObjectResource {

    private final Logger log = LoggerFactory.getLogger(LIBCloudObjectResource.class);
        
    @Inject
    private LIBCloudObjectRepository lIBCloudObjectRepository;

    /**
     * POST  /l-ib-cloud-objects : Create a new lIBCloudObject.
     *
     * @param lIBCloudObject the lIBCloudObject to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lIBCloudObject, or with status 400 (Bad Request) if the lIBCloudObject has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/l-ib-cloud-objects")
    @Timed
    public ResponseEntity<LIBCloudObject> createLIBCloudObject(@Valid @RequestBody LIBCloudObject lIBCloudObject) throws URISyntaxException {
        log.debug("REST request to save LIBCloudObject : {}", lIBCloudObject);
        if (lIBCloudObject.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lIBCloudObject", "idexists", "A new lIBCloudObject cannot already have an ID")).body(null);
        }
        LIBCloudObject result = lIBCloudObjectRepository.save(lIBCloudObject);
        return ResponseEntity.created(new URI("/api/l-ib-cloud-objects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lIBCloudObject", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /l-ib-cloud-objects : Updates an existing lIBCloudObject.
     *
     * @param lIBCloudObject the lIBCloudObject to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lIBCloudObject,
     * or with status 400 (Bad Request) if the lIBCloudObject is not valid,
     * or with status 500 (Internal Server Error) if the lIBCloudObject couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/l-ib-cloud-objects")
    @Timed
    public ResponseEntity<LIBCloudObject> updateLIBCloudObject(@Valid @RequestBody LIBCloudObject lIBCloudObject) throws URISyntaxException {
        log.debug("REST request to update LIBCloudObject : {}", lIBCloudObject);
        if (lIBCloudObject.getId() == null) {
            return createLIBCloudObject(lIBCloudObject);
        }
        LIBCloudObject result = lIBCloudObjectRepository.save(lIBCloudObject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lIBCloudObject", lIBCloudObject.getId().toString()))
            .body(result);
    }

    /**
     * GET  /l-ib-cloud-objects : get all the lIBCloudObjects.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lIBCloudObjects in body
     */
    @GetMapping("/l-ib-cloud-objects")
    @Timed
    public List<LIBCloudObject> getAllLIBCloudObjects() {
        log.debug("REST request to get all LIBCloudObjects");
        List<LIBCloudObject> lIBCloudObjects = lIBCloudObjectRepository.findAll();
        return lIBCloudObjects;
    }

    /**
     * GET  /l-ib-cloud-objects/:id : get the "id" lIBCloudObject.
     *
     * @param id the id of the lIBCloudObject to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lIBCloudObject, or with status 404 (Not Found)
     */
    @GetMapping("/l-ib-cloud-objects/{id}")
    @Timed
    public ResponseEntity<LIBCloudObject> getLIBCloudObject(@PathVariable Long id) {
        log.debug("REST request to get LIBCloudObject : {}", id);
        LIBCloudObject lIBCloudObject = lIBCloudObjectRepository.findOne(id);
        return Optional.ofNullable(lIBCloudObject)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /l-ib-cloud-objects/:id : delete the "id" lIBCloudObject.
     *
     * @param id the id of the lIBCloudObject to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/l-ib-cloud-objects/{id}")
    @Timed
    public ResponseEntity<Void> deleteLIBCloudObject(@PathVariable Long id) {
        log.debug("REST request to delete LIBCloudObject : {}", id);
        lIBCloudObjectRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lIBCloudObject", id.toString())).build();
    }

}
