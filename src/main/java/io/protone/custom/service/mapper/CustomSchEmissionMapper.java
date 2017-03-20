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
        if (emission == null) {
            return new SchEmissionPT();
        }
        return new SchEmissionPT()
            .id(emission.getId())
            .seq(emission.getSeq())
            .blockId(2L)
            .startTime(emission.getStartTime())
            .endTime(emission.getEndTime())
            .mediaItem(new LibItemPT())
            .length(emission.getLength());
    }

    public List<SchEmission> createListEmissionFromListDTO(List<SchEmissionPT> schEmissionPT) {
        if (schEmissionPT == null) {
            return new ArrayList<>();
        }
        List<SchEmission> listEmission = new ArrayList<>();
        schEmissionPT.stream().forEach(emissionPT -> {
            listEmission.add(createEmissionFromDTO(emissionPT));
        });
        return listEmission;
    }

    public SchEmission createEmissionFromDTO(SchEmissionPT schEmissionPT) {
        if (schEmissionPT == null) {
            return new SchEmission();
        }
        SchEmission schEmission = new SchEmission();
        schEmission.setId(schEmissionPT.getId());
        return schEmission
            .seq(schEmissionPT.getSeq())
            .length(schEmissionPT.getLength());
    }

    public List<SchEmissionPT> createDTOFromListEntites(Set<SchEmission> schEmissionLibItemPTMap) {
        if (schEmissionLibItemPTMap == null) {
            return new ArrayList<>();
        }
        return schEmissionLibItemPTMap.stream().map(this::creteDTOFromEntities).collect(Collectors.toList());
    }

}
