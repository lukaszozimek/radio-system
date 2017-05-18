package io.protone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

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

    @Column(name = "start_block")
    private Long startBlock;

    @Column(name = "stop_block")
    private Long stopBlock;

    @ManyToOne
    @PodamExclude
    private CorNetwork network;

    @ManyToOne
    @PodamExclude
    private CorChannel channel;

    @ManyToOne
    @PodamExclude
    private TraPlaylist block;

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

    public void setName(String name) {
        this.name = name;
    }

    public TraBlock name(String name) {
        this.name = name;
        return this;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public TraBlock length(Long length) {
        this.length = length;
        return this;
    }

    public Long getStartBlock() {
        return startBlock;
    }

    public void setStartBlock(Long startBlock) {
        this.startBlock = startBlock;
    }

    public TraBlock startBlock(Long startBlock) {
        this.startBlock = startBlock;
        return this;
    }

    public Long getStopBlock() {
        return stopBlock;
    }

    public void setStopBlock(Long stopBlock) {
        this.stopBlock = stopBlock;
    }

    public TraBlock stopBlock(Long stopBlock) {
        this.stopBlock = stopBlock;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public TraBlock network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel corChannel) {
        this.channel = corChannel;
    }

    public TraBlock channel(CorChannel corChannel) {
        this.channel = corChannel;
        return this;
    }

    public TraPlaylist getBlock() {
        return block;
    }

    public void setBlock(TraPlaylist traPlaylist) {
        this.block = traPlaylist;
    }

    public TraBlock block(TraPlaylist traPlaylist) {
        this.block = traPlaylist;
        return this;
    }

    public Set<TraEmission> getEmissions() {
        return emissions;
    }

    public void setEmissions(Set<TraEmission> traEmissions) {
        this.emissions = traEmissions;
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraBlock traBlock = (TraBlock) o;

        if (!getId().equals(traBlock.getId())) return false;
        if (!getName().equals(traBlock.getName())) return false;
        if (!getLength().equals(traBlock.getLength())) return false;
        if (!getStartBlock().equals(traBlock.getStartBlock())) return false;
        if (!getStopBlock().equals(traBlock.getStopBlock())) return false;
        if (!getNetwork().equals(traBlock.getNetwork())) return false;
        if (!getChannel().equals(traBlock.getChannel())) return false;
        if (!getBlock().equals(traBlock.getBlock())) return false;
        return getEmissions().equals(traBlock.getEmissions());
    }

    @Override
    public int hashCode() {
        int result = id != null ? getId().hashCode() : 0;
        result = 31 * result + getName().hashCode();
        result = 31 * result + length.hashCode();
        result = 31 * result + startBlock.hashCode();
        result = 31 * result + stopBlock.hashCode();

        return result;
    }


    @Override
    public String toString() {
        return "TraBlock{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", length='" + length + "'" +
            ", startBlock='" + startBlock + "'" +
            ", stopBlock='" + stopBlock + "'" +
            '}';
    }
}
