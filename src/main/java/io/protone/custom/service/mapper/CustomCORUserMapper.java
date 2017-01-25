package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreManagedUserPT;
import io.protone.domain.User;
import org.springframework.stereotype.Service;

/**
 * Created by lukaszozimek on 24.01.2017.
 */
@Service
public class CustomCORUserMapper {

    CoreManagedUserPT transformUserEnity(User user) {
        return new CoreManagedUserPT()
            .authorities(user.getAuthorities())
            .activated(user.getActivated())
            .createdBy(user.getCreatedBy())
            .email(user.getEmail())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .langKey(user.getLangKey())
            .id(user.getId())
            .lastModifiedBy(user.getLastModifiedBy())
            .login(user.getLogin())
            .createdBy(user.getCreatedBy())
            .createdDate(user.getCreatedDate());
    }

    User tranformUserDTO(CoreManagedUserPT coreManagedUserPT) {
        User user = new User();
        user.setId(coreManagedUserPT.getId());

        return user;

    }

}
