package org.nastya.service;

import org.nastya.dto.CountryOptionDTO;
import org.nastya.entity.Country;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService {
    public List<CountryOptionDTO> getAllCountries() {
        List<CountryOptionDTO> countries = new ArrayList<>();
        for (Country country : Country.values()) {
            countries.add(new CountryOptionDTO(country.name(), country.getLabel()));
        }
        return countries;
    }
}
