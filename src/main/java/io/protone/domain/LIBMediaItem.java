package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import io.protone.domain.enumeration.LIBItemTypeEnum;

import io.protone.domain.enumeration.LIBItemStateEnum;

/**
 * A LIBMediaItem.
 */
@Entity
@Table(name = "lib_media_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LIBMediaItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 15)
    @Column(name = "idx", length = 15, nullable = false)
    private String idx;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "item_type", nullable = false)
    private LIBItemTypeEnum itemType;

    @NotNull
    @Column(name = "length", nullable = false)
    private Long length;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private LIBItemStateEnum state;

    @Column(name = "command")
    private String command;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private LIBLibrary library;

    @ManyToOne
    private LIBLabel label;

    @ManyToOne
    private LIBArtist artist;

    @ManyToOne
    private LIBAlbum album;

    @ManyToOne
    private LIBTrack track;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdx() {
        return idx;
    }

    public LIBMediaItem idx(String idx) {
        this.idx = idx;
        return this;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public LIBMediaItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LIBItemTypeEnum getItemType() {
        return itemType;
    }

    public LIBMediaItem itemType(LIBItemTypeEnum itemType) {
        this.itemType = itemType;
        return this;
    }

    public void setItemType(LIBItemTypeEnum itemType) {
        this.itemType = itemType;
    }

    public Long getLength() {
        return length;
    }

    public LIBMediaItem length(Long length) {
        this.length = length;
        return this;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public LIBItemStateEnum getState() {
        return state;
    }

    public LIBMediaItem state(LIBItemStateEnum state) {
        this.state = state;
        return this;
    }

    public void setState(LIBItemStateEnum state) {
        this.state = state;
    }

    public String getCommand() {
        return command;
    }

    public LIBMediaItem command(String command) {
        this.command = command;
        return this;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getDescription() {
        return description;
    }

    public LIBMediaItem description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LIBLibrary getLibrary() {
        return library;
    }

    public LIBMediaItem library(LIBLibrary lIBLibrary) {
        this.library = lIBLibrary;
        return this;
    }

    public void setLibrary(LIBLibrary lIBLibrary) {
        this.library = lIBLibrary;
    }

    public LIBLabel getLabel() {
        return label;
    }

    public LIBMediaItem label(LIBLabel lIBLabel) {
        this.label = lIBLabel;
        return this;
    }

    public void setLabel(LIBLabel lIBLabel) {
        this.label = lIBLabel;
    }

    public LIBArtist getArtist() {
        return artist;
    }

    public LIBMediaItem artist(LIBArtist lIBArtist) {
        this.artist = lIBArtist;
        return this;
    }

    public void setArtist(LIBArtist lIBArtist) {
        this.artist = lIBArtist;
    }

    public LIBAlbum getAlbum() {
        return album;
    }

    public LIBMediaItem album(LIBAlbum lIBAlbum) {
        this.album = lIBAlbum;
        return this;
    }

    public void setAlbum(LIBAlbum lIBAlbum) {
        this.album = lIBAlbum;
    }

    public LIBTrack getTrack() {
        return track;
    }

    public LIBMediaItem track(LIBTrack lIBTrack) {
        this.track = lIBTrack;
        return this;
    }

    public void setTrack(LIBTrack lIBTrack) {
        this.track = lIBTrack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LIBMediaItem lIBMediaItem = (LIBMediaItem) o;
        if (lIBMediaItem.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lIBMediaItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LIBMediaItem{" +
            "id=" + id +
            ", idx='" + idx + "'" +
            ", name='" + name + "'" +
            ", itemType='" + itemType + "'" +
            ", length='" + length + "'" +
            ", state='" + state + "'" +
            ", command='" + command + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
