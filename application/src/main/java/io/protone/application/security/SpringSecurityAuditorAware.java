package io.protone.application.security;

import com.google.common.base.Strings;
import io.protone.domain.CorUser;
import io.protone.service.cor.CorUserService;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<CorUser> {

    private static final Long DEFAULT_SYSTEM_USER_ID = 1L;

    @Inject
    private CorUserService corUserService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW) // https://stackoverflow.com/questions/34753357/java-lang-stackoverflowerror-when-trying-to-update-any-entity-with-createdby
    public CorUser getCurrentAuditor() {
        String userName = SecurityUtils.getCurrentUserLogin();
        if (!Strings.isNullOrEmpty(userName)) {
            CorUser corUser = corUserService.getUserWithAuthoritiesByLogin(userName).orElse(null);
            return corUser != null ? corUser : getDefaultUser();

        }
        return getDefaultUser();
    }

    private CorUser getDefaultUser() {
        return corUserService.getUserWithAuthorities(DEFAULT_SYSTEM_USER_ID);
    }


}
