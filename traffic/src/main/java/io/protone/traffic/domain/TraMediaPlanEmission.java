package io.protone.traffic.domain;

import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.LibMediaItem;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A TraEmission.
 */
@Entity
@Table(name = "tra_media_plan_emission")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TraMediaPlanEmission extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @PodamExclude
    private Long id;

    @Column(name = "sequence")
    private Integer sequence;

    @Column(name = "time_start")
    private Long timeStart;

    @Column(name = "time_stop")
    private Long timeStop;


    @Column(name = "price", precision = 10, scale = 2)
    @PodamExclude
    private BigDecimal price;

    @Column(name = "fixed_postion")
    private boolean fixedPosition;

    @Column(name = "first_postion")
    private boolean firsrPosition;

    @Column(name = "last_postion")
    private boolean lastPosition;

    @ManyToOne
    @PodamExclude
    private TraOrder order;

    @ManyToOne
    @PodamExclude
    private CorNetwork network;

    @ManyToOne
    @PodamExclude
    private CorChannel channel;

    @ManyToOne
    @PodamExclude
    private LibMediaItem advertiment;

    @ManyToOne
    @PodamExclude
    private TraMediaPlan mediaPlan;

    @ManyToOne
    @PodamExclude
    private TraMediaPlanPlaylistDate mediaPlanPlaylistDate;

    @ManyToOne
    @PodamExclude
    private TraMediaPlanBlock mediaPlanBlock;

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

    public TraMediaPlanEmission sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public Long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Long timeStart) {
        this.timeStart = timeStart;
    }

    public TraMediaPlanEmission timeStart(Long timeStart) {
        this.timeStart = timeStart;
        return this;
    }

    public Long getTimeStop() {
        return timeStop;
    }

    public void setTimeStop(Long timeStop) {
        this.timeStop = timeStop;
    }

    public TraMediaPlanEmission timeStop(Long timeStop) {
        this.timeStop = timeStop;
        return this;
    }

    public TraOrder getOrder() {
        return order;
    }

    public void setOrder(TraOrder traOrder) {
        this.order = traOrder;
    }

    public TraMediaPlanEmission order(TraOrder traOrder) {
        this.order = traOrder;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public TraMediaPlanEmission network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel corChannel) {
        this.channel = corChannel;
    }

    public TraMediaPlanEmission channel(CorChannel corChannel) {
        this.channel = corChannel;
        return this;
    }

    public LibMediaItem getAdvertiment() {
        return advertiment;
    }

    public void setAdvertiment(LibMediaItem traAdvertisement) {
        this.advertiment = traAdvertisement;
    }

    public TraMediaPlanEmission advertiment(LibMediaItem traAdvertisement) {
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

    public TraMediaPlanEmission firstPosition(boolean firsrPosition) {
        this.firsrPosition = firsrPosition;
        return this;
    }

    public boolean isLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(boolean lastPosition) {
        this.lastPosition = lastPosition;
    }

    public TraMediaPlanEmission lastPosition(boolean lastPostion) {
        this.lastPosition = lastPostion;
        return this;
    }

    public TraMediaPlanEmission fixedPosition(boolean fixedPosition) {
        this.fixedPosition = fixedPosition;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public TraMediaPlan getMediaPlan() {
        return mediaPlan;
    }

    public TraMediaPlanEmission mediaPlan(TraMediaPlan mediaPlan) {
        this.mediaPlan = mediaPlan;
        return this;
    }

    public void setMediaPlan(TraMediaPlan mediaPlan) {
        this.mediaPlan = mediaPlan;
    }

    public TraMediaPlanPlaylistDate getMediaPlanPlaylistDate() {
        return mediaPlanPlaylistDate;
    }

    public TraMediaPlanEmission mediaPlanPlaylistDate(TraMediaPlanPlaylistDate mediaPlanPlaylistDate) {
        this.mediaPlanPlaylistDate = mediaPlanPlaylistDate;
        return this;
    }

    public void setMediaPlanPlaylistDate(TraMediaPlanPlaylistDate mediaPlanPlaylistDate) {
        this.mediaPlanPlaylistDate = mediaPlanPlaylistDate;
    }

    public TraMediaPlanBlock getMediaPlanBlock() {
        return mediaPlanBlock;
    }

    public TraMediaPlanEmission mediaPlanBlock(TraMediaPlanBlock mediaPlanBlock) {
        this.mediaPlanBlock = mediaPlanBlock;
        return this;
    }

    public void setMediaPlanBlock(TraMediaPlanBlock mediaPlanBlock) {
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

        TraMediaPlanEmission traEmission = (TraMediaPlanEmission) o;
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
