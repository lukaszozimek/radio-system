package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CrmContactDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CrmContact and its DTO CrmContactDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CrmContactMapper {

    @Mapping(source = "addres.id", target = "addresId")
    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "network.id", target = "networkId")
    @Mapping(source = "range.id", target = "rangeId")
    @Mapping(source = "size.id", target = "sizeId")
    @Mapping(source = "industry.id", target = "industryId")
    @Mapping(source = "area.id", target = "areaId")
    @Mapping(source = "keeper.id", target = "keeperId")
    @Mapping(source = "status.id", target = "statusId")
    CrmContactDTO crmContactToCrmContactDTO(CrmContact crmContact);

    List<CrmContactDTO> crmContactsToCrmContactDTOs(List<CrmContact> crmContacts);

    @Mapping(source = "addresId", target = "addres")
    @Mapping(source = "countryId", target = "country")
    @Mapping(source = "personId", target = "person")
    @Mapping(source = "networkId", target = "network")
    @Mapping(source = "rangeId", target = "range")
    @Mapping(source = "sizeId", target = "size")
    @Mapping(source = "industryId", target = "industry")
    @Mapping(source = "areaId", target = "area")
    @Mapping(source = "keeperId", target = "keeper")
    @Mapping(source = "statusId", target = "status")
    @Mapping(target = "tasks", ignore = true)
    CrmContact crmContactDTOToCrmContact(CrmContactDTO crmContactDTO);

    List<CrmContact> crmContactDTOsToCrmContacts(List<CrmContactDTO> crmContactDTOs);

    default CorAddress corAddressFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorAddress corAddress = new CorAddress();
        corAddress.setId(id);
        return corAddress;
    }

    default CorCountry corCountryFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorCountry corCountry = new CorCountry();
        corCountry.setId(id);
        return corCountry;
    }

    default CorPerson corPersonFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorPerson corPerson = new CorPerson();
        corPerson.setId(id);
        return corPerson;
    }

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
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

    default CorUser corUserFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorUser corUser = new CorUser();
        corUser.setId(id);
        return corUser;
    }

    default CrmContactStatus crmContactStatusFromId(Long id) {
        if (id == null) {
            return null;
        }
        CrmContactStatus crmContactStatus = new CrmContactStatus();
        crmContactStatus.setId(id);
        return crmContactStatus;
    }
}
