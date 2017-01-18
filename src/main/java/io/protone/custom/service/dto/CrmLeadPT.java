package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CrmLeadPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class CrmLeadPT {
    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("shortname")
    private String shortname = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("source")
    private ConfLeadSourcePT source = null;

    @JsonProperty("status")
    private ConfLeadStatusPT status = null;

    @JsonProperty("adress")
    private CoreAddressPT adress = null;

    @JsonProperty("area")
    private CoreAreaPT area = null;

    @JsonProperty("industry")
    private ConfIndustryPT industry = null;

    @JsonProperty("person")
    private ConfPersonPT person = null;

    @JsonProperty("contact")
    private List<CoreContactPT> contact = new ArrayList<CoreContactPT>();

    @JsonProperty("tasks")
    private List<CrmTaskPT> tasks = new ArrayList<CrmTaskPT>();

    public CrmLeadPT id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @ApiModelProperty(required = true, value = "")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CrmLeadPT name(String name) {
        this.name = name;
        return this;
    }

    public String getShortname() {
        return this.shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }
    public CrmLeadPT shortName(String shortname) {
        this.shortname = shortname;
        return this;
    }
    /**
     * Get name
     *
     * @return name
     **/
    @ApiModelProperty(value = "")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CrmLeadPT description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get description
     *
     * @return description
     **/
    @ApiModelProperty(value = "")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CrmLeadPT source(ConfLeadSourcePT source) {
        this.source = source;
        return this;
    }

    /**
     * Get source
     *
     * @return source
     **/
    @ApiModelProperty(value = "")
    public ConfLeadSourcePT getSource() {
        return source;
    }

    public void setSource(ConfLeadSourcePT source) {
        this.source = source;
    }

    public CrmLeadPT status(ConfLeadStatusPT status) {
        this.status = status;
        return this;
    }

    /**
     * Get status
     *
     * @return status
     **/
    @ApiModelProperty(value = "")
    public ConfLeadStatusPT getStatus() {
        return status;
    }

    public void setStatus(ConfLeadStatusPT status) {
        this.status = status;
    }

    public CrmLeadPT adress(CoreAddressPT adress) {
        this.adress = adress;
        return this;
    }

    /**
     * Get adress
     *
     * @return adress
     **/
    @ApiModelProperty(value = "")
    public CoreAddressPT getAdress() {
        return adress;
    }

    public void setAdress(CoreAddressPT adress) {
        this.adress = adress;
    }

    public CrmLeadPT area(CoreAreaPT area) {
        this.area = area;
        return this;
    }

    /**
     * Get area
     *
     * @return area
     **/
    @ApiModelProperty(value = "")
    public CoreAreaPT getArea() {
        return area;
    }

    public void setArea(CoreAreaPT area) {
        this.area = area;
    }

    public CrmLeadPT industry(ConfIndustryPT industry) {
        this.industry = industry;
        return this;
    }

    /**
     * Get industry
     *
     * @return industry
     **/
    @ApiModelProperty(value = "")
    public ConfIndustryPT getIndustry() {
        return industry;
    }

    public void setIndustry(ConfIndustryPT industry) {
        this.industry = industry;
    }

    public CrmLeadPT person(ConfPersonPT person) {
        this.person = person;
        return this;
    }

    /**
     * Get person
     *
     * @return person
     **/
    @ApiModelProperty(value = "")
    public ConfPersonPT getPerson() {
        return person;
    }

    public void setPerson(ConfPersonPT person) {
        this.person = person;
    }

    public CrmLeadPT contact(List<CoreContactPT> contact) {
        this.contact = contact;
        return this;
    }

    public CrmLeadPT addContactItem(CoreContactPT contactItem) {
        this.contact.add(contactItem);
        return this;
    }

    /**
     * Get contact
     *
     * @return contact
     **/
    @ApiModelProperty(value = "")
    public List<CoreContactPT> getContact() {
        return contact;
    }

    public void setContact(List<CoreContactPT> contact) {
        this.contact = contact;
    }

    public CrmLeadPT tasks(List<CrmTaskPT> tasks) {
        this.tasks = tasks;
        return this;
    }

    public CrmLeadPT addTasksItem(CrmTaskPT tasksItem) {
        this.tasks.add(tasksItem);
        return this;
    }

    /**
     * Get tasks
     *
     * @return tasks
     **/
    @ApiModelProperty(value = "")
    public List<CrmTaskPT> getTasks() {
        return tasks;
    }

    public void setTasks(List<CrmTaskPT> tasks) {
        this.tasks = tasks;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CrmLeadPT crmLeadPT = (CrmLeadPT) o;
        return Objects.equals(this.id, crmLeadPT.id) &&
            Objects.equals(this.name, crmLeadPT.name) &&
            Objects.equals(this.description, crmLeadPT.description) &&
            Objects.equals(this.source, crmLeadPT.source) &&
            Objects.equals(this.status, crmLeadPT.status) &&
            Objects.equals(this.adress, crmLeadPT.adress) &&
            Objects.equals(this.area, crmLeadPT.area) &&
            Objects.equals(this.industry, crmLeadPT.industry) &&
            Objects.equals(this.person, crmLeadPT.person) &&
            Objects.equals(this.contact, crmLeadPT.contact) &&
            Objects.equals(this.tasks, crmLeadPT.tasks)&&
            Objects.equals(this.shortname,crmLeadPT.shortname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, source, status, adress, area, industry, person, contact, tasks,shortname);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CrmLeadPT {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    source: ").append(toIndentedString(source)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
        sb.append("    adress: ").append(toIndentedString(adress)).append("\n");
        sb.append("    area: ").append(toIndentedString(area)).append("\n");
        sb.append("    industry: ").append(toIndentedString(industry)).append("\n");
        sb.append("    person: ").append(toIndentedString(person)).append("\n");
        sb.append("    contact: ").append(toIndentedString(contact)).append("\n");
        sb.append("    tasks: ").append(toIndentedString(tasks)).append("\n");
        sb.append("    shortname: ").append(toIndentedString(shortname)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

