package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CORPerson;

import io.protone.repository.CORPersonRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CORPersonDTO;
import io.protone.service.mapper.CORPersonMapper;

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
 * REST controller for managing CORPerson.
 */
@RestController
@RequestMapping("/api")
public class CORPersonResource {

    private final Logger log = LoggerFactory.getLogger(CORPersonResource.class);
        
    @Inject
    private CORPersonRepository cORPersonRepository;

    @Inject
    private CORPersonMapper cORPersonMapper;

    /**
     * POST  /c-or-people : Create a new cORPerson.
     *
     * @param cORPersonDTO the cORPersonDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cORPersonDTO, or with status 400 (Bad Request) if the cORPerson has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-or-people")
    @Timed
    public ResponseEntity<CORPersonDTO> createCORPerson(@Valid @RequestBody CORPersonDTO cORPersonDTO) throws URISyntaxException {
        log.debug("REST request to save CORPerson : {}", cORPersonDTO);
        if (cORPersonDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORPerson", "idexists", "A new cORPerson cannot already have an ID")).body(null);
        }
        CORPerson cORPerson = cORPersonMapper.cORPersonDTOToCORPerson(cORPersonDTO);
        cORPerson = cORPersonRepository.save(cORPerson);
        CORPersonDTO result = cORPersonMapper.cORPersonToCORPersonDTO(cORPerson);
        return ResponseEntity.created(new URI("/api/c-or-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cORPerson", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-or-people : Updates an existing cORPerson.
     *
     * @param cORPersonDTO the cORPersonDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cORPersonDTO,
     * or with status 400 (Bad Request) if the cORPersonDTO is not valid,
     * or with status 500 (Internal Server Error) if the cORPersonDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-or-people")
    @Timed
    public ResponseEntity<CORPersonDTO> updateCORPerson(@Valid @RequestBody CORPersonDTO cORPersonDTO) throws URISyntaxException {
        log.debug("REST request to update CORPerson : {}", cORPersonDTO);
        if (cORPersonDTO.getId() == null) {
            return createCORPerson(cORPersonDTO);
        }
        CORPerson cORPerson = cORPersonMapper.cORPersonDTOToCORPerson(cORPersonDTO);
        cORPerson = cORPersonRepository.save(cORPerson);
        CORPersonDTO result = cORPersonMapper.cORPersonToCORPersonDTO(cORPerson);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORPerson", cORPersonDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-or-people : get all the cORPeople.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cORPeople in body
     */
    @GetMapping("/c-or-people")
    @Timed
    public List<CORPersonDTO> getAllCORPeople() {
        log.debug("REST request to get all CORPeople");
        List<CORPerson> cORPeople = cORPersonRepository.findAll();
        return cORPersonMapper.cORPeopleToCORPersonDTOs(cORPeople);
    }

    /**
     * GET  /c-or-people/:id : get the "id" cORPerson.
     *
     * @param id the id of the cORPersonDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cORPersonDTO, or with status 404 (Not Found)
     */
    @GetMapping("/c-or-people/{id}")
    @Timed
    public ResponseEntity<CORPersonDTO> getCORPerson(@PathVariable Long id) {
        log.debug("REST request to get CORPerson : {}", id);
        CORPerson cORPerson = cORPersonRepository.findOne(id);
        CORPersonDTO cORPersonDTO = cORPersonMapper.cORPersonToCORPersonDTO(cORPerson);
        return Optional.ofNullable(cORPersonDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-or-people/:id : delete the "id" cORPerson.
     *
     * @param id the id of the cORPersonDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-or-people/{id}")
    @Timed
    public ResponseEntity<Void> deleteCORPerson(@PathVariable Long id) {
        log.debug("REST request to delete CORPerson : {}", id);
        cORPersonRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORPerson", id.toString())).build();
    }

}
