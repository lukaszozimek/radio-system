package io.protone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
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

    @OneToOne
    @JoinColumn(unique = true)
    private CRMStage stage;

    @OneToOne
    @JoinColumn(unique = true)
    private CORUser keeper;

    @OneToOne
    @JoinColumn(unique = true)
    private CRMContact contact;

    @OneToOne
    @JoinColumn(unique = true)
    private CRMAccount account;

    @OneToOne
    @JoinColumn(unique = true)
    private CRMLead lead;

    @OneToOne
    @JoinColumn(unique = true)
    private CORNetwork network;

    @OneToMany(mappedBy = "cRMOpportunity")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CRMTask> tasks = new HashSet<>();

    @ManyToOne
    private CRMAccount cRMAccount;

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

    public CRMStage getStage() {
        return stage;
    }

    public CRMOpportunity stage(CRMStage cRMStage) {
        this.stage = cRMStage;
        return this;
    }

    public void setStage(CRMStage cRMStage) {
        this.stage = cRMStage;
    }

    public CORUser getKeeper() {
        return keeper;
    }

    public CRMOpportunity keeper(CORUser cORUser) {
        this.keeper = cORUser;
        return this;
    }

    public void setKeeper(CORUser cORUser) {
        this.keeper = cORUser;
    }

    public CRMContact getContact() {
        return contact;
    }

    public CRMOpportunity contact(CRMContact cRMContact) {
        this.contact = cRMContact;
        return this;
    }

    public void setContact(CRMContact cRMContact) {
        this.contact = cRMContact;
    }

    public CRMAccount getAccount() {
        return account;
    }

    public CRMOpportunity account(CRMAccount cRMAccount) {
        this.account = cRMAccount;
        return this;
    }

    public void setAccount(CRMAccount cRMAccount) {
        this.account = cRMAccount;
    }

    public CRMLead getLead() {
        return lead;
    }

    public CRMOpportunity lead(CRMLead cRMLead) {
        this.lead = cRMLead;
        return this;
    }

    public void setLead(CRMLead cRMLead) {
        this.lead = cRMLead;
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

    public Set<CRMTask> getTasks() {
        return tasks;
    }

    public CRMOpportunity tasks(Set<CRMTask> cRMTasks) {
        this.tasks = cRMTasks;
        return this;
    }

    public CRMOpportunity addTasks(CRMTask cRMTask) {
        tasks.add(cRMTask);
        cRMTask.setCRMOpportunity(this);
        return this;
    }

    public CRMOpportunity removeTasks(CRMTask cRMTask) {
        tasks.remove(cRMTask);
        cRMTask.setCRMOpportunity(null);
        return this;
    }

    public void setTasks(Set<CRMTask> cRMTasks) {
        this.tasks = cRMTasks;
    }

    public CRMAccount getCRMAccount() {
        return cRMAccount;
    }

    public CRMOpportunity cRMAccount(CRMAccount cRMAccount) {
        this.cRMAccount = cRMAccount;
        return this;
    }

    public void setCRMAccount(CRMAccount cRMAccount) {
        this.cRMAccount = cRMAccount;
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
            '}';
    }
}
