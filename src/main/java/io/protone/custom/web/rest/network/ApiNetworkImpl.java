package io.protone.custom.web.rest.network;

import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.dto.CoreNetworkPT;
import io.protone.custom.service.mapper.CustomCorNetworkMapper;
import io.protone.domain.CorNetwork;
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
import java.util.List;
import java.util.Optional;

@RestController
public class ApiNetworkImpl implements ApiNetwork {
    private final Logger log = LoggerFactory.getLogger(ApiNetworkImpl.class);

    @Inject
    CustomCorNetworkMapper customCorNetworkMapper;

    @Inject
    CorNetworkService networkService;

    @Override
    public ResponseEntity<List<CoreNetworkPT>> getAllNetworksUsingGET() {
        log.debug("REST request to get all CorNetworks");
        List<CorNetwork> cORNetworks = networkService.findAllNetworks();
        return ResponseEntity.ok()
            .body(customCorNetworkMapper.cORNetworksToCorNetworkDTOs(cORNetworks));

    }

    @Override
    public ResponseEntity<CoreNetworkPT> createNetworkUsingPOST(@ApiParam(value = "network", required = true) @RequestBody CoreNetworkPT network) {
        log.debug("REST request to save CorNetwork : {}", network);
        if (network.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORNetwork", "idexists", "A new cORNetwork cannot already have an ID")).body(null);
        }
        CorNetwork cORNetwork = customCorNetworkMapper.cORNetworkDTOToCorNetwork(network);
        cORNetwork = networkService.save(cORNetwork);
        CoreNetworkPT result = customCorNetworkMapper.cORNetworkToCorNetworkDTO(cORNetwork);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityCreationAlert("cORNetwork", result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<CoreNetworkPT> updateNetworkUsingPUT(@ApiParam(value = "network", required = true) @RequestBody CoreNetworkPT network) {
        log.debug("REST request to update CorNetwork : {}", network);
        if (network.getId() == null) {
            return createNetworkUsingPOST(network);
        }
        CorNetwork cORNetwork = customCorNetworkMapper.cORNetworkDTOToCorNetwork(network);
        cORNetwork = networkService.save(cORNetwork);
        CoreNetworkPT result = customCorNetworkMapper.cORNetworkToCorNetworkDTO(cORNetwork);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORNetwork", network.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<Void> deleteNetworkUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to delete CorNetwork : {}", networkShortcut);
        networkService.deleteNetwork(networkShortcut);
        return ResponseEntity.ok().build();

    }

    @Override
    public ResponseEntity<CoreNetworkPT> getNetworkUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get CorNetwork : {}", networkShortcut);
        CorNetwork cORNetwork = networkService.findNetwork(networkShortcut);
        CoreNetworkPT cORNetworkDTO = customCorNetworkMapper.cORNetworkToCorNetworkDTO(cORNetwork);
        return Optional.ofNullable(cORNetworkDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
