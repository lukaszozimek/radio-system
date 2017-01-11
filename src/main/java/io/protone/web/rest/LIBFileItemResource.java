package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LIBFileItem;

import io.protone.repository.LIBFileItemRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.LIBFileItemDTO;
import io.protone.service.mapper.LIBFileItemMapper;

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
 * REST controller for managing LIBFileItem.
 */
@RestController
@RequestMapping("/api")
public class LIBFileItemResource {

    private final Logger log = LoggerFactory.getLogger(LIBFileItemResource.class);
        
    @Inject
    private LIBFileItemRepository lIBFileItemRepository;

    @Inject
    private LIBFileItemMapper lIBFileItemMapper;

    /**
     * POST  /l-ib-file-items : Create a new lIBFileItem.
     *
     * @param lIBFileItemDTO the lIBFileItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lIBFileItemDTO, or with status 400 (Bad Request) if the lIBFileItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/l-ib-file-items")
    @Timed
    public ResponseEntity<LIBFileItemDTO> createLIBFileItem(@Valid @RequestBody LIBFileItemDTO lIBFileItemDTO) throws URISyntaxException {
        log.debug("REST request to save LIBFileItem : {}", lIBFileItemDTO);
        if (lIBFileItemDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lIBFileItem", "idexists", "A new lIBFileItem cannot already have an ID")).body(null);
        }
        LIBFileItem lIBFileItem = lIBFileItemMapper.lIBFileItemDTOToLIBFileItem(lIBFileItemDTO);
        lIBFileItem = lIBFileItemRepository.save(lIBFileItem);
        LIBFileItemDTO result = lIBFileItemMapper.lIBFileItemToLIBFileItemDTO(lIBFileItem);
        return ResponseEntity.created(new URI("/api/l-ib-file-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lIBFileItem", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /l-ib-file-items : Updates an existing lIBFileItem.
     *
     * @param lIBFileItemDTO the lIBFileItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lIBFileItemDTO,
     * or with status 400 (Bad Request) if the lIBFileItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the lIBFileItemDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/l-ib-file-items")
    @Timed
    public ResponseEntity<LIBFileItemDTO> updateLIBFileItem(@Valid @RequestBody LIBFileItemDTO lIBFileItemDTO) throws URISyntaxException {
        log.debug("REST request to update LIBFileItem : {}", lIBFileItemDTO);
        if (lIBFileItemDTO.getId() == null) {
            return createLIBFileItem(lIBFileItemDTO);
        }
        LIBFileItem lIBFileItem = lIBFileItemMapper.lIBFileItemDTOToLIBFileItem(lIBFileItemDTO);
        lIBFileItem = lIBFileItemRepository.save(lIBFileItem);
        LIBFileItemDTO result = lIBFileItemMapper.lIBFileItemToLIBFileItemDTO(lIBFileItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lIBFileItem", lIBFileItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /l-ib-file-items : get all the lIBFileItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lIBFileItems in body
     */
    @GetMapping("/l-ib-file-items")
    @Timed
    public List<LIBFileItemDTO> getAllLIBFileItems() {
        log.debug("REST request to get all LIBFileItems");
        List<LIBFileItem> lIBFileItems = lIBFileItemRepository.findAll();
        return lIBFileItemMapper.lIBFileItemsToLIBFileItemDTOs(lIBFileItems);
    }

    /**
     * GET  /l-ib-file-items/:id : get the "id" lIBFileItem.
     *
     * @param id the id of the lIBFileItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lIBFileItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/l-ib-file-items/{id}")
    @Timed
    public ResponseEntity<LIBFileItemDTO> getLIBFileItem(@PathVariable Long id) {
        log.debug("REST request to get LIBFileItem : {}", id);
        LIBFileItem lIBFileItem = lIBFileItemRepository.findOne(id);
        LIBFileItemDTO lIBFileItemDTO = lIBFileItemMapper.lIBFileItemToLIBFileItemDTO(lIBFileItem);
        return Optional.ofNullable(lIBFileItemDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /l-ib-file-items/:id : delete the "id" lIBFileItem.
     *
     * @param id the id of the lIBFileItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/l-ib-file-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteLIBFileItem(@PathVariable Long id) {
        log.debug("REST request to delete LIBFileItem : {}", id);
        lIBFileItemRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lIBFileItem", id.toString())).build();
    }

}
