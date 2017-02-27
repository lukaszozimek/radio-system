package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CorTag;

import io.protone.repository.CorTagRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CorTagDTO;
import io.protone.service.mapper.CorTagMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CorTag.
 */
@RestController
@RequestMapping("/api")
public class CorTagResource {

    private final Logger log = LoggerFactory.getLogger(CorTagResource.class);

    private static final String ENTITY_NAME = "corTag";

    private final CorTagRepository corTagRepository;

    private final CorTagMapper corTagMapper;

    public CorTagResource(CorTagRepository corTagRepository, CorTagMapper corTagMapper) {
        this.corTagRepository = corTagRepository;
        this.corTagMapper = corTagMapper;
    }

    /**
     * POST  /cor-tags : Create a new corTag.
     *
     * @param corTagDTO the corTagDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new corTagDTO, or with status 400 (Bad Request) if the corTag has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cor-tags")
    @Timed
    public ResponseEntity<CorTagDTO> createCorTag(@Valid @RequestBody CorTagDTO corTagDTO) throws URISyntaxException {
        log.debug("REST request to save CorTag : {}", corTagDTO);
        if (corTagDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new corTag cannot already have an ID")).body(null);
        }
        CorTag corTag = corTagMapper.corTagDTOToCorTag(corTagDTO);
        corTag = corTagRepository.save(corTag);
        CorTagDTO result = corTagMapper.corTagToCorTagDTO(corTag);
        return ResponseEntity.created(new URI("/api/cor-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cor-tags : Updates an existing corTag.
     *
     * @param corTagDTO the corTagDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated corTagDTO,
     * or with status 400 (Bad Request) if the corTagDTO is not valid,
     * or with status 500 (Internal Server Error) if the corTagDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cor-tags")
    @Timed
    public ResponseEntity<CorTagDTO> updateCorTag(@Valid @RequestBody CorTagDTO corTagDTO) throws URISyntaxException {
        log.debug("REST request to update CorTag : {}", corTagDTO);
        if (corTagDTO.getId() == null) {
            return createCorTag(corTagDTO);
        }
        CorTag corTag = corTagMapper.corTagDTOToCorTag(corTagDTO);
        corTag = corTagRepository.save(corTag);
        CorTagDTO result = corTagMapper.corTagToCorTagDTO(corTag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, corTagDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cor-tags : get all the corTags.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of corTags in body
     */
    @GetMapping("/cor-tags")
    @Timed
    public List<CorTagDTO> getAllCorTags() {
        log.debug("REST request to get all CorTags");
        List<CorTag> corTags = corTagRepository.findAll();
        return corTagMapper.corTagsToCorTagDTOs(corTags);
    }

    /**
     * GET  /cor-tags/:id : get the "id" corTag.
     *
     * @param id the id of the corTagDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the corTagDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cor-tags/{id}")
    @Timed
    public ResponseEntity<CorTagDTO> getCorTag(@PathVariable Long id) {
        log.debug("REST request to get CorTag : {}", id);
        CorTag corTag = corTagRepository.findOne(id);
        CorTagDTO corTagDTO = corTagMapper.corTagToCorTagDTO(corTag);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(corTagDTO));
    }

    /**
     * DELETE  /cor-tags/:id : delete the "id" corTag.
     *
     * @param id the id of the corTagDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cor-tags/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorTag(@PathVariable Long id) {
        log.debug("REST request to delete CorTag : {}", id);
        corTagRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
