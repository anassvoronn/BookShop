package org.nastya.controller;

import org.nastya.dto.CountryOptionDTO;
import org.nastya.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/country")
public class CountryController {
    @Autowired
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public List<CountryOptionDTO> getAllCountry() {
        return countryService.getAllCountries();
    }
}
