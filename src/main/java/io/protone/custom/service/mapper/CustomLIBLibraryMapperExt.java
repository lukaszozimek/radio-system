package io.protone.custom.service.mapper;

import io.protone.custom.consts.GKAssociationConstants;
import io.protone.custom.service.dto.LibraryPT;
import io.protone.domain.*;
import io.protone.domain.enumeration.LIBCounterTypeEnum;
import io.protone.domain.enumeration.LIBObjectTypeEnum;
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
    CustomCORUserMapperExt userMapper;

    @Inject
    CORChannelRepositoryEx channelRepository;

    @Inject
    CustomCORChannelMapper channelMapper;

    public LibraryPT DB2DTO(LIBLibrary libraryDB) {

        if ( libraryDB == null )
            return null;

        LibraryPT libraryDAO = new LibraryPT();
        libraryDAO.setNetworkId( libraryDB.getNetwork().getId());
        libraryDAO.setId( libraryDB.getId() );
        libraryDAO.setPrefix( libraryDB.getPrefix() );
        libraryDAO.setName( libraryDB.getName() );
        libraryDAO.setDescription( libraryDB.getDescription() );
        libraryDAO.setShortcut(libraryDB.getShortcut());
        libraryDAO.setPrefix(libraryDB.getPrefix());
        libraryDAO.setCounter(libraryDB.getCounter());
        libraryDAO.setCounterType(libraryDB.getCounterType().toString());
        libraryDAO.setLibraryType(libraryDB.getLibraryType().toString());
        libraryDB.setIdxLength(libraryDB.getIdxLength());

        //fill association between library & users
        List<CORAssociation> userAssociations = associationRepository.findByNameAndSourceIdAndTargetClass(GKAssociationConstants.GK_LIBRARY_HAS_USER, libraryDB.getId(), User.class.getSimpleName());
        for (CORAssociation a: userAssociations)
            libraryDAO.addUsersItem(userMapper.userToCoreUserPT(userRepository.findOne(a.getTargetId())));

        //fill association between library & channel
        List<CORAssociation> channelAssociations = associationRepository.findByNameAndSourceIdAndTargetClass(GKAssociationConstants.GK_LIBRARY_HAS_CHANNEL, libraryDB.getId(), CORChannel.class.getSimpleName());
        for (CORAssociation a: channelAssociations)
            libraryDAO.addChannelsItem(channelMapper.cORChannelToCORChannelDTO(channelRepository.findOne(a.getTargetId())));

        return libraryDAO;
    }

    public List<LibraryPT> DBs2DTOs(List<LIBLibrary> libraries) {

        if ( libraries == null )
            return null;

        List<LibraryPT> list = new ArrayList<LibraryPT>();
        for ( LIBLibrary lIBLibrary : libraries )
            list.add( DB2DTO( lIBLibrary ) );

        return list;
    }

    public LIBLibrary DTO2DB(LibraryPT libraryDAO) {

        if ( libraryDAO == null ) {

            return null;
        }

        LIBLibrary libraryDB = new LIBLibrary();
        libraryDB.setNetwork( mapCORNetwork(libraryDAO.getNetworkId()));
        libraryDB.setId( libraryDAO.getId() );
        libraryDB.setPrefix( libraryDAO.getPrefix() );
        libraryDB.setName( libraryDAO.getName() );
        libraryDB.setDescription( libraryDAO.getDescription() );
        libraryDB.setShortcut(libraryDAO.getShortcut());
        libraryDB.setPrefix(libraryDAO.getPrefix());
        libraryDB.setCounter(libraryDAO.getCounter());
        libraryDB.setCounterType(LIBCounterTypeEnum.valueOf(libraryDAO.getCounterType()));
        libraryDB.setLibraryType(LIBObjectTypeEnum.valueOf(libraryDAO.getLibraryType()));
        libraryDB.setIdxLength(libraryDAO.getIndexLength());

        return libraryDB;
    }

    public List<LIBLibrary> DTOs2DBs(List<LibraryPT> libraries) {

        if ( libraries == null )
            return null;

        List<LIBLibrary> list = new ArrayList<LIBLibrary>();
        for ( LibraryPT libraryPT : libraries )
            list.add( DTO2DB( libraryPT ) );

        return list;
    }

    private CORNetwork mapCORNetwork(Long id) {

        if (id == null)
            return null;

        CORNetwork result = new CORNetwork();
        result.setId(id);
        return result;
    }
}
