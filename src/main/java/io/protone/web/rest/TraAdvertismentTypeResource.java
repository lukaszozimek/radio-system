package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.TraAdvertismentType;

import io.protone.repository.TraAdvertismentTypeRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.TraAdvertismentTypeDTO;
import io.protone.service.mapper.TraAdvertismentTypeMapper;
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
 * REST controller for managing TraAdvertismentType.
 */
@RestController
@RequestMapping("/api")
public class TraAdvertismentTypeResource {

    private final Logger log = LoggerFactory.getLogger(TraAdvertismentTypeResource.class);

    private static final String ENTITY_NAME = "traAdvertismentType";
        
    private final TraAdvertismentTypeRepository traAdvertismentTypeRepository;

    private final TraAdvertismentTypeMapper traAdvertismentTypeMapper;

    public TraAdvertismentTypeResource(TraAdvertismentTypeRepository traAdvertismentTypeRepository, TraAdvertismentTypeMapper traAdvertismentTypeMapper) {
        this.traAdvertismentTypeRepository = traAdvertismentTypeRepository;
        this.traAdvertismentTypeMapper = traAdvertismentTypeMapper;
    }

    /**
     * POST  /tra-advertisment-types : Create a new traAdvertismentType.
     *
     * @param traAdvertismentTypeDTO the traAdvertismentTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new traAdvertismentTypeDTO, or with status 400 (Bad Request) if the traAdvertismentType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tra-advertisment-types")
    @Timed
    public ResponseEntity<TraAdvertismentTypeDTO> createTraAdvertismentType(@RequestBody TraAdvertismentTypeDTO traAdvertismentTypeDTO) throws URISyntaxException {
        log.debug("REST request to save TraAdvertismentType : {}", traAdvertismentTypeDTO);
        if (traAdvertismentTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new traAdvertismentType cannot already have an ID")).body(null);
        }
        TraAdvertismentType traAdvertismentType = traAdvertismentTypeMapper.traAdvertismentTypeDTOToTraAdvertismentType(traAdvertismentTypeDTO);
        traAdvertismentType = traAdvertismentTypeRepository.save(traAdvertismentType);
        TraAdvertismentTypeDTO result = traAdvertismentTypeMapper.traAdvertismentTypeToTraAdvertismentTypeDTO(traAdvertismentType);
        return ResponseEntity.created(new URI("/api/tra-advertisment-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tra-advertisment-types : Updates an existing traAdvertismentType.
     *
     * @param traAdvertismentTypeDTO the traAdvertismentTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated traAdvertismentTypeDTO,
     * or with status 400 (Bad Request) if the traAdvertismentTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the traAdvertismentTypeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tra-advertisment-types")
    @Timed
    public ResponseEntity<TraAdvertismentTypeDTO> updateTraAdvertismentType(@RequestBody TraAdvertismentTypeDTO traAdvertismentTypeDTO) throws URISyntaxException {
        log.debug("REST request to update TraAdvertismentType : {}", traAdvertismentTypeDTO);
        if (traAdvertismentTypeDTO.getId() == null) {
            return createTraAdvertismentType(traAdvertismentTypeDTO);
        }
        TraAdvertismentType traAdvertismentType = traAdvertismentTypeMapper.traAdvertismentTypeDTOToTraAdvertismentType(traAdvertismentTypeDTO);
        traAdvertismentType = traAdvertismentTypeRepository.save(traAdvertismentType);
        TraAdvertismentTypeDTO result = traAdvertismentTypeMapper.traAdvertismentTypeToTraAdvertismentTypeDTO(traAdvertismentType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, traAdvertismentTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tra-advertisment-types : get all the traAdvertismentTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of traAdvertismentTypes in body
     */
    @GetMapping("/tra-advertisment-types")
    @Timed
    public List<TraAdvertismentTypeDTO> getAllTraAdvertismentTypes() {
        log.debug("REST request to get all TraAdvertismentTypes");
        List<TraAdvertismentType> traAdvertismentTypes = traAdvertismentTypeRepository.findAll();
        return traAdvertismentTypeMapper.traAdvertismentTypesToTraAdvertismentTypeDTOs(traAdvertismentTypes);
    }

    /**
     * GET  /tra-advertisment-types/:id : get the "id" traAdvertismentType.
     *
     * @param id the id of the traAdvertismentTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the traAdvertismentTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tra-advertisment-types/{id}")
    @Timed
    public ResponseEntity<TraAdvertismentTypeDTO> getTraAdvertismentType(@PathVariable Long id) {
        log.debug("REST request to get TraAdvertismentType : {}", id);
        TraAdvertismentType traAdvertismentType = traAdvertismentTypeRepository.findOne(id);
        TraAdvertismentTypeDTO traAdvertismentTypeDTO = traAdvertismentTypeMapper.traAdvertismentTypeToTraAdvertismentTypeDTO(traAdvertismentType);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(traAdvertismentTypeDTO));
    }

    /**
     * DELETE  /tra-advertisment-types/:id : delete the "id" traAdvertismentType.
     *
     * @param id the id of the traAdvertismentTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tra-advertisment-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteTraAdvertismentType(@PathVariable Long id) {
        log.debug("REST request to delete TraAdvertismentType : {}", id);
        traAdvertismentTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
