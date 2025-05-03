package Artimia.com.controllers;

import Artimia.com.dtos.cities.CityGet;
import Artimia.com.services.CityService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController 
{ 

    private final CityService cityService;

    @GetMapping
    public List<CityGet> getAllCities()
    {
        return cityService.getAllCities();
    }
    @GetMapping("/{name_en}")
    public CityGet getCity(@PathVariable String name_en) 
    {
        return cityService.getCityByName(name_en);
    }
}