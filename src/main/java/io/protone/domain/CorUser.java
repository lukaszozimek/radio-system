package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private CorNetwork network;

    @ManyToOne
    private CorChannel channel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CorUser name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public CorUser network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public CorUser channel(CorChannel corChannel) {
        this.channel = corChannel;
        return this;
    }

    public void setChannel(CorChannel corChannel) {
        this.channel = corChannel;
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
            ", name='" + name + "'" +
            '}';
    }
}
