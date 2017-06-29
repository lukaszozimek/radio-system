package io.protone.core.api.dto;

import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.Set;

/**
 * CorManagedUserDTO
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class CorManagedUserDTO extends CorUserDTO {


    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    public CorManagedUserDTO() {
        super();

    }

    public CorManagedUserDTO(Long id, String login, String password, String firstName, String lastName,
                             String email, boolean activated, String imageUrl, String langKey,
                             String createdBy, ZonedDateTime createdDate, String lastModifiedBy, ZonedDateTime lastModifiedDate,
                             Set<String> authorities) {

        super(id, login, firstName, lastName, email, activated,  imageUrl, langKey,
            createdBy, createdDate, lastModifiedBy, lastModifiedDate,  authorities);

        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "ManagedUserVM{" +
            "} " + super.toString();
    }
}

