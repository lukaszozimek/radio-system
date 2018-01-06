package io.protone.traffic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TraBlock.
 */
@Entity
@Table(name = "tra_media_plan_block")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TraMediaPlanBlock extends AbstractAuditingEntity implements Serializable {

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
    @PodamExclude
    private CorChannel channel;

    @ManyToOne
    @JsonIgnore
    @PodamExclude
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private TraMediaPlan mediaPlan;

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

    public TraMediaPlanBlock name(String name) {
        this.name = name;
        return this;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public TraMediaPlanBlock length(Long length) {
        this.length = length;
        return this;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public TraMediaPlanBlock sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public Long getStartBlock() {
        return startBlock;
    }

    public void setStartBlock(Long startBlock) {
        this.startBlock = startBlock;
    }

    public TraMediaPlanBlock startBlock(Long startBlock) {
        this.startBlock = startBlock;
        return this;
    }

    public Long getStopBlock() {
        return stopBlock;
    }

    public void setStopBlock(Long stopBlock) {
        this.stopBlock = stopBlock;
    }

    public TraMediaPlanBlock stopBlock(Long stopBlock) {
        this.stopBlock = stopBlock;
        return this;
    }



    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel corChannel) {
        this.channel = corChannel;
    }

    public TraMediaPlanBlock channel(CorChannel corChannel) {
        this.channel = corChannel;
        return this;
    }

    public TraMediaPlan getMediaPlan() {
        return mediaPlan;
    }

    public void setMediaPlan(TraMediaPlan mediaPlan) {
        this.mediaPlan = mediaPlan;
    }

    public TraMediaPlanBlock mediaPlan(TraMediaPlan mediaPlan) {
        this.mediaPlan = mediaPlan;
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
        TraMediaPlanBlock traBlock = (TraMediaPlanBlock) o;
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
