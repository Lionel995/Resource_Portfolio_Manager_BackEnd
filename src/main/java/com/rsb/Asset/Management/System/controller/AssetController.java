package com.rsb.Asset.Management.System.controller;

import com.rsb.Asset.Management.System.models.dto.AssetDTO;
import com.rsb.Asset.Management.System.models.entity.Asset;
import com.rsb.Asset.Management.System.models.entity.AssetCategory;
import com.rsb.Asset.Management.System.models.entity.AssetType;
import com.rsb.Asset.Management.System.models.entity.User;
import com.rsb.Asset.Management.System.repository.AssetCategoryRepository;
import com.rsb.Asset.Management.System.repository.AssetTypeRepository;
import com.rsb.Asset.Management.System.service.AssetService;
import com.rsb.Asset.Management.System.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assets")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @Autowired
    private UserService userService;

    @Autowired
    private AssetCategoryRepository assetCategoryRepository;

    @Autowired
    private AssetTypeRepository assetTypeRepository;

    @GetMapping
@PreAuthorize("hasAnyRole('STAFF','DIVISION_MANAGER','DIRECTOR_DAF','DG','LOGISTIC', 'ADMIN')")
public List<AssetDTO> getAllAssets() {
    return assetService.getAllAssets()
            .stream()
            .map(AssetDTO::new)
            .toList();
}


    @PostMapping("/register")
@PreAuthorize("hasAnyRole('LOGISTIC','ADMIN')")
public Asset registerAsset(@RequestBody Asset asset, Authentication authentication) {
    String usernameOrEmail = authentication.getName();
    User loggedInUser = userService.getUserByEmail(usernameOrEmail);

    // Validate and set the category and type
    AssetCategory category = assetCategoryRepository.findById(asset.getCategory().getCategoryId())
            .orElseThrow(() -> new RuntimeException("Category not found"));
    AssetType type = assetTypeRepository.findById(asset.getType().getTypeId())
            .orElseThrow(() -> new RuntimeException("Type not found"));

    asset.setCategory(category);
    asset.setType(type);
    asset.setRegisteredBy(loggedInUser);

    return assetService.registerAsset(asset);
}


    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('LOGISTIC', 'ADMIN')")
    public Asset updateAsset(@RequestBody Asset asset, Authentication authentication) {
        String usernameOrEmail = authentication.getName();
        User loggedInUser = userService.getUserByEmail(usernameOrEmail);

        // Update 'updatedBy' field with the logged-in user
        asset.setUpdatedBy(loggedInUser);

        return assetService.updateAsset(asset);
    }

    @PutMapping("/archive/{id}")
    @PreAuthorize("hasRole('LOGISTIC')")
    public void archiveAsset(@PathVariable Integer id) {
        assetService.archiveAsset(id);
    }


    @GetMapping("/{id}")
@PreAuthorize("hasAnyRole('STAFF','DIVISION_MANAGER','DIRECTOR_DAF','DG','LOGISTIC','ADMIN')")
public AssetDTO getAssetById(@PathVariable Integer id) {
    Asset asset = assetService.getAssetById(id);
    return new AssetDTO(asset);
}

@PutMapping("/{id}")
@PreAuthorize("hasAnyRole('LOGISTIC','ADMIN')")
public Asset updateAsset(@PathVariable Integer id, @RequestBody Asset updatedAsset, Authentication authentication) {
    String usernameOrEmail = authentication.getName();
    User loggedInUser = userService.getUserByEmail(usernameOrEmail);

    updatedAsset.setAssetId(id);
    updatedAsset.setUpdatedBy(loggedInUser);

    return assetService.updateAsset(updatedAsset);
}

}
