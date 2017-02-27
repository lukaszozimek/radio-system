package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CorSize;

import io.protone.repository.CorSizeRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CorSizeDTO;
import io.protone.service.mapper.CorSizeMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CorSize.
 */
@RestController
@RequestMapping("/api")
public class CorSizeResource {

    private final Logger log = LoggerFactory.getLogger(CorSizeResource.class);

    private static final String ENTITY_NAME = "corSize";

    private final CorSizeRepository corSizeRepository;

    private final CorSizeMapper corSizeMapper;

    public CorSizeResource(CorSizeRepository corSizeRepository, CorSizeMapper corSizeMapper) {
        this.corSizeRepository = corSizeRepository;
        this.corSizeMapper = corSizeMapper;
    }

    /**
     * POST  /cor-sizes : Create a new corSize.
     *
     * @param corSizeDTO the corSizeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new corSizeDTO, or with status 400 (Bad Request) if the corSize has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cor-sizes")
    @Timed
    public ResponseEntity<CorSizeDTO> createCorSize(@RequestBody CorSizeDTO corSizeDTO) throws URISyntaxException {
        log.debug("REST request to save CorSize : {}", corSizeDTO);
        if (corSizeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new corSize cannot already have an ID")).body(null);
        }
        CorSize corSize = corSizeMapper.corSizeDTOToCorSize(corSizeDTO);
        corSize = corSizeRepository.save(corSize);
        CorSizeDTO result = corSizeMapper.corSizeToCorSizeDTO(corSize);
        return ResponseEntity.created(new URI("/api/cor-sizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cor-sizes : Updates an existing corSize.
     *
     * @param corSizeDTO the corSizeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated corSizeDTO,
     * or with status 400 (Bad Request) if the corSizeDTO is not valid,
     * or with status 500 (Internal Server Error) if the corSizeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cor-sizes")
    @Timed
    public ResponseEntity<CorSizeDTO> updateCorSize(@RequestBody CorSizeDTO corSizeDTO) throws URISyntaxException {
        log.debug("REST request to update CorSize : {}", corSizeDTO);
        if (corSizeDTO.getId() == null) {
            return createCorSize(corSizeDTO);
        }
        CorSize corSize = corSizeMapper.corSizeDTOToCorSize(corSizeDTO);
        corSize = corSizeRepository.save(corSize);
        CorSizeDTO result = corSizeMapper.corSizeToCorSizeDTO(corSize);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, corSizeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cor-sizes : get all the corSizes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of corSizes in body
     */
    @GetMapping("/cor-sizes")
    @Timed
    public List<CorSizeDTO> getAllCorSizes() {
        log.debug("REST request to get all CorSizes");
        List<CorSize> corSizes = corSizeRepository.findAll();
        return corSizeMapper.corSizesToCorSizeDTOs(corSizes);
    }

    /**
     * GET  /cor-sizes/:id : get the "id" corSize.
     *
     * @param id the id of the corSizeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the corSizeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cor-sizes/{id}")
    @Timed
    public ResponseEntity<CorSizeDTO> getCorSize(@PathVariable Long id) {
        log.debug("REST request to get CorSize : {}", id);
        CorSize corSize = corSizeRepository.findOne(id);
        CorSizeDTO corSizeDTO = corSizeMapper.corSizeToCorSizeDTO(corSize);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(corSizeDTO));
    }

    /**
     * DELETE  /cor-sizes/:id : delete the "id" corSize.
     *
     * @param id the id of the corSizeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cor-sizes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorSize(@PathVariable Long id) {
        log.debug("REST request to delete CorSize : {}", id);
        corSizeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
