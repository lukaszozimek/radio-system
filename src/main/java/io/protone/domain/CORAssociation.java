package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CORAssociation.
 */
@Entity
@Table(name = "cor_association")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CORAssociation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "source_class", nullable = false)
    private String sourceClass;

    @NotNull
    @Column(name = "source_id", nullable = false)
    private Long sourceId;

    @NotNull
    @Column(name = "target_class", nullable = false)
    private String targetClass;

    @NotNull
    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @ManyToOne
    private CORNetwork network;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CORAssociation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourceClass() {
        return sourceClass;
    }

    public CORAssociation sourceClass(String sourceClass) {
        this.sourceClass = sourceClass;
        return this;
    }

    public void setSourceClass(String sourceClass) {
        this.sourceClass = sourceClass;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public CORAssociation sourceId(Long sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getTargetClass() {
        return targetClass;
    }

    public CORAssociation targetClass(String targetClass) {
        this.targetClass = targetClass;
        return this;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

    public Long getTargetId() {
        return targetId;
    }

    public CORAssociation targetId(Long targetId) {
        this.targetId = targetId;
        return this;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public CORAssociation network(CORNetwork cORNetwork) {
        this.network = cORNetwork;
        return this;
    }

    public void setNetwork(CORNetwork cORNetwork) {
        this.network = cORNetwork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CORAssociation cORAssociation = (CORAssociation) o;
        if (cORAssociation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cORAssociation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CORAssociation{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", sourceClass='" + sourceClass + "'" +
            ", sourceId='" + sourceId + "'" +
            ", targetClass='" + targetClass + "'" +
            ", targetId='" + targetId + "'" +
            '}';
    }
}
