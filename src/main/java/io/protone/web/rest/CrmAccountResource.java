package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CrmAccount;

import io.protone.repository.CrmAccountRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CrmAccountDTO;
import io.protone.service.mapper.CrmAccountMapper;
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
 * REST controller for managing CrmAccount.
 */
@RestController
@RequestMapping("/api")
public class CrmAccountResource {

    private final Logger log = LoggerFactory.getLogger(CrmAccountResource.class);

    private static final String ENTITY_NAME = "crmAccount";
        
    private final CrmAccountRepository crmAccountRepository;

    private final CrmAccountMapper crmAccountMapper;

    public CrmAccountResource(CrmAccountRepository crmAccountRepository, CrmAccountMapper crmAccountMapper) {
        this.crmAccountRepository = crmAccountRepository;
        this.crmAccountMapper = crmAccountMapper;
    }

    /**
     * POST  /crm-accounts : Create a new crmAccount.
     *
     * @param crmAccountDTO the crmAccountDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new crmAccountDTO, or with status 400 (Bad Request) if the crmAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/crm-accounts")
    @Timed
    public ResponseEntity<CrmAccountDTO> createCrmAccount(@RequestBody CrmAccountDTO crmAccountDTO) throws URISyntaxException {
        log.debug("REST request to save CrmAccount : {}", crmAccountDTO);
        if (crmAccountDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new crmAccount cannot already have an ID")).body(null);
        }
        CrmAccount crmAccount = crmAccountMapper.crmAccountDTOToCrmAccount(crmAccountDTO);
        crmAccount = crmAccountRepository.save(crmAccount);
        CrmAccountDTO result = crmAccountMapper.crmAccountToCrmAccountDTO(crmAccount);
        return ResponseEntity.created(new URI("/api/crm-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /crm-accounts : Updates an existing crmAccount.
     *
     * @param crmAccountDTO the crmAccountDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated crmAccountDTO,
     * or with status 400 (Bad Request) if the crmAccountDTO is not valid,
     * or with status 500 (Internal Server Error) if the crmAccountDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/crm-accounts")
    @Timed
    public ResponseEntity<CrmAccountDTO> updateCrmAccount(@RequestBody CrmAccountDTO crmAccountDTO) throws URISyntaxException {
        log.debug("REST request to update CrmAccount : {}", crmAccountDTO);
        if (crmAccountDTO.getId() == null) {
            return createCrmAccount(crmAccountDTO);
        }
        CrmAccount crmAccount = crmAccountMapper.crmAccountDTOToCrmAccount(crmAccountDTO);
        crmAccount = crmAccountRepository.save(crmAccount);
        CrmAccountDTO result = crmAccountMapper.crmAccountToCrmAccountDTO(crmAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, crmAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /crm-accounts : get all the crmAccounts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of crmAccounts in body
     */
    @GetMapping("/crm-accounts")
    @Timed
    public List<CrmAccountDTO> getAllCrmAccounts() {
        log.debug("REST request to get all CrmAccounts");
        List<CrmAccount> crmAccounts = crmAccountRepository.findAll();
        return crmAccountMapper.crmAccountsToCrmAccountDTOs(crmAccounts);
    }

    /**
     * GET  /crm-accounts/:id : get the "id" crmAccount.
     *
     * @param id the id of the crmAccountDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the crmAccountDTO, or with status 404 (Not Found)
     */
    @GetMapping("/crm-accounts/{id}")
    @Timed
    public ResponseEntity<CrmAccountDTO> getCrmAccount(@PathVariable Long id) {
        log.debug("REST request to get CrmAccount : {}", id);
        CrmAccount crmAccount = crmAccountRepository.findOne(id);
        CrmAccountDTO crmAccountDTO = crmAccountMapper.crmAccountToCrmAccountDTO(crmAccount);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(crmAccountDTO));
    }

    /**
     * DELETE  /crm-accounts/:id : delete the "id" crmAccount.
     *
     * @param id the id of the crmAccountDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/crm-accounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteCrmAccount(@PathVariable Long id) {
        log.debug("REST request to delete CrmAccount : {}", id);
        crmAccountRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
