package Artimia.com.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Column;

@Getter
@Setter
@Entity
@Table(name = "admins", uniqueConstraints = 
{
    @UniqueConstraint(columnNames = "username"),
    @UniqueConstraint(columnNames = "phone_number")
})
public class Admins 
{
    @Column(name = "email" )
    @NotBlank(message = "The email must not be empty")
    @Email
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "password_hash")
    private String passwordHash;

}
