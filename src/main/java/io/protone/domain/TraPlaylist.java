package io.protone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TraPlaylist.
 */
@Entity
@Table(name = "tra_playlist")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TraPlaylist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @PodamExclude
    private Long id;

    @Column(name = "playlist_date")
    private LocalDate playlistDate;

    @ManyToOne
    @PodamExclude
    private CorNetwork network;

    @ManyToOne
    @PodamExclude
    private CorChannel channel;

    @OneToMany(mappedBy = "block")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @PodamExclude
    private Set<TraBlock> playlists = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPlaylistDate() {
        return playlistDate;
    }

    public TraPlaylist playlistDate(LocalDate playlistDate) {
        this.playlistDate = playlistDate;
        return this;
    }

    public void setPlaylistDate(LocalDate playlistDate) {
        this.playlistDate = playlistDate;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public TraPlaylist network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public TraPlaylist channel(CorChannel corChannel) {
        this.channel = corChannel;
        return this;
    }

    public void setChannel(CorChannel corChannel) {
        this.channel = corChannel;
    }

    public Set<TraBlock> getPlaylists() {
        return playlists;
    }

    public TraPlaylist playlists(Set<TraBlock> traBlocks) {
        this.playlists = traBlocks;
        return this;
    }

    public TraPlaylist addPlaylists(TraBlock traBlock) {
        this.playlists.add(traBlock);
        traBlock.setBlock(this);
        return this;
    }

    public TraPlaylist removePlaylists(TraBlock traBlock) {
        this.playlists.remove(traBlock);
        traBlock.setBlock(null);
        return this;
    }

    public void setPlaylists(Set<TraBlock> traBlocks) {
        this.playlists = traBlocks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraPlaylist traPlaylist = (TraPlaylist) o;
        if (traPlaylist.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, traPlaylist.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraPlaylist{" +
            "id=" + id +
            ", playlistDate='" + playlistDate + "'" +
            '}';
    }
}
