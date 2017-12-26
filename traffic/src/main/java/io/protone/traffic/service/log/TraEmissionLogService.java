package io.protone.traffic.service.log;

import io.protone.traffic.api.dto.TraEmissionLogDescriptorDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by lukaszozimek on 11/12/2017.
 */
public interface TraEmissionLogService  {

    TraLogDiff transform(TraEmissionLogDescriptorDTO emissionLogDescriptorDTO, MultipartFile multipartFile, String organizationShortcut, String channelShortcut) throws IOException;
}
