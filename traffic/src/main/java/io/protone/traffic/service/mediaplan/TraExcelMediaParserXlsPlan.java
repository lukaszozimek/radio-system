package io.protone.traffic.service.mediaplan;

import com.google.common.collect.Sets;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.traffic.domain.TraMediaPlan;
import io.protone.traffic.domain.TraMediaPlanBlock;
import io.protone.traffic.domain.TraMediaPlanEmission;
import io.protone.traffic.domain.TraMediaPlanPlaylistDate;
import io.protone.traffic.service.TraMediaPlanBlockService;
import io.protone.traffic.service.TraMediaPlanEmissionService;
import io.protone.traffic.service.TraMediaPlanPlaylistDateService;
import io.protone.traffic.service.mediaplan.descriptor.TraMediaPlanDescriptor;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.apache.poi.ss.usermodel.WorkbookFactory.create;

/**
 * Created by lukaszozimek on 04/06/2017.
 */
@Service
@Transactional
public class TraExcelMediaParserXlsPlan {
    private final Logger log = LoggerFactory.getLogger(TraExcelMediaParserXlsPlan.class);
    private final Long DEFAULT_START_STOP = 0L;

    @Inject
    private TraMediaPlanPlaylistDateService traMediaPlanPlaylistDateService;

    @Inject
    private TraMediaPlanBlockService traMediaPlanBlockService;

    @Inject
    private TraMediaPlanEmissionService traMediaPlanEmissionService;


    public void parseMediaPlan(ByteArrayInputStream byteArrayInputStream, TraMediaPlan mediaPlan, TraMediaPlanDescriptor traMediaPlanDescriptor, CorChannel corChannel) throws IOException, SAXException, InvalidFormatException {
        Workbook workbook = create(byteArrayInputStream);
        Sheet sheet = workbook.getSheetAt(traMediaPlanDescriptor.getTraMediaPlanTemplate().getSheetIndexOfMediaPlan());
        Map<Integer, LocalDate> dateHashMap = findPlaylistInExcel(sheet, traMediaPlanDescriptor);
        Set<TraMediaPlanPlaylistDate> playlistDates = this.saveParsedPlaylistDates(dateHashMap, mediaPlan, corChannel);
        buildBlocks(sheet, dateHashMap, mediaPlan, playlistDates, traMediaPlanDescriptor, corChannel);
    }

    private Set<TraMediaPlanPlaylistDate> saveParsedPlaylistDates(Map<Integer, LocalDate> dateHashMap, TraMediaPlan mediaPlan, CorChannel corChannel) {
        Set<TraMediaPlanPlaylistDate> traMediaPlanPlaylistDate = Sets.newHashSet();
        dateHashMap.keySet().stream().forEach(columnIndex -> {
            TraMediaPlanPlaylistDate traMediaPlanPlaylistDate1 = new TraMediaPlanPlaylistDate().playlistDate(dateHashMap.get(columnIndex)).channel(corChannel).mediaPlan(mediaPlan);
            traMediaPlanPlaylistDate.add(traMediaPlanPlaylistDate1);
        });
        return traMediaPlanPlaylistDateService.savePlaylist(traMediaPlanPlaylistDate);
    }

    private Map<Integer, LocalDate> findPlaylistInExcel(Sheet sheet, TraMediaPlanDescriptor traMediaPlanDescriptor) {
        Map<Integer, LocalDate> dateHashMap = new HashMap<>();
        int startIndex = CellReference.convertColStringToIndex(traMediaPlanDescriptor.getTraMediaPlanTemplate().getPlaylistDateStartColumn()); //Kolumna poczatkowa dat
        int stopIndex = CellReference.convertColStringToIndex(traMediaPlanDescriptor.getTraMediaPlanTemplate().getPlaylistDateEndColumn()); //Kolumna koncowa dat
        CellReference cellReference = new CellReference(traMediaPlanDescriptor.getTraMediaPlanTemplate().getPlaylistFirsValueCell()); //Kolumna adres kolumny pierwszej daty
        Row row = sheet.getRow(cellReference.getRow());

        for (int i = startIndex; i < (stopIndex - startIndex); i++) {

            Cell cell = row.getCell(i);
            try {
                dateHashMap.put(row.getCell(i).getColumnIndex(), LocalDate.parse(cell.toString(), DateTimeFormatter.ofPattern(traMediaPlanDescriptor.getTraMediaPlanTemplate().getPlaylistDatePattern())));

            } catch (DateTimeParseException ex) {
                log.debug("Excel Column doesn't have valid date time Pattern ");
            }
        }
        return dateHashMap;
    }

