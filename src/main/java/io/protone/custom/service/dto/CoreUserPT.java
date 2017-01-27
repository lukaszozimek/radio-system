package io.protone.custom.service.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * CoreUserPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class CoreUserPT   {
  @JsonProperty("activated")
  private Boolean activated = null;

  @JsonProperty("authoritiesf")
  private List<String> authorities = new ArrayList<String>();

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("firstName")
  private String firstName = null;

  @JsonProperty("langKey")
  private String langKey = null;

  @JsonProperty("lastName")
  private String lastName = null;

  @JsonProperty("login")
  private String login = null;

  public CoreUserPT activated(Boolean activated) {
    this.activated = activated;
    return this;
  }

   /**
   * Get activated
   * @return activated
  **/
  @ApiModelProperty(value = "")
  public Boolean getActivated() {
    return activated;
  }

  public void setActivated(Boolean activated) {
    this.activated = activated;
  }

  public CoreUserPT authorities(List<String> authorities) {
    this.authorities = authorities;
    return this;
  }

  public CoreUserPT addAuthoritiesItem(String authoritiesItem) {
    this.authorities.add(authoritiesItem);
    return this;
  }

   /**
   * Get authoritiesf
   * @return authoritiesf
  **/
  @ApiModelProperty(value = "")
  public List<String> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(List<String> authorities) {
    this.authorities = authorities;
  }

  public CoreUserPT email(String email) {
    this.email = email;
    return this;
  }

   /**
   * Get email
   * @return email
  **/
  @ApiModelProperty(value = "")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public CoreUserPT firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

   /**
   * Get firstName
   * @return firstName
  **/
  @ApiModelProperty(value = "")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public CoreUserPT langKey(String langKey) {
    this.langKey = langKey;
    return this;
  }

   /**
   * Get langKey
   * @return langKey
  **/
  @ApiModelProperty(value = "")
  public String getLangKey() {
    return langKey;
  }

  public void setLangKey(String langKey) {
    this.langKey = langKey;
  }

  public CoreUserPT lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

   /**
   * Get lastName
   * @return lastName
  **/
  @ApiModelProperty(value = "")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public CoreUserPT login(String login) {
    this.login = login;
    return this;
  }

   /**
   * Get login
   * @return login
  **/
  @ApiModelProperty(value = "")
  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CoreUserPT coreUserPT = (CoreUserPT) o;
    return Objects.equals(this.activated, coreUserPT.activated) &&
        Objects.equals(this.authorities, coreUserPT.authorities) &&
        Objects.equals(this.email, coreUserPT.email) &&
        Objects.equals(this.firstName, coreUserPT.firstName) &&
        Objects.equals(this.langKey, coreUserPT.langKey) &&
        Objects.equals(this.lastName, coreUserPT.lastName) &&
        Objects.equals(this.login, coreUserPT.login);
  }

  @Override
  public int hashCode() {
    return Objects.hash(activated, authorities, email, firstName, langKey, lastName, login);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CoreUserPT {\n");

    sb.append("    activated: ").append(toIndentedString(activated)).append("\n");
    sb.append("    authoritiesf: ").append(toIndentedString(authorities)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    langKey: ").append(toIndentedString(langKey)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    login: ").append(toIndentedString(login)).append("\n");
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

