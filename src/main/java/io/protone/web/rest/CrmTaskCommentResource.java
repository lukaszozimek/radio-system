package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CrmTaskComment;

import io.protone.repository.CrmTaskCommentRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CrmTaskCommentDTO;
import io.protone.service.mapper.CrmTaskCommentMapper;
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
 * REST controller for managing CrmTaskComment.
 */
@RestController
@RequestMapping("/api")
public class CrmTaskCommentResource {

    private final Logger log = LoggerFactory.getLogger(CrmTaskCommentResource.class);

    private static final String ENTITY_NAME = "crmTaskComment";
        
    private final CrmTaskCommentRepository crmTaskCommentRepository;

    private final CrmTaskCommentMapper crmTaskCommentMapper;

    public CrmTaskCommentResource(CrmTaskCommentRepository crmTaskCommentRepository, CrmTaskCommentMapper crmTaskCommentMapper) {
        this.crmTaskCommentRepository = crmTaskCommentRepository;
        this.crmTaskCommentMapper = crmTaskCommentMapper;
    }

    /**
     * POST  /crm-task-comments : Create a new crmTaskComment.
     *
     * @param crmTaskCommentDTO the crmTaskCommentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new crmTaskCommentDTO, or with status 400 (Bad Request) if the crmTaskComment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/crm-task-comments")
    @Timed
    public ResponseEntity<CrmTaskCommentDTO> createCrmTaskComment(@RequestBody CrmTaskCommentDTO crmTaskCommentDTO) throws URISyntaxException {
        log.debug("REST request to save CrmTaskComment : {}", crmTaskCommentDTO);
        if (crmTaskCommentDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new crmTaskComment cannot already have an ID")).body(null);
        }
        CrmTaskComment crmTaskComment = crmTaskCommentMapper.crmTaskCommentDTOToCrmTaskComment(crmTaskCommentDTO);
        crmTaskComment = crmTaskCommentRepository.save(crmTaskComment);
        CrmTaskCommentDTO result = crmTaskCommentMapper.crmTaskCommentToCrmTaskCommentDTO(crmTaskComment);
        return ResponseEntity.created(new URI("/api/crm-task-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /crm-task-comments : Updates an existing crmTaskComment.
     *
     * @param crmTaskCommentDTO the crmTaskCommentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated crmTaskCommentDTO,
     * or with status 400 (Bad Request) if the crmTaskCommentDTO is not valid,
     * or with status 500 (Internal Server Error) if the crmTaskCommentDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/crm-task-comments")
    @Timed
    public ResponseEntity<CrmTaskCommentDTO> updateCrmTaskComment(@RequestBody CrmTaskCommentDTO crmTaskCommentDTO) throws URISyntaxException {
        log.debug("REST request to update CrmTaskComment : {}", crmTaskCommentDTO);
        if (crmTaskCommentDTO.getId() == null) {
            return createCrmTaskComment(crmTaskCommentDTO);
        }
        CrmTaskComment crmTaskComment = crmTaskCommentMapper.crmTaskCommentDTOToCrmTaskComment(crmTaskCommentDTO);
        crmTaskComment = crmTaskCommentRepository.save(crmTaskComment);
        CrmTaskCommentDTO result = crmTaskCommentMapper.crmTaskCommentToCrmTaskCommentDTO(crmTaskComment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, crmTaskCommentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /crm-task-comments : get all the crmTaskComments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of crmTaskComments in body
     */
    @GetMapping("/crm-task-comments")
    @Timed
    public List<CrmTaskCommentDTO> getAllCrmTaskComments() {
        log.debug("REST request to get all CrmTaskComments");
        List<CrmTaskComment> crmTaskComments = crmTaskCommentRepository.findAll();
        return crmTaskCommentMapper.crmTaskCommentsToCrmTaskCommentDTOs(crmTaskComments);
    }

    /**
     * GET  /crm-task-comments/:id : get the "id" crmTaskComment.
     *
     * @param id the id of the crmTaskCommentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the crmTaskCommentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/crm-task-comments/{id}")
    @Timed
    public ResponseEntity<CrmTaskCommentDTO> getCrmTaskComment(@PathVariable Long id) {
        log.debug("REST request to get CrmTaskComment : {}", id);
        CrmTaskComment crmTaskComment = crmTaskCommentRepository.findOne(id);
        CrmTaskCommentDTO crmTaskCommentDTO = crmTaskCommentMapper.crmTaskCommentToCrmTaskCommentDTO(crmTaskComment);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(crmTaskCommentDTO));
    }

    /**
     * DELETE  /crm-task-comments/:id : delete the "id" crmTaskComment.
     *
     * @param id the id of the crmTaskCommentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/crm-task-comments/{id}")
    @Timed
    public ResponseEntity<Void> deleteCrmTaskComment(@PathVariable Long id) {
        log.debug("REST request to delete CrmTaskComment : {}", id);
        crmTaskCommentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
