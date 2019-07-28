package io.protone.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.core.domain.enumeration.CorEntityTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A CorDictionary.
 */
@Entity
@Table(name = "cor_filter")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorFilter extends AbstractAuditingEntity implements Serializable {


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
    @PodamExclude
    private CorUser corUser;

    @ManyToOne
    @JsonIgnore
    @PodamExclude
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

    public CorFilter network(CorNetwork network) {
        this.network = network;
        return this;
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

    public CorUser getCorUser() {
        return corUser;
    }

    public void setCorUser(CorUser corUser) {
        this.corUser = corUser;
    }

    public CorFilter user(CorUser corUser) {
        this.corUser = corUser;
        return this;
    }

    public CorEntityTypeEnum getType() {
        return type;
    }

    public void setType(CorEntityTypeEnum type) {
        this.type = type;
    }


    public CorFilter type(CorEntityTypeEnum type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return "CorFilter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", type=" + type +
                ", corUser=" + corUser +
                '}';
    }


}
