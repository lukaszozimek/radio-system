package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CRMLeadStatus;

import io.protone.repository.CRMLeadStatusRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CRMLeadStatusDTO;
import io.protone.service.mapper.CRMLeadStatusMapper;

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
 * REST controller for managing CRMLeadStatus.
 */
@RestController
@RequestMapping("/api")
public class CRMLeadStatusResource {

    private final Logger log = LoggerFactory.getLogger(CRMLeadStatusResource.class);
        
    @Inject
    private CRMLeadStatusRepository cRMLeadStatusRepository;

    @Inject
    private CRMLeadStatusMapper cRMLeadStatusMapper;

    /**
     * POST  /c-rm-lead-statuses : Create a new cRMLeadStatus.
     *
     * @param cRMLeadStatusDTO the cRMLeadStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cRMLeadStatusDTO, or with status 400 (Bad Request) if the cRMLeadStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-rm-lead-statuses")
    @Timed
    public ResponseEntity<CRMLeadStatusDTO> createCRMLeadStatus(@RequestBody CRMLeadStatusDTO cRMLeadStatusDTO) throws URISyntaxException {
        log.debug("REST request to save CRMLeadStatus : {}", cRMLeadStatusDTO);
        if (cRMLeadStatusDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cRMLeadStatus", "idexists", "A new cRMLeadStatus cannot already have an ID")).body(null);
        }
        CRMLeadStatus cRMLeadStatus = cRMLeadStatusMapper.cRMLeadStatusDTOToCRMLeadStatus(cRMLeadStatusDTO);
        cRMLeadStatus = cRMLeadStatusRepository.save(cRMLeadStatus);
        CRMLeadStatusDTO result = cRMLeadStatusMapper.cRMLeadStatusToCRMLeadStatusDTO(cRMLeadStatus);
        return ResponseEntity.created(new URI("/api/c-rm-lead-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cRMLeadStatus", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-rm-lead-statuses : Updates an existing cRMLeadStatus.
     *
     * @param cRMLeadStatusDTO the cRMLeadStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cRMLeadStatusDTO,
     * or with status 400 (Bad Request) if the cRMLeadStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the cRMLeadStatusDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-rm-lead-statuses")
    @Timed
    public ResponseEntity<CRMLeadStatusDTO> updateCRMLeadStatus(@RequestBody CRMLeadStatusDTO cRMLeadStatusDTO) throws URISyntaxException {
        log.debug("REST request to update CRMLeadStatus : {}", cRMLeadStatusDTO);
        if (cRMLeadStatusDTO.getId() == null) {
            return createCRMLeadStatus(cRMLeadStatusDTO);
        }
        CRMLeadStatus cRMLeadStatus = cRMLeadStatusMapper.cRMLeadStatusDTOToCRMLeadStatus(cRMLeadStatusDTO);
        cRMLeadStatus = cRMLeadStatusRepository.save(cRMLeadStatus);
        CRMLeadStatusDTO result = cRMLeadStatusMapper.cRMLeadStatusToCRMLeadStatusDTO(cRMLeadStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cRMLeadStatus", cRMLeadStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-rm-lead-statuses : get all the cRMLeadStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cRMLeadStatuses in body
     */
    @GetMapping("/c-rm-lead-statuses")
    @Timed
    public List<CRMLeadStatusDTO> getAllCRMLeadStatuses() {
        log.debug("REST request to get all CRMLeadStatuses");
        List<CRMLeadStatus> cRMLeadStatuses = cRMLeadStatusRepository.findAll();
        return cRMLeadStatusMapper.cRMLeadStatusesToCRMLeadStatusDTOs(cRMLeadStatuses);
    }

    /**
     * GET  /c-rm-lead-statuses/:id : get the "id" cRMLeadStatus.
     *
     * @param id the id of the cRMLeadStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cRMLeadStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/c-rm-lead-statuses/{id}")
    @Timed
    public ResponseEntity<CRMLeadStatusDTO> getCRMLeadStatus(@PathVariable Long id) {
        log.debug("REST request to get CRMLeadStatus : {}", id);
        CRMLeadStatus cRMLeadStatus = cRMLeadStatusRepository.findOne(id);
        CRMLeadStatusDTO cRMLeadStatusDTO = cRMLeadStatusMapper.cRMLeadStatusToCRMLeadStatusDTO(cRMLeadStatus);
        return Optional.ofNullable(cRMLeadStatusDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-rm-lead-statuses/:id : delete the "id" cRMLeadStatus.
     *
     * @param id the id of the cRMLeadStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-rm-lead-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCRMLeadStatus(@PathVariable Long id) {
        log.debug("REST request to delete CRMLeadStatus : {}", id);
        cRMLeadStatusRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cRMLeadStatus", id.toString())).build();
    }

}
