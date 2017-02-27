package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.SchEmission;

import io.protone.repository.SchEmissionRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.SchEmissionDTO;
import io.protone.service.mapper.SchEmissionMapper;
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
 * REST controller for managing SchEmission.
 */
@RestController
@RequestMapping("/api")
public class SchEmissionResource {

    private final Logger log = LoggerFactory.getLogger(SchEmissionResource.class);

    private static final String ENTITY_NAME = "schEmission";

    private final SchEmissionRepository schEmissionRepository;

    private final SchEmissionMapper schEmissionMapper;

    public SchEmissionResource(SchEmissionRepository schEmissionRepository, SchEmissionMapper schEmissionMapper) {
        this.schEmissionRepository = schEmissionRepository;
        this.schEmissionMapper = schEmissionMapper;
    }

    /**
     * POST  /sch-emissions : Create a new schEmission.
     *
     * @param schEmissionDTO the schEmissionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new schEmissionDTO, or with status 400 (Bad Request) if the schEmission has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sch-emissions")
    @Timed
    public ResponseEntity<SchEmissionDTO> createSchEmission(@Valid @RequestBody SchEmissionDTO schEmissionDTO) throws URISyntaxException {
        log.debug("REST request to save SchEmission : {}", schEmissionDTO);
        if (schEmissionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new schEmission cannot already have an ID")).body(null);
        }
        SchEmission schEmission = schEmissionMapper.schEmissionDTOToSchEmission(schEmissionDTO);
        schEmission = schEmissionRepository.save(schEmission);
        SchEmissionDTO result = schEmissionMapper.schEmissionToSchEmissionDTO(schEmission);
        return ResponseEntity.created(new URI("/api/sch-emissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sch-emissions : Updates an existing schEmission.
     *
     * @param schEmissionDTO the schEmissionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated schEmissionDTO,
     * or with status 400 (Bad Request) if the schEmissionDTO is not valid,
     * or with status 500 (Internal Server Error) if the schEmissionDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sch-emissions")
    @Timed
    public ResponseEntity<SchEmissionDTO> updateSchEmission(@Valid @RequestBody SchEmissionDTO schEmissionDTO) throws URISyntaxException {
        log.debug("REST request to update SchEmission : {}", schEmissionDTO);
        if (schEmissionDTO.getId() == null) {
            return createSchEmission(schEmissionDTO);
        }
        SchEmission schEmission = schEmissionMapper.schEmissionDTOToSchEmission(schEmissionDTO);
        schEmission = schEmissionRepository.save(schEmission);
        SchEmissionDTO result = schEmissionMapper.schEmissionToSchEmissionDTO(schEmission);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, schEmissionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sch-emissions : get all the schEmissions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of schEmissions in body
     */
    @GetMapping("/sch-emissions")
    @Timed
    public List<SchEmissionDTO> getAllSchEmissions() {
        log.debug("REST request to get all SchEmissions");
        List<SchEmission> schEmissions = schEmissionRepository.findAll();
        return schEmissionMapper.schEmissionsToSchEmissionDTOs(schEmissions);
    }

    /**
     * GET  /sch-emissions/:id : get the "id" schEmission.
     *
     * @param id the id of the schEmissionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the schEmissionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sch-emissions/{id}")
    @Timed
    public ResponseEntity<SchEmissionDTO> getSchEmission(@PathVariable Long id) {
        log.debug("REST request to get SchEmission : {}", id);
        SchEmission schEmission = schEmissionRepository.findOne(id);
        SchEmissionDTO schEmissionDTO = schEmissionMapper.schEmissionToSchEmissionDTO(schEmission);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(schEmissionDTO));
    }

    /**
     * DELETE  /sch-emissions/:id : delete the "id" schEmission.
     *
     * @param id the id of the schEmissionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sch-emissions/{id}")
    @Timed
    public ResponseEntity<Void> deleteSchEmission(@PathVariable Long id) {
        log.debug("REST request to delete SchEmission : {}", id);
        schEmissionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
