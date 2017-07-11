package io.protone.scheduler.domain;

import io.protone.core.domain.CorChannel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A SchPlaylist.
 */
@Entity
@Table(name = "sch_playlist")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchPlaylist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "dim_year")
    private Integer dimYear;

    @Column(name = "dim_month")
    private Integer dimMonth;

    @Column(name = "dim_day")
    private Integer dimDay;

    @ManyToOne
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

    public SchPlaylist date(LocalDate date) {
        this.date = date;
        return this;
    }

    public Integer getDimYear() {
        return dimYear;
    }

    public void setDimYear(Integer dimYear) {
        this.dimYear = dimYear;
    }

    public SchPlaylist dimYear(Integer dimYear) {
        this.dimYear = dimYear;
        return this;
    }

    public Integer getDimMonth() {
        return dimMonth;
    }

    public void setDimMonth(Integer dimMonth) {
        this.dimMonth = dimMonth;
    }

    public SchPlaylist dimMonth(Integer dimMonth) {
        this.dimMonth = dimMonth;
        return this;
    }

    public Integer getDimDay() {
        return dimDay;
    }

    public void setDimDay(Integer dimDay) {
        this.dimDay = dimDay;
    }

    public SchPlaylist dimDay(Integer dimDay) {
        this.dimDay = dimDay;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel corChannel) {
        this.channel = corChannel;
    }

    public SchPlaylist channel(CorChannel corChannel) {
        this.channel = corChannel;
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
        SchPlaylist schPlaylist = (SchPlaylist) o;
        if (schPlaylist.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, schPlaylist.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SchPlaylist{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", dimYear='" + dimYear + "'" +
            ", dimMonth='" + dimMonth + "'" +
            ", dimDay='" + dimDay + "'" +
            '}';
    }
}
