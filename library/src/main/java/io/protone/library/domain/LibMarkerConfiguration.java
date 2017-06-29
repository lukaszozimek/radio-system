package io.protone.library.domain;

import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.enumeration.LibMarkerTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LibMarkerConfiguration.
 */
@Entity
@Table(name = "cfg_marker_configuration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibMarkerConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotNull
    @Size(max = 100)
    @Column(name = "display_name", length = 100, nullable = false)
    private String displayName;

    @NotNull
    @Size(max = 100)
    @Column(name = "color", length = 100, nullable = false)
    private String color;

    @NotNull
    @Size(max = 100)
    @Column(name = "keyboard_shortcut", length = 100, nullable = false)
    private String keyboardShortcut;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private LibMarkerTypeEnum type;

    @ManyToOne
    private CorNetwork network;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LibMarkerConfiguration name(String name) {
        this.name = name;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public LibMarkerConfiguration displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LibMarkerConfiguration color(String color) {
        this.color = color;
        return this;
    }

    public String getKeyboardShortcut() {
        return keyboardShortcut;
    }

    public void setKeyboardShortcut(String keyboardShortcut) {
        this.keyboardShortcut = keyboardShortcut;
    }

    public LibMarkerConfiguration keyboardShortcut(String keyboardShortcut) {
        this.keyboardShortcut = keyboardShortcut;
        return this;
    }

    public LibMarkerTypeEnum getType() {
        return type;
    }

    public void setType(LibMarkerTypeEnum type) {
        this.type = type;
    }

    public LibMarkerConfiguration type(LibMarkerTypeEnum type) {
        this.type = type;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public LibMarkerConfiguration network(CorNetwork corNetwork) {
        this.network = corNetwork;
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
        LibMarkerConfiguration cfgMarkerConfiguration = (LibMarkerConfiguration) o;
        if (cfgMarkerConfiguration.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cfgMarkerConfiguration.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LibMarkerConfiguration{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", displayName='" + displayName + "'" +
            ", color='" + color + "'" +
            ", keyboardShortcut='" + keyboardShortcut + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
