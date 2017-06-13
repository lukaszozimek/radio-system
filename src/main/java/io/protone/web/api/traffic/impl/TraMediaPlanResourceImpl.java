package io.protone.web.api.traffic.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraMediaPlan;
import io.protone.service.cor.CorChannelService;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.traffic.TraMediaPlanService;
import io.protone.service.traffic.mediaplan.descriptor.TraMediaPlanDescriptor;
import io.protone.web.api.traffic.TraMediaPlanResource;
import io.protone.web.rest.dto.traffic.TraMediaPlanDTO;
import io.protone.web.rest.dto.traffic.TraMediaPlanDescriptorDTO;
import io.protone.web.rest.dto.traffic.thin.TraMediaPlanThinDTO;
import io.protone.web.rest.mapper.TraMediaPlanDescriptorMapper;
import io.protone.web.rest.mapper.TraMediaPlanMapper;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.validation.*;
import javax.websocket.server.PathParam;
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
    private TraMediaPlanMapper traMediaPlanMapper;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CorChannelService corChannelService;

    @Inject
    private TraMediaPlanDescriptorMapper traMediaPlanDescriptorMapper;


    @Override
    public ResponseEntity<TraMediaPlanDTO> uploadChannelTrafficMediaPlanUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                  @ApiParam(value = "traMediaPlanDescriptorDTO", required = true) @RequestParam("traMediaPlanDescriptorDTO") String traMediaPlanDescriptorDTO,
                                                                                  @ApiParam(value = "files", required = true) @PathParam("file") MultipartFile file) throws URISyntaxException, TikaException, SAXException, IOException, InvalidFormatException {
        ObjectMapper mapper = new ObjectMapper();
        TraMediaPlanDescriptorDTO traMediaPlanDescriptorDeserialized = mapper.readValue(traMediaPlanDescriptorDTO, new TypeReference<TraMediaPlanDescriptorDTO>() {
        });
        validate(traMediaPlanDescriptorDeserialized);
        TraMediaPlanDescriptor traMediaPlanDescriptor = traMediaPlanDescriptorMapper.DTO2DB(traMediaPlanDescriptorDeserialized);
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
        TraMediaPlanDTO response = traMediaPlanMapper.DB2DTO(entity);
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
        List<TraMediaPlan> entities = traMediaPlanService.getMediaPlans(networkShortcut, channelShortcut, pagable);
        List<TraMediaPlanThinDTO> response = traMediaPlanMapper.DBsThin2DTOsThin(entities);
        return ResponseEntity.ok()
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
