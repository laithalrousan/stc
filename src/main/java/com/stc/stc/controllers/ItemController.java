package com.stc.stc.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stc.stc.entities.Files;
import com.stc.stc.entities.Item;
import com.stc.stc.exceptions.HttpClientErrorException;
import com.stc.stc.exceptions.UnauthorizedException;
import com.stc.stc.models.ItemModel;
import com.stc.stc.services.UploadDownloadService;
import com.stc.stc.utils.PermissionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.Map;

@RestController
public class ItemController {

    private final ObjectMapper objectMapper;

    public ItemController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @Autowired
    UploadDownloadService uploadDownloadService;

    @PostMapping("/items")
    public ResponseEntity<?> createFolder(@RequestBody ItemModel item) {
        if (!item.getItem().getType().equalsIgnoreCase("space") && !isAuthorized(item, PermissionLevel.EDIT)) {
            throw new UnauthorizedException("Unauthorized access");
        } else if (!item.getItem().getType().equalsIgnoreCase("space") && !isParentExist(item)) {
            throw new HttpClientErrorException("Parent Does not exist");
        }


        Item folder = uploadDownloadService.creatFolder(item);

        return new ResponseEntity<Item>(folder, HttpStatus.CREATED);
    }
    @RequestMapping(value = "/upload" , method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file , @RequestParam("item") String itema) throws JsonProcessingException {
        ItemModel item=objectMapper.readValue(itema, ItemModel.class);
        if (!item.getItem().getType().equalsIgnoreCase("space") && !isAuthorized(item, PermissionLevel.EDIT)) {
            throw new UnauthorizedException("Unauthorized access");
        } else if (!item.getItem().getType().equalsIgnoreCase("space") && !isParentExist(item)) {
            throw new HttpClientErrorException("Parent Does not exist");
        }



        Files filed =uploadDownloadService.createFile(item,file);
        return new ResponseEntity<Item>(item.getItem(), HttpStatus.CREATED);
    }

    protected boolean isAuthorized(ItemModel item, PermissionLevel level) {

        return uploadDownloadService.checkPermission(item, level.getValue());


    }

    protected boolean isParentExist(ItemModel item) {

        return uploadDownloadService.checkParentExist(item.getItem().getParent().getId());


    }

}
