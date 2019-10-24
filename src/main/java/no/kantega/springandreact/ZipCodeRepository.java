package no.kantega.springandreact;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface ZipCodeRepository extends MongoRepository<ZipCode, String> {

    public ZipCode findByZipCode(String zipCode);
    public List<ZipCode>  findByCounty(String County);


}
