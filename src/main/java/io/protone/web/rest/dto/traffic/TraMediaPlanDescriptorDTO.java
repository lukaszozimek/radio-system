package io.protone.web.rest.dto.traffic;

import io.protone.web.rest.dto.traffic.thin.TraAdvertisementThinDTO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by lukaszozimek on 10/06/2017.
 */
public class TraMediaPlanDescriptorDTO implements Serializable {

    @NotNull
    private String blockStartCell;

    @NotNull
    private String blockEndCell;

    @NotNull
    private String blockHourSeparator;

    @NotNull
    private String playlistDateStartColumn;

    @NotNull
    private String playlistDateEndColumn;

    @NotNull
    private String playlistFirsValueCell;

    @NotNull
    private String playlistDatePattern;

    @NotNull
    private Integer sheetIndexOfMediaPlan;

    @NotNull
    private TraAdvertisementThinDTO traAdvertisement;
    @NotNull
    private String blockStartColumn;
    @NotNull
    private String firstEmissionValueCell;
    @NotNull
    private String lastEmissionValueCell;

    public TraMediaPlanDescriptorDTO() {
    }

    public String getBlockStartCell() {
        return blockStartCell;
    }

    public void setBlockStartCell(String blockStartCell) {
        this.blockStartCell = blockStartCell;
    }

    public String getBlockEndCell() {
        return blockEndCell;
    }

    public void setBlockEndCell(String blockEndCell) {
        this.blockEndCell = blockEndCell;
    }

    public String getBlockHourSeparator() {
        return blockHourSeparator;
    }

    public void setBlockHourSeparator(String blockHourSeparator) {
        this.blockHourSeparator = blockHourSeparator;
    }

    public String getPlaylistDateStartColumn() {
        return playlistDateStartColumn;
    }

    public void setPlaylistDateStartColumn(String playlistDateStartColumn) {
        this.playlistDateStartColumn = playlistDateStartColumn;
    }

    public String getPlaylistDateEndColumn() {
        return playlistDateEndColumn;
    }

    public void setPlaylistDateEndColumn(String playlistDateEndColumn) {
        this.playlistDateEndColumn = playlistDateEndColumn;
    }

    public String getPlaylistFirsValueCell() {
        return playlistFirsValueCell;
    }

    public void setPlaylistFirsValueCell(String playlistFirsValueCell) {
        this.playlistFirsValueCell = playlistFirsValueCell;
    }

    public String getPlaylistDatePattern() {
        return playlistDatePattern;
    }

    public void setPlaylistDatePattern(String playlistDatePattern) {
        this.playlistDatePattern = playlistDatePattern;
    }

    public Integer getSheetIndexOfMediaPlan() {
        return sheetIndexOfMediaPlan;
    }

    public void setSheetIndexOfMediaPlan(Integer sheetIndexOfMediaPlan) {
        this.sheetIndexOfMediaPlan = sheetIndexOfMediaPlan;
    }

    public void setTraAdvertisement(TraAdvertisementThinDTO traAdvertisement) {
        this.traAdvertisement = traAdvertisement;
    }

    public TraAdvertisementThinDTO getTraAdvertisement() {
        return traAdvertisement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraMediaPlanDescriptorDTO that = (TraMediaPlanDescriptorDTO) o;

        if (blockStartCell != null ? !blockStartCell.equals(that.blockStartCell) : that.blockStartCell != null)
            return false;
        if (blockEndCell != null ? !blockEndCell.equals(that.blockEndCell) : that.blockEndCell != null) return false;
        if (blockHourSeparator != null ? !blockHourSeparator.equals(that.blockHourSeparator) : that.blockHourSeparator != null)
            return false;
        if (playlistDateStartColumn != null ? !playlistDateStartColumn.equals(that.playlistDateStartColumn) : that.playlistDateStartColumn != null)
            return false;
        if (playlistDateEndColumn != null ? !playlistDateEndColumn.equals(that.playlistDateEndColumn) : that.playlistDateEndColumn != null)
            return false;
        if (playlistFirsValueCell != null ? !playlistFirsValueCell.equals(that.playlistFirsValueCell) : that.playlistFirsValueCell != null)
            return false;
        if (playlistDatePattern != null ? !playlistDatePattern.equals(that.playlistDatePattern) : that.playlistDatePattern != null)
            return false;
        if (sheetIndexOfMediaPlan != null ? !sheetIndexOfMediaPlan.equals(that.sheetIndexOfMediaPlan) : that.sheetIndexOfMediaPlan != null)
            return false;
        return traAdvertisement != null ? traAdvertisement.equals(that.traAdvertisement) : that.traAdvertisement == null;
    }

