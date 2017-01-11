package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CRMAccount;

import io.protone.repository.CRMAccountRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CRMAccountDTO;
import io.protone.service.mapper.CRMAccountMapper;

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
 * REST controller for managing CRMAccount.
 */
@RestController
@RequestMapping("/api")
public class CRMAccountResource {

    private final Logger log = LoggerFactory.getLogger(CRMAccountResource.class);
        
    @Inject
    private CRMAccountRepository cRMAccountRepository;

    @Inject
    private CRMAccountMapper cRMAccountMapper;

    /**
     * POST  /c-rm-accounts : Create a new cRMAccount.
     *
     * @param cRMAccountDTO the cRMAccountDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cRMAccountDTO, or with status 400 (Bad Request) if the cRMAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-rm-accounts")
    @Timed
    public ResponseEntity<CRMAccountDTO> createCRMAccount(@RequestBody CRMAccountDTO cRMAccountDTO) throws URISyntaxException {
        log.debug("REST request to save CRMAccount : {}", cRMAccountDTO);
        if (cRMAccountDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cRMAccount", "idexists", "A new cRMAccount cannot already have an ID")).body(null);
        }
        CRMAccount cRMAccount = cRMAccountMapper.cRMAccountDTOToCRMAccount(cRMAccountDTO);
        cRMAccount = cRMAccountRepository.save(cRMAccount);
        CRMAccountDTO result = cRMAccountMapper.cRMAccountToCRMAccountDTO(cRMAccount);
        return ResponseEntity.created(new URI("/api/c-rm-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cRMAccount", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-rm-accounts : Updates an existing cRMAccount.
     *
     * @param cRMAccountDTO the cRMAccountDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cRMAccountDTO,
     * or with status 400 (Bad Request) if the cRMAccountDTO is not valid,
     * or with status 500 (Internal Server Error) if the cRMAccountDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-rm-accounts")
    @Timed
    public ResponseEntity<CRMAccountDTO> updateCRMAccount(@RequestBody CRMAccountDTO cRMAccountDTO) throws URISyntaxException {
        log.debug("REST request to update CRMAccount : {}", cRMAccountDTO);
        if (cRMAccountDTO.getId() == null) {
            return createCRMAccount(cRMAccountDTO);
        }
        CRMAccount cRMAccount = cRMAccountMapper.cRMAccountDTOToCRMAccount(cRMAccountDTO);
        cRMAccount = cRMAccountRepository.save(cRMAccount);
        CRMAccountDTO result = cRMAccountMapper.cRMAccountToCRMAccountDTO(cRMAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cRMAccount", cRMAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-rm-accounts : get all the cRMAccounts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cRMAccounts in body
     */
    @GetMapping("/c-rm-accounts")
    @Timed
    public List<CRMAccountDTO> getAllCRMAccounts() {
        log.debug("REST request to get all CRMAccounts");
        List<CRMAccount> cRMAccounts = cRMAccountRepository.findAll();
        return cRMAccountMapper.cRMAccountsToCRMAccountDTOs(cRMAccounts);
    }

    /**
     * GET  /c-rm-accounts/:id : get the "id" cRMAccount.
     *
     * @param id the id of the cRMAccountDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cRMAccountDTO, or with status 404 (Not Found)
     */
    @GetMapping("/c-rm-accounts/{id}")
    @Timed
    public ResponseEntity<CRMAccountDTO> getCRMAccount(@PathVariable Long id) {
        log.debug("REST request to get CRMAccount : {}", id);
        CRMAccount cRMAccount = cRMAccountRepository.findOne(id);
        CRMAccountDTO cRMAccountDTO = cRMAccountMapper.cRMAccountToCRMAccountDTO(cRMAccount);
        return Optional.ofNullable(cRMAccountDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-rm-accounts/:id : delete the "id" cRMAccount.
     *
     * @param id the id of the cRMAccountDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-rm-accounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteCRMAccount(@PathVariable Long id) {
        log.debug("REST request to delete CRMAccount : {}", id);
        cRMAccountRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cRMAccount", id.toString())).build();
    }

}
