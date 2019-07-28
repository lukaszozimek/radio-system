package io.protone.scheduler.domain;

import io.protone.core.domain.AbstractAuditingEntity;

import javax.persistence.*;

/**
 * Created by lukaszozimek on 07.09.2017.
 */


@MappedSuperclass
public class SchBaseEntity extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    protected Long id;

}
