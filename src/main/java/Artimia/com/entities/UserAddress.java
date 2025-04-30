package Artimia.com.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "user_addresses")
public class UserAddress {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", updatable = false)
    private Long addressId;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private Users user;

    @Getter
    @Setter
    @NotBlank(message = "Address line 1 is required")
    @Column(name = "address_line1", nullable = false, length = 255)
    private String addressLine1;

    @Getter
    @Setter
    @NotBlank(message = "City is required")
    @Column(nullable = false, length = 100)
    private String city;

    @Getter
    @Setter
    @NotBlank(message = "State is required")
    @Column(nullable = false, length = 100)
    private String state;

    @Getter
    @Setter
    @NotBlank(message = "Postal code is required")
    @Column(name = "postal_code", nullable = false, length = 20)
    private String postalCode;

    @Getter
    @Setter
    @NotBlank(message = "Country is required")
    @Column(nullable = false, length = 100)
    private String country;
}