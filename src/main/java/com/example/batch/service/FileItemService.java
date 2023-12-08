package com.example.batch.service;

import com.example.batch.model.*;
import com.example.batch.repoistory.ItemRepository;
import com.example.batch.repoistory.PermissionGroupRepository;
import com.example.batch.repoistory.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;

@Service

public class FileItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PermissionGroupRepository permissionGroupRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    public Item createFile(String folderName, String fileName, String userEmail,String groupName) throws AccessDeniedException {
        // Check if the user has the required permission for the folder
        PermissionGroup group = permissionGroupRepository.findByGroupName(groupName);

        if (group == null) {
            throw new EntityNotFoundException("Permission group not found for folder: " + folderName);
        }

        Permission userPermission = permissionRepository.findByUserEmailAndGroup(userEmail, group);

        if (userPermission == null || userPermission.getPermissionLevel() != PermissionLevel.EDIT) {
            throw new AccessDeniedException("User does not have the required EDIT permission for the folder.");
        }

        // Create File
        Item file = Item.builder()
                        .name(fileName)
                        .type(ItemType.FILE)
                        .permissionGroup(group)
                .build();


        // Save File
        return itemRepository.save(file);



    }
}
