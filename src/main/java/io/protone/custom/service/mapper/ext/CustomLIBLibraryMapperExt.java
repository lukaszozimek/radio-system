package io.protone.custom.service.mapper.ext;

import io.protone.custom.consts.GKAssociationConstants;
import io.protone.custom.service.dto.LibraryPT;
import io.protone.custom.service.mapper.CustomCORChannelMapper;
import io.protone.custom.service.mapper.CustomCORUserMapper;
import io.protone.domain.*;
import io.protone.repository.CORAssociationRepository;
import io.protone.repository.CORChannelRepositoryEx;
import io.protone.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomLIBLibraryMapperExt {

    @Inject
    CORAssociationRepository associationRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    CustomCORUserMapper userMapper;

    @Inject
    CORChannelRepositoryEx channelRepository;

    @Inject
    CustomCORChannelMapper channelMapper;

    public LibraryPT libLibrary2LibraryPT(LIBLibrary library) {

        if ( library == null )
            return null;

        LibraryPT libraryPT = new LibraryPT();
        libraryPT.setNetworkId( library.getNetwork().getId());
        libraryPT.setId( library.getId() );
        libraryPT.setPrefix( library.getPrefix() );
        libraryPT.setName( library.getName() );
        libraryPT.setDescription( library.getDescription() );

        //fill association between library & users
        List<CORAssociation> userAssociations = associationRepository.findByNameAndSourceIdAndTargetClass(GKAssociationConstants.GK_LIBRARY_HAS_USER, library.getId(), User.class.getSimpleName());
        for (CORAssociation a: userAssociations)
            libraryPT.addUsersItem(userMapper.userToCoreUserPT(userRepository.findOne(a.getTargetId())));

        //fill association between library & channel
        List<CORAssociation> channelAssociations = associationRepository.findByNameAndSourceIdAndTargetClass(GKAssociationConstants.GK_LIBRARY_HAS_CHANNEL, library.getId(), CORChannel.class.getSimpleName());
        for (CORAssociation a: channelAssociations)
            libraryPT.addChannelsItem(channelMapper.cORChannelToCORChannelDTO(channelRepository.findOne(a.getTargetId())));

        return libraryPT;
    }

    public List<LibraryPT> libLibraries2LibraryPTs(List<LIBLibrary> libraries) {

        if ( libraries == null )
            return null;

        List<LibraryPT> list = new ArrayList<LibraryPT>();
        for ( LIBLibrary lIBLibrary : libraries )
            list.add( libLibrary2LibraryPT( lIBLibrary ) );

        return list;
    }

    public LIBLibrary libLibraryPTToLIBLibrary(LibraryPT library) {

        if ( library == null ) {

            return null;
        }

        LIBLibrary lIBLibrary = new LIBLibrary();
        lIBLibrary.setNetwork( networkFromId(library.getNetworkId()));
        lIBLibrary.setId( library.getId() );
        lIBLibrary.setPrefix( library.getPrefix() );
        lIBLibrary.setName( library.getName() );
        lIBLibrary.setDescription( library.getDescription() );

        return lIBLibrary;
    }

    public List<LIBLibrary> libLibraryPTsToLibraries(List<LibraryPT> libraries) {

        if ( libraries == null )
            return null;

        List<LIBLibrary> list = new ArrayList<LIBLibrary>();
        for ( LibraryPT libraryPT : libraries )
            list.add( libLibraryPTToLIBLibrary( libraryPT ) );

        return list;
    }

    private CORNetwork networkFromId(Long id) {

        if (id == null)
            return null;

        CORNetwork result = new CORNetwork();
        result.setId(id);
        return result;
    }
}
