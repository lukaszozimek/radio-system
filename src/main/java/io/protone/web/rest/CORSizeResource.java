package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CORSize;

import io.protone.repository.CORSizeRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CORSizeDTO;
import io.protone.service.mapper.CORSizeMapper;

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
 * REST controller for managing CORSize.
 */
@RestController
@RequestMapping("/api")
public class CORSizeResource {

    private final Logger log = LoggerFactory.getLogger(CORSizeResource.class);
        
    @Inject
    private CORSizeRepository cORSizeRepository;

    @Inject
    private CORSizeMapper cORSizeMapper;

    /**
     * POST  /c-or-sizes : Create a new cORSize.
     *
     * @param cORSizeDTO the cORSizeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cORSizeDTO, or with status 400 (Bad Request) if the cORSize has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-or-sizes")
    @Timed
    public ResponseEntity<CORSizeDTO> createCORSize(@RequestBody CORSizeDTO cORSizeDTO) throws URISyntaxException {
        log.debug("REST request to save CORSize : {}", cORSizeDTO);
        if (cORSizeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORSize", "idexists", "A new cORSize cannot already have an ID")).body(null);
        }
        CORSize cORSize = cORSizeMapper.cORSizeDTOToCORSize(cORSizeDTO);
        cORSize = cORSizeRepository.save(cORSize);
        CORSizeDTO result = cORSizeMapper.cORSizeToCORSizeDTO(cORSize);
        return ResponseEntity.created(new URI("/api/c-or-sizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cORSize", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-or-sizes : Updates an existing cORSize.
     *
     * @param cORSizeDTO the cORSizeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cORSizeDTO,
     * or with status 400 (Bad Request) if the cORSizeDTO is not valid,
     * or with status 500 (Internal Server Error) if the cORSizeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-or-sizes")
    @Timed
    public ResponseEntity<CORSizeDTO> updateCORSize(@RequestBody CORSizeDTO cORSizeDTO) throws URISyntaxException {
        log.debug("REST request to update CORSize : {}", cORSizeDTO);
        if (cORSizeDTO.getId() == null) {
            return createCORSize(cORSizeDTO);
        }
        CORSize cORSize = cORSizeMapper.cORSizeDTOToCORSize(cORSizeDTO);
        cORSize = cORSizeRepository.save(cORSize);
        CORSizeDTO result = cORSizeMapper.cORSizeToCORSizeDTO(cORSize);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORSize", cORSizeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-or-sizes : get all the cORSizes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cORSizes in body
     */
    @GetMapping("/c-or-sizes")
    @Timed
    public List<CORSizeDTO> getAllCORSizes() {
        log.debug("REST request to get all CORSizes");
        List<CORSize> cORSizes = cORSizeRepository.findAll();
        return cORSizeMapper.cORSizesToCORSizeDTOs(cORSizes);
    }

    /**
     * GET  /c-or-sizes/:id : get the "id" cORSize.
     *
     * @param id the id of the cORSizeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cORSizeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/c-or-sizes/{id}")
    @Timed
    public ResponseEntity<CORSizeDTO> getCORSize(@PathVariable Long id) {
        log.debug("REST request to get CORSize : {}", id);
        CORSize cORSize = cORSizeRepository.findOne(id);
        CORSizeDTO cORSizeDTO = cORSizeMapper.cORSizeToCORSizeDTO(cORSize);
        return Optional.ofNullable(cORSizeDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-or-sizes/:id : delete the "id" cORSize.
     *
     * @param id the id of the cORSizeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-or-sizes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCORSize(@PathVariable Long id) {
        log.debug("REST request to delete CORSize : {}", id);
        cORSizeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORSize", id.toString())).build();
    }

}
