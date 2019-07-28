package io.protone.traffic.service.log.pln;

import com.google.common.collect.Lists;
import io.protone.library.domain.LibMediaItem;
import io.protone.traffic.api.dto.TraEmissionLogDescriptorDTO;
import io.protone.traffic.domain.TraBlock;
import io.protone.traffic.domain.TraLogEmission;
import io.protone.traffic.domain.TraOrder;
import io.protone.traffic.service.log.TraEmissionLogParseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by lukaszozimek on 11/12/2017.
 */
@Service
public class TraPlnLogParserService implements TraEmissionLogParseService {
    private final Logger log = LoggerFactory.getLogger(TraPlnLogParserService.class);

    private static final int PLN_START_EMISSION_POSITION = 87;
    private static final char EMISSION_INDEX_CHAR = '=';
    private static final int DATE_CHAR_NUMBER = 10;
    private static final int BLOCK_CHAR_NUMBER = 11;
    private static final String HOUR_SEPARATOR = "-";

    @Override
    public List<TraLogEmission> parse(TraEmissionLogDescriptorDTO emissionLogDescriptorDTO, MultipartFile multipartFile) throws IOException {
        log.debug("Start Parsing PLN Log {}", multipartFile.getName());
        List<TraLogEmission> traLogEmissionList = Lists.newArrayList();
        InputStreamReader reader;
        reader = new InputStreamReader(new ByteArrayInputStream(multipartFile.getBytes()));
        BufferedReader bufferedReader = new BufferedReader(reader);
        for (int i = 0; i <= PLN_START_EMISSION_POSITION; i++) {
            bufferedReader.readLine();
        }
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            LibMediaItem libMediaItem = new LibMediaItem().idx(emissionLogDescriptorDTO.getLibMediaItemThinDTO().getIdx());
            libMediaItem.setId(emissionLogDescriptorDTO.getLibMediaItemThinDTO().getId());
            TraOrder traOrder = new TraOrder();
            traOrder.setId(emissionLogDescriptorDTO.getOrder().getId());
            traLogEmissionList.add(parselogLine(line).advertiment(libMediaItem).order(traOrder));
        }
        return traLogEmissionList;
    }

    private TraLogEmission parselogLine(String line) {
        String removedPositionIndex = line.substring(line.indexOf(EMISSION_INDEX_CHAR) + 1);
        String date = removedPositionIndex.substring(0, DATE_CHAR_NUMBER);
        String block = removedPositionIndex.substring(10, 11 + BLOCK_CHAR_NUMBER);
        String[] timeRange = block.toString().split(HOUR_SEPARATOR);
        TraBlock traBlock = new TraBlock().startBlock((long) LocalTime.from(DateTimeFormatter.ofPattern("HH:mm").parse(timeRange[0].trim())).toSecondOfDay() * 1000).stopBlock((long) LocalTime.from(DateTimeFormatter.ofPattern("HH:mm").parse(timeRange[1].trim())).toSecondOfDay() * 1000);
        return new TraLogEmission().playlistDate(LocalDate.parse(date)).traBlock(traBlock);
    }
}
