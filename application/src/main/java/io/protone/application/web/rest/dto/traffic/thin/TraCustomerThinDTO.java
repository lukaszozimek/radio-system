package io.protone.application.web.rest.dto.traffic.thin;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.web.rest.dto.cor.CorDictionaryDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * TraCustomerDTO
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class TraCustomerThinDTO {
    @JsonProperty("id")
    private Long id = null;

    @NotNull
    @JsonProperty("shortName")
    private String shortName = null;

    @JsonProperty("area")
    private CorDictionaryDTO area = null;

    @JsonProperty("industry")
    private CorDictionaryDTO industry = null;

    @NotNull
    @JsonProperty("name")
    private String name = null;

    @JsonProperty("paymentDate")
    private Integer paymentDate = null;

    @JsonProperty("range")
    private CorDictionaryDTO range = null;

    @JsonProperty("size")
    private CorDictionaryDTO size = null;


    public TraCustomerThinDTO id(Long id) {
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

    public TraCustomerThinDTO shortName(String shortName) {
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

    public TraCustomerThinDTO area(CorDictionaryDTO area) {
        this.area = area;
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

    public TraCustomerThinDTO name(String name) {
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


    public TraCustomerThinDTO paymentDate(Integer paymentDate) {
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

    public TraCustomerThinDTO range(CorDictionaryDTO range) {
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

    public TraCustomerThinDTO size(CorDictionaryDTO size) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraCustomerThinDTO traCustomerPT = (TraCustomerThinDTO) o;
        return Objects.equals(this.id, traCustomerPT.id) &&
            Objects.equals(this.shortName, traCustomerPT.shortName) &&
            Objects.equals(this.area, traCustomerPT.area) &&
            Objects.equals(this.industry, traCustomerPT.industry) &&
            Objects.equals(this.name, traCustomerPT.name) &&
            Objects.equals(this.paymentDate, traCustomerPT.paymentDate) &&
            Objects.equals(this.range, traCustomerPT.range) &&
            Objects.equals(this.size, traCustomerPT.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shortName, area,  industry, name, paymentDate, range, size);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TraCustomerDTO {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    shortName: ").append(toIndentedString(shortName)).append("\n");
        sb.append("    area: ").append(toIndentedString(area)).append("\n");
        sb.append("    industry: ").append(toIndentedString(industry)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    paymentDate: ").append(toIndentedString(paymentDate)).append("\n");
        sb.append("    range: ").append(toIndentedString(range)).append("\n");
        sb.append("    size: ").append(toIndentedString(size)).append("\n");
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

