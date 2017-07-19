package io.protone.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.core.domain.enumeration.CorEntityTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A CorDictionary.
 */
@Entity
@Table(name = "cor_filter")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorFilter implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "value")
    private String value;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private CorEntityTypeEnum type;

    @ManyToOne
    @JsonIgnore
    private CorNetwork network;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CorFilter name(String name) {
        this.name = name;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork network) {
        this.network = network;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CorFilter value(String value) {
        this.value = value;
        return this;
    }

    public CorEntityTypeEnum getType() {
        return type;
    }

    public void setType(CorEntityTypeEnum type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CorFilter corFilter = (CorFilter) o;

        if (id != null ? !id.equals(corFilter.id) : corFilter.id != null) return false;
        if (getName() != null ? !getName().equals(corFilter.getName()) : corFilter.getName() != null) return false;
        if (getValue() != null ? !getValue().equals(corFilter.getValue()) : corFilter.getValue() != null) return false;
        if (getType() != null ? !getType().equals(corFilter.getType()) : corFilter.getType() != null) return false;
        return getNetwork() != null ? getNetwork().equals(corFilter.getNetwork()) : corFilter.getNetwork() == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getNetwork() != null ? getNetwork().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CorFilter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
