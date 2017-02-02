package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LIBTrack.
 */
@Entity
@Table(name = "lib_track")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LIBTrack implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "disc_no", nullable = false)
    private Integer discNo;

    @NotNull
    @Column(name = "track_no", nullable = false)
    private Integer trackNo;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotNull
    @Column(name = "length", nullable = false)
    private Long length;

    @Column(name = "description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDiscNo() {
        return discNo;
    }

    public LIBTrack discNo(Integer discNo) {
        this.discNo = discNo;
        return this;
    }

    public void setDiscNo(Integer discNo) {
        this.discNo = discNo;
    }

    public Integer getTrackNo() {
        return trackNo;
    }

    public LIBTrack trackNo(Integer trackNo) {
        this.trackNo = trackNo;
        return this;
    }

    public void setTrackNo(Integer trackNo) {
        this.trackNo = trackNo;
    }

    public String getName() {
        return name;
    }

    public LIBTrack name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLength() {
        return length;
    }

    public LIBTrack length(Long length) {
        this.length = length;
        return this;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public String getDescription() {
        return description;
    }

    public LIBTrack description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LIBTrack lIBTrack = (LIBTrack) o;
        if (lIBTrack.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lIBTrack.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LIBTrack{" +
            "id=" + id +
            ", discNo='" + discNo + "'" +
            ", trackNo='" + trackNo + "'" +
            ", name='" + name + "'" +
            ", length='" + length + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
