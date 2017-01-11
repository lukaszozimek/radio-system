package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CORArea;

import io.protone.repository.CORAreaRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CORAreaDTO;
import io.protone.service.mapper.CORAreaMapper;

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
 * REST controller for managing CORArea.
 */
@RestController
@RequestMapping("/api")
public class CORAreaResource {

    private final Logger log = LoggerFactory.getLogger(CORAreaResource.class);
        
    @Inject
    private CORAreaRepository cORAreaRepository;

    @Inject
    private CORAreaMapper cORAreaMapper;

    /**
     * POST  /c-or-areas : Create a new cORArea.
     *
     * @param cORAreaDTO the cORAreaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cORAreaDTO, or with status 400 (Bad Request) if the cORArea has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-or-areas")
    @Timed
    public ResponseEntity<CORAreaDTO> createCORArea(@RequestBody CORAreaDTO cORAreaDTO) throws URISyntaxException {
        log.debug("REST request to save CORArea : {}", cORAreaDTO);
        if (cORAreaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORArea", "idexists", "A new cORArea cannot already have an ID")).body(null);
        }
        CORArea cORArea = cORAreaMapper.cORAreaDTOToCORArea(cORAreaDTO);
        cORArea = cORAreaRepository.save(cORArea);
        CORAreaDTO result = cORAreaMapper.cORAreaToCORAreaDTO(cORArea);
        return ResponseEntity.created(new URI("/api/c-or-areas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cORArea", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-or-areas : Updates an existing cORArea.
     *
     * @param cORAreaDTO the cORAreaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cORAreaDTO,
     * or with status 400 (Bad Request) if the cORAreaDTO is not valid,
     * or with status 500 (Internal Server Error) if the cORAreaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-or-areas")
    @Timed
    public ResponseEntity<CORAreaDTO> updateCORArea(@RequestBody CORAreaDTO cORAreaDTO) throws URISyntaxException {
        log.debug("REST request to update CORArea : {}", cORAreaDTO);
        if (cORAreaDTO.getId() == null) {
            return createCORArea(cORAreaDTO);
        }
        CORArea cORArea = cORAreaMapper.cORAreaDTOToCORArea(cORAreaDTO);
        cORArea = cORAreaRepository.save(cORArea);
        CORAreaDTO result = cORAreaMapper.cORAreaToCORAreaDTO(cORArea);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORArea", cORAreaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-or-areas : get all the cORAreas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cORAreas in body
     */
    @GetMapping("/c-or-areas")
    @Timed
    public List<CORAreaDTO> getAllCORAreas() {
        log.debug("REST request to get all CORAreas");
        List<CORArea> cORAreas = cORAreaRepository.findAll();
        return cORAreaMapper.cORAreasToCORAreaDTOs(cORAreas);
    }

    /**
     * GET  /c-or-areas/:id : get the "id" cORArea.
     *
     * @param id the id of the cORAreaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cORAreaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/c-or-areas/{id}")
    @Timed
    public ResponseEntity<CORAreaDTO> getCORArea(@PathVariable Long id) {
        log.debug("REST request to get CORArea : {}", id);
        CORArea cORArea = cORAreaRepository.findOne(id);
        CORAreaDTO cORAreaDTO = cORAreaMapper.cORAreaToCORAreaDTO(cORArea);
        return Optional.ofNullable(cORAreaDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-or-areas/:id : delete the "id" cORArea.
     *
     * @param id the id of the cORAreaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-or-areas/{id}")
    @Timed
    public ResponseEntity<Void> deleteCORArea(@PathVariable Long id) {
        log.debug("REST request to delete CORArea : {}", id);
        cORAreaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORArea", id.toString())).build();
    }

}
