package no.kantega.springandreact;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(SpringRunner.class)
public class HelloControllerTests {
	private static final int DEFAULT_TIME_OUT = 3000;

	private MockMvc mockMvc;

	@InjectMocks
	private HelloController helloController;

	@Before
	public void setUp() throws Exception{
		mockMvc = MockMvcBuilders.standaloneSetup(helloController).build();
	}

	public String addressFormatter(String address){
	    return(address.replaceAll("//s", "%20"));

    }

    @Test
    public void testGetValidAddressWithValidAddress(){
        /**
         * tests function to validate addresses, returns string
         * "incorrect" = incorrect address, "true" = valid address, or "narrow" = needs to be narrowed
         */

        String address = addressFormatter("1716 Iverson St Oxon Hill MD 20745");

        assertEquals("true", helloController.getValid(address));
    }

    @Test
    public void testGetValidAddressWithInvalidAddress(){
        /**
         * tests function to validate addresses, returns string
         * "incorrect" = incorrect address, "true" = valid address, or "narrow" = needs to be narrowed
         */

        String address = addressFormatter("1111 Dumbaddress St Fake TX 76103");

        assertEquals("incorrect", helloController.getValid(address));
    }

    @Test
    public void testGetValidAddressWithVagueAddress(){
        /**
         * tests function to validate addresses, returns string
         * "incorrect" = incorrect address, "true" = valid address, or "narrow" = needs to be narrowed
         */

        String address = addressFormatter("Austin");

        assertEquals("narrow", helloController.getValid(address));
    }

    @Test
    public void testGetValidAddressWithVagueAddressTwo(){
        /**
         * tests function to validate addresses, returns string
         * "incorrect" = incorrect address, "true" = valid address, or "narrow" = needs to be narrowed
         */

        String address = addressFormatter("New York");

        assertEquals("narrow", helloController.getValid(address));
    }

    @Test
    public void testGetValidAddressWithVagueAddressThree(){
        /**
         * tests function to validate addresses, returns string
         * "incorrect" = incorrect address, "true" = valid address, or "narrow" = needs to be narrowed
         */

        String address = addressFormatter("Washington");

        assertEquals("narrow", helloController.getValid(address));
    }



    @Test
    public void test_getDataValidAddressWithKnownData(){
        /**
         * tests getData method
         */

        String address = addressFormatter("1017 Blue Lake Dr Fort Worth TX 76103");

        assertEquals("true", helloController.getValid(address));
    }

    //32013, 48033, 20604, 14029, 54201, 03217, 63101




}
