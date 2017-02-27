package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.TraInvoice;

import io.protone.repository.TraInvoiceRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.TraInvoiceDTO;
import io.protone.service.mapper.TraInvoiceMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TraInvoice.
 */
@RestController
@RequestMapping("/api")
public class TraInvoiceResource {

    private final Logger log = LoggerFactory.getLogger(TraInvoiceResource.class);

    private static final String ENTITY_NAME = "traInvoice";

    private final TraInvoiceRepository traInvoiceRepository;

    private final TraInvoiceMapper traInvoiceMapper;

    public TraInvoiceResource(TraInvoiceRepository traInvoiceRepository, TraInvoiceMapper traInvoiceMapper) {
        this.traInvoiceRepository = traInvoiceRepository;
        this.traInvoiceMapper = traInvoiceMapper;
    }

    /**
     * POST  /tra-invoices : Create a new traInvoice.
     *
     * @param traInvoiceDTO the traInvoiceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new traInvoiceDTO, or with status 400 (Bad Request) if the traInvoice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tra-invoices")
    @Timed
    public ResponseEntity<TraInvoiceDTO> createTraInvoice(@RequestBody TraInvoiceDTO traInvoiceDTO) throws URISyntaxException {
        log.debug("REST request to save TraInvoice : {}", traInvoiceDTO);
        if (traInvoiceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new traInvoice cannot already have an ID")).body(null);
        }
        TraInvoice traInvoice = traInvoiceMapper.traInvoiceDTOToTraInvoice(traInvoiceDTO);
        traInvoice = traInvoiceRepository.save(traInvoice);
        TraInvoiceDTO result = traInvoiceMapper.traInvoiceToTraInvoiceDTO(traInvoice);
        return ResponseEntity.created(new URI("/api/tra-invoices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tra-invoices : Updates an existing traInvoice.
     *
     * @param traInvoiceDTO the traInvoiceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated traInvoiceDTO,
     * or with status 400 (Bad Request) if the traInvoiceDTO is not valid,
     * or with status 500 (Internal Server Error) if the traInvoiceDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tra-invoices")
    @Timed
    public ResponseEntity<TraInvoiceDTO> updateTraInvoice(@RequestBody TraInvoiceDTO traInvoiceDTO) throws URISyntaxException {
        log.debug("REST request to update TraInvoice : {}", traInvoiceDTO);
        if (traInvoiceDTO.getId() == null) {
            return createTraInvoice(traInvoiceDTO);
        }
        TraInvoice traInvoice = traInvoiceMapper.traInvoiceDTOToTraInvoice(traInvoiceDTO);
        traInvoice = traInvoiceRepository.save(traInvoice);
        TraInvoiceDTO result = traInvoiceMapper.traInvoiceToTraInvoiceDTO(traInvoice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, traInvoiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tra-invoices : get all the traInvoices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of traInvoices in body
     */
    @GetMapping("/tra-invoices")
    @Timed
    public List<TraInvoiceDTO> getAllTraInvoices() {
        log.debug("REST request to get all TraInvoices");
        List<TraInvoice> traInvoices = traInvoiceRepository.findAll();
        return traInvoiceMapper.traInvoicesToTraInvoiceDTOs(traInvoices);
    }

    /**
     * GET  /tra-invoices/:id : get the "id" traInvoice.
     *
     * @param id the id of the traInvoiceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the traInvoiceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tra-invoices/{id}")
    @Timed
    public ResponseEntity<TraInvoiceDTO> getTraInvoice(@PathVariable Long id) {
        log.debug("REST request to get TraInvoice : {}", id);
        TraInvoice traInvoice = traInvoiceRepository.findOne(id);
        TraInvoiceDTO traInvoiceDTO = traInvoiceMapper.traInvoiceToTraInvoiceDTO(traInvoice);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(traInvoiceDTO));
    }

    /**
     * DELETE  /tra-invoices/:id : delete the "id" traInvoice.
     *
     * @param id the id of the traInvoiceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tra-invoices/{id}")
    @Timed
    public ResponseEntity<Void> deleteTraInvoice(@PathVariable Long id) {
        log.debug("REST request to delete TraInvoice : {}", id);
        traInvoiceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
