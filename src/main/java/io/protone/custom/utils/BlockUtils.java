package io.protone.custom.utils;

import io.protone.custom.service.dto.SchBlockPT;
import io.protone.custom.service.dto.SchEmissionPT;
import io.protone.domain.LibLibrary;
import io.protone.domain.enumeration.SchBlockTypeEnum;
import io.protone.repository.LibLibraryRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlockUtils {

    public List<SchBlockPT> sampleDay() {
        List<SchBlockPT> results = new ArrayList<>();
        for (int h = 0; h < 24; h++)
            results.add(sampleHour());
        return results;
    }

    public SchBlockPT sampleHour() {

        SchBlockPT result = new SchBlockPT();
        result.setType(SchBlockTypeEnum.BT_HOUR);

        for (int i = 0; i < 4; i++) {
            result.addBlock(sampleCommercial(5));
            result.addBlock(sampleMusic(4));
        }

        return result;
    }

    public SchBlockPT sampleCommercial(int numOfElements) {
        SchBlockPT result = new SchBlockPT();
        result.setType(SchBlockTypeEnum.BT_COMMERCIAL);
        for (int i = 0; i < numOfElements; i++)
            result.addEmission(new SchEmissionPT());
        return result;
    }

    public SchBlockPT sampleMusic(int numOfElements) {
        SchBlockPT result = new SchBlockPT();
        result.setType(SchBlockTypeEnum.BT_PROGRAM);
        for (int i = 0; i < numOfElements; i++)
            result.addEmission(new SchEmissionPT());
        return result;
    }
}
