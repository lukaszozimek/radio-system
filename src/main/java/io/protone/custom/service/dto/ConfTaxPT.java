package io.protone.custom.service.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * ConfTaxPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class ConfTaxPT   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("networkId")
  private Long networkId = null;

  @JsonProperty("value")
  private Long value = null;

  @JsonProperty("active")
  private Boolean active = null;

  public ConfTaxPT id(Long id) {
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

  public ConfTaxPT networkId(Long networkId) {
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

  public ConfTaxPT value(Long value) {
    this.value = value;
    return this;
  }

   /**
   * Get value
   * @return value
  **/
  @ApiModelProperty(required = true, value = "")
  public Long getValue() {
    return value;
  }

  public void setValue(Long value) {
    this.value = value;
  }

  public ConfTaxPT active(Boolean active) {
    this.active = active;
    return this;
  }

   /**
   * Get active
   * @return active
  **/
  @ApiModelProperty(value = "")
  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConfTaxPT confTaxPT = (ConfTaxPT) o;
    return Objects.equals(this.id, confTaxPT.id) &&
        Objects.equals(this.networkId, confTaxPT.networkId) &&
        Objects.equals(this.value, confTaxPT.value) &&
        Objects.equals(this.active, confTaxPT.active);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, networkId, value, active);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConfTaxPT {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    networkId: ").append(toIndentedString(networkId)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    active: ").append(toIndentedString(active)).append("\n");
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

