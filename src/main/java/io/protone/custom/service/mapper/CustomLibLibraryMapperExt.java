package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.LibraryPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibLibrary;
import io.protone.domain.enumeration.LibCounterTypeEnum;
import io.protone.domain.enumeration.LibObjectTypeEnum;
import io.protone.repository.CorChannelRepository;
import io.protone.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomLibLibraryMapperExt {

    @Inject
    private UserRepository userRepository;

    @Inject
    private CustomCorUserMapperExt userMapper;

    @Inject
    private CorChannelRepository channelRepository;

    @Inject
    private CustomCORChannelMapper channelMapper;

    public LibraryPT DB2DTO(LibLibrary libraryDB) {

        if (libraryDB == null)
            return null;

        LibraryPT libraryDAO = new LibraryPT();
        libraryDAO.setNetworkId(libraryDB.getNetwork().getId());
        libraryDAO.setId(libraryDB.getId());
        libraryDAO.setPrefix(libraryDB.getPrefix());
        libraryDAO.setName(libraryDB.getName());
        libraryDAO.setDescription(libraryDB.getDescription());
        libraryDAO.setShortcut(libraryDB.getShortcut());
        libraryDAO.setPrefix(libraryDB.getPrefix());
        libraryDAO.setCounter(libraryDB.getCounter());
        libraryDAO.setCounterType(libraryDB.getCounterType().toString());
        libraryDAO.setLibraryType(libraryDB.getLibraryType().toString());
        libraryDAO.setIndexLength(libraryDB.getIdxLength());
        libraryDAO.setUsers(new ArrayList<>());
        libraryDAO.setChannels(new ArrayList<>());
        return libraryDAO;
    }

    public List<LibraryPT> DBs2DTOs(List<LibLibrary> libraries) {

        if (libraries == null)
            return null;

        List<LibraryPT> list = new ArrayList<LibraryPT>();
        for (LibLibrary lIBLibrary : libraries)
            list.add(DB2DTO(lIBLibrary));

        return list;
    }

    public LibLibrary DTO2DB(LibraryPT libraryDAO) {

        if (libraryDAO == null) {

            return null;
        }

        LibLibrary libraryDB = new LibLibrary();
        libraryDB.setNetwork(mapCorNetwork(libraryDAO.getNetworkId()));
        libraryDB.setId(libraryDAO.getId());
        libraryDB.setPrefix(libraryDAO.getPrefix());
        libraryDB.setName(libraryDAO.getName());
        libraryDB.setDescription(libraryDAO.getDescription());
        libraryDB.setShortcut(libraryDAO.getShortcut());
        libraryDB.setPrefix(libraryDAO.getPrefix());
        libraryDB.setCounter(libraryDAO.getCounter());
        libraryDB.setCounterType(LibCounterTypeEnum.valueOf(libraryDAO.getCounterType()));
        libraryDB.setLibraryType(LibObjectTypeEnum.valueOf(libraryDAO.getLibraryType()));
        libraryDB.setIdxLength(libraryDAO.getIndexLength());

        return libraryDB;
    }

    public List<LibLibrary> DTOs2DBs(List<LibraryPT> libraries) {

        if (libraries == null)
            return null;

        List<LibLibrary> list = new ArrayList<LibLibrary>();
        for (LibraryPT libraryPT : libraries)
            list.add(DTO2DB(libraryPT));

        return list;
    }

    private CorNetwork mapCorNetwork(Long id) {

        if (id == null)
            return null;

        CorNetwork result = new CorNetwork();
        result.setId(id);
        return result;
    }
}
