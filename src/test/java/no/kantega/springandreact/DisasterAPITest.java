package no.kantega.springandreact;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class DisasterAPITest {

    private MockMvc mockMvc;

    @InjectMocks
    private HelloController helloController;

    @Before
    public void setUp() throws Exception{
        mockMvc = MockMvcBuilders.standaloneSetup(helloController).build();
    }


    @Test
    public void testDisasterDataGivenValidZipCode(){
        /**
         * Test to make sure given a zipcode that it returns data
         */
    }

    @Test
    public void testDisasterDataGivenInvalidZipCode(){
        /**
         * Give API invalid API and make sure we get an empty response/null response
         * TODO: Should we even test invalid zips for this API?
         */
    }

    @Test
    public void testDisasterDataGivenValidAddress(){
        /**
         * Just Test an Address
         */
    }






}
