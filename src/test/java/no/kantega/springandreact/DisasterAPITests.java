package no.kantega.springandreact;

import static org.junit.Assert.*;

import org.json.JSONObject;
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

import java.io.IOException;
import java.util.List;


@RunWith(SpringRunner.class)
public class DisasterAPITests {

    String emptyResponse = "{\"natData\":[]}";

    @Test
    public void testDisasterValidResponse() throws IOException {
        Address address = new Address("900 West 23rd St Austin TX 78705");
        address.getAddressInformation();

        JSONObject response = DataRetrieval.getNaturalDisasterData(address);

        System.out.println(response.toString());

        assertNotEquals(emptyResponse, response.toString());
    }

    @Test
    public void testDisasterValidResponseA() throws IOException {
        Address address = new Address("20604");
        address.getAddressInformation();

        JSONObject response = DataRetrieval.getNaturalDisasterData(address);

        System.out.println(response.toString());

        assertNotEquals(emptyResponse, response.toString());
    }

    @Test
    public void testDisasterValidResponseB() throws IOException {
        Address address = new Address("76103");
        address.getAddressInformation();

        JSONObject response = DataRetrieval.getNaturalDisasterData(address);

        System.out.println(response.toString());

        assertNotEquals(emptyResponse, response.toString());
    }

    @Test
    public void testDisasterValidResponseC() throws IOException {
        Address address = new Address("12345");
        address.getAddressInformation();

        JSONObject response = DataRetrieval.getNaturalDisasterData(address);

        System.out.println(response.toString());

        assertNotEquals(emptyResponse, response.toString());
    }

    @Test
    public void testDisasterValidResponseD() throws IOException {
        Address address = new Address("32013");
        address.getAddressInformation();

        JSONObject response = DataRetrieval.getNaturalDisasterData(address);

        System.out.println(response.toString());

        assertNotEquals(emptyResponse, response.toString());
    }

    @Test
    public void testDisasterValidResponseE() throws IOException {
        Address address = new Address("48033");
        address.getAddressInformation();

        JSONObject response = DataRetrieval.getNaturalDisasterData(address);

        System.out.println(response.toString());

        assertNotEquals(emptyResponse, response.toString());
    }

    @Test
    public void testDisasterValidResponseF() throws IOException {
        Address address = new Address("20604");
        address.getAddressInformation();

        JSONObject response = DataRetrieval.getNaturalDisasterData(address);

        System.out.println(response.toString());

        assertNotEquals(emptyResponse, response.toString());
    }
}
