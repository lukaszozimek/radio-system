package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CFGStage.
 */
@Entity
@Table(name = "cfg_stage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CFGStage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(unique = true)
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

    public CFGStage name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public CFGStage network(CORNetwork cORNetwork) {
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
        CFGStage cFGStage = (CFGStage) o;
        if (cFGStage.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cFGStage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CFGStage{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
