package io.protone.custom.service.mapper.ext;

import io.protone.custom.service.dto.CoreUserPT;

import io.protone.domain.User;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Generated;
import javax.inject.Inject;

import io.protone.repository.UserRepository;
import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2017-01-26T21:11:21+0100",

    comments = "version: 1.1.0.Final, compiler: javac, environment: Java 1.8.0_71 (Oracle Corporation)"

)

@Component

public class CustomCORUserMapperExt {

    @Inject
    private UserRepository userRepository;

    public CoreUserPT userToCoreUserPT(User user) {

        if ( user == null ) {

            return null;
        }

        CoreUserPT coreUserPT = new CoreUserPT();

        coreUserPT.setActivated( user.getActivated() );

        coreUserPT.setEmail( user.getEmail() );

        coreUserPT.setFirstName( user.getFirstName() );

        coreUserPT.setLangKey( user.getLangKey() );

        coreUserPT.setLastName( user.getLastName() );

        coreUserPT.setLogin( user.getLogin() );

        coreUserPT.setId( user.getId() );

        return coreUserPT;
    }

    public List<CoreUserPT> usersToCoreUserPTs(List<User> users) {

        if ( users == null ) {

            return null;
        }

        List<CoreUserPT> list = new ArrayList<CoreUserPT>();

        for ( User user : users ) {

            list.add( userToCoreUserPT( user ) );
        }

        return list;
    }

    public User coreUserPTToUser(CoreUserPT userPT) {

        if ( userPT == null ) {

            return null;
        }

        User user = userRepository.findOneByLogin(userPT.getLogin()).get();
        user.setFirstName( userPT.getFirstName() );
        user.setLastName( userPT.getLastName() );
        user.setEmail( userPT.getEmail() );

        if ( userPT.getActivated() != null ) {

            user.setActivated( userPT.getActivated() );
        }

        user.setLangKey( userPT.getLangKey() );

        return user;
    }

    public List<User> coreUserPTsToUsers(List<CoreUserPT> userPTs) {

        if ( userPTs == null ) {

            return null;
        }

        List<User> list = new ArrayList<User>();

        for ( CoreUserPT coreUserPT : userPTs ) {

            list.add( coreUserPTToUser( coreUserPT ) );
        }

        return list;
    }
}
