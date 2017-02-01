package io.protone.custom.service;

import io.protone.custom.consts.GKAssociationConstants;
import io.protone.custom.service.dto.LibraryPT;
import io.protone.custom.service.mapper.CustomCORChannelMapper;
import io.protone.custom.service.mapper.CustomCORUserMapperExt;
import io.protone.custom.service.mapper.CustomLIBLibraryMapperExt;
import io.protone.domain.*;
import io.protone.repository.CCORNetworkRepository;
import io.protone.repository.CORAssociationRepository;
import io.protone.repository.LIBLibraryRepositoryEx;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryService {

    @Inject
    private CCORNetworkRepository networkRepository;

    @Inject
    private LIBLibraryRepositoryEx libraryRepository;

    @Inject
    CustomLIBLibraryMapperExt customLIBLibraryMapper;

    @Inject
    CustomCORChannelMapper customCORChannelMapper;

    @Inject
    CustomCORUserMapperExt userMapper;

    @Inject
    CORAssociationRepository associationRepository;

    public List<LIBLibrary> findLibrary(String networkShortcut) {
        List<LIBLibrary> results = new ArrayList<>();
        CORNetwork network = getNetworkByShortcut(networkShortcut);
        if (network != null)
            results = libraryRepository.findByNetwork(network);
        return results;
    }

    public LIBLibrary findLibrary(String networkShortcut, String libraryShortcut) {
        LIBLibrary result = null;
        CORNetwork network = getNetworkByShortcut(networkShortcut);
        if (network != null)
            result = libraryRepository.findOneByNetworkAndShortcut(network, libraryShortcut).get();
        return result;
    }

    @Transactional
    public void deleteLibrary(String networkShortcut, String libraryShortcut) {
        LIBLibrary libraryToDelete = findLibrary(networkShortcut, libraryShortcut);
        if (libraryToDelete != null) {
            deleteAssociations(libraryToDelete);
            libraryRepository.delete(libraryToDelete);
            libraryRepository.flush();
        }
    }

    @Transactional
    public LIBLibrary createOrUpdateLibrary(String networkShortcut, LibraryPT library) {

        CORNetwork network = getNetworkByShortcut(networkShortcut);
        LIBLibrary result = customLIBLibraryMapper.DTO2DB(library);
        result.setNetwork(network);
        result = libraryRepository.saveAndFlush(result);

        deleteAndCreateAssociations(library, result);

        return result;
    }

    private CORNetwork getNetworkByShortcut(String networkShortcut) {
        return networkRepository.findByShortcut(networkShortcut);
    }

    @Transactional
    private void deleteAndCreateAssociations(LibraryPT libraryDAO, LIBLibrary libraryDB) {

        deleteAssociations(libraryDB);

        List<CORChannel> channelsDB = customCORChannelMapper.cORChannelDTOsToCORChannels(libraryDAO.getChannels());
        for (CORChannel channel: channelsDB) {
            CORAssociation association = new CORAssociation()
                .name(GKAssociationConstants.GK_LIBRARY_HAS_CHANNEL)
                .network(libraryDB.getNetwork())
                .sourceClass(LIBLibrary.class.getSimpleName())
                .sourceId(libraryDB.getId())
                .targetClass(CORChannel.class.getSimpleName())
                .targetId(channel.getId());
            associationRepository.saveAndFlush(association);
        }

        List<User> usersDB = userMapper.coreUserPTsToUsers(libraryDAO.getUsers());
        for (User user: usersDB) {
            CORAssociation association = new CORAssociation()
                .name(GKAssociationConstants.GK_LIBRARY_HAS_USER)
                .network(libraryDB.getNetwork())
                .sourceClass(LIBLibrary.class.getSimpleName())
                .sourceId(libraryDB.getId())
                .targetClass(User.class.getSimpleName())
                .targetId(user.getId());
            associationRepository.saveAndFlush(association);
        }

        associationRepository.flush();
    }

    @Transactional
    private void deleteAssociations(LIBLibrary libraryDB) {
        List<CORAssociation> associations = associationRepository.findBySourceClassAndSourceId(LIBLibrary.class.getSimpleName(), libraryDB.getId());
        associationRepository.delete(associations);
        associationRepository.flush();
    }


}
