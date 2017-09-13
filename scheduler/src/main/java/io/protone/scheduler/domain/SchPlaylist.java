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
 * A Playlist.
 */
@Entity
@Table(name = "sch_playlist", uniqueConstraints = @UniqueConstraint(columnNames = {"date", "channel_id", "network_id"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchPlaylist extends SchBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @Column(name = "date")
    private LocalDate date;

    @PodamExclude
    @OneToMany(mappedBy = "playlist")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchEmission> emissions = new HashSet<>();

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

    public SchPlaylist date(LocalDate date) {
        this.date = date;
        return this;
    }

    public Set<SchEmission> getEmissions() {
        return emissions;
    }

    public void setEmissions(Set<SchEmission> emissions) {
        this.emissions = emissions;
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

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork network) {
        this.network = network;
    }

    public SchPlaylist network(CorNetwork network) {
        this.network = network;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel channel) {
        this.channel = channel;
    }

    public SchPlaylist channel(CorChannel channel) {
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
