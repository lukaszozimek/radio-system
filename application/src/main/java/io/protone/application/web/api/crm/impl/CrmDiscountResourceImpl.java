package io.protone.application.web.api.crm.impl;


import io.protone.application.web.api.crm.CrmDiscountResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorChannel;
import io.protone.core.service.CorChannelService;
import io.protone.crm.api.dto.CrmDiscountDTO;
import io.protone.crm.domain.CrmDiscount;
import io.protone.crm.mapper.CrmDiscountMapper;
import io.protone.crm.repostiory.CrmDiscountRepository;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * Created by lukaszozimek on 23/03/2017.
 */

@RestController
public class CrmDiscountResourceImpl implements CrmDiscountResource {

    private final Logger log = LoggerFactory.getLogger(CrmDiscountResourceImpl.class);

    @Autowired
    private CrmDiscountMapper crmDiscountMapper;

    @Autowired
    private CorChannelService corChannelService;

    @Autowired
    private CrmDiscountRepository traDiscountRepository;

    @Override
    public ResponseEntity<List<CrmDiscountDTO>> getAllDiscountUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                       @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                       @ApiParam(value = "pagable", required = true) Pageable pagable) {

        log.debug("REST request to get all TraDiscount");
        Slice<CrmDiscount> traDiscount = traDiscountRepository.findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(organizationShortcut, channelShortcut, pagable);
        List<CrmDiscountDTO> traDiscountPT = crmDiscountMapper.DBs2DTOs(traDiscount.getContent());
        return Optional.ofNullable(traDiscountPT)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(traDiscount),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(traDiscount), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CrmDiscountDTO> getDiscountUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                              @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {

        log.debug("REST request to get TraDiscount : {}", organizationShortcut);
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        CrmDiscount traDiscount = traDiscountRepository.findOneByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(id, organizationShortcut, channelShortcut);
        CrmDiscountDTO traDiscountPt = crmDiscountMapper.DB2DTO(traDiscount);
        return Optional.ofNullable(traDiscountPt)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CrmDiscountDTO> updateDiscountUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                 @ApiParam(value = "discountPT", required = true) @RequestBody CrmDiscountDTO discountPT) throws URISyntaxException {

        log.debug("REST request to update TraDiscount : {}", discountPT);
        if (discountPT.getId() == null) {
            return createDiscountUsingPOST(organizationShortcut, channelShortcut, discountPT);
        }
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        CrmDiscount traDiscount = crmDiscountMapper.DTO2DB(discountPT, corChannel);
        traDiscount = traDiscountRepository.save(traDiscount);
        CrmDiscountDTO result = crmDiscountMapper.DB2DTO(traDiscount);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("TraDiscount", result.getId().toString()))
                .body(result);
    }

    @Override
    public ResponseEntity<CrmDiscountDTO> createDiscountUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                  @ApiParam(value = "discountPT", required = true) @RequestBody CrmDiscountDTO discountPT) throws URISyntaxException {
        log.debug("REST request to saveCorContact TraDiscount : {}", discountPT);
        if (discountPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraDiscount", "idexists", "A new TraDiscount cannot already have an ID")).body(null);
        }
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        CrmDiscount traDiscount = crmDiscountMapper.DTO2DB(discountPT, corChannel);
        traDiscount = traDiscountRepository.save(traDiscount);
        CrmDiscountDTO result = crmDiscountMapper.DB2DTO(traDiscount);
        return ResponseEntity.created(new URI("/api/v1/organization/" + organizationShortcut + "/configuration/traffic/dictionary/discount/" + result.getId()))
                .body(result);
    }

    @Override
    public ResponseEntity<Void> deleteDiscountUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                          @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete TraDiscount : {}", id);
        traDiscountRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("TraDiscount", id.toString())).build();
    }
}
