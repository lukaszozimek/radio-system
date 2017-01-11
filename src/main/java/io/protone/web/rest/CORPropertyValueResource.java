package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CORPropertyValue;

import io.protone.repository.CORPropertyValueRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CORPropertyValueDTO;
import io.protone.service.mapper.CORPropertyValueMapper;

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
 * REST controller for managing CORPropertyValue.
 */
@RestController
@RequestMapping("/api")
public class CORPropertyValueResource {

    private final Logger log = LoggerFactory.getLogger(CORPropertyValueResource.class);
        
    @Inject
    private CORPropertyValueRepository cORPropertyValueRepository;

    @Inject
    private CORPropertyValueMapper cORPropertyValueMapper;

    /**
     * POST  /c-or-property-values : Create a new cORPropertyValue.
     *
     * @param cORPropertyValueDTO the cORPropertyValueDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cORPropertyValueDTO, or with status 400 (Bad Request) if the cORPropertyValue has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-or-property-values")
    @Timed
    public ResponseEntity<CORPropertyValueDTO> createCORPropertyValue(@Valid @RequestBody CORPropertyValueDTO cORPropertyValueDTO) throws URISyntaxException {
        log.debug("REST request to save CORPropertyValue : {}", cORPropertyValueDTO);
        if (cORPropertyValueDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORPropertyValue", "idexists", "A new cORPropertyValue cannot already have an ID")).body(null);
        }
        CORPropertyValue cORPropertyValue = cORPropertyValueMapper.cORPropertyValueDTOToCORPropertyValue(cORPropertyValueDTO);
        cORPropertyValue = cORPropertyValueRepository.save(cORPropertyValue);
        CORPropertyValueDTO result = cORPropertyValueMapper.cORPropertyValueToCORPropertyValueDTO(cORPropertyValue);
        return ResponseEntity.created(new URI("/api/c-or-property-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cORPropertyValue", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-or-property-values : Updates an existing cORPropertyValue.
     *
     * @param cORPropertyValueDTO the cORPropertyValueDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cORPropertyValueDTO,
     * or with status 400 (Bad Request) if the cORPropertyValueDTO is not valid,
     * or with status 500 (Internal Server Error) if the cORPropertyValueDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-or-property-values")
    @Timed
    public ResponseEntity<CORPropertyValueDTO> updateCORPropertyValue(@Valid @RequestBody CORPropertyValueDTO cORPropertyValueDTO) throws URISyntaxException {
        log.debug("REST request to update CORPropertyValue : {}", cORPropertyValueDTO);
        if (cORPropertyValueDTO.getId() == null) {
            return createCORPropertyValue(cORPropertyValueDTO);
        }
        CORPropertyValue cORPropertyValue = cORPropertyValueMapper.cORPropertyValueDTOToCORPropertyValue(cORPropertyValueDTO);
        cORPropertyValue = cORPropertyValueRepository.save(cORPropertyValue);
        CORPropertyValueDTO result = cORPropertyValueMapper.cORPropertyValueToCORPropertyValueDTO(cORPropertyValue);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORPropertyValue", cORPropertyValueDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-or-property-values : get all the cORPropertyValues.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cORPropertyValues in body
     */
    @GetMapping("/c-or-property-values")
    @Timed
    public List<CORPropertyValueDTO> getAllCORPropertyValues() {
        log.debug("REST request to get all CORPropertyValues");
        List<CORPropertyValue> cORPropertyValues = cORPropertyValueRepository.findAll();
        return cORPropertyValueMapper.cORPropertyValuesToCORPropertyValueDTOs(cORPropertyValues);
    }

    /**
     * GET  /c-or-property-values/:id : get the "id" cORPropertyValue.
     *
     * @param id the id of the cORPropertyValueDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cORPropertyValueDTO, or with status 404 (Not Found)
     */
    @GetMapping("/c-or-property-values/{id}")
    @Timed
    public ResponseEntity<CORPropertyValueDTO> getCORPropertyValue(@PathVariable Long id) {
        log.debug("REST request to get CORPropertyValue : {}", id);
        CORPropertyValue cORPropertyValue = cORPropertyValueRepository.findOne(id);
        CORPropertyValueDTO cORPropertyValueDTO = cORPropertyValueMapper.cORPropertyValueToCORPropertyValueDTO(cORPropertyValue);
        return Optional.ofNullable(cORPropertyValueDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-or-property-values/:id : delete the "id" cORPropertyValue.
     *
     * @param id the id of the cORPropertyValueDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-or-property-values/{id}")
    @Timed
    public ResponseEntity<Void> deleteCORPropertyValue(@PathVariable Long id) {
        log.debug("REST request to delete CORPropertyValue : {}", id);
        cORPropertyValueRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORPropertyValue", id.toString())).build();
    }

}
