package no.kantega.springandreact;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.xml.crypto.Data;
import java.io.IOException;


@RunWith(SpringRunner.class)
public class AQIAPITests {

    String emptyResponse = "{\"airData\":[]}";

//    @Test
//    public void testAqiValidResponse() throws IOException {
//
//        Address address = new Address("900 West 23rd St Austin TX 78705");
//        address.getAddressInformation();
//
//        String response = DataRetrieval.getAQIData(address).toString();
//
//        System.out.println(response);
//
//        assertNotEquals(emptyResponse, response);
//    }
//
//    @Test
//    public void testAqiValidResponseA() throws IOException {
//
//        Address address = new Address("48033");
//        address.getAddressInformation();
//
//        String response = DataRetrieval.getAQIData(address).toString();
//
//        System.out.println(response);
//
//        assertNotEquals(emptyResponse, response);
//    }
//
//    @Test
//    public void testAqiValidResponseB() throws IOException {
//
//        Address address = new Address("12345");
//        address.getAddressInformation();
//
//        String response = DataRetrieval.getAQIData(address).toString();
//
//        System.out.println(response);
//
//        assertNotEquals(emptyResponse, response);
//    }
//
//    @Test
//    public void testAqiValidResponseC() throws IOException {
//
//        Address address = new Address("14029");
//        address.getAddressInformation();
//
//        String response = DataRetrieval.getAQIData(address).toString();
//
//        System.out.println(response);
//
//        assertNotEquals(emptyResponse, response);
//    }
//
//    @Test
//    public void testAqiValidResponseD() throws IOException {
//
//        Address address = new Address("03217");
//        address.getAddressInformation();
//
//        String response = DataRetrieval.getAQIData(address).toString();
//
//        System.out.println(response);
//
//        assertNotEquals(emptyResponse, response);
//    }
//
//    @Test
//    public void testAqiValidResponseE() throws IOException {
//
//        Address address = new Address("54201");
//        address.getAddressInformation();
//
//        String response = DataRetrieval.getAQIData(address).toString();
//
//        System.out.println(response);
//
//        assertNotEquals(emptyResponse, response);
//    }
//
//    @Test
//    public void testAqiValidResponseF() throws IOException {
//
//        Address address = new Address("32013");
//        address.getAddressInformation();
//
//        String response = DataRetrieval.getAQIData(address).toString();
//
//        System.out.println(response);
//
//        assertNotEquals(emptyResponse, response);
//    }
//
//    @Test
//    public void testAqiValidResponseG() throws IOException {
//
//        Address address = new Address("20604");
//        address.getAddressInformation();
//
//        String response = DataRetrieval.getAQIData(address).toString();
//
//        System.out.println(response);
//
//        assertNotEquals(emptyResponse, response);
//    }

}