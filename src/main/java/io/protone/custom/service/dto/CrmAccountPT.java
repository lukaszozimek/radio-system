package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.web.rest.dto.cor.CorDictionaryDTO;
import io.protone.web.rest.dto.cor.thin.CoreUserThinDTO;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CrmAccountPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class CrmAccountPT {
    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("shortName")
    private String shortName = null;

    @JsonProperty("area")
    private CorDictionaryDTO area = null;

    @JsonProperty("idNumber1")
    private String idNumber1 = null;

    @JsonProperty("idNumber2")
    private String idNumber2 = null;

    @JsonProperty("paymentDelay")
    private Integer paymentDelay = null;

    @JsonProperty("industry")
    private CorDictionaryDTO industry = null;

    @JsonProperty("name")
    private String name = null;


    @JsonProperty("range")
    private CorDictionaryDTO range = null;

    @JsonProperty("size")
    private CorDictionaryDTO size = null;

    @JsonProperty("vatNumber")
    private String vatNumber = null;

    @JsonProperty("addres")
    private CoreAddressPT addres = null;

    @JsonProperty("account")
    private CoreUserThinDTO account = null;

    @JsonProperty("person")
    private TraCustomerPersonPT person = null;

    @JsonProperty("orders")
    private List<TraOrderPT> orders = new ArrayList<TraOrderPT>();

    @JsonProperty("campains")
    private List<TraCampaignPT> campains = new ArrayList<TraCampaignPT>();

    @JsonProperty("tasks")
    private List<CrmTaskPT> tasks = new ArrayList<CrmTaskPT>();

    public CrmAccountPT id(Long id) {
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

    public CrmAccountPT shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    /**
     * Get shortName
     *
     * @return shortName
     **/
    @ApiModelProperty(value = "")
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public CrmAccountPT area(CorDictionaryDTO area) {
        this.area = area;
        return this;
    }

    /**
     * Get area
     *
     * @return area
     **/
    @ApiModelProperty(value = "")
    public CorDictionaryDTO getArea() {
        return area;
    }

    public void setArea(CorDictionaryDTO area) {
        this.area = area;
    }

    public CrmAccountPT idNumber1(String idNumber1) {
        this.idNumber1 = idNumber1;
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

    public CrmAccountPT idNumber2(String idNumber2) {
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

    public CrmAccountPT industry(CorDictionaryDTO industry) {
        this.industry = industry;
        return this;
    }

    /**
     * Get industry
     *
     * @return industry
     **/
    @ApiModelProperty(value = "")
    public CorDictionaryDTO getIndustry() {
        return industry;
    }

    public void setIndustry(CorDictionaryDTO industry) {
        this.industry = industry;
    }

    public CrmAccountPT name(String name) {
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


    public CrmAccountPT range(CorDictionaryDTO range) {
        this.range = range;
        return this;
    }

    /**
     * Get range
     *
     * @return range
     **/
    @ApiModelProperty(value = "")
    public CorDictionaryDTO getRange() {
        return range;
    }

    public void setRange(CorDictionaryDTO range) {
        this.range = range;
    }

    public CrmAccountPT size(CorDictionaryDTO size) {
        this.size = size;
        return this;
    }

    /**
     * Get size
     *
     * @return size
     **/
    @ApiModelProperty(value = "")
    public CorDictionaryDTO getSize() {
        return size;
    }

    public void setSize(CorDictionaryDTO size) {
        this.size = size;
    }

    public CrmAccountPT vatNumber(String vatNumber) {
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

    public CrmAccountPT addres(CoreAddressPT addres) {
        this.addres = addres;
        return this;
    }

    /**
     * Get adress
     *
     * @return adress
     **/
    @ApiModelProperty(value = "")
    public CoreAddressPT getAddres() {
        return addres;
    }

    public void setAddres(CoreAddressPT addres) {
        this.addres = addres;
    }

    public CrmAccountPT account(CoreUserThinDTO account) {
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

    public CrmAccountPT person(TraCustomerPersonPT person) {
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

    public CrmAccountPT orders(List<TraOrderPT> orders) {
        this.orders = orders;
        return this;
    }

    public CrmAccountPT addOrdersItem(TraOrderPT ordersItem) {
        this.orders.add(ordersItem);
        return this;
    }

    @ApiModelProperty(value = "")
    public Integer getPaymentDelay() {
        return paymentDelay;
    }

    public void setPaymentDelay(Integer paymentDelay) {
        this.paymentDelay = paymentDelay;
    }

    public CrmAccountPT paymentDelay(Integer paymentDelay) {
        this.paymentDelay = paymentDelay;
        return this;
    }

    /**
     * Get orders
     *
     * @return orders
     **/
    @ApiModelProperty(value = "")
    public List<TraOrderPT> getOrders() {
        return orders;
    }

    public void setOrders(List<TraOrderPT> orders) {
        this.orders = orders;
    }

    public CrmAccountPT campains(List<TraCampaignPT> campains) {
        this.campains = campains;
        return this;
    }

    public CrmAccountPT addCampainsItem(TraCampaignPT campainsItem) {
        this.campains.add(campainsItem);
        return this;
    }

    /**
     * Get campains
     *
     * @return campains
     **/
    @ApiModelProperty(value = "")
    public List<TraCampaignPT> getCampains() {
        return campains;
    }

    public void setCampains(List<TraCampaignPT> campains) {
        this.campains = campains;
    }

    public CrmAccountPT tasks(List<CrmTaskPT> tasks) {
        this.tasks = tasks;
        return this;
    }

    public CrmAccountPT addTasksItem(CrmTaskPT tasksItem) {
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
        CrmAccountPT crmAccountPT = (CrmAccountPT) o;
        return Objects.equals(this.id, crmAccountPT.id) &&
            Objects.equals(this.shortName, crmAccountPT.shortName) &&
            Objects.equals(this.area, crmAccountPT.area) &&
            Objects.equals(this.idNumber1, crmAccountPT.idNumber1) &&
            Objects.equals(this.idNumber2, crmAccountPT.idNumber2) &&
            Objects.equals(this.industry, crmAccountPT.industry) &&
            Objects.equals(this.name, crmAccountPT.name) &&
            Objects.equals(this.range, crmAccountPT.range) &&
            Objects.equals(this.size, crmAccountPT.size) &&
            Objects.equals(this.vatNumber, crmAccountPT.vatNumber) &&
            Objects.equals(this.addres, crmAccountPT.addres) &&
            Objects.equals(this.account, crmAccountPT.account) &&
            Objects.equals(this.person, crmAccountPT.person) &&
            Objects.equals(this.orders, crmAccountPT.orders) &&
            Objects.equals(this.campains, crmAccountPT.campains) &&
            Objects.equals(this.tasks, crmAccountPT.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shortName, area, idNumber1, idNumber2, industry, name, range, size, vatNumber, addres, account, person, orders, campains, tasks);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CrmAccountPT {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    shortName: ").append(toIndentedString(shortName)).append("\n");
        sb.append("    area: ").append(toIndentedString(area)).append("\n");
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
        sb.append("    orders: ").append(toIndentedString(orders)).append("\n");
        sb.append("    campains: ").append(toIndentedString(campains)).append("\n");
        sb.append("    tasks: ").append(toIndentedString(tasks)).append("\n");
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

