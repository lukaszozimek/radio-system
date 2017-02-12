package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CorPropertyValue;

import io.protone.repository.CorPropertyValueRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CorPropertyValueDTO;
import io.protone.service.mapper.CorPropertyValueMapper;
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
 * REST controller for managing CorPropertyValue.
 */
@RestController
@RequestMapping("/api")
public class CorPropertyValueResource {

    private final Logger log = LoggerFactory.getLogger(CorPropertyValueResource.class);

    private static final String ENTITY_NAME = "corPropertyValue";
        
    private final CorPropertyValueRepository corPropertyValueRepository;

    private final CorPropertyValueMapper corPropertyValueMapper;

    public CorPropertyValueResource(CorPropertyValueRepository corPropertyValueRepository, CorPropertyValueMapper corPropertyValueMapper) {
        this.corPropertyValueRepository = corPropertyValueRepository;
        this.corPropertyValueMapper = corPropertyValueMapper;
    }

    /**
     * POST  /cor-property-values : Create a new corPropertyValue.
     *
     * @param corPropertyValueDTO the corPropertyValueDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new corPropertyValueDTO, or with status 400 (Bad Request) if the corPropertyValue has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cor-property-values")
    @Timed
    public ResponseEntity<CorPropertyValueDTO> createCorPropertyValue(@Valid @RequestBody CorPropertyValueDTO corPropertyValueDTO) throws URISyntaxException {
        log.debug("REST request to save CorPropertyValue : {}", corPropertyValueDTO);
        if (corPropertyValueDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new corPropertyValue cannot already have an ID")).body(null);
        }
        CorPropertyValue corPropertyValue = corPropertyValueMapper.corPropertyValueDTOToCorPropertyValue(corPropertyValueDTO);
        corPropertyValue = corPropertyValueRepository.save(corPropertyValue);
        CorPropertyValueDTO result = corPropertyValueMapper.corPropertyValueToCorPropertyValueDTO(corPropertyValue);
        return ResponseEntity.created(new URI("/api/cor-property-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cor-property-values : Updates an existing corPropertyValue.
     *
     * @param corPropertyValueDTO the corPropertyValueDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated corPropertyValueDTO,
     * or with status 400 (Bad Request) if the corPropertyValueDTO is not valid,
     * or with status 500 (Internal Server Error) if the corPropertyValueDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cor-property-values")
    @Timed
    public ResponseEntity<CorPropertyValueDTO> updateCorPropertyValue(@Valid @RequestBody CorPropertyValueDTO corPropertyValueDTO) throws URISyntaxException {
        log.debug("REST request to update CorPropertyValue : {}", corPropertyValueDTO);
        if (corPropertyValueDTO.getId() == null) {
            return createCorPropertyValue(corPropertyValueDTO);
        }
        CorPropertyValue corPropertyValue = corPropertyValueMapper.corPropertyValueDTOToCorPropertyValue(corPropertyValueDTO);
        corPropertyValue = corPropertyValueRepository.save(corPropertyValue);
        CorPropertyValueDTO result = corPropertyValueMapper.corPropertyValueToCorPropertyValueDTO(corPropertyValue);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, corPropertyValueDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cor-property-values : get all the corPropertyValues.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of corPropertyValues in body
     */
    @GetMapping("/cor-property-values")
    @Timed
    public List<CorPropertyValueDTO> getAllCorPropertyValues() {
        log.debug("REST request to get all CorPropertyValues");
        List<CorPropertyValue> corPropertyValues = corPropertyValueRepository.findAll();
        return corPropertyValueMapper.corPropertyValuesToCorPropertyValueDTOs(corPropertyValues);
    }

    /**
     * GET  /cor-property-values/:id : get the "id" corPropertyValue.
     *
     * @param id the id of the corPropertyValueDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the corPropertyValueDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cor-property-values/{id}")
    @Timed
    public ResponseEntity<CorPropertyValueDTO> getCorPropertyValue(@PathVariable Long id) {
        log.debug("REST request to get CorPropertyValue : {}", id);
        CorPropertyValue corPropertyValue = corPropertyValueRepository.findOne(id);
        CorPropertyValueDTO corPropertyValueDTO = corPropertyValueMapper.corPropertyValueToCorPropertyValueDTO(corPropertyValue);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(corPropertyValueDTO));
    }

    /**
     * DELETE  /cor-property-values/:id : delete the "id" corPropertyValue.
     *
     * @param id the id of the corPropertyValueDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cor-property-values/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorPropertyValue(@PathVariable Long id) {
        log.debug("REST request to delete CorPropertyValue : {}", id);
        corPropertyValueRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
