package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.TRAPrice;

import io.protone.repository.TRAPriceRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.TRAPriceDTO;
import io.protone.service.mapper.TRAPriceMapper;

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
 * REST controller for managing TRAPrice.
 */
@RestController
@RequestMapping("/api")
public class TRAPriceResource {

    private final Logger log = LoggerFactory.getLogger(TRAPriceResource.class);
        
    @Inject
    private TRAPriceRepository tRAPriceRepository;

    @Inject
    private TRAPriceMapper tRAPriceMapper;

    /**
     * POST  /t-ra-prices : Create a new tRAPrice.
     *
     * @param tRAPriceDTO the tRAPriceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tRAPriceDTO, or with status 400 (Bad Request) if the tRAPrice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-ra-prices")
    @Timed
    public ResponseEntity<TRAPriceDTO> createTRAPrice(@Valid @RequestBody TRAPriceDTO tRAPriceDTO) throws URISyntaxException {
        log.debug("REST request to save TRAPrice : {}", tRAPriceDTO);
        if (tRAPriceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tRAPrice", "idexists", "A new tRAPrice cannot already have an ID")).body(null);
        }
        TRAPrice tRAPrice = tRAPriceMapper.tRAPriceDTOToTRAPrice(tRAPriceDTO);
        tRAPrice = tRAPriceRepository.save(tRAPrice);
        TRAPriceDTO result = tRAPriceMapper.tRAPriceToTRAPriceDTO(tRAPrice);
        return ResponseEntity.created(new URI("/api/t-ra-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tRAPrice", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-ra-prices : Updates an existing tRAPrice.
     *
     * @param tRAPriceDTO the tRAPriceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tRAPriceDTO,
     * or with status 400 (Bad Request) if the tRAPriceDTO is not valid,
     * or with status 500 (Internal Server Error) if the tRAPriceDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-ra-prices")
    @Timed
    public ResponseEntity<TRAPriceDTO> updateTRAPrice(@Valid @RequestBody TRAPriceDTO tRAPriceDTO) throws URISyntaxException {
        log.debug("REST request to update TRAPrice : {}", tRAPriceDTO);
        if (tRAPriceDTO.getId() == null) {
            return createTRAPrice(tRAPriceDTO);
        }
        TRAPrice tRAPrice = tRAPriceMapper.tRAPriceDTOToTRAPrice(tRAPriceDTO);
        tRAPrice = tRAPriceRepository.save(tRAPrice);
        TRAPriceDTO result = tRAPriceMapper.tRAPriceToTRAPriceDTO(tRAPrice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tRAPrice", tRAPriceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-ra-prices : get all the tRAPrices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tRAPrices in body
     */
    @GetMapping("/t-ra-prices")
    @Timed
    public List<TRAPriceDTO> getAllTRAPrices() {
        log.debug("REST request to get all TRAPrices");
        List<TRAPrice> tRAPrices = tRAPriceRepository.findAll();
        return tRAPriceMapper.tRAPricesToTRAPriceDTOs(tRAPrices);
    }

    /**
     * GET  /t-ra-prices/:id : get the "id" tRAPrice.
     *
     * @param id the id of the tRAPriceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tRAPriceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/t-ra-prices/{id}")
    @Timed
    public ResponseEntity<TRAPriceDTO> getTRAPrice(@PathVariable Long id) {
        log.debug("REST request to get TRAPrice : {}", id);
        TRAPrice tRAPrice = tRAPriceRepository.findOne(id);
        TRAPriceDTO tRAPriceDTO = tRAPriceMapper.tRAPriceToTRAPriceDTO(tRAPrice);
        return Optional.ofNullable(tRAPriceDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /t-ra-prices/:id : delete the "id" tRAPrice.
     *
     * @param id the id of the tRAPriceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-ra-prices/{id}")
    @Timed
    public ResponseEntity<Void> deleteTRAPrice(@PathVariable Long id) {
        log.debug("REST request to delete TRAPrice : {}", id);
        tRAPriceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tRAPrice", id.toString())).build();
    }

}
