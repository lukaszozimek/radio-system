package io.protone.scheduler.mapper;

import org.mapstruct.Mapper;

import java.util.List;

/**
 * Contract for a generic dto to entity mapper.
 @param <D> - DTO type parameter.
 @param <E> - Entity type parameter.
 */
public interface SchEntityMapper<D, E> {

    public E toEntity(D dto);

    public D toDto(E entity);

    public List <E> toEntity(List<D> dtoList);

    public List <D> toDto(List<E> entityList);
}