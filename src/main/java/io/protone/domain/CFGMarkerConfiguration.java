package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import io.protone.domain.enumeration.LIBMarkerTypeEnum;

/**
 * A CFGMarkerConfiguration.
 */
@Entity
@Table(name = "cfg_marker_configuration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CFGMarkerConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private LIBMarkerTypeEnum type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CFGMarkerConfiguration name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public CFGMarkerConfiguration displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getColor() {
        return color;
    }

    public CFGMarkerConfiguration color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getKeyboardShortcut() {
        return keyboardShortcut;
    }

    public CFGMarkerConfiguration keyboardShortcut(String keyboardShortcut) {
        this.keyboardShortcut = keyboardShortcut;
        return this;
    }

    public void setKeyboardShortcut(String keyboardShortcut) {
        this.keyboardShortcut = keyboardShortcut;
    }

    public LIBMarkerTypeEnum getType() {
        return type;
    }

    public CFGMarkerConfiguration type(LIBMarkerTypeEnum type) {
        this.type = type;
        return this;
    }

    public void setType(LIBMarkerTypeEnum type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CFGMarkerConfiguration cFGMarkerConfiguration = (CFGMarkerConfiguration) o;
        if (cFGMarkerConfiguration.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cFGMarkerConfiguration.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CFGMarkerConfiguration{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", displayName='" + displayName + "'" +
            ", color='" + color + "'" +
            ", keyboardShortcut='" + keyboardShortcut + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
