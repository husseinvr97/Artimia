package Artimia.com.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cities" , indexes = @Index(name = "city_name_index" , columnList = "name_en"))
public class City 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "name_en" , length = 100)
    private String nameEn;

    @ManyToOne
    @JoinColumn(name = "governorate_id", referencedColumnName = "governorate_id")
    private Governorate governorate;
}