    private void buildBlocks(Sheet sheet, Map<Integer, LocalDate> dateHashMap, TraMediaPlan mediaPlan, Set<TraMediaPlanPlaylistDate> traPlaylists, TraMediaPlanDescriptor traMediaPlanDescriptor, CorChannel corChannel) {
        int blockColumnIndex = CellReference.convertColStringToIndex(traMediaPlanDescriptor.getTraMediaPlanTemplate().getBlockStartColumn()); /// Kolumna poczatkowa bloków
        CellReference blockStrat = new CellReference(traMediaPlanDescriptor.getTraMediaPlanTemplate().getBlockStartCell());//Pierwsza wartośc deklaracji blokwo
        CellReference blockEnd = new CellReference(traMediaPlanDescriptor.getTraMediaPlanTemplate().getBlockEndCell());//Pierwszej wartości deklaracji blokow
        Set<TraMediaPlanBlock> parsedBlocks = parseBlocks(sheet, blockStrat, blockEnd, blockColumnIndex, mediaPlan, traMediaPlanDescriptor, corChannel);
        List<TraMediaPlanBlock> blocks = traMediaPlanBlockService.traSaveBlockSet(parsedBlocks);

        dateHashMap.keySet().stream().forEach(columnIndex -> {

            TraMediaPlanPlaylistDate traMediaPlanPlaylistDate = traPlaylists.stream().filter(mediaPlanPlaylistDate -> mediaPlanPlaylistDate.getPlaylistDate().equals(dateHashMap.get(columnIndex))).findFirst().get();
            Set<TraMediaPlanEmission> traMediaPlanEmissions = findEmissionsInDay(sheet, columnIndex, traMediaPlanDescriptor, corChannel, blocks, mediaPlan, traMediaPlanPlaylistDate);
            traMediaPlanEmissionService.saveTraMediaPlanEmissions(traMediaPlanEmissions);
        });


    }

    private Set<TraMediaPlanEmission> findEmissionsInDay(Sheet sheet, Integer columnIndex, TraMediaPlanDescriptor traMediaPlanDescriptor, CorChannel corChannel, List<TraMediaPlanBlock> traBlocks, TraMediaPlan mediaPlan, TraMediaPlanPlaylistDate traMediaPlanPlaylistDates) {
        Set<TraMediaPlanEmission> traMediaPlanEmissions = Sets.newHashSet();
        CellReference emissionStart = new CellReference(traMediaPlanDescriptor.getTraMediaPlanTemplate().getFirstEmissionValueCell());//Pierwszej wartości emisji
        CellReference emissionsStop = new CellReference(traMediaPlanDescriptor.getTraMediaPlanTemplate().getLastEmissionValueCell()); //Adress komórki po przekątnej zakresu wartości emisji
        int startEmissionRowIndex = emissionStart.getRow();
        int emissionsStopRow = emissionsStop.getRow();

        for (int rowIndex = startEmissionRowIndex; rowIndex <= emissionsStopRow; rowIndex++) {
            Cell emissionCell = sheet.getRow(rowIndex).getCell(columnIndex);
            try {
                Double excelCellNumberOfEmission = Double.valueOf(emissionCell.toString().trim()); ///Parametr wypełnienia media planu
                for (int numberOfEmission = 0; numberOfEmission < excelCellNumberOfEmission.intValue(); numberOfEmission++) {
                    TraMediaPlanBlock traMediaPlanBlock = traBlocks.get(rowIndex - startEmissionRowIndex);
                    traMediaPlanEmissions.add(new TraMediaPlanEmission()
                            .timeStart(DEFAULT_START_STOP)
                            .timeStop(DEFAULT_START_STOP)
                            .mediaPlanBlock(traMediaPlanBlock)
                            .advertiment(traMediaPlanDescriptor.getLibMediaItem())
                            .channel(corChannel)
                            .order(traMediaPlanDescriptor.getOrder())
                            .mediaPlan(mediaPlan)
                            .mediaPlanPlaylistDate(traMediaPlanPlaylistDates));
                }
            } catch (NumberFormatException e) {
                log.debug("Can't parse value of cell as double");
            }
        }
        return traMediaPlanEmissions;
    }

    private Set<TraMediaPlanBlock> parseBlocks(Sheet sheet, CellReference blockCellStart, CellReference blockCellEnd, int blockColumnIndex, TraMediaPlan mediaPlan, TraMediaPlanDescriptor traMediaPlanDescriptor, CorChannel corChannel) {
        String hourSeparator = traMediaPlanDescriptor.getTraMediaPlanTemplate().getBlockHourSeparator();
        Set<TraMediaPlanBlock> blocks = Sets.newHashSet();
        int startBlockIndex = blockCellStart.getRow();
        int stopBlockIndex = blockCellEnd.getRow();
        for (int rowIndex = startBlockIndex; rowIndex <= stopBlockIndex; rowIndex++) {
            Cell blockEmission = sheet.getRow(rowIndex).getCell(blockColumnIndex);
            String[] timeRange = blockEmission.toString().split(hourSeparator);
            if (timeRange.length != 1) {
                blocks.add(new TraMediaPlanBlock().sequence(rowIndex - startBlockIndex).startBlock(LocalTime.from(DateTimeFormatter.ofPattern("HH:mm").parse(timeRange[0])).toNanoOfDay()).stopBlock(LocalTime.from(DateTimeFormatter.ofPattern("HH:mm").parse(timeRange[1])).toNanoOfDay()).channel(corChannel).mediaPlan(mediaPlan));
            }
        }
        return blocks;
    }

}
