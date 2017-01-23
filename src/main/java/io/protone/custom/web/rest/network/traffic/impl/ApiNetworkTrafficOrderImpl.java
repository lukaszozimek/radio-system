package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.custom.service.TRAOrderService;
import io.protone.custom.service.dto.TraOrderPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficOrder;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;


@RestController
public class ApiNetworkTrafficOrderImpl implements ApiNetworkTrafficOrder {
    @Inject
    private TRAOrderService traOrderService;

    @Override
    public ResponseEntity<TraOrderPT> updateAnOrderUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "anOrderDTO", required = true) @RequestBody TraOrderPT anOrderDTO) {
        if (anOrderDTO.getId() == null) {
            return createAnOrderUsingPOST(networkShortcut, anOrderDTO);
        }
        return ResponseEntity.ok().body(traOrderService.update(anOrderDTO));

    }

    @Override
    public ResponseEntity<TraOrderPT> createAnOrderUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "anOrderDTO", required = true) @RequestBody TraOrderPT anOrderDTO) {
        return ResponseEntity.ok().body(traOrderService.saveOrder(anOrderDTO));
    }

    @Override
    public ResponseEntity<List<TraOrderPT>> getAllAnOrdersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return ResponseEntity.ok().body(traOrderService.getAllOrder());
    }

    @Override
    public ResponseEntity<TraOrderPT> getAnOrderUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return ResponseEntity.ok().body(traOrderService.getOrder(id));
    }

    @Override
    public ResponseEntity<Void> deleteAnOrderUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        traOrderService.getOrder(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<TraOrderPT> notifyCustomerAboutUnpaidOrderUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }
}
