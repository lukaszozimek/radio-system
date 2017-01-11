package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CRMContact;

import io.protone.repository.CRMContactRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CRMContactDTO;
import io.protone.service.mapper.CRMContactMapper;

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
 * REST controller for managing CRMContact.
 */
@RestController
@RequestMapping("/api")
public class CRMContactResource {

    private final Logger log = LoggerFactory.getLogger(CRMContactResource.class);
        
    @Inject
    private CRMContactRepository cRMContactRepository;

    @Inject
    private CRMContactMapper cRMContactMapper;

    /**
     * POST  /c-rm-contacts : Create a new cRMContact.
     *
     * @param cRMContactDTO the cRMContactDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cRMContactDTO, or with status 400 (Bad Request) if the cRMContact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-rm-contacts")
    @Timed
    public ResponseEntity<CRMContactDTO> createCRMContact(@RequestBody CRMContactDTO cRMContactDTO) throws URISyntaxException {
        log.debug("REST request to save CRMContact : {}", cRMContactDTO);
        if (cRMContactDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cRMContact", "idexists", "A new cRMContact cannot already have an ID")).body(null);
        }
        CRMContact cRMContact = cRMContactMapper.cRMContactDTOToCRMContact(cRMContactDTO);
        cRMContact = cRMContactRepository.save(cRMContact);
        CRMContactDTO result = cRMContactMapper.cRMContactToCRMContactDTO(cRMContact);
        return ResponseEntity.created(new URI("/api/c-rm-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cRMContact", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-rm-contacts : Updates an existing cRMContact.
     *
     * @param cRMContactDTO the cRMContactDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cRMContactDTO,
     * or with status 400 (Bad Request) if the cRMContactDTO is not valid,
     * or with status 500 (Internal Server Error) if the cRMContactDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-rm-contacts")
    @Timed
    public ResponseEntity<CRMContactDTO> updateCRMContact(@RequestBody CRMContactDTO cRMContactDTO) throws URISyntaxException {
        log.debug("REST request to update CRMContact : {}", cRMContactDTO);
        if (cRMContactDTO.getId() == null) {
            return createCRMContact(cRMContactDTO);
        }
        CRMContact cRMContact = cRMContactMapper.cRMContactDTOToCRMContact(cRMContactDTO);
        cRMContact = cRMContactRepository.save(cRMContact);
        CRMContactDTO result = cRMContactMapper.cRMContactToCRMContactDTO(cRMContact);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cRMContact", cRMContactDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-rm-contacts : get all the cRMContacts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cRMContacts in body
     */
    @GetMapping("/c-rm-contacts")
    @Timed
    public List<CRMContactDTO> getAllCRMContacts() {
        log.debug("REST request to get all CRMContacts");
        List<CRMContact> cRMContacts = cRMContactRepository.findAll();
        return cRMContactMapper.cRMContactsToCRMContactDTOs(cRMContacts);
    }

    /**
     * GET  /c-rm-contacts/:id : get the "id" cRMContact.
     *
     * @param id the id of the cRMContactDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cRMContactDTO, or with status 404 (Not Found)
     */
    @GetMapping("/c-rm-contacts/{id}")
    @Timed
    public ResponseEntity<CRMContactDTO> getCRMContact(@PathVariable Long id) {
        log.debug("REST request to get CRMContact : {}", id);
        CRMContact cRMContact = cRMContactRepository.findOne(id);
        CRMContactDTO cRMContactDTO = cRMContactMapper.cRMContactToCRMContactDTO(cRMContact);
        return Optional.ofNullable(cRMContactDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-rm-contacts/:id : delete the "id" cRMContact.
     *
     * @param id the id of the cRMContactDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-rm-contacts/{id}")
    @Timed
    public ResponseEntity<Void> deleteCRMContact(@PathVariable Long id) {
        log.debug("REST request to delete CRMContact : {}", id);
        cRMContactRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cRMContact", id.toString())).build();
    }

}
