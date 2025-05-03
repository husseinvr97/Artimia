package Artimia.com.services;

import Artimia.com.dtos.governorates.GovernorateGet;
import Artimia.com.entities.City;
import Artimia.com.exceptions.ResourceNotFoundException;
import Artimia.com.geojson.GeoFeature;
import Artimia.com.geojson.GeoJson;
import Artimia.com.mapper.GovernorateMapper;
import Artimia.com.repositories.CityRepository;
import Artimia.com.repositories.GovernorateRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GovernorateServices
{

    private final GovernorateRepository governorateRepository;

    public List<GovernorateGet> getAllGovernorates() {
        return governorateRepository.findAll().stream()
            .map(GovernorateMapper::toDto)
            .toList();
    }

    public GovernorateGet getGovernorateById(Long id) {
        return governorateRepository.findById(id)
            .map(GovernorateMapper::toDto)
            .orElseThrow(() -> new ResourceNotFoundException("Governorate not found"));
    }

    public GovernorateGet getGovernorateByGid(String gid) 
    {
        return governorateRepository.findByGid(gid)
            .map(GovernorateMapper::toDto)
            .orElseThrow(() -> new ResourceNotFoundException("Governorate not found"));
    }

    public GovernorateGet getGovernorateByName(String governName)
    {
        return governorateRepository.findByNameEn(governName)
        .map(GovernorateMapper::toDto)
        .orElseThrow(()->new ResourceNotFoundException("Governorate not found"));
    }

    @Autowired
    private CityRepository cityRepo;

    public void importGovernorates(File geoJsonFile) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        GeoJson geo = mapper.readValue(geoJsonFile, GeoJson.class);

        for (GeoFeature feature : geo.features) 
        {
            String gid = (String) feature.properties.get("GID_2");
            String nameEn = (String) feature.properties.get("NAME_2");
            String nameAr = (String) feature.properties.get("NL_NAME_2");
            String govGid = (String) feature.properties.get("GID_1");

            if (!cityRepo.existsByGid(gid)) {
                City city = new City();
                city.setGid(gid);
                city.setNameEn(nameEn);
                city.setNameAr(!"NA".equals(nameAr) ? nameAr : null);

                governorateRepository.findByGid(govGid).ifPresent(city::setGovernorate);
                cityRepo.save(city);
            }
        }
    }
}