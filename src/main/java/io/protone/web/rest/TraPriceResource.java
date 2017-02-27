package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.TraPrice;

import io.protone.repository.TraPriceRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.TraPriceDTO;
import io.protone.service.mapper.TraPriceMapper;
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
 * REST controller for managing TraPrice.
 */
@RestController
@RequestMapping("/api")
public class TraPriceResource {

    private final Logger log = LoggerFactory.getLogger(TraPriceResource.class);

    private static final String ENTITY_NAME = "traPrice";

    private final TraPriceRepository traPriceRepository;

    private final TraPriceMapper traPriceMapper;

    public TraPriceResource(TraPriceRepository traPriceRepository, TraPriceMapper traPriceMapper) {
        this.traPriceRepository = traPriceRepository;
        this.traPriceMapper = traPriceMapper;
    }

    /**
     * POST  /tra-prices : Create a new traPrice.
     *
     * @param traPriceDTO the traPriceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new traPriceDTO, or with status 400 (Bad Request) if the traPrice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tra-prices")
    @Timed
    public ResponseEntity<TraPriceDTO> createTraPrice(@Valid @RequestBody TraPriceDTO traPriceDTO) throws URISyntaxException {
        log.debug("REST request to save TraPrice : {}", traPriceDTO);
        if (traPriceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new traPrice cannot already have an ID")).body(null);
        }
        TraPrice traPrice = traPriceMapper.traPriceDTOToTraPrice(traPriceDTO);
        traPrice = traPriceRepository.save(traPrice);
        TraPriceDTO result = traPriceMapper.traPriceToTraPriceDTO(traPrice);
        return ResponseEntity.created(new URI("/api/tra-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tra-prices : Updates an existing traPrice.
     *
     * @param traPriceDTO the traPriceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated traPriceDTO,
     * or with status 400 (Bad Request) if the traPriceDTO is not valid,
     * or with status 500 (Internal Server Error) if the traPriceDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tra-prices")
    @Timed
    public ResponseEntity<TraPriceDTO> updateTraPrice(@Valid @RequestBody TraPriceDTO traPriceDTO) throws URISyntaxException {
        log.debug("REST request to update TraPrice : {}", traPriceDTO);
        if (traPriceDTO.getId() == null) {
            return createTraPrice(traPriceDTO);
        }
        TraPrice traPrice = traPriceMapper.traPriceDTOToTraPrice(traPriceDTO);
        traPrice = traPriceRepository.save(traPrice);
        TraPriceDTO result = traPriceMapper.traPriceToTraPriceDTO(traPrice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, traPriceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tra-prices : get all the traPrices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of traPrices in body
     */
    @GetMapping("/tra-prices")
    @Timed
    public List<TraPriceDTO> getAllTraPrices() {
        log.debug("REST request to get all TraPrices");
        List<TraPrice> traPrices = traPriceRepository.findAll();
        return traPriceMapper.traPricesToTraPriceDTOs(traPrices);
    }

    /**
     * GET  /tra-prices/:id : get the "id" traPrice.
     *
     * @param id the id of the traPriceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the traPriceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tra-prices/{id}")
    @Timed
    public ResponseEntity<TraPriceDTO> getTraPrice(@PathVariable Long id) {
        log.debug("REST request to get TraPrice : {}", id);
        TraPrice traPrice = traPriceRepository.findOne(id);
        TraPriceDTO traPriceDTO = traPriceMapper.traPriceToTraPriceDTO(traPrice);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(traPriceDTO));
    }

    /**
     * DELETE  /tra-prices/:id : delete the "id" traPrice.
     *
     * @param id the id of the traPriceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tra-prices/{id}")
    @Timed
    public ResponseEntity<Void> deleteTraPrice(@PathVariable Long id) {
        log.debug("REST request to delete TraPrice : {}", id);
        traPriceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
