package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CorCountry;

import io.protone.repository.CorCountryRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CorCountryDTO;
import io.protone.service.mapper.CorCountryMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing CorCountry.
 */
@RestController
@RequestMapping("/api")
public class CorCountryResource {

    private final Logger log = LoggerFactory.getLogger(CorCountryResource.class);

    private static final String ENTITY_NAME = "corCountry";
        
    private final CorCountryRepository corCountryRepository;

    private final CorCountryMapper corCountryMapper;

    public CorCountryResource(CorCountryRepository corCountryRepository, CorCountryMapper corCountryMapper) {
        this.corCountryRepository = corCountryRepository;
        this.corCountryMapper = corCountryMapper;
    }

    /**
     * POST  /cor-countries : Create a new corCountry.
     *
     * @param corCountryDTO the corCountryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new corCountryDTO, or with status 400 (Bad Request) if the corCountry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cor-countries")
    @Timed
    public ResponseEntity<CorCountryDTO> createCorCountry(@RequestBody CorCountryDTO corCountryDTO) throws URISyntaxException {
        log.debug("REST request to save CorCountry : {}", corCountryDTO);
        if (corCountryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new corCountry cannot already have an ID")).body(null);
        }
        CorCountry corCountry = corCountryMapper.corCountryDTOToCorCountry(corCountryDTO);
        corCountry = corCountryRepository.save(corCountry);
        CorCountryDTO result = corCountryMapper.corCountryToCorCountryDTO(corCountry);
        return ResponseEntity.created(new URI("/api/cor-countries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cor-countries : Updates an existing corCountry.
     *
     * @param corCountryDTO the corCountryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated corCountryDTO,
     * or with status 400 (Bad Request) if the corCountryDTO is not valid,
     * or with status 500 (Internal Server Error) if the corCountryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cor-countries")
    @Timed
    public ResponseEntity<CorCountryDTO> updateCorCountry(@RequestBody CorCountryDTO corCountryDTO) throws URISyntaxException {
        log.debug("REST request to update CorCountry : {}", corCountryDTO);
        if (corCountryDTO.getId() == null) {
            return createCorCountry(corCountryDTO);
        }
        CorCountry corCountry = corCountryMapper.corCountryDTOToCorCountry(corCountryDTO);
        corCountry = corCountryRepository.save(corCountry);
        CorCountryDTO result = corCountryMapper.corCountryToCorCountryDTO(corCountry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, corCountryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cor-countries : get all the corCountries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of corCountries in body
     */
    @GetMapping("/cor-countries")
    @Timed
    public List<CorCountryDTO> getAllCorCountries() {
        log.debug("REST request to get all CorCountries");
        List<CorCountry> corCountries = corCountryRepository.findAll();
        return corCountryMapper.corCountriesToCorCountryDTOs(corCountries);
    }

    /**
     * GET  /cor-countries/:id : get the "id" corCountry.
     *
     * @param id the id of the corCountryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the corCountryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cor-countries/{id}")
    @Timed
    public ResponseEntity<CorCountryDTO> getCorCountry(@PathVariable Long id) {
        log.debug("REST request to get CorCountry : {}", id);
        CorCountry corCountry = corCountryRepository.findOne(id);
        CorCountryDTO corCountryDTO = corCountryMapper.corCountryToCorCountryDTO(corCountry);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(corCountryDTO));
    }

    /**
     * DELETE  /cor-countries/:id : delete the "id" corCountry.
     *
     * @param id the id of the corCountryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cor-countries/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorCountry(@PathVariable Long id) {
        log.debug("REST request to delete CorCountry : {}", id);
        corCountryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
