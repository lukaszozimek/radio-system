package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.LibItemPT;
import io.protone.custom.service.dto.SchEmissionPT;
import io.protone.domain.SCHEmission;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Service
public class CustomSCHEmissionMapper {

    public SchEmissionPT creteDTOFromEntities(SCHEmission emission) {
        return new SchEmissionPT()
            .blockId(2L)
            .endTime(emission.getEndTime().toString())
            .startTime(emission.getStartTime().toString())
            .finished(emission.isFinished())
            .seq(emission.getSeq())
            .relativeDelay(emission.getRelativeDelay())
            .mediaItemId(new LibItemPT())
            .startType(emission.getStartType())
            .length(emission.getLength())
            .templateId(2L)
            .id(emission.getId());
    }

    public List<SCHEmission> createListEmissionFromListDTO(List<SchEmissionPT> schEmissionPT) {
        List<SCHEmission> listEmission = new ArrayList<>();
        schEmissionPT.stream().forEach(emissionPT -> {
            listEmission.add(createEmissionFromDTO(emissionPT));
        });
        return listEmission;
    }

    public SCHEmission createEmissionFromDTO(SchEmissionPT schEmissionPT) {
        SCHEmission schEmission = new SCHEmission();
        schEmission.setId(schEmissionPT.getId());
        return schEmission
            .finished(schEmissionPT.getFinished())
            .seq(schEmissionPT.getSeq())
            .relativeDelay(schEmissionPT.getRelativeDelay())
            .startType(schEmissionPT.getStartType())
            .length(schEmissionPT.getLength());
    }

    public List<SchEmissionPT> createDTOFromListEntites(Set<SCHEmission> schEmissionLibItemPTMap) {

        return schEmissionLibItemPTMap.stream().map(this::creteDTOFromEntities).collect(Collectors.toList());
    }

}
