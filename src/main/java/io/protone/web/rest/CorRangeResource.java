package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CorRange;

import io.protone.repository.CorRangeRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CorRangeDTO;
import io.protone.service.mapper.CorRangeMapper;
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
 * REST controller for managing CorRange.
 */
@RestController
@RequestMapping("/api")
public class CorRangeResource {

    private final Logger log = LoggerFactory.getLogger(CorRangeResource.class);

    private static final String ENTITY_NAME = "corRange";

    private final CorRangeRepository corRangeRepository;

    private final CorRangeMapper corRangeMapper;

    public CorRangeResource(CorRangeRepository corRangeRepository, CorRangeMapper corRangeMapper) {
        this.corRangeRepository = corRangeRepository;
        this.corRangeMapper = corRangeMapper;
    }

    /**
     * POST  /cor-ranges : Create a new corRange.
     *
     * @param corRangeDTO the corRangeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new corRangeDTO, or with status 400 (Bad Request) if the corRange has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cor-ranges")
    @Timed
    public ResponseEntity<CorRangeDTO> createCorRange(@RequestBody CorRangeDTO corRangeDTO) throws URISyntaxException {
        log.debug("REST request to save CorRange : {}", corRangeDTO);
        if (corRangeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new corRange cannot already have an ID")).body(null);
        }
        CorRange corRange = corRangeMapper.corRangeDTOToCorRange(corRangeDTO);
        corRange = corRangeRepository.save(corRange);
        CorRangeDTO result = corRangeMapper.corRangeToCorRangeDTO(corRange);
        return ResponseEntity.created(new URI("/api/cor-ranges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cor-ranges : Updates an existing corRange.
     *
     * @param corRangeDTO the corRangeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated corRangeDTO,
     * or with status 400 (Bad Request) if the corRangeDTO is not valid,
     * or with status 500 (Internal Server Error) if the corRangeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cor-ranges")
    @Timed
    public ResponseEntity<CorRangeDTO> updateCorRange(@RequestBody CorRangeDTO corRangeDTO) throws URISyntaxException {
        log.debug("REST request to update CorRange : {}", corRangeDTO);
        if (corRangeDTO.getId() == null) {
            return createCorRange(corRangeDTO);
        }
        CorRange corRange = corRangeMapper.corRangeDTOToCorRange(corRangeDTO);
        corRange = corRangeRepository.save(corRange);
        CorRangeDTO result = corRangeMapper.corRangeToCorRangeDTO(corRange);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, corRangeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cor-ranges : get all the corRanges.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of corRanges in body
     */
    @GetMapping("/cor-ranges")
    @Timed
    public List<CorRangeDTO> getAllCorRanges() {
        log.debug("REST request to get all CorRanges");
        List<CorRange> corRanges = corRangeRepository.findAll();
        return corRangeMapper.corRangesToCorRangeDTOs(corRanges);
    }

    /**
     * GET  /cor-ranges/:id : get the "id" corRange.
     *
     * @param id the id of the corRangeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the corRangeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cor-ranges/{id}")
    @Timed
    public ResponseEntity<CorRangeDTO> getCorRange(@PathVariable Long id) {
        log.debug("REST request to get CorRange : {}", id);
        CorRange corRange = corRangeRepository.findOne(id);
        CorRangeDTO corRangeDTO = corRangeMapper.corRangeToCorRangeDTO(corRange);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(corRangeDTO));
    }

    /**
     * DELETE  /cor-ranges/:id : delete the "id" corRange.
     *
     * @param id the id of the corRangeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cor-ranges/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorRange(@PathVariable Long id) {
        log.debug("REST request to delete CorRange : {}", id);
        corRangeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
