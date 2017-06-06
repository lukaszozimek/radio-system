package io.protone.service.traffic.mediaplan;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import io.protone.domain.*;
import io.protone.service.traffic.TraPlaylistService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import static io.protone.service.traffic.TraAdvertisementShuffleService.canAddEmissionToBlock;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

/**
 * Created by lukaszozimek on 04/06/2017.
 */
@Service
public class ExcelMediaPlan {

    private final Logger log = LoggerFactory.getLogger(ExcelMediaPlan.class);

    @Inject
    private TraPlaylistService traPlaylistService;

    public List<TraPlaylist> saveMediaPlan(ByteArrayInputStream bais, CrmAccount customer, String originalFileName, TraAdvertisement traAdvertisement, CorNetwork corNetwork, CorChannel corChannel) throws IOException, SAXException {
        Workbook workbook = new HSSFWorkbook(bais);
        Sheet sheet = workbook.getSheetAt(0);

        Map<Integer, LocalDate> dateHashMap = findPlaylistInExcel(sheet);
        List<LocalDate> localDates = dateHashMap.values().stream().sorted(Comparator.comparing(LocalDate::toString)).collect(Collectors.toList());
        List<TraPlaylist> entiyPlaylists = traPlaylistService.getTraPlaylistListInRange(localDates.get(0), localDates.get(localDates.size() - 1), corNetwork.getShortcut(), corChannel.getShortcut());

        List<TraPlaylist> paredFromMediaPlan = buildPlaylist(sheet, dateHashMap, traAdvertisement, corNetwork, corChannel);
        return mapPlaylist(entiyPlaylists, paredFromMediaPlan, traAdvertisement);
    }

    private List<TraPlaylist> mapPlaylist(List<TraPlaylist> entiyPlaylists, List<TraPlaylist> parsedFromMediaPlan, TraAdvertisement traAdvertisement) {
        log.debug("Start mapping entity Playlist with parsed Playlists");
        List<TraPlaylist> traPlaylists = entiyPlaylists;
        traPlaylists.forEach(entiyPlaylist -> {
            Optional<TraPlaylist> filteredPlaylist = parsedFromMediaPlan.stream().filter(parsedPlaylist -> parsedPlaylist.getPlaylistDate().equals(entiyPlaylist.getPlaylistDate())).findFirst();
            if (filteredPlaylist.isPresent()) {
                log.debug("Found Playlist for Date {} ", filteredPlaylist.get().getPlaylistDate());
                filteredPlaylist.get().getPlaylists().stream().forEach(parsedFormExcelTraBlock -> {

                    long numberOfCommercialToPut = parsedFormExcelTraBlock.getEmissions().stream().count();
                    Set<TraBlock> entityFilteredByRangeBlockSet = entiyPlaylist.getPlaylists().stream().filter(entityTraBlock -> isInRange(parsedFormExcelTraBlock.getStartBlock(), entityTraBlock.getStartBlock(), parsedFormExcelTraBlock.getStopBlock(), entityTraBlock.getStopBlock())).collect(toSet());
                    if (isNotEmpty(entityFilteredByRangeBlockSet)) {
                        log.debug("Found Block matching to range ");
                        entityFilteredByRangeBlockSet.stream().forEach(filteredEntityTraBlock -> {
                            if (isNotEmpty(filteredEntityTraBlock.getEmissions())) {
                                Long lastTimeStop = filteredEntityTraBlock.getEmissions().stream().max(Comparator.comparingLong(TraEmission::getTimeStop)).get().getTimeStop();
                                Integer lastSequence = filteredEntityTraBlock.getEmissions().stream().max(Comparator.comparingLong(TraEmission::getSequence)).get().getSequence();
                                for (int index = 0; index < numberOfCommercialToPut; index++)
                                    if (canAddEmissionToBlock(lastTimeStop, filteredEntityTraBlock.getLength(), traAdvertisement.getMediaItem().getLength())) {
                                        TraEmission emisssion = new TraEmission().sequence(lastSequence).block(filteredEntityTraBlock).timeStart(lastTimeStop).timeStop(lastTimeStop + traAdvertisement.getMediaItem().getLength().longValue()).advertiment(traAdvertisement).channel(filteredEntityTraBlock.getChannel()).network(filteredEntityTraBlock.getNetwork());
                                        filteredEntityTraBlock.addEmissions(emisssion);
                                        if (entiyPlaylist.getPlaylists().removeIf(traBlock -> traBlock.getSequence().equals(filteredEntityTraBlock.getSequence()))) {
                                            entiyPlaylist.addPlaylists(filteredEntityTraBlock);
                                        }
                                    } else {
                                        log.debug("Can't put commercial because block size excide maximum number of seconds");
                                    }
                            } else {
                                log.debug("Block is empty");
                                Long lastTimeStop = 0L;
                                TraEmission emisssion = new TraEmission().block(filteredEntityTraBlock).timeStart(lastTimeStop).timeStop(lastTimeStop + traAdvertisement.getMediaItem().getLength().longValue()).advertiment(traAdvertisement).sequence(0).channel(filteredEntityTraBlock.getChannel()).network(filteredEntityTraBlock.getNetwork());
                                filteredEntityTraBlock.addEmissions(emisssion);
                                if (entiyPlaylist.getPlaylists().removeIf(traBlock -> traBlock.getSequence().equals(filteredEntityTraBlock.getSequence()))) {
                                    entiyPlaylist.addPlaylists(filteredEntityTraBlock);
                                }


                            }
                        });
                    }
                });
            }
        });
        return traPlaylists;
    }


