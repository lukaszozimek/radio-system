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
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Grid.
 */
@Entity
@Table(name = "sch_grid", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"channel_id", "short_name", "network_id"})
})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchGrid extends SchBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")
    private CorDayOfWeekEnum dayOfWeek;

    @Column(name = "name")
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "default")
    private Boolean defaultGrid = null;

    @ManyToOne
    @PodamExclude
    private CorDictionary gridCategory;

    @PodamExclude
    @OneToMany(mappedBy = "schGrid")
    private Set<SchGridClockConfiguration> clocks = new HashSet<>();

    @ManyToOne
    @PodamExclude
    private CorNetwork network;

    @ManyToOne
    @PodamExclude
    private CorChannel channel;

    @Transient
    private Set<SchClockConfiguration> internalClockcs;

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

    public Set<SchGridClockConfiguration> getClocks() {
        return clocks;
    }

    public void setClocks(Set<SchGridClockConfiguration> clocks) {
        this.clocks = clocks;
    }

    public SchGrid clocks(Set<SchGridClockConfiguration> clocks) {
        this.clocks = clocks;
        return this;
    }

    public SchGrid addClock(SchGridClockConfiguration clock) {
        clock.grid(this);
        this.clocks.add(clock);
        return this;
    }

    public SchGrid removeClock(SchGridClockConfiguration clock) {
        clock.grid(null);
        this.clocks.remove(clock);
        return this;
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


    public Set<SchClockConfiguration> getInternalClockcs() {
        return internalClockcs;
    }

    public void setInternalClockcs(Set<SchClockConfiguration> internalClockcs) {
        this.internalClockcs = internalClockcs;
    }

    public SchGrid internalClockcs(Set<SchClockConfiguration> internalClockcs) {
        this.internalClockcs = internalClockcs;
        return this;
    }
}
