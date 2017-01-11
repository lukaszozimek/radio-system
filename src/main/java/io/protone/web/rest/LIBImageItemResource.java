package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LIBImageItem;

import io.protone.repository.LIBImageItemRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.LIBImageItemDTO;
import io.protone.service.mapper.LIBImageItemMapper;

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
 * REST controller for managing LIBImageItem.
 */
@RestController
@RequestMapping("/api")
public class LIBImageItemResource {

    private final Logger log = LoggerFactory.getLogger(LIBImageItemResource.class);
        
    @Inject
    private LIBImageItemRepository lIBImageItemRepository;

    @Inject
    private LIBImageItemMapper lIBImageItemMapper;

    /**
     * POST  /l-ib-image-items : Create a new lIBImageItem.
     *
     * @param lIBImageItemDTO the lIBImageItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lIBImageItemDTO, or with status 400 (Bad Request) if the lIBImageItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/l-ib-image-items")
    @Timed
    public ResponseEntity<LIBImageItemDTO> createLIBImageItem(@Valid @RequestBody LIBImageItemDTO lIBImageItemDTO) throws URISyntaxException {
        log.debug("REST request to save LIBImageItem : {}", lIBImageItemDTO);
        if (lIBImageItemDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lIBImageItem", "idexists", "A new lIBImageItem cannot already have an ID")).body(null);
        }
        LIBImageItem lIBImageItem = lIBImageItemMapper.lIBImageItemDTOToLIBImageItem(lIBImageItemDTO);
        lIBImageItem = lIBImageItemRepository.save(lIBImageItem);
        LIBImageItemDTO result = lIBImageItemMapper.lIBImageItemToLIBImageItemDTO(lIBImageItem);
        return ResponseEntity.created(new URI("/api/l-ib-image-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lIBImageItem", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /l-ib-image-items : Updates an existing lIBImageItem.
     *
     * @param lIBImageItemDTO the lIBImageItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lIBImageItemDTO,
     * or with status 400 (Bad Request) if the lIBImageItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the lIBImageItemDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/l-ib-image-items")
    @Timed
    public ResponseEntity<LIBImageItemDTO> updateLIBImageItem(@Valid @RequestBody LIBImageItemDTO lIBImageItemDTO) throws URISyntaxException {
        log.debug("REST request to update LIBImageItem : {}", lIBImageItemDTO);
        if (lIBImageItemDTO.getId() == null) {
            return createLIBImageItem(lIBImageItemDTO);
        }
        LIBImageItem lIBImageItem = lIBImageItemMapper.lIBImageItemDTOToLIBImageItem(lIBImageItemDTO);
        lIBImageItem = lIBImageItemRepository.save(lIBImageItem);
        LIBImageItemDTO result = lIBImageItemMapper.lIBImageItemToLIBImageItemDTO(lIBImageItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lIBImageItem", lIBImageItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /l-ib-image-items : get all the lIBImageItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lIBImageItems in body
     */
    @GetMapping("/l-ib-image-items")
    @Timed
    public List<LIBImageItemDTO> getAllLIBImageItems() {
        log.debug("REST request to get all LIBImageItems");
        List<LIBImageItem> lIBImageItems = lIBImageItemRepository.findAll();
        return lIBImageItemMapper.lIBImageItemsToLIBImageItemDTOs(lIBImageItems);
    }

    /**
     * GET  /l-ib-image-items/:id : get the "id" lIBImageItem.
     *
     * @param id the id of the lIBImageItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lIBImageItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/l-ib-image-items/{id}")
    @Timed
    public ResponseEntity<LIBImageItemDTO> getLIBImageItem(@PathVariable Long id) {
        log.debug("REST request to get LIBImageItem : {}", id);
        LIBImageItem lIBImageItem = lIBImageItemRepository.findOne(id);
        LIBImageItemDTO lIBImageItemDTO = lIBImageItemMapper.lIBImageItemToLIBImageItemDTO(lIBImageItem);
        return Optional.ofNullable(lIBImageItemDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /l-ib-image-items/:id : delete the "id" lIBImageItem.
     *
     * @param id the id of the lIBImageItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/l-ib-image-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteLIBImageItem(@PathVariable Long id) {
        log.debug("REST request to delete LIBImageItem : {}", id);
        lIBImageItemRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lIBImageItem", id.toString())).build();
    }

}
