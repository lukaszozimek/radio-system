package io.protone.traffic.domain;


import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.core.domain.CorChannel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lukaszozimek on 11/06/2017.
 */
@Entity
@Table(name = "tra_media_plan_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TraMediaPlanTemplate extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "block_start_column")
    private String blockStartColumn;

    @Column(name = "block_start_cell")
    private String blockStartCell;

    @Column(name = "block_end_cell")
    private String blockEndCell;

    @Column(name = "block_hour_separator")
    private String blockHourSeparator;

    @Column(name = "playlist_date_start_column")
    private String playlistDateStartColumn;

    @Column(name = "playlist_date_end_column")
    private String playlistDateEndColumn;

    @Column(name = "playlist_first_value_cell")
    private String playlistFirsValueCell;

    @Column(name = "playlist_date_pattern")
    private String playlistDatePattern;

    @Column(name = "sheet_index_of_media_plan")
    private Integer sheetIndexOfMediaPlan;

    @Column(name = "first_emission_value_cell")
    private String firstEmissionValueCell;

    @Column(name = "last_emission_value_cell")
    private String lastEmissionValueCell;

    @ManyToOne
    @PodamExclude
    private CorChannel channel;

    public String getBlockStartCell() {
        return blockStartCell;
    }

    public void setBlockStartCell(String blockStartCell) {
        this.blockStartCell = blockStartCell;
    }

    public TraMediaPlanTemplate blockStartCell(String blockStartCell) {
        this.blockStartCell = blockStartCell;
        return this;
    }

    public String getBlockEndCell() {
        return blockEndCell;
    }

    public void setBlockEndCell(String blockEndCell) {
        this.blockEndCell = blockEndCell;
    }

    public TraMediaPlanTemplate blockEndCell(String blockEndCell) {
        this.blockEndCell = blockEndCell;
        return this;
    }

    public String getBlockHourSeparator() {
        return blockHourSeparator;
    }

    public void setBlockHourSeparator(String blockHourSeparator) {
        this.blockHourSeparator = blockHourSeparator;
    }

    public TraMediaPlanTemplate blockHourSeparator(String blockHourSeparator) {
        this.blockHourSeparator = blockHourSeparator;
        return this;
    }

    public String getPlaylistDateStartColumn() {
        return playlistDateStartColumn;
    }

    public void setPlaylistDateStartColumn(String playlistDateStartColumn) {
        this.playlistDateStartColumn = playlistDateStartColumn;
    }

    public TraMediaPlanTemplate playlistDateStartColumn(String playlistDateStartColumn) {
        this.playlistDateStartColumn = playlistDateStartColumn;
        return this;
    }

    public String getPlaylistDateEndColumn() {
        return playlistDateEndColumn;
    }

    public void setPlaylistDateEndColumn(String playlistDateEndColumn) {
        this.playlistDateEndColumn = playlistDateEndColumn;
    }

    public TraMediaPlanTemplate playlistDateEndColumn(String playlistDateEndColumn) {
        this.playlistDateEndColumn = playlistDateEndColumn;
        return this;
    }

    public String getPlaylistFirsValueCell() {
        return playlistFirsValueCell;
    }

    public void setPlaylistFirsValueCell(String playlistFirsValueCell) {
        this.playlistFirsValueCell = playlistFirsValueCell;
    }

    public TraMediaPlanTemplate playlistFirsValueCell(String playlistFirsValueCell) {
        this.playlistFirsValueCell = playlistFirsValueCell;
        return this;
    }

    public String getPlaylistDatePattern() {
        return playlistDatePattern;
    }

    public void setPlaylistDatePattern(String playlistDatePattern) {
        this.playlistDatePattern = playlistDatePattern;
    }

    public TraMediaPlanTemplate playlistDatePattern(String playlistDatePattern) {
        this.playlistDatePattern = playlistDatePattern;
        return this;
    }

    public Integer getSheetIndexOfMediaPlan() {
        return sheetIndexOfMediaPlan;
    }

    public void setSheetIndexOfMediaPlan(Integer sheetIndexOfMediaPlan) {
        this.sheetIndexOfMediaPlan = sheetIndexOfMediaPlan;
    }

    public TraMediaPlanTemplate sheetIndexOfMediaPlan(Integer sheetIndexOfMediaPlan) {
        this.sheetIndexOfMediaPlan = sheetIndexOfMediaPlan;
        return this;
    }

    public String getBlockStartColumn() {
        return blockStartColumn;
    }

    public void setBlockStartColumn(String blockStartColumn) {
        this.blockStartColumn = blockStartColumn;
    }

    public TraMediaPlanTemplate blockStartColumn(String blockStartColumn) {
        this.blockStartColumn = blockStartColumn;
        return this;
    }

    public String getFirstEmissionValueCell() {
        return firstEmissionValueCell;
    }

    public void setFirstEmissionValueCell(String firstEmissionValueCell) {
        this.firstEmissionValueCell = firstEmissionValueCell;
    }


    public TraMediaPlanTemplate firstEmissionValueCell(String firstEmissionValueCell) {
        this.firstEmissionValueCell = firstEmissionValueCell;
        return this;
    }

    public String getLastEmissionValueCell() {
        return lastEmissionValueCell;
    }

    public void setLastEmissionValueCell(String lastEmissionValueCell) {
        this.lastEmissionValueCell = lastEmissionValueCell;
    }

    public TraMediaPlanTemplate lastEmissionValueCell(String lastEmissionValueCell) {
        this.lastEmissionValueCell = lastEmissionValueCell;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraMediaPlanTemplate that = (TraMediaPlanTemplate) o;

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

        return lastEmissionValueCell != null ? !lastEmissionValueCell.equals(that.lastEmissionValueCell) : that.lastEmissionValueCell != null;
    }

    @Override
    public int hashCode() {
        int result = blockStartColumn != null ? blockStartColumn.hashCode() : 0;
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
        return "TraMediaPlanTemplate{" +
                "blockStartColumn='" + blockStartColumn + '\'' +
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

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel channel) {
        this.channel = channel;
    }

    public TraMediaPlanTemplate channel(CorChannel network) {
        this.channel = network;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public TraMediaPlanTemplate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }
}
