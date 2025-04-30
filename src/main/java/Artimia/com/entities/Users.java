package Artimia.com.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;

@Getter
@Setter
@Entity
@Table(name = "users",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "email"),
           @UniqueConstraint(columnNames = "phone_number")
       })
public class Users 
{

    @Column(name = "first_name", length = 30, nullable = false)
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 30, message = "2-30 characters")
    @Pattern(regexp = "^[\\p{L} '-]+$", message = "Invalid characters")
    private String firstName;

    @Column(name = "last_name", length = 30, nullable = false)
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 30, message = "2-30 characters")
    @Pattern(regexp = "^[\\p{L} '-]+$", message = "Invalid characters")
    private String lastName;

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long userId;

    @Column(name = "email" , nullable = false)
    @NotBlank(message = "The email must not be empty")
    @Email
    private String email;
    
    @Column(name = "phone_number",nullable = false)
    @NotBlank(message = "The phone number must not be empty")
    private String phoneNumber;

    @Column(name="password_hash",nullable = false)
    private String passwordHash;

    @CreationTimestamp
    @Column(name = "date_created", updatable = false)
    private LocalDateTime dateCreated;
    
}
