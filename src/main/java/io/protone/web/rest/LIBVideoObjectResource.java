package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LIBVideoObject;

import io.protone.repository.LIBVideoObjectRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.LIBVideoObjectDTO;
import io.protone.service.mapper.LIBVideoObjectMapper;

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
 * REST controller for managing LIBVideoObject.
 */
@RestController
@RequestMapping("/api")
public class LIBVideoObjectResource {

    private final Logger log = LoggerFactory.getLogger(LIBVideoObjectResource.class);
        
    @Inject
    private LIBVideoObjectRepository lIBVideoObjectRepository;

    @Inject
    private LIBVideoObjectMapper lIBVideoObjectMapper;

    /**
     * POST  /l-ib-video-objects : Create a new lIBVideoObject.
     *
     * @param lIBVideoObjectDTO the lIBVideoObjectDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lIBVideoObjectDTO, or with status 400 (Bad Request) if the lIBVideoObject has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/l-ib-video-objects")
    @Timed
    public ResponseEntity<LIBVideoObjectDTO> createLIBVideoObject(@Valid @RequestBody LIBVideoObjectDTO lIBVideoObjectDTO) throws URISyntaxException {
        log.debug("REST request to save LIBVideoObject : {}", lIBVideoObjectDTO);
        if (lIBVideoObjectDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lIBVideoObject", "idexists", "A new lIBVideoObject cannot already have an ID")).body(null);
        }
        LIBVideoObject lIBVideoObject = lIBVideoObjectMapper.lIBVideoObjectDTOToLIBVideoObject(lIBVideoObjectDTO);
        lIBVideoObject = lIBVideoObjectRepository.save(lIBVideoObject);
        LIBVideoObjectDTO result = lIBVideoObjectMapper.lIBVideoObjectToLIBVideoObjectDTO(lIBVideoObject);
        return ResponseEntity.created(new URI("/api/l-ib-video-objects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lIBVideoObject", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /l-ib-video-objects : Updates an existing lIBVideoObject.
     *
     * @param lIBVideoObjectDTO the lIBVideoObjectDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lIBVideoObjectDTO,
     * or with status 400 (Bad Request) if the lIBVideoObjectDTO is not valid,
     * or with status 500 (Internal Server Error) if the lIBVideoObjectDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/l-ib-video-objects")
    @Timed
    public ResponseEntity<LIBVideoObjectDTO> updateLIBVideoObject(@Valid @RequestBody LIBVideoObjectDTO lIBVideoObjectDTO) throws URISyntaxException {
        log.debug("REST request to update LIBVideoObject : {}", lIBVideoObjectDTO);
        if (lIBVideoObjectDTO.getId() == null) {
            return createLIBVideoObject(lIBVideoObjectDTO);
        }
        LIBVideoObject lIBVideoObject = lIBVideoObjectMapper.lIBVideoObjectDTOToLIBVideoObject(lIBVideoObjectDTO);
        lIBVideoObject = lIBVideoObjectRepository.save(lIBVideoObject);
        LIBVideoObjectDTO result = lIBVideoObjectMapper.lIBVideoObjectToLIBVideoObjectDTO(lIBVideoObject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lIBVideoObject", lIBVideoObjectDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /l-ib-video-objects : get all the lIBVideoObjects.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lIBVideoObjects in body
     */
    @GetMapping("/l-ib-video-objects")
    @Timed
    public List<LIBVideoObjectDTO> getAllLIBVideoObjects() {
        log.debug("REST request to get all LIBVideoObjects");
        List<LIBVideoObject> lIBVideoObjects = lIBVideoObjectRepository.findAll();
        return lIBVideoObjectMapper.lIBVideoObjectsToLIBVideoObjectDTOs(lIBVideoObjects);
    }

    /**
     * GET  /l-ib-video-objects/:id : get the "id" lIBVideoObject.
     *
     * @param id the id of the lIBVideoObjectDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lIBVideoObjectDTO, or with status 404 (Not Found)
     */
    @GetMapping("/l-ib-video-objects/{id}")
    @Timed
    public ResponseEntity<LIBVideoObjectDTO> getLIBVideoObject(@PathVariable Long id) {
        log.debug("REST request to get LIBVideoObject : {}", id);
        LIBVideoObject lIBVideoObject = lIBVideoObjectRepository.findOne(id);
        LIBVideoObjectDTO lIBVideoObjectDTO = lIBVideoObjectMapper.lIBVideoObjectToLIBVideoObjectDTO(lIBVideoObject);
        return Optional.ofNullable(lIBVideoObjectDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /l-ib-video-objects/:id : delete the "id" lIBVideoObject.
     *
     * @param id the id of the lIBVideoObjectDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/l-ib-video-objects/{id}")
    @Timed
    public ResponseEntity<Void> deleteLIBVideoObject(@PathVariable Long id) {
        log.debug("REST request to delete LIBVideoObject : {}", id);
        lIBVideoObjectRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lIBVideoObject", id.toString())).build();
    }

}
