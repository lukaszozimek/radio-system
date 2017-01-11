package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LIBLibrary;

import io.protone.repository.LIBLibraryRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.LIBLibraryDTO;
import io.protone.service.mapper.LIBLibraryMapper;

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
 * REST controller for managing LIBLibrary.
 */
@RestController
@RequestMapping("/api")
public class LIBLibraryResource {

    private final Logger log = LoggerFactory.getLogger(LIBLibraryResource.class);
        
    @Inject
    private LIBLibraryRepository lIBLibraryRepository;

    @Inject
    private LIBLibraryMapper lIBLibraryMapper;

    /**
     * POST  /l-ib-libraries : Create a new lIBLibrary.
     *
     * @param lIBLibraryDTO the lIBLibraryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lIBLibraryDTO, or with status 400 (Bad Request) if the lIBLibrary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/l-ib-libraries")
    @Timed
    public ResponseEntity<LIBLibraryDTO> createLIBLibrary(@Valid @RequestBody LIBLibraryDTO lIBLibraryDTO) throws URISyntaxException {
        log.debug("REST request to save LIBLibrary : {}", lIBLibraryDTO);
        if (lIBLibraryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lIBLibrary", "idexists", "A new lIBLibrary cannot already have an ID")).body(null);
        }
        LIBLibrary lIBLibrary = lIBLibraryMapper.lIBLibraryDTOToLIBLibrary(lIBLibraryDTO);
        lIBLibrary = lIBLibraryRepository.save(lIBLibrary);
        LIBLibraryDTO result = lIBLibraryMapper.lIBLibraryToLIBLibraryDTO(lIBLibrary);
        return ResponseEntity.created(new URI("/api/l-ib-libraries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lIBLibrary", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /l-ib-libraries : Updates an existing lIBLibrary.
     *
     * @param lIBLibraryDTO the lIBLibraryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lIBLibraryDTO,
     * or with status 400 (Bad Request) if the lIBLibraryDTO is not valid,
     * or with status 500 (Internal Server Error) if the lIBLibraryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/l-ib-libraries")
    @Timed
    public ResponseEntity<LIBLibraryDTO> updateLIBLibrary(@Valid @RequestBody LIBLibraryDTO lIBLibraryDTO) throws URISyntaxException {
        log.debug("REST request to update LIBLibrary : {}", lIBLibraryDTO);
        if (lIBLibraryDTO.getId() == null) {
            return createLIBLibrary(lIBLibraryDTO);
        }
        LIBLibrary lIBLibrary = lIBLibraryMapper.lIBLibraryDTOToLIBLibrary(lIBLibraryDTO);
        lIBLibrary = lIBLibraryRepository.save(lIBLibrary);
        LIBLibraryDTO result = lIBLibraryMapper.lIBLibraryToLIBLibraryDTO(lIBLibrary);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lIBLibrary", lIBLibraryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /l-ib-libraries : get all the lIBLibraries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lIBLibraries in body
     */
    @GetMapping("/l-ib-libraries")
    @Timed
    public List<LIBLibraryDTO> getAllLIBLibraries() {
        log.debug("REST request to get all LIBLibraries");
        List<LIBLibrary> lIBLibraries = lIBLibraryRepository.findAll();
        return lIBLibraryMapper.lIBLibrariesToLIBLibraryDTOs(lIBLibraries);
    }

    /**
     * GET  /l-ib-libraries/:id : get the "id" lIBLibrary.
     *
     * @param id the id of the lIBLibraryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lIBLibraryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/l-ib-libraries/{id}")
    @Timed
    public ResponseEntity<LIBLibraryDTO> getLIBLibrary(@PathVariable Long id) {
        log.debug("REST request to get LIBLibrary : {}", id);
        LIBLibrary lIBLibrary = lIBLibraryRepository.findOne(id);
        LIBLibraryDTO lIBLibraryDTO = lIBLibraryMapper.lIBLibraryToLIBLibraryDTO(lIBLibrary);
        return Optional.ofNullable(lIBLibraryDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /l-ib-libraries/:id : delete the "id" lIBLibrary.
     *
     * @param id the id of the lIBLibraryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/l-ib-libraries/{id}")
    @Timed
    public ResponseEntity<Void> deleteLIBLibrary(@PathVariable Long id) {
        log.debug("REST request to delete LIBLibrary : {}", id);
        lIBLibraryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lIBLibrary", id.toString())).build();
    }

}
