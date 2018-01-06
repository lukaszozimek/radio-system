package io.protone.traffic.domain;

import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.crm.domain.CrmAccount;
import io.protone.library.domain.LibFileItem;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TraMediaPlan.
 */
@Entity
@Table(name = "tra_media_plan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TraMediaPlan extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne
    @PodamExclude
    @JoinColumn(unique = true, nullable = false)
    private LibFileItem libFileItem;

    @ManyToOne
    @PodamExclude
    private CorChannel channel;

    @ManyToOne
    @PodamExclude
    private CrmAccount account;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TraMediaPlan name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LibFileItem getLibFileItem() {
        return libFileItem;
    }

    public TraMediaPlan fileItem(LibFileItem libMediaItem) {
        this.libFileItem = libMediaItem;
        return this;
    }

    public void setLibFileItem(LibFileItem libMediaItem) {
        this.libFileItem = libMediaItem;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public TraMediaPlan channel(CorChannel corChannel) {
        this.channel = corChannel;
        return this;
    }

    public void setChannel(CorChannel corChannel) {
        this.channel = corChannel;
    }

    public CrmAccount getAccount() {
        return account;
    }

    public TraMediaPlan account(CrmAccount crmAccount) {
        this.account = crmAccount;
        return this;
    }

    public void setAccount(CrmAccount crmAccount) {
        this.account = crmAccount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraMediaPlan traMediaPlan = (TraMediaPlan) o;
        if (traMediaPlan.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, traMediaPlan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraMediaPlan{" +
                "id=" + id +
                ", name='" + name + "'" +
                '}';
    }
}
