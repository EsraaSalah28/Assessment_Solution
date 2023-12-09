package com.example.batch.service;

import com.example.batch.model.*;
import com.example.batch.repoistory.FileRepository;
import com.example.batch.repoistory.ItemRepository;
import com.example.batch.repoistory.PermissionGroupRepository;
import com.example.batch.repoistory.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;

@Service

public class FileItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PermissionGroupRepository permissionGroupRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private FileRepository fileRepository;

    public Item createFile(String folderName, String fileName, String userEmail, String groupName, byte[] fileData) throws AccessDeniedException {
        // Check if the user has the required permission for the folder
        PermissionGroup group = getPermissionGroup(folderName, userEmail, groupName);

        // Create File
        Item file = Item.builder()
                .name(fileName)
                .type(ItemType.FILE)
                .permissionGroup(group)
                .build();

        Item fileItem = itemRepository.save(file);

        File fileObj = File.builder()
                .item(file)
                .binaryData(fileData)
                .build();
        fileRepository.save(fileObj);

        return fileItem;
    }


    public Item viewFileMetadata(String fileName, String userEmail) {

        Item fileMetadata = itemRepository.viewFileMetadata(fileName, userEmail);

        if (fileMetadata == null) {
            throw new EntityNotFoundException("File not found or user does not have access.");
        }

        return fileMetadata;
    }


    public byte[] downloadFile(Long fileId, String userEmail) throws AccessDeniedException, FileNotFoundException {

        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found"));

        // Check user's access to the file
        checkUserAccess(file, userEmail);

        return file.getBinaryData();
    }

    private void checkUserAccess(File file, String userEmail) throws AccessDeniedException {

        PermissionGroup group = file.getItem().getPermissionGroup();
        Permission userPermission = permissionRepository.findByUserEmailAndGroup(userEmail, group);

        if (userPermission == null ) {
            throw new AccessDeniedException("User does not have the required VIEW permission for the file.");
        }
    }

    private PermissionGroup getPermissionGroup(String folderName, String userEmail, String groupName) throws AccessDeniedException {
        PermissionGroup group = permissionGroupRepository.findByGroupName(groupName);

        if (group == null) {
            throw new EntityNotFoundException("Permission group not found for folder: " + folderName);
        }

        getUserPermission(userEmail, group);
        return group;
    }

    private void getUserPermission(String userEmail, PermissionGroup group) throws AccessDeniedException {
        Permission userPermission = permissionRepository.findByUserEmailAndGroup(userEmail, group);

        if (userPermission == null || userPermission.getPermissionLevel() != PermissionLevel.EDIT) {
            throw new AccessDeniedException("User does not have the required EDIT permission for the folder.");
        }
    }

}
