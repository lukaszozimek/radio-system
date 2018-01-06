package io.protone.traffic.mapper;

import io.protone.core.api.dto.CoreContactDTO;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorContact;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorPerson;
import io.protone.core.mapper.CorAddressMapper;
import io.protone.core.mapper.CorDictionaryMapper;
import io.protone.core.mapper.CorPersonMapper;
import io.protone.crm.domain.CrmAccount;
import io.protone.crm.mapper.CrmDiscountMapper;
import io.protone.crm.mapper.CrmTaskMapper;
import io.protone.traffic.api.dto.TraCustomerDTO;
import io.protone.traffic.api.dto.TraCustomerPersonDTO;
import io.protone.traffic.api.dto.thin.TraCustomerThinDTO;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 28/06/2017.
 */
@Mapper(componentModel = "spring", uses = {CrmTaskMapper.class, CorDictionaryMapper.class, CorAddressMapper.class, CorPersonMapper.class, CrmDiscountMapper.class})
public interface TraCustomerMapper {

    @Mapping(source = "person", target = "person")
    @Mapping(source = "addres", target = "addres")
    @Mapping(source = "keeper", target = "account")
    @Mapping(source = "range", target = "range")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "industry", target = "industry")
    @Mapping(source = "area", target = "area")
    TraCustomerDTO traDB2DTO(CrmAccount crmAccount);

    List<TraCustomerDTO> traDBs2DTOs(List<CrmAccount> crmAccounts);

    @Mapping(source = "person", target = "person")
    @Mapping(source = "addres", target = "addres")
    @Mapping(source = "account", target = "keeper")
    @Mapping(source = "range", target = "range")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "industry", target = "industry")
    @Mapping(source = "area", target = "area")
    CrmAccount traDTO2DB(TraCustomerDTO crmAccountDTO, @Context CorChannel networkId);

    default List<CrmAccount> traDTOs2DBs(List<TraCustomerDTO> crmAccountDTOs, CorChannel networkId) {
        List<CrmAccount> crmAccounts = new ArrayList<>();
        if (crmAccountDTOs.isEmpty() || crmAccountDTOs == null) {
            return null;
        }
        for (TraCustomerDTO dto : crmAccountDTOs) {
            crmAccounts.add(traDTO2DB(dto, networkId));
        }
        return crmAccounts;
    }

    @AfterMapping
    default void traCustomerPTToCrmAccountAfterMapping(TraCustomerDTO dto, @MappingTarget CrmAccount entity, @Context CorChannel corNetwork) {
        entity.setChannel(corNetwork);
    }

    @AfterMapping
    default void traCustomerPersonDTOToCorPersonAfterMapping(TraCustomerPersonDTO dto, @MappingTarget CorPerson entity, @Context CorChannel corNetwork) {
        entity.setChannel(corNetwork);
    }

    @AfterMapping
    default void coreContactDTOToCorContactAfterMapping(CoreContactDTO dto, @MappingTarget CorContact entity, @Context CorChannel corNetwork) {
        entity.setChannel(corNetwork);
    }

    CrmAccount crmAccountFromTraCustomerThinPT(TraCustomerThinDTO id);

    TraCustomerThinDTO traCustomerThinPTFromCrmAccount(CrmAccount id);

}
