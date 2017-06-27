package io.protone.application.web.rest.dto.cor;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * CorValueDTO
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class CorValueDTO {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("propertyKeyId")
  private CorKeyDTO propertyKeyId = null;

  @JsonProperty("value")
  private String value = null;

  public CorValueDTO id(Long id) {
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

  public CorValueDTO propertyKeyId(CorKeyDTO propertyKeyId) {
    this.propertyKeyId = propertyKeyId;
    return this;
  }

   /**
   * Get propertyKeyId
   * @return propertyKeyId
  **/
  @ApiModelProperty(value = "")
  public CorKeyDTO getPropertyKeyId() {
    return propertyKeyId;
  }

  public void setPropertyKeyId(CorKeyDTO propertyKeyId) {
    this.propertyKeyId = propertyKeyId;
  }

  public CorValueDTO value(String value) {
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
    CorValueDTO corValueDTO = (CorValueDTO) o;
    return Objects.equals(this.id, corValueDTO.id) &&
        Objects.equals(this.propertyKeyId, corValueDTO.propertyKeyId) &&
        Objects.equals(this.value, corValueDTO.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, propertyKeyId, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CorValueDTO {\n");

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

