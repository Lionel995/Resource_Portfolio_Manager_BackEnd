package com.rsb.Asset.Management.System.controller;

import com.rsb.Asset.Management.System.models.entity.Division;
import com.rsb.Asset.Management.System.service.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/divisions")

public class DivisionController {

    @Autowired
    private DivisionService divisionService;

    @GetMapping
    public List<Division> getAllDivisions() {
        return divisionService.getAllDivisions();
    }

    @GetMapping("/{id}")
    public Division getDivisionById(@PathVariable Integer id) {
        return divisionService.getDivisionById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public Division createDivision(@RequestBody Division division) {
        return divisionService.createDivision(division);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Division updateDivision(@PathVariable Integer id, @RequestBody Division division) {
        return divisionService.updateDivision(id, division);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteDivision(@PathVariable Integer id) {
        divisionService.deleteDivision(id);
    }
}