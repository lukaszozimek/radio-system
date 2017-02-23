package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * CoreManagedUserPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class CoreManagedUserPT {
    @JsonProperty("activated")
    private Boolean activated = null;

    @JsonProperty("createdBy")
    private String createdBy = null;

    @JsonProperty("createdDate")
    private ZonedDateTime createdDate = null;

    @JsonProperty("email")
    private String email = null;

    @JsonProperty("firstName")
    private String firstName = null;

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("langKey")
    private String langKey = null;

    @JsonProperty("lastModifiedBy")
    private String lastModifiedBy = null;

    @JsonProperty("lastModifiedDate")
    private String lastModifiedDate = null;

    @JsonProperty("lastName")
    private String lastName = null;

    @JsonProperty("login")
    private String login = null;

    @JsonProperty("password")
    private String password = null;

    public CoreManagedUserPT activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    /**
     * Get activated
     *
     * @return activated
     **/
    @ApiModelProperty(value = "")
    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }


    public CoreManagedUserPT createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    /**
     * Get createdBy
     *
     * @return createdBy
     **/
    @ApiModelProperty(value = "")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public CoreManagedUserPT createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    /**
     * Get createdDate
     *
     * @return createdDate
     **/
    @ApiModelProperty(value = "")
    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public CoreManagedUserPT email(String email) {
        this.email = email;
        return this;
    }

    /**
     * Get email
     *
     * @return email
     **/
    @ApiModelProperty(value = "")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CoreManagedUserPT firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Get firstName
     *
     * @return firstName
     **/
    @ApiModelProperty(value = "")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public CoreManagedUserPT id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @ApiModelProperty(value = "")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CoreManagedUserPT langKey(String langKey) {
        this.langKey = langKey;
        return this;
    }

    /**
     * Get langKey
     *
     * @return langKey
     **/
    @ApiModelProperty(value = "")
    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public CoreManagedUserPT lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    /**
     * Get lastModifiedBy
     *
     * @return lastModifiedBy
     **/
    @ApiModelProperty(value = "")
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public CoreManagedUserPT lastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    /**
     * Get lastModifiedDate
     *
     * @return lastModifiedDate
     **/
    @ApiModelProperty(value = "")
    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public CoreManagedUserPT lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Get lastName
     *
     * @return lastName
     **/
    @ApiModelProperty(value = "")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public CoreManagedUserPT login(String login) {
        this.login = login;
        return this;
    }

    /**
     * Get login
     *
     * @return login
     **/
    @ApiModelProperty(value = "")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public CoreManagedUserPT password(String password) {
        this.password = password;
        return this;
    }

    /**
     * Get password
     *
     * @return password
     **/
    @ApiModelProperty(value = "")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CoreManagedUserPT coreManagedUserPT = (CoreManagedUserPT) o;
        return Objects.equals(this.activated, coreManagedUserPT.activated) &&
            Objects.equals(this.createdBy, coreManagedUserPT.createdBy) &&
            Objects.equals(this.createdDate, coreManagedUserPT.createdDate) &&
            Objects.equals(this.email, coreManagedUserPT.email) &&
            Objects.equals(this.firstName, coreManagedUserPT.firstName) &&
            Objects.equals(this.id, coreManagedUserPT.id) &&
            Objects.equals(this.langKey, coreManagedUserPT.langKey) &&
            Objects.equals(this.lastModifiedBy, coreManagedUserPT.lastModifiedBy) &&
            Objects.equals(this.lastModifiedDate, coreManagedUserPT.lastModifiedDate) &&
            Objects.equals(this.lastName, coreManagedUserPT.lastName) &&
            Objects.equals(this.login, coreManagedUserPT.login) &&
            Objects.equals(this.password, coreManagedUserPT.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activated, createdBy, createdDate, email, firstName, id, langKey, lastModifiedBy, lastModifiedDate, lastName, login, password);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CoreManagedUserPT {\n");

        sb.append("    activated: ").append(toIndentedString(activated)).append("\n");
        sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
        sb.append("    createdDate: ").append(toIndentedString(createdDate)).append("\n");
        sb.append("    email: ").append(toIndentedString(email)).append("\n");
        sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    langKey: ").append(toIndentedString(langKey)).append("\n");
        sb.append("    lastModifiedBy: ").append(toIndentedString(lastModifiedBy)).append("\n");
        sb.append("    lastModifiedDate: ").append(toIndentedString(lastModifiedDate)).append("\n");
        sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
        sb.append("    login: ").append(toIndentedString(login)).append("\n");
        sb.append("    password: ").append(toIndentedString(password)).append("\n");
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

