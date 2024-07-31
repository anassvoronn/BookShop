package org.nastya.controller;

import org.nastya.dto.GenderOptionDTO;
import org.nastya.service.GenderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/gender")
public class GenderController {
    private final GenderService genderService;

    public GenderController(GenderService genderService) {
        this.genderService = genderService;
    }
    
    @GetMapping
    public List<GenderOptionDTO> getAllGenders() {
        return genderService.getAllGenders();
    }
}
