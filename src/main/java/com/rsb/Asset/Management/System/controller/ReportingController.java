package com.rsb.Asset.Management.System.controller;

import com.rsb.Asset.Management.System.service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportingController {

    @Autowired
    private ReportingService reportingService;

    // STAFF
    @GetMapping("/staff/requests")
    @PreAuthorize("hasRole('STAFF')")
    public List<?> getStaffRequestedAssets(Principal principal) {
        return reportingService.getStaffRequestedAssets(principal.getName());
    }

    // DIVISION MANAGER
    @GetMapping("/division-manager/requests")
    @PreAuthorize("hasRole('DIVISION_MANAGER')")
    public List<?> getDivisionManagerRequestedAssets(Principal principal) {
        return reportingService.getDivisionManagerRequestedAssets(principal.getName());
    }

    @GetMapping("/division-manager/own")
    @PreAuthorize("hasRole('DIVISION_MANAGER')")
    public List<?> getDivisionManagerOwnRequests(Principal principal) {
        return reportingService.getDivisionManagerOwnRequests(principal.getName());
    }

    @GetMapping("/division-manager/accepted-denied")
    @PreAuthorize("hasRole('DIVISION_MANAGER')")
    public List<?> getDivisionManagerAcceptedOrDenied(Principal principal) {
        return reportingService.getDivisionManagerAcceptedOrDenied(principal.getName());
    }

    // DIRECTOR_DAF & DG
    @GetMapping("/daf/requests")
    @PreAuthorize("hasRole('DIRECTOR_DAF')")
    public List<?> getDirectorRequestedAssets(Principal principal) {
        return reportingService.getDirectorRequestedAssets(principal.getName());
    }

    @GetMapping("/daf/all")
    @PreAuthorize("hasRole('DIRECTOR_DAF')")
    public List<?> getAllRequestedAssets() {
        return reportingService.getAllRequestedAssets();
    }

    @GetMapping("/daf/approved-rejected")
    @PreAuthorize("hasRole('DIRECTOR_DAF')")
    public List<?> getDafApprovedOrRejected() {
        return reportingService.getDafApprovedOrRejected();
    }

    @GetMapping("/dg/requests")
    @PreAuthorize("hasRole('DG')")
    public List<?> getDirectorRequestedAssetsDG(Principal principal) {
        return reportingService.getDirectorRequestedAssets(principal.getName());
    }

    @GetMapping("/dg/all")
    @PreAuthorize("hasRole('DG')")
    public List<?> getAllRequestedAssetsDG() {
        return reportingService.getAllRequestedAssets();
    }

    @GetMapping("/dg/approved-rejected")
    @PreAuthorize("hasRole('DG')")
    public List<?> getDafApprovedOrRejectedDG() {
        return reportingService.getDafApprovedOrRejected();
    }

    // LOGISTIC
    @GetMapping("/logistic/requests")
    @PreAuthorize("hasRole('LOGISTIC')")
    public List<?> getLogisticRequestedAssets(Principal principal) {
        return reportingService.getLogisticRequestedAssets(principal.getName());
    }

    @GetMapping("/logistic/all")
    @PreAuthorize("hasRole('LOGISTIC')")
    public List<?> getLogisticAllRequestedAssets() {
        return reportingService.getLogisticAllRequestedAssets();
    }

    @GetMapping("/logistic/distributed")
    @PreAuthorize("hasRole('LOGISTIC')")
    public List<?> getDistributedAssets() {
        return reportingService.getDistributedAssets();
    }

    @GetMapping("/logistic/instock")
    @PreAuthorize("hasRole('LOGISTIC')")
    public List<?> getInstockAssets() {
        return reportingService.getInstockAssets();
    }

    @GetMapping("/logistic/registered")
    @PreAuthorize("hasRole('LOGISTIC')")
    public List<?> getAllRegisteredAssets() {
        return reportingService.getAllRegisteredAssets();
    }

    // ADMIN
    @GetMapping("/admin/all-assets")
    @PreAuthorize("hasRole('ADMIN')")
    public List<?> getAdminAllAssets() {
        return reportingService.getAdminAllAssets();
    }

    @GetMapping("/admin/all-requests")
    @PreAuthorize("hasRole('ADMIN')")
    public List<?> getAdminAllRequests() {
        return reportingService.getAdminAllRequests();
    }

    @GetMapping("/admin/archived-assets")
    @PreAuthorize("hasRole('ADMIN')")
    public List<?> getArchivedAssets() {
        return reportingService.getArchivedAssets();
    }
}