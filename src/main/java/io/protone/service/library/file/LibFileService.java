package io.protone.service.library.file;

/**
 * Created by lukaszozimek on 28/05/2017.
 */
public interface LibFileService {

    void saveFile();

    byte[] download(String networkShortcut, String libraryShortcut, String idx);

    void deleteFile();

}
