package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CorUser;

import io.protone.repository.CorUserRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CorUserDTO;
import io.protone.service.mapper.CorUserMapper;
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
 * REST controller for managing CorUser.
 */
@RestController
@RequestMapping("/api")
public class CorUserResource {

    private final Logger log = LoggerFactory.getLogger(CorUserResource.class);

    private static final String ENTITY_NAME = "corUser";
        
    private final CorUserRepository corUserRepository;

    private final CorUserMapper corUserMapper;

    public CorUserResource(CorUserRepository corUserRepository, CorUserMapper corUserMapper) {
        this.corUserRepository = corUserRepository;
        this.corUserMapper = corUserMapper;
    }

    /**
     * POST  /cor-users : Create a new corUser.
     *
     * @param corUserDTO the corUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new corUserDTO, or with status 400 (Bad Request) if the corUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cor-users")
    @Timed
    public ResponseEntity<CorUserDTO> createCorUser(@RequestBody CorUserDTO corUserDTO) throws URISyntaxException {
        log.debug("REST request to save CorUser : {}", corUserDTO);
        if (corUserDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new corUser cannot already have an ID")).body(null);
        }
        CorUser corUser = corUserMapper.corUserDTOToCorUser(corUserDTO);
        corUser = corUserRepository.save(corUser);
        CorUserDTO result = corUserMapper.corUserToCorUserDTO(corUser);
        return ResponseEntity.created(new URI("/api/cor-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cor-users : Updates an existing corUser.
     *
     * @param corUserDTO the corUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated corUserDTO,
     * or with status 400 (Bad Request) if the corUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the corUserDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cor-users")
    @Timed
    public ResponseEntity<CorUserDTO> updateCorUser(@RequestBody CorUserDTO corUserDTO) throws URISyntaxException {
        log.debug("REST request to update CorUser : {}", corUserDTO);
        if (corUserDTO.getId() == null) {
            return createCorUser(corUserDTO);
        }
        CorUser corUser = corUserMapper.corUserDTOToCorUser(corUserDTO);
        corUser = corUserRepository.save(corUser);
        CorUserDTO result = corUserMapper.corUserToCorUserDTO(corUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, corUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cor-users : get all the corUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of corUsers in body
     */
    @GetMapping("/cor-users")
    @Timed
    public List<CorUserDTO> getAllCorUsers() {
        log.debug("REST request to get all CorUsers");
        List<CorUser> corUsers = corUserRepository.findAll();
        return corUserMapper.corUsersToCorUserDTOs(corUsers);
    }

    /**
     * GET  /cor-users/:id : get the "id" corUser.
     *
     * @param id the id of the corUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the corUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cor-users/{id}")
    @Timed
    public ResponseEntity<CorUserDTO> getCorUser(@PathVariable Long id) {
        log.debug("REST request to get CorUser : {}", id);
        CorUser corUser = corUserRepository.findOne(id);
        CorUserDTO corUserDTO = corUserMapper.corUserToCorUserDTO(corUser);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(corUserDTO));
    }

    /**
     * DELETE  /cor-users/:id : delete the "id" corUser.
     *
     * @param id the id of the corUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cor-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorUser(@PathVariable Long id) {
        log.debug("REST request to delete CorUser : {}", id);
        corUserRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
