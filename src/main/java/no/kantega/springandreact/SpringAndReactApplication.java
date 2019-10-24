package no.kantega.springandreact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringAndReactApplication implements CommandLineRunner {

	@Autowired
	private ZipCodeRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringAndReactApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		repository.deleteAll();

		//save a couple of zip codes
		repository.save(new ZipCode("76103", "Tarrant", "12", "12"));
		repository.save(new ZipCode("76112", "Dallas", "22", "22"));

		System.out.println("ZipCodes found with findAll():");
		System.out.println("-------------------------------");
		for(ZipCode zip : repository.findAll()){
			System.out.println(zip);
		}
		System.out.println();




	}
}
