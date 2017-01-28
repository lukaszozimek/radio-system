package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.LibItemPT;
import io.protone.custom.service.dto.SchEmissionPT;
import io.protone.domain.SCHEmission;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Service
public class CustomSCHEmissionMapper {

    public SchEmissionPT creteDTOFromEntities(SCHEmission emission, LibItemPT libItemPT) {
        return new SchEmissionPT()
            .blockId(emission.getBlock().getId())
            .endTime(emission.getEndTime().toString())
            .startTime(emission.getStartTime().toString())
            .finished(emission.isFinished())
            .seq(emission.getSeq())
            .relativeDelay(emission.getRelativeDelay())
            .mediaItemId(libItemPT)
            .startType(emission.getStartType())
            .length(emission.getLength())
            .templateId(emission.getTemplate().getId())
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

    public List<SchEmissionPT> createDTOFromListEntites(Map<SCHEmission, LibItemPT> schEmissionLibItemPTMap) {
        List<SchEmissionPT> schEmissionPTList = new ArrayList<>();
        schEmissionLibItemPTMap.keySet().stream().forEach(schEmission -> {
                schEmissionPTList.add(creteDTOFromEntities(schEmission, schEmissionLibItemPTMap.get(schEmission)));
            }
        );
        return schEmissionPTList;
    }

}
