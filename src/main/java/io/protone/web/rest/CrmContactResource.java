package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CrmContact;

import io.protone.repository.CrmContactRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CrmContactDTO;
import io.protone.service.mapper.CrmContactMapper;
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
 * REST controller for managing CrmContact.
 */
@RestController
@RequestMapping("/api")
public class CrmContactResource {

    private final Logger log = LoggerFactory.getLogger(CrmContactResource.class);

    private static final String ENTITY_NAME = "crmContact";
        
    private final CrmContactRepository crmContactRepository;

    private final CrmContactMapper crmContactMapper;

    public CrmContactResource(CrmContactRepository crmContactRepository, CrmContactMapper crmContactMapper) {
        this.crmContactRepository = crmContactRepository;
        this.crmContactMapper = crmContactMapper;
    }

    /**
     * POST  /crm-contacts : Create a new crmContact.
     *
     * @param crmContactDTO the crmContactDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new crmContactDTO, or with status 400 (Bad Request) if the crmContact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/crm-contacts")
    @Timed
    public ResponseEntity<CrmContactDTO> createCrmContact(@RequestBody CrmContactDTO crmContactDTO) throws URISyntaxException {
        log.debug("REST request to save CrmContact : {}", crmContactDTO);
        if (crmContactDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new crmContact cannot already have an ID")).body(null);
        }
        CrmContact crmContact = crmContactMapper.crmContactDTOToCrmContact(crmContactDTO);
        crmContact = crmContactRepository.save(crmContact);
        CrmContactDTO result = crmContactMapper.crmContactToCrmContactDTO(crmContact);
        return ResponseEntity.created(new URI("/api/crm-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /crm-contacts : Updates an existing crmContact.
     *
     * @param crmContactDTO the crmContactDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated crmContactDTO,
     * or with status 400 (Bad Request) if the crmContactDTO is not valid,
     * or with status 500 (Internal Server Error) if the crmContactDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/crm-contacts")
    @Timed
    public ResponseEntity<CrmContactDTO> updateCrmContact(@RequestBody CrmContactDTO crmContactDTO) throws URISyntaxException {
        log.debug("REST request to update CrmContact : {}", crmContactDTO);
        if (crmContactDTO.getId() == null) {
            return createCrmContact(crmContactDTO);
        }
        CrmContact crmContact = crmContactMapper.crmContactDTOToCrmContact(crmContactDTO);
        crmContact = crmContactRepository.save(crmContact);
        CrmContactDTO result = crmContactMapper.crmContactToCrmContactDTO(crmContact);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, crmContactDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /crm-contacts : get all the crmContacts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of crmContacts in body
     */
    @GetMapping("/crm-contacts")
    @Timed
    public List<CrmContactDTO> getAllCrmContacts() {
        log.debug("REST request to get all CrmContacts");
        List<CrmContact> crmContacts = crmContactRepository.findAll();
        return crmContactMapper.crmContactsToCrmContactDTOs(crmContacts);
    }

    /**
     * GET  /crm-contacts/:id : get the "id" crmContact.
     *
     * @param id the id of the crmContactDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the crmContactDTO, or with status 404 (Not Found)
     */
    @GetMapping("/crm-contacts/{id}")
    @Timed
    public ResponseEntity<CrmContactDTO> getCrmContact(@PathVariable Long id) {
        log.debug("REST request to get CrmContact : {}", id);
        CrmContact crmContact = crmContactRepository.findOne(id);
        CrmContactDTO crmContactDTO = crmContactMapper.crmContactToCrmContactDTO(crmContact);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(crmContactDTO));
    }

    /**
     * DELETE  /crm-contacts/:id : delete the "id" crmContact.
     *
     * @param id the id of the crmContactDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/crm-contacts/{id}")
    @Timed
    public ResponseEntity<Void> deleteCrmContact(@PathVariable Long id) {
        log.debug("REST request to delete CrmContact : {}", id);
        crmContactRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
