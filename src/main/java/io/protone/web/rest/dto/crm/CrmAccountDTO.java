package io.protone.web.rest.dto.crm;

import io.protone.custom.service.dto.CoreAddressPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.web.rest.dto.traffic.TraCampaignDTO;
import io.protone.custom.service.dto.TraCustomerPersonPT;
import io.protone.web.rest.dto.cor.CorDictionaryDTO;
import io.protone.web.rest.dto.cor.thin.CoreUserThinDTO;
import io.protone.web.rest.dto.traffic.TraOrderDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CrmAccountDTO
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class CrmAccountDTO implements Serializable {

    private Long id = null;

    @NotNull
    private String name = null;

    @NotNull
    private String shortName = null;

    private CorDictionaryDTO area = null;

    private String externalId1 = null;

    private String externalId2 = null;

    private Integer paymentDelay = null;

    private CorDictionaryDTO industry = null;


    private CorDictionaryDTO range = null;

    private CorDictionaryDTO size = null;

    private String vatNumber = null;

    private CoreAddressPT addres = null;

    private CoreUserThinDTO account = null;

    private TraCustomerPersonPT person = null;

    private List<TraOrderDTO> orders = new ArrayList<TraOrderDTO>();

    private List<TraCampaignDTO> campains = new ArrayList<TraCampaignDTO>();

    private List<CrmTaskPT> tasks = new ArrayList<CrmTaskPT>();

    public CrmAccountDTO id(Long id) {
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

    public CrmAccountDTO shortName(String shortName) {
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

    public CrmAccountDTO area(CorDictionaryDTO area) {
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

    public CrmAccountDTO idNumber1(String idNumber1) {
        this.externalId1 = idNumber1;
        return this;
    }

    /**
     * Get externalId1
     *
     * @return externalId1
     **/
    @ApiModelProperty(value = "")
    public String getExternalId1() {
        return externalId1;
    }

    public void setExternalId1(String externalId1) {
        this.externalId1 = externalId1;
    }

    public CrmAccountDTO idNumber2(String idNumber2) {
        this.externalId2 = idNumber2;
        return this;
    }

    /**
     * Get externalId2
     *
     * @return externalId2
     **/
    @ApiModelProperty(value = "")
    public String getExternalId2() {
        return externalId2;
    }

    public void setExternalId2(String externalId2) {
        this.externalId2 = externalId2;
    }

    public CrmAccountDTO industry(CorDictionaryDTO industry) {
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

    public CrmAccountDTO name(String name) {
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


    public CrmAccountDTO range(CorDictionaryDTO range) {
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

    public CrmAccountDTO size(CorDictionaryDTO size) {
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

    public CrmAccountDTO vatNumber(String vatNumber) {
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

    public CrmAccountDTO addres(CoreAddressPT addres) {
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

    public CrmAccountDTO account(CoreUserThinDTO account) {
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

    public CrmAccountDTO person(TraCustomerPersonPT person) {
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

    public CrmAccountDTO orders(List<TraOrderDTO> orders) {
        this.orders = orders;
        return this;
    }

    public CrmAccountDTO addOrdersItem(TraOrderDTO ordersItem) {
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

    public CrmAccountDTO paymentDelay(Integer paymentDelay) {
        this.paymentDelay = paymentDelay;
        return this;
    }

    /**
     * Get orders
     *
     * @return orders
     **/
    @ApiModelProperty(value = "")
    public List<TraOrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<TraOrderDTO> orders) {
        this.orders = orders;
    }

    public CrmAccountDTO campains(List<TraCampaignDTO> campains) {
        this.campains = campains;
        return this;
    }

    public CrmAccountDTO addCampainsItem(TraCampaignDTO campainsItem) {
        this.campains.add(campainsItem);
        return this;
    }

    /**
     * Get campains
     *
     * @return campains
     **/
    @ApiModelProperty(value = "")
    public List<TraCampaignDTO> getCampains() {
        return campains;
    }

    public void setCampains(List<TraCampaignDTO> campains) {
        this.campains = campains;
    }

    public CrmAccountDTO tasks(List<CrmTaskPT> tasks) {
        this.tasks = tasks;
        return this;
    }

    public CrmAccountDTO addTasksItem(CrmTaskPT tasksItem) {
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
        CrmAccountDTO crmAccountDTO = (CrmAccountDTO) o;
        return Objects.equals(this.id, crmAccountDTO.id) &&
            Objects.equals(this.shortName, crmAccountDTO.shortName) &&
            Objects.equals(this.area, crmAccountDTO.area) &&
            Objects.equals(this.externalId1, crmAccountDTO.externalId1) &&
            Objects.equals(this.externalId2, crmAccountDTO.externalId2) &&
            Objects.equals(this.industry, crmAccountDTO.industry) &&
            Objects.equals(this.name, crmAccountDTO.name) &&
            Objects.equals(this.range, crmAccountDTO.range) &&
            Objects.equals(this.size, crmAccountDTO.size) &&
            Objects.equals(this.vatNumber, crmAccountDTO.vatNumber) &&
            Objects.equals(this.addres, crmAccountDTO.addres) &&
            Objects.equals(this.account, crmAccountDTO.account) &&
            Objects.equals(this.person, crmAccountDTO.person) &&
            Objects.equals(this.orders, crmAccountDTO.orders) &&
            Objects.equals(this.campains, crmAccountDTO.campains) &&
            Objects.equals(this.tasks, crmAccountDTO.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shortName, area, externalId1, externalId2, industry, name, range, size, vatNumber, addres, account, person, orders, campains, tasks);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CrmAccountDTO {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    shortName: ").append(toIndentedString(shortName)).append("\n");
        sb.append("    area: ").append(toIndentedString(area)).append("\n");
        sb.append("    externalId1: ").append(toIndentedString(externalId1)).append("\n");
        sb.append("    externalId2: ").append(toIndentedString(externalId2)).append("\n");
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

