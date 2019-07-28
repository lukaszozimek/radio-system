package io.protone.traffic.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.core.api.dto.CoreContactDTO;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * TraCustomerPersonDTO
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class TraCustomerPersonDTO {
  @JsonProperty("description")
  private String description = null;

  @JsonProperty("firstName")
  private String firstName = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("lastName")
  private String lastName = null;

  @JsonProperty("contacts")
  private List<CoreContactDTO> contacts = new ArrayList<CoreContactDTO>();

  public TraCustomerPersonDTO description(String description) {
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

  public TraCustomerPersonDTO firstName(String firstName) {
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

  public TraCustomerPersonDTO id(Long id) {
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

  public TraCustomerPersonDTO lastName(String lastName) {
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

  public TraCustomerPersonDTO contacts(List<CoreContactDTO> contacts) {
    this.contacts = contacts;
    return this;
  }

  public TraCustomerPersonDTO addContactsItem(CoreContactDTO contactsItem) {
    this.contacts.add(contactsItem);
    return this;
  }

   /**
   * Get contacts
   * @return contacts
  **/
  @ApiModelProperty(value = "")
  public List<CoreContactDTO> getContacts() {
    return contacts;
  }

  public void setContacts(List<CoreContactDTO> contacts) {
    this.contacts = contacts;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TraCustomerPersonDTO traCustomerPersonDTO = (TraCustomerPersonDTO) o;
    return Objects.equals(this.description, traCustomerPersonDTO.description) &&
        Objects.equals(this.firstName, traCustomerPersonDTO.firstName) &&
        Objects.equals(this.id, traCustomerPersonDTO.id) &&
        Objects.equals(this.lastName, traCustomerPersonDTO.lastName) &&
        Objects.equals(this.contacts, traCustomerPersonDTO.contacts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, firstName, id, lastName, contacts);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TraCustomerPersonDTO {\n");

    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    contacts: ").append(toIndentedString(contacts)).append("\n");
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

