package com.rsb.Asset.Management.System.service;

import com.rsb.Asset.Management.System.models.entity.Division;
import com.rsb.Asset.Management.System.repository.DivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DivisionService {
    @Autowired
    private DivisionRepository divisionRepository;

    public List<Division> getAllDivisions() {
        return divisionRepository.findAll();
    }

    public Division getDivisionById(Integer id) {
        return divisionRepository.findById(id).orElse(null);
    }

    public Division createDivision(Division division) {
        return divisionRepository.save(division);
    }

    public Division updateDivision(Integer id, Division division) {
        Division existing = divisionRepository.findById(id).orElse(null);
        if (existing == null) return null;
        existing.setDivisionName(division.getDivisionName());
        existing.setDescription(division.getDescription());
        return divisionRepository.save(existing);
    }

    public void deleteDivision(Integer id) {
        divisionRepository.deleteById(id);
    }
}