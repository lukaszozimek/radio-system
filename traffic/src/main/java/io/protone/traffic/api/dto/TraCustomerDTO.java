package io.protone.traffic.api.dto;


import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.core.api.dto.CoreAddressDTO;
import io.protone.core.api.dto.thin.CoreUserThinDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * TraCustomerDTO
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class TraCustomerDTO implements Serializable {

    private Long id = null;

    @NotNull
    private String name = null;

    @NotNull
    private String shortName = null;

    private CorDictionaryDTO area = null;

    private String description = null;

    private String externalId1 = null;

    private String externalId2 = null;

    private Integer paymentDelay = null;

    private CorDictionaryDTO industry = null;


    private Integer paymentDate = null;

    private CorDictionaryDTO range = null;

    private CorDictionaryDTO size = null;

    private String vatNumber = null;

    private CoreAddressDTO addres = null;

    private CoreUserThinDTO account = null;

    private TraCustomerPersonDTO person = null;

    private TraDiscountDTO traDiscount = null;

    private CoreUserThinDTO createdBy;

    private ZonedDateTime createdDate;

    private CoreUserThinDTO lastModifiedBy;

    private ZonedDateTime lastModifiedDate;

    public TraCustomerDTO id(Long id) {
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

    public TraCustomerDTO shortName(String shortName) {
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

    public TraCustomerDTO area(CorDictionaryDTO area) {
        this.area = area;
        return this;
    }

    /**
     * Get shortName
     *
     * @return shortName
     **/
    @ApiModelProperty(value = "")
    public TraDiscountDTO getDiscount() {
        return traDiscount;
    }

    public void setDiscount(TraDiscountDTO traDiscount) {
        this.traDiscount = traDiscount;
    }

    public TraCustomerDTO discount(TraDiscountDTO traDiscount) {
        this.traDiscount = traDiscount;
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

    public TraCustomerDTO idNumber1(String idNumber1) {
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

    public TraCustomerDTO idNumber2(String idNumber2) {
        this.externalId2 = idNumber2;
        return this;
    }

    @ApiModelProperty(value = "")
    public Integer getPaymentDelay() {
        return paymentDelay;
    }

    public void setPaymentDelay(Integer paymentDelay) {
        this.paymentDelay = paymentDelay;
    }

    public TraCustomerDTO paymentDelay(Integer paymentDelay) {
        this.paymentDelay = paymentDelay;
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

    public TraCustomerDTO industry(CorDictionaryDTO industry) {
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

    public TraCustomerDTO name(String name) {
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


    public TraCustomerDTO paymentDate(Integer paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    /**
     * Get paymentDate
     *
     * @return paymentDate
     **/
    @ApiModelProperty(value = "")
    public Integer getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Integer paymentDate) {
        this.paymentDate = paymentDate;
    }

    public TraCustomerDTO range(CorDictionaryDTO range) {
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

    public TraCustomerDTO size(CorDictionaryDTO size) {
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

    public TraCustomerDTO vatNumber(String vatNumber) {
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

    public TraCustomerDTO addres(CoreAddressDTO adress) {
        this.addres = adress;
        return this;
    }

    /**
     * Get addres
     *
     * @return addres
     **/
    @ApiModelProperty(value = "")
    public CoreAddressDTO getAddres() {
        return addres;
    }

    public void setAddres(CoreAddressDTO addres) {
        this.addres = addres;
    }

    public TraCustomerDTO account(CoreUserThinDTO account) {
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

    public TraCustomerDTO persons(TraCustomerPersonDTO person) {
        this.person = person;
        return this;
    }


    /**
     * Get person
     *
     * @return person
     **/
    @ApiModelProperty(value = "")
    public TraCustomerPersonDTO getPerson() {
        return person;
    }

    public void setPerson(TraCustomerPersonDTO person) {
        this.person = person;
    }

    public CoreUserThinDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CoreUserThinDTO createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public CoreUserThinDTO getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(CoreUserThinDTO lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TraDiscountDTO getTraDiscount() {
        return traDiscount;
    }

    public void setTraDiscount(TraDiscountDTO traDiscount) {
        this.traDiscount = traDiscount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraCustomerDTO that = (TraCustomerDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;
        if (area != null ? !area.equals(that.area) : that.area != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (externalId1 != null ? !externalId1.equals(that.externalId1) : that.externalId1 != null) return false;
        if (externalId2 != null ? !externalId2.equals(that.externalId2) : that.externalId2 != null) return false;
        if (paymentDelay != null ? !paymentDelay.equals(that.paymentDelay) : that.paymentDelay != null) return false;
        if (industry != null ? !industry.equals(that.industry) : that.industry != null) return false;
        if (paymentDate != null ? !paymentDate.equals(that.paymentDate) : that.paymentDate != null) return false;
        if (range != null ? !range.equals(that.range) : that.range != null) return false;
        if (size != null ? !size.equals(that.size) : that.size != null) return false;
        if (vatNumber != null ? !vatNumber.equals(that.vatNumber) : that.vatNumber != null) return false;
        if (addres != null ? !addres.equals(that.addres) : that.addres != null) return false;
        if (account != null ? !account.equals(that.account) : that.account != null) return false;
        if (person != null ? !person.equals(that.person) : that.person != null) return false;
        if (traDiscount != null ? !traDiscount.equals(that.traDiscount) : that.traDiscount != null) return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (lastModifiedBy != null ? !lastModifiedBy.equals(that.lastModifiedBy) : that.lastModifiedBy != null)
            return false;
        if (lastModifiedDate != null ? !lastModifiedDate.equals(that.lastModifiedDate) : that.lastModifiedDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (area != null ? area.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (externalId1 != null ? externalId1.hashCode() : 0);
        result = 31 * result + (externalId2 != null ? externalId2.hashCode() : 0);
        result = 31 * result + (paymentDelay != null ? paymentDelay.hashCode() : 0);
        result = 31 * result + (industry != null ? industry.hashCode() : 0);
        result = 31 * result + (paymentDate != null ? paymentDate.hashCode() : 0);
        result = 31 * result + (range != null ? range.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (vatNumber != null ? vatNumber.hashCode() : 0);
        result = 31 * result + (addres != null ? addres.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (person != null ? person.hashCode() : 0);
        result = 31 * result + (traDiscount != null ? traDiscount.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastModifiedBy != null ? lastModifiedBy.hashCode() : 0);
        result = 31 * result + (lastModifiedDate != null ? lastModifiedDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TraCustomerDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", area=" + area +
                ", description='" + description + '\'' +
                ", externalId1='" + externalId1 + '\'' +
                ", externalId2='" + externalId2 + '\'' +
                ", paymentDelay=" + paymentDelay +
                ", industry=" + industry +
                ", paymentDate=" + paymentDate +
                ", range=" + range +
                ", size=" + size +
                ", vatNumber='" + vatNumber + '\'' +
                ", addres=" + addres +
                ", account=" + account +
                ", person=" + person +
                ", traDiscount=" + traDiscount +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                ", lastModifiedBy=" + lastModifiedBy +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}

