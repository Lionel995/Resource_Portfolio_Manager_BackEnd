package com.rsb.Asset.Management.System.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

//import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "asset_types")
public class AssetType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private Integer typeId;
    
    @NotBlank
    @Size(max = 100)
    @Column(name = "type_name", nullable = false)
    private String typeName;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "assetTypes"})
    private AssetCategory category;
    
    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Asset> assets;
    
    public AssetType() {}
    
    public AssetType(String typeName, AssetCategory category) {
        this.typeName = typeName;
        this.category = category;
    }
    
    // Getters and Setters
    public Integer getTypeId() {
        return typeId;
    }
    
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
    
    public String getTypeName() {
        return typeName;
    }
    
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    
    public AssetCategory getCategory() {
        return category;
    }
    
    public void setCategory(AssetCategory category) {
        this.category = category;
    }
    
    public List<Asset> getAssets() {
        return assets;
    }
    
    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }
}
