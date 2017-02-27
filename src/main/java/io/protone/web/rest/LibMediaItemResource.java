package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LibMediaItem;

import io.protone.repository.LibMediaItemRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.LibMediaItemDTO;
import io.protone.service.mapper.LibMediaItemMapper;
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
 * REST controller for managing LibMediaItem.
 */
@RestController
@RequestMapping("/api")
public class LibMediaItemResource {

    private final Logger log = LoggerFactory.getLogger(LibMediaItemResource.class);

    private static final String ENTITY_NAME = "libMediaItem";

    private final LibMediaItemRepository libMediaItemRepository;

    private final LibMediaItemMapper libMediaItemMapper;

    public LibMediaItemResource(LibMediaItemRepository libMediaItemRepository, LibMediaItemMapper libMediaItemMapper) {
        this.libMediaItemRepository = libMediaItemRepository;
        this.libMediaItemMapper = libMediaItemMapper;
    }

    /**
     * POST  /lib-media-items : Create a new libMediaItem.
     *
     * @param libMediaItemDTO the libMediaItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new libMediaItemDTO, or with status 400 (Bad Request) if the libMediaItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lib-media-items")
    @Timed
    public ResponseEntity<LibMediaItemDTO> createLibMediaItem(@Valid @RequestBody LibMediaItemDTO libMediaItemDTO) throws URISyntaxException {
        log.debug("REST request to save LibMediaItem : {}", libMediaItemDTO);
        if (libMediaItemDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new libMediaItem cannot already have an ID")).body(null);
        }
        LibMediaItem libMediaItem = libMediaItemMapper.libMediaItemDTOToLibMediaItem(libMediaItemDTO);
        libMediaItem = libMediaItemRepository.save(libMediaItem);
        LibMediaItemDTO result = libMediaItemMapper.libMediaItemToLibMediaItemDTO(libMediaItem);
        return ResponseEntity.created(new URI("/api/lib-media-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lib-media-items : Updates an existing libMediaItem.
     *
     * @param libMediaItemDTO the libMediaItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated libMediaItemDTO,
     * or with status 400 (Bad Request) if the libMediaItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the libMediaItemDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lib-media-items")
    @Timed
    public ResponseEntity<LibMediaItemDTO> updateLibMediaItem(@Valid @RequestBody LibMediaItemDTO libMediaItemDTO) throws URISyntaxException {
        log.debug("REST request to update LibMediaItem : {}", libMediaItemDTO);
        if (libMediaItemDTO.getId() == null) {
            return createLibMediaItem(libMediaItemDTO);
        }
        LibMediaItem libMediaItem = libMediaItemMapper.libMediaItemDTOToLibMediaItem(libMediaItemDTO);
        libMediaItem = libMediaItemRepository.save(libMediaItem);
        LibMediaItemDTO result = libMediaItemMapper.libMediaItemToLibMediaItemDTO(libMediaItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, libMediaItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lib-media-items : get all the libMediaItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of libMediaItems in body
     */
    @GetMapping("/lib-media-items")
    @Timed
    public List<LibMediaItemDTO> getAllLibMediaItems() {
        log.debug("REST request to get all LibMediaItems");
        List<LibMediaItem> libMediaItems = libMediaItemRepository.findAll();
        return libMediaItemMapper.libMediaItemsToLibMediaItemDTOs(libMediaItems);
    }

    /**
     * GET  /lib-media-items/:id : get the "id" libMediaItem.
     *
     * @param id the id of the libMediaItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the libMediaItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lib-media-items/{id}")
    @Timed
    public ResponseEntity<LibMediaItemDTO> getLibMediaItem(@PathVariable Long id) {
        log.debug("REST request to get LibMediaItem : {}", id);
        LibMediaItem libMediaItem = libMediaItemRepository.findOne(id);
        LibMediaItemDTO libMediaItemDTO = libMediaItemMapper.libMediaItemToLibMediaItemDTO(libMediaItem);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(libMediaItemDTO));
    }

    /**
     * DELETE  /lib-media-items/:id : delete the "id" libMediaItem.
     *
     * @param id the id of the libMediaItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lib-media-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteLibMediaItem(@PathVariable Long id) {
        log.debug("REST request to delete LibMediaItem : {}", id);
        libMediaItemRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
