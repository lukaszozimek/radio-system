package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.TRABlockPrice;

import io.protone.repository.TRABlockPriceRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.TRABlockPriceDTO;
import io.protone.service.mapper.TRABlockPriceMapper;

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
 * REST controller for managing TRABlockPrice.
 */
@RestController
@RequestMapping("/api")
public class TRABlockPriceResource {

    private final Logger log = LoggerFactory.getLogger(TRABlockPriceResource.class);
        
    @Inject
    private TRABlockPriceRepository tRABlockPriceRepository;

    @Inject
    private TRABlockPriceMapper tRABlockPriceMapper;

    /**
     * POST  /t-ra-block-prices : Create a new tRABlockPrice.
     *
     * @param tRABlockPriceDTO the tRABlockPriceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tRABlockPriceDTO, or with status 400 (Bad Request) if the tRABlockPrice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-ra-block-prices")
    @Timed
    public ResponseEntity<TRABlockPriceDTO> createTRABlockPrice(@RequestBody TRABlockPriceDTO tRABlockPriceDTO) throws URISyntaxException {
        log.debug("REST request to save TRABlockPrice : {}", tRABlockPriceDTO);
        if (tRABlockPriceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tRABlockPrice", "idexists", "A new tRABlockPrice cannot already have an ID")).body(null);
        }
        TRABlockPrice tRABlockPrice = tRABlockPriceMapper.tRABlockPriceDTOToTRABlockPrice(tRABlockPriceDTO);
        tRABlockPrice = tRABlockPriceRepository.save(tRABlockPrice);
        TRABlockPriceDTO result = tRABlockPriceMapper.tRABlockPriceToTRABlockPriceDTO(tRABlockPrice);
        return ResponseEntity.created(new URI("/api/t-ra-block-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tRABlockPrice", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-ra-block-prices : Updates an existing tRABlockPrice.
     *
     * @param tRABlockPriceDTO the tRABlockPriceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tRABlockPriceDTO,
     * or with status 400 (Bad Request) if the tRABlockPriceDTO is not valid,
     * or with status 500 (Internal Server Error) if the tRABlockPriceDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-ra-block-prices")
    @Timed
    public ResponseEntity<TRABlockPriceDTO> updateTRABlockPrice(@RequestBody TRABlockPriceDTO tRABlockPriceDTO) throws URISyntaxException {
        log.debug("REST request to update TRABlockPrice : {}", tRABlockPriceDTO);
        if (tRABlockPriceDTO.getId() == null) {
            return createTRABlockPrice(tRABlockPriceDTO);
        }
        TRABlockPrice tRABlockPrice = tRABlockPriceMapper.tRABlockPriceDTOToTRABlockPrice(tRABlockPriceDTO);
        tRABlockPrice = tRABlockPriceRepository.save(tRABlockPrice);
        TRABlockPriceDTO result = tRABlockPriceMapper.tRABlockPriceToTRABlockPriceDTO(tRABlockPrice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tRABlockPrice", tRABlockPriceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-ra-block-prices : get all the tRABlockPrices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tRABlockPrices in body
     */
    @GetMapping("/t-ra-block-prices")
    @Timed
    public List<TRABlockPriceDTO> getAllTRABlockPrices() {
        log.debug("REST request to get all TRABlockPrices");
        List<TRABlockPrice> tRABlockPrices = tRABlockPriceRepository.findAll();
        return tRABlockPriceMapper.tRABlockPricesToTRABlockPriceDTOs(tRABlockPrices);
    }

    /**
     * GET  /t-ra-block-prices/:id : get the "id" tRABlockPrice.
     *
     * @param id the id of the tRABlockPriceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tRABlockPriceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/t-ra-block-prices/{id}")
    @Timed
    public ResponseEntity<TRABlockPriceDTO> getTRABlockPrice(@PathVariable Long id) {
        log.debug("REST request to get TRABlockPrice : {}", id);
        TRABlockPrice tRABlockPrice = tRABlockPriceRepository.findOne(id);
        TRABlockPriceDTO tRABlockPriceDTO = tRABlockPriceMapper.tRABlockPriceToTRABlockPriceDTO(tRABlockPrice);
        return Optional.ofNullable(tRABlockPriceDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /t-ra-block-prices/:id : delete the "id" tRABlockPrice.
     *
     * @param id the id of the tRABlockPriceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-ra-block-prices/{id}")
    @Timed
    public ResponseEntity<Void> deleteTRABlockPrice(@PathVariable Long id) {
        log.debug("REST request to delete TRABlockPrice : {}", id);
        tRABlockPriceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tRABlockPrice", id.toString())).build();
    }

}
