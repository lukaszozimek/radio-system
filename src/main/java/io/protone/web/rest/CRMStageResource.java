package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CRMStage;

import io.protone.repository.CRMStageRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CRMStageDTO;
import io.protone.service.mapper.CRMStageMapper;

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
 * REST controller for managing CRMStage.
 */
@RestController
@RequestMapping("/api")
public class CRMStageResource {

    private final Logger log = LoggerFactory.getLogger(CRMStageResource.class);
        
    @Inject
    private CRMStageRepository cRMStageRepository;

    @Inject
    private CRMStageMapper cRMStageMapper;

    /**
     * POST  /c-rm-stages : Create a new cRMStage.
     *
     * @param cRMStageDTO the cRMStageDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cRMStageDTO, or with status 400 (Bad Request) if the cRMStage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-rm-stages")
    @Timed
    public ResponseEntity<CRMStageDTO> createCRMStage(@RequestBody CRMStageDTO cRMStageDTO) throws URISyntaxException {
        log.debug("REST request to save CRMStage : {}", cRMStageDTO);
        if (cRMStageDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cRMStage", "idexists", "A new cRMStage cannot already have an ID")).body(null);
        }
        CRMStage cRMStage = cRMStageMapper.cRMStageDTOToCRMStage(cRMStageDTO);
        cRMStage = cRMStageRepository.save(cRMStage);
        CRMStageDTO result = cRMStageMapper.cRMStageToCRMStageDTO(cRMStage);
        return ResponseEntity.created(new URI("/api/c-rm-stages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cRMStage", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-rm-stages : Updates an existing cRMStage.
     *
     * @param cRMStageDTO the cRMStageDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cRMStageDTO,
     * or with status 400 (Bad Request) if the cRMStageDTO is not valid,
     * or with status 500 (Internal Server Error) if the cRMStageDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-rm-stages")
    @Timed
    public ResponseEntity<CRMStageDTO> updateCRMStage(@RequestBody CRMStageDTO cRMStageDTO) throws URISyntaxException {
        log.debug("REST request to update CRMStage : {}", cRMStageDTO);
        if (cRMStageDTO.getId() == null) {
            return createCRMStage(cRMStageDTO);
        }
        CRMStage cRMStage = cRMStageMapper.cRMStageDTOToCRMStage(cRMStageDTO);
        cRMStage = cRMStageRepository.save(cRMStage);
        CRMStageDTO result = cRMStageMapper.cRMStageToCRMStageDTO(cRMStage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cRMStage", cRMStageDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-rm-stages : get all the cRMStages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cRMStages in body
     */
    @GetMapping("/c-rm-stages")
    @Timed
    public List<CRMStageDTO> getAllCRMStages() {
        log.debug("REST request to get all CRMStages");
        List<CRMStage> cRMStages = cRMStageRepository.findAll();
        return cRMStageMapper.cRMStagesToCRMStageDTOs(cRMStages);
    }

    /**
     * GET  /c-rm-stages/:id : get the "id" cRMStage.
     *
     * @param id the id of the cRMStageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cRMStageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/c-rm-stages/{id}")
    @Timed
    public ResponseEntity<CRMStageDTO> getCRMStage(@PathVariable Long id) {
        log.debug("REST request to get CRMStage : {}", id);
        CRMStage cRMStage = cRMStageRepository.findOne(id);
        CRMStageDTO cRMStageDTO = cRMStageMapper.cRMStageToCRMStageDTO(cRMStage);
        return Optional.ofNullable(cRMStageDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-rm-stages/:id : delete the "id" cRMStage.
     *
     * @param id the id of the cRMStageDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-rm-stages/{id}")
    @Timed
    public ResponseEntity<Void> deleteCRMStage(@PathVariable Long id) {
        log.debug("REST request to delete CRMStage : {}", id);
        cRMStageRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cRMStage", id.toString())).build();
    }

}
