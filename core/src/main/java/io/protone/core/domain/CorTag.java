package io.protone.core.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CorTag.
 */
@Entity
@Table(name = "cor_tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "tag", length = 100, nullable = false)
    private String tag;

    @ManyToOne
    private CorChannel channel;

    @ManyToOne
    private CorItem tags;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public CorTag tag(String tag) {
        this.tag = tag;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel corNetwork) {
        this.channel = corNetwork;
    }

    public CorTag channel(CorChannel corNetwork) {
        this.channel = corNetwork;
        return this;
    }

    public CorItem getTags() {
        return tags;
    }

    public void setTags(CorItem libMediaItem) {
        this.tags = libMediaItem;
    }

    public CorTag tags(CorItem libMediaItem) {
        this.tags = libMediaItem;
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
        CorTag corTag = (CorTag) o;
        if (corTag.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, corTag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CorTag{" +
            "id=" + id +
            ", tag='" + tag + "'" +
            '}';
    }
}
