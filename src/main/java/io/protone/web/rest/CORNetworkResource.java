package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CORNetwork;

import io.protone.repository.CORNetworkRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CORNetworkDTO;
import io.protone.service.mapper.CORNetworkMapper;

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
 * REST controller for managing CORNetwork.
 */
@RestController
@RequestMapping("/api")
public class CORNetworkResource {

    private final Logger log = LoggerFactory.getLogger(CORNetworkResource.class);
        
    @Inject
    private CORNetworkRepository cORNetworkRepository;

    @Inject
    private CORNetworkMapper cORNetworkMapper;

    /**
     * POST  /c-or-networks : Create a new cORNetwork.
     *
     * @param cORNetworkDTO the cORNetworkDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cORNetworkDTO, or with status 400 (Bad Request) if the cORNetwork has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-or-networks")
    @Timed
    public ResponseEntity<CORNetworkDTO> createCORNetwork(@Valid @RequestBody CORNetworkDTO cORNetworkDTO) throws URISyntaxException {
        log.debug("REST request to save CORNetwork : {}", cORNetworkDTO);
        if (cORNetworkDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORNetwork", "idexists", "A new cORNetwork cannot already have an ID")).body(null);
        }
        CORNetwork cORNetwork = cORNetworkMapper.cORNetworkDTOToCORNetwork(cORNetworkDTO);
        cORNetwork = cORNetworkRepository.save(cORNetwork);
        CORNetworkDTO result = cORNetworkMapper.cORNetworkToCORNetworkDTO(cORNetwork);
        return ResponseEntity.created(new URI("/api/c-or-networks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cORNetwork", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-or-networks : Updates an existing cORNetwork.
     *
     * @param cORNetworkDTO the cORNetworkDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cORNetworkDTO,
     * or with status 400 (Bad Request) if the cORNetworkDTO is not valid,
     * or with status 500 (Internal Server Error) if the cORNetworkDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-or-networks")
    @Timed
    public ResponseEntity<CORNetworkDTO> updateCORNetwork(@Valid @RequestBody CORNetworkDTO cORNetworkDTO) throws URISyntaxException {
        log.debug("REST request to update CORNetwork : {}", cORNetworkDTO);
        if (cORNetworkDTO.getId() == null) {
            return createCORNetwork(cORNetworkDTO);
        }
        CORNetwork cORNetwork = cORNetworkMapper.cORNetworkDTOToCORNetwork(cORNetworkDTO);
        cORNetwork = cORNetworkRepository.save(cORNetwork);
        CORNetworkDTO result = cORNetworkMapper.cORNetworkToCORNetworkDTO(cORNetwork);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORNetwork", cORNetworkDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-or-networks : get all the cORNetworks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cORNetworks in body
     */
    @GetMapping("/c-or-networks")
    @Timed
    public List<CORNetworkDTO> getAllCORNetworks() {
        log.debug("REST request to get all CORNetworks");
        List<CORNetwork> cORNetworks = cORNetworkRepository.findAll();
        return cORNetworkMapper.cORNetworksToCORNetworkDTOs(cORNetworks);
    }

    /**
     * GET  /c-or-networks/:id : get the "id" cORNetwork.
     *
     * @param id the id of the cORNetworkDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cORNetworkDTO, or with status 404 (Not Found)
     */
    @GetMapping("/c-or-networks/{id}")
    @Timed
    public ResponseEntity<CORNetworkDTO> getCORNetwork(@PathVariable Long id) {
        log.debug("REST request to get CORNetwork : {}", id);
        CORNetwork cORNetwork = cORNetworkRepository.findOne(id);
        CORNetworkDTO cORNetworkDTO = cORNetworkMapper.cORNetworkToCORNetworkDTO(cORNetwork);
        return Optional.ofNullable(cORNetworkDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-or-networks/:id : delete the "id" cORNetwork.
     *
     * @param id the id of the cORNetworkDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-or-networks/{id}")
    @Timed
    public ResponseEntity<Void> deleteCORNetwork(@PathVariable Long id) {
        log.debug("REST request to delete CORNetwork : {}", id);
        cORNetworkRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORNetwork", id.toString())).build();
    }

}
