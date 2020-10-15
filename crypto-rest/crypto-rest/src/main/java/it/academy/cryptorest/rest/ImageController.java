package it.academy.cryptorest.rest;

import it.academy.cryptorest.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    @Autowired
    private StorageService storageService;

    @PostMapping("/images/{id}")
    public Byte[] post(
            @PathVariable String id,
            Byte[] avatarBytes
    ){
        //написать получение и запись аватара в storage
        return avatarBytes;
    }


}
