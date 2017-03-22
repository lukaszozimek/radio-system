package io.protone.domain;

import io.protone.domain.enumeration.CfgLogTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CfgExternalSystemLog.
 */
@Entity
@Table(name = "cfg_external_system_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CfgExternalSystemLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lenght")
    private Integer lenght;

    @Enumerated(EnumType.STRING)
    @Column(name = "log_column")
    private CfgLogTypeEnum logColumn;

    @Column(name = "delimiter")
    private String delimiter;

    @Column(name = "column_sequence")
    private Integer columnSequence;

    @ManyToOne
    private CorNetwork network;

    @ManyToOne
    private CorChannel channel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CfgExternalSystemLog name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLenght() {
        return lenght;
    }

    public CfgExternalSystemLog lenght(Integer lenght) {
        this.lenght = lenght;
        return this;
    }

    public void setLenght(Integer lenght) {
        this.lenght = lenght;
    }

    public CfgLogTypeEnum getLogColumn() {
        return logColumn;
    }

    public CfgExternalSystemLog logColumn(CfgLogTypeEnum logColumn) {
        this.logColumn = logColumn;
        return this;
    }

    public void setLogColumn(CfgLogTypeEnum logColumn) {
        this.logColumn = logColumn;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public CfgExternalSystemLog delimiter(String delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public Integer getColumnSequence() {
        return columnSequence;
    }

    public CfgExternalSystemLog columnSequence(Integer columnSequence) {
        this.columnSequence = columnSequence;
        return this;
    }

    public void setColumnSequence(Integer columnSequence) {
        this.columnSequence = columnSequence;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public CfgExternalSystemLog network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public CfgExternalSystemLog channel(CorChannel corChannel) {
        this.channel = corChannel;
        return this;
    }

    public void setChannel(CorChannel corChannel) {
        this.channel = corChannel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CfgExternalSystemLog cfgExternalSystemLog = (CfgExternalSystemLog) o;
        if (cfgExternalSystemLog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cfgExternalSystemLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CfgExternalSystemLog{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", lenght='" + lenght + "'" +
            ", logColumn='" + logColumn + "'" +
            ", delimiter='" + delimiter + "'" +
            ", columnSequence='" + columnSequence + "'" +
            '}';
    }
}
