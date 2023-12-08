package com.example.batch.service;

import com.example.batch.model.*;
import com.example.batch.repoistory.ItemRepository;
import com.example.batch.repoistory.PermissionGroupRepository;
import com.example.batch.repoistory.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpaceItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PermissionGroupRepository permissionGroupRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    public Item createSpace(String spaceName, String groupName) {
        // Create Space
        Item space = Item.builder()
                .name(spaceName)
                .type(ItemType.SPACE)
                .build();
        // Create Permission Group
        PermissionGroup group = PermissionGroup.builder()
                .groupName(groupName)
                .build();
        PermissionGroup savedGroup = permissionGroupRepository.save(group);

        // Assign Permission Group to Space
        space.setPermissionGroup(savedGroup);

        // Save Space
        Item savedSpace = itemRepository.save(space);

        // Create and Assign Permissions (VIEW and EDIT) to the group
        createPermissions(savedGroup, "user1@example.com", PermissionLevel.VIEW);
        createPermissions(savedGroup, "user2@example.com", PermissionLevel.EDIT);

        return savedSpace;
    }

    private void createPermissions(PermissionGroup group, String userEmail, PermissionLevel permissionLevel) {
        Permission permission= Permission.builder()
                .userEmail(userEmail)
                .permissionLevel(permissionLevel)
                .group(group)
                .build();
        permissionRepository.save(permission);
    }

}

