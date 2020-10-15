package it.academy.cryptorest.service;

import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

@Service
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StorageService {

    private static final Logger log = Logger.getLogger(StorageService.class.getName());

    static final String STORAGE_DIRECTORY =
            System.getProperty("user.dir") +
            System.lineSeparator()+
            "storage"+
            System.lineSeparator();


    public void saveFile(MultipartFile file, String id) {

        try {
            byte[] bytes = file.getBytes();
            String name = file.getOriginalFilename();
            String path = getUserStoragePath(id);
            saveToDisc(bytes,path,name);
        } catch (IOException e) {
            log.info("can't read or save file: "+e.getMessage());
        }
    }


    private void saveToDisc(byte[] bytes, String path, String name) throws IOException {
        File dir = mkdir(path);
        File saveFile = new File(dir,name);
        try (FileOutputStream fileOutputStream = new FileOutputStream(saveFile)) {
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
        }

    }





    private File mkdir(String dir) {
        File file = new File(dir);
        if (!file.exists()) file.mkdirs();
        return file;
    }


    private String getUserStoragePath(String id){
        return STORAGE_DIRECTORY+ id;
    }

}
