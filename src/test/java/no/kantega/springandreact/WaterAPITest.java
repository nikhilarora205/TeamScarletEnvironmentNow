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
public class WaterAPITest {

    private MockMvc mockMvc;

    @InjectMocks
    private HelloController helloController;

    @Before
    public void setUp() throws Exception{
        mockMvc = MockMvcBuilders.standaloneSetup(helloController).build();
        SpringAndReactApplication.removeFromMongoDB("water", "76119");

    }

    @After
    public void cleanUp(){
        //Cleans up Database from TestRetrieval Invalid Address
        SpringAndReactApplication.removeFromMongoDB("water", "WATERFAKEZIPSHOULDNOTBEINDATABASE");

    }

    @Test
    public void testResponseOk() throws Exception{
        /**
         * Tests for 200 OK response for API Get Request
         */

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/waterData/60171")
        )
            .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void testWaterAPI_HardCodedZipInURL_DatabaseStorage() throws Exception {
        /**
         * Tests hardcoded zipcode in URL, and should NOT store in Database
         * TODO: fix water api to reject zipcodes that are not valid, also should not store in database but it does
         */

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/waterData/WATERFAKEZIPSHOULDNOTBEINDATABASE")
        )
                .andExpect(MockMvcResultMatchers.status().isOk());

        String queryThatShouldBeNull = SpringAndReactApplication.queryMongoDB("water", "WATERFAKEZIPSHOULDNOTBEINDATABASE", true);
        assertNull(queryThatShouldBeNull);


    }

    @Test
    public void testWaterEmptyResponse() throws Exception{
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/waterData/SHOULDGIVEEMPTYRESPONSE")
        )
                .andReturn();

        String response = result.getResponse().getContentAsString();

        assertNull(response);
    }

    @Test
    public void testWaterValidResponse() throws Exception{
        /**
         * Setup removes any entries that contain "76119" zip code
         * should scrape website and return valid response (not null);
         */

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/waterData/76119")
        )
                .andReturn();

        String response = result.getResponse().getContentAsString();
        System.out.println(response);
        assertNotNull(response);
    }




}