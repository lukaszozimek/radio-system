package io.protone.traffic.domain;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.LibMediaItem;
import uk.co.jemos.podam.common.PodamExclude;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A TraEmission.
 */
public class TraLogEmission implements Serializable {

    private static final long serialVersionUID = 1L;

    @PodamExclude
    private Long id;

    private Integer sequence;

    private Long timeStart;

    private Long timeStop;

    private BigDecimal price;

    private boolean fixedPosition;

    private boolean firsrPosition;

    private boolean lastPosition;

    private TraOrder order;


    private CorChannel channel;

    private LibMediaItem advertiment;

    private LocalDate playlistDate;


    private TraBlock traBlock;

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

    public TraLogEmission sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public Long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Long timeStart) {
        this.timeStart = timeStart;
    }

    public TraLogEmission timeStart(Long timeStart) {
        this.timeStart = timeStart;
        return this;
    }

    public Long getTimeStop() {
        return timeStop;
    }

    public void setTimeStop(Long timeStop) {
        this.timeStop = timeStop;
    }

    public TraLogEmission timeStop(Long timeStop) {
        this.timeStop = timeStop;
        return this;
    }

    public TraOrder getOrder() {
        return order;
    }

    public void setOrder(TraOrder traOrder) {
        this.order = traOrder;
    }

    public TraLogEmission order(TraOrder traOrder) {
        this.order = traOrder;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel corChannel) {
        this.channel = corChannel;
    }

    public TraLogEmission channel(CorChannel corChannel) {
        this.channel = corChannel;
        return this;
    }

    public LibMediaItem getAdvertiment() {
        return advertiment;
    }

    public void setAdvertiment(LibMediaItem traAdvertisement) {
        this.advertiment = traAdvertisement;
    }

    public TraLogEmission advertiment(LibMediaItem traAdvertisement) {
        this.advertiment = traAdvertisement;
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

    public TraLogEmission firstPosition(boolean firsrPosition) {
        this.firsrPosition = firsrPosition;
        return this;
    }

    public boolean isLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(boolean lastPosition) {
        this.lastPosition = lastPosition;
    }

    public TraLogEmission lastPosition(boolean lastPostion) {
        this.lastPosition = lastPostion;
        return this;
    }

    public TraLogEmission fixedPosition(boolean fixedPosition) {
        this.fixedPosition = fixedPosition;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public TraBlock getTraBlock() {
        return traBlock;
    }

    public TraLogEmission traBlock(TraBlock traBlock) {
        this.traBlock = traBlock;
        return this;
    }

    public void setTraBlock(TraBlock traBlock) {
        this.traBlock = traBlock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TraLogEmission traEmission = (TraLogEmission) o;
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

    public LocalDate getPlaylistDate() {
        return playlistDate;
    }

    public TraLogEmission playlistDate(LocalDate localDate) {
        this.playlistDate = localDate;
        return this;
    }

    public void setPlaylistDate(LocalDate playlistDate) {
        this.playlistDate = playlistDate;
    }

    public long getStartBlock() {
        return this.traBlock.getStartBlock();
    }
}
