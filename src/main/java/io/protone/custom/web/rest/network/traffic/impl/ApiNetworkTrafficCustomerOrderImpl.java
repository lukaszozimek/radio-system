package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.web.rest.dto.traffic.TraOrderDTO;
import io.protone.domain.TraOrder;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.traffic.TraOrderService;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficCustomerOrder;
import io.protone.web.rest.mapper.TraOrderMapper;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@RestController
public class ApiNetworkTrafficCustomerOrderImpl implements ApiNetworkTrafficCustomerOrder {
    private final Logger log = LoggerFactory.getLogger(ApiNetworkTrafficCustomerOrderImpl.class);

    @Inject
    private TraOrderService orderService;

    @Inject
    private CorNetworkService networkService;
    @Inject
    private TraOrderMapper traOrderMapper;

    @Override
    public ResponseEntity<List<TraOrderDTO>> getAllCustomerOrdersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut,
                                                                          @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraOrder, for TraCustomer: {} and Network: {}", customerShortcut, networkShortcut);
        List<TraOrder> entity = orderService.getCustomerOrders(customerShortcut, networkShortcut, pagable);
        List<TraOrderDTO> response = traOrderMapper.DBs2DTOs(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
