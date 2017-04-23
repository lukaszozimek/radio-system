package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * TraCustomerPersonPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class TraCustomerPersonPT   {
  @JsonProperty("description")
  private String description = null;

  @JsonProperty("firstName")
  private String firstName = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("lastName")
  private String lastName = null;

  @JsonProperty("contacts")
  private List<CoreContactPT> contacts = new ArrayList<CoreContactPT>();

  public TraCustomerPersonPT description(String description) {
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

  public TraCustomerPersonPT firstName(String firstName) {
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

  public TraCustomerPersonPT id(Long id) {
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

  public TraCustomerPersonPT lastName(String lastName) {
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

  public TraCustomerPersonPT contacts(List<CoreContactPT> contacts) {
    this.contacts = contacts;
    return this;
  }

  public TraCustomerPersonPT addContactsItem(CoreContactPT contactsItem) {
    this.contacts.add(contactsItem);
    return this;
  }

   /**
   * Get contacts
   * @return contacts
  **/
  @ApiModelProperty(value = "")
  public List<CoreContactPT> getContacts() {
    return contacts;
  }

  public void setContacts(List<CoreContactPT> contacts) {
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
    TraCustomerPersonPT traCustomerPersonPT = (TraCustomerPersonPT) o;
    return Objects.equals(this.description, traCustomerPersonPT.description) &&
        Objects.equals(this.firstName, traCustomerPersonPT.firstName) &&
        Objects.equals(this.id, traCustomerPersonPT.id) &&
        Objects.equals(this.lastName, traCustomerPersonPT.lastName) &&
        Objects.equals(this.contacts, traCustomerPersonPT.contacts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, firstName, id, lastName, contacts);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TraCustomerPersonPT {\n");

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

