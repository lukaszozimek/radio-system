package io.protone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @PodamExclude
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "shortname")
    private String shortname;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    @PodamExclude
    private CorPerson person;

    @OneToOne
    @JoinColumn(unique = true)
    @PodamExclude
    private CorAddress addres;

    @ManyToOne
    @PodamExclude
    private CorDictionary leadStatus;

    @ManyToOne
    @PodamExclude
    private CorDictionary leadSource;

    @ManyToOne
    @PodamExclude
    private CorUser keeper;

    @ManyToOne
    @PodamExclude
    private CorDictionary industry;

    @ManyToOne
    @PodamExclude
    private CorDictionary area;

    @ManyToOne
    @PodamExclude
    private CorNetwork network;

    @OneToMany(mappedBy = "lead")
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

    public CorDictionary getLeadStatus() {
        return leadStatus;
    }

    public CrmLead leadStatus(CorDictionary corDictionary) {
        this.leadStatus = corDictionary;
        return this;
    }

    public void setLeadStatus(CorDictionary corDictionary) {
        this.leadStatus = corDictionary;
    }

    public CorDictionary getLeadSource() {
        return leadSource;
    }

    public CrmLead leadSource(CorDictionary corDictionary) {
        this.leadSource = corDictionary;
        return this;
    }

    public void setLeadSource(CorDictionary corDictionary) {
        this.leadSource = corDictionary;
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

    public CorDictionary getIndustry() {
        return industry;
    }

    public CrmLead industry(CorDictionary corDictionary) {
        this.industry = corDictionary;
        return this;
    }

    public void setIndustry(CorDictionary corDictionary) {
        this.industry = corDictionary;
    }

    public CorDictionary getArea() {
        return area;
    }

    public CrmLead area(CorDictionary corDictionary) {
        this.area = corDictionary;
        return this;
    }

    public void setArea(CorDictionary corDictionary) {
        this.area = corDictionary;
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
        this.tasks.add(crmTask);
        crmTask.setLead(this);
        return this;
    }

    public CrmLead removeTasks(CrmTask crmTask) {
        this.tasks.remove(crmTask);
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
