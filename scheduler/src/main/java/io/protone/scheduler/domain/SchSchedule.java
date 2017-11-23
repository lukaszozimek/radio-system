package io.protone.scheduler.domain;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import static io.protone.scheduler.domain.SchDiscriminators.SCHEDULE;

/**
 * A Schedule.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DiscriminatorValue(SCHEDULE)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class SchSchedule extends SchBlock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "date")
    private LocalDate date;


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

    public SchSchedule network(CorNetwork network) {
        this.setNetwork(network);
        return this;
    }

    public SchSchedule channel(CorChannel channel) {
        this.setChannel(channel);
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
