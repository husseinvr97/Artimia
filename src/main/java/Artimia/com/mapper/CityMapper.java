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
            govRepo.findByGid(city.getGid()).get().getNameEn(),
            city.getNameEn()
        );
    }
}