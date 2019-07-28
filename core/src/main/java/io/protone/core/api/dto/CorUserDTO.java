package io.protone.core.api.dto;


import io.protone.core.domain.CorUser;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

/**
 * CorUserDTO
 */

public class CorUserDTO implements Serializable {

    private Long id = null;

    private Boolean activated = null;

    @NotNull
    private List<String> authorities = new ArrayList<String>();

    private String imageurl = null;

    @NotNull
    private String email = null;

    private String firstName = null;

    private String langKey = null;

    private String lastName = null;

    @NotNull
    private String login = null;

    private CorNetworkDTO network;

    private List<CorChannelDTO> channel = null;

    public CorUserDTO(Long id) {
        this.id = id;
    }

    public CorUserDTO() {
    }

    public CorUserDTO(CorUser corUser) {
        this.id = corUser.getId();
        this.activated = corUser.isActivated();
        this.authorities = corUser.getAuthorities().stream().map(authority -> authority.getName().toString()).collect(toList());
        this.imageurl = corUser.getCorImageItem() != null ? corUser.getCorImageItem().getPublicUrl() : "";
        this.email = corUser.getEmail();
        this.firstName = corUser.getFirstname();
        this.langKey = corUser.getLangkey();
        this.lastName = corUser.getLastname();
        this.login = corUser.getLogin();


    }

    public CorUserDTO(Long id, String login, String firstName, String lastName, String email, boolean activated, String imageUrl, String langKey, String createdBy, ZonedDateTime createdDate, String lastModifiedBy, ZonedDateTime lastModifiedDate, Set<String> authorities) {
        this.id = id;
        this.activated = activated;
        this.authorities = authorities.stream().collect(toList());
        this.imageurl = imageUrl;
        this.email = email;
        this.firstName = firstName;
        this.langKey = langKey;
        this.lastName = lastName;
        this.login = login;

    }


    public CorUserDTO activated(Boolean activated) {
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

    public CorUserDTO authorities(List<String> authorities) {
        this.authorities = authorities;
        return this;
    }

    public CorUserDTO addAuthoritiesItem(String authoritiesItem) {
        this.authorities.add(authoritiesItem);
        return this;
    }

    /**
     * Get authoritiesf
     *
     * @return authoritiesf
     **/
    @ApiModelProperty(value = "")
    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public CorUserDTO email(String email) {
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

    public CorUserDTO firstName(String firstName) {
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

    public CorUserDTO langKey(String langKey) {
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

    public CorUserDTO lastName(String lastName) {
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

    public CorUserDTO login(String login) {
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

    public CorUserDTO imageUrl(String imageurl) {
        this.imageurl = imageurl;
        return this;
    }

    @ApiModelProperty(value = "")
    public CorNetworkDTO getNetwork() {
        return this.network;
    }

    public void setNetwork(CorNetworkDTO networkPt) {
        this.network = networkPt;
    }

    public CorUserDTO network(CorNetworkDTO networkPt) {
        this.network = networkPt;
        return this;
    }

    @ApiModelProperty(value = "")
    public List<CorChannelDTO> getChannel() {
        return this.channel;
    }

    public void setChannel(List<CorChannelDTO> channelId) {
        this.channel = channelId;
    }

    public CorUserDTO channel(List<CorChannelDTO> channelId) {
        this.channel = channelId;
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
        if (!(o instanceof CorUserDTO)) return false;

        CorUserDTO that = (CorUserDTO) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getActivated() != null ? !getActivated().equals(that.getActivated()) : that.getActivated() != null)
            return false;
        if (getAuthorities() != null ? !getAuthorities().equals(that.getAuthorities()) : that.getAuthorities() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(that.getEmail()) : that.getEmail() != null) return false;
        if (getFirstName() != null ? !getFirstName().equals(that.getFirstName()) : that.getFirstName() != null)
            return false;
        if (getLangKey() != null ? !getLangKey().equals(that.getLangKey()) : that.getLangKey() != null) return false;
        if (getLastName() != null ? !getLastName().equals(that.getLastName()) : that.getLastName() != null)
            return false;
        if (getLogin() != null ? !getLogin().equals(that.getLogin()) : that.getLogin() != null) return false;
        return getImageurl() != null ? getImageurl().equals(that.getImageurl()) : that.getImageurl() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getActivated() != null ? getActivated().hashCode() : 0);
        result = 31 * result + (getAuthorities() != null ? getAuthorities().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLangKey() != null ? getLangKey().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getLogin() != null ? getLogin().hashCode() : 0);
        result = 31 * result + (getImageurl() != null ? getImageurl().hashCode() : 0);
        result = 31 * result + (getChannel() != null ? getChannel().hashCode() : 0);
        result = 31 * result + (getNetwork() != null ? getNetwork().hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CorUserDTO{");
        sb.append("id=").append(id);
        sb.append(", activated=").append(activated);
        sb.append(", authorities=").append(authorities);
        sb.append(", email='").append(email).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", langKey='").append(langKey).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", login='").append(login).append('\'');
        sb.append(", imageurl='").append(imageurl).append('\'');
        sb.append(", network='").append(network).append('\'');
        sb.append(", channel='").append(channel).append('\'');
        sb.append('}');
        return sb.toString();

    }
}

