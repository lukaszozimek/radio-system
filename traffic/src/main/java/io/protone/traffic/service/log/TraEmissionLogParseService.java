package io.protone.traffic.service.log;

import io.protone.traffic.api.dto.TraEmissionLogDescriptorDTO;
import io.protone.traffic.domain.TraLogEmission;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by lukaszozimek on 11/12/2017.
 */
public interface TraEmissionLogParseService {

    List<TraLogEmission> parse(TraEmissionLogDescriptorDTO emissionLogDescriptorDTO, MultipartFile multipartFile) throws IOException;
}
