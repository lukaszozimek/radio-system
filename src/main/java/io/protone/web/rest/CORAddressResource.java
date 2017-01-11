package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CORAddress;

import io.protone.repository.CORAddressRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CORAddressDTO;
import io.protone.service.mapper.CORAddressMapper;

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
 * REST controller for managing CORAddress.
 */
@RestController
@RequestMapping("/api")
public class CORAddressResource {

    private final Logger log = LoggerFactory.getLogger(CORAddressResource.class);
        
    @Inject
    private CORAddressRepository cORAddressRepository;

    @Inject
    private CORAddressMapper cORAddressMapper;

    /**
     * POST  /c-or-addresses : Create a new cORAddress.
     *
     * @param cORAddressDTO the cORAddressDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cORAddressDTO, or with status 400 (Bad Request) if the cORAddress has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-or-addresses")
    @Timed
    public ResponseEntity<CORAddressDTO> createCORAddress(@Valid @RequestBody CORAddressDTO cORAddressDTO) throws URISyntaxException {
        log.debug("REST request to save CORAddress : {}", cORAddressDTO);
        if (cORAddressDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORAddress", "idexists", "A new cORAddress cannot already have an ID")).body(null);
        }
        CORAddress cORAddress = cORAddressMapper.cORAddressDTOToCORAddress(cORAddressDTO);
        cORAddress = cORAddressRepository.save(cORAddress);
        CORAddressDTO result = cORAddressMapper.cORAddressToCORAddressDTO(cORAddress);
        return ResponseEntity.created(new URI("/api/c-or-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cORAddress", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-or-addresses : Updates an existing cORAddress.
     *
     * @param cORAddressDTO the cORAddressDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cORAddressDTO,
     * or with status 400 (Bad Request) if the cORAddressDTO is not valid,
     * or with status 500 (Internal Server Error) if the cORAddressDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-or-addresses")
    @Timed
    public ResponseEntity<CORAddressDTO> updateCORAddress(@Valid @RequestBody CORAddressDTO cORAddressDTO) throws URISyntaxException {
        log.debug("REST request to update CORAddress : {}", cORAddressDTO);
        if (cORAddressDTO.getId() == null) {
            return createCORAddress(cORAddressDTO);
        }
        CORAddress cORAddress = cORAddressMapper.cORAddressDTOToCORAddress(cORAddressDTO);
        cORAddress = cORAddressRepository.save(cORAddress);
        CORAddressDTO result = cORAddressMapper.cORAddressToCORAddressDTO(cORAddress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORAddress", cORAddressDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-or-addresses : get all the cORAddresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cORAddresses in body
     */
    @GetMapping("/c-or-addresses")
    @Timed
    public List<CORAddressDTO> getAllCORAddresses() {
        log.debug("REST request to get all CORAddresses");
        List<CORAddress> cORAddresses = cORAddressRepository.findAll();
        return cORAddressMapper.cORAddressesToCORAddressDTOs(cORAddresses);
    }

    /**
     * GET  /c-or-addresses/:id : get the "id" cORAddress.
     *
     * @param id the id of the cORAddressDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cORAddressDTO, or with status 404 (Not Found)
     */
    @GetMapping("/c-or-addresses/{id}")
    @Timed
    public ResponseEntity<CORAddressDTO> getCORAddress(@PathVariable Long id) {
        log.debug("REST request to get CORAddress : {}", id);
        CORAddress cORAddress = cORAddressRepository.findOne(id);
        CORAddressDTO cORAddressDTO = cORAddressMapper.cORAddressToCORAddressDTO(cORAddress);
        return Optional.ofNullable(cORAddressDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-or-addresses/:id : delete the "id" cORAddress.
     *
     * @param id the id of the cORAddressDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-or-addresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCORAddress(@PathVariable Long id) {
        log.debug("REST request to delete CORAddress : {}", id);
        cORAddressRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORAddress", id.toString())).build();
    }

}
