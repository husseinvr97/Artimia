package Artimia.com.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "user_addresses")
public class UserAddress 
{

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", updatable = false)
    private Long addressId;

    @NotNull(message = "The user Id cannot be null")
    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "user_id", updatable = false)
    private Users user;

    @NotBlank(message = "the address line cannot be null")
    @Getter
    @Setter
    @NotBlank(message = "Address line 1 is required")
    @Size(message = "the address line must be between 25 and 255 characters",min = 25,max = 255)
    @Column(name = "address_line1")
    private String addressLine1;

    @NotNull(message = "the governorate cannot be null")
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "governorate_id")
    private Governorate governorate;

    @NotNull(message = "the city cannot be null")
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    
}