package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CorTaxDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CorTax and its DTO CorTaxDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorTaxMapper {

    @Mapping(source = "network.id", target = "networkId")
    CorTaxDTO corTaxToCorTaxDTO(CorTax corTax);

    List<CorTaxDTO> corTaxesToCorTaxDTOs(List<CorTax> corTaxes);

    @Mapping(source = "networkId", target = "network")
    CorTax corTaxDTOToCorTax(CorTaxDTO corTaxDTO);

    List<CorTax> corTaxDTOsToCorTaxes(List<CorTaxDTO> corTaxDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
