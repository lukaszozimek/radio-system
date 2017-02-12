package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CrmStage;

import io.protone.repository.CrmStageRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CrmStageDTO;
import io.protone.service.mapper.CrmStageMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing CrmStage.
 */
@RestController
@RequestMapping("/api")
public class CrmStageResource {

    private final Logger log = LoggerFactory.getLogger(CrmStageResource.class);

    private static final String ENTITY_NAME = "crmStage";
        
    private final CrmStageRepository crmStageRepository;

    private final CrmStageMapper crmStageMapper;

    public CrmStageResource(CrmStageRepository crmStageRepository, CrmStageMapper crmStageMapper) {
        this.crmStageRepository = crmStageRepository;
        this.crmStageMapper = crmStageMapper;
    }

    /**
     * POST  /crm-stages : Create a new crmStage.
     *
     * @param crmStageDTO the crmStageDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new crmStageDTO, or with status 400 (Bad Request) if the crmStage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/crm-stages")
    @Timed
    public ResponseEntity<CrmStageDTO> createCrmStage(@RequestBody CrmStageDTO crmStageDTO) throws URISyntaxException {
        log.debug("REST request to save CrmStage : {}", crmStageDTO);
        if (crmStageDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new crmStage cannot already have an ID")).body(null);
        }
        CrmStage crmStage = crmStageMapper.crmStageDTOToCrmStage(crmStageDTO);
        crmStage = crmStageRepository.save(crmStage);
        CrmStageDTO result = crmStageMapper.crmStageToCrmStageDTO(crmStage);
        return ResponseEntity.created(new URI("/api/crm-stages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /crm-stages : Updates an existing crmStage.
     *
     * @param crmStageDTO the crmStageDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated crmStageDTO,
     * or with status 400 (Bad Request) if the crmStageDTO is not valid,
     * or with status 500 (Internal Server Error) if the crmStageDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/crm-stages")
    @Timed
    public ResponseEntity<CrmStageDTO> updateCrmStage(@RequestBody CrmStageDTO crmStageDTO) throws URISyntaxException {
        log.debug("REST request to update CrmStage : {}", crmStageDTO);
        if (crmStageDTO.getId() == null) {
            return createCrmStage(crmStageDTO);
        }
        CrmStage crmStage = crmStageMapper.crmStageDTOToCrmStage(crmStageDTO);
        crmStage = crmStageRepository.save(crmStage);
        CrmStageDTO result = crmStageMapper.crmStageToCrmStageDTO(crmStage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, crmStageDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /crm-stages : get all the crmStages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of crmStages in body
     */
    @GetMapping("/crm-stages")
    @Timed
    public List<CrmStageDTO> getAllCrmStages() {
        log.debug("REST request to get all CrmStages");
        List<CrmStage> crmStages = crmStageRepository.findAll();
        return crmStageMapper.crmStagesToCrmStageDTOs(crmStages);
    }

    /**
     * GET  /crm-stages/:id : get the "id" crmStage.
     *
     * @param id the id of the crmStageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the crmStageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/crm-stages/{id}")
    @Timed
    public ResponseEntity<CrmStageDTO> getCrmStage(@PathVariable Long id) {
        log.debug("REST request to get CrmStage : {}", id);
        CrmStage crmStage = crmStageRepository.findOne(id);
        CrmStageDTO crmStageDTO = crmStageMapper.crmStageToCrmStageDTO(crmStage);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(crmStageDTO));
    }

    /**
     * DELETE  /crm-stages/:id : delete the "id" crmStage.
     *
     * @param id the id of the crmStageDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/crm-stages/{id}")
    @Timed
    public ResponseEntity<Void> deleteCrmStage(@PathVariable Long id) {
        log.debug("REST request to delete CrmStage : {}", id);
        crmStageRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
