package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CorSubscription;

import io.protone.repository.CorSubscriptionRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CorSubscriptionDTO;
import io.protone.service.mapper.CorSubscriptionMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing CorSubscription.
 */
@RestController
@RequestMapping("/api")
public class CorSubscriptionResource {

    private final Logger log = LoggerFactory.getLogger(CorSubscriptionResource.class);

    private static final String ENTITY_NAME = "corSubscription";
        
    private final CorSubscriptionRepository corSubscriptionRepository;

    private final CorSubscriptionMapper corSubscriptionMapper;

    public CorSubscriptionResource(CorSubscriptionRepository corSubscriptionRepository, CorSubscriptionMapper corSubscriptionMapper) {
        this.corSubscriptionRepository = corSubscriptionRepository;
        this.corSubscriptionMapper = corSubscriptionMapper;
    }

    /**
     * POST  /cor-subscriptions : Create a new corSubscription.
     *
     * @param corSubscriptionDTO the corSubscriptionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new corSubscriptionDTO, or with status 400 (Bad Request) if the corSubscription has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cor-subscriptions")
    @Timed
    public ResponseEntity<CorSubscriptionDTO> createCorSubscription(@RequestBody CorSubscriptionDTO corSubscriptionDTO) throws URISyntaxException {
        log.debug("REST request to save CorSubscription : {}", corSubscriptionDTO);
        if (corSubscriptionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new corSubscription cannot already have an ID")).body(null);
        }
        CorSubscription corSubscription = corSubscriptionMapper.corSubscriptionDTOToCorSubscription(corSubscriptionDTO);
        corSubscription = corSubscriptionRepository.save(corSubscription);
        CorSubscriptionDTO result = corSubscriptionMapper.corSubscriptionToCorSubscriptionDTO(corSubscription);
        return ResponseEntity.created(new URI("/api/cor-subscriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cor-subscriptions : Updates an existing corSubscription.
     *
     * @param corSubscriptionDTO the corSubscriptionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated corSubscriptionDTO,
     * or with status 400 (Bad Request) if the corSubscriptionDTO is not valid,
     * or with status 500 (Internal Server Error) if the corSubscriptionDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cor-subscriptions")
    @Timed
    public ResponseEntity<CorSubscriptionDTO> updateCorSubscription(@RequestBody CorSubscriptionDTO corSubscriptionDTO) throws URISyntaxException {
        log.debug("REST request to update CorSubscription : {}", corSubscriptionDTO);
        if (corSubscriptionDTO.getId() == null) {
            return createCorSubscription(corSubscriptionDTO);
        }
        CorSubscription corSubscription = corSubscriptionMapper.corSubscriptionDTOToCorSubscription(corSubscriptionDTO);
        corSubscription = corSubscriptionRepository.save(corSubscription);
        CorSubscriptionDTO result = corSubscriptionMapper.corSubscriptionToCorSubscriptionDTO(corSubscription);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, corSubscriptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cor-subscriptions : get all the corSubscriptions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of corSubscriptions in body
     */
    @GetMapping("/cor-subscriptions")
    @Timed
    public List<CorSubscriptionDTO> getAllCorSubscriptions() {
        log.debug("REST request to get all CorSubscriptions");
        List<CorSubscription> corSubscriptions = corSubscriptionRepository.findAll();
        return corSubscriptionMapper.corSubscriptionsToCorSubscriptionDTOs(corSubscriptions);
    }

    /**
     * GET  /cor-subscriptions/:id : get the "id" corSubscription.
     *
     * @param id the id of the corSubscriptionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the corSubscriptionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cor-subscriptions/{id}")
    @Timed
    public ResponseEntity<CorSubscriptionDTO> getCorSubscription(@PathVariable Long id) {
        log.debug("REST request to get CorSubscription : {}", id);
        CorSubscription corSubscription = corSubscriptionRepository.findOne(id);
        CorSubscriptionDTO corSubscriptionDTO = corSubscriptionMapper.corSubscriptionToCorSubscriptionDTO(corSubscription);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(corSubscriptionDTO));
    }

    /**
     * DELETE  /cor-subscriptions/:id : delete the "id" corSubscription.
     *
     * @param id the id of the corSubscriptionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cor-subscriptions/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorSubscription(@PathVariable Long id) {
        log.debug("REST request to delete CorSubscription : {}", id);
        corSubscriptionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
