package Artimia.com.services;

import Artimia.com.dtos.user_Address.CreateUserAddress;
import Artimia.com.dtos.user_Address.GetUserAddress;
import Artimia.com.dtos.user_Address.UpdateUserAddress;
import Artimia.com.entities.City;
import Artimia.com.entities.Governorate;
import Artimia.com.entities.UserAddress;
import Artimia.com.entities.Users;
import Artimia.com.exceptions.DuplicateResourceException;
import Artimia.com.exceptions.ResourceNotFoundException;
import Artimia.com.repositories.CityRepository;
import Artimia.com.repositories.GovernorateRepository;
import Artimia.com.repositories.UserAddressRepository;
import Artimia.com.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileReader;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAddressesServices {
    private final UserAddressRepository userAddressRepository;
    private final UserRepository userRepository;
    private final GovernorateRepository governorateRepository;
    private final CityRepository cityRepository;

    @Transactional
    public GetUserAddress createUserAddress(CreateUserAddress dto) {

        if (userAddressRepository.existsById(dto.userId()))
            throw new DuplicateResourceException("Address already exists");
        Users user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Governorate governorate = governorateRepository.findByNameEn(dto.governorateName())
                .orElseThrow(() -> new ResourceNotFoundException("State not found"));

        City city = cityRepository.findByNameEn(dto.cityName())
                .orElseThrow(() -> new ResourceNotFoundException("City not found"));

        UserAddress address = new UserAddress();
        address.setUser(user);
        address.setAddressLine1(dto.addressLine1());
        address.setGovernorate(governorate);
        address.setCity(city);

        UserAddress savedAddress = userAddressRepository.save(address);
        return convertToGetDTO(savedAddress);
    }

    public GetUserAddress getUserAddressesByUserId(Long userId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return convertToGetDTO(userAddressRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found")));
    }

    public List<GetUserAddress> getAllByGovName(String govName) {
        List<GetUserAddress> allByGov = userAddressRepository.findAllByGovernorateNameEn(govName)
                .orElseThrow(() -> new ResourceNotFoundException("nothing is found by" + govName)).stream()
                .map(this::convertToGetDTO).toList();
        if (allByGov.isEmpty())
            throw new ResourceNotFoundException("Nothing is found by " + govName);
        return allByGov;
    }

    @Transactional
    public GetUserAddress updateUserAddress(Long addressId, UpdateUserAddress dto) {
        UserAddress address = userAddressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        address.setAddressLine1(dto.addressLine1());
        address.setCity(cityRepository.findByNameEn(dto.city())
                .orElseThrow(() -> new ResourceNotFoundException("City not found")));
        address.setGovernorate(governorateRepository.findByNameEn(dto.state())
                .orElseThrow(() -> new ResourceNotFoundException("governorate not found")));

        return convertToGetDTO(userAddressRepository.save(address));
    }

    @Transactional
    public void deleteUserAddress(Long addressId) {
        userAddressRepository.deleteById(addressId);
    }

    public void importAllRegions() {

        String filePath = "D:\\projects\\Artimia\\src\\main\\resources\\gadm41_EGY_2.json";

        try (FileReader reader = new FileReader(filePath)) {

            JSONTokener tokener = new JSONTokener(reader);
            JSONObject geoJson = new JSONObject(tokener);

            JSONArray features = geoJson.getJSONArray("features");

            for (int i = 0; i < features.length(); i++) {
                JSONObject feature = features.getJSONObject(i);
                JSONObject properties = feature.getJSONObject("properties");

                String governorateName = properties.getString("NAME_1");
                if (!governorateRepository.existsByNameEn(governorateName)) {
                    Governorate governorate = new Governorate();
                    governorate.setNameEn(governorateName);
                    governorateRepository.save(governorate);
                }
                String cityName = properties.getString("NAME_2");
                City city = new City();
                city.setGovernorate(governorateRepository.findByNameEn(governorateName)
                        .orElseThrow(() -> new ResourceNotFoundException("Governorate Not Found")));
                city.setNameEn(cityName);

                cityRepository.save(city);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private GetUserAddress convertToGetDTO(UserAddress address) {
        return new GetUserAddress(
                address.getAddressId(),
                address.getUser().getUserId(),
                address.getAddressLine1(),
                address.getCity().getNameEn(),
                address.getGovernorate().getNameEn());
    }
}