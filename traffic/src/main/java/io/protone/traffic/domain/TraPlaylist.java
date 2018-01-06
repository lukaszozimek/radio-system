package io.protone.traffic.domain;

import io.protone.core.domain.AbstractAuditingEntity;
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
 * A TraPlaylist.
 */
@Entity
@Table(name = "tra_playlist", uniqueConstraints =
@UniqueConstraint(columnNames = {"playlist_date", "channel_id"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TraPlaylist extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @PodamExclude
    private Long id;

    @Column(name = "playlist_date", nullable = false, unique = true)
    private LocalDate playlistDate;

    @ManyToOne
    @PodamExclude
    private CorChannel channel;

    @PodamExclude
    @OneToMany
    @JoinTable(
        name = "tra_playlist_tra_block",
        joinColumns = @JoinColumn(name = "tra_playlist_id"),
        inverseJoinColumns = @JoinColumn(name = "tra_block_id")
    )
    private Set<TraBlock> playlists = new HashSet<>();

    public TraPlaylist(){}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPlaylistDate() {
        return playlistDate;
    }

    public void setPlaylistDate(LocalDate playlistDate) {
        this.playlistDate = playlistDate;
    }

    public TraPlaylist playlistDate(LocalDate playlistDate) {
        this.playlistDate = playlistDate;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel corChannel) {
        this.channel = corChannel;
    }

    public TraPlaylist channel(CorChannel corChannel) {
        this.channel = corChannel;
        return this;
    }

    public Set<TraBlock> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Set<TraBlock> traBlocks) {
        this.playlists = traBlocks;
    }

    public TraPlaylist playlists(Set<TraBlock> traBlocks) {
        this.playlists = traBlocks;
        return this;
    }

    public TraPlaylist addPlaylists(TraBlock traBlock) {
        this.playlists.add(traBlock);
        return this;
    }

    public TraPlaylist removePlaylists(TraBlock traBlock) {
        this.playlists.remove(traBlock);
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
