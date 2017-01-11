package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.TRAEmissionOrder;

import io.protone.repository.TRAEmissionOrderRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.TRAEmissionOrderDTO;
import io.protone.service.mapper.TRAEmissionOrderMapper;

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
 * REST controller for managing TRAEmissionOrder.
 */
@RestController
@RequestMapping("/api")
public class TRAEmissionOrderResource {

    private final Logger log = LoggerFactory.getLogger(TRAEmissionOrderResource.class);
        
    @Inject
    private TRAEmissionOrderRepository tRAEmissionOrderRepository;

    @Inject
    private TRAEmissionOrderMapper tRAEmissionOrderMapper;

    /**
     * POST  /t-ra-emission-orders : Create a new tRAEmissionOrder.
     *
     * @param tRAEmissionOrderDTO the tRAEmissionOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tRAEmissionOrderDTO, or with status 400 (Bad Request) if the tRAEmissionOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-ra-emission-orders")
    @Timed
    public ResponseEntity<TRAEmissionOrderDTO> createTRAEmissionOrder(@RequestBody TRAEmissionOrderDTO tRAEmissionOrderDTO) throws URISyntaxException {
        log.debug("REST request to save TRAEmissionOrder : {}", tRAEmissionOrderDTO);
        if (tRAEmissionOrderDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tRAEmissionOrder", "idexists", "A new tRAEmissionOrder cannot already have an ID")).body(null);
        }
        TRAEmissionOrder tRAEmissionOrder = tRAEmissionOrderMapper.tRAEmissionOrderDTOToTRAEmissionOrder(tRAEmissionOrderDTO);
        tRAEmissionOrder = tRAEmissionOrderRepository.save(tRAEmissionOrder);
        TRAEmissionOrderDTO result = tRAEmissionOrderMapper.tRAEmissionOrderToTRAEmissionOrderDTO(tRAEmissionOrder);
        return ResponseEntity.created(new URI("/api/t-ra-emission-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tRAEmissionOrder", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-ra-emission-orders : Updates an existing tRAEmissionOrder.
     *
     * @param tRAEmissionOrderDTO the tRAEmissionOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tRAEmissionOrderDTO,
     * or with status 400 (Bad Request) if the tRAEmissionOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the tRAEmissionOrderDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-ra-emission-orders")
    @Timed
    public ResponseEntity<TRAEmissionOrderDTO> updateTRAEmissionOrder(@RequestBody TRAEmissionOrderDTO tRAEmissionOrderDTO) throws URISyntaxException {
        log.debug("REST request to update TRAEmissionOrder : {}", tRAEmissionOrderDTO);
        if (tRAEmissionOrderDTO.getId() == null) {
            return createTRAEmissionOrder(tRAEmissionOrderDTO);
        }
        TRAEmissionOrder tRAEmissionOrder = tRAEmissionOrderMapper.tRAEmissionOrderDTOToTRAEmissionOrder(tRAEmissionOrderDTO);
        tRAEmissionOrder = tRAEmissionOrderRepository.save(tRAEmissionOrder);
        TRAEmissionOrderDTO result = tRAEmissionOrderMapper.tRAEmissionOrderToTRAEmissionOrderDTO(tRAEmissionOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tRAEmissionOrder", tRAEmissionOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-ra-emission-orders : get all the tRAEmissionOrders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tRAEmissionOrders in body
     */
    @GetMapping("/t-ra-emission-orders")
    @Timed
    public List<TRAEmissionOrderDTO> getAllTRAEmissionOrders() {
        log.debug("REST request to get all TRAEmissionOrders");
        List<TRAEmissionOrder> tRAEmissionOrders = tRAEmissionOrderRepository.findAll();
        return tRAEmissionOrderMapper.tRAEmissionOrdersToTRAEmissionOrderDTOs(tRAEmissionOrders);
    }

    /**
     * GET  /t-ra-emission-orders/:id : get the "id" tRAEmissionOrder.
     *
     * @param id the id of the tRAEmissionOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tRAEmissionOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/t-ra-emission-orders/{id}")
    @Timed
    public ResponseEntity<TRAEmissionOrderDTO> getTRAEmissionOrder(@PathVariable Long id) {
        log.debug("REST request to get TRAEmissionOrder : {}", id);
        TRAEmissionOrder tRAEmissionOrder = tRAEmissionOrderRepository.findOne(id);
        TRAEmissionOrderDTO tRAEmissionOrderDTO = tRAEmissionOrderMapper.tRAEmissionOrderToTRAEmissionOrderDTO(tRAEmissionOrder);
        return Optional.ofNullable(tRAEmissionOrderDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /t-ra-emission-orders/:id : delete the "id" tRAEmissionOrder.
     *
     * @param id the id of the tRAEmissionOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-ra-emission-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteTRAEmissionOrder(@PathVariable Long id) {
        log.debug("REST request to delete TRAEmissionOrder : {}", id);
        tRAEmissionOrderRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tRAEmissionOrder", id.toString())).build();
    }

}
