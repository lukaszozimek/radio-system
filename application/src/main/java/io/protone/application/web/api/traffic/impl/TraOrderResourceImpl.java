package io.protone.application.web.api.traffic.impl;


import io.protone.application.web.api.traffic.TraOrderResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorNetworkService;
import io.protone.traffic.api.dto.TraOrderDTO;
import io.protone.traffic.api.dto.thin.TraOrderThinDTO;
import io.protone.traffic.domain.TraOrder;
import io.protone.traffic.mapper.TraOrderMapper;
import io.protone.traffic.service.TraOrderService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
public class TraOrderResourceImpl implements TraOrderResource {
    private final Logger log = LoggerFactory.getLogger(TraOrderResourceImpl.class);

    @Inject
    private TraOrderService traOrderService;

    @Inject
    private TraOrderMapper traOrderMapper;

    @Inject
    private CorNetworkService corNetworkService;

    @Override
    public ResponseEntity<TraOrderDTO> updateAnOrderUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "traOrderDTO", required = true) @Valid @RequestBody TraOrderDTO traOrderDTO) throws URISyntaxException {
        log.debug("REST request to update TraOrder : {}, for Network: {}", traOrderDTO, networkShortcut);
        if (traOrderDTO.getId() == null) {
            return createAnOrderUsingPOST(networkShortcut, traOrderDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        TraOrder traOrder = traOrderMapper.DTO2DB(traOrderDTO, corNetwork);
        TraOrder entity = traOrderService.saveOrder(traOrder);
        TraOrderDTO response = traOrderMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);

    }

    @Override
    public ResponseEntity<TraOrderDTO> createAnOrderUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "traOrderDTO", required = true) @Valid @RequestBody TraOrderDTO traOrderDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact TraOrder : {}, for Network: {}", traOrderDTO, networkShortcut);
        if (traOrderDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraOrder", "idexists", "A new TraOrder cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        TraOrder traOrder = traOrderMapper.DTO2DB(traOrderDTO, corNetwork);
        TraOrder entity = traOrderService.saveOrder(traOrder);
        TraOrderDTO response = traOrderMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/traffic/order/" + response.getId()))
                .body(response);
    }

    @Override
    public ResponseEntity<List<TraOrderThinDTO>> getAllAnOrdersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraOrder, for Network: {}", networkShortcut);
        Slice<TraOrder> entity = traOrderService.getAllOrders(networkShortcut, pagable);
        List<TraOrderThinDTO> response = traOrderMapper.DBs2ThinDTOs(entity.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entity), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraOrderDTO> getAnOrderUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get TraOrder : {}, for Network: {}", id, networkShortcut);
        TraOrder entity = traOrderService.getOrder(id, networkShortcut);
        TraOrderDTO response = traOrderMapper.DB2DTO(entity);
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
    public ResponseEntity<List<TraOrderThinDTO>> getAllCustomerOrdersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                              @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut,
                                                                              @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraOrder, for TraCustomer: {} and Network: {}", customerShortcut, networkShortcut);
        Slice<TraOrder> entity = traOrderService.getCustomerOrders(customerShortcut, networkShortcut, pagable);
        List<TraOrderThinDTO> response = traOrderMapper.DBs2ThinDTOs(entity.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entity), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraOrderDTO> notifyCustomerAboutUnpaidOrderUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }
}
