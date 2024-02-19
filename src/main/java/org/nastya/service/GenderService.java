package org.nastya.service;

import org.nastya.dto.GenderOptionDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.nastya.entity.Gender.FEMALE;
import static org.nastya.entity.Gender.MALE;
import static org.nastya.entity.Gender.UNKNOWN;

@Service
public class GenderService {
    public List<GenderOptionDTO> getAllGenders() {
        List<GenderOptionDTO> genders = new ArrayList<>();
        genders.add(new GenderOptionDTO(MALE.name(), MALE.getLabel()));
        genders.add(new GenderOptionDTO(FEMALE.name(), FEMALE.getLabel()));
        genders.add(new GenderOptionDTO(UNKNOWN.name(), UNKNOWN.getLabel()));
        return genders;
    }
}
