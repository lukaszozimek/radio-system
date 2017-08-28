package io.protone.scheduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
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
@Table(name = "sch_grid",uniqueConstraints = @UniqueConstraint(columnNames = {"channel_id", "short_name", "network_id"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchGrid  extends AbstractAuditingEntity implements Serializable {

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

    @ManyToOne
    private CorNetwork network;

    @ManyToOne
    @PodamExclude
    private CorChannel channel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public DayOfWeekEnum getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeekEnum dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public SchGrid dayOfWeek(DayOfWeekEnum dayOfWeek) {
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

    public SchSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(SchSchedule schedule) {
        this.schedule = schedule;
    }

    public SchGrid schedule(SchSchedule schedule) {
        this.schedule = schedule;
        return this;
    }

    public Set<SchClock> getClocks() {
        return clocks;
    }

    public void setClocks(Set<SchClock> clocks) {
        this.clocks = clocks;
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
    public CorNetwork getNetwork() {
        return network;
    }

    public SchGrid network(CorNetwork network) {
        this.network = network;
        return this;
    }

    public void setNetwork(CorNetwork network) {
        this.network = network;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public SchGrid channel(CorChannel channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(CorChannel channel) {
        this.channel = channel;
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
