package io.protone.repository.custom;

import io.protone.domain.CorUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by lukaszozimek on 27/02/2017.
 */
public interface CustomCorUserRepository extends JpaRepository<CorUser, Long> {
    Optional<CorUser> findOneByActivationkey(String activationKey);

    // List<CorUser> findAllByActivatedIsFalseAndCreatedDateBefore(ZonedDateTime dateTime);

    Optional<CorUser> findOneByResetkey(String resetKey);

    Optional<CorUser> findOneByEmail(String email);

    Optional<CorUser> findOneByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    Optional<CorUser> findOneWithAuthoritiesByLogin(String login);

    CorUser findOneWithAuthoritiesById(Long id);

    List<CorUser> findAllByActivatedIsFalseAndCreateddateBefore(ZonedDateTime zonedDateTime);

    //qCorUser findOneWithAuthoritiesById(Long id);

    //@EntityGraph(attributePaths = "authorities")
    // Optional<CorUser> findOneWithAuthoritiesByLogin(String login);
}
