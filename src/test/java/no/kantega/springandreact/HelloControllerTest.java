package no.kantega.springandreact;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HelloControllerTest {

	@Test
	public void testAllApiResponses(){
		/**
		 * give valid address expect valid responses from all api's
		 */
	}

	@Test
	public void testAllApiResponsesInvalidAddress(){
		/**
		 * give invalid address expect error responses from all api's
		 */
	}

	@Test
	public void testForDataStorage() {
		/**
		 * call api's with same address twice (make sure api's scrape originally)
		 * then the second time make sure the api's are retrieving the data from the DB (current data)
		 */
	}

	@Test
	public void testForOldDataInDB(){
		/**
		 * Force add old entry to database and call API
		 * Make sure the API responds with new scraped data instead of old DB data
		 */
	}

}
