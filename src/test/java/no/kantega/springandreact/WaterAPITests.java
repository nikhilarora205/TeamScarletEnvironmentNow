package no.kantega.springandreact;

import static org.junit.Assert.*;

import org.json.JSONException;
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

@RunWith(SpringRunner.class)
public class WaterAPITests {

    String emptyResponse = "{\"waterData\":[]}";

    //TEST Addresses with Featured Utilities

    @Test
    public void testWaterWithFeaturedUtility(){
        /**
         * tests water api with zipcode that has a featured utility
         * expected: returns a non-empty response
         */

        Address address = new Address("900 West 23rd St Austin TX 78705");
        address.getAddressInformation();

        assertNotEquals(emptyResponse, DataRetrieval.getWaterData(address).toString());

    }

    @Test
    public void testWaterWithFeaturedUtilityTwo(){
        /**
         * tests water api with zipcode that has a featured utility
         * expected: returns a non-empty response
         */

        Address address = new Address("1017 Blue Lake Dr Fort Worth TX 76103");
        address.getAddressInformation();

        assertNotEquals(emptyResponse, DataRetrieval.getWaterData(address).toString());

    }

    @Test
    public void testWaterWithFeaturedUtilityThree(){
        /**
         * tests water api with zipcode that has a featured utility
         * expected: returns a non-empty response
         */

        Address address = new Address("10021");
        address.getAddressInformation();

        assertNotEquals(emptyResponse, DataRetrieval.getWaterData(address).toString());

    }


    //TEST addresses without featured utilities

    @Test
    public void testWaterWithoutFeaturedUtility() throws JSONException {
        /**
         * tests water api with zipcode that does not have a featured utility
         * expected: returns a non-empty response
         */

        Address address = new Address("20604");
        address.getAddressInformation();

        JSONObject temp = DataRetrieval.getWaterData(address);

        assertNotEquals(emptyResponse, temp.toString());
    }

    @Test
    public void testWaterWithoutFeaturedUtilityTwo() throws JSONException {
        /**
         * tests water api with zipcode that does not have a featured utility
         * expected: returns a non-empty response
         */

        Address address = new Address("57350");
        address.getAddressInformation();

        JSONObject temp = DataRetrieval.getWaterData(address);

        assertNotEquals(emptyResponse, temp.toString());
    }

    @Test
    public void testWaterWithoutFeaturedUtilityThree() throws JSONException {
        /**
         * tests water api with zipcode that does not have a featured utility
         * expected: returns a non-empty response
         */

        Address address = new Address("12345");
        address.getAddressInformation();

        JSONObject temp = DataRetrieval.getWaterData(address);

        assertNotEquals(emptyResponse, temp.toString());
    }

    @Test
    public void testWaterWithoutFeaturedUtilityFour() throws JSONException {
        /**
         * tests water api with zipcode that does not have a featured utility
         * expected: returns a non-empty response
         */

        Address address = new Address("14029");
        address.getAddressInformation();

        JSONObject temp = DataRetrieval.getWaterData(address);

        assertNotEquals(emptyResponse, temp.toString());
    }

    @Test
    public void testWaterWithoutFeaturedUtilityFive() throws JSONException {
        /**
         * tests water api with zipcode that does not have a featured utility
         * expected: returns a non-empty response
         */

        Address address = new Address("32013");
        address.getAddressInformation();

        JSONObject temp = DataRetrieval.getWaterData(address);

        assertNotEquals(emptyResponse, temp.toString());
    }


}