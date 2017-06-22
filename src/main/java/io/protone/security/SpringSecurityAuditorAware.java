package io.protone.security;

import com.google.common.base.Strings;
import io.protone.domain.CorUser;
import io.protone.service.cor.CorUserService;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<CorUser> {

    @Inject
    private CorUserService corUserService;

    private CorUser getDefaultUser() {
        CorUser corUser = new CorUser();
        corUser.setId(1L);
        return corUser;
    }

    @Override
    public CorUser getCurrentAuditor() {
        String userName = SecurityUtils.getCurrentUserLogin();
        if (!Strings.isNullOrEmpty(userName)) {
            CorUser corUser = corUserService.getUserWithAuthoritiesByLogin(userName).orElse(null);
            return corUser != null ? corUser : getDefaultUser();

        }
        return getDefaultUser();
    }
}
