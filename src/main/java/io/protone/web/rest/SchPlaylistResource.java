package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.SchPlaylist;

import io.protone.repository.SchPlaylistRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.SchPlaylistDTO;
import io.protone.service.mapper.SchPlaylistMapper;
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
 * REST controller for managing SchPlaylist.
 */
@RestController
@RequestMapping("/api")
public class SchPlaylistResource {

    private final Logger log = LoggerFactory.getLogger(SchPlaylistResource.class);

    private static final String ENTITY_NAME = "schPlaylist";
        
    private final SchPlaylistRepository schPlaylistRepository;

    private final SchPlaylistMapper schPlaylistMapper;

    public SchPlaylistResource(SchPlaylistRepository schPlaylistRepository, SchPlaylistMapper schPlaylistMapper) {
        this.schPlaylistRepository = schPlaylistRepository;
        this.schPlaylistMapper = schPlaylistMapper;
    }

    /**
     * POST  /sch-playlists : Create a new schPlaylist.
     *
     * @param schPlaylistDTO the schPlaylistDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new schPlaylistDTO, or with status 400 (Bad Request) if the schPlaylist has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sch-playlists")
    @Timed
    public ResponseEntity<SchPlaylistDTO> createSchPlaylist(@Valid @RequestBody SchPlaylistDTO schPlaylistDTO) throws URISyntaxException {
        log.debug("REST request to save SchPlaylist : {}", schPlaylistDTO);
        if (schPlaylistDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new schPlaylist cannot already have an ID")).body(null);
        }
        SchPlaylist schPlaylist = schPlaylistMapper.schPlaylistDTOToSchPlaylist(schPlaylistDTO);
        schPlaylist = schPlaylistRepository.save(schPlaylist);
        SchPlaylistDTO result = schPlaylistMapper.schPlaylistToSchPlaylistDTO(schPlaylist);
        return ResponseEntity.created(new URI("/api/sch-playlists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sch-playlists : Updates an existing schPlaylist.
     *
     * @param schPlaylistDTO the schPlaylistDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated schPlaylistDTO,
     * or with status 400 (Bad Request) if the schPlaylistDTO is not valid,
     * or with status 500 (Internal Server Error) if the schPlaylistDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sch-playlists")
    @Timed
    public ResponseEntity<SchPlaylistDTO> updateSchPlaylist(@Valid @RequestBody SchPlaylistDTO schPlaylistDTO) throws URISyntaxException {
        log.debug("REST request to update SchPlaylist : {}", schPlaylistDTO);
        if (schPlaylistDTO.getId() == null) {
            return createSchPlaylist(schPlaylistDTO);
        }
        SchPlaylist schPlaylist = schPlaylistMapper.schPlaylistDTOToSchPlaylist(schPlaylistDTO);
        schPlaylist = schPlaylistRepository.save(schPlaylist);
        SchPlaylistDTO result = schPlaylistMapper.schPlaylistToSchPlaylistDTO(schPlaylist);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, schPlaylistDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sch-playlists : get all the schPlaylists.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of schPlaylists in body
     */
    @GetMapping("/sch-playlists")
    @Timed
    public List<SchPlaylistDTO> getAllSchPlaylists() {
        log.debug("REST request to get all SchPlaylists");
        List<SchPlaylist> schPlaylists = schPlaylistRepository.findAll();
        return schPlaylistMapper.schPlaylistsToSchPlaylistDTOs(schPlaylists);
    }

    /**
     * GET  /sch-playlists/:id : get the "id" schPlaylist.
     *
     * @param id the id of the schPlaylistDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the schPlaylistDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sch-playlists/{id}")
    @Timed
    public ResponseEntity<SchPlaylistDTO> getSchPlaylist(@PathVariable Long id) {
        log.debug("REST request to get SchPlaylist : {}", id);
        SchPlaylist schPlaylist = schPlaylistRepository.findOne(id);
        SchPlaylistDTO schPlaylistDTO = schPlaylistMapper.schPlaylistToSchPlaylistDTO(schPlaylist);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(schPlaylistDTO));
    }

    /**
     * DELETE  /sch-playlists/:id : delete the "id" schPlaylist.
     *
     * @param id the id of the schPlaylistDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sch-playlists/{id}")
    @Timed
    public ResponseEntity<Void> deleteSchPlaylist(@PathVariable Long id) {
        log.debug("REST request to delete SchPlaylist : {}", id);
        schPlaylistRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
