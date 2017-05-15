package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
    private Long id;

    @Column(name = "time_start")
    private Long timeStart;

    @Column(name = "time_stop")
    private Long timeStop;

    @ManyToOne
    private CorNetwork network;

    @ManyToOne
    private CorChannel channel;

    @ManyToOne
    private TraAdvertisement advertiment;

    @ManyToOne
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

    public TraEmission timeStart(Long timeStart) {
        this.timeStart = timeStart;
        return this;
    }

    public void setTimeStart(Long timeStart) {
        this.timeStart = timeStart;
    }

    public Long getTimeStop() {
        return timeStop;
    }

    public TraEmission timeStop(Long timeStop) {
        this.timeStop = timeStop;
        return this;
    }

    public void setTimeStop(Long timeStop) {
        this.timeStop = timeStop;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public TraEmission network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public TraEmission channel(CorChannel corChannel) {
        this.channel = corChannel;
        return this;
    }

    public void setChannel(CorChannel corChannel) {
        this.channel = corChannel;
    }

    public TraAdvertisement getAdvertiment() {
        return advertiment;
    }

    public TraEmission advertiment(TraAdvertisement traAdvertisement) {
        this.advertiment = traAdvertisement;
        return this;
    }

    public void setAdvertiment(TraAdvertisement traAdvertisement) {
        this.advertiment = traAdvertisement;
    }

    public TraBlock getBlock() {
        return block;
    }

    public TraEmission block(TraBlock traBlock) {
        this.block = traBlock;
        return this;
    }

    public void setBlock(TraBlock traBlock) {
        this.block = traBlock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraEmission traEmission = (TraEmission) o;
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
            ", timeStart='" + timeStart + "'" +
            ", timeStop='" + timeStop + "'" +
            '}';
    }
}
