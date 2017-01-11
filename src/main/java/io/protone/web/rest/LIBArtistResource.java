package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LIBArtist;

import io.protone.repository.LIBArtistRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.LIBArtistDTO;
import io.protone.service.mapper.LIBArtistMapper;

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
 * REST controller for managing LIBArtist.
 */
@RestController
@RequestMapping("/api")
public class LIBArtistResource {

    private final Logger log = LoggerFactory.getLogger(LIBArtistResource.class);
        
    @Inject
    private LIBArtistRepository lIBArtistRepository;

    @Inject
    private LIBArtistMapper lIBArtistMapper;

    /**
     * POST  /l-ib-artists : Create a new lIBArtist.
     *
     * @param lIBArtistDTO the lIBArtistDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lIBArtistDTO, or with status 400 (Bad Request) if the lIBArtist has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/l-ib-artists")
    @Timed
    public ResponseEntity<LIBArtistDTO> createLIBArtist(@Valid @RequestBody LIBArtistDTO lIBArtistDTO) throws URISyntaxException {
        log.debug("REST request to save LIBArtist : {}", lIBArtistDTO);
        if (lIBArtistDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lIBArtist", "idexists", "A new lIBArtist cannot already have an ID")).body(null);
        }
        LIBArtist lIBArtist = lIBArtistMapper.lIBArtistDTOToLIBArtist(lIBArtistDTO);
        lIBArtist = lIBArtistRepository.save(lIBArtist);
        LIBArtistDTO result = lIBArtistMapper.lIBArtistToLIBArtistDTO(lIBArtist);
        return ResponseEntity.created(new URI("/api/l-ib-artists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lIBArtist", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /l-ib-artists : Updates an existing lIBArtist.
     *
     * @param lIBArtistDTO the lIBArtistDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lIBArtistDTO,
     * or with status 400 (Bad Request) if the lIBArtistDTO is not valid,
     * or with status 500 (Internal Server Error) if the lIBArtistDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/l-ib-artists")
    @Timed
    public ResponseEntity<LIBArtistDTO> updateLIBArtist(@Valid @RequestBody LIBArtistDTO lIBArtistDTO) throws URISyntaxException {
        log.debug("REST request to update LIBArtist : {}", lIBArtistDTO);
        if (lIBArtistDTO.getId() == null) {
            return createLIBArtist(lIBArtistDTO);
        }
        LIBArtist lIBArtist = lIBArtistMapper.lIBArtistDTOToLIBArtist(lIBArtistDTO);
        lIBArtist = lIBArtistRepository.save(lIBArtist);
        LIBArtistDTO result = lIBArtistMapper.lIBArtistToLIBArtistDTO(lIBArtist);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lIBArtist", lIBArtistDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /l-ib-artists : get all the lIBArtists.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lIBArtists in body
     */
    @GetMapping("/l-ib-artists")
    @Timed
    public List<LIBArtistDTO> getAllLIBArtists() {
        log.debug("REST request to get all LIBArtists");
        List<LIBArtist> lIBArtists = lIBArtistRepository.findAll();
        return lIBArtistMapper.lIBArtistsToLIBArtistDTOs(lIBArtists);
    }

    /**
     * GET  /l-ib-artists/:id : get the "id" lIBArtist.
     *
     * @param id the id of the lIBArtistDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lIBArtistDTO, or with status 404 (Not Found)
     */
    @GetMapping("/l-ib-artists/{id}")
    @Timed
    public ResponseEntity<LIBArtistDTO> getLIBArtist(@PathVariable Long id) {
        log.debug("REST request to get LIBArtist : {}", id);
        LIBArtist lIBArtist = lIBArtistRepository.findOne(id);
        LIBArtistDTO lIBArtistDTO = lIBArtistMapper.lIBArtistToLIBArtistDTO(lIBArtist);
        return Optional.ofNullable(lIBArtistDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /l-ib-artists/:id : delete the "id" lIBArtist.
     *
     * @param id the id of the lIBArtistDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/l-ib-artists/{id}")
    @Timed
    public ResponseEntity<Void> deleteLIBArtist(@PathVariable Long id) {
        log.debug("REST request to delete LIBArtist : {}", id);
        lIBArtistRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lIBArtist", id.toString())).build();
    }

}
