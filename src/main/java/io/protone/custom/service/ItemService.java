package io.protone.custom.service;

import io.protone.config.s3.S3Client;
import io.protone.config.s3.exceptions.*;
import io.protone.custom.consts.ServiceConstants;
import io.protone.custom.service.dto.LibItemPT;
import io.protone.custom.service.mapper.CustomItemMapperExt;
import io.protone.custom.utils.MediaUtils;
import io.protone.domain.*;
import io.protone.domain.enumeration.LibAudioQualityEnum;
import io.protone.domain.enumeration.LibItemStateEnum;
import io.protone.domain.enumeration.LibItemTypeEnum;
import io.protone.repository.*;
import io.protone.security.SecurityUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemService {

    @Inject
    S3Client s3Client;

    @Inject
    LibraryService libraryService;

    @Inject
    LibMediaItemRepositoryEx itemRepository;

    @Inject
    CustomItemMapperExt itemMapper;
    @Inject
    LibCloudObjectRepository cloudObjectRepository;
    @Inject
    MediaUtils mediaUtils;
    @Inject
    LibMediaItemRepositoryEx mediaItemRepository;
    @Inject
    LibAudioObjectRepositoryEx audioObjectRepository;
    @Inject
    private UserRepository userRepository;

    public LibItemPT getItem(String networkShortcut, String libraryShortcut, String idx) {
        LibItemPT result = null;
        LibLibrary libraryDB = libraryService.findLibrary(networkShortcut, libraryShortcut);
        if (libraryDB == null)
            return result;

        Optional<LibMediaItem> optionalItemDB = itemRepository.findByLibraryAndIdx(libraryDB, idx);
        result = itemMapper.DB2DTO(optionalItemDB.orElse(null));
        return result;
    }

    public List<LibItemPT> getItem(String networkShortcut, String libraryShortcut) {
        List<LibItemPT> results = new ArrayList<>();
        LibLibrary libraryDB = libraryService.findLibrary(networkShortcut, libraryShortcut);
        if (libraryDB == null)
            return results;

        List<LibMediaItem> itemsDB = itemRepository.findByLibrary(libraryDB);
        results = itemMapper.DBs2DTOs(itemsDB);
        return results;
    }

    @Transactional
    public void deleteItem(String networkShortcut, String libraryShortcut, String idx) throws MediaResourceException {

        LibMediaItem itemToDelete = getItemFromDB(networkShortcut, libraryShortcut, idx);
        if (itemToDelete != null) {

            List<LibAudioObject> audioObjects = audioObjectRepository.findByMediaItem(itemToDelete);
            if (audioObjects != null || audioObjects.size() > 0) {
                for (LibAudioObject audioObject : audioObjects) {
                    LibCloudObject cloudObject = audioObject.getCloudObject();
                    try {
                        s3Client.delete(cloudObject.getUuid());
                        audioObjectRepository.delete(audioObject);
                        audioObjectRepository.flush();
                        cloudObjectRepository.delete(cloudObject);
                        cloudObjectRepository.flush();
                    } catch (DeleteException e) {
                        throw new MediaResourceException(e.getMessage());
                    } catch (S3Exception e) {
                        throw new MediaResourceException(e.getMessage());
                    }
                }
            }
            itemRepository.delete(itemToDelete);
        }

    }

    public List<LibItemPT> upload(String networkShortcut, String libraryShortcut, MultipartFile[] files) throws IOException, MediaResourceException {

        List<LibItemPT> result = new ArrayList<>();

        if (files == null || files.length == 0)
            return result;

        LibLibrary libraryDB = libraryService.findLibrary(networkShortcut, libraryShortcut);
        if (libraryDB == null)
            return result;

        for (MultipartFile file : files) {

            String fileName = file.getOriginalFilename();

            //FIX THIS ASAP!!!
            //Path filePath = Paths.get(String.format("%s%s", File.separator, fileName));
            //String contentType = Files.probeContentType(filePath);
            String contentType = "application/octet-stream";

            String fileUUID = UUID.randomUUID().toString();

            ByteArrayInputStream bais = null;
            bais = new ByteArrayInputStream(file.getBytes());

            try {
                s3Client.upload(fileUUID, bais, contentType);

                LibCloudObject cloudObject = new LibCloudObject();

                cloudObject.setUuid(fileUUID);
                cloudObject.setContentType(contentType);
                cloudObject.setOriginalName(fileName);
                cloudObject.setOriginal(Boolean.TRUE);
                cloudObject.setSize(file.getSize());
                cloudObject.setCreateDate(ZonedDateTime.now());

                //User currentUser = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
                //TODO fix current user addition to argument
                cloudObject.setCreatedBy(null);

                cloudObject.setNetwork(libraryDB.getNetwork());
                cloudObject.setHash(ServiceConstants.NO_HASH);

                cloudObject = cloudObjectRepository.saveAndFlush(cloudObject);

                LibMediaItem mediaItem = new LibMediaItem();
                mediaItem.setItemType(LibItemTypeEnum.IT_AUDIO);
                mediaItem.setName(ServiceConstants.NO_DATA);
                mediaItem.setIdx(mediaUtils.generateIdx(libraryDB));
                mediaItem.setLength(-1L);
                mediaItem.setState(LibItemStateEnum.IS_NEW);
                mediaItem.setLibrary(libraryDB);

                mediaItemRepository.saveAndFlush(mediaItem);

                LibAudioObject audioObject = new LibAudioObject();
                audioObject.setBiTrate(-1);
                audioObject.setCloudObject(cloudObject);
                audioObject.setMediaItem(mediaItem);
                audioObject.setQuality(LibAudioQualityEnum.AQ_ORIGINAL);
                audioObject.setLength(-1L);
                audioObject.setCodec(ServiceConstants.NO_DATA);

                audioObjectRepository.saveAndFlush(audioObject);

                result.add(itemMapper.DB2DTO(mediaItem));
            } catch (UploadException e) {
                throw new MediaResourceException(e.getMessage());
            } catch (S3Exception e) {
                throw new MediaResourceException(e.getMessage());
            } finally {
                bais.close();
            }
        }
        return result;
    }

    public byte[] download(String networkShortcut, String libraryShortcut, String idx) throws DownloadException, IOException, MediaResourceException {

        byte[] result = null;

        LibMediaItem itemDB = getItemFromDB(networkShortcut, libraryShortcut, idx);

        if (itemDB == null)
            return result;

        List<LibAudioObject> audioObjects = audioObjectRepository.findByMediaItem(itemDB);

        if (audioObjects == null || audioObjects.size() == 0)
            return result;

        LibAudioObject audioObject = audioObjects.iterator().next();

        LibCloudObject cloudObject = audioObject.getCloudObject();

        InputStream stream = null;
        try {
            stream = s3Client.download(cloudObject.getUuid());

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("content-disposition", "filename=" + cloudObject.getOriginalName());
            responseHeaders.add("Content-Length", String.format("%d", cloudObject.getSize()));

            // TODO: usunac hardcode , brac dane z pola stream.contentType
            responseHeaders.add("Content-Type", "audio/mpeg");

            result = IOUtils.toByteArray(stream);
        } catch (DownloadException e) {
            throw new MediaResourceException(e.getMessage());
        } catch (S3Exception e) {
            throw new MediaResourceException(e.getMessage());
        } finally {
            stream.close();
        }
        return result;
    }

    public LibMediaItem getItemFromDB(String networkShortcut, String libraryShortcut, String idx) {
        LibMediaItem result = null;
        LibLibrary libraryDB = libraryService.findLibrary(networkShortcut, libraryShortcut);
        if (libraryDB == null)
            return result;

        Optional<LibMediaItem> optItemDB = itemRepository.findByLibraryAndIdx(libraryDB, idx);

        return optItemDB.orElse(null);
    }
}
