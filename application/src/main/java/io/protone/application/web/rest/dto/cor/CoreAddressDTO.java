package io.protone.application.web.rest.dto.cor;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * CoreAddressDTO
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class CoreAddressDTO {
  @JsonProperty("city")
  private String city = null;

  @JsonProperty("country")
  private String country = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("number")
  private String number = null;

  @JsonProperty("postalCode")
  private String postalCode = null;

  @JsonProperty("street")
  private String street = null;

  public CoreAddressDTO city(String city) {
    this.city = city;
    return this;
  }

   /**
   * Get city
   * @return city
  **/
  @ApiModelProperty(required = true, value = "")
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public CoreAddressDTO country(String country) {
    this.country = country;
    return this;
  }

   /**
   * Get country
   * @return country
  **/
  @ApiModelProperty(required = true, value = "")
  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public CoreAddressDTO id(Long id) {
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

  public CoreAddressDTO number(String number) {
    this.number = number;
    return this;
  }

   /**
   * Get number
   * @return number
  **/
  @ApiModelProperty(required = true, value = "")
  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public CoreAddressDTO postalCode(String postalCode) {
    this.postalCode = postalCode;
    return this;
  }

   /**
   * Get postalCode
   * @return postalCode
  **/
  @ApiModelProperty(required = true, value = "")
  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public CoreAddressDTO street(String street) {
    this.street = street;
    return this;
  }

   /**
   * Get street
   * @return street
  **/
  @ApiModelProperty(required = true, value = "")
  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CoreAddressDTO coreAddressDTO = (CoreAddressDTO) o;
    return Objects.equals(this.city, coreAddressDTO.city) &&
        Objects.equals(this.country, coreAddressDTO.country) &&
        Objects.equals(this.id, coreAddressDTO.id) &&
        Objects.equals(this.number, coreAddressDTO.number) &&
        Objects.equals(this.postalCode, coreAddressDTO.postalCode) &&
        Objects.equals(this.street, coreAddressDTO.street);
  }

  @Override
  public int hashCode() {
    return Objects.hash(city, country, id, number, postalCode, street);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CoreAddressDTO {\n");

    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    number: ").append(toIndentedString(number)).append("\n");
    sb.append("    postalCode: ").append(toIndentedString(postalCode)).append("\n");
    sb.append("    street: ").append(toIndentedString(street)).append("\n");
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

