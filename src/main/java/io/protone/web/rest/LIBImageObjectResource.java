package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LIBImageObject;

import io.protone.repository.LIBImageObjectRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.LIBImageObjectDTO;
import io.protone.service.mapper.LIBImageObjectMapper;

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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing LIBImageObject.
 */
@RestController
@RequestMapping("/api")
public class LIBImageObjectResource {

    private final Logger log = LoggerFactory.getLogger(LIBImageObjectResource.class);
        
    @Inject
    private LIBImageObjectRepository lIBImageObjectRepository;

    @Inject
    private LIBImageObjectMapper lIBImageObjectMapper;

    /**
     * POST  /l-ib-image-objects : Create a new lIBImageObject.
     *
     * @param lIBImageObjectDTO the lIBImageObjectDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lIBImageObjectDTO, or with status 400 (Bad Request) if the lIBImageObject has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/l-ib-image-objects")
    @Timed
    public ResponseEntity<LIBImageObjectDTO> createLIBImageObject(@Valid @RequestBody LIBImageObjectDTO lIBImageObjectDTO) throws URISyntaxException {
        log.debug("REST request to save LIBImageObject : {}", lIBImageObjectDTO);
        if (lIBImageObjectDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lIBImageObject", "idexists", "A new lIBImageObject cannot already have an ID")).body(null);
        }
        LIBImageObject lIBImageObject = lIBImageObjectMapper.lIBImageObjectDTOToLIBImageObject(lIBImageObjectDTO);
        lIBImageObject = lIBImageObjectRepository.save(lIBImageObject);
        LIBImageObjectDTO result = lIBImageObjectMapper.lIBImageObjectToLIBImageObjectDTO(lIBImageObject);
        return ResponseEntity.created(new URI("/api/l-ib-image-objects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lIBImageObject", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /l-ib-image-objects : Updates an existing lIBImageObject.
     *
     * @param lIBImageObjectDTO the lIBImageObjectDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lIBImageObjectDTO,
     * or with status 400 (Bad Request) if the lIBImageObjectDTO is not valid,
     * or with status 500 (Internal Server Error) if the lIBImageObjectDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/l-ib-image-objects")
    @Timed
    public ResponseEntity<LIBImageObjectDTO> updateLIBImageObject(@Valid @RequestBody LIBImageObjectDTO lIBImageObjectDTO) throws URISyntaxException {
        log.debug("REST request to update LIBImageObject : {}", lIBImageObjectDTO);
        if (lIBImageObjectDTO.getId() == null) {
            return createLIBImageObject(lIBImageObjectDTO);
        }
        LIBImageObject lIBImageObject = lIBImageObjectMapper.lIBImageObjectDTOToLIBImageObject(lIBImageObjectDTO);
        lIBImageObject = lIBImageObjectRepository.save(lIBImageObject);
        LIBImageObjectDTO result = lIBImageObjectMapper.lIBImageObjectToLIBImageObjectDTO(lIBImageObject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lIBImageObject", lIBImageObjectDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /l-ib-image-objects : get all the lIBImageObjects.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lIBImageObjects in body
     */
    @GetMapping("/l-ib-image-objects")
    @Timed
    public List<LIBImageObjectDTO> getAllLIBImageObjects() {
        log.debug("REST request to get all LIBImageObjects");
        List<LIBImageObject> lIBImageObjects = lIBImageObjectRepository.findAll();
        return lIBImageObjectMapper.lIBImageObjectsToLIBImageObjectDTOs(lIBImageObjects);
    }

    /**
     * GET  /l-ib-image-objects/:id : get the "id" lIBImageObject.
     *
     * @param id the id of the lIBImageObjectDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lIBImageObjectDTO, or with status 404 (Not Found)
     */
    @GetMapping("/l-ib-image-objects/{id}")
    @Timed
    public ResponseEntity<LIBImageObjectDTO> getLIBImageObject(@PathVariable Long id) {
        log.debug("REST request to get LIBImageObject : {}", id);
        LIBImageObject lIBImageObject = lIBImageObjectRepository.findOne(id);
        LIBImageObjectDTO lIBImageObjectDTO = lIBImageObjectMapper.lIBImageObjectToLIBImageObjectDTO(lIBImageObject);
        return Optional.ofNullable(lIBImageObjectDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /l-ib-image-objects/:id : delete the "id" lIBImageObject.
     *
     * @param id the id of the lIBImageObjectDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/l-ib-image-objects/{id}")
    @Timed
    public ResponseEntity<Void> deleteLIBImageObject(@PathVariable Long id) {
        log.debug("REST request to delete LIBImageObject : {}", id);
        lIBImageObjectRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lIBImageObject", id.toString())).build();
    }

}
