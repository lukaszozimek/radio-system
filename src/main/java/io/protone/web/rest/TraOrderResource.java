package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.TraOrder;

import io.protone.repository.TraOrderRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.TraOrderDTO;
import io.protone.service.mapper.TraOrderMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing TraOrder.
 */
@RestController
@RequestMapping("/api")
public class TraOrderResource {

    private final Logger log = LoggerFactory.getLogger(TraOrderResource.class);

    private static final String ENTITY_NAME = "traOrder";
        
    private final TraOrderRepository traOrderRepository;

    private final TraOrderMapper traOrderMapper;

    public TraOrderResource(TraOrderRepository traOrderRepository, TraOrderMapper traOrderMapper) {
        this.traOrderRepository = traOrderRepository;
        this.traOrderMapper = traOrderMapper;
    }

    /**
     * POST  /tra-orders : Create a new traOrder.
     *
     * @param traOrderDTO the traOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new traOrderDTO, or with status 400 (Bad Request) if the traOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tra-orders")
    @Timed
    public ResponseEntity<TraOrderDTO> createTraOrder(@Valid @RequestBody TraOrderDTO traOrderDTO) throws URISyntaxException {
        log.debug("REST request to save TraOrder : {}", traOrderDTO);
        if (traOrderDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new traOrder cannot already have an ID")).body(null);
        }
        TraOrder traOrder = traOrderMapper.traOrderDTOToTraOrder(traOrderDTO);
        traOrder = traOrderRepository.save(traOrder);
        TraOrderDTO result = traOrderMapper.traOrderToTraOrderDTO(traOrder);
        return ResponseEntity.created(new URI("/api/tra-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tra-orders : Updates an existing traOrder.
     *
     * @param traOrderDTO the traOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated traOrderDTO,
     * or with status 400 (Bad Request) if the traOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the traOrderDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tra-orders")
    @Timed
    public ResponseEntity<TraOrderDTO> updateTraOrder(@Valid @RequestBody TraOrderDTO traOrderDTO) throws URISyntaxException {
        log.debug("REST request to update TraOrder : {}", traOrderDTO);
        if (traOrderDTO.getId() == null) {
            return createTraOrder(traOrderDTO);
        }
        TraOrder traOrder = traOrderMapper.traOrderDTOToTraOrder(traOrderDTO);
        traOrder = traOrderRepository.save(traOrder);
        TraOrderDTO result = traOrderMapper.traOrderToTraOrderDTO(traOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, traOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tra-orders : get all the traOrders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of traOrders in body
     */
    @GetMapping("/tra-orders")
    @Timed
    public List<TraOrderDTO> getAllTraOrders() {
        log.debug("REST request to get all TraOrders");
        List<TraOrder> traOrders = traOrderRepository.findAll();
        return traOrderMapper.traOrdersToTraOrderDTOs(traOrders);
    }

    /**
     * GET  /tra-orders/:id : get the "id" traOrder.
     *
     * @param id the id of the traOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the traOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tra-orders/{id}")
    @Timed
    public ResponseEntity<TraOrderDTO> getTraOrder(@PathVariable Long id) {
        log.debug("REST request to get TraOrder : {}", id);
        TraOrder traOrder = traOrderRepository.findOne(id);
        TraOrderDTO traOrderDTO = traOrderMapper.traOrderToTraOrderDTO(traOrder);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(traOrderDTO));
    }

    /**
     * DELETE  /tra-orders/:id : delete the "id" traOrder.
     *
     * @param id the id of the traOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tra-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteTraOrder(@PathVariable Long id) {
        log.debug("REST request to delete TraOrder : {}", id);
        traOrderRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