    @Override
    public int hashCode() {
        int result = blockStartCell != null ? blockStartCell.hashCode() : 0;
        result = 31 * result + (blockEndCell != null ? blockEndCell.hashCode() : 0);
        result = 31 * result + (blockHourSeparator != null ? blockHourSeparator.hashCode() : 0);
        result = 31 * result + (playlistDateStartColumn != null ? playlistDateStartColumn.hashCode() : 0);
        result = 31 * result + (playlistDateEndColumn != null ? playlistDateEndColumn.hashCode() : 0);
        result = 31 * result + (playlistFirsValueCell != null ? playlistFirsValueCell.hashCode() : 0);
        result = 31 * result + (playlistDatePattern != null ? playlistDatePattern.hashCode() : 0);
        result = 31 * result + (sheetIndexOfMediaPlan != null ? sheetIndexOfMediaPlan.hashCode() : 0);
        result = 31 * result + (traAdvertisement != null ? traAdvertisement.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "{" +
            "blockStartCell='" + blockStartCell + '\'' +
            ", blockEndCell='" + blockEndCell + '\'' +
            ", blockHourSeparator='" + blockHourSeparator + '\'' +
            ", playlistDateStartColumn='" + playlistDateStartColumn + '\'' +
            ", playlistDateEndColumn='" + playlistDateEndColumn + '\'' +
            ", playlistFirsValueCell='" + playlistFirsValueCell + '\'' +
            ", playlistDatePattern='" + playlistDatePattern + '\'' +
            ", sheetIndexOfMediaPlan=" + sheetIndexOfMediaPlan +
            ", traAdvertisement=" + traAdvertisement +
            '}';
    }

    public TraMediaPlanDescriptorDTO sheetIndexOfMediaPlan(int sheetIndexOfMediaPlan) {
        this.sheetIndexOfMediaPlan = sheetIndexOfMediaPlan;
        return this;
    }

    public TraMediaPlanDescriptorDTO playlistDatePattern(String playlistDatePattern) {
        this.playlistDatePattern = playlistDatePattern;
        return this;
    }

    public TraMediaPlanDescriptorDTO playlistDateStartColumn(String playlistDateStartColumn) {
        this.playlistDateStartColumn = playlistDateStartColumn;
        return this;
    }

    public TraMediaPlanDescriptorDTO playlistDateEndColumn(String playlistDateEndColumn) {
        this.playlistDateEndColumn = playlistDateEndColumn;
        return this;
    }

    public TraMediaPlanDescriptorDTO playlistFirsValueCell(String playlistFirsValueCell) {
        this.playlistFirsValueCell = playlistFirsValueCell;
        return this;
    }

    public TraMediaPlanDescriptorDTO blockStartCell(String blockStartCell) {
        this.blockStartCell = blockStartCell;
        return this;
    }

    public TraMediaPlanDescriptorDTO blockEndCell(String blockEndCell) {
        this.blockEndCell = blockEndCell;
        return this;
    }

    public TraMediaPlanDescriptorDTO blockStartColumn(String blockStartColumn) {
        this.blockStartColumn = blockStartColumn;
        return this;
    }

    public TraMediaPlanDescriptorDTO blockHourSeparator(String blockHourSeparator) {
        this.blockHourSeparator = blockHourSeparator;
        return this;
    }

    public TraMediaPlanDescriptorDTO firstEmissionValueCell(String firstEmissionValueCell) {
        this.firstEmissionValueCell = firstEmissionValueCell;
        return this;
    }

    public TraMediaPlanDescriptorDTO lastEmissionValueCell(String lastEmissionValueCell) {
        this.lastEmissionValueCell = lastEmissionValueCell;
        return this;
    }

    public TraMediaPlanDescriptorDTO traAdvertisment(TraAdvertisementThinDTO traAdvertisementThinDto) {
        this.traAdvertisement = traAdvertisementThinDto;
        return this;
    }

    public String getBlockStartColumn() {
        return blockStartColumn;
    }

    public void setBlockStartColumn(String blockStartColumn) {
        this.blockStartColumn = blockStartColumn;
    }

    public String getFirstEmissionValueCell() {
        return firstEmissionValueCell;
    }

    public void setFirstEmissionValueCell(String firstEmissionValueCell) {
        this.firstEmissionValueCell = firstEmissionValueCell;
    }

    public String getLastEmissionValueCell() {
        return lastEmissionValueCell;
    }

    public void setLastEmissionValueCell(String lastEmissionValueCell) {
        this.lastEmissionValueCell = lastEmissionValueCell;
    }
}
