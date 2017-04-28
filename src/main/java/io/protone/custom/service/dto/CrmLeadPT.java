package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.service.dto.thin.CoreUserThinDTO;
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
    private CorDictionaryPT source = null;

    @JsonProperty("status")
    private CorDictionaryPT status = null;

    @JsonProperty("addres")
    private CoreAddressPT addres = null;

    @JsonProperty("area")
    private CorDictionaryPT area = null;

    @JsonProperty("industry")
    private CorDictionaryPT industry = null;

    @JsonProperty("leadOwner")
    private CoreUserThinDTO leadOwner = null;

    @JsonProperty("person")
    private TraCustomerPersonPT person = null;

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

    public CrmLeadPT source(CorDictionaryPT source) {
        this.source = source;
        return this;
    }

    @ApiModelProperty(value = "")
    public CoreUserThinDTO getOwner() {
        return leadOwner;
    }

    public void setOwner(CoreUserThinDTO leadOwner) {
        this.leadOwner = leadOwner;
    }

    public CrmLeadPT owner(CoreUserThinDTO leadOwner) {
        this.leadOwner = leadOwner;
        return this;
    }

    /**
     * Get source
     *
     * @return source
     **/
    @ApiModelProperty(value = "")
    public CorDictionaryPT getSource() {
        return source;
    }

    public void setSource(CorDictionaryPT source) {
        this.source = source;
    }

    public CrmLeadPT status(CorDictionaryPT status) {
        this.status = status;
        return this;
    }

    /**
     * Get status
     *
     * @return status
     **/
    @ApiModelProperty(value = "")
    public CorDictionaryPT getStatus() {
        return status;
    }

    public void setStatus(CorDictionaryPT status) {
        this.status = status;
    }

    public CrmLeadPT addres(CoreAddressPT addres) {
        this.addres = addres;
        return this;
    }

    /**
     * Get addres
     *
     * @return addres
     **/
    @ApiModelProperty(value = "")
    public CoreAddressPT getAddres() {
        return addres;
    }

    public void setAddres(CoreAddressPT addres) {
        this.addres = addres;
    }

    public CrmLeadPT area(CorDictionaryPT area) {
        this.area = area;
        return this;
    }

    /**
     * Get area
     *
     * @return area
     **/
    @ApiModelProperty(value = "")
    public CorDictionaryPT getArea() {
        return area;
    }

    public void setArea(CorDictionaryPT area) {
        this.area = area;
    }

    public CrmLeadPT industry(CorDictionaryPT industry) {
        this.industry = industry;
        return this;
    }

    /**
     * Get industry
     *
     * @return industry
     **/
    @ApiModelProperty(value = "")
    public CorDictionaryPT getIndustry() {
        return industry;
    }

    public void setIndustry(CorDictionaryPT industry) {
        this.industry = industry;
    }

    public CrmLeadPT person(TraCustomerPersonPT person) {
        this.person = person;
        return this;
    }

    /**
     * Get person
     *
     * @return person
     **/
    @ApiModelProperty(value = "")
    public TraCustomerPersonPT getPerson() {
        return person;
    }

    public void setPerson(TraCustomerPersonPT person) {
        this.person = person;
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
            Objects.equals(this.addres, crmLeadPT.addres) &&
            Objects.equals(this.area, crmLeadPT.area) &&
            Objects.equals(this.industry, crmLeadPT.industry) &&
            Objects.equals(this.person, crmLeadPT.person) &&
            Objects.equals(this.tasks, crmLeadPT.tasks) &&
            Objects.equals(this.shortname, crmLeadPT.shortname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, source, status, addres, area, industry, person, tasks, shortname);
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
        sb.append("    addres: ").append(toIndentedString(addres)).append("\n");
        sb.append("    area: ").append(toIndentedString(area)).append("\n");
        sb.append("    industry: ").append(toIndentedString(industry)).append("\n");
        sb.append("    person: ").append(toIndentedString(person)).append("\n");
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

