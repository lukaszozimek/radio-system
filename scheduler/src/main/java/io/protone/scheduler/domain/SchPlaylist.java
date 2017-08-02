package io.protone.scheduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A Playlist.
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

    @Column(name = "jhi_date")
    private LocalDate date;

    @PodamExclude
    @OneToMany(mappedBy = "playlist")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchEmission> emissions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public SchPlaylist date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<SchEmission> getEmissions() {
        return emissions;
    }

    public SchPlaylist emissions(Set<SchEmission> emissions) {
        this.emissions = emissions;
        return this;
    }

    public SchPlaylist addEmission(SchEmission emission) {
        this.emissions.add(emission);
        emission.setPlaylist(this);
        return this;
    }

    public SchPlaylist removeEmission(SchEmission emission) {
        this.emissions.remove(emission);
        emission.setPlaylist(null);
        return this;
    }

    public void setEmissions(Set<SchEmission> emissions) {
        this.emissions = emissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchPlaylist playlist = (SchPlaylist) o;
        if (playlist.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), playlist.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Playlist{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
