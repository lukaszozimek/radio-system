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
import java.util.Objects;

/**
 * A TraMediaPlanPlaylistDate.
 */
@Entity
@Table(name = "tra_media_plan_playlist_date")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TraMediaPlanPlaylistDate extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "playlist_date", nullable = false)
    private LocalDate playlistDate;

    @ManyToOne
    @PodamExclude
    private TraMediaPlan mediaPlan;

    @ManyToOne
    @PodamExclude
    private CorChannel channel;

    @ManyToOne
    @PodamExclude
    private CorNetwork network;


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

    public TraMediaPlanPlaylistDate playlistDate(LocalDate playlistDate) {
        this.playlistDate = playlistDate;
        return this;
    }

    public TraMediaPlan getMediaPlan() {
        return mediaPlan;
    }

    public void setMediaPlan(TraMediaPlan traMediaPlan) {
        this.mediaPlan = traMediaPlan;
    }

    public TraMediaPlanPlaylistDate mediaPlan(TraMediaPlan traMediaPlan) {
        this.mediaPlan = traMediaPlan;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel corChannel) {
        this.channel = corChannel;
    }

    public TraMediaPlanPlaylistDate channel(CorChannel corChannel) {
        this.channel = corChannel;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public TraMediaPlanPlaylistDate network(CorNetwork corNetwork) {
        this.network = corNetwork;
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

        TraMediaPlanPlaylistDate traMediaPlanPlaylistDate = (TraMediaPlanPlaylistDate) o;
        if (traMediaPlanPlaylistDate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, traMediaPlanPlaylistDate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraMediaPlanPlaylistDate{" +
                "id=" + id +
                ", playlistDate=" + playlistDate +
                ", mediaPlan=" + mediaPlan +
                ", channel=" + channel +
                ", network=" + network +
                '}';
    }

}
