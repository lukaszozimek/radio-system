package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CORContact;

import io.protone.repository.CORContactRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CORContactDTO;
import io.protone.service.mapper.CORContactMapper;

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
 * REST controller for managing CORContact.
 */
@RestController
@RequestMapping("/api")
public class CORContactResource {

    private final Logger log = LoggerFactory.getLogger(CORContactResource.class);
        
    @Inject
    private CORContactRepository cORContactRepository;

    @Inject
    private CORContactMapper cORContactMapper;

    /**
     * POST  /c-or-contacts : Create a new cORContact.
     *
     * @param cORContactDTO the cORContactDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cORContactDTO, or with status 400 (Bad Request) if the cORContact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-or-contacts")
    @Timed
    public ResponseEntity<CORContactDTO> createCORContact(@Valid @RequestBody CORContactDTO cORContactDTO) throws URISyntaxException {
        log.debug("REST request to save CORContact : {}", cORContactDTO);
        if (cORContactDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORContact", "idexists", "A new cORContact cannot already have an ID")).body(null);
        }
        CORContact cORContact = cORContactMapper.cORContactDTOToCORContact(cORContactDTO);
        cORContact = cORContactRepository.save(cORContact);
        CORContactDTO result = cORContactMapper.cORContactToCORContactDTO(cORContact);
        return ResponseEntity.created(new URI("/api/c-or-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cORContact", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-or-contacts : Updates an existing cORContact.
     *
     * @param cORContactDTO the cORContactDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cORContactDTO,
     * or with status 400 (Bad Request) if the cORContactDTO is not valid,
     * or with status 500 (Internal Server Error) if the cORContactDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-or-contacts")
    @Timed
    public ResponseEntity<CORContactDTO> updateCORContact(@Valid @RequestBody CORContactDTO cORContactDTO) throws URISyntaxException {
        log.debug("REST request to update CORContact : {}", cORContactDTO);
        if (cORContactDTO.getId() == null) {
            return createCORContact(cORContactDTO);
        }
        CORContact cORContact = cORContactMapper.cORContactDTOToCORContact(cORContactDTO);
        cORContact = cORContactRepository.save(cORContact);
        CORContactDTO result = cORContactMapper.cORContactToCORContactDTO(cORContact);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORContact", cORContactDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-or-contacts : get all the cORContacts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cORContacts in body
     */
    @GetMapping("/c-or-contacts")
    @Timed
    public List<CORContactDTO> getAllCORContacts() {
        log.debug("REST request to get all CORContacts");
        List<CORContact> cORContacts = cORContactRepository.findAll();
        return cORContactMapper.cORContactsToCORContactDTOs(cORContacts);
    }

    /**
     * GET  /c-or-contacts/:id : get the "id" cORContact.
     *
     * @param id the id of the cORContactDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cORContactDTO, or with status 404 (Not Found)
     */
    @GetMapping("/c-or-contacts/{id}")
    @Timed
    public ResponseEntity<CORContactDTO> getCORContact(@PathVariable Long id) {
        log.debug("REST request to get CORContact : {}", id);
        CORContact cORContact = cORContactRepository.findOne(id);
        CORContactDTO cORContactDTO = cORContactMapper.cORContactToCORContactDTO(cORContact);
        return Optional.ofNullable(cORContactDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-or-contacts/:id : delete the "id" cORContact.
     *
     * @param id the id of the cORContactDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-or-contacts/{id}")
    @Timed
    public ResponseEntity<Void> deleteCORContact(@PathVariable Long id) {
        log.debug("REST request to delete CORContact : {}", id);
        cORContactRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORContact", id.toString())).build();
    }

}
