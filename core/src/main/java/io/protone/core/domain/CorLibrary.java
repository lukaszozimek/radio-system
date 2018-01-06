package io.protone.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A LibLibrary.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class CorLibrary extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @PodamExclude
    protected Long id;

    @NotNull
    @Size(max = 1)
    @Column(name = "prefix", length = 1, nullable = false)
    protected String prefix;

    @NotNull
    @Size(max = 10)
    @Column(name = "shortcut", length = 10, nullable = false, unique = true)
    protected String shortcut;

    @NotNull
    @Column(name = "name", length = 100, nullable = false, unique = true)
    protected String name;

    @NotNull
    @Column(name = "counter", nullable = false)
    protected Long counter;

    @Column(name = "description")
    protected String description;

    @ManyToOne
    @JsonIgnore
    @PodamExclude
    protected CorChannel channel;

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel channel) {
        this.channel = channel;
    }

}
