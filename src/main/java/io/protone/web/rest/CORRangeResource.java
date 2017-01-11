package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CORRange;

import io.protone.repository.CORRangeRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CORRangeDTO;
import io.protone.service.mapper.CORRangeMapper;

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
 * REST controller for managing CORRange.
 */
@RestController
@RequestMapping("/api")
public class CORRangeResource {

    private final Logger log = LoggerFactory.getLogger(CORRangeResource.class);
        
    @Inject
    private CORRangeRepository cORRangeRepository;

    @Inject
    private CORRangeMapper cORRangeMapper;

    /**
     * POST  /c-or-ranges : Create a new cORRange.
     *
     * @param cORRangeDTO the cORRangeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cORRangeDTO, or with status 400 (Bad Request) if the cORRange has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-or-ranges")
    @Timed
    public ResponseEntity<CORRangeDTO> createCORRange(@RequestBody CORRangeDTO cORRangeDTO) throws URISyntaxException {
        log.debug("REST request to save CORRange : {}", cORRangeDTO);
        if (cORRangeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORRange", "idexists", "A new cORRange cannot already have an ID")).body(null);
        }
        CORRange cORRange = cORRangeMapper.cORRangeDTOToCORRange(cORRangeDTO);
        cORRange = cORRangeRepository.save(cORRange);
        CORRangeDTO result = cORRangeMapper.cORRangeToCORRangeDTO(cORRange);
        return ResponseEntity.created(new URI("/api/c-or-ranges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cORRange", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-or-ranges : Updates an existing cORRange.
     *
     * @param cORRangeDTO the cORRangeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cORRangeDTO,
     * or with status 400 (Bad Request) if the cORRangeDTO is not valid,
     * or with status 500 (Internal Server Error) if the cORRangeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-or-ranges")
    @Timed
    public ResponseEntity<CORRangeDTO> updateCORRange(@RequestBody CORRangeDTO cORRangeDTO) throws URISyntaxException {
        log.debug("REST request to update CORRange : {}", cORRangeDTO);
        if (cORRangeDTO.getId() == null) {
            return createCORRange(cORRangeDTO);
        }
        CORRange cORRange = cORRangeMapper.cORRangeDTOToCORRange(cORRangeDTO);
        cORRange = cORRangeRepository.save(cORRange);
        CORRangeDTO result = cORRangeMapper.cORRangeToCORRangeDTO(cORRange);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORRange", cORRangeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-or-ranges : get all the cORRanges.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cORRanges in body
     */
    @GetMapping("/c-or-ranges")
    @Timed
    public List<CORRangeDTO> getAllCORRanges() {
        log.debug("REST request to get all CORRanges");
        List<CORRange> cORRanges = cORRangeRepository.findAll();
        return cORRangeMapper.cORRangesToCORRangeDTOs(cORRanges);
    }

    /**
     * GET  /c-or-ranges/:id : get the "id" cORRange.
     *
     * @param id the id of the cORRangeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cORRangeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/c-or-ranges/{id}")
    @Timed
    public ResponseEntity<CORRangeDTO> getCORRange(@PathVariable Long id) {
        log.debug("REST request to get CORRange : {}", id);
        CORRange cORRange = cORRangeRepository.findOne(id);
        CORRangeDTO cORRangeDTO = cORRangeMapper.cORRangeToCORRangeDTO(cORRange);
        return Optional.ofNullable(cORRangeDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-or-ranges/:id : delete the "id" cORRange.
     *
     * @param id the id of the cORRangeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-or-ranges/{id}")
    @Timed
    public ResponseEntity<Void> deleteCORRange(@PathVariable Long id) {
        log.debug("REST request to delete CORRange : {}", id);
        cORRangeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORRange", id.toString())).build();
    }

}
