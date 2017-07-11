package io.protone.core.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * CorKeyValueDTO
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class CorKeyValueDTO {
  @JsonProperty("key")
  private CorKeyDTO key = null;

  @JsonProperty("value")
  private CorValueDTO value = null;

  public CorKeyValueDTO key(CorKeyDTO key) {
    this.key = key;
    return this;
  }

   /**
   * Get key
   * @return key
  **/
  @ApiModelProperty(value = "")
  public CorKeyDTO getKey() {
    return key;
  }

  public void setKey(CorKeyDTO key) {
    this.key = key;
  }

  public CorKeyValueDTO value(CorValueDTO value) {
    this.value = value;
    return this;
  }

   /**
   * Get value
   * @return value
  **/
  @ApiModelProperty(value = "")
  public CorValueDTO getValue() {
    return value;
  }

  public void setValue(CorValueDTO value) {
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
    CorKeyValueDTO corKeyValueDTO = (CorKeyValueDTO) o;
    return Objects.equals(this.key, corKeyValueDTO.key) &&
        Objects.equals(this.value, corKeyValueDTO.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CorKeyValueDTO {\n");

    sb.append("    key: ").append(toIndentedString(key)).append("\n");
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

