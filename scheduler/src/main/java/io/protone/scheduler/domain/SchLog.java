package io.protone.scheduler.domain;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.LibFileItem;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Emission.
 */
@Entity
@Table(name = "sch_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchLog extends SchBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @PodamExclude
    private SchLogConfiguration schLogConfiguration;

    @ManyToOne
    @PodamExclude
    private LibFileItem libFileItem;

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

    public SchLog date(LocalDate date) {
        this.date = date;
        return this;
    }

    public SchLogConfiguration getSchLogConfiguration() {
        return schLogConfiguration;
    }

    public void setSchLogConfiguration(SchLogConfiguration schLogConfiguration) {
        this.schLogConfiguration = schLogConfiguration;
    }

    public LibFileItem getLibFileItem() {
        return libFileItem;
    }

    public void setLibFileItem(LibFileItem libFileItem) {
        this.libFileItem = libFileItem;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork network) {
        this.network = network;
    }

    public SchLog network(CorNetwork network) {
        this.network = network;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel channel) {
        this.channel = channel;
    }

    public SchLog channel(CorChannel channel) {
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
        SchLog emission = (SchLog) o;
        if (emission.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emission.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SchLogColumn{" +
                "id=" + id +
                ", network=" + network +
                ", channel=" + channel +
                '}';
    }


    public SchLog fileItem(LibFileItem libFileItem) {
        this.libFileItem = libFileItem;
        return this;
    }

    public SchLog schLogConfiguration(SchLogConfiguration schLogConfiguration) {
        this.schLogConfiguration = schLogConfiguration;
        return this;
    }

}
