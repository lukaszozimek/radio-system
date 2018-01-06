package io.protone.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

/**
 * Created by lukaszozimek on 28/06/2017.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class CorItem extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @PodamExclude
    protected Long id;
    @NotNull
    @Column(name = "idx", nullable = false)
    protected String idx;
    @NotNull
    @Column(name = "name")
    protected String name;
    @Column(name = "description")
    protected String description;

    @ManyToOne
    @PodamExclude
    protected CorChannel channel;

    @PodamExclude
    @OneToMany(mappedBy = "tags", fetch = EAGER)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    protected Set<CorTag> tags = new HashSet<>();

    @PodamExclude
    @OneToMany(mappedBy = "libItemPropertyValue", fetch = EAGER)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    protected Set<CorPropertyValue> properites = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
