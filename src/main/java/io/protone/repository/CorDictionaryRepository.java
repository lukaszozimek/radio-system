package io.protone.repository;

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

    List<CorDictionary> findAllByCorDictionaryTypeAndNetwork(String corDictionaryType, CorNetwork network);

    CorDictionary findByIdAndCorDictionaryTypeAndNetwork(Long id, CorDictionaryType corDictionaryType, CorNetwork network);

    CorDictionary findOneByIdAndNetwork(Long id, CorNetwork corNetwork);
}
