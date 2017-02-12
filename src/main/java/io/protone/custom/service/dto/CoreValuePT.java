package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * CoreValuePT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class CoreValuePT   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("propertyKeyId")
  private Long propertyKeyId = null;

  @JsonProperty("value")
  private String value = null;

  public CoreValuePT id(Long id) {
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

  public CoreValuePT propertyKeyId(Long propertyKeyId) {
    this.propertyKeyId = propertyKeyId;
    return this;
  }

   /**
   * Get propertyKeyId
   * @return propertyKeyId
  **/
  @ApiModelProperty(value = "")
  public Long getPropertyKeyId() {
    return propertyKeyId;
  }

  public void setPropertyKeyId(Long propertyKeyId) {
    this.propertyKeyId = propertyKeyId;
  }

  public CoreValuePT value(String value) {
    this.value = value;
    return this;
  }

   /**
   * Get value
   * @return value
  **/
  @ApiModelProperty(required = true, value = "")
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CoreValuePT coreValuePT = (CoreValuePT) o;
    return Objects.equals(this.id, coreValuePT.id) &&
        Objects.equals(this.propertyKeyId, coreValuePT.propertyKeyId) &&
        Objects.equals(this.value, coreValuePT.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, propertyKeyId, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CoreValuePT {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    propertyKeyId: ").append(toIndentedString(propertyKeyId)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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

