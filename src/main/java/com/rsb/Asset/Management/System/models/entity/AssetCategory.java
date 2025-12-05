package com.rsb.Asset.Management.System.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

//import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "asset_categories")
public class AssetCategory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;
    
    @NotBlank
    @Size(max = 100)
    @Column(name = "category_name", unique = true, nullable = false)
    private String categoryName;
    
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "category"})
    private List<AssetType> assetTypes;
    
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Asset> assets;
    
    public AssetCategory() {}
    
    public AssetCategory(String categoryName) {
        this.categoryName = categoryName;
    }
    
    // Getters and Setters
    public Integer getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public List<AssetType> getAssetTypes() {
        return assetTypes;
    }
    
    public void setAssetTypes(List<AssetType> assetTypes) {
        this.assetTypes = assetTypes;
    }
    
    public List<Asset> getAssets() {
        return assets;
    }
    
    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }
}
