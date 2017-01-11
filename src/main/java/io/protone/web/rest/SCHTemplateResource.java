package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.SCHTemplate;

import io.protone.repository.SCHTemplateRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.SCHTemplateDTO;
import io.protone.service.mapper.SCHTemplateMapper;

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
 * REST controller for managing SCHTemplate.
 */
@RestController
@RequestMapping("/api")
public class SCHTemplateResource {

    private final Logger log = LoggerFactory.getLogger(SCHTemplateResource.class);
        
    @Inject
    private SCHTemplateRepository sCHTemplateRepository;

    @Inject
    private SCHTemplateMapper sCHTemplateMapper;

    /**
     * POST  /s-ch-templates : Create a new sCHTemplate.
     *
     * @param sCHTemplateDTO the sCHTemplateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sCHTemplateDTO, or with status 400 (Bad Request) if the sCHTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/s-ch-templates")
    @Timed
    public ResponseEntity<SCHTemplateDTO> createSCHTemplate(@Valid @RequestBody SCHTemplateDTO sCHTemplateDTO) throws URISyntaxException {
        log.debug("REST request to save SCHTemplate : {}", sCHTemplateDTO);
        if (sCHTemplateDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sCHTemplate", "idexists", "A new sCHTemplate cannot already have an ID")).body(null);
        }
        SCHTemplate sCHTemplate = sCHTemplateMapper.sCHTemplateDTOToSCHTemplate(sCHTemplateDTO);
        sCHTemplate = sCHTemplateRepository.save(sCHTemplate);
        SCHTemplateDTO result = sCHTemplateMapper.sCHTemplateToSCHTemplateDTO(sCHTemplate);
        return ResponseEntity.created(new URI("/api/s-ch-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sCHTemplate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /s-ch-templates : Updates an existing sCHTemplate.
     *
     * @param sCHTemplateDTO the sCHTemplateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sCHTemplateDTO,
     * or with status 400 (Bad Request) if the sCHTemplateDTO is not valid,
     * or with status 500 (Internal Server Error) if the sCHTemplateDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/s-ch-templates")
    @Timed
    public ResponseEntity<SCHTemplateDTO> updateSCHTemplate(@Valid @RequestBody SCHTemplateDTO sCHTemplateDTO) throws URISyntaxException {
        log.debug("REST request to update SCHTemplate : {}", sCHTemplateDTO);
        if (sCHTemplateDTO.getId() == null) {
            return createSCHTemplate(sCHTemplateDTO);
        }
        SCHTemplate sCHTemplate = sCHTemplateMapper.sCHTemplateDTOToSCHTemplate(sCHTemplateDTO);
        sCHTemplate = sCHTemplateRepository.save(sCHTemplate);
        SCHTemplateDTO result = sCHTemplateMapper.sCHTemplateToSCHTemplateDTO(sCHTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sCHTemplate", sCHTemplateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /s-ch-templates : get all the sCHTemplates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sCHTemplates in body
     */
    @GetMapping("/s-ch-templates")
    @Timed
    public List<SCHTemplateDTO> getAllSCHTemplates() {
        log.debug("REST request to get all SCHTemplates");
        List<SCHTemplate> sCHTemplates = sCHTemplateRepository.findAll();
        return sCHTemplateMapper.sCHTemplatesToSCHTemplateDTOs(sCHTemplates);
    }

    /**
     * GET  /s-ch-templates/:id : get the "id" sCHTemplate.
     *
     * @param id the id of the sCHTemplateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sCHTemplateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/s-ch-templates/{id}")
    @Timed
    public ResponseEntity<SCHTemplateDTO> getSCHTemplate(@PathVariable Long id) {
        log.debug("REST request to get SCHTemplate : {}", id);
        SCHTemplate sCHTemplate = sCHTemplateRepository.findOne(id);
        SCHTemplateDTO sCHTemplateDTO = sCHTemplateMapper.sCHTemplateToSCHTemplateDTO(sCHTemplate);
        return Optional.ofNullable(sCHTemplateDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /s-ch-templates/:id : delete the "id" sCHTemplate.
     *
     * @param id the id of the sCHTemplateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/s-ch-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteSCHTemplate(@PathVariable Long id) {
        log.debug("REST request to delete SCHTemplate : {}", id);
        sCHTemplateRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sCHTemplate", id.toString())).build();
    }

}
