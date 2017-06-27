package io.protone.repository.cor;

import io.protone.domain.CorNetwork;
import io.protone.domain.CorUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by lukaszozimek on 27/02/2017.
 */
public interface CorUserRepository extends JpaRepository<CorUser, Long> {

    Optional<CorUser> findOneByActivationkey(String activationKey);

    Optional<CorUser> findOneByResetkey(String resetKey);

    Optional<CorUser> findOneByEmail(String email);

    Optional<CorUser> findOneByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    Optional<CorUser> findOneWithAuthoritiesByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    Optional<CorUser> findOneWithAuthoritiesByLoginAndNetworks(String login, Set<CorNetwork> corNetwork);

    List<CorUser> findByNetworks(Set<CorNetwork> network);

    CorUser findOneWithAuthoritiesById(Long id);

    List<CorUser> findAllByActivatedIsFalseAndCreatedDateBefore(ZonedDateTime zonedDateTime);

}
