package io.protone.scheduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Schedule.
 */
@Entity
@Table(name = "sch_schedule", uniqueConstraints = @UniqueConstraint(columnNames = {"date", "channel_id", "network_id"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchSchedule extends SchBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @Column(name = "date")
    private LocalDate date;

    @PodamExclude
    @OneToMany(mappedBy = "schSchedule")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchClock> clocks = new HashSet<>();

    @ManyToOne
    @PodamExclude
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public SchSchedule date(LocalDate date) {
        this.date = date;
        return this;
    }

    public Set<SchClock> getClocks() {
        return clocks;
    }

    public void setClocks(Set<SchClock> clocks) {
        this.clocks = clocks;
    }

    public SchSchedule clocks(Set<SchClock> clocks) {
        this.clocks = clocks;
        return this;
    }

    public SchSchedule addClock(SchClock clock) {
        this.clocks.add(clock);
        return this;
    }

    public SchSchedule removeClock(SchClock clock) {
        this.clocks.remove(clock);
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork network) {
        this.network = network;
    }

    public SchSchedule network(CorNetwork network) {
        this.network = network;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel channel) {
        this.channel = channel;
    }

    public SchSchedule channel(CorChannel channel) {
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
        SchSchedule schedule = (SchSchedule) o;
        if (schedule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), schedule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + getId() +
                ", date='" + getDate() + "'" +
                "}";
    }
}
