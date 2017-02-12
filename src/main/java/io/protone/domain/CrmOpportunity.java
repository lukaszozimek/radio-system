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
 * A CrmOpportunity.
 */
@Entity
@Table(name = "crm_opportunity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CrmOpportunity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
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
    private CrmStage stage;

    @ManyToOne
    private CorUser keeper;

    @ManyToOne
    private CrmContact contact;

    @ManyToOne
    private CrmAccount account;

    @ManyToOne
    private CrmLead lead;

    @ManyToOne
    private CorNetwork network;

    @OneToMany(mappedBy = "opportunity")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CrmTask> tasks = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CrmOpportunity name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getLastTry() {
        return lastTry;
    }

    public CrmOpportunity lastTry(LocalDate lastTry) {
        this.lastTry = lastTry;
        return this;
    }

    public void setLastTry(LocalDate lastTry) {
        this.lastTry = lastTry;
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public CrmOpportunity closeDate(LocalDate closeDate) {
        this.closeDate = closeDate;
        return this;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    public Integer getProbability() {
        return probability;
    }

    public CrmOpportunity probability(Integer probability) {
        this.probability = probability;
        return this;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }

    public CrmStage getStage() {
        return stage;
    }

    public CrmOpportunity stage(CrmStage crmStage) {
        this.stage = crmStage;
        return this;
    }

    public void setStage(CrmStage crmStage) {
        this.stage = crmStage;
    }

    public CorUser getKeeper() {
        return keeper;
    }

    public CrmOpportunity keeper(CorUser corUser) {
        this.keeper = corUser;
        return this;
    }

    public void setKeeper(CorUser corUser) {
        this.keeper = corUser;
    }

    public CrmContact getContact() {
        return contact;
    }

    public CrmOpportunity contact(CrmContact crmContact) {
        this.contact = crmContact;
        return this;
    }

    public void setContact(CrmContact crmContact) {
        this.contact = crmContact;
    }

    public CrmAccount getAccount() {
        return account;
    }

    public CrmOpportunity account(CrmAccount crmAccount) {
        this.account = crmAccount;
        return this;
    }

    public void setAccount(CrmAccount crmAccount) {
        this.account = crmAccount;
    }

    public CrmLead getLead() {
        return lead;
    }

    public CrmOpportunity lead(CrmLead crmLead) {
        this.lead = crmLead;
        return this;
    }

    public void setLead(CrmLead crmLead) {
        this.lead = crmLead;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public CrmOpportunity network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public Set<CrmTask> getTasks() {
        return tasks;
    }

    public CrmOpportunity tasks(Set<CrmTask> crmTasks) {
        this.tasks = crmTasks;
        return this;
    }

    public CrmOpportunity addTasks(CrmTask crmTask) {
        this.tasks.add(crmTask);
        crmTask.setOpportunity(this);
        return this;
    }

    public CrmOpportunity removeTasks(CrmTask crmTask) {
        this.tasks.remove(crmTask);
        crmTask.setOpportunity(null);
        return this;
    }

    public void setTasks(Set<CrmTask> crmTasks) {
        this.tasks = crmTasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CrmOpportunity crmOpportunity = (CrmOpportunity) o;
        if (crmOpportunity.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, crmOpportunity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CrmOpportunity{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", lastTry='" + lastTry + "'" +
            ", closeDate='" + closeDate + "'" +
            ", probability='" + probability + "'" +
            '}';
    }
}
