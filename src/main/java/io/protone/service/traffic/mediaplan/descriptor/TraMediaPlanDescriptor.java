package io.protone.service.traffic.mediaplan.descriptor;

import io.protone.domain.TraAdvertisement;

/**
 * Created by lukaszozimek on 11/06/2017.
 */
public class TraMediaPlanDescriptor {

    private String blockStartColumn;

    private String blockStartCell;

    private String blockEndCell;

    private String blockHourSeparator;

    private String playlistDateStartColumn;

    private String playlistDateEndColumn;

    private String playlistFirsValueCell;

    private String playlistDatePattern;

    private Integer sheetIndexOfMediaPlan;

    private String firstEmissionValueCell;

    private String lastEmissionValueCell;

    private TraAdvertisement traAdvertisement;

    public String getBlockStartCell() {
        return blockStartCell;
    }

    public void setBlockStartCell(String blockStartCell) {
        this.blockStartCell = blockStartCell;
    }

    public TraMediaPlanDescriptor blockStartCell(String blockStartCell) {
        this.blockStartCell = blockStartCell;
        return this;
    }

    public String getBlockEndCell() {
        return blockEndCell;
    }

    public void setBlockEndCell(String blockEndCell) {
        this.blockEndCell = blockEndCell;
    }

    public TraMediaPlanDescriptor blockEndCell(String blockEndCell) {
        this.blockEndCell = blockEndCell;
        return this;
    }

    public String getBlockHourSeparator() {
        return blockHourSeparator;
    }

    public void setBlockHourSeparator(String blockHourSeparator) {
        this.blockHourSeparator = blockHourSeparator;
    }

    public TraMediaPlanDescriptor blockHourSeparator(String blockHourSeparator) {
        this.blockHourSeparator = blockHourSeparator;
        return this;
    }

    public String getPlaylistDateStartColumn() {
        return playlistDateStartColumn;
    }

    public void setPlaylistDateStartColumn(String playlistDateStartColumn) {
        this.playlistDateStartColumn = playlistDateStartColumn;
    }

    public TraMediaPlanDescriptor playlistDateStartColumn(String playlistDateStartColumn) {
        this.playlistDateStartColumn = playlistDateStartColumn;
        return this;
    }

    public String getPlaylistDateEndColumn() {
        return playlistDateEndColumn;
    }

    public void setPlaylistDateEndColumn(String playlistDateEndColumn) {
        this.playlistDateEndColumn = playlistDateEndColumn;
    }

    public TraMediaPlanDescriptor playlistDateEndColumn(String playlistDateEndColumn) {
        this.playlistDateEndColumn = playlistDateEndColumn;
        return this;
    }

    public String getPlaylistFirsValueCell() {
        return playlistFirsValueCell;
    }

    public void setPlaylistFirsValueCell(String playlistFirsValueCell) {
        this.playlistFirsValueCell = playlistFirsValueCell;
    }

    public TraMediaPlanDescriptor playlistFirsValueCell(String playlistFirsValueCell) {
        this.playlistFirsValueCell = playlistFirsValueCell;
        return this;
    }

    public String getPlaylistDatePattern() {
        return playlistDatePattern;
    }

    public void setPlaylistDatePattern(String playlistDatePattern) {
        this.playlistDatePattern = playlistDatePattern;
    }

    public TraMediaPlanDescriptor playlistDatePattern(String playlistDatePattern) {
        this.playlistDatePattern = playlistDatePattern;
        return this;
    }

    public Integer getSheetIndexOfMediaPlan() {
        return sheetIndexOfMediaPlan;
    }

    public void setSheetIndexOfMediaPlan(Integer sheetIndexOfMediaPlan) {
        this.sheetIndexOfMediaPlan = sheetIndexOfMediaPlan;
    }

    public TraMediaPlanDescriptor sheetIndexOfMediaPlan(Integer sheetIndexOfMediaPlan) {
        this.sheetIndexOfMediaPlan = sheetIndexOfMediaPlan;
        return this;
    }

    public void setTraAdvertisement(TraAdvertisement traAdvertisement) {
        this.traAdvertisement = traAdvertisement;
    }

    public TraMediaPlanDescriptor traAdvertisment(TraAdvertisement traAdvertisement) {
        this.traAdvertisement = traAdvertisement;
        return this;
    }

    public TraAdvertisement getTraAdvertisement() {
        return traAdvertisement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraMediaPlanDescriptor that = (TraMediaPlanDescriptor) o;

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
        return "TraMediaPlanDescriptorDTO{" +
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

    public String getBlockStartColumn() {
        return blockStartColumn;
    }

    public void setBlockStartColumn(String blockStartColumn) {
        this.blockStartColumn = blockStartColumn;
    }

    public TraMediaPlanDescriptor blockStartColumn(String blockStartColumn) {
        this.blockStartColumn = blockStartColumn;
        return this;
    }

    public String getFirstEmissionValueCell() {
        return firstEmissionValueCell;
    }

    public void setFirstEmissionValueCell(String firstEmissionValueCell) {
        this.firstEmissionValueCell = firstEmissionValueCell;
    }


    public TraMediaPlanDescriptor firstEmissionValueCell(String firstEmissionValueCell) {
        this.firstEmissionValueCell = firstEmissionValueCell;
        return this;
    }

    public String getLastEmissionValueCell() {
        return lastEmissionValueCell;
    }

    public void setLastEmissionValueCell(String lastEmissionValueCell) {
        this.lastEmissionValueCell = lastEmissionValueCell;
    }
    public TraMediaPlanDescriptor lastEmissionValueCell(String lastEmissionValueCell) {
        this.lastEmissionValueCell = lastEmissionValueCell;
        return this;
    }

}
