package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.SCHBlock;

import io.protone.repository.SCHBlockRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.SCHBlockDTO;
import io.protone.service.mapper.SCHBlockMapper;

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
 * REST controller for managing SCHBlock.
 */
@RestController
@RequestMapping("/api")
public class SCHBlockResource {

    private final Logger log = LoggerFactory.getLogger(SCHBlockResource.class);
        
    @Inject
    private SCHBlockRepository sCHBlockRepository;

    @Inject
    private SCHBlockMapper sCHBlockMapper;

    /**
     * POST  /s-ch-blocks : Create a new sCHBlock.
     *
     * @param sCHBlockDTO the sCHBlockDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sCHBlockDTO, or with status 400 (Bad Request) if the sCHBlock has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/s-ch-blocks")
    @Timed
    public ResponseEntity<SCHBlockDTO> createSCHBlock(@Valid @RequestBody SCHBlockDTO sCHBlockDTO) throws URISyntaxException {
        log.debug("REST request to save SCHBlock : {}", sCHBlockDTO);
        if (sCHBlockDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sCHBlock", "idexists", "A new sCHBlock cannot already have an ID")).body(null);
        }
        SCHBlock sCHBlock = sCHBlockMapper.sCHBlockDTOToSCHBlock(sCHBlockDTO);
        sCHBlock = sCHBlockRepository.save(sCHBlock);
        SCHBlockDTO result = sCHBlockMapper.sCHBlockToSCHBlockDTO(sCHBlock);
        return ResponseEntity.created(new URI("/api/s-ch-blocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sCHBlock", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /s-ch-blocks : Updates an existing sCHBlock.
     *
     * @param sCHBlockDTO the sCHBlockDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sCHBlockDTO,
     * or with status 400 (Bad Request) if the sCHBlockDTO is not valid,
     * or with status 500 (Internal Server Error) if the sCHBlockDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/s-ch-blocks")
    @Timed
    public ResponseEntity<SCHBlockDTO> updateSCHBlock(@Valid @RequestBody SCHBlockDTO sCHBlockDTO) throws URISyntaxException {
        log.debug("REST request to update SCHBlock : {}", sCHBlockDTO);
        if (sCHBlockDTO.getId() == null) {
            return createSCHBlock(sCHBlockDTO);
        }
        SCHBlock sCHBlock = sCHBlockMapper.sCHBlockDTOToSCHBlock(sCHBlockDTO);
        sCHBlock = sCHBlockRepository.save(sCHBlock);
        SCHBlockDTO result = sCHBlockMapper.sCHBlockToSCHBlockDTO(sCHBlock);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sCHBlock", sCHBlockDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /s-ch-blocks : get all the sCHBlocks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sCHBlocks in body
     */
    @GetMapping("/s-ch-blocks")
    @Timed
    public List<SCHBlockDTO> getAllSCHBlocks() {
        log.debug("REST request to get all SCHBlocks");
        List<SCHBlock> sCHBlocks = sCHBlockRepository.findAll();
        return sCHBlockMapper.sCHBlocksToSCHBlockDTOs(sCHBlocks);
    }

    /**
     * GET  /s-ch-blocks/:id : get the "id" sCHBlock.
     *
     * @param id the id of the sCHBlockDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sCHBlockDTO, or with status 404 (Not Found)
     */
    @GetMapping("/s-ch-blocks/{id}")
    @Timed
    public ResponseEntity<SCHBlockDTO> getSCHBlock(@PathVariable Long id) {
        log.debug("REST request to get SCHBlock : {}", id);
        SCHBlock sCHBlock = sCHBlockRepository.findOne(id);
        SCHBlockDTO sCHBlockDTO = sCHBlockMapper.sCHBlockToSCHBlockDTO(sCHBlock);
        return Optional.ofNullable(sCHBlockDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /s-ch-blocks/:id : delete the "id" sCHBlock.
     *
     * @param id the id of the sCHBlockDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/s-ch-blocks/{id}")
    @Timed
    public ResponseEntity<Void> deleteSCHBlock(@PathVariable Long id) {
        log.debug("REST request to delete SCHBlock : {}", id);
        sCHBlockRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sCHBlock", id.toString())).build();
    }

}
