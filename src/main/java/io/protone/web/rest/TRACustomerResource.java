package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.TRACustomer;

import io.protone.repository.TRACustomerRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.TRACustomerDTO;
import io.protone.service.mapper.TRACustomerMapper;

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
 * REST controller for managing TRACustomer.
 */
@RestController
@RequestMapping("/api")
public class TRACustomerResource {

    private final Logger log = LoggerFactory.getLogger(TRACustomerResource.class);
        
    @Inject
    private TRACustomerRepository tRACustomerRepository;

    @Inject
    private TRACustomerMapper tRACustomerMapper;

    /**
     * POST  /t-ra-customers : Create a new tRACustomer.
     *
     * @param tRACustomerDTO the tRACustomerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tRACustomerDTO, or with status 400 (Bad Request) if the tRACustomer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-ra-customers")
    @Timed
    public ResponseEntity<TRACustomerDTO> createTRACustomer(@Valid @RequestBody TRACustomerDTO tRACustomerDTO) throws URISyntaxException {
        log.debug("REST request to save TRACustomer : {}", tRACustomerDTO);
        if (tRACustomerDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tRACustomer", "idexists", "A new tRACustomer cannot already have an ID")).body(null);
        }
        TRACustomer tRACustomer = tRACustomerMapper.tRACustomerDTOToTRACustomer(tRACustomerDTO);
        tRACustomer = tRACustomerRepository.save(tRACustomer);
        TRACustomerDTO result = tRACustomerMapper.tRACustomerToTRACustomerDTO(tRACustomer);
        return ResponseEntity.created(new URI("/api/t-ra-customers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tRACustomer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-ra-customers : Updates an existing tRACustomer.
     *
     * @param tRACustomerDTO the tRACustomerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tRACustomerDTO,
     * or with status 400 (Bad Request) if the tRACustomerDTO is not valid,
     * or with status 500 (Internal Server Error) if the tRACustomerDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-ra-customers")
    @Timed
    public ResponseEntity<TRACustomerDTO> updateTRACustomer(@Valid @RequestBody TRACustomerDTO tRACustomerDTO) throws URISyntaxException {
        log.debug("REST request to update TRACustomer : {}", tRACustomerDTO);
        if (tRACustomerDTO.getId() == null) {
            return createTRACustomer(tRACustomerDTO);
        }
        TRACustomer tRACustomer = tRACustomerMapper.tRACustomerDTOToTRACustomer(tRACustomerDTO);
        tRACustomer = tRACustomerRepository.save(tRACustomer);
        TRACustomerDTO result = tRACustomerMapper.tRACustomerToTRACustomerDTO(tRACustomer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tRACustomer", tRACustomerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-ra-customers : get all the tRACustomers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tRACustomers in body
     */
    @GetMapping("/t-ra-customers")
    @Timed
    public List<TRACustomerDTO> getAllTRACustomers() {
        log.debug("REST request to get all TRACustomers");
        List<TRACustomer> tRACustomers = tRACustomerRepository.findAll();
        return tRACustomerMapper.tRACustomersToTRACustomerDTOs(tRACustomers);
    }

    /**
     * GET  /t-ra-customers/:id : get the "id" tRACustomer.
     *
     * @param id the id of the tRACustomerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tRACustomerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/t-ra-customers/{id}")
    @Timed
    public ResponseEntity<TRACustomerDTO> getTRACustomer(@PathVariable Long id) {
        log.debug("REST request to get TRACustomer : {}", id);
        TRACustomer tRACustomer = tRACustomerRepository.findOne(id);
        TRACustomerDTO tRACustomerDTO = tRACustomerMapper.tRACustomerToTRACustomerDTO(tRACustomer);
        return Optional.ofNullable(tRACustomerDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /t-ra-customers/:id : delete the "id" tRACustomer.
     *
     * @param id the id of the tRACustomerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-ra-customers/{id}")
    @Timed
    public ResponseEntity<Void> deleteTRACustomer(@PathVariable Long id) {
        log.debug("REST request to delete TRACustomer : {}", id);
        tRACustomerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tRACustomer", id.toString())).build();
    }

}
