package no.kantega.springandreact;

import org.springframework.data.annotation.Id;

public class ZipCode {

    @Id
    public String id;

    public String zipCode;
    public String county;
    public String waterData;
    public String airData;
    public String disasterData;

    public ZipCode(){}

    public ZipCode(String _zipcode, String _county, String ad, String dd){

        county = _county;
        zipCode = _zipcode;
        airData = ad;
        disasterData = dd;

    }

    @Override
    public String toString(){
        return String.format("waterData: "+waterData+", County: "+county);
    }
}
