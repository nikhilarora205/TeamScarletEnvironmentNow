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
public class AQIAPITest {

    private MockMvc mockMvc;

    @InjectMocks
    private HelloController helloController;

    @Before
    public void setUp() throws Exception{
        mockMvc = MockMvcBuilders.standaloneSetup(helloController).build();
        SpringAndReactApplication.removeFromMongoDB("air", "76119");
    }

    @After
    public void cleanUp(){
        //Cleans up Database from TestRetrieval Invalid Address
        try {
            SpringAndReactApplication.removeFromMongoDB("air", "AQIFAKEZIPSHOULDNOTBEINDATABASE");
        }catch(Exception e){
            System.out.println("Removal failed, so AQIFAKEZIPSHOULDNOTBEINDATABASE was not there");
        }
    }

    @Test
    public void testAQIAPI_HardCodedZipInURL_DatabaseStorage() throws Exception {
        /**
         * Tests hardcoded zipcode in URL, and should NOT store in Database
         */

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/AQIData/AQIFAKEZIPSHOULDNOTBEINDATABASE")
        )
                .andExpect(MockMvcResultMatchers.status().isOk());

        String queryThatShouldBeNull = SpringAndReactApplication.queryMongoDB("air", "AQIFAKEZIPSHOULDNOTBEINDATABASE", true);
        assertNull(queryThatShouldBeNull);

    }

    @Test
    public void testAQIAPIEmptyResponse() throws Exception{
        /**
         * Tests calling API with an hardcoded zipcode that should be rejected
         *
         */

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/AQIData/AQISHOULDGIVEEMPTYRESPONSE")
        )
                .andReturn();

        String response = result.getResponse().getContentAsString();

        assertEquals("{}", response);
    }

    @Test
    public void testAQIValidResponse() throws Exception{
        /**
         * Setup removes any entries that contain "76119" zip code
         * should scrape website and return valid response (not null);
         */

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/AQIData/76119")
        )
                .andReturn();

        String response = result.getResponse().getContentAsString();
        System.out.println(response);
        assertNotNull(response);
    }







}