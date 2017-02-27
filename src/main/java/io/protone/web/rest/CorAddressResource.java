package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CorAddress;

import io.protone.repository.CorAddressRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CorAddressDTO;
import io.protone.service.mapper.CorAddressMapper;
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
 * REST controller for managing CorAddress.
 */
@RestController
@RequestMapping("/api")
public class CorAddressResource {

    private final Logger log = LoggerFactory.getLogger(CorAddressResource.class);

    private static final String ENTITY_NAME = "corAddress";

    private final CorAddressRepository corAddressRepository;

    private final CorAddressMapper corAddressMapper;

    public CorAddressResource(CorAddressRepository corAddressRepository, CorAddressMapper corAddressMapper) {
        this.corAddressRepository = corAddressRepository;
        this.corAddressMapper = corAddressMapper;
    }

    /**
     * POST  /cor-addresses : Create a new corAddress.
     *
     * @param corAddressDTO the corAddressDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new corAddressDTO, or with status 400 (Bad Request) if the corAddress has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cor-addresses")
    @Timed
    public ResponseEntity<CorAddressDTO> createCorAddress(@Valid @RequestBody CorAddressDTO corAddressDTO) throws URISyntaxException {
        log.debug("REST request to save CorAddress : {}", corAddressDTO);
        if (corAddressDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new corAddress cannot already have an ID")).body(null);
        }
        CorAddress corAddress = corAddressMapper.corAddressDTOToCorAddress(corAddressDTO);
        corAddress = corAddressRepository.save(corAddress);
        CorAddressDTO result = corAddressMapper.corAddressToCorAddressDTO(corAddress);
        return ResponseEntity.created(new URI("/api/cor-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cor-addresses : Updates an existing corAddress.
     *
     * @param corAddressDTO the corAddressDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated corAddressDTO,
     * or with status 400 (Bad Request) if the corAddressDTO is not valid,
     * or with status 500 (Internal Server Error) if the corAddressDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cor-addresses")
    @Timed
    public ResponseEntity<CorAddressDTO> updateCorAddress(@Valid @RequestBody CorAddressDTO corAddressDTO) throws URISyntaxException {
        log.debug("REST request to update CorAddress : {}", corAddressDTO);
        if (corAddressDTO.getId() == null) {
            return createCorAddress(corAddressDTO);
        }
        CorAddress corAddress = corAddressMapper.corAddressDTOToCorAddress(corAddressDTO);
        corAddress = corAddressRepository.save(corAddress);
        CorAddressDTO result = corAddressMapper.corAddressToCorAddressDTO(corAddress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, corAddressDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cor-addresses : get all the corAddresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of corAddresses in body
     */
    @GetMapping("/cor-addresses")
    @Timed
    public List<CorAddressDTO> getAllCorAddresses() {
        log.debug("REST request to get all CorAddresses");
        List<CorAddress> corAddresses = corAddressRepository.findAll();
        return corAddressMapper.corAddressesToCorAddressDTOs(corAddresses);
    }

    /**
     * GET  /cor-addresses/:id : get the "id" corAddress.
     *
     * @param id the id of the corAddressDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the corAddressDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cor-addresses/{id}")
    @Timed
    public ResponseEntity<CorAddressDTO> getCorAddress(@PathVariable Long id) {
        log.debug("REST request to get CorAddress : {}", id);
        CorAddress corAddress = corAddressRepository.findOne(id);
        CorAddressDTO corAddressDTO = corAddressMapper.corAddressToCorAddressDTO(corAddress);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(corAddressDTO));
    }

    /**
     * DELETE  /cor-addresses/:id : delete the "id" corAddress.
     *
     * @param id the id of the corAddressDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cor-addresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorAddress(@PathVariable Long id) {
        log.debug("REST request to delete CorAddress : {}", id);
        corAddressRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
