package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CORAssociation;

import io.protone.repository.CORAssociationRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CORAssociationDTO;
import io.protone.service.mapper.CORAssociationMapper;

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
 * REST controller for managing CORAssociation.
 */
@RestController
@RequestMapping("/api")
public class CORAssociationResource {

    private final Logger log = LoggerFactory.getLogger(CORAssociationResource.class);
        
    @Inject
    private CORAssociationRepository cORAssociationRepository;

    @Inject
    private CORAssociationMapper cORAssociationMapper;

    /**
     * POST  /c-or-associations : Create a new cORAssociation.
     *
     * @param cORAssociationDTO the cORAssociationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cORAssociationDTO, or with status 400 (Bad Request) if the cORAssociation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-or-associations")
    @Timed
    public ResponseEntity<CORAssociationDTO> createCORAssociation(@Valid @RequestBody CORAssociationDTO cORAssociationDTO) throws URISyntaxException {
        log.debug("REST request to save CORAssociation : {}", cORAssociationDTO);
        if (cORAssociationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORAssociation", "idexists", "A new cORAssociation cannot already have an ID")).body(null);
        }
        CORAssociation cORAssociation = cORAssociationMapper.cORAssociationDTOToCORAssociation(cORAssociationDTO);
        cORAssociation = cORAssociationRepository.save(cORAssociation);
        CORAssociationDTO result = cORAssociationMapper.cORAssociationToCORAssociationDTO(cORAssociation);
        return ResponseEntity.created(new URI("/api/c-or-associations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cORAssociation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-or-associations : Updates an existing cORAssociation.
     *
     * @param cORAssociationDTO the cORAssociationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cORAssociationDTO,
     * or with status 400 (Bad Request) if the cORAssociationDTO is not valid,
     * or with status 500 (Internal Server Error) if the cORAssociationDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-or-associations")
    @Timed
    public ResponseEntity<CORAssociationDTO> updateCORAssociation(@Valid @RequestBody CORAssociationDTO cORAssociationDTO) throws URISyntaxException {
        log.debug("REST request to update CORAssociation : {}", cORAssociationDTO);
        if (cORAssociationDTO.getId() == null) {
            return createCORAssociation(cORAssociationDTO);
        }
        CORAssociation cORAssociation = cORAssociationMapper.cORAssociationDTOToCORAssociation(cORAssociationDTO);
        cORAssociation = cORAssociationRepository.save(cORAssociation);
        CORAssociationDTO result = cORAssociationMapper.cORAssociationToCORAssociationDTO(cORAssociation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORAssociation", cORAssociationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-or-associations : get all the cORAssociations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cORAssociations in body
     */
    @GetMapping("/c-or-associations")
    @Timed
    public List<CORAssociationDTO> getAllCORAssociations() {
        log.debug("REST request to get all CORAssociations");
        List<CORAssociation> cORAssociations = cORAssociationRepository.findAll();
        return cORAssociationMapper.cORAssociationsToCORAssociationDTOs(cORAssociations);
    }

    /**
     * GET  /c-or-associations/:id : get the "id" cORAssociation.
     *
     * @param id the id of the cORAssociationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cORAssociationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/c-or-associations/{id}")
    @Timed
    public ResponseEntity<CORAssociationDTO> getCORAssociation(@PathVariable Long id) {
        log.debug("REST request to get CORAssociation : {}", id);
        CORAssociation cORAssociation = cORAssociationRepository.findOne(id);
        CORAssociationDTO cORAssociationDTO = cORAssociationMapper.cORAssociationToCORAssociationDTO(cORAssociation);
        return Optional.ofNullable(cORAssociationDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-or-associations/:id : delete the "id" cORAssociation.
     *
     * @param id the id of the cORAssociationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-or-associations/{id}")
    @Timed
    public ResponseEntity<Void> deleteCORAssociation(@PathVariable Long id) {
        log.debug("REST request to delete CORAssociation : {}", id);
        cORAssociationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORAssociation", id.toString())).build();
    }

}
