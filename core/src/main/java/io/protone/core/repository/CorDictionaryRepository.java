package io.protone.core.repository;


import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CorDictionary entity.
 */
@SuppressWarnings("unused")
public interface CorDictionaryRepository extends JpaRepository<CorDictionary, Long> {

    Slice<CorDictionary> findSliceByCorDictionaryTypeAndCorModuleAndChannel_Organization_Shortcut(String corDictionaryType, String corModule, String network, Pageable pageable);

    CorDictionary findByIdAndCorDictionaryTypeAndCorModuleAndChannel_Organization_Shortcut(Long id, String corDictionaryType, String corModule, String network);

    CorDictionary findOneByIdAndChannel_Organization_Shortcut(Long id, String organizationShortcut);
}
