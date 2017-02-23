package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CorContact;

import io.protone.repository.CorContactRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CorContactDTO;
import io.protone.service.mapper.CorContactMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing CorContact.
 */
@RestController
@RequestMapping("/api")
public class CorContactResource {

    private final Logger log = LoggerFactory.getLogger(CorContactResource.class);

    private static final String ENTITY_NAME = "corContact";
        
    private final CorContactRepository corContactRepository;

    private final CorContactMapper corContactMapper;

    public CorContactResource(CorContactRepository corContactRepository, CorContactMapper corContactMapper) {
        this.corContactRepository = corContactRepository;
        this.corContactMapper = corContactMapper;
    }

    /**
     * POST  /cor-contacts : Create a new corContact.
     *
     * @param corContactDTO the corContactDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new corContactDTO, or with status 400 (Bad Request) if the corContact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cor-contacts")
    @Timed
    public ResponseEntity<CorContactDTO> createCorContact(@Valid @RequestBody CorContactDTO corContactDTO) throws URISyntaxException {
        log.debug("REST request to save CorContact : {}", corContactDTO);
        if (corContactDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new corContact cannot already have an ID")).body(null);
        }
        CorContact corContact = corContactMapper.corContactDTOToCorContact(corContactDTO);
        corContact = corContactRepository.save(corContact);
        CorContactDTO result = corContactMapper.corContactToCorContactDTO(corContact);
        return ResponseEntity.created(new URI("/api/cor-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cor-contacts : Updates an existing corContact.
     *
     * @param corContactDTO the corContactDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated corContactDTO,
     * or with status 400 (Bad Request) if the corContactDTO is not valid,
     * or with status 500 (Internal Server Error) if the corContactDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cor-contacts")
    @Timed
    public ResponseEntity<CorContactDTO> updateCorContact(@Valid @RequestBody CorContactDTO corContactDTO) throws URISyntaxException {
        log.debug("REST request to update CorContact : {}", corContactDTO);
        if (corContactDTO.getId() == null) {
            return createCorContact(corContactDTO);
        }
        CorContact corContact = corContactMapper.corContactDTOToCorContact(corContactDTO);
        corContact = corContactRepository.save(corContact);
        CorContactDTO result = corContactMapper.corContactToCorContactDTO(corContact);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, corContactDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cor-contacts : get all the corContacts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of corContacts in body
     */
    @GetMapping("/cor-contacts")
    @Timed
    public List<CorContactDTO> getAllCorContacts() {
        log.debug("REST request to get all CorContacts");
        List<CorContact> corContacts = corContactRepository.findAll();
        return corContactMapper.corContactsToCorContactDTOs(corContacts);
    }

    /**
     * GET  /cor-contacts/:id : get the "id" corContact.
     *
     * @param id the id of the corContactDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the corContactDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cor-contacts/{id}")
    @Timed
    public ResponseEntity<CorContactDTO> getCorContact(@PathVariable Long id) {
        log.debug("REST request to get CorContact : {}", id);
        CorContact corContact = corContactRepository.findOne(id);
        CorContactDTO corContactDTO = corContactMapper.corContactToCorContactDTO(corContact);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(corContactDTO));
    }

    /**
     * DELETE  /cor-contacts/:id : delete the "id" corContact.
     *
     * @param id the id of the corContactDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cor-contacts/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorContact(@PathVariable Long id) {
        log.debug("REST request to delete CorContact : {}", id);
        corContactRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
