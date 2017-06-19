package io.protone.service.traffic.mediaplan;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.protone.domain.*;
import io.protone.service.traffic.mediaplan.descriptor.TraMediaPlanDescriptor;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Created by lukaszozimek on 04/06/2017.
 */
@Service
public class TraExcelMediaXlsPlan {

    private final Logger log = LoggerFactory.getLogger(TraExcelMediaXlsPlan.class);
    private final Long DEFAULT_START_STOP = 0L;

    public Set<TraMediaPlanPlaylist> parseMediaPlan(ByteArrayInputStream byteArrayInputStream, TraMediaPlanDescriptor traMediaPlanDescriptor, CorNetwork corNetwork, CorChannel corChannel) throws IOException, SAXException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(byteArrayInputStream);
        Sheet sheet = workbook.getSheetAt(traMediaPlanDescriptor.getSheetIndexOfMediaPlan());
        Map<Integer, LocalDate> dateHashMap = findPlaylistInExcel(sheet, traMediaPlanDescriptor);
        Set<TraMediaPlanPlaylist> paredFromMediaPlan = buildPlaylist(sheet, dateHashMap, traMediaPlanDescriptor, corNetwork, corChannel);
        return paredFromMediaPlan;
    }

    private Map<Integer, LocalDate> findPlaylistInExcel(Sheet sheet, TraMediaPlanDescriptor traMediaPlanDescriptor) {
        Map<Integer, LocalDate> dateHashMap = new HashMap<>();
        int startIndex = CellReference.convertColStringToIndex(traMediaPlanDescriptor.getPlaylistDateStartColumn()); //Kolumna poczatkowa dat
        int stopIndex = CellReference.convertColStringToIndex(traMediaPlanDescriptor.getPlaylistDateEndColumn()); //Kolumna koncowa dat
        CellReference cellReference = new CellReference(traMediaPlanDescriptor.getPlaylistFirsValueCell()); //Kolumna adres kolumny pierwszej daty
        Row row = sheet.getRow(cellReference.getRow());

        for (int i = startIndex; i < (stopIndex - startIndex); i++) {

            Cell cell = row.getCell(i);
            try {
                dateHashMap.put(row.getCell(i).getColumnIndex(), LocalDate.parse(cell.toString(), DateTimeFormatter.ofPattern(traMediaPlanDescriptor.getPlaylistDatePattern())));

            } catch (DateTimeParseException ex) {
                log.debug("Excel Column doesn't have valid date time Pattern ");
            }
        }
        return dateHashMap;
    }

    private Set<TraMediaPlanPlaylist> buildPlaylist(Sheet sheet, Map<Integer, LocalDate> dateHashMap, TraMediaPlanDescriptor traMediaPlanDescriptor, CorNetwork corNetwork, CorChannel corChannel) {
        Set<TraMediaPlanPlaylist> traPlaylists = Sets.newHashSet();
        int blockColumnIndex = CellReference.convertColStringToIndex(traMediaPlanDescriptor.getBlockStartColumn()); /// Kolumna poczatkowa bloków
        CellReference blockStrat = new CellReference(traMediaPlanDescriptor.getBlockStartCell());//Pierwsza wartośc deklaracji blokwo
        CellReference blockEnd = new CellReference(traMediaPlanDescriptor.getBlockEndCell());//Pierwszej wartości deklaracji blokow

        dateHashMap.keySet().stream().forEach(columnIndex -> {
            List<TraBlock> parsedBlocks = parseBlocks(sheet, blockStrat, blockEnd, blockColumnIndex, traMediaPlanDescriptor, corNetwork, corChannel);
            traPlaylists.add(new TraMediaPlanPlaylist().playlistDate(dateHashMap.get(columnIndex)).playlists(findEmissionsInDay(sheet, columnIndex, traMediaPlanDescriptor, corNetwork, corChannel, parsedBlocks)).channel(corChannel).network(corNetwork));
        });
        return traPlaylists;

    }

    private List<TraBlock> parseBlocks(Sheet sheet, CellReference blockCellStart, CellReference blockCellEnd, int blockColumnIndex, TraMediaPlanDescriptor traMediaPlanDescriptor, CorNetwork corNetwork, CorChannel corChannel) {
        String hourSeparator = traMediaPlanDescriptor.getBlockHourSeparator();
        List<TraBlock> blocks = Lists.newArrayList();
        int startBlockIndex = blockCellStart.getRow();
        int stopBlockIndex = blockCellEnd.getRow();
        for (int rowIndex = startBlockIndex; rowIndex <= stopBlockIndex; rowIndex++) {
            Cell blockEmission = sheet.getRow(rowIndex).getCell(blockColumnIndex);
            String[] timeRange = blockEmission.toString().split(hourSeparator);
            if (timeRange.length != 1) {
                blocks.add(new TraBlock().sequence(rowIndex - startBlockIndex).startBlock(LocalTime.from(DateTimeFormatter.ofPattern("HH:mm").parse(timeRange[0])).toNanoOfDay()).stopBlock(LocalTime.from(DateTimeFormatter.ofPattern("HH:mm").parse(timeRange[1])).toNanoOfDay()).network(corNetwork).channel(corChannel));
            }
        }
        return blocks;
    }


    private Set<TraBlock> findEmissionsInDay(Sheet sheet, Integer columnIndex, TraMediaPlanDescriptor traMediaPlanDescriptor, CorNetwork corNetwork, CorChannel corChannel, List<TraBlock> traBlocks) {

        CellReference emissionStart = new CellReference(traMediaPlanDescriptor.getFirstEmissionValueCell());//Pierwszej wartości emisji
        CellReference emissionsStop = new CellReference(traMediaPlanDescriptor.getLastEmissionValueCell()); //Adress komórki po przekątnej zakresu wartości emisji
        int startEmissionRowIndex = emissionStart.getRow();
        int emissionsStopRow = emissionsStop.getRow();

        for (int rowIndex = startEmissionRowIndex; rowIndex <= emissionsStopRow; rowIndex++) {
            Cell emissionCell = sheet.getRow(rowIndex).getCell(columnIndex);
            try {
                Double excelCellNumberOfEmission = Double.valueOf(emissionCell.toString().trim()); ///Parametr wypełnienia media planu
                for (int numberOfEmission = 0; numberOfEmission < excelCellNumberOfEmission.intValue(); numberOfEmission++) {
                    TraBlock traBlock = traBlocks.get(rowIndex - startEmissionRowIndex);
                    traBlock.addEmissions(new TraEmission().timeStart(DEFAULT_START_STOP).timeStop(DEFAULT_START_STOP).advertiment(traMediaPlanDescriptor.getOrder().getAdvertisment()).network(corNetwork).channel(corChannel).order(traMediaPlanDescriptor.getOrder()));
                }
            } catch (NumberFormatException e) {
                log.debug("Can't parse value of cell as double");
            }
        }
        return new HashSet<>(traBlocks);
    }


}
