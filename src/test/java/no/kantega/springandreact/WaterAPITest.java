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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
public class WaterAPITest {

    private MockMvc mockMvc;

    @InjectMocks
    private HelloController helloController;

    @Before
    public void setUp() throws Exception{
        mockMvc = MockMvcBuilders.standaloneSetup(helloController).build();
    }

    @Test
    public void testResponseOk() throws Exception{
        /**
         * Tests for 200 OK response for API Get Request
         */

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/waterData/900%20West%2023rd%20St%20Austin%20Texas")
        )
            .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void testDataRetrievalValidAddress(){
        /**
         * Tests valid address and expects valid response for address in JSON format
         */

    }

    @Test
    public void testDataRetrievalInvalidAddress(){
        /**
         * Tests invalid address and expects error message or code from API
         */

    }

    @Test
    public void testValidAddressNoDataAvailable(){
        /**
         * test an address that is valid with a null response
         */
    }



}