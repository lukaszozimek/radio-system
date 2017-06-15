package io.protone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A TraBlock.
 */
@Entity
@Table(name = "tra_block")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TraBlock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @PodamExclude
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "length")
    private Long length;

    @Column(name = "sequence")
    private Integer sequence;

    @Column(name = "start_block")
    private Long startBlock;

    @Column(name = "stop_block")
    private Long stopBlock;

    @ManyToOne
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

    @OneToMany(mappedBy = "block")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @PodamExclude
    private Set<TraEmission> emissions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TraBlock name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLength() {
        return length;
    }

    public TraBlock length(Long length) {
        this.length = length;
        return this;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Integer getSequence() {
        return sequence;
    }

    public TraBlock sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Long getStartBlock() {
        return startBlock;
    }

    public TraBlock startBlock(Long startBlock) {
        this.startBlock = startBlock;
        return this;
    }

    public void setStartBlock(Long startBlock) {
        this.startBlock = startBlock;
    }

    public Long getStopBlock() {
        return stopBlock;
    }

    public TraBlock stopBlock(Long stopBlock) {
        this.stopBlock = stopBlock;
        return this;
    }

    public void setStopBlock(Long stopBlock) {
        this.stopBlock = stopBlock;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public TraBlock network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public LibMediaItem getBlockStartSound() {
        return blockStartSound;
    }

    public TraBlock blockStartSound(LibMediaItem libMediaItem) {
        this.blockStartSound = libMediaItem;
        return this;
    }

    public void setBlockStartSound(LibMediaItem libMediaItem) {
        this.blockStartSound = libMediaItem;
    }

    public LibMediaItem getBlockEndSound() {
        return blockEndSound;
    }

    public TraBlock blockEndSound(LibMediaItem libMediaItem) {
        this.blockEndSound = libMediaItem;
        return this;
    }

    public void setBlockEndSound(LibMediaItem libMediaItem) {
        this.blockEndSound = libMediaItem;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public TraBlock channel(CorChannel corChannel) {
        this.channel = corChannel;
        return this;
    }

    public void setChannel(CorChannel corChannel) {
        this.channel = corChannel;
    }

    public Set<TraEmission> getEmissions() {
        return emissions;
    }

    public TraBlock emissions(Set<TraEmission> traEmissions) {
        this.emissions = traEmissions;
        return this;
    }

    public TraBlock addEmissions(TraEmission traEmission) {
        this.emissions.add(traEmission);
        traEmission.setBlock(this);
        return this;
    }

    public TraBlock removeEmissions(TraEmission traEmission) {
        this.emissions.remove(traEmission);
        traEmission.setBlock(null);
        return this;
    }

    public void setEmissions(Set<TraEmission> traEmissions) {
        this.emissions = traEmissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraBlock traBlock = (TraBlock) o;
        if (traBlock.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, traBlock.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraBlock{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", length='" + length + "'" +
            ", sequence='" + sequence + "'" +
            ", startBlock='" + startBlock + "'" +
            ", stopBlock='" + stopBlock + "'" +
            '}';
    }
}
