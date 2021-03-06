package io.protone.scheduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Emission.
 */
@Entity
@Table(name = "sch_log_configuration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchLogConfiguration extends SchBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @Column(name = "name")
    private String name;

    @Column(name = "pattern")
    private String pattern;

    @Column(name = "extension")
    private String extension;

    @Column(name = "spearator")
    private String spearator;

    @ManyToOne
    @PodamExclude
    private CorNetwork network;

    @ManyToOne
    @PodamExclude
    private CorChannel channel;


    @OneToMany(mappedBy = "schLogConfiguration", fetch = FetchType.EAGER)
    @JsonIgnore
    @PodamExclude
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchLogColumn> logColumns = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork network) {
        this.network = network;
    }

    public SchLogConfiguration network(CorNetwork network) {
        this.network = network;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchLogConfiguration name(String name) {
        this.name = name;
        return this;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public SchLogConfiguration pattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel channel) {
        this.channel = channel;
    }

    public SchLogConfiguration channel(CorChannel channel) {
        this.channel = channel;
        return this;
    }

    public Set<SchLogColumn> getLogColumns() {
        return logColumns;
    }

    public void setLogColumns(Set<SchLogColumn> logColumns) {
        this.logColumns = logColumns;
    }

    public SchLogConfiguration columns(Set<SchLogColumn> logColumns) {
        this.logColumns = logColumns;
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
        SchLogConfiguration emission = (SchLogConfiguration) o;
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
        return "SchLogConfiguration{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pattern='" + pattern + '\'' +
                ", network=" + network +
                ", channel=" + channel +
                ", logColumns=" + logColumns +
                '}';
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getSpearator() {
        return spearator;
    }

    public void setSpearator(String spearator) {
        this.spearator = spearator;
    }

    public SchLogConfiguration extension(String extension) {
        this.extension = extension;
        return this;
    }
}
