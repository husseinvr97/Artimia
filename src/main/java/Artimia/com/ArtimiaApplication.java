package Artimia.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", maxAge = 3600)
@SpringBootApplication
public class ArtimiaApplication 
{
	public static void main(String[] args) 
	{
		SpringApplication.run(ArtimiaApplication.class, args);
	}
}
