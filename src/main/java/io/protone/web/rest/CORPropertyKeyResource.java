package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CORPropertyKey;

import io.protone.repository.CORPropertyKeyRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CORPropertyKeyDTO;
import io.protone.service.mapper.CORPropertyKeyMapper;

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
 * REST controller for managing CORPropertyKey.
 */
@RestController
@RequestMapping("/api")
public class CORPropertyKeyResource {

    private final Logger log = LoggerFactory.getLogger(CORPropertyKeyResource.class);
        
    @Inject
    private CORPropertyKeyRepository cORPropertyKeyRepository;

    @Inject
    private CORPropertyKeyMapper cORPropertyKeyMapper;

    /**
     * POST  /c-or-property-keys : Create a new cORPropertyKey.
     *
     * @param cORPropertyKeyDTO the cORPropertyKeyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cORPropertyKeyDTO, or with status 400 (Bad Request) if the cORPropertyKey has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-or-property-keys")
    @Timed
    public ResponseEntity<CORPropertyKeyDTO> createCORPropertyKey(@Valid @RequestBody CORPropertyKeyDTO cORPropertyKeyDTO) throws URISyntaxException {
        log.debug("REST request to save CORPropertyKey : {}", cORPropertyKeyDTO);
        if (cORPropertyKeyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORPropertyKey", "idexists", "A new cORPropertyKey cannot already have an ID")).body(null);
        }
        CORPropertyKey cORPropertyKey = cORPropertyKeyMapper.cORPropertyKeyDTOToCORPropertyKey(cORPropertyKeyDTO);
        cORPropertyKey = cORPropertyKeyRepository.save(cORPropertyKey);
        CORPropertyKeyDTO result = cORPropertyKeyMapper.cORPropertyKeyToCORPropertyKeyDTO(cORPropertyKey);
        return ResponseEntity.created(new URI("/api/c-or-property-keys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cORPropertyKey", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-or-property-keys : Updates an existing cORPropertyKey.
     *
     * @param cORPropertyKeyDTO the cORPropertyKeyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cORPropertyKeyDTO,
     * or with status 400 (Bad Request) if the cORPropertyKeyDTO is not valid,
     * or with status 500 (Internal Server Error) if the cORPropertyKeyDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-or-property-keys")
    @Timed
    public ResponseEntity<CORPropertyKeyDTO> updateCORPropertyKey(@Valid @RequestBody CORPropertyKeyDTO cORPropertyKeyDTO) throws URISyntaxException {
        log.debug("REST request to update CORPropertyKey : {}", cORPropertyKeyDTO);
        if (cORPropertyKeyDTO.getId() == null) {
            return createCORPropertyKey(cORPropertyKeyDTO);
        }
        CORPropertyKey cORPropertyKey = cORPropertyKeyMapper.cORPropertyKeyDTOToCORPropertyKey(cORPropertyKeyDTO);
        cORPropertyKey = cORPropertyKeyRepository.save(cORPropertyKey);
        CORPropertyKeyDTO result = cORPropertyKeyMapper.cORPropertyKeyToCORPropertyKeyDTO(cORPropertyKey);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORPropertyKey", cORPropertyKeyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-or-property-keys : get all the cORPropertyKeys.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cORPropertyKeys in body
     */
    @GetMapping("/c-or-property-keys")
    @Timed
    public List<CORPropertyKeyDTO> getAllCORPropertyKeys() {
        log.debug("REST request to get all CORPropertyKeys");
        List<CORPropertyKey> cORPropertyKeys = cORPropertyKeyRepository.findAll();
        return cORPropertyKeyMapper.cORPropertyKeysToCORPropertyKeyDTOs(cORPropertyKeys);
    }

    /**
     * GET  /c-or-property-keys/:id : get the "id" cORPropertyKey.
     *
     * @param id the id of the cORPropertyKeyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cORPropertyKeyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/c-or-property-keys/{id}")
    @Timed
    public ResponseEntity<CORPropertyKeyDTO> getCORPropertyKey(@PathVariable Long id) {
        log.debug("REST request to get CORPropertyKey : {}", id);
        CORPropertyKey cORPropertyKey = cORPropertyKeyRepository.findOne(id);
        CORPropertyKeyDTO cORPropertyKeyDTO = cORPropertyKeyMapper.cORPropertyKeyToCORPropertyKeyDTO(cORPropertyKey);
        return Optional.ofNullable(cORPropertyKeyDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-or-property-keys/:id : delete the "id" cORPropertyKey.
     *
     * @param id the id of the cORPropertyKeyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-or-property-keys/{id}")
    @Timed
    public ResponseEntity<Void> deleteCORPropertyKey(@PathVariable Long id) {
        log.debug("REST request to delete CORPropertyKey : {}", id);
        cORPropertyKeyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORPropertyKey", id.toString())).build();
    }

}
