package Artimia.com.controllers;

import Artimia.com.dtos.admins.*;
import Artimia.com.services.AdminServices;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admins")
public class AdminsController {

    private final AdminServices adminService;

    public AdminsController(AdminServices adminService) {
        this.adminService = adminService;
    }

    @PutMapping("/{username}")
    public ResponseEntity<AdminGet> updateAdmin(
        @PathVariable String username,
        @Valid @RequestBody AdminUpdate dto
    ) {
        return ResponseEntity.ok(adminService.updateAdmin(username, dto));
    }

}