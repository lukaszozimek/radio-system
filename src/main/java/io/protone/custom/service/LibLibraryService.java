package io.protone.custom.service;

import io.protone.custom.service.dto.LibraryPT;
import io.protone.custom.service.mapper.CustomCORChannelMapper;
import io.protone.custom.service.mapper.CustomCorUserMapperExt;
import io.protone.custom.service.mapper.CustomLibLibraryMapperExt;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibLibrary;
import io.protone.repository.custom.CustomCorNetworkRepository;
import io.protone.repository.custom.CustomLibLibraryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LibLibraryService {

    @Inject
    private CustomCorNetworkRepository networkRepository;

    @Inject
    private CustomLibLibraryRepository libraryRepository;

    @Inject
    private CustomLibLibraryMapperExt customLibLibraryMapper;

    @Inject
    private CustomCORChannelMapper customCorChannelMapper;

    @Inject
    private CustomCorUserMapperExt userMapper;


    public List<LibLibrary> findLibrary(String networkShortcut) {
        List<LibLibrary> results = new ArrayList<>();
        CorNetwork network = getNetworkByShortcut(networkShortcut);
        if (network != null)
            results = libraryRepository.findByNetwork(network);
        return results;
    }

    public LibLibrary findLibrary(String networkShortcut, String libraryShortcut) {
        LibLibrary result = null;
        CorNetwork network = getNetworkByShortcut(networkShortcut);
        if (network != null)
            result = libraryRepository.findOneByNetworkAndShortcut(network, libraryShortcut);
        return result;
    }

    public void deleteLibrary(String networkShortcut, String libraryShortcut) {
        LibLibrary libraryToDelete = findLibrary(networkShortcut, libraryShortcut);
        if (libraryToDelete != null) {
            libraryRepository.delete(libraryToDelete);
            libraryRepository.flush();
        }
    }

    public LibLibrary createOrUpdateLibrary(String networkShortcut, LibraryPT library) {

        CorNetwork network = getNetworkByShortcut(networkShortcut);
        LibLibrary result = customLibLibraryMapper.DTO2DB(library);
        result.setNetwork(network);
        result = libraryRepository.saveAndFlush(result);

        return result;
    }

    private CorNetwork getNetworkByShortcut(String networkShortcut) {
        return networkRepository.findOneByShortcut(networkShortcut);
    }


}
