package io.protone.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.protone.domain.CorNotification;

import io.protone.repository.CorNotificationRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.protone.service.dto.CorNotificationDTO;
import io.protone.service.mapper.CorNotificationMapper;
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
 * REST controller for managing CorNotification.
 */
@RestController
@RequestMapping("/api")
public class CorNotificationResource {

    private final Logger log = LoggerFactory.getLogger(CorNotificationResource.class);

    private static final String ENTITY_NAME = "corNotification";

    private final CorNotificationRepository corNotificationRepository;

    private final CorNotificationMapper corNotificationMapper;

    public CorNotificationResource(CorNotificationRepository corNotificationRepository, CorNotificationMapper corNotificationMapper) {
        this.corNotificationRepository = corNotificationRepository;
        this.corNotificationMapper = corNotificationMapper;
    }

    /**
     * POST  /cor-notifications : Create a new corNotification.
     *
     * @param corNotificationDTO the corNotificationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new corNotificationDTO, or with status 400 (Bad Request) if the corNotification has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cor-notifications")
    @Timed
    public ResponseEntity<CorNotificationDTO> createCorNotification(@RequestBody CorNotificationDTO corNotificationDTO) throws URISyntaxException {
        log.debug("REST request to save CorNotification : {}", corNotificationDTO);
        if (corNotificationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new corNotification cannot already have an ID")).body(null);
        }
        CorNotification corNotification = corNotificationMapper.corNotificationDTOToCorNotification(corNotificationDTO);
        corNotification = corNotificationRepository.save(corNotification);
        CorNotificationDTO result = corNotificationMapper.corNotificationToCorNotificationDTO(corNotification);
        return ResponseEntity.created(new URI("/api/cor-notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cor-notifications : Updates an existing corNotification.
     *
     * @param corNotificationDTO the corNotificationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated corNotificationDTO,
     * or with status 400 (Bad Request) if the corNotificationDTO is not valid,
     * or with status 500 (Internal Server Error) if the corNotificationDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cor-notifications")
    @Timed
    public ResponseEntity<CorNotificationDTO> updateCorNotification(@RequestBody CorNotificationDTO corNotificationDTO) throws URISyntaxException {
        log.debug("REST request to update CorNotification : {}", corNotificationDTO);
        if (corNotificationDTO.getId() == null) {
            return createCorNotification(corNotificationDTO);
        }
        CorNotification corNotification = corNotificationMapper.corNotificationDTOToCorNotification(corNotificationDTO);
        corNotification = corNotificationRepository.save(corNotification);
        CorNotificationDTO result = corNotificationMapper.corNotificationToCorNotificationDTO(corNotification);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, corNotificationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cor-notifications : get all the corNotifications.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of corNotifications in body
     */
    @GetMapping("/cor-notifications")
    @Timed
    public List<CorNotificationDTO> getAllCorNotifications() {
        log.debug("REST request to get all CorNotifications");
        List<CorNotification> corNotifications = corNotificationRepository.findAll();
        return corNotificationMapper.corNotificationsToCorNotificationDTOs(corNotifications);
    }

    /**
     * GET  /cor-notifications/:id : get the "id" corNotification.
     *
     * @param id the id of the corNotificationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the corNotificationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cor-notifications/{id}")
    @Timed
    public ResponseEntity<CorNotificationDTO> getCorNotification(@PathVariable Long id) {
        log.debug("REST request to get CorNotification : {}", id);
        CorNotification corNotification = corNotificationRepository.findOne(id);
        CorNotificationDTO corNotificationDTO = corNotificationMapper.corNotificationToCorNotificationDTO(corNotification);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(corNotificationDTO));
    }

    /**
     * DELETE  /cor-notifications/:id : delete the "id" corNotification.
     *
     * @param id the id of the corNotificationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cor-notifications/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorNotification(@PathVariable Long id) {
        log.debug("REST request to delete CorNotification : {}", id);
        corNotificationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
