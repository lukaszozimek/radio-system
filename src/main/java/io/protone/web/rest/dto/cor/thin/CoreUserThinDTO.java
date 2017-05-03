package io.protone.web.rest.dto.cor.thin;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.domain.CorUser;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Set;

/**
 * CoreUserPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class CoreUserThinDTO {

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("imageurl")
    private String imageurl = null;

    @NotNull
    @JsonProperty("email")
    private String email = null;

    @JsonProperty("firstName")
    private String firstName = null;

    @JsonProperty("langKey")
    private String langKey = null;

    @JsonProperty("lastName")
    private String lastName = null;

    @NotNull
    @JsonProperty("login")
    private String login = null;


    public CoreUserThinDTO(Long id) {
        this.id = id;
    }

    public CoreUserThinDTO() {
    }

    public CoreUserThinDTO(CorUser corUser) {
        this.id = corUser.getId();
        this.imageurl = corUser.getImageurl();
        this.email = corUser.getEmail();
        this.firstName = corUser.getFirstname();
        this.langKey = corUser.getLangkey();
        this.lastName = corUser.getLastname();
        this.login = corUser.getLogin();


    }

    public CoreUserThinDTO(Long id, String login, String firstName, String lastName, String email, boolean activated, String imageUrl, String langKey, String createdBy, ZonedDateTime createdDate, String lastModifiedBy, ZonedDateTime lastModifiedDate, Set<String> authorities) {
        this.id = id;
        this.imageurl = imageUrl;
        this.email = email;
        this.firstName = firstName;
        this.langKey = langKey;
        this.lastName = lastName;
        this.login = login;

    }

    public CoreUserThinDTO email(String email) {
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

    public CoreUserThinDTO firstName(String firstName) {
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

    public CoreUserThinDTO langKey(String langKey) {
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

    public CoreUserThinDTO lastName(String lastName) {
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

    public CoreUserThinDTO login(String login) {
        this.login = login;
        return this;
    }

    @ApiModelProperty(value = "")
    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public CoreUserThinDTO imageUrl(String imageurl) {
        this.imageurl = imageurl;
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CoreUserThinDTO)) return false;

        CoreUserThinDTO that = (CoreUserThinDTO) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getEmail() != null ? !getEmail().equals(that.getEmail()) : that.getEmail() != null) return false;
        if (getFirstName() != null ? !getFirstName().equals(that.getFirstName()) : that.getFirstName() != null)
            return false;
        if (getLangKey() != null ? !getLangKey().equals(that.getLangKey()) : that.getLangKey() != null) return false;
        if (getLastName() != null ? !getLastName().equals(that.getLastName()) : that.getLastName() != null)
            return false;
        if (getLogin() != null ? !getLogin().equals(that.getLogin()) : that.getLogin() != null) return false;
        if (getImageurl() != null ? !getImageurl().equals(that.getImageurl()) : that.getImageurl() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLangKey() != null ? getLangKey().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getLogin() != null ? getLogin().hashCode() : 0);
        result = 31 * result + (getImageurl() != null ? getImageurl().hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CoreUserPT{");
        sb.append("id=").append(id);
        sb.append(", email='").append(email).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", langKey='").append(langKey).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", login='").append(login).append('\'');
        sb.append(", imageurl='").append(imageurl).append('\'');
        sb.append('}');
        return sb.toString();

    }
}

