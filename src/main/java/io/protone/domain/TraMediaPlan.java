package io.protone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A TraMediaPlan.
 */
@Entity
@Table(name = "tra_media_plan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TraMediaPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne
    @PodamExclude
    @JoinColumn(unique = true)
    private LibMediaItem mediaItem;

    @ManyToOne
    @PodamExclude
    private CorChannel channel;

    @ManyToOne
    @PodamExclude
    private CrmAccount account;

    @ManyToOne
    @PodamExclude
    private CorNetwork network;

    @OneToMany(mappedBy = "mediaPlan")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @PodamExclude
    private Set<TraMediaPlanPlaylist> playlists = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TraMediaPlan name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LibMediaItem getMediaItem() {
        return mediaItem;
    }

    public TraMediaPlan mediaItem(LibMediaItem libMediaItem) {
        this.mediaItem = libMediaItem;
        return this;
    }

    public void setMediaItem(LibMediaItem libMediaItem) {
        this.mediaItem = libMediaItem;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public TraMediaPlan channel(CorChannel corChannel) {
        this.channel = corChannel;
        return this;
    }

    public void setChannel(CorChannel corChannel) {
        this.channel = corChannel;
    }

    public CrmAccount getAccount() {
        return account;
    }

    public TraMediaPlan account(CrmAccount crmAccount) {
        this.account = crmAccount;
        return this;
    }

    public void setAccount(CrmAccount crmAccount) {
        this.account = crmAccount;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public TraMediaPlan network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public Set<TraMediaPlanPlaylist> getPlaylists() {
        return playlists;
    }

    public TraMediaPlan playlists(Set<TraMediaPlanPlaylist> traMediaPlanPlaylists) {
        this.playlists = traMediaPlanPlaylists;
        return this;
    }

    public TraMediaPlan addPlaylists(TraMediaPlanPlaylist traMediaPlanPlaylist) {
        this.playlists.add(traMediaPlanPlaylist);
        traMediaPlanPlaylist.setMediaPlan(this);
        return this;
    }

    public TraMediaPlan removePlaylists(TraMediaPlanPlaylist traMediaPlanPlaylist) {
        this.playlists.remove(traMediaPlanPlaylist);
        traMediaPlanPlaylist.setMediaPlan(null);
        return this;
    }

    public void setPlaylists(Set<TraMediaPlanPlaylist> traMediaPlanPlaylists) {
        this.playlists = traMediaPlanPlaylists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraMediaPlan traMediaPlan = (TraMediaPlan) o;
        if (traMediaPlan.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, traMediaPlan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraMediaPlan{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
