package io.protone.traffic.domain;


import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.enumeration.CorDayOfWeekEnum;
import io.protone.library.domain.LibMediaItem;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TraBlockConfiguration.
 */
@Entity
@Table(name = "tra_block_configuration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TraBlockConfiguration extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @PodamExclude
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    private CorDayOfWeekEnum day;

    @Column(name = "name")
    private String name;

    @Column(name = "length")
    private Long length;

    @Column(name = "start_block")
    private Long startBlock;

    @Column(name = "stop_block")
    private Long stopBlock;

    @Column(name = "sequence")
    private Integer sequence;

    @ManyToOne
    @PodamExclude
    private CorNetwork network;

    @ManyToOne
    @PodamExclude
    private LibMediaItem blockStartSound;

    @ManyToOne
    @PodamExclude
    private LibMediaItem blockEndSound;

    @ManyToOne
    @PodamExclude
    private CorChannel channel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CorDayOfWeekEnum getDay() {
        return day;
    }

    public void setDay(CorDayOfWeekEnum day) {
        this.day = day;
    }

    public TraBlockConfiguration day(CorDayOfWeekEnum day) {
        this.day = day;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TraBlockConfiguration name(String name) {
        this.name = name;
        return this;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public TraBlockConfiguration length(Long length) {
        this.length = length;
        return this;
    }

    public Long getStartBlock() {
        return startBlock;
    }

    public void setStartBlock(Long startBlock) {
        this.startBlock = startBlock;
    }

    public TraBlockConfiguration startBlock(Long startBlock) {
        this.startBlock = startBlock;
        return this;
    }

    public Long getStopBlock() {
        return stopBlock;
    }

    public void setStopBlock(Long stopBlock) {
        this.stopBlock = stopBlock;
    }

    public TraBlockConfiguration stopBlock(Long stopBlock) {
        this.stopBlock = stopBlock;
        return this;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public TraBlockConfiguration sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public TraBlockConfiguration network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public LibMediaItem getBlockStartSound() {
        return blockStartSound;
    }

    public void setBlockStartSound(LibMediaItem libMediaItem) {
        this.blockStartSound = libMediaItem;
    }

    public TraBlockConfiguration blockStartSound(LibMediaItem libMediaItem) {
        this.blockStartSound = libMediaItem;
        return this;
    }

    public LibMediaItem getBlockEndSound() {
        return blockEndSound;
    }

    public void setBlockEndSound(LibMediaItem libMediaItem) {
        this.blockEndSound = libMediaItem;
    }

    public TraBlockConfiguration blockEndSound(LibMediaItem libMediaItem) {
        this.blockEndSound = libMediaItem;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel corChannel) {
        this.channel = corChannel;
    }

    public TraBlockConfiguration channel(CorChannel corChannel) {
        this.channel = corChannel;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraBlockConfiguration traBlockConfiguration = (TraBlockConfiguration) o;
        if (traBlockConfiguration.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, traBlockConfiguration.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraBlockConfiguration{" +
            "id=" + id +
            ", day='" + day + "'" +
            ", name='" + name + "'" +
            ", length='" + length + "'" +
            ", startBlock='" + startBlock + "'" +
            ", stopBlock='" + stopBlock + "'" +
            ", sequence='" + sequence + "'" +
            '}';
    }
}
