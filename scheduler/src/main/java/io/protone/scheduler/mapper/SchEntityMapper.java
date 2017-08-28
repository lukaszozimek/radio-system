package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import org.mapstruct.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Contract for a generic dto to entity mapper.
 *
 * @param <D> - DTO type parameter.
 * @param <E> - Entity type parameter.
 */
public interface SchEntityMapper<D, E> {

    public E DTO2DB(D dto, @Context CorNetwork network, @Context CorChannel corChannel);

    public D DB2DTO(E entity);

    public List<D> DBs2DTOs(List<E> entityList);

    default List<E> DTOs2DBs(List<D> dList, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<E> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (D dto : dList) {
            eList.add(DTO2DB(dto, network, corChannel));
        }
        return eList;
    }
}
