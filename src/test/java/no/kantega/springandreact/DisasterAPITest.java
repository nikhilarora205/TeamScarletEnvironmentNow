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


@RunWith(SpringRunner.class)
public class DisasterAPITest {

    private MockMvc mockMvc;

    @InjectMocks
    private HelloController helloController;

    @Before
    public void setUp() throws Exception{
        mockMvc = MockMvcBuilders.standaloneSetup(helloController).build();
    }


    @Test
    public void testAQIAPIEmptyResponse() throws Exception{
        /**
         * Tests calling API with an hardcoded zipcode that should be rejected
         *
         */

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/naturalDisasters/NATDISSHOULDGIVEEMPTYRESPONSE")
        )
                .andReturn();

        String response = result.getResponse().getContentAsString();

        assertEquals("Failed to query disaster data", response);
    }

    @Test
    public void testDisasterDataGivenValidZipCode() throws Exception{
        /**
         * Test to make sure given a zipcode that it returns data
         */

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/naturalDisasters/78705")
        )
                .andReturn();

        String response = result.getResponse().getContentAsString();

        assertNotEquals("Failed to query disaster data", response);

    }

    @Test
    public void testDisasterDataGivenInvalidZipCode() throws Exception{
        /**
         * Give API invalid API and make sure we get an empty response/null response
         */

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/naturalDisasters/11111")
        )
                .andReturn();

        String response = result.getResponse().getContentAsString();

        assertEquals("Failed to query disaster data", response);

    }

    @Test
    public void testDisasterDataGivenValidAddress() throws Exception{
        /**
         * Just Test an Address
         */

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/naturalDisasters/509%20Willow%20Creek%20Ct%20Arlington%20TX")
        )
                .andReturn();

        String response = result.getResponse().getContentAsString();

        assertNotEquals("Failed to query disaster data", response);
    }






}
