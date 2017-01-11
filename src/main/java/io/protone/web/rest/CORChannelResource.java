package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CORChannel;

import io.protone.repository.CORChannelRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CORChannelDTO;
import io.protone.service.mapper.CORChannelMapper;

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
 * REST controller for managing CORChannel.
 */
@RestController
@RequestMapping("/api")
public class CORChannelResource {

    private final Logger log = LoggerFactory.getLogger(CORChannelResource.class);
        
    @Inject
    private CORChannelRepository cORChannelRepository;

    @Inject
    private CORChannelMapper cORChannelMapper;

    /**
     * POST  /c-or-channels : Create a new cORChannel.
     *
     * @param cORChannelDTO the cORChannelDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cORChannelDTO, or with status 400 (Bad Request) if the cORChannel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-or-channels")
    @Timed
    public ResponseEntity<CORChannelDTO> createCORChannel(@Valid @RequestBody CORChannelDTO cORChannelDTO) throws URISyntaxException {
        log.debug("REST request to save CORChannel : {}", cORChannelDTO);
        if (cORChannelDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORChannel", "idexists", "A new cORChannel cannot already have an ID")).body(null);
        }
        CORChannel cORChannel = cORChannelMapper.cORChannelDTOToCORChannel(cORChannelDTO);
        cORChannel = cORChannelRepository.save(cORChannel);
        CORChannelDTO result = cORChannelMapper.cORChannelToCORChannelDTO(cORChannel);
        return ResponseEntity.created(new URI("/api/c-or-channels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cORChannel", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-or-channels : Updates an existing cORChannel.
     *
     * @param cORChannelDTO the cORChannelDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cORChannelDTO,
     * or with status 400 (Bad Request) if the cORChannelDTO is not valid,
     * or with status 500 (Internal Server Error) if the cORChannelDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-or-channels")
    @Timed
    public ResponseEntity<CORChannelDTO> updateCORChannel(@Valid @RequestBody CORChannelDTO cORChannelDTO) throws URISyntaxException {
        log.debug("REST request to update CORChannel : {}", cORChannelDTO);
        if (cORChannelDTO.getId() == null) {
            return createCORChannel(cORChannelDTO);
        }
        CORChannel cORChannel = cORChannelMapper.cORChannelDTOToCORChannel(cORChannelDTO);
        cORChannel = cORChannelRepository.save(cORChannel);
        CORChannelDTO result = cORChannelMapper.cORChannelToCORChannelDTO(cORChannel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORChannel", cORChannelDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-or-channels : get all the cORChannels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cORChannels in body
     */
    @GetMapping("/c-or-channels")
    @Timed
    public List<CORChannelDTO> getAllCORChannels() {
        log.debug("REST request to get all CORChannels");
        List<CORChannel> cORChannels = cORChannelRepository.findAll();
        return cORChannelMapper.cORChannelsToCORChannelDTOs(cORChannels);
    }

    /**
     * GET  /c-or-channels/:id : get the "id" cORChannel.
     *
     * @param id the id of the cORChannelDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cORChannelDTO, or with status 404 (Not Found)
     */
    @GetMapping("/c-or-channels/{id}")
    @Timed
    public ResponseEntity<CORChannelDTO> getCORChannel(@PathVariable Long id) {
        log.debug("REST request to get CORChannel : {}", id);
        CORChannel cORChannel = cORChannelRepository.findOne(id);
        CORChannelDTO cORChannelDTO = cORChannelMapper.cORChannelToCORChannelDTO(cORChannel);
        return Optional.ofNullable(cORChannelDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-or-channels/:id : delete the "id" cORChannel.
     *
     * @param id the id of the cORChannelDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-or-channels/{id}")
    @Timed
    public ResponseEntity<Void> deleteCORChannel(@PathVariable Long id) {
        log.debug("REST request to delete CORChannel : {}", id);
        cORChannelRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORChannel", id.toString())).build();
    }

}
