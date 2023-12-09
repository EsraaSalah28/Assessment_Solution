package com.example.batch.controller;

import com.example.batch.model.Item;
import com.example.batch.service.FileItemService;
import com.example.batch.service.FolderItemService;
import com.example.batch.service.SpaceItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/items")
public class SpaceItemController {


    private  final SpaceItemService spaceItemService ;

    private final FolderItemService folderItemService ;
    private final FileItemService fileItemService;

    public SpaceItemController( SpaceItemService spaceItemService, FolderItemService folderItemService, FileItemService fileItemService) {
        this.spaceItemService = spaceItemService;
        this.folderItemService = folderItemService;
        this.fileItemService = fileItemService;
    }


    @PostMapping("/create-space")
    public ResponseEntity<Item> createSpace(
            @RequestParam(name = "spaceName", defaultValue = "stc-assessments") String spaceName,
            @RequestParam(name = "groupName", defaultValue = "admin") String groupName) {

        Item space = spaceItemService.createSpace(spaceName, groupName);
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

    @PostMapping("/create-file")
    public ResponseEntity<Item> createFile(
            @RequestParam(name = "folderName") String folderName,
            @RequestParam(name = "fileName") String fileName,
            @RequestParam(name = "groupName", defaultValue = "admin") String  groupName,
            @RequestParam(name = "userEmail") String userEmail) throws AccessDeniedException {

        Item file = fileItemService.createFile(folderName, fileName, userEmail,groupName);
        return new ResponseEntity<>(file, HttpStatus.CREATED);
    }


    @GetMapping("/view-file-metadata")
    public ResponseEntity<Item> viewFileMetadata(
            @RequestParam(name = "fileName") String fileName,
            @RequestParam(name = "userEmail") String userEmail) {

        Item fileMetadata = fileItemService.viewFileMetadata(fileName, userEmail);
        return new ResponseEntity<>(fileMetadata, HttpStatus.OK);
    }
}

