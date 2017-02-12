package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LibImageItem;

import io.protone.repository.LibImageItemRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.LibImageItemDTO;
import io.protone.service.mapper.LibImageItemMapper;
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
 * REST controller for managing LibImageItem.
 */
@RestController
@RequestMapping("/api")
public class LibImageItemResource {

    private final Logger log = LoggerFactory.getLogger(LibImageItemResource.class);

    private static final String ENTITY_NAME = "libImageItem";
        
    private final LibImageItemRepository libImageItemRepository;

    private final LibImageItemMapper libImageItemMapper;

    public LibImageItemResource(LibImageItemRepository libImageItemRepository, LibImageItemMapper libImageItemMapper) {
        this.libImageItemRepository = libImageItemRepository;
        this.libImageItemMapper = libImageItemMapper;
    }

    /**
     * POST  /lib-image-items : Create a new libImageItem.
     *
     * @param libImageItemDTO the libImageItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new libImageItemDTO, or with status 400 (Bad Request) if the libImageItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lib-image-items")
    @Timed
    public ResponseEntity<LibImageItemDTO> createLibImageItem(@Valid @RequestBody LibImageItemDTO libImageItemDTO) throws URISyntaxException {
        log.debug("REST request to save LibImageItem : {}", libImageItemDTO);
        if (libImageItemDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new libImageItem cannot already have an ID")).body(null);
        }
        LibImageItem libImageItem = libImageItemMapper.libImageItemDTOToLibImageItem(libImageItemDTO);
        libImageItem = libImageItemRepository.save(libImageItem);
        LibImageItemDTO result = libImageItemMapper.libImageItemToLibImageItemDTO(libImageItem);
        return ResponseEntity.created(new URI("/api/lib-image-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lib-image-items : Updates an existing libImageItem.
     *
     * @param libImageItemDTO the libImageItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated libImageItemDTO,
     * or with status 400 (Bad Request) if the libImageItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the libImageItemDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lib-image-items")
    @Timed
    public ResponseEntity<LibImageItemDTO> updateLibImageItem(@Valid @RequestBody LibImageItemDTO libImageItemDTO) throws URISyntaxException {
        log.debug("REST request to update LibImageItem : {}", libImageItemDTO);
        if (libImageItemDTO.getId() == null) {
            return createLibImageItem(libImageItemDTO);
        }
        LibImageItem libImageItem = libImageItemMapper.libImageItemDTOToLibImageItem(libImageItemDTO);
        libImageItem = libImageItemRepository.save(libImageItem);
        LibImageItemDTO result = libImageItemMapper.libImageItemToLibImageItemDTO(libImageItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, libImageItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lib-image-items : get all the libImageItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of libImageItems in body
     */
    @GetMapping("/lib-image-items")
    @Timed
    public List<LibImageItemDTO> getAllLibImageItems() {
        log.debug("REST request to get all LibImageItems");
        List<LibImageItem> libImageItems = libImageItemRepository.findAll();
        return libImageItemMapper.libImageItemsToLibImageItemDTOs(libImageItems);
    }

    /**
     * GET  /lib-image-items/:id : get the "id" libImageItem.
     *
     * @param id the id of the libImageItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the libImageItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lib-image-items/{id}")
    @Timed
    public ResponseEntity<LibImageItemDTO> getLibImageItem(@PathVariable Long id) {
        log.debug("REST request to get LibImageItem : {}", id);
        LibImageItem libImageItem = libImageItemRepository.findOne(id);
        LibImageItemDTO libImageItemDTO = libImageItemMapper.libImageItemToLibImageItemDTO(libImageItem);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(libImageItemDTO));
    }

    /**
     * DELETE  /lib-image-items/:id : delete the "id" libImageItem.
     *
     * @param id the id of the libImageItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lib-image-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteLibImageItem(@PathVariable Long id) {
        log.debug("REST request to delete LibImageItem : {}", id);
        libImageItemRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
