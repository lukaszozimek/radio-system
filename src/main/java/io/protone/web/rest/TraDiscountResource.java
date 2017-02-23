package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.TraDiscount;

import io.protone.repository.TraDiscountRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.TraDiscountDTO;
import io.protone.service.mapper.TraDiscountMapper;
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
 * REST controller for managing TraDiscount.
 */
@RestController
@RequestMapping("/api")
public class TraDiscountResource {

    private final Logger log = LoggerFactory.getLogger(TraDiscountResource.class);

    private static final String ENTITY_NAME = "traDiscount";
        
    private final TraDiscountRepository traDiscountRepository;

    private final TraDiscountMapper traDiscountMapper;

    public TraDiscountResource(TraDiscountRepository traDiscountRepository, TraDiscountMapper traDiscountMapper) {
        this.traDiscountRepository = traDiscountRepository;
        this.traDiscountMapper = traDiscountMapper;
    }

    /**
     * POST  /tra-discounts : Create a new traDiscount.
     *
     * @param traDiscountDTO the traDiscountDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new traDiscountDTO, or with status 400 (Bad Request) if the traDiscount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tra-discounts")
    @Timed
    public ResponseEntity<TraDiscountDTO> createTraDiscount(@RequestBody TraDiscountDTO traDiscountDTO) throws URISyntaxException {
        log.debug("REST request to save TraDiscount : {}", traDiscountDTO);
        if (traDiscountDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new traDiscount cannot already have an ID")).body(null);
        }
        TraDiscount traDiscount = traDiscountMapper.traDiscountDTOToTraDiscount(traDiscountDTO);
        traDiscount = traDiscountRepository.save(traDiscount);
        TraDiscountDTO result = traDiscountMapper.traDiscountToTraDiscountDTO(traDiscount);
        return ResponseEntity.created(new URI("/api/tra-discounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tra-discounts : Updates an existing traDiscount.
     *
     * @param traDiscountDTO the traDiscountDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated traDiscountDTO,
     * or with status 400 (Bad Request) if the traDiscountDTO is not valid,
     * or with status 500 (Internal Server Error) if the traDiscountDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tra-discounts")
    @Timed
    public ResponseEntity<TraDiscountDTO> updateTraDiscount(@RequestBody TraDiscountDTO traDiscountDTO) throws URISyntaxException {
        log.debug("REST request to update TraDiscount : {}", traDiscountDTO);
        if (traDiscountDTO.getId() == null) {
            return createTraDiscount(traDiscountDTO);
        }
        TraDiscount traDiscount = traDiscountMapper.traDiscountDTOToTraDiscount(traDiscountDTO);
        traDiscount = traDiscountRepository.save(traDiscount);
        TraDiscountDTO result = traDiscountMapper.traDiscountToTraDiscountDTO(traDiscount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, traDiscountDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tra-discounts : get all the traDiscounts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of traDiscounts in body
     */
    @GetMapping("/tra-discounts")
    @Timed
    public List<TraDiscountDTO> getAllTraDiscounts() {
        log.debug("REST request to get all TraDiscounts");
        List<TraDiscount> traDiscounts = traDiscountRepository.findAll();
        return traDiscountMapper.traDiscountsToTraDiscountDTOs(traDiscounts);
    }

    /**
     * GET  /tra-discounts/:id : get the "id" traDiscount.
     *
     * @param id the id of the traDiscountDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the traDiscountDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tra-discounts/{id}")
    @Timed
    public ResponseEntity<TraDiscountDTO> getTraDiscount(@PathVariable Long id) {
        log.debug("REST request to get TraDiscount : {}", id);
        TraDiscount traDiscount = traDiscountRepository.findOne(id);
        TraDiscountDTO traDiscountDTO = traDiscountMapper.traDiscountToTraDiscountDTO(traDiscount);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(traDiscountDTO));
    }

    /**
     * DELETE  /tra-discounts/:id : delete the "id" traDiscount.
     *
     * @param id the id of the traDiscountDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tra-discounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteTraDiscount(@PathVariable Long id) {
        log.debug("REST request to delete TraDiscount : {}", id);
        traDiscountRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
