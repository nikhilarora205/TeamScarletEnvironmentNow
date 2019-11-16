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
public class HelloControllerTest {

	private static final int DEFAULT_TIME_OUT = 3000;

	private MockMvc mockMvc;

	@InjectMocks
	private HelloController helloController;

	@Before
	public void setUp() throws Exception{
		mockMvc = MockMvcBuilders.standaloneSetup(helloController).build();
	}

	@Test
	public void testAllApiResponses() throws Exception {
		/**
		 * give valid address expect valid responses from all api's
		 */

		String address = "509%20Willow%20Creek%20Ct%20Arlington%20TX";

		//parse addresses in {Number}%20{Street Name}%20{street,drive,avenue,etc.}%20{city}%20{state}


		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/waterData/" + address)
		)
				.andExpect(MockMvcResultMatchers.status().isOk());

		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/AQIData/"+address)
		)
				.andExpect(MockMvcResultMatchers.status().isOk());

		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/naturalDisasters/"+address)
		)
				.andExpect(MockMvcResultMatchers.status().isOk());
	}


	@Test

	public void testGetLocationAddressToZip(){
		/**
		 * Test Valid Address, get correct zipcode in return
		 */

		HelloController helloController = new HelloController();

		assertEquals("76103", helloController.getLocation("1017 Blue Lake Dr. Fort Worth, TX", 0));

	}

	@Test
	public void testGetLocationZipToZip(){
		/**
		 * Test valid zip input should return the same zipcode
		 */

		HelloController helloController = new HelloController();

		assertEquals("60141", helloController.getLocation("60141", 0));

	}

//	@Test
//	public void testGetLocationInvalidAddress(){
//		/**
//		 * Test invalid address input should return please narrow search prompt
//		 */
//
//		HelloController helloController = new HelloController();
//
//		assertEquals("Please narrow search", helloController.getLocation("1283 Fakeroad St. Chicago, IL", 0));
//
//	}
//
//	@Test
//	public void testGetLocationInvalidZip(){
//		/**
//		 * Test invalid zip input should return the please narrow search prompt
//		 */
//		HelloController hController = new HelloController();
//
//		assertEquals("Please narrow search", hController.getLocation("11111", 0));
//
//	}

	@Test(timeout = DEFAULT_TIME_OUT)
	public void testReverseLocation(){
		/**
		 */
		HelloController hController = new HelloController();

		for(int i =0; i < 3; i ++) {
			assertEquals("Texas", hController.reverseLocation("76"+i+"03"));
		}
	}

	@Test(timeout = DEFAULT_TIME_OUT)
	public void testReverseLocation2(){
		/**
		 */
		HelloController hController = new HelloController();

		assertEquals("Illinois", hController.reverseLocation("60141"));
	}

	@Test(timeout = DEFAULT_TIME_OUT)
	public void testReverseLocation3(){
		/**
		 */
		HelloController hController = new HelloController();

		assertEquals("New York", hController.reverseLocation("10025"));
	}

}
