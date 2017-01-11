package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.SCHEmission;

import io.protone.repository.SCHEmissionRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.SCHEmissionDTO;
import io.protone.service.mapper.SCHEmissionMapper;

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
 * REST controller for managing SCHEmission.
 */
@RestController
@RequestMapping("/api")
public class SCHEmissionResource {

    private final Logger log = LoggerFactory.getLogger(SCHEmissionResource.class);
        
    @Inject
    private SCHEmissionRepository sCHEmissionRepository;

    @Inject
    private SCHEmissionMapper sCHEmissionMapper;

    /**
     * POST  /s-ch-emissions : Create a new sCHEmission.
     *
     * @param sCHEmissionDTO the sCHEmissionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sCHEmissionDTO, or with status 400 (Bad Request) if the sCHEmission has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/s-ch-emissions")
    @Timed
    public ResponseEntity<SCHEmissionDTO> createSCHEmission(@Valid @RequestBody SCHEmissionDTO sCHEmissionDTO) throws URISyntaxException {
        log.debug("REST request to save SCHEmission : {}", sCHEmissionDTO);
        if (sCHEmissionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sCHEmission", "idexists", "A new sCHEmission cannot already have an ID")).body(null);
        }
        SCHEmission sCHEmission = sCHEmissionMapper.sCHEmissionDTOToSCHEmission(sCHEmissionDTO);
        sCHEmission = sCHEmissionRepository.save(sCHEmission);
        SCHEmissionDTO result = sCHEmissionMapper.sCHEmissionToSCHEmissionDTO(sCHEmission);
        return ResponseEntity.created(new URI("/api/s-ch-emissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sCHEmission", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /s-ch-emissions : Updates an existing sCHEmission.
     *
     * @param sCHEmissionDTO the sCHEmissionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sCHEmissionDTO,
     * or with status 400 (Bad Request) if the sCHEmissionDTO is not valid,
     * or with status 500 (Internal Server Error) if the sCHEmissionDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/s-ch-emissions")
    @Timed
    public ResponseEntity<SCHEmissionDTO> updateSCHEmission(@Valid @RequestBody SCHEmissionDTO sCHEmissionDTO) throws URISyntaxException {
        log.debug("REST request to update SCHEmission : {}", sCHEmissionDTO);
        if (sCHEmissionDTO.getId() == null) {
            return createSCHEmission(sCHEmissionDTO);
        }
        SCHEmission sCHEmission = sCHEmissionMapper.sCHEmissionDTOToSCHEmission(sCHEmissionDTO);
        sCHEmission = sCHEmissionRepository.save(sCHEmission);
        SCHEmissionDTO result = sCHEmissionMapper.sCHEmissionToSCHEmissionDTO(sCHEmission);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sCHEmission", sCHEmissionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /s-ch-emissions : get all the sCHEmissions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sCHEmissions in body
     */
    @GetMapping("/s-ch-emissions")
    @Timed
    public List<SCHEmissionDTO> getAllSCHEmissions() {
        log.debug("REST request to get all SCHEmissions");
        List<SCHEmission> sCHEmissions = sCHEmissionRepository.findAll();
        return sCHEmissionMapper.sCHEmissionsToSCHEmissionDTOs(sCHEmissions);
    }

    /**
     * GET  /s-ch-emissions/:id : get the "id" sCHEmission.
     *
     * @param id the id of the sCHEmissionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sCHEmissionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/s-ch-emissions/{id}")
    @Timed
    public ResponseEntity<SCHEmissionDTO> getSCHEmission(@PathVariable Long id) {
        log.debug("REST request to get SCHEmission : {}", id);
        SCHEmission sCHEmission = sCHEmissionRepository.findOne(id);
        SCHEmissionDTO sCHEmissionDTO = sCHEmissionMapper.sCHEmissionToSCHEmissionDTO(sCHEmission);
        return Optional.ofNullable(sCHEmissionDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /s-ch-emissions/:id : delete the "id" sCHEmission.
     *
     * @param id the id of the sCHEmissionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/s-ch-emissions/{id}")
    @Timed
    public ResponseEntity<Void> deleteSCHEmission(@PathVariable Long id) {
        log.debug("REST request to delete SCHEmission : {}", id);
        sCHEmissionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sCHEmission", id.toString())).build();
    }

}
