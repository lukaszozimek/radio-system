package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LIBLabel;

import io.protone.repository.LIBLabelRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.LIBLabelDTO;
import io.protone.service.mapper.LIBLabelMapper;

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
 * REST controller for managing LIBLabel.
 */
@RestController
@RequestMapping("/api")
public class LIBLabelResource {

    private final Logger log = LoggerFactory.getLogger(LIBLabelResource.class);
        
    @Inject
    private LIBLabelRepository lIBLabelRepository;

    @Inject
    private LIBLabelMapper lIBLabelMapper;

    /**
     * POST  /l-ib-labels : Create a new lIBLabel.
     *
     * @param lIBLabelDTO the lIBLabelDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lIBLabelDTO, or with status 400 (Bad Request) if the lIBLabel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/l-ib-labels")
    @Timed
    public ResponseEntity<LIBLabelDTO> createLIBLabel(@Valid @RequestBody LIBLabelDTO lIBLabelDTO) throws URISyntaxException {
        log.debug("REST request to save LIBLabel : {}", lIBLabelDTO);
        if (lIBLabelDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lIBLabel", "idexists", "A new lIBLabel cannot already have an ID")).body(null);
        }
        LIBLabel lIBLabel = lIBLabelMapper.lIBLabelDTOToLIBLabel(lIBLabelDTO);
        lIBLabel = lIBLabelRepository.save(lIBLabel);
        LIBLabelDTO result = lIBLabelMapper.lIBLabelToLIBLabelDTO(lIBLabel);
        return ResponseEntity.created(new URI("/api/l-ib-labels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lIBLabel", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /l-ib-labels : Updates an existing lIBLabel.
     *
     * @param lIBLabelDTO the lIBLabelDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lIBLabelDTO,
     * or with status 400 (Bad Request) if the lIBLabelDTO is not valid,
     * or with status 500 (Internal Server Error) if the lIBLabelDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/l-ib-labels")
    @Timed
    public ResponseEntity<LIBLabelDTO> updateLIBLabel(@Valid @RequestBody LIBLabelDTO lIBLabelDTO) throws URISyntaxException {
        log.debug("REST request to update LIBLabel : {}", lIBLabelDTO);
        if (lIBLabelDTO.getId() == null) {
            return createLIBLabel(lIBLabelDTO);
        }
        LIBLabel lIBLabel = lIBLabelMapper.lIBLabelDTOToLIBLabel(lIBLabelDTO);
        lIBLabel = lIBLabelRepository.save(lIBLabel);
        LIBLabelDTO result = lIBLabelMapper.lIBLabelToLIBLabelDTO(lIBLabel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lIBLabel", lIBLabelDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /l-ib-labels : get all the lIBLabels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lIBLabels in body
     */
    @GetMapping("/l-ib-labels")
    @Timed
    public List<LIBLabelDTO> getAllLIBLabels() {
        log.debug("REST request to get all LIBLabels");
        List<LIBLabel> lIBLabels = lIBLabelRepository.findAll();
        return lIBLabelMapper.lIBLabelsToLIBLabelDTOs(lIBLabels);
    }

    /**
     * GET  /l-ib-labels/:id : get the "id" lIBLabel.
     *
     * @param id the id of the lIBLabelDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lIBLabelDTO, or with status 404 (Not Found)
     */
    @GetMapping("/l-ib-labels/{id}")
    @Timed
    public ResponseEntity<LIBLabelDTO> getLIBLabel(@PathVariable Long id) {
        log.debug("REST request to get LIBLabel : {}", id);
        LIBLabel lIBLabel = lIBLabelRepository.findOne(id);
        LIBLabelDTO lIBLabelDTO = lIBLabelMapper.lIBLabelToLIBLabelDTO(lIBLabel);
        return Optional.ofNullable(lIBLabelDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /l-ib-labels/:id : delete the "id" lIBLabel.
     *
     * @param id the id of the lIBLabelDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/l-ib-labels/{id}")
    @Timed
    public ResponseEntity<Void> deleteLIBLabel(@PathVariable Long id) {
        log.debug("REST request to delete LIBLabel : {}", id);
        lIBLabelRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lIBLabel", id.toString())).build();
    }

}
