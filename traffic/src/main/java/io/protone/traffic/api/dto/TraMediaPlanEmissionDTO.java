package io.protone.traffic.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by lukaszozimek on 11/06/2017.
 */
public class TraMediaPlanEmissionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer sequence;

    private Long timeStart;

    private Long timeStop;


    private BigDecimal price;

    private boolean fixedPosition;

    private boolean firsrPosition;

    private boolean lastPosition;



    private TraMediaPlanPlaylistDateDTO mediaPlanPlaylistDate;

    private TraMediaPlanBlockDTO mediaPlanBlock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public TraMediaPlanEmissionDTO sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public Long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Long timeStart) {
        this.timeStart = timeStart;
    }

    public TraMediaPlanEmissionDTO timeStart(Long timeStart) {
        this.timeStart = timeStart;
        return this;
    }

    public Long getTimeStop() {
        return timeStop;
    }

    public void setTimeStop(Long timeStop) {
        this.timeStop = timeStop;
    }

    public TraMediaPlanEmissionDTO timeStop(Long timeStop) {
        this.timeStop = timeStop;
        return this;
    }




    public boolean isFixedPosition() {
        return fixedPosition;
    }

    public void setFixedPosition(boolean fixedPosition) {
        this.fixedPosition = fixedPosition;
    }

    public boolean isFirsrPosition() {
        return firsrPosition;
    }

    public void setFirsrPosition(boolean firsrPosition) {
        this.firsrPosition = firsrPosition;
    }

    public TraMediaPlanEmissionDTO firstPosition(boolean firsrPosition) {
        this.firsrPosition = firsrPosition;
        return this;
    }

    public boolean isLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(boolean lastPosition) {
        this.lastPosition = lastPosition;
    }

    public TraMediaPlanEmissionDTO lastPosition(boolean lastPostion) {
        this.lastPosition = lastPostion;
        return this;
    }

    public TraMediaPlanEmissionDTO fixedPosition(boolean fixedPosition) {
        this.fixedPosition = fixedPosition;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public TraMediaPlanPlaylistDateDTO getMediaPlanPlaylistDate() {
        return mediaPlanPlaylistDate;
    }

    public TraMediaPlanEmissionDTO mediaPlanPlaylistDate(TraMediaPlanPlaylistDateDTO mediaPlanPlaylistDate) {
        this.mediaPlanPlaylistDate = mediaPlanPlaylistDate;
        return this;
    }

    public void setMediaPlanPlaylistDate(TraMediaPlanPlaylistDateDTO mediaPlanPlaylistDate) {
        this.mediaPlanPlaylistDate = mediaPlanPlaylistDate;
    }

    public TraMediaPlanBlockDTO getMediaPlanBlock() {
        return mediaPlanBlock;
    }

    public TraMediaPlanEmissionDTO mediaPlanBlock(TraMediaPlanBlockDTO mediaPlanBlock) {
        this.mediaPlanBlock = mediaPlanBlock;
        return this;
    }

    public void setMediaPlanBlock(TraMediaPlanBlockDTO mediaPlanBlock) {
        this.mediaPlanBlock = mediaPlanBlock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TraMediaPlanEmissionDTO traEmission = (TraMediaPlanEmissionDTO) o;
        if (traEmission.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, traEmission.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraEmission{" +
                "id=" + id +
                ", sequence='" + sequence + "'" +
                ", timeStart='" + timeStart + "'" +
                ", timeStop='" + timeStop + "'" +
                '}';
    }

}
