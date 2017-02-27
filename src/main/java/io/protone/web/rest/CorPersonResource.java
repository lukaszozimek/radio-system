package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CorPerson;

import io.protone.repository.CorPersonRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CorPersonDTO;
import io.protone.service.mapper.CorPersonMapper;
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
 * REST controller for managing CorPerson.
 */
@RestController
@RequestMapping("/api")
public class CorPersonResource {

    private final Logger log = LoggerFactory.getLogger(CorPersonResource.class);

    private static final String ENTITY_NAME = "corPerson";

    private final CorPersonRepository corPersonRepository;

    private final CorPersonMapper corPersonMapper;

    public CorPersonResource(CorPersonRepository corPersonRepository, CorPersonMapper corPersonMapper) {
        this.corPersonRepository = corPersonRepository;
        this.corPersonMapper = corPersonMapper;
    }

    /**
     * POST  /cor-people : Create a new corPerson.
     *
     * @param corPersonDTO the corPersonDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new corPersonDTO, or with status 400 (Bad Request) if the corPerson has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cor-people")
    @Timed
    public ResponseEntity<CorPersonDTO> createCorPerson(@Valid @RequestBody CorPersonDTO corPersonDTO) throws URISyntaxException {
        log.debug("REST request to save CorPerson : {}", corPersonDTO);
        if (corPersonDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new corPerson cannot already have an ID")).body(null);
        }
        CorPerson corPerson = corPersonMapper.corPersonDTOToCorPerson(corPersonDTO);
        corPerson = corPersonRepository.save(corPerson);
        CorPersonDTO result = corPersonMapper.corPersonToCorPersonDTO(corPerson);
        return ResponseEntity.created(new URI("/api/cor-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cor-people : Updates an existing corPerson.
     *
     * @param corPersonDTO the corPersonDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated corPersonDTO,
     * or with status 400 (Bad Request) if the corPersonDTO is not valid,
     * or with status 500 (Internal Server Error) if the corPersonDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cor-people")
    @Timed
    public ResponseEntity<CorPersonDTO> updateCorPerson(@Valid @RequestBody CorPersonDTO corPersonDTO) throws URISyntaxException {
        log.debug("REST request to update CorPerson : {}", corPersonDTO);
        if (corPersonDTO.getId() == null) {
            return createCorPerson(corPersonDTO);
        }
        CorPerson corPerson = corPersonMapper.corPersonDTOToCorPerson(corPersonDTO);
        corPerson = corPersonRepository.save(corPerson);
        CorPersonDTO result = corPersonMapper.corPersonToCorPersonDTO(corPerson);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, corPersonDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cor-people : get all the corPeople.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of corPeople in body
     */
    @GetMapping("/cor-people")
    @Timed
    public List<CorPersonDTO> getAllCorPeople() {
        log.debug("REST request to get all CorPeople");
        List<CorPerson> corPeople = corPersonRepository.findAll();
        return corPersonMapper.corPeopleToCorPersonDTOs(corPeople);
    }

    /**
     * GET  /cor-people/:id : get the "id" corPerson.
     *
     * @param id the id of the corPersonDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the corPersonDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cor-people/{id}")
    @Timed
    public ResponseEntity<CorPersonDTO> getCorPerson(@PathVariable Long id) {
        log.debug("REST request to get CorPerson : {}", id);
        CorPerson corPerson = corPersonRepository.findOne(id);
        CorPersonDTO corPersonDTO = corPersonMapper.corPersonToCorPersonDTO(corPerson);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(corPersonDTO));
    }

    /**
     * DELETE  /cor-people/:id : delete the "id" corPerson.
     *
     * @param id the id of the corPersonDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cor-people/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorPerson(@PathVariable Long id) {
        log.debug("REST request to delete CorPerson : {}", id);
        corPersonRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
