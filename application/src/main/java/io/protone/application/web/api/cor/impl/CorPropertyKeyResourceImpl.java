package io.protone.application.web.api.cor.impl;

import io.protone.application.web.api.cor.CorPropertyKeyResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.api.dto.CorKeyDTO;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorPropertyKey;
import io.protone.core.mapper.CorPropertyKeyMapper;
import io.protone.core.repository.CorPropertyKeyRepository;
import io.protone.core.service.CorChannelService;
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
public class CorPropertyKeyResourceImpl implements CorPropertyKeyResource {

    private final Logger log = LoggerFactory.getLogger(CorPropertyKeyResourceImpl.class);

    @Inject
    private CorPropertyKeyRepository corPropertyKeyRepository;

    @Inject
    private CorChannelService corChannelService;

    @Inject
    private CorPropertyKeyMapper corPropertyKeyMapper;

    @Override
    public ResponseEntity<CorKeyDTO> getPropertyKeyUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                            @ApiParam(value = "keyName", required = true) @PathVariable("id") String id) {
        log.debug("REST request to get CorPropertyKey : {}", id);
        CorPropertyKey cORPropertyKey = corPropertyKeyRepository.findByIdAndChannel_Organization_Shortcut(Long.parseLong(id), organizationShortcut);
        CorKeyDTO cORPropertyKeyDTO = corPropertyKeyMapper.DB2DTO(cORPropertyKey);
        return Optional.ofNullable(cORPropertyKeyDTO)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deletePropertyKeyUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                             @ApiParam(value = "keyName", required = true) @PathVariable("id") String id) {
        log.debug("REST request to delete CorPropertyKey : {}", id);
        corPropertyKeyRepository.deleteByIdAndChannel_Organization_Shortcut(Long.parseLong(id), organizationShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORPropertyKey", id.toString())).build();

    }

    @Override
    public ResponseEntity<List<CorKeyDTO>> getAllPropertyKeysUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                      @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CorPropertyKeys");
        Slice<CorPropertyKey> cORPropertyKeys = corPropertyKeyRepository.findSliceByChannel_Organization_Shortcut(organizationShortcut, pagable);
        List<CorKeyDTO> corKeyDTOList = corPropertyKeyMapper.DBs2DTOs(cORPropertyKeys.getContent());
        return Optional.ofNullable(corKeyDTOList)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(cORPropertyKeys),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(PaginationUtil.generateSliceHttpHeaders(cORPropertyKeys), HttpStatus.NOT_FOUND));
    }


    @Override
    public ResponseEntity<CorKeyDTO> updatePropertyKeyUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                               @ApiParam(value = "propertyKeyDTO", required = true) @Valid @RequestBody CorKeyDTO propertyKeyDTO) throws URISyntaxException {
        log.debug("REST request to update CorPropertyKey : {}", propertyKeyDTO);
        if (propertyKeyDTO.getId() == null) {
            return createPropertyKeyUsingPOST(organizationShortcut, channelShortcut, propertyKeyDTO);
        }
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        CorPropertyKey cORPropertyKey = corPropertyKeyMapper.DTO2DB(propertyKeyDTO, corChannel);
        cORPropertyKey = corPropertyKeyRepository.save(cORPropertyKey);
        CorKeyDTO result = corPropertyKeyMapper.DB2DTO(cORPropertyKey);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("cORPropertyKey", propertyKeyDTO.getId().toString()))
                .body(result);
    }

    @Override
    public ResponseEntity<CorKeyDTO> createPropertyKeyUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                @ApiParam(value = "propertyKeyDTO", required = true) @Valid @RequestBody CorKeyDTO propertyKeyDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact CorPropertyKey : {}", propertyKeyDTO);
        if (propertyKeyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORPropertyKey", "idexists", "A new cORPropertyKey cannot already have an ID")).body(null);
        }
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        CorPropertyKey cORPropertyKey = corPropertyKeyMapper.DTO2DB(propertyKeyDTO, corChannel);
        cORPropertyKey = corPropertyKeyRepository.save(cORPropertyKey);
        CorKeyDTO cORPropertyKeyDTO = corPropertyKeyMapper.DB2DTO(cORPropertyKey);
        return ResponseEntity.created(new URI("/api/v1/organization/" + organizationShortcut + "/configuration/organization/dictionary/property/key" + cORPropertyKeyDTO.getId()))
                .headers(HeaderUtil.createEntityUpdateAlert("cORPropertyKey", cORPropertyKeyDTO.getId().toString()))
                .body(cORPropertyKeyDTO);
    }
}
