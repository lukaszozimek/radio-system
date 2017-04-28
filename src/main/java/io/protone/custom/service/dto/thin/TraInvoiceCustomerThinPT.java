package io.protone.custom.service.dto.thin;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.custom.service.dto.CorDictionaryPT;
import io.protone.custom.service.dto.CoreAddressPT;
import io.protone.custom.service.dto.TraCustomerPT;
import io.protone.custom.service.dto.TraCustomerPersonPT;
import io.protone.domain.TraDiscount;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * TraCustomerPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class TraInvoiceCustomerThinPT {
    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("shortName")
    private String shortName = null;

    @JsonProperty("idNumber1")
    private String idNumber1 = null;

    @JsonProperty("idNumber2")
    private String idNumber2 = null;

    @JsonProperty("paymentDelay")
    private Integer paymentDelay = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("paymentDate")
    private Integer paymentDate = null;

    @JsonProperty("vatNumber")
    private String vatNumber = null;

    @JsonProperty("addres")
    private CoreAddressPT addres = null;


    public TraInvoiceCustomerThinPT id(Long id) {
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

    public TraInvoiceCustomerThinPT shortName(String shortName) {
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


    public TraInvoiceCustomerThinPT idNumber1(String idNumber1) {
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

    public TraInvoiceCustomerThinPT idNumber2(String idNumber2) {
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

    public TraInvoiceCustomerThinPT paymentDelay(Integer paymentDelay) {
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


    public TraInvoiceCustomerThinPT name(String name) {
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


    public TraInvoiceCustomerThinPT paymentDate(Integer paymentDate) {
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

    public TraInvoiceCustomerThinPT addres(CoreAddressPT adress) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraInvoiceCustomerThinPT traCustomerPT = (TraInvoiceCustomerThinPT) o;
        return Objects.equals(this.id, traCustomerPT.id) &&
            Objects.equals(this.shortName, traCustomerPT.shortName) &&
            Objects.equals(this.idNumber1, traCustomerPT.idNumber1) &&
            Objects.equals(this.idNumber2, traCustomerPT.idNumber2) &&
            Objects.equals(this.name, traCustomerPT.name) &&
            Objects.equals(this.paymentDate, traCustomerPT.paymentDate) &&
            Objects.equals(this.vatNumber, traCustomerPT.vatNumber) &&
            Objects.equals(this.addres, traCustomerPT.addres);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shortName, idNumber1, idNumber2, name, paymentDate, vatNumber, addres);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TraCustomerPT {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    shortName: ").append(toIndentedString(shortName)).append("\n");
        sb.append("    idNumber1: ").append(toIndentedString(idNumber1)).append("\n");
        sb.append("    idNumber2: ").append(toIndentedString(idNumber2)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    paymentDate: ").append(toIndentedString(paymentDate)).append("\n");
        sb.append("    vatNumber: ").append(toIndentedString(vatNumber)).append("\n");
        sb.append("    addres: ").append(toIndentedString(addres)).append("\n");
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
