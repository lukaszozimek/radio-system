package io.protone.application.web.api.traffic.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.protone.application.web.api.traffic.TraMediaPlanResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.traffic.api.dto.TraMediaPlanDTO;
import io.protone.traffic.api.dto.TraMediaPlanDescriptorDTO;
import io.protone.traffic.api.dto.thin.TraMediaPlanThinDTO;
import io.protone.traffic.domain.TraMediaPlan;
import io.protone.traffic.domain.TraMediaPlanBlock;
import io.protone.traffic.domain.TraMediaPlanEmission;
import io.protone.traffic.domain.TraMediaPlanPlaylistDate;
import io.protone.traffic.mapper.TraMediaPlanDescriptorMapper;
import io.protone.traffic.mapper.TraMediaPlanMapper;
import io.protone.traffic.service.TraMediaPlanBlockService;
import io.protone.traffic.service.TraMediaPlanEmissionService;
import io.protone.traffic.service.TraMediaPlanPlaylistDateService;
import io.protone.traffic.service.TraMediaPlanService;
import io.protone.traffic.service.mediaplan.descriptor.TraMediaPlanDescriptor;
import io.swagger.annotations.ApiParam;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.validation.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by lukaszozimek on 14.05.2017.
 */


@RestController
public class TraMediaPlanResourceImpl implements TraMediaPlanResource {
    private final Logger log = LoggerFactory.getLogger(TraMediaPlanResourceImpl.class);

    @Inject
    private TraMediaPlanService traMediaPlanService;

    @Inject
    private TraMediaPlanBlockService traMediaPlanBlockService;

    @Inject
    private TraMediaPlanPlaylistDateService traMediaPlanPlaylistDateService;

    @Inject
    private TraMediaPlanEmissionService traMediaPlanEmissionService;

    @Inject
    private TraMediaPlanMapper traMediaPlanMapper;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CorChannelService corChannelService;

    @Inject
    private TraMediaPlanDescriptorMapper traMediaPlanDescriptorMapper;

    @Inject
    private ObjectMapper objectMapper;

    @Override
    public ResponseEntity<TraMediaPlanDTO> uploadChannelTrafficMediaPlanUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                  @ApiParam(value = "traMediaPlanDescriptorDTO", required = true) @RequestPart("traMediaPlanDescriptorDTO") @Valid TraMediaPlanDescriptorDTO traMediaPlanDescriptorDTO,
                                                                                  @ApiParam(value = "file", required = true) @RequestPart("file") MultipartFile file) throws URISyntaxException, TikaException, SAXException, IOException, InvalidFormatException {

        TraMediaPlanDescriptor traMediaPlanDescriptor = traMediaPlanDescriptorMapper.DTO2DB(traMediaPlanDescriptorDTO);
        if (traMediaPlanDescriptor == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraMediaPlanDTO", "wrongSchema", "Can't add Element if TraMediaPlanDescriptorDTO doesn't exist")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CorChannel corChannel = corChannelService.findChannel(networkShortcut, channelShortcut);
        TraMediaPlan entity = traMediaPlanService.saveMediaPlan(file, traMediaPlanDescriptor, corNetwork, corChannel);
        TraMediaPlanDTO response = traMediaPlanMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deleteChannelTrafficMediaPlanUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                         @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete TraMediaPlan : {}", id);
        traMediaPlanService.deleteMediaPlan(id, networkShortcut, channelShortcut);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<TraMediaPlanDTO> getChannelTrafficMediaPlanUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                              @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get TraMediaPlan: {}", id);
        TraMediaPlan entity = traMediaPlanService.getMediaPlan(id, networkShortcut, channelShortcut);
        TraMediaPlanDTO response = null;

        if (entity != null) {
            List<TraMediaPlanBlock> blockList = traMediaPlanBlockService.findBlockByNetworkShortcutAndChannelShortcutAndMediaplanId(networkShortcut, channelShortcut, id);
            List<TraMediaPlanPlaylistDate> dateList = traMediaPlanPlaylistDateService.findMediaPlanDatesByNetworkShortcutAndChannelShortcutAndMediaplanId(networkShortcut, channelShortcut, id);
            List<TraMediaPlanEmission> emissionList = traMediaPlanEmissionService.findEmissionsByNetworkShortcutAndChannelShortcutAndMediaplanId(networkShortcut, channelShortcut, id);
            response = traMediaPlanMapper.DB2DTO(entity, blockList, dateList, emissionList);

        }
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<TraMediaPlanThinDTO>> getAllChannelTrafficMediaPlanUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                           @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraMediaPlan");
        Slice<TraMediaPlan> entities = traMediaPlanService.getMediaPlans(networkShortcut, channelShortcut, pagable);
        List<TraMediaPlanThinDTO> response = traMediaPlanMapper.DBsThin2DTOsThin(entities.getContent());
        return ResponseEntity.ok().headers(PaginationUtil.generateSliceHttpHeaders(entities))
                .body(response);
    }

    @Override
    public ResponseEntity<TraMediaPlanDTO> updateChannelTrafficMediaPlanUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                 @ApiParam(value = "traMediaPlanDTO", required = true) @Valid @RequestBody TraMediaPlanDTO traMediaPlanDTO) throws URISyntaxException {
        if (traMediaPlanDTO.getId() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraMediaPlan", "missingID", "Can't edit Element if File doesn't exist")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CorChannel corChannel = corChannelService.findChannel(networkShortcut, channelShortcut);
        TraMediaPlan requestEntity = traMediaPlanMapper.DTO2DB(traMediaPlanDTO, corNetwork, corChannel);
        TraMediaPlan entity = traMediaPlanService.updateMediaPlan(requestEntity);
        TraMediaPlanDTO response = traMediaPlanMapper.DB2DTO(entity);
        return ResponseEntity.ok()
                .body(response);
    }

    private TraMediaPlanDescriptorDTO validate(TraMediaPlanDescriptorDTO traMediaPlanDescriptorDTO) throws IOException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<TraMediaPlanDescriptorDTO>> constraintViolations = validator.validate(traMediaPlanDescriptorDTO);
        if (constraintViolations.isEmpty()) {
            return traMediaPlanDescriptorDTO;
        } else {
            throw new ValidationException();
        }

    }
}
