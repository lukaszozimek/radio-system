package io.protone.scheduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.scheduler.domain.enumeration.DayOfWeekEnum;
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
@Table(name = "sch_grid")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchGrid implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")
    private DayOfWeekEnum dayOfWeek;

    @Column(name = "name")
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @PodamExclude
    @ManyToOne
    private SchSchedule schedule;

    @PodamExclude
    @OneToMany(mappedBy = "grid")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchClock> clocks = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeekEnum getDayOfWeek() {
        return dayOfWeek;
    }

    public SchGrid dayOfWeek(DayOfWeekEnum dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public void setDayOfWeek(DayOfWeekEnum dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getName() {
        return name;
    }

    public SchGrid name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public SchGrid shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public SchSchedule getSchedule() {
        return schedule;
    }

    public SchGrid schedule(SchSchedule schedule) {
        this.schedule = schedule;
        return this;
    }

    public void setSchedule(SchSchedule schedule) {
        this.schedule = schedule;
    }

    public Set<SchClock> getClocks() {
        return clocks;
    }

    public SchGrid clocks(Set<SchClock> clocks) {
        this.clocks = clocks;
        return this;
    }

    public SchGrid addClock(SchClock clock) {
        this.clocks.add(clock);
        return this;
    }

    public SchGrid removeClock(SchClock clock) {
        this.clocks.remove(clock);
        return this;
    }

    public void setClocks(Set<SchClock> clocks) {
        this.clocks = clocks;
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
}