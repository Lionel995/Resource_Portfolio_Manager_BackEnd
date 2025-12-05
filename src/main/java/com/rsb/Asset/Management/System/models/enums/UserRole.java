package com.rsb.Asset.Management.System.models.enums;

public enum UserRole {
    STAFF("Staff"),
    DIVISION_MANAGER("Division Manager"),
    DIRECTOR_DAF("Director-DAF"),
    DG("DG"),
    LOGISTIC("Logistic"),
    ADMIN("Admin");
    
    private final String displayName;
    
    UserRole(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    // Check if role has approval authority
    public boolean canApproveRequests() {
        return this == DIVISION_MANAGER || this == DIRECTOR_DAF;
    }
    
    // Check if role can manage assets
    public boolean canManageAssets() {
        return this == LOGISTIC || this == ADMIN;
    }
    
    // Check if role has administrative privileges
    public boolean isAdmin() {
        return this == ADMIN || this == DG;
    }
    
    // Get approval level for workflow
    public int getApprovalLevel() {
        switch (this) {
            case DIVISION_MANAGER:
                return 1;
            case DIRECTOR_DAF:
                return 2;
            default:
                return 0;
        }
    }
}
