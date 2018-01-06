package io.protone.application.security.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwsHeader;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorUser;
import io.protone.core.service.CorSearchUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

import static io.protone.application.security.jwt.TokenProvider.ORGANIZATION;

/**
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a valid user is
 * found.
 */
public class JWTFilter extends GenericFilterBean {
    private static final String EMPTY = "";
    private static String NETWORK_SHORTCUT_KEY = "shortcut";
    private static String CHANNEL_SHORTCUT_KEY = "shortcut";
    private final Logger log = LoggerFactory.getLogger(JWTFilter.class);

    private CorSearchUserService corUserService;

    private TokenProvider tokenProvider;

    public JWTFilter(TokenProvider tokenProvider, CorSearchUserService corUserService) {
        this.tokenProvider = tokenProvider;
        this.corUserService = corUserService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String jwt = resolveToken(httpServletRequest);
            if (StringUtils.hasText(jwt)) {
                if (this.tokenProvider.validateToken(jwt)) {

                    if (this.checkIfUserCanAccesResource(httpServletRequest, jwt)) {
                        Authentication authentication = this.tokenProvider.getAuthentication(jwt);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ExpiredJwtException eje) {
            log.info("Security exception for user {} - {}", eje.getClaims().getSubject(), eje.getMessage());
            ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }


    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JWTConfigurer.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String jwt = bearerToken.substring(7, bearerToken.length());
            return jwt;
        }
        return null;
    }

    private boolean checkIfUserCanAccesResource(HttpServletRequest httpServletRequest, String jwt) {
        if (httpServletRequest.getRequestURI().equals("/api/v1/organization")) {
            return true;
        }
        if (httpServletRequest.getRequestURI().startsWith("/api/v1/user")) {
            return true;
        }
        String newtworkShortcut = getorganizationShortcutFromPathParameter(httpServletRequest.getRequestURI());
        String corChannelShortcut = getChannelShortcutFromPathParameter(httpServletRequest.getRequestURI());

        if (Strings.isNullOrEmpty(newtworkShortcut)) {
            return false;
        }
        UsernamePasswordAuthenticationToken userAuth = (UsernamePasswordAuthenticationToken) tokenProvider.getAuthentication(jwt);
        User user = (User) userAuth.getPrincipal();
        JwsHeader jwsHeader = tokenProvider.getUserAuthorizationAccess(jwt);
        LinkedHashMap<String, String> corNetworkMap = (LinkedHashMap<String, String>) jwsHeader.get(ORGANIZATION);
        Optional<CorUser> corUser = corUserService.getUserWithAuthoritiesByLoginAndOrganizationShortcut(user.getUsername(), newtworkShortcut);

        if (corUser.isPresent() && checkChannelAccess(corUser.get().getChannels(), corChannelShortcut)) {
            return true;
        }
        return false;
    }

    private String getChannelShortcutFromPathParameter(String pathInfo) {
        String[] splitedUrl = pathInfo.split("/");
        if (Arrays.stream(splitedUrl).anyMatch(Predicate.isEqual("channel"))) {
            if (splitedUrl.length > 7 && splitedUrl[5].equals("channel"))
                return pathInfo.split("/")[6];
        }
        return EMPTY;
    }

    private String getorganizationShortcutFromPathParameter(String pathInfo) {
        String[] splitedUrl = pathInfo.split("/");
        if (splitedUrl.length >= 4) {
            return splitedUrl[4];
        }
        return EMPTY;
    }

    private boolean checkChannelAccess(Set<CorChannel> corChannels, String channelShortcut) {
        if (Strings.isNullOrEmpty(channelShortcut)) {
            return true;
        }
        if (corChannels.isEmpty()) {
            return false;
        }
        if (corChannels.stream().anyMatch(corChannel -> corChannel.getShortcut().equals(channelShortcut))) {
            return true;
        }
        return false;
    }
}
