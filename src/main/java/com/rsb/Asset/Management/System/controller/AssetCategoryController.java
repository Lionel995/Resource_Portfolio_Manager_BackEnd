package com.rsb.Asset.Management.System.controller;

import com.rsb.Asset.Management.System.models.entity.AssetCategory;
import com.rsb.Asset.Management.System.service.AssetCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@PreAuthorize("hasRole('ADMIN')")
public class AssetCategoryController {

    @Autowired
    private AssetCategoryService assetCategoryService;

    @GetMapping
    public List<AssetCategory> getAllCategories() {
        return assetCategoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public AssetCategory getCategoryById(@PathVariable Integer id) {
        return assetCategoryService.getCategoryById(id);
    }

    @PostMapping("/Register")
    public AssetCategory createCategory(@RequestBody AssetCategory category) {
        return assetCategoryService.createCategory(category);
    }

    @PutMapping("/{id}")
    public AssetCategory updateCategory(@PathVariable Integer id, @RequestBody AssetCategory category) {
        return assetCategoryService.updateCategory(id, category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        assetCategoryService.deleteCategory(id);
    }
}