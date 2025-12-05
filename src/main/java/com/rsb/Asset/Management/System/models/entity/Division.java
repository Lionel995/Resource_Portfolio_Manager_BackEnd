package com.rsb.Asset.Management.System.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "divisions")
public class Division {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "division_id")
    private Integer divisionId;
    
    @NotBlank
    @Size(max = 100)
    @Column(name = "division_name", unique = true, nullable = false)
    private String divisionName;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @OneToMany(mappedBy = "division", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> users;
    
    public Division() {}
    
    public Division(String divisionName, String description) {
        this.divisionName = divisionName;
        this.description = description;
    }
    
    // Getters and Setters
    public Integer getDivisionId() {
        return divisionId;
    }
    
    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
    }
    
    public String getDivisionName() {
        return divisionName;
    }
    
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public List<User> getUsers() {
        return users;
    }
    
    public void setUsers(List<User> users) {
        this.users = users;
    }
}
