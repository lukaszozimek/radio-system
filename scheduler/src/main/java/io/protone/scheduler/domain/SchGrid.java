package io.protone.scheduler.domain;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.enumeration.CorDayOfWeekEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import static io.protone.scheduler.domain.SchDiscriminators.GRID_TEMPLATE;

/**
 * A Grid.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(GRID_TEMPLATE)
public class SchGrid extends SchEventTemplate implements Serializable {

    private static final long serialVersionUID = 1L;


    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")
    private CorDayOfWeekEnum dayOfWeek;

    @Column(name = "default_grid")
    private Boolean defaultGrid = null;

    @ManyToOne
    @PodamExclude
    private CorDictionary gridCategory;

    @Transient
    private List<SchClockTemplate> internalClockcs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public CorDayOfWeekEnum getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(CorDayOfWeekEnum dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public SchGrid dayOfWeek(CorDayOfWeekEnum dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchGrid name(String name) {
        this.name = name;
        return this;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public SchGrid shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public Boolean getDefaultGrid() {
        return defaultGrid;
    }

    public void setDefaultGrid(Boolean defaultGrid) {
        this.defaultGrid = defaultGrid;
    }

    public CorDictionary getGridCategory() {
        return gridCategory;
    }

    public void setGridCategory(CorDictionary gridCategory) {
        this.gridCategory = gridCategory;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork network) {
        this.network = network;
    }

    public SchGrid network(CorNetwork network) {
        this.network = network;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel channel) {
        this.channel = channel;
    }

    public SchGrid channel(CorChannel channel) {
        this.channel = channel;
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
        SchGrid grid = (SchGrid) o;
        if (grid.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), grid.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Grid{" +
                "id=" + getId() +
                ", dayOfWeek='" + getDayOfWeek() + "'" +
                ", name='" + getName() + "'" +
                ", shortName='" + getShortName() + "'" +
                "}";
    }


    public List<SchClockTemplate> getInternalClockcs() {
        return internalClockcs;
    }

    public void setInternalClockcs(List<SchClockTemplate> internalClockcs) {
        this.internalClockcs = internalClockcs;
    }

    public SchGrid internalClockcs(List<SchClockTemplate> internalClockcs) {
        this.internalClockcs = internalClockcs;
        return this;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public SchGrid defaultGrid(boolean isDefault) {
        this.defaultGrid = isDefault;
        return this;
    }

    public SchGrid gridCategory(CorDictionary corDictionary) {
        this.gridCategory = corDictionary;
        return this;
    }
}
