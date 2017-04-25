package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.TraOrderService;
import io.protone.custom.service.dto.TraOrderPT;
import io.protone.custom.web.rest.network.configuration.library.impl.ApiConfigurationLibraryMarkerImpl;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficOrder;
import io.protone.domain.CorNetwork;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;


@RestController
public class ApiNetworkTrafficOrderImpl implements ApiNetworkTrafficOrder {
    private final Logger log = LoggerFactory.getLogger(ApiNetworkTrafficOrderImpl.class);

    @Inject
    private TraOrderService traOrderService;

    @Inject
    private CorNetworkService networkService;

    @Override
    public ResponseEntity<TraOrderPT> updateAnOrderUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "traOrderPT", required = true) @RequestBody TraOrderPT traOrderPT) {
        log.debug("REST request to update TraOrder : {}, for Network: {}", traOrderPT, networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        if (traOrderPT.getId() == null) {
            return createAnOrderUsingPOST(networkShortcut, traOrderPT);
        }
        return ResponseEntity.ok().body(traOrderService.saveOrder(traOrderPT, corNetwork));

    }

    @Override
    public ResponseEntity<TraOrderPT> createAnOrderUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "traOrderPT", required = true) @RequestBody TraOrderPT traOrderPT) {
        log.debug("REST request to save TraOrder : {}, for Network: {}", traOrderPT, networkShortcut);
        if (traOrderPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraOrder", "idexists", "A new TraOrder cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(traOrderService.saveOrder(traOrderPT, corNetwork));
    }

    @Override
    public ResponseEntity<List<TraOrderPT>> getAllAnOrdersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraOrder, for Network: {}", networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(traOrderService.getAllOrder(corNetwork));
    }

    @Override
    public ResponseEntity<TraOrderPT> getAnOrderUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get TraOrder : {}, for Network: {}", id, networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(traOrderService.getOrder(id, corNetwork));
    }

    @Override
    public ResponseEntity<Void> deleteAnOrderUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete TraOrder : {}, for Network: {}", id, networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        traOrderService.deleteOrder(id, corNetwork);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<TraOrderPT> notifyCustomerAboutUnpaidOrderUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }
}
