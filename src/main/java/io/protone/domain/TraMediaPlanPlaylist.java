package io.protone.domain;

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
 * A TraMediaPlanPlaylist.
 */
@Entity
@Table(name = "tra_media_plan_playlist")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TraMediaPlanPlaylist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "playlist_date")
    private LocalDate playlistDate;

    @ManyToOne
    private TraMediaPlan mediaPlan;

    @ManyToOne
    private CorChannel channel;

    @ManyToOne
    private CorNetwork network;


    @PodamExclude
    @OneToMany
    @JoinTable(
        name = "tra_media_plan_playlist_tra_block",
        joinColumns = @JoinColumn(name = "tra_media_plan_playlist_id"),
        inverseJoinColumns = @JoinColumn(name = "tra_block_id")
    )
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

    public TraMediaPlanPlaylist playlistDate(LocalDate playlistDate) {
        this.playlistDate = playlistDate;
        return this;
    }

    public void setPlaylistDate(LocalDate playlistDate) {
        this.playlistDate = playlistDate;
    }

    public TraMediaPlan getMediaPlan() {
        return mediaPlan;
    }

    public TraMediaPlanPlaylist mediaPlan(TraMediaPlan traMediaPlan) {
        this.mediaPlan = traMediaPlan;
        return this;
    }

    public void setMediaPlan(TraMediaPlan traMediaPlan) {
        this.mediaPlan = traMediaPlan;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public TraMediaPlanPlaylist channel(CorChannel corChannel) {
        this.channel = corChannel;
        return this;
    }

    public void setChannel(CorChannel corChannel) {
        this.channel = corChannel;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public TraMediaPlanPlaylist network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public Set<TraBlock> getPlaylists() {
        return playlists;
    }

    public TraMediaPlanPlaylist playlists(Set<TraBlock> playlists) {
        this.playlists = playlists;
        return this;
    }

    public void setPlaylists(Set<TraBlock> playlists) {
        this.playlists = playlists;
    }

    public TraMediaPlanPlaylist addPlaylists(TraBlock traBlock) {
        this.playlists.add(traBlock);
        return this;
    }

    public TraMediaPlanPlaylist removePlaylists(TraBlock traBlock) {
        this.playlists.remove(traBlock);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraMediaPlanPlaylist that = (TraMediaPlanPlaylist) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (playlistDate != null ? !playlistDate.equals(that.playlistDate) : that.playlistDate != null) return false;
        if (mediaPlan != null ? !mediaPlan.equals(that.mediaPlan) : that.mediaPlan != null) return false;
        if (channel != null ? !channel.equals(that.channel) : that.channel != null) return false;
        if (network != null ? !network.equals(that.network) : that.network != null) return false;
        return playlists != null ? playlists.equals(that.playlists) : that.playlists == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (playlistDate != null ? playlistDate.hashCode() : 0);
        result = 31 * result + (mediaPlan != null ? mediaPlan.hashCode() : 0);
        result = 31 * result + (channel != null ? channel.hashCode() : 0);
        result = 31 * result + (network != null ? network.hashCode() : 0);
        result = 31 * result + (playlists != null ? playlists.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TraMediaPlanPlaylist{" +
            "id=" + id +
            ", playlistDate=" + playlistDate +
            ", mediaPlan=" + mediaPlan +
            ", channel=" + channel +
            ", network=" + network +
            ", playlists=" + playlists +
            '}';
    }

}
