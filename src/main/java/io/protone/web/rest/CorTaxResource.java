package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CorTax;

import io.protone.repository.CorTaxRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CorTaxDTO;
import io.protone.service.mapper.CorTaxMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CorTax.
 */
@RestController
@RequestMapping("/api")
public class CorTaxResource {

    private final Logger log = LoggerFactory.getLogger(CorTaxResource.class);

    private static final String ENTITY_NAME = "corTax";

    private final CorTaxRepository corTaxRepository;

    private final CorTaxMapper corTaxMapper;

    public CorTaxResource(CorTaxRepository corTaxRepository, CorTaxMapper corTaxMapper) {
        this.corTaxRepository = corTaxRepository;
        this.corTaxMapper = corTaxMapper;
    }

    /**
     * POST  /cor-taxes : Create a new corTax.
     *
     * @param corTaxDTO the corTaxDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new corTaxDTO, or with status 400 (Bad Request) if the corTax has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cor-taxes")
    @Timed
    public ResponseEntity<CorTaxDTO> createCorTax(@RequestBody CorTaxDTO corTaxDTO) throws URISyntaxException {
        log.debug("REST request to save CorTax : {}", corTaxDTO);
        if (corTaxDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new corTax cannot already have an ID")).body(null);
        }
        CorTax corTax = corTaxMapper.corTaxDTOToCorTax(corTaxDTO);
        corTax = corTaxRepository.save(corTax);
        CorTaxDTO result = corTaxMapper.corTaxToCorTaxDTO(corTax);
        return ResponseEntity.created(new URI("/api/cor-taxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cor-taxes : Updates an existing corTax.
     *
     * @param corTaxDTO the corTaxDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated corTaxDTO,
     * or with status 400 (Bad Request) if the corTaxDTO is not valid,
     * or with status 500 (Internal Server Error) if the corTaxDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cor-taxes")
    @Timed
    public ResponseEntity<CorTaxDTO> updateCorTax(@RequestBody CorTaxDTO corTaxDTO) throws URISyntaxException {
        log.debug("REST request to update CorTax : {}", corTaxDTO);
        if (corTaxDTO.getId() == null) {
            return createCorTax(corTaxDTO);
        }
        CorTax corTax = corTaxMapper.corTaxDTOToCorTax(corTaxDTO);
        corTax = corTaxRepository.save(corTax);
        CorTaxDTO result = corTaxMapper.corTaxToCorTaxDTO(corTax);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, corTaxDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cor-taxes : get all the corTaxes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of corTaxes in body
     */
    @GetMapping("/cor-taxes")
    @Timed
    public List<CorTaxDTO> getAllCorTaxes() {
        log.debug("REST request to get all CorTaxes");
        List<CorTax> corTaxes = corTaxRepository.findAll();
        return corTaxMapper.corTaxesToCorTaxDTOs(corTaxes);
    }

    /**
     * GET  /cor-taxes/:id : get the "id" corTax.
     *
     * @param id the id of the corTaxDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the corTaxDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cor-taxes/{id}")
    @Timed
    public ResponseEntity<CorTaxDTO> getCorTax(@PathVariable Long id) {
        log.debug("REST request to get CorTax : {}", id);
        CorTax corTax = corTaxRepository.findOne(id);
        CorTaxDTO corTaxDTO = corTaxMapper.corTaxToCorTaxDTO(corTax);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(corTaxDTO));
    }

    /**
     * DELETE  /cor-taxes/:id : delete the "id" corTax.
     *
     * @param id the id of the corTaxDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cor-taxes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorTax(@PathVariable Long id) {
        log.debug("REST request to delete CorTax : {}", id);
        corTaxRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
