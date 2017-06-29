package io.protone.core.repository;


import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CorDictionary entity.
 */
@SuppressWarnings("unused")
public interface CorDictionaryRepository extends JpaRepository<CorDictionary, Long> {

    List<CorDictionary> findByCorDictionaryTypeAndCorModuleAndNetwork_Shortcut(String corDictionaryType, String corModule, String network);

    CorDictionary findByIdAndCorDictionaryTypeAndCorModuleAndNetwork_Shortcut(Long id, String corDictionaryType, String corModule, String network);

    CorDictionary findOneByIdAndNetwork(Long id, CorNetwork corNetwork);
}
