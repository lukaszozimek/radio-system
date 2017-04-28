package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.service.dto.thin.CoreUserThinDTO;
import io.protone.domain.TraDiscount;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * TraCustomerPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class TraCustomerPT {
    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("shortName")
    private String shortName = null;

    @JsonProperty("area")
    private CorDictionaryPT area = null;

    @JsonProperty("idNumber1")
    private String idNumber1 = null;

    @JsonProperty("idNumber2")
    private String idNumber2 = null;

    @JsonProperty("paymentDelay")
    private Integer paymentDelay = null;

    @JsonProperty("industry")
    private CorDictionaryPT industry = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("paymentDate")
    private Integer paymentDate = null;

    @JsonProperty("range")
    private CorDictionaryPT range = null;

    @JsonProperty("size")
    private CorDictionaryPT size = null;

    @JsonProperty("vatNumber")
    private String vatNumber = null;

    @JsonProperty("addres")
    private CoreAddressPT addres = null;

    @JsonProperty("account")
    private CoreUserThinDTO account = null;

    @JsonProperty("person")
    private TraCustomerPersonPT person = null;

    @JsonProperty("discount")
    private TraDiscount traDiscount = null;

    public TraCustomerPT id(Long id) {
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

    public TraCustomerPT shortName(String shortName) {
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

    public TraCustomerPT area(CorDictionaryPT area) {
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

    public TraCustomerPT discount(TraDiscount traDiscount) {
        this.traDiscount = traDiscount;
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

    public TraCustomerPT idNumber1(String idNumber1) {
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

    public TraCustomerPT idNumber2(String idNumber2) {
        this.idNumber2 = idNumber2;
        return this;
    }

    @ApiModelProperty(value = "")
    public Integer getPaymentDelay() {
        return paymentDelay;
    }

    public void setPaymentDelay(Integer paymentDelay) {
        this.paymentDelay = paymentDelay;
    }

    public TraCustomerPT paymentDelay(Integer paymentDelay) {
        this.paymentDelay = paymentDelay;
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

    public TraCustomerPT industry(CorDictionaryPT industry) {
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

    public TraCustomerPT name(String name) {
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


    public TraCustomerPT paymentDate(Integer paymentDate) {
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

    public TraCustomerPT range(CorDictionaryPT range) {
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

    public TraCustomerPT size(CorDictionaryPT size) {
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

    public TraCustomerPT vatNumber(String vatNumber) {
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

    public TraCustomerPT addres(CoreAddressPT adress) {
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

    public TraCustomerPT account(CoreUserThinDTO account) {
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

    public TraCustomerPT persons(TraCustomerPersonPT person) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraCustomerPT traCustomerPT = (TraCustomerPT) o;
        return Objects.equals(this.id, traCustomerPT.id) &&
            Objects.equals(this.shortName, traCustomerPT.shortName) &&
            Objects.equals(this.area, traCustomerPT.area) &&
            Objects.equals(this.idNumber1, traCustomerPT.idNumber1) &&
            Objects.equals(this.idNumber2, traCustomerPT.idNumber2) &&
            Objects.equals(this.industry, traCustomerPT.industry) &&
            Objects.equals(this.name, traCustomerPT.name) &&
            Objects.equals(this.paymentDate, traCustomerPT.paymentDate) &&
            Objects.equals(this.range, traCustomerPT.range) &&
            Objects.equals(this.size, traCustomerPT.size) &&
            Objects.equals(this.vatNumber, traCustomerPT.vatNumber) &&
            Objects.equals(this.addres, traCustomerPT.addres) &&
            Objects.equals(this.account, traCustomerPT.account) &&
            Objects.equals(this.person, traCustomerPT.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shortName, area, idNumber1, idNumber2, industry, name, paymentDate, range, size, vatNumber, addres, account, person);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TraCustomerPT {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    shortName: ").append(toIndentedString(shortName)).append("\n");
        sb.append("    area: ").append(toIndentedString(area)).append("\n");
        sb.append("    idNumber1: ").append(toIndentedString(idNumber1)).append("\n");
        sb.append("    idNumber2: ").append(toIndentedString(idNumber2)).append("\n");
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

