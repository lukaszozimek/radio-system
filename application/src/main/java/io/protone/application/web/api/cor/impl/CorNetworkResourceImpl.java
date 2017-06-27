package io.protone.application.web.api.cor.impl;

import io.protone.domain.CorNetwork;
import io.protone.service.cor.CorNetworkService;
import io.protone.web.api.cor.CorNetworkResource;
import io.protone.web.rest.dto.cor.CorNetworkDTO;
import io.protone.web.rest.mapper.CorNetworkMapper;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@RestController
public class CorNetworkResourceImpl implements CorNetworkResource {
    private final Logger log = LoggerFactory.getLogger(CorNetworkResourceImpl.class);

    @Inject
    private CorNetworkMapper corNetworkMapper;

    @Inject
    private CorNetworkService corNetworkService;

    @Override
    public ResponseEntity<List<CorNetworkDTO>> getAllNetworksUsingGET() {
        log.debug("REST request to get all CorNetworks");
        List<CorNetwork> networks = corNetworkService.findAllNetworks();
        return ResponseEntity.ok()
            .body(corNetworkMapper.DBs2DTOs(networks));

    }

    @Override
    public ResponseEntity<CorNetworkDTO> createNetworkUsingPOST(@ApiParam(value = "network", required = true) @Valid @RequestBody CorNetworkDTO network) throws URISyntaxException {
        log.debug("REST request to saveCorContact CorNetwork : {}", network);
        if (network.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("corNetwork", "idexists", "A new corNetwork cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkMapper.DTO2DB(network);
        corNetwork = corNetworkService.save(corNetwork);
        CorNetworkDTO result = corNetworkMapper.DB2DTO(corNetwork);
        return ResponseEntity.created(new URI("/api/v1/network/" + result.getShortcut()))
            .headers(HeaderUtil.createEntityCreationAlert("corNetwork", result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<CorNetworkDTO> updateNetworkUsingPUT(@ApiParam(value = "network", required = true) @Valid @RequestBody CorNetworkDTO network) throws URISyntaxException {
        log.debug("REST request to update CorNetwork : {}", network);
        if (network.getId() == null) {
            return createNetworkUsingPOST(network);
        }
        CorNetwork corNetwork = corNetworkMapper.DTO2DB(network);
        corNetwork = corNetworkService.save(corNetwork);
        CorNetworkDTO result = corNetworkMapper.DB2DTO(corNetwork);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("corNetwork", network.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<Void> deleteNetworkUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to delete CorNetwork : {}", networkShortcut);
        corNetworkService.deleteNetwork(networkShortcut);
        return ResponseEntity.ok().build();

    }

    @Override
    public ResponseEntity<CorNetworkDTO> getNetworkUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get CorNetwork : {}", networkShortcut);
        CorNetwork cORNetwork = corNetworkService.findNetwork(networkShortcut);
        CorNetworkDTO cORNetworkDTO = corNetworkMapper.DB2DTO(cORNetwork);
        return Optional.ofNullable(cORNetworkDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
