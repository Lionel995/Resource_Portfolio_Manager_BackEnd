package com.rsb.Asset.Management.System.controller;

import com.rsb.Asset.Management.System.models.dto.AssetDistributionRequestDTO;
import com.rsb.Asset.Management.System.models.dto.AssetDistributionResponseDTO;
import com.rsb.Asset.Management.System.service.AssetDistributionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/distributions")
public class AssetDistributionController {

    private final AssetDistributionService distributionService;

    public AssetDistributionController(AssetDistributionService distributionService) {
        this.distributionService = distributionService;
    }

    @PostMapping("/distribute")
    public ResponseEntity<AssetDistributionResponseDTO> distributeAsset(
            @RequestBody AssetDistributionRequestDTO dto) {
        return ResponseEntity.ok(distributionService.distributeAsset(dto));
    }

    @PutMapping("/{id}/return")
public ResponseEntity<AssetDistributionResponseDTO> returnAsset(
        @PathVariable Integer id,
        @RequestParam String returner) {
    return ResponseEntity.ok(distributionService.returnAsset(id, returner));
}


    @GetMapping("/all")
    public ResponseEntity<List<AssetDistributionResponseDTO>> getAllDistributions() {
        return ResponseEntity.ok(distributionService.getAllDistributions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetDistributionResponseDTO> getDistributionById(@PathVariable Integer id) {
        return distributionService.getDistributionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AssetDistributionResponseDTO>> getDistributionsByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(distributionService.getDistributionsByUser(userId));
    }

    @GetMapping("/asset/{assetId}")
    public ResponseEntity<List<AssetDistributionResponseDTO>> getDistributionsByAsset(@PathVariable Integer assetId) {
        return ResponseEntity.ok(distributionService.getDistributionsByAsset(assetId));
    }

    @GetMapping("/request/{requestId}")
    public ResponseEntity<List<AssetDistributionResponseDTO>> getDistributionsByRequest(@PathVariable Integer requestId) {
        return ResponseEntity.ok(distributionService.getDistributionsByRequest(requestId));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<AssetDistributionResponseDTO>> getDistributionsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(distributionService.getDistributionsByDateRange(startDate, endDate));
    }

    @GetMapping("/returns/date-range")
    public ResponseEntity<List<AssetDistributionResponseDTO>> getReturnsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(distributionService.getReturnsByDateRange(startDate, endDate));
    }
}
