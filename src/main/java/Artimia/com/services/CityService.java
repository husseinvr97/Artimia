package Artimia.com.services;

import Artimia.com.dtos.cities.CityGet;
import Artimia.com.exceptions.ResourceNotFoundException;
import Artimia.com.mapper.CityMapper;
import Artimia.com.repositories.CityRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityService 
{

    private final CityRepository cityRepository;

    public CityGet getCityByName(String cityName) 
    {
        return cityRepository.findByNameEn(cityName)
            .map(CityMapper::toDto)
            .orElseThrow(() -> new ResourceNotFoundException("City not found"));
    }

    public List<CityGet> getAllCities()
    {
        return cityRepository.findAll().stream().map(CityMapper::toDto)
        .toList();
    }

}