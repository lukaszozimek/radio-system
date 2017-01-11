package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CRMOpportunity.
 */
@Entity
@Table(name = "crm_opportunity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CRMOpportunity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_try")
    private LocalDate lastTry;

    @Column(name = "close_date")
    private LocalDate closeDate;

    @Column(name = "probability")
    private Integer probability;

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

    public CRMOpportunity name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getLastTry() {
        return lastTry;
    }

    public CRMOpportunity lastTry(LocalDate lastTry) {
        this.lastTry = lastTry;
        return this;
    }

    public void setLastTry(LocalDate lastTry) {
        this.lastTry = lastTry;
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public CRMOpportunity closeDate(LocalDate closeDate) {
        this.closeDate = closeDate;
        return this;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    public Integer getProbability() {
        return probability;
    }

    public CRMOpportunity probability(Integer probability) {
        this.probability = probability;
        return this;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public CRMOpportunity network(CORNetwork cORNetwork) {
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
        CRMOpportunity cRMOpportunity = (CRMOpportunity) o;
        if (cRMOpportunity.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cRMOpportunity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CRMOpportunity{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", lastTry='" + lastTry + "'" +
            ", closeDate='" + closeDate + "'" +
            ", probability='" + probability + "'" +
            '}';
    }
}
