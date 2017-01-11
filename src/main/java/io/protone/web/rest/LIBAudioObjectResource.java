package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LIBAudioObject;

import io.protone.repository.LIBAudioObjectRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.LIBAudioObjectDTO;
import io.protone.service.mapper.LIBAudioObjectMapper;

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
 * REST controller for managing LIBAudioObject.
 */
@RestController
@RequestMapping("/api")
public class LIBAudioObjectResource {

    private final Logger log = LoggerFactory.getLogger(LIBAudioObjectResource.class);
        
    @Inject
    private LIBAudioObjectRepository lIBAudioObjectRepository;

    @Inject
    private LIBAudioObjectMapper lIBAudioObjectMapper;

    /**
     * POST  /l-ib-audio-objects : Create a new lIBAudioObject.
     *
     * @param lIBAudioObjectDTO the lIBAudioObjectDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lIBAudioObjectDTO, or with status 400 (Bad Request) if the lIBAudioObject has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/l-ib-audio-objects")
    @Timed
    public ResponseEntity<LIBAudioObjectDTO> createLIBAudioObject(@Valid @RequestBody LIBAudioObjectDTO lIBAudioObjectDTO) throws URISyntaxException {
        log.debug("REST request to save LIBAudioObject : {}", lIBAudioObjectDTO);
        if (lIBAudioObjectDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lIBAudioObject", "idexists", "A new lIBAudioObject cannot already have an ID")).body(null);
        }
        LIBAudioObject lIBAudioObject = lIBAudioObjectMapper.lIBAudioObjectDTOToLIBAudioObject(lIBAudioObjectDTO);
        lIBAudioObject = lIBAudioObjectRepository.save(lIBAudioObject);
        LIBAudioObjectDTO result = lIBAudioObjectMapper.lIBAudioObjectToLIBAudioObjectDTO(lIBAudioObject);
        return ResponseEntity.created(new URI("/api/l-ib-audio-objects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lIBAudioObject", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /l-ib-audio-objects : Updates an existing lIBAudioObject.
     *
     * @param lIBAudioObjectDTO the lIBAudioObjectDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lIBAudioObjectDTO,
     * or with status 400 (Bad Request) if the lIBAudioObjectDTO is not valid,
     * or with status 500 (Internal Server Error) if the lIBAudioObjectDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/l-ib-audio-objects")
    @Timed
    public ResponseEntity<LIBAudioObjectDTO> updateLIBAudioObject(@Valid @RequestBody LIBAudioObjectDTO lIBAudioObjectDTO) throws URISyntaxException {
        log.debug("REST request to update LIBAudioObject : {}", lIBAudioObjectDTO);
        if (lIBAudioObjectDTO.getId() == null) {
            return createLIBAudioObject(lIBAudioObjectDTO);
        }
        LIBAudioObject lIBAudioObject = lIBAudioObjectMapper.lIBAudioObjectDTOToLIBAudioObject(lIBAudioObjectDTO);
        lIBAudioObject = lIBAudioObjectRepository.save(lIBAudioObject);
        LIBAudioObjectDTO result = lIBAudioObjectMapper.lIBAudioObjectToLIBAudioObjectDTO(lIBAudioObject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lIBAudioObject", lIBAudioObjectDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /l-ib-audio-objects : get all the lIBAudioObjects.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lIBAudioObjects in body
     */
    @GetMapping("/l-ib-audio-objects")
    @Timed
    public List<LIBAudioObjectDTO> getAllLIBAudioObjects() {
        log.debug("REST request to get all LIBAudioObjects");
        List<LIBAudioObject> lIBAudioObjects = lIBAudioObjectRepository.findAll();
        return lIBAudioObjectMapper.lIBAudioObjectsToLIBAudioObjectDTOs(lIBAudioObjects);
    }

    /**
     * GET  /l-ib-audio-objects/:id : get the "id" lIBAudioObject.
     *
     * @param id the id of the lIBAudioObjectDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lIBAudioObjectDTO, or with status 404 (Not Found)
     */
    @GetMapping("/l-ib-audio-objects/{id}")
    @Timed
    public ResponseEntity<LIBAudioObjectDTO> getLIBAudioObject(@PathVariable Long id) {
        log.debug("REST request to get LIBAudioObject : {}", id);
        LIBAudioObject lIBAudioObject = lIBAudioObjectRepository.findOne(id);
        LIBAudioObjectDTO lIBAudioObjectDTO = lIBAudioObjectMapper.lIBAudioObjectToLIBAudioObjectDTO(lIBAudioObject);
        return Optional.ofNullable(lIBAudioObjectDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /l-ib-audio-objects/:id : delete the "id" lIBAudioObject.
     *
     * @param id the id of the lIBAudioObjectDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/l-ib-audio-objects/{id}")
    @Timed
    public ResponseEntity<Void> deleteLIBAudioObject(@PathVariable Long id) {
        log.debug("REST request to delete LIBAudioObject : {}", id);
        lIBAudioObjectRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lIBAudioObject", id.toString())).build();
    }

}
