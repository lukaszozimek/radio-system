package io.protone.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A CorUser.
 */
@Entity
@Table(name = "cor_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorUser extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "passwordhash", nullable = false)
    private String passwordhash;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "activated")
    private Boolean activated;

    @Column(name = "langkey")
    private String langkey;

    @Column(name = "activationkey")
    private String activationkey;

    @Column(name = "resetkey")
    private String resetkey;

    @Column(name = "resetdate")
    private ZonedDateTime resetdate;

    @OneToOne
    @JoinColumn(unique = true)
    @PodamExclude
    private CorImageItem corImageItem;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "cor_user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "name")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<CorAuthority> authorities = new HashSet<>();


    @ManyToMany(fetch= FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "cor_user_channel",
            joinColumns = @JoinColumn(name = "cor_users_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "channels_id", referencedColumnName = "id"))
    private Set<CorChannel> channels = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "cor_user_network",
            joinColumns = @JoinColumn(name = "cor_users_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "networks_id", referencedColumnName = "id"))
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

    public void setLogin(String login) {
        this.login = login;
    }

    public CorUser login(String login) {
        this.login = login;
        return this;
    }

    public String getPasswordhash() {
        return passwordhash;
    }

    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    public CorUser passwordhash(String passwordhash) {
        this.passwordhash = passwordhash;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public CorUser firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public CorUser lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CorUser email(String email) {
        this.email = email;
        return this;
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

    public void setLangkey(String langkey) {
        this.langkey = langkey;
    }

    public CorUser langkey(String langkey) {
        this.langkey = langkey;
        return this;
    }

    public String getActivationkey() {
        return activationkey;
    }

    public void setActivationkey(String activationkey) {
        this.activationkey = activationkey;
    }

    public CorUser activationkey(String activationkey) {
        this.activationkey = activationkey;
        return this;
    }

    public String getResetkey() {
        return resetkey;
    }

    public void setResetkey(String resetkey) {
        this.resetkey = resetkey;
    }

    public CorUser resetkey(String resetkey) {
        this.resetkey = resetkey;
        return this;
    }


    public ZonedDateTime getResetdate() {
        return resetdate;
    }

    public void setResetdate(ZonedDateTime resetdate) {
        this.resetdate = resetdate;
    }

    public CorUser resetdate(ZonedDateTime resetdate) {
        this.resetdate = resetdate;
        return this;
    }

    public CorUser avatar(CorImageItem corImageItem) {
        this.corImageItem = corImageItem;
        return this;
    }

    public CorImageItem getCorImageItem() {
        return corImageItem;
    }

    public void setCorImageItem(CorImageItem corImageItem) {
        this.corImageItem = corImageItem;
    }

    public Set<CorAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<CorAuthority> corUserAuthorities) {
        this.authorities = corUserAuthorities;
    }

    public CorUser authorities(Set<CorAuthority> corUserAuthorities) {
        this.authorities = corUserAuthorities;
        return this;
    }

    public Set<CorChannel> getChannels() {
        return channels;
    }

    public void setChannels(Set<CorChannel> corChannels) {
        this.channels = corChannels;
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

    public Set<CorNetwork> getNetworks() {
        return networks;
    }

    public void setNetworks(Set<CorNetwork> corNetworks) {
        this.networks = corNetworks;
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
                ", activated='" + activated + "'" +
                ", langkey='" + langkey + "'" +
                ", activationkey='" + activationkey + "'" +
                ", resetkey='" + resetkey + "'" +
                ", resetdate='" + resetdate + "'" +
                '}';
    }


}
