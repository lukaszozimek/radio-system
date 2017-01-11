package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CRMOpportunity;

import io.protone.repository.CRMOpportunityRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CRMOpportunityDTO;
import io.protone.service.mapper.CRMOpportunityMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing CRMOpportunity.
 */
@RestController
@RequestMapping("/api")
public class CRMOpportunityResource {

    private final Logger log = LoggerFactory.getLogger(CRMOpportunityResource.class);
        
    @Inject
    private CRMOpportunityRepository cRMOpportunityRepository;

    @Inject
    private CRMOpportunityMapper cRMOpportunityMapper;

    /**
     * POST  /c-rm-opportunities : Create a new cRMOpportunity.
     *
     * @param cRMOpportunityDTO the cRMOpportunityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cRMOpportunityDTO, or with status 400 (Bad Request) if the cRMOpportunity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-rm-opportunities")
    @Timed
    public ResponseEntity<CRMOpportunityDTO> createCRMOpportunity(@RequestBody CRMOpportunityDTO cRMOpportunityDTO) throws URISyntaxException {
        log.debug("REST request to save CRMOpportunity : {}", cRMOpportunityDTO);
        if (cRMOpportunityDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cRMOpportunity", "idexists", "A new cRMOpportunity cannot already have an ID")).body(null);
        }
        CRMOpportunity cRMOpportunity = cRMOpportunityMapper.cRMOpportunityDTOToCRMOpportunity(cRMOpportunityDTO);
        cRMOpportunity = cRMOpportunityRepository.save(cRMOpportunity);
        CRMOpportunityDTO result = cRMOpportunityMapper.cRMOpportunityToCRMOpportunityDTO(cRMOpportunity);
        return ResponseEntity.created(new URI("/api/c-rm-opportunities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cRMOpportunity", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-rm-opportunities : Updates an existing cRMOpportunity.
     *
     * @param cRMOpportunityDTO the cRMOpportunityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cRMOpportunityDTO,
     * or with status 400 (Bad Request) if the cRMOpportunityDTO is not valid,
     * or with status 500 (Internal Server Error) if the cRMOpportunityDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-rm-opportunities")
    @Timed
    public ResponseEntity<CRMOpportunityDTO> updateCRMOpportunity(@RequestBody CRMOpportunityDTO cRMOpportunityDTO) throws URISyntaxException {
        log.debug("REST request to update CRMOpportunity : {}", cRMOpportunityDTO);
        if (cRMOpportunityDTO.getId() == null) {
            return createCRMOpportunity(cRMOpportunityDTO);
        }
        CRMOpportunity cRMOpportunity = cRMOpportunityMapper.cRMOpportunityDTOToCRMOpportunity(cRMOpportunityDTO);
        cRMOpportunity = cRMOpportunityRepository.save(cRMOpportunity);
        CRMOpportunityDTO result = cRMOpportunityMapper.cRMOpportunityToCRMOpportunityDTO(cRMOpportunity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cRMOpportunity", cRMOpportunityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-rm-opportunities : get all the cRMOpportunities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cRMOpportunities in body
     */
    @GetMapping("/c-rm-opportunities")
    @Timed
    public List<CRMOpportunityDTO> getAllCRMOpportunities() {
        log.debug("REST request to get all CRMOpportunities");
        List<CRMOpportunity> cRMOpportunities = cRMOpportunityRepository.findAll();
        return cRMOpportunityMapper.cRMOpportunitiesToCRMOpportunityDTOs(cRMOpportunities);
    }

    /**
     * GET  /c-rm-opportunities/:id : get the "id" cRMOpportunity.
     *
     * @param id the id of the cRMOpportunityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cRMOpportunityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/c-rm-opportunities/{id}")
    @Timed
    public ResponseEntity<CRMOpportunityDTO> getCRMOpportunity(@PathVariable Long id) {
        log.debug("REST request to get CRMOpportunity : {}", id);
        CRMOpportunity cRMOpportunity = cRMOpportunityRepository.findOne(id);
        CRMOpportunityDTO cRMOpportunityDTO = cRMOpportunityMapper.cRMOpportunityToCRMOpportunityDTO(cRMOpportunity);
        return Optional.ofNullable(cRMOpportunityDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-rm-opportunities/:id : delete the "id" cRMOpportunity.
     *
     * @param id the id of the cRMOpportunityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-rm-opportunities/{id}")
    @Timed
    public ResponseEntity<Void> deleteCRMOpportunity(@PathVariable Long id) {
        log.debug("REST request to delete CRMOpportunity : {}", id);
        cRMOpportunityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cRMOpportunity", id.toString())).build();
    }

}
