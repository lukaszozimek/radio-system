package io.protone.custom.service.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * LibLabelPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class LibLabelPT   {
  @JsonProperty("description")
  private String description = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("networkId")
  private Long networkId = null;

  public LibLabelPT description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(value = "")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LibLabelPT id(Long id) {
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

  public LibLabelPT name(String name) {
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

  public LibLabelPT networkId(Long networkId) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LibLabelPT libLabelPT = (LibLabelPT) o;
    return Objects.equals(this.description, libLabelPT.description) &&
        Objects.equals(this.id, libLabelPT.id) &&
        Objects.equals(this.name, libLabelPT.name) &&
        Objects.equals(this.networkId, libLabelPT.networkId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, id, name, networkId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LibLabelPT {\n");

    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    networkId: ").append(toIndentedString(networkId)).append("\n");
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

