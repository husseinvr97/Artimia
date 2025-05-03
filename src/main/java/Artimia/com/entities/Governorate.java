package Artimia.com.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "governorates")
public class Governorate 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "governorate_id")
    private long governorate_Id;

    @Column(name = "gid" , unique = true)
    private String gid;

    @Column(name = "name_en") 
    private String nameEn;

    @Column(name = "name_ar")
    private String nameAr;
}
