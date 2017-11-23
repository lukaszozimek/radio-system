package io.protone.scheduler.domain;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.List;

import static io.protone.scheduler.domain.SchDiscriminators.CLOCK_TEMPLATE;

/**
 * A Clock.
 */
@Entity
@DiscriminatorValue(CLOCK_TEMPLATE)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchClockTemplate extends SchEventTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.EAGER)
    @PodamExclude
    private CorDictionary clockCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public SchClockTemplate shortName(String shortName) {
        super.shortName(shortName);
        return this;
    }


    public SchClockTemplate name(String name) {
        super.name(name);
        return this;
    }

    public List<SchEmissionTemplate> getEmissions() {
        return super.getEmissions();
    }

    public void setEmissions(List<SchEmissionTemplate> emissions) {
        this.emissions.clear();
        if (emissions != null) {
            this.emissions.addAll(emissions);

        }
    }

    public SchClockTemplate emissions(List<SchEmissionTemplate> emissions) {
        this.emissions.clear();
        if (emissions != null) {
            this.emissions.addAll(emissions);
        }
        return this;
    }

    public SchClockTemplate addEmission(SchEmissionTemplate emission) {
        this.emissions.add(emission);
        return this;
    }

    public SchClockTemplate removeEmission(SchEmissionTemplate emission) {
        this.emissions.remove(emission);
        return this;
    }


    public SchClockTemplate network(CorNetwork network) {
        super.network(network);
        return this;
    }

    public SchClockTemplate channel(CorChannel channel) {
        super.channel(channel);
        return this;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchEventTemplate)) return false;
        SchClockTemplate that = (SchClockTemplate) o;
        return com.google.common.base.Objects.equal(getName(), that.getName()) &&
                com.google.common.base.Objects.equal(getId(), that.getId()) &&
                com.google.common.base.Objects.equal(getInstance(), that.getInstance()) &&
                com.google.common.base.Objects.equal(getShortName(), that.getShortName()) &&
                com.google.common.base.Objects.equal(getEventCategory(), that.getEventCategory()) &&
                com.google.common.base.Objects.equal(getSchEventTemplates(), that.getSchEventTemplates()) &&
                com.google.common.base.Objects.equal(getEmissionsLog(), that.getEmissionsLog()) &&
                com.google.common.base.Objects.equal(getEmissions(), that.getEmissions()) &&
                getEventType() == that.getEventType() &&
                com.google.common.base.Objects.equal(getSchLogConfiguration(), that.getSchLogConfiguration()) &&
                com.google.common.base.Objects.equal(getNetwork(), that.getNetwork()) &&
                com.google.common.base.Objects.equal(getChannel(), that.getChannel()) &&
                com.google.common.base.Objects.equal(getSequence(), that.getSequence());

    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(getId(), getName(), getSequence(), getInstance(), getShortName(), getEventCategory(), getSchEventTemplates(), getEmissionsLog(), getEmissions(), getEventType(), getSchLogConfiguration(), getNetwork(), getChannel());
    }


    public SchClockTemplate length(Long length) {
        this.length = length;
        return this;
    }

    public SchClockTemplate sequence(Long sequence) {
        super.setSequence(sequence);
        return this;
    }

    public SchClockTemplate clockCategory(CorDictionary corDictionary) {
        this.clockCategory = corDictionary;
        return this;
    }


    public CorDictionary getClockCategory() {
        return clockCategory;
    }

    public void setClockCategory(CorDictionary clockCategory) {
        this.clockCategory = clockCategory;
    }

    public SchEventTemplate id(Long id) {
        this.id = id;
        return this;
    }
}
