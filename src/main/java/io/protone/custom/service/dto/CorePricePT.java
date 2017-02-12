package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * CorePricePT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class CorePricePT   {
  @JsonProperty("baseLength")
  private Integer baseLength = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("networkId")
  private Long networkId = null;

  @JsonProperty("price")
  private BigDecimal price = null;

  @JsonProperty("priceAlternative")
  private BigDecimal priceAlternative = null;

  @JsonProperty("validFrom")
  private String validFrom = null;

  @JsonProperty("validTo")
  private String validTo = null;

  public CorePricePT baseLength(Integer baseLength) {
    this.baseLength = baseLength;
    return this;
  }

   /**
   * Get baseLength
   * @return baseLength
  **/
  @ApiModelProperty(value = "")
  public Integer getBaseLength() {
    return baseLength;
  }

  public void setBaseLength(Integer baseLength) {
    this.baseLength = baseLength;
  }

  public CorePricePT id(Long id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public CorePricePT name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(required = true, value = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CorePricePT networkId(Long networkId) {
    this.networkId = networkId;
    return this;
  }

   /**
   * Get networkId
   * @return networkId
  **/
  @ApiModelProperty(value = "")
  public Long getNetworkId() {
    return networkId;
  }

  public void setNetworkId(Long networkId) {
    this.networkId = networkId;
  }

  public CorePricePT price(BigDecimal price) {
    this.price = price;
    return this;
  }

   /**
   * Get price
   * @return price
  **/
  @ApiModelProperty(required = true, value = "")
  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public CorePricePT priceAlternative(BigDecimal priceAlternative) {
    this.priceAlternative = priceAlternative;
    return this;
  }

   /**
   * Get priceAlternative
   * @return priceAlternative
  **/
  @ApiModelProperty(required = true, value = "")
  public BigDecimal getPriceAlternative() {
    return priceAlternative;
  }

  public void setPriceAlternative(BigDecimal priceAlternative) {
    this.priceAlternative = priceAlternative;
  }

  public CorePricePT validFrom(String validFrom) {
    this.validFrom = validFrom;
    return this;
  }

   /**
   * Get validFrom
   * @return validFrom
  **/
  @ApiModelProperty(value = "")
  public String getValidFrom() {
    return validFrom;
  }

  public void setValidFrom(String validFrom) {
    this.validFrom = validFrom;
  }

  public CorePricePT validTo(String validTo) {
    this.validTo = validTo;
    return this;
  }

   /**
   * Get validTo
   * @return validTo
  **/
  @ApiModelProperty(value = "")
  public String getValidTo() {
    return validTo;
  }

  public void setValidTo(String validTo) {
    this.validTo = validTo;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CorePricePT corePricePT = (CorePricePT) o;
    return Objects.equals(this.baseLength, corePricePT.baseLength) &&
        Objects.equals(this.id, corePricePT.id) &&
        Objects.equals(this.name, corePricePT.name) &&
        Objects.equals(this.networkId, corePricePT.networkId) &&
        Objects.equals(this.price, corePricePT.price) &&
        Objects.equals(this.priceAlternative, corePricePT.priceAlternative) &&
        Objects.equals(this.validFrom, corePricePT.validFrom) &&
        Objects.equals(this.validTo, corePricePT.validTo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(baseLength, id, name, networkId, price, priceAlternative, validFrom, validTo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CorePricePT {\n");

    sb.append("    baseLength: ").append(toIndentedString(baseLength)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    networkId: ").append(toIndentedString(networkId)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    priceAlternative: ").append(toIndentedString(priceAlternative)).append("\n");
    sb.append("    validFrom: ").append(toIndentedString(validFrom)).append("\n");
    sb.append("    validTo: ").append(toIndentedString(validTo)).append("\n");
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

