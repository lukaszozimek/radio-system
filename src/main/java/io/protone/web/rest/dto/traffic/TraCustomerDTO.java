package io.protone.web.rest.dto.traffic;

import io.protone.web.rest.dto.cor.CoreAddressDTO;
import io.protone.web.rest.dto.cor.CorDictionaryDTO;
import io.protone.web.rest.dto.cor.thin.CoreUserThinDTO;
import io.protone.domain.TraDiscount;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

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

    private TraDiscount traDiscount = null;

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
    public TraDiscount getDiscount() {
        return traDiscount;
    }

    public void setDiscount(TraDiscount traDiscount) {
        this.traDiscount = traDiscount;
    }

    public TraCustomerDTO discount(TraDiscount traDiscount) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraCustomerDTO traCustomerDTO = (TraCustomerDTO) o;
        return Objects.equals(this.id, traCustomerDTO.id) &&
            Objects.equals(this.shortName, traCustomerDTO.shortName) &&
            Objects.equals(this.area, traCustomerDTO.area) &&
            Objects.equals(this.externalId1, traCustomerDTO.externalId1) &&
            Objects.equals(this.externalId2, traCustomerDTO.externalId2) &&
            Objects.equals(this.industry, traCustomerDTO.industry) &&
            Objects.equals(this.name, traCustomerDTO.name) &&
            Objects.equals(this.paymentDate, traCustomerDTO.paymentDate) &&
            Objects.equals(this.range, traCustomerDTO.range) &&
            Objects.equals(this.size, traCustomerDTO.size) &&
            Objects.equals(this.vatNumber, traCustomerDTO.vatNumber) &&
            Objects.equals(this.addres, traCustomerDTO.addres) &&
            Objects.equals(this.account, traCustomerDTO.account) &&
            Objects.equals(this.person, traCustomerDTO.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shortName, area, externalId1, externalId2, industry, name, paymentDate, range, size, vatNumber, addres, account, person);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TraCustomerDTO {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    shortName: ").append(toIndentedString(shortName)).append("\n");
        sb.append("    area: ").append(toIndentedString(area)).append("\n");
        sb.append("    externalId1: ").append(toIndentedString(externalId1)).append("\n");
        sb.append("    externalId2: ").append(toIndentedString(externalId2)).append("\n");
        sb.append("    industry: ").append(toIndentedString(industry)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    paymentDate: ").append(toIndentedString(paymentDate)).append("\n");
        sb.append("    range: ").append(toIndentedString(range)).append("\n");
        sb.append("    size: ").append(toIndentedString(size)).append("\n");
        sb.append("    vatNumber: ").append(toIndentedString(vatNumber)).append("\n");
        sb.append("    addres: ").append(toIndentedString(addres)).append("\n");
        sb.append("    account: ").append(toIndentedString(account)).append("\n");
        sb.append("    person: ").append(toIndentedString(person)).append("\n");
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

