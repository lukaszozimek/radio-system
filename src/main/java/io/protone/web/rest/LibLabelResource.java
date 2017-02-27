package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LibLabel;

import io.protone.repository.LibLabelRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.LibLabelDTO;
import io.protone.service.mapper.LibLabelMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LibLabel.
 */
@RestController
@RequestMapping("/api")
public class LibLabelResource {

    private final Logger log = LoggerFactory.getLogger(LibLabelResource.class);

    private static final String ENTITY_NAME = "libLabel";

    private final LibLabelRepository libLabelRepository;

    private final LibLabelMapper libLabelMapper;

    public LibLabelResource(LibLabelRepository libLabelRepository, LibLabelMapper libLabelMapper) {
        this.libLabelRepository = libLabelRepository;
        this.libLabelMapper = libLabelMapper;
    }

    /**
     * POST  /lib-labels : Create a new libLabel.
     *
     * @param libLabelDTO the libLabelDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new libLabelDTO, or with status 400 (Bad Request) if the libLabel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lib-labels")
    @Timed
    public ResponseEntity<LibLabelDTO> createLibLabel(@Valid @RequestBody LibLabelDTO libLabelDTO) throws URISyntaxException {
        log.debug("REST request to save LibLabel : {}", libLabelDTO);
        if (libLabelDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new libLabel cannot already have an ID")).body(null);
        }
        LibLabel libLabel = libLabelMapper.libLabelDTOToLibLabel(libLabelDTO);
        libLabel = libLabelRepository.save(libLabel);
        LibLabelDTO result = libLabelMapper.libLabelToLibLabelDTO(libLabel);
        return ResponseEntity.created(new URI("/api/lib-labels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lib-labels : Updates an existing libLabel.
     *
     * @param libLabelDTO the libLabelDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated libLabelDTO,
     * or with status 400 (Bad Request) if the libLabelDTO is not valid,
     * or with status 500 (Internal Server Error) if the libLabelDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lib-labels")
    @Timed
    public ResponseEntity<LibLabelDTO> updateLibLabel(@Valid @RequestBody LibLabelDTO libLabelDTO) throws URISyntaxException {
        log.debug("REST request to update LibLabel : {}", libLabelDTO);
        if (libLabelDTO.getId() == null) {
            return createLibLabel(libLabelDTO);
        }
        LibLabel libLabel = libLabelMapper.libLabelDTOToLibLabel(libLabelDTO);
        libLabel = libLabelRepository.save(libLabel);
        LibLabelDTO result = libLabelMapper.libLabelToLibLabelDTO(libLabel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, libLabelDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lib-labels : get all the libLabels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of libLabels in body
     */
    @GetMapping("/lib-labels")
    @Timed
    public List<LibLabelDTO> getAllLibLabels() {
        log.debug("REST request to get all LibLabels");
        List<LibLabel> libLabels = libLabelRepository.findAll();
        return libLabelMapper.libLabelsToLibLabelDTOs(libLabels);
    }

    /**
     * GET  /lib-labels/:id : get the "id" libLabel.
     *
     * @param id the id of the libLabelDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the libLabelDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lib-labels/{id}")
    @Timed
    public ResponseEntity<LibLabelDTO> getLibLabel(@PathVariable Long id) {
        log.debug("REST request to get LibLabel : {}", id);
        LibLabel libLabel = libLabelRepository.findOne(id);
        LibLabelDTO libLabelDTO = libLabelMapper.libLabelToLibLabelDTO(libLabel);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(libLabelDTO));
    }

    /**
     * DELETE  /lib-labels/:id : delete the "id" libLabel.
     *
     * @param id the id of the libLabelDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lib-labels/{id}")
    @Timed
    public ResponseEntity<Void> deleteLibLabel(@PathVariable Long id) {
        log.debug("REST request to delete LibLabel : {}", id);
        libLabelRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
