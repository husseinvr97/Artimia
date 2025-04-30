package Artimia.com.services;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import Artimia.com.entities.City;
import Artimia.com.geojson.GeoFeature;
import Artimia.com.geojson.GeoJson;
import Artimia.com.repositories.CityRepository;
import Artimia.com.repositories.GovernorateRepository;

@Service
public class GovernoratesServices 
{
    @Autowired
    private GovernorateRepository governorateRepo;

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

                governorateRepo.findByGid(govGid).ifPresent(city::setGovernorate);
                cityRepo.save(city);
            }
        }
    }
}
