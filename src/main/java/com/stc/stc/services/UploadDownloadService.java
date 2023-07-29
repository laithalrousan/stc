package com.stc.stc.services;

import com.stc.stc.entities.Files;
import com.stc.stc.entities.Item;
import com.stc.stc.entities.Permission;
import com.stc.stc.models.ItemModel;
import com.stc.stc.repository.FileRepository;
import com.stc.stc.repository.PermissionRepository;
import com.stc.stc.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class UploadDownloadService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    FileRepository fileRepository;
    @Autowired
    PermissionRepository permissionRepository;

    public Item creatSpace(Item item) {

        Item savedItem = itemRepository.save(item);
        return (savedItem);


    }

    public Files createFile(ItemModel itemModel, MultipartFile file){
        Item savedItem = itemRepository.save(itemModel.getItem());
        try {
            Files FileDB = new Files();
            FileDB.setData(file.getBytes());
            FileDB.setItem(itemModel.getItem());
            Files savedFile = fileRepository.save(FileDB);
            return savedFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Item creatFolder(ItemModel itemModel) {

        Item savedItem = itemRepository.save(itemModel.getItem());
        return (savedItem);


    }
    public Files getFile(int id) {
        return fileRepository.findById(id).get();
    }

    public Stream<Files> getAllFiles() {
        return fileRepository.findAll().stream();
    }

    public boolean checkPermission(ItemModel item, int level) {
        Optional<Permission> optionalUser =
                permissionRepository.findByPermissionGroupAndUserEmailAndLevel(
                        item.getItem().getPermissionGroup(),
                        item.getEmail(),
                        level);

        return optionalUser.isPresent();


    }


    public boolean checkParentExist(int parentId) {
       Optional<Item> item= itemRepository.findById(parentId);
        return item.isPresent();
    }
}
