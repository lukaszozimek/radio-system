package io.protone.traffic.domain;

import io.protone.domain.enumeration.CorDayOfWeekEnum;
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

    public TraBlockConfiguration day(CorDayOfWeekEnum day) {
        this.day = day;
        return this;
    }

    public void setDay(CorDayOfWeekEnum day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public TraBlockConfiguration name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLength() {
        return length;
    }

    public TraBlockConfiguration length(Long length) {
        this.length = length;
        return this;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Long getStartBlock() {
        return startBlock;
    }

    public TraBlockConfiguration startBlock(Long startBlock) {
        this.startBlock = startBlock;
        return this;
    }

    public void setStartBlock(Long startBlock) {
        this.startBlock = startBlock;
    }

    public Long getStopBlock() {
        return stopBlock;
    }

    public TraBlockConfiguration stopBlock(Long stopBlock) {
        this.stopBlock = stopBlock;
        return this;
    }

    public void setStopBlock(Long stopBlock) {
        this.stopBlock = stopBlock;
    }

    public Integer getSequence() {
        return sequence;
    }

    public TraBlockConfiguration sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public TraBlockConfiguration network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public LibMediaItem getBlockStartSound() {
        return blockStartSound;
    }

    public TraBlockConfiguration blockStartSound(LibMediaItem libMediaItem) {
        this.blockStartSound = libMediaItem;
        return this;
    }

    public void setBlockStartSound(LibMediaItem libMediaItem) {
        this.blockStartSound = libMediaItem;
    }

    public LibMediaItem getBlockEndSound() {
        return blockEndSound;
    }

    public TraBlockConfiguration blockEndSound(LibMediaItem libMediaItem) {
        this.blockEndSound = libMediaItem;
        return this;
    }

    public void setBlockEndSound(LibMediaItem libMediaItem) {
        this.blockEndSound = libMediaItem;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public TraBlockConfiguration channel(CorChannel corChannel) {
        this.channel = corChannel;
        return this;
    }

    public void setChannel(CorChannel corChannel) {
        this.channel = corChannel;
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
