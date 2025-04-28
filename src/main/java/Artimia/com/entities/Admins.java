package Artimia.com.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "admins", uniqueConstraints = 
{
    @UniqueConstraint(columnNames = "username"),
    @UniqueConstraint(columnNames = "phone_number")
})
public class Admins 
{

    @Id
    @Column(name = "username", nullable = false, updatable = false)
    private String username;

    @Column(name = "phone_number" , nullable = false , updatable = false)
    private String phoneNumber;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    public String getUsername() 
    {
        return username;
    }
    public String getPhoneNumber()
    {
        return phoneNumber;
    }
    public String getPasswordHash() 
    {
        return passwordHash;
    }
}
