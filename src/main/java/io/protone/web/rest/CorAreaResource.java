package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CorArea;

import io.protone.repository.CorAreaRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CorAreaDTO;
import io.protone.service.mapper.CorAreaMapper;
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
 * REST controller for managing CorArea.
 */
@RestController
@RequestMapping("/api")
public class CorAreaResource {

    private final Logger log = LoggerFactory.getLogger(CorAreaResource.class);

    private static final String ENTITY_NAME = "corArea";
        
    private final CorAreaRepository corAreaRepository;

    private final CorAreaMapper corAreaMapper;

    public CorAreaResource(CorAreaRepository corAreaRepository, CorAreaMapper corAreaMapper) {
        this.corAreaRepository = corAreaRepository;
        this.corAreaMapper = corAreaMapper;
    }

    /**
     * POST  /cor-areas : Create a new corArea.
     *
     * @param corAreaDTO the corAreaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new corAreaDTO, or with status 400 (Bad Request) if the corArea has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cor-areas")
    @Timed
    public ResponseEntity<CorAreaDTO> createCorArea(@RequestBody CorAreaDTO corAreaDTO) throws URISyntaxException {
        log.debug("REST request to save CorArea : {}", corAreaDTO);
        if (corAreaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new corArea cannot already have an ID")).body(null);
        }
        CorArea corArea = corAreaMapper.corAreaDTOToCorArea(corAreaDTO);
        corArea = corAreaRepository.save(corArea);
        CorAreaDTO result = corAreaMapper.corAreaToCorAreaDTO(corArea);
        return ResponseEntity.created(new URI("/api/cor-areas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cor-areas : Updates an existing corArea.
     *
     * @param corAreaDTO the corAreaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated corAreaDTO,
     * or with status 400 (Bad Request) if the corAreaDTO is not valid,
     * or with status 500 (Internal Server Error) if the corAreaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cor-areas")
    @Timed
    public ResponseEntity<CorAreaDTO> updateCorArea(@RequestBody CorAreaDTO corAreaDTO) throws URISyntaxException {
        log.debug("REST request to update CorArea : {}", corAreaDTO);
        if (corAreaDTO.getId() == null) {
            return createCorArea(corAreaDTO);
        }
        CorArea corArea = corAreaMapper.corAreaDTOToCorArea(corAreaDTO);
        corArea = corAreaRepository.save(corArea);
        CorAreaDTO result = corAreaMapper.corAreaToCorAreaDTO(corArea);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, corAreaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cor-areas : get all the corAreas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of corAreas in body
     */
    @GetMapping("/cor-areas")
    @Timed
    public List<CorAreaDTO> getAllCorAreas() {
        log.debug("REST request to get all CorAreas");
        List<CorArea> corAreas = corAreaRepository.findAll();
        return corAreaMapper.corAreasToCorAreaDTOs(corAreas);
    }

    /**
     * GET  /cor-areas/:id : get the "id" corArea.
     *
     * @param id the id of the corAreaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the corAreaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cor-areas/{id}")
    @Timed
    public ResponseEntity<CorAreaDTO> getCorArea(@PathVariable Long id) {
        log.debug("REST request to get CorArea : {}", id);
        CorArea corArea = corAreaRepository.findOne(id);
        CorAreaDTO corAreaDTO = corAreaMapper.corAreaToCorAreaDTO(corArea);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(corAreaDTO));
    }

    /**
     * DELETE  /cor-areas/:id : delete the "id" corArea.
     *
     * @param id the id of the corAreaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cor-areas/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorArea(@PathVariable Long id) {
        log.debug("REST request to delete CorArea : {}", id);
        corAreaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
