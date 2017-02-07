package io.protone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A CrmLead.
 */
@Entity
@Table(name = "crm_lead")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CrmLead implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "shortname")
    private String shortname;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    private CorPerson person;

    @OneToOne
    @JoinColumn(unique = true)
    private CorAddress addres;

    @ManyToOne
    private CrmLeadStatus leadStatus;

    @ManyToOne
    private CrmLeadSource leadSource;

    @ManyToOne
    private CorUser keeper;

    @ManyToOne
    private TraIndustry industry;

    @ManyToOne
    private CorArea area;

    @ManyToOne
    private CorNetwork network;

    @OneToMany(mappedBy = "lead")
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

    public CrmLead name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortname() {
        return shortname;
    }

    public CrmLead shortname(String shortname) {
        this.shortname = shortname;
        return this;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getDescription() {
        return description;
    }

    public CrmLead description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CorPerson getPerson() {
        return person;
    }

    public CrmLead person(CorPerson corPerson) {
        this.person = corPerson;
        return this;
    }

    public void setPerson(CorPerson corPerson) {
        this.person = corPerson;
    }

    public CorAddress getAddres() {
        return addres;
    }

    public CrmLead addres(CorAddress corAddress) {
        this.addres = corAddress;
        return this;
    }

    public void setAddres(CorAddress corAddress) {
        this.addres = corAddress;
    }

    public CrmLeadStatus getLeadStatus() {
        return leadStatus;
    }

    public CrmLead leadStatus(CrmLeadStatus crmLeadStatus) {
        this.leadStatus = crmLeadStatus;
        return this;
    }

    public void setLeadStatus(CrmLeadStatus crmLeadStatus) {
        this.leadStatus = crmLeadStatus;
    }

    public CrmLeadSource getLeadSource() {
        return leadSource;
    }

    public CrmLead leadSource(CrmLeadSource crmLeadSource) {
        this.leadSource = crmLeadSource;
        return this;
    }

    public void setLeadSource(CrmLeadSource crmLeadSource) {
        this.leadSource = crmLeadSource;
    }

    public CorUser getKeeper() {
        return keeper;
    }

    public CrmLead keeper(CorUser corUser) {
        this.keeper = corUser;
        return this;
    }

    public void setKeeper(CorUser corUser) {
        this.keeper = corUser;
    }

    public TraIndustry getIndustry() {
        return industry;
    }

    public CrmLead industry(TraIndustry traIndustry) {
        this.industry = traIndustry;
        return this;
    }

    public void setIndustry(TraIndustry traIndustry) {
        this.industry = traIndustry;
    }

    public CorArea getArea() {
        return area;
    }

    public CrmLead area(CorArea corArea) {
        this.area = corArea;
        return this;
    }

    public void setArea(CorArea corArea) {
        this.area = corArea;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public CrmLead network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public Set<CrmTask> getTasks() {
        return tasks;
    }

    public CrmLead tasks(Set<CrmTask> crmTasks) {
        this.tasks = crmTasks;
        return this;
    }

    public CrmLead addTasks(CrmTask crmTask) {
        tasks.add(crmTask);
        crmTask.setLead(this);
        return this;
    }

    public CrmLead removeTasks(CrmTask crmTask) {
        tasks.remove(crmTask);
        crmTask.setLead(null);
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
        CrmLead crmLead = (CrmLead) o;
        if (crmLead.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, crmLead.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CrmLead{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", shortname='" + shortname + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
