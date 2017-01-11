package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CRMLead;

import io.protone.repository.CRMLeadRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CRMLeadDTO;
import io.protone.service.mapper.CRMLeadMapper;

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
 * REST controller for managing CRMLead.
 */
@RestController
@RequestMapping("/api")
public class CRMLeadResource {

    private final Logger log = LoggerFactory.getLogger(CRMLeadResource.class);
        
    @Inject
    private CRMLeadRepository cRMLeadRepository;

    @Inject
    private CRMLeadMapper cRMLeadMapper;

    /**
     * POST  /c-rm-leads : Create a new cRMLead.
     *
     * @param cRMLeadDTO the cRMLeadDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cRMLeadDTO, or with status 400 (Bad Request) if the cRMLead has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-rm-leads")
    @Timed
    public ResponseEntity<CRMLeadDTO> createCRMLead(@RequestBody CRMLeadDTO cRMLeadDTO) throws URISyntaxException {
        log.debug("REST request to save CRMLead : {}", cRMLeadDTO);
        if (cRMLeadDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cRMLead", "idexists", "A new cRMLead cannot already have an ID")).body(null);
        }
        CRMLead cRMLead = cRMLeadMapper.cRMLeadDTOToCRMLead(cRMLeadDTO);
        cRMLead = cRMLeadRepository.save(cRMLead);
        CRMLeadDTO result = cRMLeadMapper.cRMLeadToCRMLeadDTO(cRMLead);
        return ResponseEntity.created(new URI("/api/c-rm-leads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cRMLead", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-rm-leads : Updates an existing cRMLead.
     *
     * @param cRMLeadDTO the cRMLeadDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cRMLeadDTO,
     * or with status 400 (Bad Request) if the cRMLeadDTO is not valid,
     * or with status 500 (Internal Server Error) if the cRMLeadDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-rm-leads")
    @Timed
    public ResponseEntity<CRMLeadDTO> updateCRMLead(@RequestBody CRMLeadDTO cRMLeadDTO) throws URISyntaxException {
        log.debug("REST request to update CRMLead : {}", cRMLeadDTO);
        if (cRMLeadDTO.getId() == null) {
            return createCRMLead(cRMLeadDTO);
        }
        CRMLead cRMLead = cRMLeadMapper.cRMLeadDTOToCRMLead(cRMLeadDTO);
        cRMLead = cRMLeadRepository.save(cRMLead);
        CRMLeadDTO result = cRMLeadMapper.cRMLeadToCRMLeadDTO(cRMLead);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cRMLead", cRMLeadDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-rm-leads : get all the cRMLeads.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cRMLeads in body
     */
    @GetMapping("/c-rm-leads")
    @Timed
    public List<CRMLeadDTO> getAllCRMLeads() {
        log.debug("REST request to get all CRMLeads");
        List<CRMLead> cRMLeads = cRMLeadRepository.findAll();
        return cRMLeadMapper.cRMLeadsToCRMLeadDTOs(cRMLeads);
    }

    /**
     * GET  /c-rm-leads/:id : get the "id" cRMLead.
     *
     * @param id the id of the cRMLeadDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cRMLeadDTO, or with status 404 (Not Found)
     */
    @GetMapping("/c-rm-leads/{id}")
    @Timed
    public ResponseEntity<CRMLeadDTO> getCRMLead(@PathVariable Long id) {
        log.debug("REST request to get CRMLead : {}", id);
        CRMLead cRMLead = cRMLeadRepository.findOne(id);
        CRMLeadDTO cRMLeadDTO = cRMLeadMapper.cRMLeadToCRMLeadDTO(cRMLead);
        return Optional.ofNullable(cRMLeadDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-rm-leads/:id : delete the "id" cRMLead.
     *
     * @param id the id of the cRMLeadDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-rm-leads/{id}")
    @Timed
    public ResponseEntity<Void> deleteCRMLead(@PathVariable Long id) {
        log.debug("REST request to delete CRMLead : {}", id);
        cRMLeadRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cRMLead", id.toString())).build();
    }

}
