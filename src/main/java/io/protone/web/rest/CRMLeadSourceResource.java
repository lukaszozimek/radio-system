package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CRMLeadSource;

import io.protone.repository.CRMLeadSourceRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CRMLeadSourceDTO;
import io.protone.service.mapper.CRMLeadSourceMapper;

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
 * REST controller for managing CRMLeadSource.
 */
@RestController
@RequestMapping("/api")
public class CRMLeadSourceResource {

    private final Logger log = LoggerFactory.getLogger(CRMLeadSourceResource.class);
        
    @Inject
    private CRMLeadSourceRepository cRMLeadSourceRepository;

    @Inject
    private CRMLeadSourceMapper cRMLeadSourceMapper;

    /**
     * POST  /c-rm-lead-sources : Create a new cRMLeadSource.
     *
     * @param cRMLeadSourceDTO the cRMLeadSourceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cRMLeadSourceDTO, or with status 400 (Bad Request) if the cRMLeadSource has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-rm-lead-sources")
    @Timed
    public ResponseEntity<CRMLeadSourceDTO> createCRMLeadSource(@RequestBody CRMLeadSourceDTO cRMLeadSourceDTO) throws URISyntaxException {
        log.debug("REST request to save CRMLeadSource : {}", cRMLeadSourceDTO);
        if (cRMLeadSourceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cRMLeadSource", "idexists", "A new cRMLeadSource cannot already have an ID")).body(null);
        }
        CRMLeadSource cRMLeadSource = cRMLeadSourceMapper.cRMLeadSourceDTOToCRMLeadSource(cRMLeadSourceDTO);
        cRMLeadSource = cRMLeadSourceRepository.save(cRMLeadSource);
        CRMLeadSourceDTO result = cRMLeadSourceMapper.cRMLeadSourceToCRMLeadSourceDTO(cRMLeadSource);
        return ResponseEntity.created(new URI("/api/c-rm-lead-sources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cRMLeadSource", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-rm-lead-sources : Updates an existing cRMLeadSource.
     *
     * @param cRMLeadSourceDTO the cRMLeadSourceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cRMLeadSourceDTO,
     * or with status 400 (Bad Request) if the cRMLeadSourceDTO is not valid,
     * or with status 500 (Internal Server Error) if the cRMLeadSourceDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-rm-lead-sources")
    @Timed
    public ResponseEntity<CRMLeadSourceDTO> updateCRMLeadSource(@RequestBody CRMLeadSourceDTO cRMLeadSourceDTO) throws URISyntaxException {
        log.debug("REST request to update CRMLeadSource : {}", cRMLeadSourceDTO);
        if (cRMLeadSourceDTO.getId() == null) {
            return createCRMLeadSource(cRMLeadSourceDTO);
        }
        CRMLeadSource cRMLeadSource = cRMLeadSourceMapper.cRMLeadSourceDTOToCRMLeadSource(cRMLeadSourceDTO);
        cRMLeadSource = cRMLeadSourceRepository.save(cRMLeadSource);
        CRMLeadSourceDTO result = cRMLeadSourceMapper.cRMLeadSourceToCRMLeadSourceDTO(cRMLeadSource);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cRMLeadSource", cRMLeadSourceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-rm-lead-sources : get all the cRMLeadSources.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cRMLeadSources in body
     */
    @GetMapping("/c-rm-lead-sources")
    @Timed
    public List<CRMLeadSourceDTO> getAllCRMLeadSources() {
        log.debug("REST request to get all CRMLeadSources");
        List<CRMLeadSource> cRMLeadSources = cRMLeadSourceRepository.findAll();
        return cRMLeadSourceMapper.cRMLeadSourcesToCRMLeadSourceDTOs(cRMLeadSources);
    }

    /**
     * GET  /c-rm-lead-sources/:id : get the "id" cRMLeadSource.
     *
     * @param id the id of the cRMLeadSourceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cRMLeadSourceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/c-rm-lead-sources/{id}")
    @Timed
    public ResponseEntity<CRMLeadSourceDTO> getCRMLeadSource(@PathVariable Long id) {
        log.debug("REST request to get CRMLeadSource : {}", id);
        CRMLeadSource cRMLeadSource = cRMLeadSourceRepository.findOne(id);
        CRMLeadSourceDTO cRMLeadSourceDTO = cRMLeadSourceMapper.cRMLeadSourceToCRMLeadSourceDTO(cRMLeadSource);
        return Optional.ofNullable(cRMLeadSourceDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-rm-lead-sources/:id : delete the "id" cRMLeadSource.
     *
     * @param id the id of the cRMLeadSourceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-rm-lead-sources/{id}")
    @Timed
    public ResponseEntity<Void> deleteCRMLeadSource(@PathVariable Long id) {
        log.debug("REST request to delete CRMLeadSource : {}", id);
        cRMLeadSourceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cRMLeadSource", id.toString())).build();
    }

}
