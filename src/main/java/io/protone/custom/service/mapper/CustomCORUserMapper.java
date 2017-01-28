package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreManagedUserPT;
import io.protone.domain.*;
import org.springframework.stereotype.Service;

/**
 * Created by lukaszozimek on 24.01.2017.
 */
@Service
public class CustomCORUserMapper {

    public static final String ASSIGNED_TO = "ASSIGNEDTOTASK";
    public static final String CREATED_BY = "CREATEDBY";
    public static final String OPPORTUNITY_OWNER = "OPPORTUNINTY_OWNER";
    public static final String ACCOUNT_OWNER = "ACCOUNT_OWNER";

    public CoreManagedUserPT transformUserEnity(User user) {
        return new CoreManagedUserPT()
            .authoritiesf(user.getAuthorities())
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

    public User tranformUserDTO(CoreManagedUserPT coreManagedUserPT) {
        User user = new User();
        user.setId(coreManagedUserPT.getId());
        return user;

    }

    public CORAssociation createCRMAssignetToTaskAssociation(CRMTask crmTask, User user, CORNetwork corNetwork) {
        return new CORAssociation().name(ASSIGNED_TO)
            .sourceId(crmTask.getId())
            .sourceClass(CRMTask.class.getName())
            .targetId(user.getId())
            .targetClass(User.class.getName())
            .network(corNetwork);
    }

    public CORAssociation createCRMCreatedByTaskAssociation(CRMTask crmTask, User user, CORNetwork corNetwork) {
        return new CORAssociation().name(CREATED_BY)
            .sourceId(crmTask.getId())
            .sourceClass(CRMTask.class.getName())
            .targetId(user.getId())
            .targetClass(User.class.getName())
            .network(corNetwork);
    }

    public CORAssociation createCRMOpportunitnyUserAssociation(CRMOpportunity crmOpportunity, User user, CORNetwork corNetwork) {
        return new CORAssociation().name(OPPORTUNITY_OWNER)
            .sourceId(crmOpportunity.getId())
            .sourceClass(CRMOpportunity.class.getName())
            .targetId(user.getId())
            .targetClass(User.class.getName())
            .network(corNetwork);
    }

    public CORAssociation createCRMAccountUserAssociation(CRMAccount crmAccount, User user, CORNetwork corNetwork) {
        return new CORAssociation().name(ACCOUNT_OWNER)
            .sourceId(crmAccount.getId())
            .sourceClass(CRMAccount.class.getName())
            .targetId(user.getId())
            .targetClass(User.class.getName())
            .network(corNetwork);
    }
}
