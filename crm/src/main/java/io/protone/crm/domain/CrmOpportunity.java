package io.protone.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorUser;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A CrmOpportunity.
 */
@Entity
@Table(name = "crm_opportunity", uniqueConstraints =
@UniqueConstraint(columnNames = {"short_name", "network_id"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CrmOpportunity extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "short_name", unique = true, nullable = false)
    private String shortName;

    @Column(name = "last_try")
    private LocalDate lastTry;

    @Column(name = "close_date")
    private LocalDate closeDate;

    @Column(name = "probability")
    private Integer probability;

    @ManyToOne
    @PodamExclude
    private CorDictionary stage;

    @ManyToOne
    @PodamExclude
    private CorUser keeper;

    @ManyToOne
    @PodamExclude
    private CrmContact contact;

    @ManyToOne
    @PodamExclude
    private CrmAccount account;

    @ManyToOne
    @PodamExclude
    private CrmLead lead;

    @ManyToOne
    @PodamExclude
    private CorNetwork network;

    @OneToMany(mappedBy = "opportunity",fetch = FetchType.EAGER)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @PodamExclude
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

    public void setName(String name) {
        this.name = name;
    }

    public CrmOpportunity name(String name) {
        this.name = name;
        return this;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public CrmOpportunity shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public LocalDate getLastTry() {
        return lastTry;
    }

    public void setLastTry(LocalDate lastTry) {
        this.lastTry = lastTry;
    }

    public CrmOpportunity lastTry(LocalDate lastTry) {
        this.lastTry = lastTry;
        return this;
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    public CrmOpportunity closeDate(LocalDate closeDate) {
        this.closeDate = closeDate;
        return this;
    }

    public Integer getProbability() {
        return probability;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }

    public CrmOpportunity probability(Integer probability) {
        this.probability = probability;
        return this;
    }

    public CorDictionary getStage() {
        return stage;
    }

    public void setStage(CorDictionary corDictionary) {
        this.stage = corDictionary;
    }

    public CrmOpportunity stage(CorDictionary corDictionary) {
        this.stage = corDictionary;
        return this;
    }

    public CorUser getKeeper() {
        return keeper;
    }

    public void setKeeper(CorUser corUser) {
        this.keeper = corUser;
    }

    public CrmOpportunity keeper(CorUser corUser) {
        this.keeper = corUser;
        return this;
    }

    public CrmContact getContact() {
        return contact;
    }

    public void setContact(CrmContact crmContact) {
        this.contact = crmContact;
    }

    public CrmOpportunity contact(CrmContact crmContact) {
        this.contact = crmContact;
        return this;
    }

    public CrmAccount getAccount() {
        return account;
    }

    public void setAccount(CrmAccount crmAccount) {
        this.account = crmAccount;
    }

    public CrmOpportunity account(CrmAccount crmAccount) {
        this.account = crmAccount;
        return this;
    }

    public CrmLead getLead() {
        return lead;
    }

    public void setLead(CrmLead crmLead) {
        this.lead = crmLead;
    }

    public CrmOpportunity lead(CrmLead crmLead) {
        this.lead = crmLead;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public CrmOpportunity network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public Set<CrmTask> getTasks() {
        return tasks;
    }

    public void setTasks(Set<CrmTask> crmTasks) {
        this.tasks = crmTasks;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
            ", shortName='" + shortName + "'" +
            ", lastTry='" + lastTry + "'" +
            ", closeDate='" + closeDate + "'" +
            ", probability='" + probability + "'" +
            '}';
    }


}
