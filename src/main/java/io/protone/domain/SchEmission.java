package io.protone.domain;

import io.protone.domain.enumeration.SchStartTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A SchEmission.
 */
@Entity
@Table(name = "sch_emission")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchEmission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "seq", nullable = false)
    private Integer seq;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private ZonedDateTime startTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "start_type", nullable = false)
    private SchStartTypeEnum startType;

    @Column(name = "relative_delay")
    private Long relativeDelay;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private ZonedDateTime endTime;

    @NotNull
    @Column(name = "length", nullable = false)
    private Long length;

    @NotNull
    @Column(name = "finished", nullable = false)
    private Boolean finished;

    @ManyToOne
    private LibMediaItem mediaItem;

    @ManyToOne
    private SchBlock block;

    @ManyToOne
    private TraCampaign campaings;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeq() {
        return seq;
    }

    public SchEmission seq(Integer seq) {
        this.seq = seq;
        return this;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public SchEmission startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public SchStartTypeEnum getStartType() {
        return startType;
    }

    public SchEmission startType(SchStartTypeEnum startType) {
        this.startType = startType;
        return this;
    }

    public void setStartType(SchStartTypeEnum startType) {
        this.startType = startType;
    }

    public Long getRelativeDelay() {
        return relativeDelay;
    }

    public SchEmission relativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
        return this;
    }

    public void setRelativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public SchEmission endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getLength() {
        return length;
    }

    public SchEmission length(Long length) {
        this.length = length;
        return this;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Boolean isFinished() {
        return finished;
    }

    public SchEmission finished(Boolean finished) {
        this.finished = finished;
        return this;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public LibMediaItem getMediaItem() {
        return mediaItem;
    }

    public SchEmission mediaItem(LibMediaItem libMediaItem) {
        this.mediaItem = libMediaItem;
        return this;
    }

    public void setMediaItem(LibMediaItem libMediaItem) {
        this.mediaItem = libMediaItem;
    }

    public SchBlock getBlock() {
        return block;
    }

    public SchEmission block(SchBlock schBlock) {
        this.block = schBlock;
        return this;
    }

    public void setBlock(SchBlock schBlock) {
        this.block = schBlock;
    }

    public TraCampaign getCampaings() {
        return campaings;
    }

    public SchEmission campaings(TraCampaign traCampaign) {
        this.campaings = traCampaign;
        return this;
    }

    public void setCampaings(TraCampaign traCampaign) {
        this.campaings = traCampaign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchEmission schEmission = (SchEmission) o;
        if (schEmission.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, schEmission.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SchEmission{" +
            "id=" + id +
            ", seq='" + seq + "'" +
            ", startTime='" + startTime + "'" +
            ", startType='" + startType + "'" +
            ", relativeDelay='" + relativeDelay + "'" +
            ", endTime='" + endTime + "'" +
            ", length='" + length + "'" +
            ", finished='" + finished + "'" +
            '}';
    }
}
