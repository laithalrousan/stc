package com.stc.stc.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stc.stc.entities.Files;
import com.stc.stc.entities.Item;
import com.stc.stc.entities.ResponseFile;
import com.stc.stc.exceptions.HttpClientErrorException;
import com.stc.stc.exceptions.UnauthorizedException;
import com.stc.stc.models.ItemModel;
import com.stc.stc.services.UploadDownloadService;
import com.stc.stc.utils.PermissionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable int id) {
        Files fileDB = uploadDownloadService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getItem().getName() + "\"")
                .body(fileDB.getData());
    }
    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = uploadDownloadService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId().toString())
                    .toUriString();

            return new ResponseFile(
                    fileDownloadUri);
        }).collect(Collectors.toList());

        return new ResponseEntity<List<ResponseFile>>(files, HttpStatus.CREATED);
    }
    protected boolean isAuthorized(ItemModel item, PermissionLevel level) {

        return uploadDownloadService.checkPermission(item, level.getValue());


    }

    protected boolean isParentExist(ItemModel item) {

        return uploadDownloadService.checkParentExist(item.getItem().getParent().getId());


    }

}
