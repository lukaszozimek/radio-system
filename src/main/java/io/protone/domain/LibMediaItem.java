package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import io.protone.domain.enumeration.LibItemTypeEnum;

import io.protone.domain.enumeration.LibItemStateEnum;

/**
 * A LibMediaItem.
 */
@Entity
@Table(name = "lib_media_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibMediaItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
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
    private LibItemTypeEnum itemType;

    @NotNull
    @Column(name = "length", nullable = false)
    private Long length;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private LibItemStateEnum state;

    @Column(name = "command")
    private String command;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private LibLibrary library;

    @ManyToOne
    private LibLabel label;

    @ManyToOne
    private LibArtist artist;

    @ManyToOne
    private LibAlbum album;

    @ManyToOne
    private LibTrack track;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdx() {
        return idx;
    }

    public LibMediaItem idx(String idx) {
        this.idx = idx;
        return this;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public LibMediaItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LibItemTypeEnum getItemType() {
        return itemType;
    }

    public LibMediaItem itemType(LibItemTypeEnum itemType) {
        this.itemType = itemType;
        return this;
    }

    public void setItemType(LibItemTypeEnum itemType) {
        this.itemType = itemType;
    }

    public Long getLength() {
        return length;
    }

    public LibMediaItem length(Long length) {
        this.length = length;
        return this;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public LibItemStateEnum getState() {
        return state;
    }

    public LibMediaItem state(LibItemStateEnum state) {
        this.state = state;
        return this;
    }

    public void setState(LibItemStateEnum state) {
        this.state = state;
    }

    public String getCommand() {
        return command;
    }

    public LibMediaItem command(String command) {
        this.command = command;
        return this;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getDescription() {
        return description;
    }

    public LibMediaItem description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LibLibrary getLibrary() {
        return library;
    }

    public LibMediaItem library(LibLibrary libLibrary) {
        this.library = libLibrary;
        return this;
    }

    public void setLibrary(LibLibrary libLibrary) {
        this.library = libLibrary;
    }

    public LibLabel getLabel() {
        return label;
    }

    public LibMediaItem label(LibLabel libLabel) {
        this.label = libLabel;
        return this;
    }

    public void setLabel(LibLabel libLabel) {
        this.label = libLabel;
    }

    public LibArtist getArtist() {
        return artist;
    }

    public LibMediaItem artist(LibArtist libArtist) {
        this.artist = libArtist;
        return this;
    }

    public void setArtist(LibArtist libArtist) {
        this.artist = libArtist;
    }

    public LibAlbum getAlbum() {
        return album;
    }

    public LibMediaItem album(LibAlbum libAlbum) {
        this.album = libAlbum;
        return this;
    }

    public void setAlbum(LibAlbum libAlbum) {
        this.album = libAlbum;
    }

    public LibTrack getTrack() {
        return track;
    }

    public LibMediaItem track(LibTrack libTrack) {
        this.track = libTrack;
        return this;
    }

    public void setTrack(LibTrack libTrack) {
        this.track = libTrack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LibMediaItem libMediaItem = (LibMediaItem) o;
        if (libMediaItem.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, libMediaItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LibMediaItem{" +
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
