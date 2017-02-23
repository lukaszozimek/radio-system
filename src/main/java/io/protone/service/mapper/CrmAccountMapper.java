package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CrmAccountDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CrmAccount and its DTO CrmAccountDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CrmAccountMapper {

    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "addres.id", target = "addresId")
    @Mapping(source = "network.id", target = "networkId")
    @Mapping(source = "discount.id", target = "discountId")
    @Mapping(source = "keeper.id", target = "keeperId")
    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "range.id", target = "rangeId")
    @Mapping(source = "size.id", target = "sizeId")
    @Mapping(source = "industry.id", target = "industryId")
    @Mapping(source = "area.id", target = "areaId")
    CrmAccountDTO crmAccountToCrmAccountDTO(CrmAccount crmAccount);

    List<CrmAccountDTO> crmAccountsToCrmAccountDTOs(List<CrmAccount> crmAccounts);

    @Mapping(source = "personId", target = "person")
    @Mapping(source = "addresId", target = "addres")
    @Mapping(source = "networkId", target = "network")
    @Mapping(source = "discountId", target = "discount")
    @Mapping(source = "keeperId", target = "keeper")
    @Mapping(source = "countryId", target = "country")
    @Mapping(source = "rangeId", target = "range")
    @Mapping(source = "sizeId", target = "size")
    @Mapping(source = "industryId", target = "industry")
    @Mapping(source = "areaId", target = "area")
    @Mapping(target = "tasks", ignore = true)
    CrmAccount crmAccountDTOToCrmAccount(CrmAccountDTO crmAccountDTO);

    List<CrmAccount> crmAccountDTOsToCrmAccounts(List<CrmAccountDTO> crmAccountDTOs);

    default CorPerson corPersonFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorPerson corPerson = new CorPerson();
        corPerson.setId(id);
        return corPerson;
    }

    default CorAddress corAddressFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorAddress corAddress = new CorAddress();
        corAddress.setId(id);
        return corAddress;
    }

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }

    default TraDiscount traDiscountFromId(Long id) {
        if (id == null) {
            return null;
        }
        TraDiscount traDiscount = new TraDiscount();
        traDiscount.setId(id);
        return traDiscount;
    }

    default CorUser corUserFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorUser corUser = new CorUser();
        corUser.setId(id);
        return corUser;
    }

    default CorCountry corCountryFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorCountry corCountry = new CorCountry();
        corCountry.setId(id);
        return corCountry;
    }

    default CorRange corRangeFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorRange corRange = new CorRange();
        corRange.setId(id);
        return corRange;
    }

    default CorSize corSizeFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorSize corSize = new CorSize();
        corSize.setId(id);
        return corSize;
    }

    default TraIndustry traIndustryFromId(Long id) {
        if (id == null) {
            return null;
        }
        TraIndustry traIndustry = new TraIndustry();
        traIndustry.setId(id);
        return traIndustry;
    }

    default CorArea corAreaFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorArea corArea = new CorArea();
        corArea.setId(id);
        return corArea;
    }
}
