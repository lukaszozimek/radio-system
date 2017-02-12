package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.SchTemplate;

import io.protone.repository.SchTemplateRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.SchTemplateDTO;
import io.protone.service.mapper.SchTemplateMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing SchTemplate.
 */
@RestController
@RequestMapping("/api")
public class SchTemplateResource {

    private final Logger log = LoggerFactory.getLogger(SchTemplateResource.class);

    private static final String ENTITY_NAME = "schTemplate";
        
    private final SchTemplateRepository schTemplateRepository;

    private final SchTemplateMapper schTemplateMapper;

    public SchTemplateResource(SchTemplateRepository schTemplateRepository, SchTemplateMapper schTemplateMapper) {
        this.schTemplateRepository = schTemplateRepository;
        this.schTemplateMapper = schTemplateMapper;
    }

    /**
     * POST  /sch-templates : Create a new schTemplate.
     *
     * @param schTemplateDTO the schTemplateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new schTemplateDTO, or with status 400 (Bad Request) if the schTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sch-templates")
    @Timed
    public ResponseEntity<SchTemplateDTO> createSchTemplate(@Valid @RequestBody SchTemplateDTO schTemplateDTO) throws URISyntaxException {
        log.debug("REST request to save SchTemplate : {}", schTemplateDTO);
        if (schTemplateDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new schTemplate cannot already have an ID")).body(null);
        }
        SchTemplate schTemplate = schTemplateMapper.schTemplateDTOToSchTemplate(schTemplateDTO);
        schTemplate = schTemplateRepository.save(schTemplate);
        SchTemplateDTO result = schTemplateMapper.schTemplateToSchTemplateDTO(schTemplate);
        return ResponseEntity.created(new URI("/api/sch-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sch-templates : Updates an existing schTemplate.
     *
     * @param schTemplateDTO the schTemplateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated schTemplateDTO,
     * or with status 400 (Bad Request) if the schTemplateDTO is not valid,
     * or with status 500 (Internal Server Error) if the schTemplateDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sch-templates")
    @Timed
    public ResponseEntity<SchTemplateDTO> updateSchTemplate(@Valid @RequestBody SchTemplateDTO schTemplateDTO) throws URISyntaxException {
        log.debug("REST request to update SchTemplate : {}", schTemplateDTO);
        if (schTemplateDTO.getId() == null) {
            return createSchTemplate(schTemplateDTO);
        }
        SchTemplate schTemplate = schTemplateMapper.schTemplateDTOToSchTemplate(schTemplateDTO);
        schTemplate = schTemplateRepository.save(schTemplate);
        SchTemplateDTO result = schTemplateMapper.schTemplateToSchTemplateDTO(schTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, schTemplateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sch-templates : get all the schTemplates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of schTemplates in body
     */
    @GetMapping("/sch-templates")
    @Timed
    public List<SchTemplateDTO> getAllSchTemplates() {
        log.debug("REST request to get all SchTemplates");
        List<SchTemplate> schTemplates = schTemplateRepository.findAll();
        return schTemplateMapper.schTemplatesToSchTemplateDTOs(schTemplates);
    }

    /**
     * GET  /sch-templates/:id : get the "id" schTemplate.
     *
     * @param id the id of the schTemplateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the schTemplateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sch-templates/{id}")
    @Timed
    public ResponseEntity<SchTemplateDTO> getSchTemplate(@PathVariable Long id) {
        log.debug("REST request to get SchTemplate : {}", id);
        SchTemplate schTemplate = schTemplateRepository.findOne(id);
        SchTemplateDTO schTemplateDTO = schTemplateMapper.schTemplateToSchTemplateDTO(schTemplate);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(schTemplateDTO));
    }

    /**
     * DELETE  /sch-templates/:id : delete the "id" schTemplate.
     *
     * @param id the id of the schTemplateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sch-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteSchTemplate(@PathVariable Long id) {
        log.debug("REST request to delete SchTemplate : {}", id);
        schTemplateRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
