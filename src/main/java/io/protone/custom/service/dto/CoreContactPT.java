package io.protone.custom.service.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.protone.domain.enumeration.CORContactTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * CoreContactPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class CoreContactPT   {
  @JsonProperty("contact")
  private String contact = null;



  @JsonProperty("contactType")
  private CORContactTypeEnum contactType = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("networkId")
  private Long networkId = null;

  public CoreContactPT contact(String contact) {
    this.contact = contact;
    return this;
  }

   /**
   * Get contact
   * @return contact
  **/
  @ApiModelProperty(required = true, value = "")
  public String getContact() {
    return contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public CoreContactPT contactType(CORContactTypeEnum contactType) {
    this.contactType = contactType;
    return this;
  }

   /**
   * Get contactType
   * @return contactType
  **/
  @ApiModelProperty(required = true, value = "")
  public CORContactTypeEnum getContactType() {
    return contactType;
  }

  public void setContactType(CORContactTypeEnum contactType) {
    this.contactType = contactType;
  }

  public CoreContactPT id(Long id) {
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

  public CoreContactPT networkId(Long networkId) {
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
    CoreContactPT coreContactPT = (CoreContactPT) o;
    return Objects.equals(this.contact, coreContactPT.contact) &&
        Objects.equals(this.contactType, coreContactPT.contactType) &&
        Objects.equals(this.id, coreContactPT.id) &&
        Objects.equals(this.networkId, coreContactPT.networkId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(contact, contactType, id, networkId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CoreContactPT {\n");

    sb.append("    contact: ").append(toIndentedString(contact)).append("\n");
    sb.append("    contactType: ").append(toIndentedString(contactType)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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

