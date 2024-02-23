package org.nastya.service;

import org.nastya.dto.CountryOptionDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.nastya.entity.Country.RUSSIA;
import static org.nastya.entity.Country.ENGLAND;
import static org.nastya.entity.Country.USA;
import static org.nastya.entity.Country.UKRAINE;
import static org.nastya.entity.Country.CANADA;

@Service
public class CountryService {
    public List<CountryOptionDTO> getAllCountry() {
        List<CountryOptionDTO> country = new ArrayList<>();
        country.add(new CountryOptionDTO(RUSSIA.name(), RUSSIA.getLabel()));
        country.add(new CountryOptionDTO(ENGLAND.name(), ENGLAND.getLabel()));
        country.add(new CountryOptionDTO(USA.name(), USA.getLabel()));
        country.add(new CountryOptionDTO(UKRAINE.name(), UKRAINE.getLabel()));
        country.add(new CountryOptionDTO(CANADA.name(), CANADA.getLabel()));
        return country;
    }
}
