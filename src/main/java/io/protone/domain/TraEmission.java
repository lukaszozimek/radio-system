package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TraEmission.
 */
@Entity
@Table(name = "tra_emission")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TraEmission implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @PodamExclude
    private Long id;

    @Column(name = "time_start")
    private Long timeStart;

    @Column(name = "time_stop")
    private Long timeStop;

    @ManyToOne
    @PodamExclude
    private CorNetwork network;

    @ManyToOne
    @PodamExclude
    private CorChannel channel;

    @ManyToOne
    @PodamExclude
    private TraAdvertisement advertiment;

    @ManyToOne
    @PodamExclude
    private TraBlock block;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Long timeStart) {
        this.timeStart = timeStart;
    }

    public TraEmission timeStart(Long timeStart) {
        this.timeStart = timeStart;
        return this;
    }

    public Long getTimeStop() {
        return timeStop;
    }

    public void setTimeStop(Long timeStop) {
        this.timeStop = timeStop;
    }

    public TraEmission timeStop(Long timeStop) {
        this.timeStop = timeStop;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public TraEmission network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel corChannel) {
        this.channel = corChannel;
    }

    public TraEmission channel(CorChannel corChannel) {
        this.channel = corChannel;
        return this;
    }

    public TraAdvertisement getAdvertiment() {
        return advertiment;
    }

    public void setAdvertiment(TraAdvertisement traAdvertisement) {
        this.advertiment = traAdvertisement;
    }

    public TraEmission advertiment(TraAdvertisement traAdvertisement) {
        this.advertiment = traAdvertisement;
        return this;
    }

    public TraBlock getBlock() {
        return block;
    }

    public void setBlock(TraBlock traBlock) {
        this.block = traBlock;
    }

    public TraEmission block(TraBlock traBlock) {
        this.block = traBlock;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraEmission that = (TraEmission) o;

        if (!getId().equals(that.getId())) return false;
        if (!getTimeStart().equals(that.getTimeStart())) return false;
        if (!getTimeStop().equals(that.getTimeStop())) return false;
        if (!getNetwork().equals(that.getNetwork())) return false;
        if (!getChannel().equals(that.getChannel())) return false;
        if (!getAdvertiment().equals(that.getAdvertiment())) return false;
        return getBlock().equals(that.getBlock());
    }

    @Override
    public int hashCode() {
        int result = id != null ? getId().hashCode() : 0;
        result = 31 * result + getTimeStart().hashCode();
        result = 31 * result + getTimeStop().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TraEmission{" +
            "id=" + id +
            ", timeStart='" + timeStart + "'" +
            ", timeStop='" + timeStop + "'" +
            '}';
    }
}
