package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.domain.TraOrder;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.traffic.TraOrderService;
import io.protone.custom.service.dto.TraOrderPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficOrder;
import io.protone.domain.CorNetwork;
import io.protone.web.rest.mapper.TraOrderMapper;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@RestController
public class ApiNetworkTrafficOrderImpl implements ApiNetworkTrafficOrder {
    private final Logger log = LoggerFactory.getLogger(ApiNetworkTrafficOrderImpl.class);

    @Inject
    private TraOrderService traOrderService;

    @Inject
    private TraOrderMapper traOrderMapper;

    @Inject
    private CorNetworkService corNetworkService;

    @Override
    public ResponseEntity<TraOrderPT> updateAnOrderUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "traOrderPT", required = true) @RequestBody TraOrderPT traOrderPT) throws URISyntaxException {
        log.debug("REST request to update TraOrder : {}, for Network: {}", traOrderPT, networkShortcut);
        if (traOrderPT.getId() == null) {
            return createAnOrderUsingPOST(networkShortcut, traOrderPT);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        TraOrder traOrder = traOrderMapper.DTO2DB(traOrderPT, corNetwork);
        TraOrder entity = traOrderService.saveOrder(traOrder);
        TraOrderPT response = traOrderMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);

    }

    @Override
    public ResponseEntity<TraOrderPT> createAnOrderUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "traOrderPT", required = true) @RequestBody TraOrderPT traOrderPT) throws URISyntaxException {
        log.debug("REST request to save TraOrder : {}, for Network: {}", traOrderPT, networkShortcut);
        if (traOrderPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraOrder", "idexists", "A new TraOrder cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        TraOrder traOrder = traOrderMapper.DTO2DB(traOrderPT, corNetwork);
        TraOrder entity = traOrderService.saveOrder(traOrder);
        TraOrderPT response = traOrderMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/traffic/order/" + response.getId()))
            .body(response);
    }

    @Override
    public ResponseEntity<List<TraOrderPT>> getAllAnOrdersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraOrder, for Network: {}", networkShortcut);
        List<TraOrder> entity = traOrderService.getAllOrders(networkShortcut, pagable);
        List<TraOrderPT> response = traOrderMapper.DBs2DTOs(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraOrderPT> getAnOrderUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get TraOrder : {}, for Network: {}", id, networkShortcut);
        TraOrder entity = traOrderService.getOrder(id, networkShortcut);
        TraOrderPT response = traOrderMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deleteAnOrderUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete TraOrder : {}, for Network: {}", id, networkShortcut);
        traOrderService.deleteOrder(id, networkShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("traOrder", id.toString())).build();
    }

    @Override
    public ResponseEntity<TraOrderPT> notifyCustomerAboutUnpaidOrderUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }
}
