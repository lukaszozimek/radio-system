package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CORDocument;

import io.protone.repository.CORDocumentRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CORDocumentDTO;
import io.protone.service.mapper.CORDocumentMapper;

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
 * REST controller for managing CORDocument.
 */
@RestController
@RequestMapping("/api")
public class CORDocumentResource {

    private final Logger log = LoggerFactory.getLogger(CORDocumentResource.class);
        
    @Inject
    private CORDocumentRepository cORDocumentRepository;

    @Inject
    private CORDocumentMapper cORDocumentMapper;

    /**
     * POST  /c-or-documents : Create a new cORDocument.
     *
     * @param cORDocumentDTO the cORDocumentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cORDocumentDTO, or with status 400 (Bad Request) if the cORDocument has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-or-documents")
    @Timed
    public ResponseEntity<CORDocumentDTO> createCORDocument(@Valid @RequestBody CORDocumentDTO cORDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save CORDocument : {}", cORDocumentDTO);
        if (cORDocumentDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORDocument", "idexists", "A new cORDocument cannot already have an ID")).body(null);
        }
        CORDocument cORDocument = cORDocumentMapper.cORDocumentDTOToCORDocument(cORDocumentDTO);
        cORDocument = cORDocumentRepository.save(cORDocument);
        CORDocumentDTO result = cORDocumentMapper.cORDocumentToCORDocumentDTO(cORDocument);
        return ResponseEntity.created(new URI("/api/c-or-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cORDocument", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-or-documents : Updates an existing cORDocument.
     *
     * @param cORDocumentDTO the cORDocumentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cORDocumentDTO,
     * or with status 400 (Bad Request) if the cORDocumentDTO is not valid,
     * or with status 500 (Internal Server Error) if the cORDocumentDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-or-documents")
    @Timed
    public ResponseEntity<CORDocumentDTO> updateCORDocument(@Valid @RequestBody CORDocumentDTO cORDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update CORDocument : {}", cORDocumentDTO);
        if (cORDocumentDTO.getId() == null) {
            return createCORDocument(cORDocumentDTO);
        }
        CORDocument cORDocument = cORDocumentMapper.cORDocumentDTOToCORDocument(cORDocumentDTO);
        cORDocument = cORDocumentRepository.save(cORDocument);
        CORDocumentDTO result = cORDocumentMapper.cORDocumentToCORDocumentDTO(cORDocument);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORDocument", cORDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-or-documents : get all the cORDocuments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cORDocuments in body
     */
    @GetMapping("/c-or-documents")
    @Timed
    public List<CORDocumentDTO> getAllCORDocuments() {
        log.debug("REST request to get all CORDocuments");
        List<CORDocument> cORDocuments = cORDocumentRepository.findAll();
        return cORDocumentMapper.cORDocumentsToCORDocumentDTOs(cORDocuments);
    }

    /**
     * GET  /c-or-documents/:id : get the "id" cORDocument.
     *
     * @param id the id of the cORDocumentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cORDocumentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/c-or-documents/{id}")
    @Timed
    public ResponseEntity<CORDocumentDTO> getCORDocument(@PathVariable Long id) {
        log.debug("REST request to get CORDocument : {}", id);
        CORDocument cORDocument = cORDocumentRepository.findOne(id);
        CORDocumentDTO cORDocumentDTO = cORDocumentMapper.cORDocumentToCORDocumentDTO(cORDocument);
        return Optional.ofNullable(cORDocumentDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-or-documents/:id : delete the "id" cORDocument.
     *
     * @param id the id of the cORDocumentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-or-documents/{id}")
    @Timed
    public ResponseEntity<Void> deleteCORDocument(@PathVariable Long id) {
        log.debug("REST request to delete CORDocument : {}", id);
        cORDocumentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORDocument", id.toString())).build();
    }

}
