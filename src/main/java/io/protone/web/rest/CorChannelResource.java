package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CorChannel;

import io.protone.repository.CorChannelRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CorChannelDTO;
import io.protone.service.mapper.CorChannelMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CorChannel.
 */
@RestController
@RequestMapping("/api")
public class CorChannelResource {

    private final Logger log = LoggerFactory.getLogger(CorChannelResource.class);

    private static final String ENTITY_NAME = "corChannel";

    private final CorChannelRepository corChannelRepository;

    private final CorChannelMapper corChannelMapper;

    public CorChannelResource(CorChannelRepository corChannelRepository, CorChannelMapper corChannelMapper) {
        this.corChannelRepository = corChannelRepository;
        this.corChannelMapper = corChannelMapper;
    }

    /**
     * POST  /cor-channels : Create a new corChannel.
     *
     * @param corChannelDTO the corChannelDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new corChannelDTO, or with status 400 (Bad Request) if the corChannel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cor-channels")
    @Timed
    public ResponseEntity<CorChannelDTO> createCorChannel(@Valid @RequestBody CorChannelDTO corChannelDTO) throws URISyntaxException {
        log.debug("REST request to save CorChannel : {}", corChannelDTO);
        if (corChannelDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new corChannel cannot already have an ID")).body(null);
        }
        CorChannel corChannel = corChannelMapper.corChannelDTOToCorChannel(corChannelDTO);
        corChannel = corChannelRepository.save(corChannel);
        CorChannelDTO result = corChannelMapper.corChannelToCorChannelDTO(corChannel);
        return ResponseEntity.created(new URI("/api/cor-channels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cor-channels : Updates an existing corChannel.
     *
     * @param corChannelDTO the corChannelDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated corChannelDTO,
     * or with status 400 (Bad Request) if the corChannelDTO is not valid,
     * or with status 500 (Internal Server Error) if the corChannelDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cor-channels")
    @Timed
    public ResponseEntity<CorChannelDTO> updateCorChannel(@Valid @RequestBody CorChannelDTO corChannelDTO) throws URISyntaxException {
        log.debug("REST request to update CorChannel : {}", corChannelDTO);
        if (corChannelDTO.getId() == null) {
            return createCorChannel(corChannelDTO);
        }
        CorChannel corChannel = corChannelMapper.corChannelDTOToCorChannel(corChannelDTO);
        corChannel = corChannelRepository.save(corChannel);
        CorChannelDTO result = corChannelMapper.corChannelToCorChannelDTO(corChannel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, corChannelDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cor-channels : get all the corChannels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of corChannels in body
     */
    @GetMapping("/cor-channels")
    @Timed
    public List<CorChannelDTO> getAllCorChannels() {
        log.debug("REST request to get all CorChannels");
        List<CorChannel> corChannels = corChannelRepository.findAll();
        return corChannelMapper.corChannelsToCorChannelDTOs(corChannels);
    }

    /**
     * GET  /cor-channels/:id : get the "id" corChannel.
     *
     * @param id the id of the corChannelDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the corChannelDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cor-channels/{id}")
    @Timed
    public ResponseEntity<CorChannelDTO> getCorChannel(@PathVariable Long id) {
        log.debug("REST request to get CorChannel : {}", id);
        CorChannel corChannel = corChannelRepository.findOne(id);
        CorChannelDTO corChannelDTO = corChannelMapper.corChannelToCorChannelDTO(corChannel);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(corChannelDTO));
    }

    /**
     * DELETE  /cor-channels/:id : delete the "id" corChannel.
     *
     * @param id the id of the corChannelDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cor-channels/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorChannel(@PathVariable Long id) {
        log.debug("REST request to delete CorChannel : {}", id);
        corChannelRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
