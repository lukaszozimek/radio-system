package io.protone.crm.repostiory;


import io.protone.crm.domain.CrmContact;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CrmContact entity.
 */
public interface CrmContactRepository extends JpaRepository<CrmContact, Long> {
    CrmContact findOneByShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(String shortname, String organization, String channelShortcut);

    Slice<CrmContact> findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(String organization, String channelShortcut, Pageable pagable);

    void deleteByShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(String shortname, String organization, String channelShortcut );
}
