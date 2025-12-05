package com.rsb.Asset.Management.System.controller;

import com.rsb.Asset.Management.System.models.entity.AssetType;
import com.rsb.Asset.Management.System.service.AssetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/types")
@PreAuthorize("hasRole('ADMIN')")
public class AssetTypeController {

    @Autowired
    private AssetTypeService assetTypeService;

    @GetMapping
    public List<AssetType> getAllTypes() {
        return assetTypeService.getAllTypes();
    }

    @GetMapping("/{id}")
    public AssetType getTypeById(@PathVariable Integer id) {
        return assetTypeService.getTypeById(id);
    }

    @PostMapping
    public AssetType createType(@RequestBody AssetType type) {
        return assetTypeService.createType(type);
    }

    @PutMapping("/{id}")
    public AssetType updateType(@PathVariable Integer id, @RequestBody AssetType type) {
        return assetTypeService.updateType(id, type);
    }

    @DeleteMapping("/{id}")
    public void deleteType(@PathVariable Integer id) {
        assetTypeService.deleteType(id);
    }
}