package io.protone.core.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by lukaszozimek on 28/06/2017.
 */
@Entity
@Table(name = "cor_image_item")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public  class CorImageItem extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    protected Long id;

    @Column(name = "name")
    protected String name;

    @Column(name = "public_url")
    protected String publicUrl;

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

    public CorImageItem name(String name) {
        this.name = name;
        return this;
    }

    public String getPublicUrl() {
        return publicUrl;
    }

    public void setPublicUrl(String publicUrl) {
        this.publicUrl = publicUrl;
    }

    public CorImageItem publicUrl(String publicUrl) {
        this.publicUrl = publicUrl;
        return this;
    }
    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public CorImageItem network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }
}
