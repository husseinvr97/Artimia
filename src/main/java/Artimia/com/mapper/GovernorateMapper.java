package Artimia.com.mapper;

import Artimia.com.dtos.governorates.GovernorateGet;
import Artimia.com.entities.Governorate;

public class GovernorateMapper 
{
    public static GovernorateGet toDto(Governorate governorate) 
    {
        return new GovernorateGet
        (
            governorate.getNameEn()
        );
    }
}