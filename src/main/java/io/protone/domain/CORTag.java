package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CORTag.
 */
@Entity
@Table(name = "cor_tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CORTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "tag", length = 100, nullable = false)
    private String tag;

    @ManyToOne
    private LIBMediaItem lIBMediaItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public CORTag tag(String tag) {
        this.tag = tag;
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public LIBMediaItem getLIBMediaItem() {
        return lIBMediaItem;
    }

    public CORTag lIBMediaItem(LIBMediaItem lIBMediaItem) {
        this.lIBMediaItem = lIBMediaItem;
        return this;
    }

    public void setLIBMediaItem(LIBMediaItem lIBMediaItem) {
        this.lIBMediaItem = lIBMediaItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CORTag cORTag = (CORTag) o;
        if (cORTag.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cORTag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CORTag{" +
            "id=" + id +
            ", tag='" + tag + "'" +
            '}';
    }
}
