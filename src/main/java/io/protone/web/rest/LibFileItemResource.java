package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LibFileItem;

import io.protone.repository.LibFileItemRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.LibFileItemDTO;
import io.protone.service.mapper.LibFileItemMapper;
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
 * REST controller for managing LibFileItem.
 */
@RestController
@RequestMapping("/api")
public class LibFileItemResource {

    private final Logger log = LoggerFactory.getLogger(LibFileItemResource.class);

    private static final String ENTITY_NAME = "libFileItem";
        
    private final LibFileItemRepository libFileItemRepository;

    private final LibFileItemMapper libFileItemMapper;

    public LibFileItemResource(LibFileItemRepository libFileItemRepository, LibFileItemMapper libFileItemMapper) {
        this.libFileItemRepository = libFileItemRepository;
        this.libFileItemMapper = libFileItemMapper;
    }

    /**
     * POST  /lib-file-items : Create a new libFileItem.
     *
     * @param libFileItemDTO the libFileItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new libFileItemDTO, or with status 400 (Bad Request) if the libFileItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lib-file-items")
    @Timed
    public ResponseEntity<LibFileItemDTO> createLibFileItem(@Valid @RequestBody LibFileItemDTO libFileItemDTO) throws URISyntaxException {
        log.debug("REST request to save LibFileItem : {}", libFileItemDTO);
        if (libFileItemDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new libFileItem cannot already have an ID")).body(null);
        }
        LibFileItem libFileItem = libFileItemMapper.libFileItemDTOToLibFileItem(libFileItemDTO);
        libFileItem = libFileItemRepository.save(libFileItem);
        LibFileItemDTO result = libFileItemMapper.libFileItemToLibFileItemDTO(libFileItem);
        return ResponseEntity.created(new URI("/api/lib-file-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lib-file-items : Updates an existing libFileItem.
     *
     * @param libFileItemDTO the libFileItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated libFileItemDTO,
     * or with status 400 (Bad Request) if the libFileItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the libFileItemDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lib-file-items")
    @Timed
    public ResponseEntity<LibFileItemDTO> updateLibFileItem(@Valid @RequestBody LibFileItemDTO libFileItemDTO) throws URISyntaxException {
        log.debug("REST request to update LibFileItem : {}", libFileItemDTO);
        if (libFileItemDTO.getId() == null) {
            return createLibFileItem(libFileItemDTO);
        }
        LibFileItem libFileItem = libFileItemMapper.libFileItemDTOToLibFileItem(libFileItemDTO);
        libFileItem = libFileItemRepository.save(libFileItem);
        LibFileItemDTO result = libFileItemMapper.libFileItemToLibFileItemDTO(libFileItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, libFileItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lib-file-items : get all the libFileItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of libFileItems in body
     */
    @GetMapping("/lib-file-items")
    @Timed
    public List<LibFileItemDTO> getAllLibFileItems() {
        log.debug("REST request to get all LibFileItems");
        List<LibFileItem> libFileItems = libFileItemRepository.findAll();
        return libFileItemMapper.libFileItemsToLibFileItemDTOs(libFileItems);
    }

    /**
     * GET  /lib-file-items/:id : get the "id" libFileItem.
     *
     * @param id the id of the libFileItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the libFileItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lib-file-items/{id}")
    @Timed
    public ResponseEntity<LibFileItemDTO> getLibFileItem(@PathVariable Long id) {
        log.debug("REST request to get LibFileItem : {}", id);
        LibFileItem libFileItem = libFileItemRepository.findOne(id);
        LibFileItemDTO libFileItemDTO = libFileItemMapper.libFileItemToLibFileItemDTO(libFileItem);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(libFileItemDTO));
    }

    /**
     * DELETE  /lib-file-items/:id : delete the "id" libFileItem.
     *
     * @param id the id of the libFileItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lib-file-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteLibFileItem(@PathVariable Long id) {
        log.debug("REST request to delete LibFileItem : {}", id);
        libFileItemRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
