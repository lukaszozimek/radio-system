package io.protone.security;

import com.google.common.base.Strings;
import io.protone.domain.CorUser;
import io.protone.service.cor.CorUserService;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<CorUser> {

    private static final Long DEFAULT_SYSTEM_USER_ID = 1L;

    @Inject
    private CorUserService corUserService;

    //TODO: NOT GOOD SOULTION. WE SHOULD Groom this issue.
    //TODO: PUTING THIS INTO THE METHOD couse stackoverflow exception
    private CorUser defaultUser;

    @PostConstruct
    public void postInitializeBean() {
        defaultUser = corUserService.getUserWithAuthorities(DEFAULT_SYSTEM_USER_ID);
    }

    @Override
    public CorUser getCurrentAuditor() {
        String userName = SecurityUtils.getCurrentUserLogin();
        if (!Strings.isNullOrEmpty(userName)) {
            CorUser corUser = corUserService.getUserWithAuthoritiesByLogin(userName).orElse(null);
            return corUser != null ? corUser : defaultUser;

        }
        return defaultUser;
    }


}
