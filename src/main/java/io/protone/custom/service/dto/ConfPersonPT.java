package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * ConfPersonPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class ConfPersonPT   {
  @JsonProperty("description")
  private String description = null;

  @JsonProperty("firstName")
  private String firstName = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("lastName")
  private String lastName = null;

  @JsonProperty("networkId")
  private Long networkId = null;

  public ConfPersonPT description(String description) {
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

  public ConfPersonPT firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

   /**
   * Get firstName
   * @return firstName
  **/
  @ApiModelProperty(required = true, value = "")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public ConfPersonPT id(Long id) {
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

  public ConfPersonPT lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

   /**
   * Get lastName
   * @return lastName
  **/
  @ApiModelProperty(required = true, value = "")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public ConfPersonPT networkId(Long networkId) {
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
    ConfPersonPT confPersonPT = (ConfPersonPT) o;
    return Objects.equals(this.description, confPersonPT.description) &&
        Objects.equals(this.firstName, confPersonPT.firstName) &&
        Objects.equals(this.id, confPersonPT.id) &&
        Objects.equals(this.lastName, confPersonPT.lastName) &&
        Objects.equals(this.networkId, confPersonPT.networkId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, firstName, id, lastName, networkId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConfPersonPT {\n");

    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
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

