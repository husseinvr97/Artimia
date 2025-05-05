package Artimia.com.services;

import Artimia.com.dtos.user_Address.CreateUserAddress;
import Artimia.com.dtos.user_Address.GetUserAddress;
import Artimia.com.dtos.user_Address.UpdateUserAddress;
import Artimia.com.entities.City;
import Artimia.com.entities.Governorate;
import Artimia.com.entities.UserAddress;
import Artimia.com.entities.Users;
import Artimia.com.exceptions.ResourceNotFoundException;
import Artimia.com.repositories.CityRepository;
import Artimia.com.repositories.GovernorateRepository;
import Artimia.com.repositories.UserAddressRepository;
import Artimia.com.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAddressesServices 
{
    private final UserAddressRepository userAddressRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final GovernorateRepository governorateRepository;

    @Transactional
    public GetUserAddress createUserAddress(CreateUserAddress dto) {
        Users user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        Governorate governorate = governorateRepository.findByNameEn(dto.state())
                .orElseThrow(() -> new ResourceNotFoundException("State not found"));
        
        City city = cityRepository.findByNameEn(dto.city())
                .orElseThrow(() -> new ResourceNotFoundException("City not found"));

        UserAddress address = new UserAddress();
        address.setUser(user);
        address.setAddressLine1(dto.addressLine1());
        address.setGovernorate(governorate);
        address.setCity(city);
        address.setPostalCode(dto.postalCode());

        UserAddress savedAddress = userAddressRepository.save(address);
        return convertToGetDTO(savedAddress);
    }

    public GetUserAddress getUserAddressesByUserId(Long userId)
    {
        Users user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        return convertToGetDTO(userAddressRepository.findByUser(user));
    }

    @Transactional
    public GetUserAddress updateUserAddress(Long addressId, UpdateUserAddress dto)
    {
        UserAddress address = userAddressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        address.setAddressLine1(dto.addressLine1());
        address.setPostalCode(dto.postalCode());

        return convertToGetDTO(userAddressRepository.save(address));
    }

    @Transactional
    public void deleteUserAddress(Long addressId)
    {
        userAddressRepository.deleteById(addressId);
    }

    private GetUserAddress convertToGetDTO(UserAddress address) 
    {
        return new GetUserAddress(
                address.getAddressId(),
                address.getUser().getUserId(),
                address.getAddressLine1(),
                address.getCity().getNameEn(),
                address.getGovernorate().getNameEn(),
                address.getPostalCode() 
        );
    }
}