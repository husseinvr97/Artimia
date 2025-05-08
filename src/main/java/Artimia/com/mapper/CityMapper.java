package Artimia.com.mapper;

import Artimia.com.dtos.cities.CityGet;
import Artimia.com.entities.City;
import Artimia.com.repositories.GovernorateRepository;

public class CityMapper 
{
    public static GovernorateRepository govRepo;
    public static CityGet toDto(City city) 
    {
        return new CityGet(
            city.getGovernorate().getNameEn(),
            city.getNameEn(),
            city.getCityId()
        );
    }
}