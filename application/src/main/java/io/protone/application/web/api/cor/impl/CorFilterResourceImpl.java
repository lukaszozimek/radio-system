package io.protone.application.web.api.cor.impl;

import io.protone.application.web.api.cor.CorFilterResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.api.dto.CorFilterDTO;
import io.protone.core.domain.CorFilter;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.enumeration.CorEntityTypeEnum;
import io.protone.core.mapper.CorFilterMapper;
import io.protone.core.service.CorFilterService;
import io.protone.core.service.CorNetworkService;
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
public class CorFilterResourceImpl implements CorFilterResource {

    private final Logger log = LoggerFactory.getLogger(CorFilterResourceImpl.class);

    @Inject
    private CorFilterService corFilterService;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CorFilterMapper corFilterMapper;


    @Override
    public ResponseEntity<List<CorFilterDTO>> getAllFilterForTypeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "type", required = true) @PathVariable("type") CorEntityTypeEnum typeEnum,
                                                                          @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CorFilter");
        Slice<CorFilter> corFilters = corFilterService.findAll(networkShortcut, typeEnum, pagable);
        List<CorFilterDTO> CorFilterDTOS = corFilterMapper.DBs2DTOs(corFilters.getContent());
        return Optional.ofNullable(CorFilterDTOS)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(corFilters),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(PaginationUtil.generateSliceHttpHeaders(corFilters), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CorFilterDTO> getFilterForTypeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "type", required = true) @PathVariable("type") CorEntityTypeEnum typeEnum,
                                                                 @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CorFilter : {}", networkShortcut);

        CorFilter CorFilter = corFilterService.findOne(id, typeEnum, networkShortcut);
        CorFilterDTO CorFilterDTO = corFilterMapper.DB2DTO(CorFilter);
        return Optional.ofNullable(CorFilterDTO)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CorFilterDTO> updateFilterUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                             @ApiParam(value = "corFilterDTO", required = true) @Valid @RequestBody CorFilterDTO corFilterDTO) throws URISyntaxException {
        log.debug("REST request to update CorFilter : {}", corFilterDTO);
        if (corFilterDTO.getId() == null) {
            return createFilterUsingPOST(networkShortcut, corFilterDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CorFilter CorFilter = corFilterMapper.DTO2DB(corFilterDTO, corNetwork);
        CorFilter = corFilterService.save(CorFilter);
        CorFilterDTO result = corFilterMapper.DB2DTO(CorFilter);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("CorFilter", result.getId().toString()))
                .body(result);
    }

    @Override
    public ResponseEntity<CorFilterDTO> createFilterUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "corFilterDTO", required = true)
    @Valid @RequestBody CorFilterDTO corFilterDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact CorFilter : {}", corFilterDTO);
        if (corFilterDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CorFilter", "idexists", "A new CorFilter cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CorFilter corFilter = corFilterMapper.DTO2DB(corFilterDTO, corNetwork);
        corFilter = corFilterService.save(corFilter);
        CorFilterDTO result = corFilterMapper.DB2DTO(corFilter);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/configuration/traffic/dictionary/currency/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("CorFilter", result.getId().toString())).body(result);
    }

    @Override
    public ResponseEntity<Void> deleteFilterUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "type", required = true) @PathVariable("type") CorEntityTypeEnum typeEnum,
                                                        @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CorFilter : {}", id);
        corFilterService.delete(id, networkShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CorTax", id.toString())).build();
    }
}
