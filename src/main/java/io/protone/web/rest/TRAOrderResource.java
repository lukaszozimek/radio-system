package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.TRAOrder;

import io.protone.repository.TRAOrderRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.TRAOrderDTO;
import io.protone.service.mapper.TRAOrderMapper;

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
 * REST controller for managing TRAOrder.
 */
@RestController
@RequestMapping("/api")
public class TRAOrderResource {

    private final Logger log = LoggerFactory.getLogger(TRAOrderResource.class);
        
    @Inject
    private TRAOrderRepository tRAOrderRepository;

    @Inject
    private TRAOrderMapper tRAOrderMapper;

    /**
     * POST  /t-ra-orders : Create a new tRAOrder.
     *
     * @param tRAOrderDTO the tRAOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tRAOrderDTO, or with status 400 (Bad Request) if the tRAOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-ra-orders")
    @Timed
    public ResponseEntity<TRAOrderDTO> createTRAOrder(@Valid @RequestBody TRAOrderDTO tRAOrderDTO) throws URISyntaxException {
        log.debug("REST request to save TRAOrder : {}", tRAOrderDTO);
        if (tRAOrderDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tRAOrder", "idexists", "A new tRAOrder cannot already have an ID")).body(null);
        }
        TRAOrder tRAOrder = tRAOrderMapper.tRAOrderDTOToTRAOrder(tRAOrderDTO);
        tRAOrder = tRAOrderRepository.save(tRAOrder);
        TRAOrderDTO result = tRAOrderMapper.tRAOrderToTRAOrderDTO(tRAOrder);
        return ResponseEntity.created(new URI("/api/t-ra-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tRAOrder", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-ra-orders : Updates an existing tRAOrder.
     *
     * @param tRAOrderDTO the tRAOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tRAOrderDTO,
     * or with status 400 (Bad Request) if the tRAOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the tRAOrderDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-ra-orders")
    @Timed
    public ResponseEntity<TRAOrderDTO> updateTRAOrder(@Valid @RequestBody TRAOrderDTO tRAOrderDTO) throws URISyntaxException {
        log.debug("REST request to update TRAOrder : {}", tRAOrderDTO);
        if (tRAOrderDTO.getId() == null) {
            return createTRAOrder(tRAOrderDTO);
        }
        TRAOrder tRAOrder = tRAOrderMapper.tRAOrderDTOToTRAOrder(tRAOrderDTO);
        tRAOrder = tRAOrderRepository.save(tRAOrder);
        TRAOrderDTO result = tRAOrderMapper.tRAOrderToTRAOrderDTO(tRAOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tRAOrder", tRAOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-ra-orders : get all the tRAOrders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tRAOrders in body
     */
    @GetMapping("/t-ra-orders")
    @Timed
    public List<TRAOrderDTO> getAllTRAOrders() {
        log.debug("REST request to get all TRAOrders");
        List<TRAOrder> tRAOrders = tRAOrderRepository.findAll();
        return tRAOrderMapper.tRAOrdersToTRAOrderDTOs(tRAOrders);
    }

    /**
     * GET  /t-ra-orders/:id : get the "id" tRAOrder.
     *
     * @param id the id of the tRAOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tRAOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/t-ra-orders/{id}")
    @Timed
    public ResponseEntity<TRAOrderDTO> getTRAOrder(@PathVariable Long id) {
        log.debug("REST request to get TRAOrder : {}", id);
        TRAOrder tRAOrder = tRAOrderRepository.findOne(id);
        TRAOrderDTO tRAOrderDTO = tRAOrderMapper.tRAOrderToTRAOrderDTO(tRAOrder);
        return Optional.ofNullable(tRAOrderDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /t-ra-orders/:id : delete the "id" tRAOrder.
     *
     * @param id the id of the tRAOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-ra-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteTRAOrder(@PathVariable Long id) {
        log.debug("REST request to delete TRAOrder : {}", id);
        tRAOrderRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tRAOrder", id.toString())).build();
    }

}
