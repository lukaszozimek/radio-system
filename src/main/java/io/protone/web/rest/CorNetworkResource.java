package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CorNetwork;

import io.protone.repository.CorNetworkRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CorNetworkDTO;
import io.protone.service.mapper.CorNetworkMapper;
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
 * REST controller for managing CorNetwork.
 */
@RestController
@RequestMapping("/api")
public class CorNetworkResource {

    private final Logger log = LoggerFactory.getLogger(CorNetworkResource.class);

    private static final String ENTITY_NAME = "corNetwork";

    private final CorNetworkRepository corNetworkRepository;

    private final CorNetworkMapper corNetworkMapper;

    public CorNetworkResource(CorNetworkRepository corNetworkRepository, CorNetworkMapper corNetworkMapper) {
        this.corNetworkRepository = corNetworkRepository;
        this.corNetworkMapper = corNetworkMapper;
    }

    /**
     * POST  /cor-networks : Create a new corNetwork.
     *
     * @param corNetworkDTO the corNetworkDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new corNetworkDTO, or with status 400 (Bad Request) if the corNetwork has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cor-networks")
    @Timed
    public ResponseEntity<CorNetworkDTO> createCorNetwork(@Valid @RequestBody CorNetworkDTO corNetworkDTO) throws URISyntaxException {
        log.debug("REST request to save CorNetwork : {}", corNetworkDTO);
        if (corNetworkDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new corNetwork cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkMapper.corNetworkDTOToCorNetwork(corNetworkDTO);
        corNetwork = corNetworkRepository.save(corNetwork);
        CorNetworkDTO result = corNetworkMapper.corNetworkToCorNetworkDTO(corNetwork);
        return ResponseEntity.created(new URI("/api/cor-networks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cor-networks : Updates an existing corNetwork.
     *
     * @param corNetworkDTO the corNetworkDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated corNetworkDTO,
     * or with status 400 (Bad Request) if the corNetworkDTO is not valid,
     * or with status 500 (Internal Server Error) if the corNetworkDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cor-networks")
    @Timed
    public ResponseEntity<CorNetworkDTO> updateCorNetwork(@Valid @RequestBody CorNetworkDTO corNetworkDTO) throws URISyntaxException {
        log.debug("REST request to update CorNetwork : {}", corNetworkDTO);
        if (corNetworkDTO.getId() == null) {
            return createCorNetwork(corNetworkDTO);
        }
        CorNetwork corNetwork = corNetworkMapper.corNetworkDTOToCorNetwork(corNetworkDTO);
        corNetwork = corNetworkRepository.save(corNetwork);
        CorNetworkDTO result = corNetworkMapper.corNetworkToCorNetworkDTO(corNetwork);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, corNetworkDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cor-networks : get all the corNetworks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of corNetworks in body
     */
    @GetMapping("/cor-networks")
    @Timed
    public List<CorNetworkDTO> getAllCorNetworks() {
        log.debug("REST request to get all CorNetworks");
        List<CorNetwork> corNetworks = corNetworkRepository.findAll();
        return corNetworkMapper.corNetworksToCorNetworkDTOs(corNetworks);
    }

    /**
     * GET  /cor-networks/:id : get the "id" corNetwork.
     *
     * @param id the id of the corNetworkDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the corNetworkDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cor-networks/{id}")
    @Timed
    public ResponseEntity<CorNetworkDTO> getCorNetwork(@PathVariable Long id) {
        log.debug("REST request to get CorNetwork : {}", id);
        CorNetwork corNetwork = corNetworkRepository.findOne(id);
        CorNetworkDTO corNetworkDTO = corNetworkMapper.corNetworkToCorNetworkDTO(corNetwork);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(corNetworkDTO));
    }

    /**
     * DELETE  /cor-networks/:id : delete the "id" corNetwork.
     *
     * @param id the id of the corNetworkDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cor-networks/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorNetwork(@PathVariable Long id) {
        log.debug("REST request to delete CorNetwork : {}", id);
        corNetworkRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
