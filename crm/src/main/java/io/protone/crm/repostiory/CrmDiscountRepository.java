package io.protone.crm.repostiory;

import io.protone.core.domain.CorNetwork;
import io.protone.crm.domain.CrmDiscount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the TraDiscount entity.
 */
public interface CrmDiscountRepository extends JpaRepository<CrmDiscount, Long> {

    Slice<CrmDiscount> findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(String organization, String channelShortcut, Pageable pageable);

    CrmDiscount findOneByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(Long id, String organization, String channelShortcut);
}

