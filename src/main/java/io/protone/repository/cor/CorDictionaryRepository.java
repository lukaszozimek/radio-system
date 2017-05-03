package io.protone.repository.cor;

import java.util.List;

import io.protone.domain.CorDictionary;
import io.protone.domain.CorDictionaryType;
import io.protone.domain.CorModule;
import io.protone.domain.CorNetwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Spring Data JPA repository for the CorDictionary entity.
 */
@SuppressWarnings("unused")
public interface CorDictionaryRepository extends JpaRepository<CorDictionary, Long> {

    List<CorDictionary> findByCorDictionaryTypeAndCorModuleAndNetwork_Shortcut(String corDictionaryType, String corModule, String network);

    CorDictionary findByIdAndCorDictionaryTypeAndCorModuleAndNetwork_Shortcut(Long id, String corDictionaryType, String corModule, String network);

    CorDictionary findOneByIdAndNetwork(Long id, CorNetwork corNetwork);
}
