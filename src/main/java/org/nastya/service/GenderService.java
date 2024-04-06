package org.nastya.service;

import org.nastya.dto.GenderOptionDTO;
import org.nastya.entity.Gender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenderService {
    public List<GenderOptionDTO> getAllGenders() {
        List<GenderOptionDTO> genders = new ArrayList<>();
        for (Gender gender : Gender.values()) {
            genders.add(new GenderOptionDTO(gender.name(), gender.getLabel()));
        }
        return genders;
    }
}
