package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.TRAInvoice;

import io.protone.repository.TRAInvoiceRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.TRAInvoiceDTO;
import io.protone.service.mapper.TRAInvoiceMapper;

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
 * REST controller for managing TRAInvoice.
 */
@RestController
@RequestMapping("/api")
public class TRAInvoiceResource {

    private final Logger log = LoggerFactory.getLogger(TRAInvoiceResource.class);
        
    @Inject
    private TRAInvoiceRepository tRAInvoiceRepository;

    @Inject
    private TRAInvoiceMapper tRAInvoiceMapper;

    /**
     * POST  /t-ra-invoices : Create a new tRAInvoice.
     *
     * @param tRAInvoiceDTO the tRAInvoiceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tRAInvoiceDTO, or with status 400 (Bad Request) if the tRAInvoice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-ra-invoices")
    @Timed
    public ResponseEntity<TRAInvoiceDTO> createTRAInvoice(@RequestBody TRAInvoiceDTO tRAInvoiceDTO) throws URISyntaxException {
        log.debug("REST request to save TRAInvoice : {}", tRAInvoiceDTO);
        if (tRAInvoiceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tRAInvoice", "idexists", "A new tRAInvoice cannot already have an ID")).body(null);
        }
        TRAInvoice tRAInvoice = tRAInvoiceMapper.tRAInvoiceDTOToTRAInvoice(tRAInvoiceDTO);
        tRAInvoice = tRAInvoiceRepository.save(tRAInvoice);
        TRAInvoiceDTO result = tRAInvoiceMapper.tRAInvoiceToTRAInvoiceDTO(tRAInvoice);
        return ResponseEntity.created(new URI("/api/t-ra-invoices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tRAInvoice", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-ra-invoices : Updates an existing tRAInvoice.
     *
     * @param tRAInvoiceDTO the tRAInvoiceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tRAInvoiceDTO,
     * or with status 400 (Bad Request) if the tRAInvoiceDTO is not valid,
     * or with status 500 (Internal Server Error) if the tRAInvoiceDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-ra-invoices")
    @Timed
    public ResponseEntity<TRAInvoiceDTO> updateTRAInvoice(@RequestBody TRAInvoiceDTO tRAInvoiceDTO) throws URISyntaxException {
        log.debug("REST request to update TRAInvoice : {}", tRAInvoiceDTO);
        if (tRAInvoiceDTO.getId() == null) {
            return createTRAInvoice(tRAInvoiceDTO);
        }
        TRAInvoice tRAInvoice = tRAInvoiceMapper.tRAInvoiceDTOToTRAInvoice(tRAInvoiceDTO);
        tRAInvoice = tRAInvoiceRepository.save(tRAInvoice);
        TRAInvoiceDTO result = tRAInvoiceMapper.tRAInvoiceToTRAInvoiceDTO(tRAInvoice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tRAInvoice", tRAInvoiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-ra-invoices : get all the tRAInvoices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tRAInvoices in body
     */
    @GetMapping("/t-ra-invoices")
    @Timed
    public List<TRAInvoiceDTO> getAllTRAInvoices() {
        log.debug("REST request to get all TRAInvoices");
        List<TRAInvoice> tRAInvoices = tRAInvoiceRepository.findAll();
        return tRAInvoiceMapper.tRAInvoicesToTRAInvoiceDTOs(tRAInvoices);
    }

    /**
     * GET  /t-ra-invoices/:id : get the "id" tRAInvoice.
     *
     * @param id the id of the tRAInvoiceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tRAInvoiceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/t-ra-invoices/{id}")
    @Timed
    public ResponseEntity<TRAInvoiceDTO> getTRAInvoice(@PathVariable Long id) {
        log.debug("REST request to get TRAInvoice : {}", id);
        TRAInvoice tRAInvoice = tRAInvoiceRepository.findOne(id);
        TRAInvoiceDTO tRAInvoiceDTO = tRAInvoiceMapper.tRAInvoiceToTRAInvoiceDTO(tRAInvoice);
        return Optional.ofNullable(tRAInvoiceDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /t-ra-invoices/:id : delete the "id" tRAInvoice.
     *
     * @param id the id of the tRAInvoiceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-ra-invoices/{id}")
    @Timed
    public ResponseEntity<Void> deleteTRAInvoice(@PathVariable Long id) {
        log.debug("REST request to delete TRAInvoice : {}", id);
        tRAInvoiceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tRAInvoice", id.toString())).build();
    }

}
