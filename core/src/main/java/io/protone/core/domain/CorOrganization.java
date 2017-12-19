package io.protone.core.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A CorNetwork.
 */
@Entity
@Table(name = "cor_organization")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorOrganization extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "shortcut", length = 100, nullable = false, unique = true)
    private String shortcut;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    @PodamExclude
    private CorImageItem corImageItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public CorOrganization shortcut(String shortcut) {
        this.shortcut = shortcut;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CorOrganization name(String name) {
        this.name = name;
        return this;
    }

    public Boolean isActive() {
        return active;
    }

    public CorOrganization active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CorOrganization description(String description) {
        this.description = description;
        return this;
    }

    public CorOrganization image(CorImageItem imageItem) {
        this.corImageItem = imageItem;
        return this;
    }

    public CorImageItem getCorImageItem() {
        return corImageItem;
    }

    public void setCorImageItem(CorImageItem corImageItem) {
        this.corImageItem = corImageItem;
    }

    public CorOrganization logo(CorImageItem corImageItem) {
        this.corImageItem = corImageItem;
        return this;
    }



}
