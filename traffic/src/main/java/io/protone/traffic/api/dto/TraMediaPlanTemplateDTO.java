package io.protone.traffic.api.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by lukaszozimek on 30/07/2017.
 */
public class TraMediaPlanTemplateDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String blockStartColumn;

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
    private String firstEmissionValueCell;

    @NotNull
    private String lastEmissionValueCell;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TraMediaPlanTemplateDTO name(String name) {
        this.name = name;
        return this;
    }

    public String getBlockStartColumn() {
        return blockStartColumn;
    }

    public void setBlockStartColumn(String blockStartColumn) {
        this.blockStartColumn = blockStartColumn;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraMediaPlanTemplateDTO that = (TraMediaPlanTemplateDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (blockStartColumn != null ? !blockStartColumn.equals(that.blockStartColumn) : that.blockStartColumn != null)
            return false;
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
        if (firstEmissionValueCell != null ? !firstEmissionValueCell.equals(that.firstEmissionValueCell) : that.firstEmissionValueCell != null)
            return false;
        return lastEmissionValueCell != null ? lastEmissionValueCell.equals(that.lastEmissionValueCell) : that.lastEmissionValueCell == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (blockStartColumn != null ? blockStartColumn.hashCode() : 0);
        result = 31 * result + (blockStartCell != null ? blockStartCell.hashCode() : 0);
        result = 31 * result + (blockEndCell != null ? blockEndCell.hashCode() : 0);
        result = 31 * result + (blockHourSeparator != null ? blockHourSeparator.hashCode() : 0);
        result = 31 * result + (playlistDateStartColumn != null ? playlistDateStartColumn.hashCode() : 0);
        result = 31 * result + (playlistDateEndColumn != null ? playlistDateEndColumn.hashCode() : 0);
        result = 31 * result + (playlistFirsValueCell != null ? playlistFirsValueCell.hashCode() : 0);
        result = 31 * result + (playlistDatePattern != null ? playlistDatePattern.hashCode() : 0);
        result = 31 * result + (sheetIndexOfMediaPlan != null ? sheetIndexOfMediaPlan.hashCode() : 0);
        result = 31 * result + (firstEmissionValueCell != null ? firstEmissionValueCell.hashCode() : 0);
        result = 31 * result + (lastEmissionValueCell != null ? lastEmissionValueCell.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TraMediaPlanTemplateDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blockStartColumn='" + blockStartColumn + '\'' +
                ", blockStartCell='" + blockStartCell + '\'' +
                ", blockEndCell='" + blockEndCell + '\'' +
                ", blockHourSeparator='" + blockHourSeparator + '\'' +
                ", playlistDateStartColumn='" + playlistDateStartColumn + '\'' +
                ", playlistDateEndColumn='" + playlistDateEndColumn + '\'' +
                ", playlistFirsValueCell='" + playlistFirsValueCell + '\'' +
                ", playlistDatePattern='" + playlistDatePattern + '\'' +
                ", sheetIndexOfMediaPlan=" + sheetIndexOfMediaPlan +
                ", firstEmissionValueCell='" + firstEmissionValueCell + '\'' +
                ", lastEmissionValueCell='" + lastEmissionValueCell + '\'' +
                '}';
    }

    public TraMediaPlanTemplateDTO sheetIndexOfMediaPlan(int sheetIndexOfMediaPlan) {
        this.sheetIndexOfMediaPlan = sheetIndexOfMediaPlan;
        return this;
    }

    public TraMediaPlanTemplateDTO playlistDatePattern(String playlistDatePattern) {
        this.playlistDatePattern = playlistDatePattern;
        return this;
    }

    public TraMediaPlanTemplateDTO playlistDateStartColumn(String playlistDateStartColumn) {
        this.playlistDateStartColumn = playlistDateStartColumn;
        return this;
    }

    public TraMediaPlanTemplateDTO playlistDateEndColumn(String playlistDateEndColumn) {
        this.playlistDateEndColumn = playlistDateEndColumn;
        return this;
    }

    public TraMediaPlanTemplateDTO playlistFirsValueCell(String playlistFirsValueCell) {
        this.playlistFirsValueCell = playlistFirsValueCell;
        return this;
    }

    public TraMediaPlanTemplateDTO blockStartCell(String blockStartCell) {
        this.blockStartCell = blockStartCell;
        return this;
    }

    public TraMediaPlanTemplateDTO blockEndCell(String blockEndCell) {
        this.blockEndCell = blockEndCell;
        return this;
    }

    public TraMediaPlanTemplateDTO blockStartColumn(String blockStartColumn) {
        this.blockStartColumn = blockStartColumn;
        return this;
    }

    public TraMediaPlanTemplateDTO blockHourSeparator(String blockHourSeparator) {
        this.blockHourSeparator = blockHourSeparator;
        return this;
    }

    public TraMediaPlanTemplateDTO firstEmissionValueCell(String firstEmissionValueCell) {
        this.firstEmissionValueCell = firstEmissionValueCell;
        return this;
    }

    public TraMediaPlanTemplateDTO lastEmissionValueCell(String lastEmissionValueCell) {
        this.lastEmissionValueCell = lastEmissionValueCell;
        return this;
    }
}