    private boolean isInRange(long parsedStartBlock, long entityStratBlock, long parsedEndBlock, long entityEndBlock) {
        return entityStratBlock >= parsedStartBlock && entityEndBlock <= parsedEndBlock;
    }

    @VisibleForTesting
    public Map<Integer, LocalDate> findPlaylistInExcel(Sheet sheet) {
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

    @VisibleForTesting
    public List<TraPlaylist> buildPlaylist(Sheet sheet, Map<Integer, LocalDate> dateHashMap, TraAdvertisement traAdvertisement, CorNetwork corNetwork, CorChannel corChannel) {
        List<TraPlaylist> traPlaylists = Lists.newArrayList();
        int blockColumnIndex = CellReference.convertColStringToIndex("A"); /// Kolumna poczatkowa bloków
        CellReference blockStrat = new CellReference("A10");//Pierwsza wartośc deklaracji blokwo
        CellReference blockEnd = new CellReference("A47");//Pierwszej wartości deklaracji blokow
        List<TraBlock> parsedBlocks = parseBlocks(sheet, blockStrat, blockEnd, blockColumnIndex);

        dateHashMap.keySet().stream().forEach(columnIndex -> {
            traPlaylists.add(new TraPlaylist().playlistDate(dateHashMap.get(columnIndex)).playlists(findEmissionsInDay(sheet, columnIndex, traAdvertisement, corNetwork, corChannel, parsedBlocks)));
        });
        return traPlaylists;

    }

    private List<TraBlock> parseBlocks(Sheet sheet, CellReference blockCellStart, CellReference blockCellEnd, int blockColumnIndex) {
        String hourSeparator = "-";
        List<TraBlock> blocks = Lists.newArrayList();
        int startBlockIndex = blockCellStart.getRow();
        int stopBlockIndex = blockCellEnd.getRow();
        for (int rowIndex = startBlockIndex; rowIndex <= stopBlockIndex; rowIndex++) {
            Cell blockEmission = sheet.getRow(rowIndex).getCell(blockColumnIndex);
            String[] timeRange = blockEmission.toString().split(hourSeparator);
            if (timeRange.length != 0) {
                blocks.add(new TraBlock().sequence(rowIndex - startBlockIndex).startBlock(LocalTime.parse(timeRange[0]).toNanoOfDay()).stopBlock(LocalTime.parse(timeRange[1]).toNanoOfDay()));
            }
        }
        return blocks;
    }


    private Set<TraBlock> findEmissionsInDay(Sheet sheet, Integer columnIndex, TraAdvertisement traAdvertisement, CorNetwork corNetwork, CorChannel corChannel, List<TraBlock> traBlocks) {

        CellReference emissionStart = new CellReference("G10");//Pierwszej wartości emisji
        CellReference emissionsStop = new CellReference("CW47"); //Adress komórki po przekątnej zakresu wartości emisji
        int startEmissionRowIndex = emissionStart.getRow();
        int emissionsStopRow = emissionsStop.getRow();

        for (int rowIndex = startEmissionRowIndex; rowIndex <= emissionsStopRow; rowIndex++) {
            Cell emissionCell = sheet.getRow(rowIndex).getCell(columnIndex);
            if (emissionCell.toString().trim().equalsIgnoreCase("1.0")) { ///Parametr wypełnienia media planu
                traBlocks.get(rowIndex - startEmissionRowIndex).addEmissions(new TraEmission().advertiment(traAdvertisement).network(corNetwork).channel(corChannel));
            }
        }
        return new HashSet<>(traBlocks);
    }
}
