package Artimia.com.services;

import Artimia.com.dtos.governorates.GovernorateGet;

import Artimia.com.exceptions.ResourceNotFoundException;

import Artimia.com.mapper.GovernorateMapper;

import Artimia.com.repositories.GovernorateRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GovernorateServices
{

    private final GovernorateRepository governorateRepository;

    public List<GovernorateGet> getAllGovernorates() 
    {
        return governorateRepository.findAll().stream()
            .map(GovernorateMapper::toDto)
            .toList();
    }

    public GovernorateGet getGovernorateById(Long id) 
    {
        return governorateRepository.findById(id)
            .map(GovernorateMapper::toDto)
            .orElseThrow(() -> new ResourceNotFoundException("Governorate not found"));
    }

    public GovernorateGet getGovernorateByName(String governName)
    {
        return governorateRepository.findByNameEn(governName)
        .map(GovernorateMapper::toDto)
        .orElseThrow(()->new ResourceNotFoundException("Governorate not found"));
    }

}