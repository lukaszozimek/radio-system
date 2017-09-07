package io.protone.scheduler.domain;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.domain.enumeration.LogColumnTypEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Emission.
 */
@Entity
@Table(name = "sch_log_column")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchLogColumn extends SchBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @Enumerated(EnumType.STRING)
    @Column(name = "column_type")
    private LogColumnTypEnum columnTypEnum;

    @Column(name = "length")
    private Integer length;

    @Column(name = "column_sequence")
    private Integer columnSequence;

    @ManyToOne
    @PodamExclude
    private SchLogConfiguration schLogConfiguration;

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

    public SchLogConfiguration getSchLogConfiguration() {
        return schLogConfiguration;
    }

    public LogColumnTypEnum getName() {
        return columnTypEnum;
    }

    public void setName(LogColumnTypEnum name) {
        this.columnTypEnum = name;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getColumnSequence() {
        return columnSequence;
    }

    public void setColumnSequence(Integer columnSequence) {
        this.columnSequence = columnSequence;
    }

    public void setSchLogConfiguration(SchLogConfiguration schLogConfiguration) {
        this.schLogConfiguration = schLogConfiguration;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public SchLogColumn network(CorNetwork network) {
        this.network = network;
        return this;
    }

    public void setNetwork(CorNetwork network) {
        this.network = network;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public SchLogColumn channel(CorChannel channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(CorChannel channel) {
        this.channel = channel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchLogColumn emission = (SchLogColumn) o;
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
                ", name='" + columnTypEnum + '\'' +
                ", length=" + length +
                ", columnSequence=" + columnSequence +
                ", network=" + network +
                ", channel=" + channel +
                '}';
    }


}
