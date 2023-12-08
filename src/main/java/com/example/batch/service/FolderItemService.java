package com.example.batch.service;

import com.example.batch.model.*;
import com.example.batch.repoistory.ItemRepository;
import com.example.batch.repoistory.PermissionGroupRepository;
import com.example.batch.repoistory.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class FolderItemService {

    @Autowired
    private PermissionGroupRepository permissionGroupRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private ItemRepository itemRepository;



        public Item createFolder(String spaceName, String folderName, String userEmail,String groupName)  {
            // Check if the user has the required permission for the space
            PermissionGroup group = permissionGroupRepository.findByGroupName(groupName);

            if (group == null) {
                throw new EntityNotFoundException("Permission group not found for space: " + spaceName);
            }

            Permission userPermission = permissionRepository.findByUserEmailAndGroup(userEmail,group);

            if (userPermission == null || userPermission.getPermissionLevel() != PermissionLevel.EDIT) {
                throw new RuntimeException();
            }

           //  Create Folder
            Item folder =Item.builder()
                    .name(folderName)
                    .type(ItemType.FOLDER)
                    .permissionGroup(group)
                    .build();

            // Save Folder
            Item savedFolder = itemRepository.save(folder);

            return savedFolder;
        }
    }



