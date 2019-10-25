package no.kantega.springandreact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import java.util.*;
import org.bson.Document;
import com.mongodb.util.JSON;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoClient;
import com.mongodb.BasicDBObject;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.springframework.data.mongodb.MongoDbFactory;
import com.mongodb.Block;
@SpringBootApplication
//public class SpringAndReactApplication {
public class SpringAndReactApplication implements CommandLineRunner {
	@Autowired
	private ZipCodeRepository repository;


	public static void main(String[] args) {
		SpringApplication.run(SpringAndReactApplication.class, args);

	}





	@Override
	public void run(String... args) throws Exception{

		MongoClientURI uri = new MongoClientURI(
				"mongodb://nikhilarora:soft461datatest@cluster0-shard-00-00-kvrlc.gcp.mongodb.net:27017,cluster0-shard-00-01-kvrlc.gcp.mongodb.net:27017,cluster0-shard-00-02-kvrlc.gcp.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true&w=majority");

		MongoClient mongoClient = new MongoClient(uri);

		DB database = mongoClient.getDB("environmentnow");
		DBCollection collection = database.getCollection("locations");
		BasicDBObject document = new BasicDBObject();
		document.put("zipcode", "75062");
		document.put("aqi", "52");

		//collection.insert(document);
		//^that's how you stick shit into Database
		System.out.println("success");

		//this code doesn't work but i was trying to query the database
		BasicDBObject query = new BasicDBObject("zipcode", "75062");
		query.toJson();
		DBCursor test = collection.find(query);
		while(test.hasNext()){
			System.out.println(test.next());
		}

		/*repository.deleteAll();

		//save a couple of zip codes
		repository.save(new ZipCode("76103", "Tarrant", "12", "12"));
		repository.save(new ZipCode("76112", "Dallas", "22", "22"));

		System.out.println("ZipCodes found with findAll():");
		System.out.println("-------------------------------");
		for(ZipCode zip : repository.findAll()){
			System.out.println(zip);
		}
		System.out.println();
		*/





	}

}
