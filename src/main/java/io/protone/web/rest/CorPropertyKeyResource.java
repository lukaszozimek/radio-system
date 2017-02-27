package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CorPropertyKey;

import io.protone.repository.CorPropertyKeyRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CorPropertyKeyDTO;
import io.protone.service.mapper.CorPropertyKeyMapper;
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
 * REST controller for managing CorPropertyKey.
 */
@RestController
@RequestMapping("/api")
public class CorPropertyKeyResource {

    private final Logger log = LoggerFactory.getLogger(CorPropertyKeyResource.class);

    private static final String ENTITY_NAME = "corPropertyKey";

    private final CorPropertyKeyRepository corPropertyKeyRepository;

    private final CorPropertyKeyMapper corPropertyKeyMapper;

    public CorPropertyKeyResource(CorPropertyKeyRepository corPropertyKeyRepository, CorPropertyKeyMapper corPropertyKeyMapper) {
        this.corPropertyKeyRepository = corPropertyKeyRepository;
        this.corPropertyKeyMapper = corPropertyKeyMapper;
    }

    /**
     * POST  /cor-property-keys : Create a new corPropertyKey.
     *
     * @param corPropertyKeyDTO the corPropertyKeyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new corPropertyKeyDTO, or with status 400 (Bad Request) if the corPropertyKey has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cor-property-keys")
    @Timed
    public ResponseEntity<CorPropertyKeyDTO> createCorPropertyKey(@Valid @RequestBody CorPropertyKeyDTO corPropertyKeyDTO) throws URISyntaxException {
        log.debug("REST request to save CorPropertyKey : {}", corPropertyKeyDTO);
        if (corPropertyKeyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new corPropertyKey cannot already have an ID")).body(null);
        }
        CorPropertyKey corPropertyKey = corPropertyKeyMapper.corPropertyKeyDTOToCorPropertyKey(corPropertyKeyDTO);
        corPropertyKey = corPropertyKeyRepository.save(corPropertyKey);
        CorPropertyKeyDTO result = corPropertyKeyMapper.corPropertyKeyToCorPropertyKeyDTO(corPropertyKey);
        return ResponseEntity.created(new URI("/api/cor-property-keys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cor-property-keys : Updates an existing corPropertyKey.
     *
     * @param corPropertyKeyDTO the corPropertyKeyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated corPropertyKeyDTO,
     * or with status 400 (Bad Request) if the corPropertyKeyDTO is not valid,
     * or with status 500 (Internal Server Error) if the corPropertyKeyDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cor-property-keys")
    @Timed
    public ResponseEntity<CorPropertyKeyDTO> updateCorPropertyKey(@Valid @RequestBody CorPropertyKeyDTO corPropertyKeyDTO) throws URISyntaxException {
        log.debug("REST request to update CorPropertyKey : {}", corPropertyKeyDTO);
        if (corPropertyKeyDTO.getId() == null) {
            return createCorPropertyKey(corPropertyKeyDTO);
        }
        CorPropertyKey corPropertyKey = corPropertyKeyMapper.corPropertyKeyDTOToCorPropertyKey(corPropertyKeyDTO);
        corPropertyKey = corPropertyKeyRepository.save(corPropertyKey);
        CorPropertyKeyDTO result = corPropertyKeyMapper.corPropertyKeyToCorPropertyKeyDTO(corPropertyKey);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, corPropertyKeyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cor-property-keys : get all the corPropertyKeys.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of corPropertyKeys in body
     */
    @GetMapping("/cor-property-keys")
    @Timed
    public List<CorPropertyKeyDTO> getAllCorPropertyKeys() {
        log.debug("REST request to get all CorPropertyKeys");
        List<CorPropertyKey> corPropertyKeys = corPropertyKeyRepository.findAll();
        return corPropertyKeyMapper.corPropertyKeysToCorPropertyKeyDTOs(corPropertyKeys);
    }

    /**
     * GET  /cor-property-keys/:id : get the "id" corPropertyKey.
     *
     * @param id the id of the corPropertyKeyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the corPropertyKeyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cor-property-keys/{id}")
    @Timed
    public ResponseEntity<CorPropertyKeyDTO> getCorPropertyKey(@PathVariable Long id) {
        log.debug("REST request to get CorPropertyKey : {}", id);
        CorPropertyKey corPropertyKey = corPropertyKeyRepository.findOne(id);
        CorPropertyKeyDTO corPropertyKeyDTO = corPropertyKeyMapper.corPropertyKeyToCorPropertyKeyDTO(corPropertyKey);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(corPropertyKeyDTO));
    }

    /**
     * DELETE  /cor-property-keys/:id : delete the "id" corPropertyKey.
     *
     * @param id the id of the corPropertyKeyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cor-property-keys/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorPropertyKey(@PathVariable Long id) {
        log.debug("REST request to delete CorPropertyKey : {}", id);
        corPropertyKeyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
