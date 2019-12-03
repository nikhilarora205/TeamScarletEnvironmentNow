package no.kantega.springandreact;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotEquals;

public class AllergenAPITests {

    String emptyResponse = "{}";


    @Test
    public void testAllergenValidAddress() throws IOException {


        Address address = new Address("900 West 23rd St Austin TX 78705");
        address.getAddressInformation();

        String response = DataRetrieval.getAllergenData(address).toString();

        System.out.println("THIS IS THE RESPONSE:" + response);

        assertNotEquals(emptyResponse, response);

    }

    @Test
    public void testAllergenValidAddressA() throws IOException {


        Address address = new Address("12345");
        address.getAddressInformation();

        String response = DataRetrieval.getAllergenData(address).toString();

        System.out.println("THIS IS THE RESPONSE:" + response);

        assertNotEquals(emptyResponse, response);

    }

    @Test
    public void testAllergenValidAddressB() throws IOException {

        Address address = new Address("32013");
        address.getAddressInformation();

        String response = DataRetrieval.getAllergenData(address).toString();

        System.out.println("THIS IS THE RESPONSE:" + response);

        assertNotEquals(emptyResponse, response);

    }

    @Test
    public void testAllergenValidAddressC() throws IOException {

        Address address = new Address("20604");
        address.getAddressInformation();

        String response = DataRetrieval.getAllergenData(address).toString();

        System.out.println("THIS IS THE RESPONSE:" + response);

        assertNotEquals(emptyResponse, response);

    }

    @Test
    public void testAllergenValidAddressD() throws IOException {

        Address address = new Address("14029");
        address.getAddressInformation();

        String response = DataRetrieval.getAllergenData(address).toString();

        System.out.println("THIS IS THE RESPONSE:" + response);

        assertNotEquals(emptyResponse, response);

    }

}
