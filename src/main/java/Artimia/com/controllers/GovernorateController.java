package Artimia.com.controllers;

import Artimia.com.dtos.governorates.GovernorateGet;
import Artimia.com.services.GovernorateServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/governorates")
@RequiredArgsConstructor
public class GovernorateController {

    private final GovernorateServices governorateService;

    @GetMapping
    public List<GovernorateGet> getAllGovernorates() {
        return governorateService.getAllGovernorates();
    }

    @GetMapping("/{name}")
    public GovernorateGet getGovernorateByName(@PathVariable String GovernName) {
        return governorateService.getGovernorateByName(GovernName);
    }

}