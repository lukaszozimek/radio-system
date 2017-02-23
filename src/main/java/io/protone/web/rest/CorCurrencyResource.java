package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CorCurrency;

import io.protone.repository.CorCurrencyRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CorCurrencyDTO;
import io.protone.service.mapper.CorCurrencyMapper;
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
 * REST controller for managing CorCurrency.
 */
@RestController
@RequestMapping("/api")
public class CorCurrencyResource {

    private final Logger log = LoggerFactory.getLogger(CorCurrencyResource.class);

    private static final String ENTITY_NAME = "corCurrency";
        
    private final CorCurrencyRepository corCurrencyRepository;

    private final CorCurrencyMapper corCurrencyMapper;

    public CorCurrencyResource(CorCurrencyRepository corCurrencyRepository, CorCurrencyMapper corCurrencyMapper) {
        this.corCurrencyRepository = corCurrencyRepository;
        this.corCurrencyMapper = corCurrencyMapper;
    }

    /**
     * POST  /cor-currencies : Create a new corCurrency.
     *
     * @param corCurrencyDTO the corCurrencyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new corCurrencyDTO, or with status 400 (Bad Request) if the corCurrency has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cor-currencies")
    @Timed
    public ResponseEntity<CorCurrencyDTO> createCorCurrency(@RequestBody CorCurrencyDTO corCurrencyDTO) throws URISyntaxException {
        log.debug("REST request to save CorCurrency : {}", corCurrencyDTO);
        if (corCurrencyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new corCurrency cannot already have an ID")).body(null);
        }
        CorCurrency corCurrency = corCurrencyMapper.corCurrencyDTOToCorCurrency(corCurrencyDTO);
        corCurrency = corCurrencyRepository.save(corCurrency);
        CorCurrencyDTO result = corCurrencyMapper.corCurrencyToCorCurrencyDTO(corCurrency);
        return ResponseEntity.created(new URI("/api/cor-currencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cor-currencies : Updates an existing corCurrency.
     *
     * @param corCurrencyDTO the corCurrencyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated corCurrencyDTO,
     * or with status 400 (Bad Request) if the corCurrencyDTO is not valid,
     * or with status 500 (Internal Server Error) if the corCurrencyDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cor-currencies")
    @Timed
    public ResponseEntity<CorCurrencyDTO> updateCorCurrency(@RequestBody CorCurrencyDTO corCurrencyDTO) throws URISyntaxException {
        log.debug("REST request to update CorCurrency : {}", corCurrencyDTO);
        if (corCurrencyDTO.getId() == null) {
            return createCorCurrency(corCurrencyDTO);
        }
        CorCurrency corCurrency = corCurrencyMapper.corCurrencyDTOToCorCurrency(corCurrencyDTO);
        corCurrency = corCurrencyRepository.save(corCurrency);
        CorCurrencyDTO result = corCurrencyMapper.corCurrencyToCorCurrencyDTO(corCurrency);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, corCurrencyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cor-currencies : get all the corCurrencies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of corCurrencies in body
     */
    @GetMapping("/cor-currencies")
    @Timed
    public List<CorCurrencyDTO> getAllCorCurrencies() {
        log.debug("REST request to get all CorCurrencies");
        List<CorCurrency> corCurrencies = corCurrencyRepository.findAll();
        return corCurrencyMapper.corCurrenciesToCorCurrencyDTOs(corCurrencies);
    }

    /**
     * GET  /cor-currencies/:id : get the "id" corCurrency.
     *
     * @param id the id of the corCurrencyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the corCurrencyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cor-currencies/{id}")
    @Timed
    public ResponseEntity<CorCurrencyDTO> getCorCurrency(@PathVariable Long id) {
        log.debug("REST request to get CorCurrency : {}", id);
        CorCurrency corCurrency = corCurrencyRepository.findOne(id);
        CorCurrencyDTO corCurrencyDTO = corCurrencyMapper.corCurrencyToCorCurrencyDTO(corCurrency);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(corCurrencyDTO));
    }

    /**
     * DELETE  /cor-currencies/:id : delete the "id" corCurrency.
     *
     * @param id the id of the corCurrencyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cor-currencies/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorCurrency(@PathVariable Long id) {
        log.debug("REST request to delete CorCurrency : {}", id);
        corCurrencyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
