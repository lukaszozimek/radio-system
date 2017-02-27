package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.LibItemPT;
import io.protone.domain.LibMediaItem;
import io.protone.domain.enumeration.LibItemStateEnum;
import io.protone.domain.enumeration.LibItemTypeEnum;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Component
public class CustomItemMapperExt {

    @Inject
    private CustomLibAlbumMapper albumMapper;

    @Inject
    private CustomLibArtistMapper artistMapper;

    @Inject
    private CustomCorPersonMapperExt personMapper;

    @Inject
    private CustomLibLabelMapperExt labelMapper;

    @Inject
    private CustomLibLibraryMapperExt libraryMapper;

    @Inject
    private CustomLibMarkerMapperExt markerMapper;

    @Inject
    private CustomLibTrackMapperExt trackMapper;

    public LibItemPT DB2DTO(LibMediaItem db) {

        if (db == null)
            return null;

        LibItemPT dto = new LibItemPT()
            .idx(db.getIdx())
            .length(db.getLength().intValue())
            .name(db.getName())
            .resourceType(mapResourceType(db.getItemType()))
            .state(mapState(db.getState()))
            .label(labelMapper.DB2DTO(db.getLabel()))
            .album(albumMapper.DB2DTO(db.getAlbum()))
            .artist(artistMapper.DB2DTO(db.getArtist()))
            .library(libraryMapper.DB2DTO(db.getLibrary()))
            .track(trackMapper.DB2DTO(db.getTrack()))
            .author(db.getAuthors().stream().map(corPerson -> personMapper.DB2DTO(corPerson)).collect(toList()))
            .composer(db.getComposers().stream().map(corPerson -> personMapper.DB2DTO(corPerson)).collect(toList()))
            .markers(db.getMarkers().stream().map(libMarker -> markerMapper.DB2DTO(libMarker)).collect(toList()));
        return dto;
    }

    private LibItemPT.StateEnum mapState(LibItemStateEnum state) {

        if (state.compareTo(LibItemStateEnum.IS_ARCHIVED) == 0)
            return LibItemPT.StateEnum.ARCHIVED;
        else if (state.compareTo(LibItemStateEnum.IS_DELETED) == 0)
            return LibItemPT.StateEnum.DELETED;
        else if (state.compareTo(LibItemStateEnum.IS_DISABLED) == 0)
            return LibItemPT.StateEnum.DISABLED;
        else if (state.compareTo(LibItemStateEnum.IS_ENABLED) == 0)
            return LibItemPT.StateEnum.ENABLED;
        else if (state.compareTo(LibItemStateEnum.IS_ERROR) == 0)
            return LibItemPT.StateEnum.ERROR;
        else if (state.compareTo(LibItemStateEnum.IS_NEW) == 0)
            return LibItemPT.StateEnum.NEW;
        else if (state.compareTo(LibItemStateEnum.IS_POSTPROCESS) == 0)
            return LibItemPT.StateEnum.POSTPROCESS;
        else
            return LibItemPT.StateEnum.OTHER;
    }

    private LibItemPT.ResourceTypeEnum mapResourceType(LibItemTypeEnum itemType) {

        if (itemType.compareTo(LibItemTypeEnum.IT_AUDIO) == 0)
            return LibItemPT.ResourceTypeEnum.AUDIO;
        else if (itemType.compareTo(LibItemTypeEnum.IT_VIDEO) == 0)
            return LibItemPT.ResourceTypeEnum.VIDEO;
        else if (itemType.compareTo(LibItemTypeEnum.IT_COMMAND) == 0)
            return LibItemPT.ResourceTypeEnum.COMMAND;
        else
            return LibItemPT.ResourceTypeEnum.OTHER;
    }


    public List<LibItemPT> DBs2DTOs(List<LibMediaItem> dbs) {

        if (dbs == null)
            return null;

        List<LibItemPT> dtos = new ArrayList<>();
        for (LibMediaItem item : dbs)
            dtos.add(DB2DTO(item));

        return dtos;
    }

    public LibMediaItem DTO2DB(LibItemPT dto) {

        if (dto == null)
            return null;

        LibMediaItem db = new LibMediaItem()
            .idx(dto.getIdx())
            .length(dto.getLength().longValue())
            .name(dto.getName())
            .itemType(mapItemType(dto.getResourceType()))
            .state(LibItemStateEnum.valueOf(dto.getState().toString()))
            .label(labelMapper.DTO2DB(dto.getLabel()))
            .album(albumMapper.DTO2DB(dto.getAlbum()))
            .artist(artistMapper.DTO2DB(dto.getArtist()))
            .library(libraryMapper.DTO2DB(dto.getLibrary()))
            .track(trackMapper.DTO2DB(dto.getTrack()))
            .authors(dto.getAuthors().stream().map(corPerson -> personMapper.DTO2DB(corPerson)).collect(toSet()))
            .composers(dto.getComposers().stream().map(corPerson -> personMapper.DTO2DB(corPerson)).collect(toSet()))
            .markers(dto.getMarkers().stream().map(libMarkerPT -> markerMapper.DTO2DB(libMarkerPT)).collect(toSet()));
        return db;
    }

    public LibItemTypeEnum mapItemType(LibItemPT.ResourceTypeEnum type) {

        if (type.compareTo(LibItemPT.ResourceTypeEnum.AUDIO) == 0)
            return LibItemTypeEnum.IT_AUDIO;
        else if (type.compareTo(LibItemPT.ResourceTypeEnum.VIDEO) == 0)
            return LibItemTypeEnum.IT_VIDEO;
        else if (type.compareTo(LibItemPT.ResourceTypeEnum.COMMAND) == 0)
            return LibItemTypeEnum.IT_COMMAND;
        else
            return LibItemTypeEnum.IT_OTHER;
    }

    public List<LibMediaItem> DTOs2DBs(List<LibItemPT> dtos) {

        if (dtos == null)
            return null;

        List<LibMediaItem> dbs = new ArrayList<>();
        for (LibItemPT item : dtos)
            dbs.add(DTO2DB(item));

        return dbs;
    }
}
