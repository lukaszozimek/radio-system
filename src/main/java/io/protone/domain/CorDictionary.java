package io.protone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CorDictionary.
 */
@Entity
@Table(name = "cor_dictionary")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorDictionary  extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;


    @Column(name = "seq_number")
    private Long seqNumber;

    @Column(name = "dictionary_type_name", nullable = false)
    private String corDictionaryType;

    @Column(name = "module_name", nullable = false)
    private String corModule;

    @ManyToOne
    private CorNetwork network;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CorDictionary name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public CorDictionary description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCorDictionaryType() {
        return corDictionaryType;
    }

    public CorDictionary dictionaryType(String dictionaryType) {
        this.corDictionaryType = dictionaryType;
        return this;
    }

    public void setCorDictionaryType(String corDictionaryType) {
        this.corDictionaryType = corDictionaryType;
    }

    public Long getSeqNumber() {
        return seqNumber;
    }

    public CorDictionary seqNumber(Long seqNumber) {
        this.seqNumber = seqNumber;
        return this;
    }

    public void setSeqNumber(Long seqNumber) {
        this.seqNumber = seqNumber;
    }

    public String getCorModule() {
        return corModule;
    }

    public CorDictionary corModule(String module) {
        this.corModule = module;
        return this;
    }

    public void setCorModule(String corModule) {
        this.corModule = corModule;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public CorDictionary network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CorDictionary corDictionary = (CorDictionary) o;
        if (corDictionary.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, corDictionary.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CorDictionary{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", corDictionaryType='" + corDictionaryType + "'" +
            ", seqNumber='" + seqNumber + "'" +
            ", corModule='" + corModule + "'" +
            '}';
    }
}
