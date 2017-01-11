package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CORTag;

import io.protone.repository.CORTagRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CORTagDTO;
import io.protone.service.mapper.CORTagMapper;

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
 * REST controller for managing CORTag.
 */
@RestController
@RequestMapping("/api")
public class CORTagResource {

    private final Logger log = LoggerFactory.getLogger(CORTagResource.class);
        
    @Inject
    private CORTagRepository cORTagRepository;

    @Inject
    private CORTagMapper cORTagMapper;

    /**
     * POST  /c-or-tags : Create a new cORTag.
     *
     * @param cORTagDTO the cORTagDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cORTagDTO, or with status 400 (Bad Request) if the cORTag has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-or-tags")
    @Timed
    public ResponseEntity<CORTagDTO> createCORTag(@Valid @RequestBody CORTagDTO cORTagDTO) throws URISyntaxException {
        log.debug("REST request to save CORTag : {}", cORTagDTO);
        if (cORTagDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORTag", "idexists", "A new cORTag cannot already have an ID")).body(null);
        }
        CORTag cORTag = cORTagMapper.cORTagDTOToCORTag(cORTagDTO);
        cORTag = cORTagRepository.save(cORTag);
        CORTagDTO result = cORTagMapper.cORTagToCORTagDTO(cORTag);
        return ResponseEntity.created(new URI("/api/c-or-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cORTag", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-or-tags : Updates an existing cORTag.
     *
     * @param cORTagDTO the cORTagDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cORTagDTO,
     * or with status 400 (Bad Request) if the cORTagDTO is not valid,
     * or with status 500 (Internal Server Error) if the cORTagDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-or-tags")
    @Timed
    public ResponseEntity<CORTagDTO> updateCORTag(@Valid @RequestBody CORTagDTO cORTagDTO) throws URISyntaxException {
        log.debug("REST request to update CORTag : {}", cORTagDTO);
        if (cORTagDTO.getId() == null) {
            return createCORTag(cORTagDTO);
        }
        CORTag cORTag = cORTagMapper.cORTagDTOToCORTag(cORTagDTO);
        cORTag = cORTagRepository.save(cORTag);
        CORTagDTO result = cORTagMapper.cORTagToCORTagDTO(cORTag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORTag", cORTagDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-or-tags : get all the cORTags.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cORTags in body
     */
    @GetMapping("/c-or-tags")
    @Timed
    public List<CORTagDTO> getAllCORTags() {
        log.debug("REST request to get all CORTags");
        List<CORTag> cORTags = cORTagRepository.findAll();
        return cORTagMapper.cORTagsToCORTagDTOs(cORTags);
    }

    /**
     * GET  /c-or-tags/:id : get the "id" cORTag.
     *
     * @param id the id of the cORTagDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cORTagDTO, or with status 404 (Not Found)
     */
    @GetMapping("/c-or-tags/{id}")
    @Timed
    public ResponseEntity<CORTagDTO> getCORTag(@PathVariable Long id) {
        log.debug("REST request to get CORTag : {}", id);
        CORTag cORTag = cORTagRepository.findOne(id);
        CORTagDTO cORTagDTO = cORTagMapper.cORTagToCORTagDTO(cORTag);
        return Optional.ofNullable(cORTagDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-or-tags/:id : delete the "id" cORTag.
     *
     * @param id the id of the cORTagDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-or-tags/{id}")
    @Timed
    public ResponseEntity<Void> deleteCORTag(@PathVariable Long id) {
        log.debug("REST request to delete CORTag : {}", id);
        cORTagRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORTag", id.toString())).build();
    }

}
