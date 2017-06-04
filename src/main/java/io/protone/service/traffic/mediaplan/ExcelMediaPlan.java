package io.protone.service.traffic.mediaplan;

import com.google.common.collect.Lists;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import io.protone.domain.*;
import io.protone.service.traffic.TraPlaylistService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by lukaszozimek on 04/06/2017.
 */
@Service
public class ExcelMediaPlan {

    private final Logger log = LoggerFactory.getLogger(ExcelMediaPlan.class);

    @Inject
    private TraPlaylistService traPlaylistService;

    public void saveMediaPlan(ByteArrayInputStream bais, CrmAccount customer, String originalFileName, TraAdvertisement traAdvertisement, CorNetwork corNetwork, CorChannel corChannel) throws IOException, SAXException {
        Workbook workbook = new HSSFWorkbook(bais);
        Sheet sheet = workbook.getSheetAt(0);

        Map<Integer, LocalDate> dateHashMap = findPlaylistInExcel(sheet);
        List<LocalDate> localDates = dateHashMap.values().stream().sorted(Comparator.comparing(LocalDate::toString)).collect(Collectors.toList());
        List<TraPlaylist> traPlaylists = traPlaylistService.getTraPlaylistListInRange(localDates.get(0), localDates.get(localDates.lastIndexOf(localDates)), corNetwork.getShortcut(), corChannel.getShortcut());

        ////Emission Cells
        Map<LocalDate, List<TraEmission>> dateTraEmissionMap = findEmissions(sheet, dateHashMap, traAdvertisement, corNetwork, corChannel);

    }

    private Map<Integer, LocalDate> findPlaylistInExcel(Sheet sheet) {
        Map<Integer, LocalDate> dateHashMap = new HashMap<>();
        int startIndex = CellReference.convertColStringToIndex("G"); //Kolumna poczatkowa dat
        int stopIndex = CellReference.convertColStringToIndex("CW"); //Kolumna koncowa dat
        CellReference cellReference = new CellReference("G8"); //Kolumna adres kolumny pierwszej daty
        Row row = sheet.getRow(cellReference.getRow());

        for (int i = startIndex; i < (stopIndex - startIndex); i++) {

            Cell cell = row.getCell(i);
            try {
                dateHashMap.put(row.getCell(i).getColumnIndex(), LocalDate.parse(cell.toString(), DateTimeFormatter.ofPattern("dd-MMM-yyyy")));

            } catch (DateTimeParseException ex) {
                log.debug("Excel Column doesn't have valid date time Pattern ");
            }
        }
        return dateHashMap;

    }

    private Map<LocalDate, List<TraBlock>> findBlocks() {
        CellReference blockStrat = new CellReference("G10");//Pierwszej wartości

        return new HashMap<>();

    }

    private Map<LocalDate, List<TraEmission>> findEmissions(Sheet sheet, Map<Integer, LocalDate> dateHashMap, TraAdvertisement traAdvertisement, CorNetwork corNetwork, CorChannel corChannel) {
        Map<LocalDate, List<TraEmission>> dateTraEmissionMap = new HashMap<>();
        CellReference emissionStart = new CellReference("G10");//Pierwszej wartości
        CellReference emissionsStop = new CellReference("CW47"); //Adress komórki po przekątnej zakresu wartości
        int startEmissionRowIndex = emissionStart.getRow();
        int emissionsStopRow = emissionsStop.getRow();

        dateHashMap.keySet().stream().forEach(columnIndex -> {
            List<TraEmission> traEmissions = Lists.newArrayList();
            for (int rowIndex = startEmissionRowIndex; rowIndex < emissionsStopRow - startEmissionRowIndex; rowIndex++) {
                Cell emissionCell = sheet.getRow(rowIndex).getCell(columnIndex);
                if (emissionCell.toString().trim().equalsIgnoreCase("1.0")) { ///Parametr wypełnienia media planu
                    traEmissions.add(new TraEmission().advertiment(traAdvertisement).network(corNetwork).channel(corChannel));
                }
            }
            dateTraEmissionMap.put(dateHashMap.get(columnIndex), traEmissions);

        });
        return dateTraEmissionMap;
    }
}
