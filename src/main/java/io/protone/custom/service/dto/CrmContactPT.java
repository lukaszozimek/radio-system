package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.web.rest.dto.thin.CoreUserThinDTO;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CrmContactPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class CrmContactPT {
    @JsonProperty("area")
    private CorDictionaryPT area = null;

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("idNumber1")
    private String idNumber1 = null;

    @JsonProperty("idNumber2")
    private String idNumber2 = null;

    @JsonProperty("industry")
    private CorDictionaryPT industry = null;

    @JsonProperty("name")
    private String name = null;


    @JsonProperty("paymentDelay")
    private Integer paymentDelay = null;

    @JsonProperty("range")
    private CorDictionaryPT range = null;

    @JsonProperty("size")
    private CorDictionaryPT size = null;

    @JsonProperty("vatNumber")
    private String vatNumber = null;

    @JsonProperty("shortName")
    private String shortName = null;

    @JsonProperty("addres")
    private CoreAddressPT addres = null;

    @JsonProperty("account")
    private CoreUserThinDTO account = null;

    @JsonProperty("person")
    private TraCustomerPersonPT person;


    @JsonProperty("tasks")
    private List<CrmTaskPT> tasks = new ArrayList<CrmTaskPT>();


    public CrmContactPT area(CorDictionaryPT area) {
        this.area = area;
        return this;
    }


    public CrmContactPT addTasksItem(CrmTaskPT tasksItem) {
        this.tasks.add(tasksItem);
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

    public CrmContactPT id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @ApiModelProperty(value = "")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CrmContactPT idNumber1(String idNumber1) {
        this.idNumber1 = idNumber1;
        return this;
    }

    @ApiModelProperty(value = "")
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public CrmContactPT shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    @ApiModelProperty(value = "")
    public Integer getPaymentDelay() {
        return paymentDelay;
    }

    public void setPaymentDelay(Integer paymentDelay) {
        this.paymentDelay = paymentDelay;
    }

    public CrmContactPT paymentDelay(Integer paymentDelay) {
        this.paymentDelay = paymentDelay;
        return this;
    }

    /**
     * Get idNumber1
     *
     * @return idNumber1
     **/
    @ApiModelProperty(value = "")
    public String getIdNumber1() {
        return idNumber1;
    }

    public void setIdNumber1(String idNumber1) {
        this.idNumber1 = idNumber1;
    }

    public CrmContactPT idNumber2(String idNumber2) {
        this.idNumber2 = idNumber2;
        return this;
    }

    /**
     * Get idNumber2
     *
     * @return idNumber2
     **/
    @ApiModelProperty(value = "")
    public String getIdNumber2() {
        return idNumber2;
    }

    public void setIdNumber2(String idNumber2) {
        this.idNumber2 = idNumber2;
    }

    public CrmContactPT industry(CorDictionaryPT industry) {
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

    public CrmContactPT name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     **/
    @ApiModelProperty(required = true, value = "")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CrmContactPT range(CorDictionaryPT range) {
        this.range = range;
        return this;
    }

    /**
     * Get range
     *
     * @return range
     **/
    @ApiModelProperty(value = "")
    public CorDictionaryPT getRange() {
        return range;
    }

    public void setRange(CorDictionaryPT range) {
        this.range = range;
    }

    public CrmContactPT size(CorDictionaryPT size) {
        this.size = size;
        return this;
    }

    /**
     * Get size
     *
     * @return size
     **/
    @ApiModelProperty(value = "")
    public CorDictionaryPT getSize() {
        return size;
    }

    public void setSize(CorDictionaryPT size) {
        this.size = size;
    }

    public CrmContactPT vatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
        return this;
    }

    /**
     * Get vatNumber
     *
     * @return vatNumber
     **/
    @ApiModelProperty(value = "")
    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public CrmContactPT addres(CoreAddressPT adress) {
        this.addres = adress;
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

    public CrmContactPT account(CoreUserThinDTO account) {
        this.account = account;
        return this;
    }

    /**
     * Get account
     *
     * @return account
     **/
    @ApiModelProperty(value = "")
    public CoreUserThinDTO getAccount() {
        return account;
    }

    public void setAccount(CoreUserThinDTO account) {
        this.account = account;
    }

    public CrmContactPT person(TraCustomerPersonPT person) {
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

    public CrmContactPT tasks(List<CrmTaskPT> tasks) {
        this.tasks = tasks;
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
        CrmContactPT crmContactPT = (CrmContactPT) o;
        return Objects.equals(this.area, crmContactPT.area) &&
            Objects.equals(this.id, crmContactPT.id) &&
            Objects.equals(this.idNumber1, crmContactPT.idNumber1) &&
            Objects.equals(this.idNumber2, crmContactPT.idNumber2) &&
            Objects.equals(this.industry, crmContactPT.industry) &&
            Objects.equals(this.name, crmContactPT.name) &&
            Objects.equals(this.range, crmContactPT.range) &&
            Objects.equals(this.size, crmContactPT.size) &&
            Objects.equals(this.vatNumber, crmContactPT.vatNumber) &&
            Objects.equals(this.addres, crmContactPT.addres) &&
            Objects.equals(this.account, crmContactPT.account) &&
            Objects.equals(this.person, crmContactPT.person) &&
            Objects.equals(this.tasks, crmContactPT.tasks) &&
            Objects.equals(this.shortName, crmContactPT.shortName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(area, id, idNumber1, idNumber2, industry, name, range, size, vatNumber, addres, account, person, tasks, shortName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CrmContactPT {\n");

        sb.append("    area: ").append(toIndentedString(area)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    idNumber1: ").append(toIndentedString(idNumber1)).append("\n");
        sb.append("    idNumber2: ").append(toIndentedString(idNumber2)).append("\n");
        sb.append("    industry: ").append(toIndentedString(industry)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    range: ").append(toIndentedString(range)).append("\n");
        sb.append("    size: ").append(toIndentedString(size)).append("\n");
        sb.append("    vatNumber: ").append(toIndentedString(vatNumber)).append("\n");
        sb.append("    addres: ").append(toIndentedString(addres)).append("\n");
        sb.append("    account: ").append(toIndentedString(account)).append("\n");
        sb.append("    person: ").append(toIndentedString(person)).append("\n");
        sb.append("    tasks: ").append(toIndentedString(tasks)).append("\n");
        sb.append("    shortName: ").append(toIndentedString(shortName)).append("\n");
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

