package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.TRADiscount;

import io.protone.repository.TRADiscountRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.TRADiscountDTO;
import io.protone.service.mapper.TRADiscountMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing TRADiscount.
 */
@RestController
@RequestMapping("/api")
public class TRADiscountResource {

    private final Logger log = LoggerFactory.getLogger(TRADiscountResource.class);
        
    @Inject
    private TRADiscountRepository tRADiscountRepository;

    @Inject
    private TRADiscountMapper tRADiscountMapper;

    /**
     * POST  /t-ra-discounts : Create a new tRADiscount.
     *
     * @param tRADiscountDTO the tRADiscountDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tRADiscountDTO, or with status 400 (Bad Request) if the tRADiscount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-ra-discounts")
    @Timed
    public ResponseEntity<TRADiscountDTO> createTRADiscount(@RequestBody TRADiscountDTO tRADiscountDTO) throws URISyntaxException {
        log.debug("REST request to save TRADiscount : {}", tRADiscountDTO);
        if (tRADiscountDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tRADiscount", "idexists", "A new tRADiscount cannot already have an ID")).body(null);
        }
        TRADiscount tRADiscount = tRADiscountMapper.tRADiscountDTOToTRADiscount(tRADiscountDTO);
        tRADiscount = tRADiscountRepository.save(tRADiscount);
        TRADiscountDTO result = tRADiscountMapper.tRADiscountToTRADiscountDTO(tRADiscount);
        return ResponseEntity.created(new URI("/api/t-ra-discounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tRADiscount", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-ra-discounts : Updates an existing tRADiscount.
     *
     * @param tRADiscountDTO the tRADiscountDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tRADiscountDTO,
     * or with status 400 (Bad Request) if the tRADiscountDTO is not valid,
     * or with status 500 (Internal Server Error) if the tRADiscountDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-ra-discounts")
    @Timed
    public ResponseEntity<TRADiscountDTO> updateTRADiscount(@RequestBody TRADiscountDTO tRADiscountDTO) throws URISyntaxException {
        log.debug("REST request to update TRADiscount : {}", tRADiscountDTO);
        if (tRADiscountDTO.getId() == null) {
            return createTRADiscount(tRADiscountDTO);
        }
        TRADiscount tRADiscount = tRADiscountMapper.tRADiscountDTOToTRADiscount(tRADiscountDTO);
        tRADiscount = tRADiscountRepository.save(tRADiscount);
        TRADiscountDTO result = tRADiscountMapper.tRADiscountToTRADiscountDTO(tRADiscount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tRADiscount", tRADiscountDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-ra-discounts : get all the tRADiscounts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tRADiscounts in body
     */
    @GetMapping("/t-ra-discounts")
    @Timed
    public List<TRADiscountDTO> getAllTRADiscounts() {
        log.debug("REST request to get all TRADiscounts");
        List<TRADiscount> tRADiscounts = tRADiscountRepository.findAll();
        return tRADiscountMapper.tRADiscountsToTRADiscountDTOs(tRADiscounts);
    }

    /**
     * GET  /t-ra-discounts/:id : get the "id" tRADiscount.
     *
     * @param id the id of the tRADiscountDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tRADiscountDTO, or with status 404 (Not Found)
     */
    @GetMapping("/t-ra-discounts/{id}")
    @Timed
    public ResponseEntity<TRADiscountDTO> getTRADiscount(@PathVariable Long id) {
        log.debug("REST request to get TRADiscount : {}", id);
        TRADiscount tRADiscount = tRADiscountRepository.findOne(id);
        TRADiscountDTO tRADiscountDTO = tRADiscountMapper.tRADiscountToTRADiscountDTO(tRADiscount);
        return Optional.ofNullable(tRADiscountDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /t-ra-discounts/:id : delete the "id" tRADiscount.
     *
     * @param id the id of the tRADiscountDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-ra-discounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteTRADiscount(@PathVariable Long id) {
        log.debug("REST request to delete TRADiscount : {}", id);
        tRADiscountRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tRADiscount", id.toString())).build();
    }

}
