package io.protone.custom.web.rest.network;

import io.protone.custom.repository.CustomCORNetworkRepository;
import io.protone.custom.service.dto.CoreNetworkPT;
import io.protone.custom.service.mapper.CustomCORNetworkMapper;
import io.protone.domain.CORNetwork;
import io.protone.service.dto.CORNetworkDTO;
import io.protone.web.rest.CORNetworkResource;
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
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class ApiNetworkImpl implements ApiNetwork {
    private final Logger log = LoggerFactory.getLogger(CORNetworkResource.class);

    @Inject
    CustomCORNetworkMapper customCORNetworkMapper;

    @Inject
    CustomCORNetworkRepository customCORNetworkRepository;

    @Override
    public ResponseEntity<List<CoreNetworkPT>> getAllNetworksUsingGET() {
        log.debug("REST request to get all CORNetworks");
        List<CORNetwork> cORNetworks = customCORNetworkRepository.findAll();
        return ResponseEntity.ok()
            .body(customCORNetworkMapper.cORNetworksToCORNetworkDTOs(cORNetworks));

    }

    @Override
    public ResponseEntity<CoreNetworkPT> createNetworkUsingPOST(@ApiParam(value = "network", required = true) @RequestBody CoreNetworkPT network) {
        log.debug("REST request to save CORNetwork : {}", network);
        if (network.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORNetwork", "idexists", "A new cORNetwork cannot already have an ID")).body(null);
        }
        CORNetwork cORNetwork = customCORNetworkMapper.cORNetworkDTOToCORNetwork(network);
        cORNetwork = customCORNetworkRepository.save(cORNetwork);
        CoreNetworkPT result = customCORNetworkMapper.cORNetworkToCORNetworkDTO(cORNetwork);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityCreationAlert("cORNetwork", result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<CoreNetworkPT> updateNetworkUsingPUT(@ApiParam(value = "network", required = true) @RequestBody CoreNetworkPT network) {
        log.debug("REST request to update CORNetwork : {}", network);
        if (network.getId() == null) {
            return createNetworkUsingPOST(network);
        }
        CORNetwork cORNetwork = customCORNetworkMapper.cORNetworkDTOToCORNetwork(network);
        cORNetwork = customCORNetworkRepository.save(cORNetwork);
        CoreNetworkPT result = customCORNetworkMapper.cORNetworkToCORNetworkDTO(cORNetwork);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORNetwork", network.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<Void> deleteNetworkUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to delete CORNetwork : {}", networkShortcut);
        customCORNetworkRepository.deleteByShortcut(networkShortcut);
        return ResponseEntity.ok().build();

    }

    @Override
    public ResponseEntity<CoreNetworkPT> getNetworkUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get CORNetwork : {}", networkShortcut);
        CORNetwork cORNetwork = customCORNetworkRepository.findByShortcut(networkShortcut);
        CoreNetworkPT cORNetworkDTO = customCORNetworkMapper.cORNetworkToCORNetworkDTO(cORNetwork);
        return Optional.ofNullable(cORNetworkDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
