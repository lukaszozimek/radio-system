package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.LibItemPT;
import io.protone.custom.service.dto.SchEmissionPT;
import io.protone.domain.SchEmission;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Service
public class CustomSchEmissionMapper {

    public SchEmissionPT creteDTOFromEntities(SchEmission emission) {
        return new SchEmissionPT()
            .blockId(2L)
            .endTime(emission.getEndTime().toString())
            .startTime(emission.getStartTime().toString())
            .seq(emission.getSeq())
            .mediaItemId(new LibItemPT())
            .length(emission.getLength())
            .templateId(2L)
            .id(emission.getId());
    }

    public List<SchEmission> createListEmissionFromListDTO(List<SchEmissionPT> schEmissionPT) {
        List<SchEmission> listEmission = new ArrayList<>();
        schEmissionPT.stream().forEach(emissionPT -> {
            listEmission.add(createEmissionFromDTO(emissionPT));
        });
        return listEmission;
    }

    public SchEmission createEmissionFromDTO(SchEmissionPT schEmissionPT) {
        SchEmission schEmission = new SchEmission();
        schEmission.setId(schEmissionPT.getId());
        return schEmission
            .seq(schEmissionPT.getSeq())
            .length(schEmissionPT.getLength());
    }

    public List<SchEmissionPT> createDTOFromListEntites(Set<SchEmission> schEmissionLibItemPTMap) {

        return schEmissionLibItemPTMap.stream().map(this::creteDTOFromEntities).collect(Collectors.toList());
    }

}
