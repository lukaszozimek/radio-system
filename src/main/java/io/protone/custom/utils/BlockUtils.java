package io.protone.custom.utils;

import io.protone.custom.service.dto.SchBlockPT;
import io.protone.custom.service.dto.SchEmissionPT;
import io.protone.domain.enumeration.SchBlockTypeEnum;
import io.protone.domain.enumeration.SchStartTypeEnum;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlockUtils {

    public List<SchBlockPT> sampleBand() {

        ZonedDateTime now = ZonedDateTime.now();

        List<SchBlockPT> results = new ArrayList<>();
        SchBlockPT band = new SchBlockPT();
        band
        .addBlock(sampleHour(now, 12,false))
        .startTime(now)
        .startType(SchStartTypeEnum.ST_ABSOLUTE)
        .relativeDelay(0L);
        results.add(band);
        updateSeq(results);
        updateStartTime(results, band.getStartTime());
        return results;
    }

    private void updateStartTime(List<SchBlockPT> results, ZonedDateTime dateTime) {

        ZonedDateTime dt = dateTime;
        for (SchBlockPT block: results) {
            block.scheduledStartTime(dt);
            block.startTime(dt);
            dt = dt.plus(block.getLength(), ChronoField.MILLI_OF_DAY.getBaseUnit());
            block.scheduledEndTime(dt);
            block.endTime(dt);
            for (SchEmissionPT emission: block.getEmissions()) {
                emission.startTime(dt);
                dt = dt.plus(emission.getMediaItem().getLength(), ChronoField.MILLI_OF_DAY.getBaseUnit());
                emission.endTime(dt);
            }
            updateStartTime(block.getBlocks(), dt);
        }
    }

    private void updateSeq(List<SchBlockPT> results) {
        int seq = 0;
        for (SchBlockPT block: results) {
            block.seq(seq++);
            for (SchEmissionPT emission: block.getEmissions())
                emission.seq(seq++);
            updateSeq(block.getBlocks());
        }
    }

    public List<SchBlockPT> sampleDay(ZonedDateTime dateTime) {
        List<SchBlockPT> results = new ArrayList<>();
        for (int h = 0; h < 24; h++)
            results.add(sampleHour(dateTime, h, true));
        SchBlockPT firstBlock = results.iterator().next();
        updateSeq(results);
        updateStartTime(results, firstBlock.getStartTime());
        return results;
    }

    public List<SchBlockPT> sampleTemplate() {
        List<SchBlockPT> results = new ArrayList<>();
        for (int h = 0; h < 24; h++)
            results.add(sampleHour(ZonedDateTime.now(), h, true));
        SchBlockPT firstBlock = results.iterator().next();
        updateSeq(results);
        updateStartTime(results, firstBlock.getStartTime());
        return results;
    }

    public SchBlockPT sampleHour(ZonedDateTime dateTime, int h, boolean full) {

        ZonedDateTime dt = dateTime.withHour(h).withMinute(0).withSecond(0).withNano(0);

        SchBlockPT result = new SchBlockPT()
            .startTime(dt)
            .startType(SchStartTypeEnum.ST_ABSOLUTE)
            .relativeDelay(0L)
            .name("SAMPLE_" + SchBlockTypeEnum.BT_HOUR);
        if (full)
            for (int i = 0; i < 4; i++) {
                result.addBlock(sampleCommercial(3));
                result.addBlock(sampleMusic(4));
            }
        else {
            result.addBlock(sampleCommercial(3));
            result.addBlock(sampleMusic(3));
        }

        return result;
    }

    public SchBlockPT sampleCommercial(int numOfElements) {
        SchBlockPT result = new SchBlockPT()
            .name("SAMPLE_" + SchBlockTypeEnum.BT_COMMERCIAL)
            .startType(SchStartTypeEnum.ST_ABSOLUTE)
            .relativeDelay(0L);
        //intro:
        result.addEmission(sampleEmission(1500L));
        //commercials:
        for (int c = 0; c < numOfElements; c++)
            result.addEmission(sampleEmission(30000L));
        //outro:
        result.addEmission(sampleEmission(1500L));
        return result;
    }

    public SchBlockPT sampleMusic(int numOfElements) {
        SchBlockPT result = new SchBlockPT()
            .name("SAMPLE_" + SchBlockTypeEnum.BT_PROGRAM)
            .startType(SchStartTypeEnum.ST_ABSOLUTE)
            .relativeDelay(0L);
        for (int i = 0; i < numOfElements; i++)
            result.addEmission(sampleEmission(240000L));
        return result;
    }

    public SchEmissionPT sampleEmission(Long length) {
        SchEmissionPT emission = new SchEmissionPT()
            //.mediaItem(new LibMediaItemDTO())
            ;
        return emission;
    }
}
