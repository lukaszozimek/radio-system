package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LIBMediaItem;

import io.protone.repository.LIBMediaItemRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.LIBMediaItemDTO;
import io.protone.service.mapper.LIBMediaItemMapper;

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
 * REST controller for managing LIBMediaItem.
 */
@RestController
@RequestMapping("/api")
public class LIBMediaItemResource {

    private final Logger log = LoggerFactory.getLogger(LIBMediaItemResource.class);
        
    @Inject
    private LIBMediaItemRepository lIBMediaItemRepository;

    @Inject
    private LIBMediaItemMapper lIBMediaItemMapper;

    /**
     * POST  /l-ib-media-items : Create a new lIBMediaItem.
     *
     * @param lIBMediaItemDTO the lIBMediaItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lIBMediaItemDTO, or with status 400 (Bad Request) if the lIBMediaItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/l-ib-media-items")
    @Timed
    public ResponseEntity<LIBMediaItemDTO> createLIBMediaItem(@Valid @RequestBody LIBMediaItemDTO lIBMediaItemDTO) throws URISyntaxException {
        log.debug("REST request to save LIBMediaItem : {}", lIBMediaItemDTO);
        if (lIBMediaItemDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lIBMediaItem", "idexists", "A new lIBMediaItem cannot already have an ID")).body(null);
        }
        LIBMediaItem lIBMediaItem = lIBMediaItemMapper.lIBMediaItemDTOToLIBMediaItem(lIBMediaItemDTO);
        lIBMediaItem = lIBMediaItemRepository.save(lIBMediaItem);
        LIBMediaItemDTO result = lIBMediaItemMapper.lIBMediaItemToLIBMediaItemDTO(lIBMediaItem);
        return ResponseEntity.created(new URI("/api/l-ib-media-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lIBMediaItem", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /l-ib-media-items : Updates an existing lIBMediaItem.
     *
     * @param lIBMediaItemDTO the lIBMediaItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lIBMediaItemDTO,
     * or with status 400 (Bad Request) if the lIBMediaItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the lIBMediaItemDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/l-ib-media-items")
    @Timed
    public ResponseEntity<LIBMediaItemDTO> updateLIBMediaItem(@Valid @RequestBody LIBMediaItemDTO lIBMediaItemDTO) throws URISyntaxException {
        log.debug("REST request to update LIBMediaItem : {}", lIBMediaItemDTO);
        if (lIBMediaItemDTO.getId() == null) {
            return createLIBMediaItem(lIBMediaItemDTO);
        }
        LIBMediaItem lIBMediaItem = lIBMediaItemMapper.lIBMediaItemDTOToLIBMediaItem(lIBMediaItemDTO);
        lIBMediaItem = lIBMediaItemRepository.save(lIBMediaItem);
        LIBMediaItemDTO result = lIBMediaItemMapper.lIBMediaItemToLIBMediaItemDTO(lIBMediaItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lIBMediaItem", lIBMediaItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /l-ib-media-items : get all the lIBMediaItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lIBMediaItems in body
     */
    @GetMapping("/l-ib-media-items")
    @Timed
    public List<LIBMediaItemDTO> getAllLIBMediaItems() {
        log.debug("REST request to get all LIBMediaItems");
        List<LIBMediaItem> lIBMediaItems = lIBMediaItemRepository.findAll();
        return lIBMediaItemMapper.lIBMediaItemsToLIBMediaItemDTOs(lIBMediaItems);
    }

    /**
     * GET  /l-ib-media-items/:id : get the "id" lIBMediaItem.
     *
     * @param id the id of the lIBMediaItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lIBMediaItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/l-ib-media-items/{id}")
    @Timed
    public ResponseEntity<LIBMediaItemDTO> getLIBMediaItem(@PathVariable Long id) {
        log.debug("REST request to get LIBMediaItem : {}", id);
        LIBMediaItem lIBMediaItem = lIBMediaItemRepository.findOne(id);
        LIBMediaItemDTO lIBMediaItemDTO = lIBMediaItemMapper.lIBMediaItemToLIBMediaItemDTO(lIBMediaItem);
        return Optional.ofNullable(lIBMediaItemDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /l-ib-media-items/:id : delete the "id" lIBMediaItem.
     *
     * @param id the id of the lIBMediaItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/l-ib-media-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteLIBMediaItem(@PathVariable Long id) {
        log.debug("REST request to delete LIBMediaItem : {}", id);
        lIBMediaItemRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lIBMediaItem", id.toString())).build();
    }

}
