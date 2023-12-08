package com.example.batch.controller;

import com.example.batch.model.Item;
import com.example.batch.service.FolderItemService;
import com.example.batch.service.SpaceItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
public class SpaceItemController {


    private  final SpaceItemService itemService;

    private final FolderItemService folderItemService ;

    public SpaceItemController(SpaceItemService itemService, FolderItemService folderItemService) {
        this.itemService = itemService;
        this.folderItemService = folderItemService;
    }


    @PostMapping("/create-space")
    public ResponseEntity<Item> createSpace(
            @RequestParam(name = "spaceName", defaultValue = "stc-assessments") String spaceName,
            @RequestParam(name = "groupName", defaultValue = "admin") String groupName) {

        Item space = itemService.createSpace(spaceName, groupName);
        return new ResponseEntity<>(space, HttpStatus.CREATED);
    }
    @PostMapping("/create-folder/{spaceName}/{folderName}")
    public ResponseEntity<Item> createFolder(
            @PathVariable(name = "spaceName") String spaceName,
            @PathVariable(name = "folderName") String folderName,
            @RequestParam(name = "groupName", defaultValue = "admin") String  groupName,
            @RequestParam(name = "userEmail") String userEmail)  {

        Item folder = folderItemService.createFolder(spaceName,folderName,userEmail,groupName);
        return new ResponseEntity<>(folder, HttpStatus.CREATED);
    }
}

