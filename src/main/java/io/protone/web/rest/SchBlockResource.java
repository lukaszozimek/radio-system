package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.SchBlock;

import io.protone.repository.SchBlockRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.SchBlockDTO;
import io.protone.service.mapper.SchBlockMapper;
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
 * REST controller for managing SchBlock.
 */
@RestController
@RequestMapping("/api")
public class SchBlockResource {

    private final Logger log = LoggerFactory.getLogger(SchBlockResource.class);

    private static final String ENTITY_NAME = "schBlock";

    private final SchBlockRepository schBlockRepository;

    private final SchBlockMapper schBlockMapper;

    public SchBlockResource(SchBlockRepository schBlockRepository, SchBlockMapper schBlockMapper) {
        this.schBlockRepository = schBlockRepository;
        this.schBlockMapper = schBlockMapper;
    }

    /**
     * POST  /sch-blocks : Create a new schBlock.
     *
     * @param schBlockDTO the schBlockDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new schBlockDTO, or with status 400 (Bad Request) if the schBlock has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sch-blocks")
    @Timed
    public ResponseEntity<SchBlockDTO> createSchBlock(@Valid @RequestBody SchBlockDTO schBlockDTO) throws URISyntaxException {
        log.debug("REST request to save SchBlock : {}", schBlockDTO);
        if (schBlockDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new schBlock cannot already have an ID")).body(null);
        }
        SchBlock schBlock = schBlockMapper.schBlockDTOToSchBlock(schBlockDTO);
        schBlock = schBlockRepository.save(schBlock);
        SchBlockDTO result = schBlockMapper.schBlockToSchBlockDTO(schBlock);
        return ResponseEntity.created(new URI("/api/sch-blocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sch-blocks : Updates an existing schBlock.
     *
     * @param schBlockDTO the schBlockDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated schBlockDTO,
     * or with status 400 (Bad Request) if the schBlockDTO is not valid,
     * or with status 500 (Internal Server Error) if the schBlockDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sch-blocks")
    @Timed
    public ResponseEntity<SchBlockDTO> updateSchBlock(@Valid @RequestBody SchBlockDTO schBlockDTO) throws URISyntaxException {
        log.debug("REST request to update SchBlock : {}", schBlockDTO);
        if (schBlockDTO.getId() == null) {
            return createSchBlock(schBlockDTO);
        }
        SchBlock schBlock = schBlockMapper.schBlockDTOToSchBlock(schBlockDTO);
        schBlock = schBlockRepository.save(schBlock);
        SchBlockDTO result = schBlockMapper.schBlockToSchBlockDTO(schBlock);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, schBlockDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sch-blocks : get all the schBlocks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of schBlocks in body
     */
    @GetMapping("/sch-blocks")
    @Timed
    public List<SchBlockDTO> getAllSchBlocks() {
        log.debug("REST request to get all SchBlocks");
        List<SchBlock> schBlocks = schBlockRepository.findAll();
        return schBlockMapper.schBlocksToSchBlockDTOs(schBlocks);
    }

    /**
     * GET  /sch-blocks/:id : get the "id" schBlock.
     *
     * @param id the id of the schBlockDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the schBlockDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sch-blocks/{id}")
    @Timed
    public ResponseEntity<SchBlockDTO> getSchBlock(@PathVariable Long id) {
        log.debug("REST request to get SchBlock : {}", id);
        SchBlock schBlock = schBlockRepository.findOne(id);
        SchBlockDTO schBlockDTO = schBlockMapper.schBlockToSchBlockDTO(schBlock);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(schBlockDTO));
    }

    /**
     * DELETE  /sch-blocks/:id : delete the "id" schBlock.
     *
     * @param id the id of the schBlockDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sch-blocks/{id}")
    @Timed
    public ResponseEntity<Void> deleteSchBlock(@PathVariable Long id) {
        log.debug("REST request to delete SchBlock : {}", id);
        schBlockRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
