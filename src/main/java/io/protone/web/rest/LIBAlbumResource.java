package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.LIBAlbum;

import io.protone.repository.LIBAlbumRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.LIBAlbumDTO;
import io.protone.service.mapper.LIBAlbumMapper;

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
 * REST controller for managing LIBAlbum.
 */
@RestController
@RequestMapping("/api")
public class LIBAlbumResource {

    private final Logger log = LoggerFactory.getLogger(LIBAlbumResource.class);
        
    @Inject
    private LIBAlbumRepository lIBAlbumRepository;

    @Inject
    private LIBAlbumMapper lIBAlbumMapper;

    /**
     * POST  /l-ib-albums : Create a new lIBAlbum.
     *
     * @param lIBAlbumDTO the lIBAlbumDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lIBAlbumDTO, or with status 400 (Bad Request) if the lIBAlbum has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/l-ib-albums")
    @Timed
    public ResponseEntity<LIBAlbumDTO> createLIBAlbum(@Valid @RequestBody LIBAlbumDTO lIBAlbumDTO) throws URISyntaxException {
        log.debug("REST request to save LIBAlbum : {}", lIBAlbumDTO);
        if (lIBAlbumDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lIBAlbum", "idexists", "A new lIBAlbum cannot already have an ID")).body(null);
        }
        LIBAlbum lIBAlbum = lIBAlbumMapper.lIBAlbumDTOToLIBAlbum(lIBAlbumDTO);
        lIBAlbum = lIBAlbumRepository.save(lIBAlbum);
        LIBAlbumDTO result = lIBAlbumMapper.lIBAlbumToLIBAlbumDTO(lIBAlbum);
        return ResponseEntity.created(new URI("/api/l-ib-albums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lIBAlbum", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /l-ib-albums : Updates an existing lIBAlbum.
     *
     * @param lIBAlbumDTO the lIBAlbumDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lIBAlbumDTO,
     * or with status 400 (Bad Request) if the lIBAlbumDTO is not valid,
     * or with status 500 (Internal Server Error) if the lIBAlbumDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/l-ib-albums")
    @Timed
    public ResponseEntity<LIBAlbumDTO> updateLIBAlbum(@Valid @RequestBody LIBAlbumDTO lIBAlbumDTO) throws URISyntaxException {
        log.debug("REST request to update LIBAlbum : {}", lIBAlbumDTO);
        if (lIBAlbumDTO.getId() == null) {
            return createLIBAlbum(lIBAlbumDTO);
        }
        LIBAlbum lIBAlbum = lIBAlbumMapper.lIBAlbumDTOToLIBAlbum(lIBAlbumDTO);
        lIBAlbum = lIBAlbumRepository.save(lIBAlbum);
        LIBAlbumDTO result = lIBAlbumMapper.lIBAlbumToLIBAlbumDTO(lIBAlbum);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lIBAlbum", lIBAlbumDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /l-ib-albums : get all the lIBAlbums.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lIBAlbums in body
     */
    @GetMapping("/l-ib-albums")
    @Timed
    public List<LIBAlbumDTO> getAllLIBAlbums() {
        log.debug("REST request to get all LIBAlbums");
        List<LIBAlbum> lIBAlbums = lIBAlbumRepository.findAll();
        return lIBAlbumMapper.lIBAlbumsToLIBAlbumDTOs(lIBAlbums);
    }

    /**
     * GET  /l-ib-albums/:id : get the "id" lIBAlbum.
     *
     * @param id the id of the lIBAlbumDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lIBAlbumDTO, or with status 404 (Not Found)
     */
    @GetMapping("/l-ib-albums/{id}")
    @Timed
    public ResponseEntity<LIBAlbumDTO> getLIBAlbum(@PathVariable Long id) {
        log.debug("REST request to get LIBAlbum : {}", id);
        LIBAlbum lIBAlbum = lIBAlbumRepository.findOne(id);
        LIBAlbumDTO lIBAlbumDTO = lIBAlbumMapper.lIBAlbumToLIBAlbumDTO(lIBAlbum);
        return Optional.ofNullable(lIBAlbumDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /l-ib-albums/:id : delete the "id" lIBAlbum.
     *
     * @param id the id of the lIBAlbumDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/l-ib-albums/{id}")
    @Timed
    public ResponseEntity<Void> deleteLIBAlbum(@PathVariable Long id) {
        log.debug("REST request to delete LIBAlbum : {}", id);
        lIBAlbumRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lIBAlbum", id.toString())).build();
    }

}
