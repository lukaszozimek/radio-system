package io.protone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CorUser.
 */
@Entity
@Table(name = "cor_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "login",nullable = false)
    private String login;

    @Column(name = "passwordhash",nullable = false)
    private String passwordhash;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "imageurl")
    private String imageurl;

    @Column(name = "activated")
    private Boolean activated;

    @Column(name = "langkey")
    private String langkey;

    @Column(name = "activationkey")
    private String activationkey;

    @Column(name = "resetkey")
    private String resetkey;

    @Column(name = "createdby")
    private String createdby;

    @Column(name = "createddate")
    private LocalDate createddate;

    @Column(name = "resetdate")
    private ZonedDateTime resetdate;

    @Column(name = "lastmodifiedby")
    private String lastmodifiedby;

    @Column(name = "lastmodifieddate")
    private LocalDate lastmodifieddate;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "cor_user_authority",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "name")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<CorAuthority> authorities = new HashSet<>();


    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "cor_user_channel",
        joinColumns = @JoinColumn(name="cor_users_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name="channels_id", referencedColumnName="id"))
    private Set<CorChannel> channels = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "cor_user_network",
        joinColumns = @JoinColumn(name="cor_users_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name="networks_id", referencedColumnName="id"))
    private Set<CorNetwork> networks = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public CorUser login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordhash() {
        return passwordhash;
    }

    public CorUser passwordhash(String passwordhash) {
        this.passwordhash = passwordhash;
        return this;
    }

    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    public String getFirstname() {
        return firstname;
    }

    public CorUser firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public CorUser lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public CorUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageurl() {
        return imageurl;
    }

    public CorUser imageurl(String imageurl) {
        this.imageurl = imageurl;
        return this;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Boolean isActivated() {
        return activated;
    }

    public CorUser activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getLangkey() {
        return langkey;
    }

    public CorUser langkey(String langkey) {
        this.langkey = langkey;
        return this;
    }

    public void setLangkey(String langkey) {
        this.langkey = langkey;
    }

    public String getActivationkey() {
        return activationkey;
    }

    public CorUser activationkey(String activationkey) {
        this.activationkey = activationkey;
        return this;
    }

    public void setActivationkey(String activationkey) {
        this.activationkey = activationkey;
    }

    public String getResetkey() {
        return resetkey;
    }

    public CorUser resetkey(String resetkey) {
        this.resetkey = resetkey;
        return this;
    }

    public void setResetkey(String resetkey) {
        this.resetkey = resetkey;
    }

    public String getCreatedby() {
        return createdby;
    }

    public CorUser createdby(String createdby) {
        this.createdby = createdby;
        return this;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public LocalDate getCreateddate() {
        return createddate;
    }

    public CorUser createddate(LocalDate createddate) {
        this.createddate = createddate;
        return this;
    }

    public void setCreateddate(LocalDate createddate) {
        this.createddate = createddate;
    }

    public ZonedDateTime getResetdate() {
        return resetdate;
    }

    public CorUser resetdate(ZonedDateTime resetdate) {
        this.resetdate = resetdate;
        return this;
    }

    public void setResetdate(ZonedDateTime resetdate) {
        this.resetdate = resetdate;
    }

    public String getLastmodifiedby() {
        return lastmodifiedby;
    }

    public CorUser lastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
        return this;
    }

    public void setLastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
    }

    public LocalDate getLastmodifieddate() {
        return lastmodifieddate;
    }

    public CorUser lastmodifieddate(LocalDate lastmodifieddate) {
        this.lastmodifieddate = lastmodifieddate;
        return this;
    }

    public void setLastmodifieddate(LocalDate lastmodifieddate) {
        this.lastmodifieddate = lastmodifieddate;
    }

    public Set<CorAuthority> getAuthorities() {
        return authorities;
    }

    public CorUser authorities(Set<CorAuthority> corUserAuthorities) {
        this.authorities = corUserAuthorities;
        return this;
    }

    public void setAuthorities(Set<CorAuthority> corUserAuthorities) {
        this.authorities = corUserAuthorities;
    }

    public Set<CorChannel> getChannels() {
        return channels;
    }

    public CorUser channels(Set<CorChannel> corChannels) {
        this.channels = corChannels;
        return this;
    }

    public CorUser addChannel(CorChannel corChannel) {
        this.channels.add(corChannel);
        corChannel.addChannelUsers(this);
        return this;
    }

    public CorUser removeChannel(CorChannel corChannel) {
        this.channels.remove(corChannel);
        corChannel.addChannelUsers(null);
        return this;
    }

    public void setChannels(Set<CorChannel> corChannels) {
        this.channels = corChannels;
    }

    public Set<CorNetwork> getNetworks() {
        return networks;
    }

    public CorUser networks(Set<CorNetwork> corNetworks) {
        this.networks = corNetworks;
        return this;
    }

    public CorUser addNetwork(CorNetwork corNetwork) {
        this.networks.add(corNetwork);
        corNetwork.addNetworkUsers(this);
        return this;
    }

    public CorUser removeNetwork(CorNetwork corNetwork) {
        this.networks.remove(corNetwork);
        corNetwork.addNetworkUsers(null);
        return this;
    }

    public void setNetworks(Set<CorNetwork> corNetworks) {
        this.networks = corNetworks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CorUser corUser = (CorUser) o;
        if (corUser.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, corUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CorUser{" +
            "id=" + id +
            ", login='" + login + "'" +
            ", passwordhash='" + passwordhash + "'" +
            ", firstname='" + firstname + "'" +
            ", lastname='" + lastname + "'" +
            ", email='" + email + "'" +
            ", imageurl='" + imageurl + "'" +
            ", activated='" + activated + "'" +
            ", langkey='" + langkey + "'" +
            ", activationkey='" + activationkey + "'" +
            ", resetkey='" + resetkey + "'" +
            ", createdby='" + createdby + "'" +
            ", createddate='" + createddate + "'" +
            ", resetdate='" + resetdate + "'" +
            ", lastmodifiedby='" + lastmodifiedby + "'" +
            ", lastmodifieddate='" + lastmodifieddate + "'" +
            '}';
    }
}
