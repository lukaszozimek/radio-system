package io.protone.scheduler.domain;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import static io.protone.scheduler.domain.SchDiscriminators.CLOCK;

/**
 * A Clock.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DiscriminatorValue(CLOCK)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class SchClock extends SchBlock implements Serializable {

    private static final long serialVersionUID = 1L;

    @PodamExclude
    @ManyToOne
    private SchSchedule schSchedule;


    @Column(name = "short_name", unique = false, nullable = false)
    protected String shortName;

    @ManyToOne(fetch = FetchType.EAGER)
    @PodamExclude
    private CorDictionary clockCategory;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SchClock shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public SchClock name(String name) {
        super.setName(name);
        return this;
    }

    public SchSchedule getSchSchedule() {
        return schSchedule;
    }

    public void setSchSchedule(SchSchedule schSchedule) {
        this.schSchedule = schSchedule;
    }

    public SchClock grid(SchSchedule schSchedule) {
        this.schSchedule = schSchedule;
        return this;
    }


    public Set<SchEmission> getEmissions() {
        return super.getEmissions();
    }

    public void setEmissions(Set<SchEmission> emissions) {
        super.setEmissions(emissions);
    }

    public SchClock emissions(Set<SchEmission> emissions) {
        super.setEmissions(emissions);
        return this;
    }

    public SchClock addEmission(SchEmission emission) {
        super.getEmissions().add(emission);
        return this;
    }

    public SchClock removeEmission(SchEmission emission) {
        super.getEmissions().remove(emission);
        return this;
    }


    public SchClock network(CorNetwork network) {
        super.setNetwork(network);
        return this;
    }


    public SchClock channel(CorChannel channel) {
        super.setChannel(channel);
        return this;
    }


    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public SchClock sequence(Long sequence) {
        this.sequence = sequence;
        return this;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public SchClock length(Long length) {
        this.length = length;
        return this;
    }

    public SchClock startTime(LocalDateTime startTime) {
        super.setStartTime(startTime);
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
        SchClock clock = (SchClock) o;
        if (clock.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clock.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SchClock{" +
                "id=" + id +
                ", schSchedule=" + schSchedule +
                ", sequence=" + sequence +
                ", length=" + length +
                '}';
    }

    public SchClock schedule(SchSchedule finalEntity) {
        this.schSchedule = finalEntity;
        return this;
    }

    public CorDictionary getClockCategory() {
        return clockCategory;
    }

    public void setClockCategory(CorDictionary clockCategory) {
        this.clockCategory = clockCategory;
    }
}
