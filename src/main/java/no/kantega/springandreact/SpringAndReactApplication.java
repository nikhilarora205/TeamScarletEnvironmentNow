package no.kantega.springandreact;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.json.JsonParser;
import org.springframework.context.annotation.Bean;

import java.util.*;
import org.bson.Document;
import com.mongodb.util.JSON;

import static com.mongodb.client.model.Filters.*;

import org.springframework.data.mongodb.MongoDbFactory;

import javax.print.Doc;


@SpringBootApplication
public class SpringAndReactApplication implements CommandLineRunner {


	private final static String key = "mongodb+srv://nikhilarora:soft461datatest@cluster0-kvrlc.gcp.mongodb.net/test?retryWrites=true&w=majority";
	private final static String databaseName = "environmentnow";

	public static void main(String[] args) {
		SpringApplication.run(SpringAndReactApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

	}

	public static void writeToMongoDB(String desiredCollection, String identifier, HashMap<String, String> values) {
		try {
			MongoClientURI uri = new MongoClientURI(key);
			MongoClient mongoClient = new MongoClient(uri);
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			MongoCollection<Document> collection = database.getCollection(desiredCollection);

			@SuppressWarnings("deprecation")
			Document document = new Document("zipcode", identifier);
			//-eberlein- thing is because fucking MongoDB doesn't accept a "." when writing
			//wasted way too fucking long figuring that out and trying to make everything work clean
			for (Map.Entry mapElement : values.entrySet()) {
				String key = (String) mapElement.getKey();
				String value = (String) mapElement.getValue();
				if (key.contains(".")) {
					System.out.println("here");
					key = key.replace(".", "-eberlein-");
				}
				document.append(key, value);
				System.out.println(key + " : " + value);
			}

			collection.insertOne(document);
			mongoClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void writeToMongoDB(String desiredCollection, Document toPut) {
		try {
			MongoClientURI uri = new MongoClientURI(key);
			MongoClient mongoClient = new MongoClient(uri);
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			MongoCollection<Document> collection = database.getCollection(desiredCollection);

			System.out.println("Storing air data: " + toPut.toString());
			collection.insertOne(toPut);
			mongoClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// FOR TESTING ONLY

	public static boolean removeFromMongoDB(String desiredCollection, String toRemove) {
		try {
			MongoClientURI uri = new MongoClientURI(key);
			MongoClient mongoClient = new MongoClient(uri);
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			MongoCollection<Document> collection = database.getCollection(desiredCollection);

			System.out.println("Removing DB entry with zipcode: " + toRemove.toString());
			BasicDBObject query = new BasicDBObject("zipcode", toRemove);
			Document remove = collection.findOneAndDelete(query);

			if(remove != null) {
				System.out.println("Removed: " + remove.toString());
			}

			mongoClient.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public static String queryMongoDB(String desiredCollection, String identifier) {
		try {
			MongoClientURI uri = new MongoClientURI(key);
			MongoClient mongoClient = new MongoClient(uri);

			@SuppressWarnings("deprecation")
			DB database = mongoClient.getDB(databaseName);
			DBCollection collection = database.getCollection(desiredCollection);

			System.out.println("success");

			BasicDBObject query = new BasicDBObject("zipcode", identifier);
			query.toJson();
			DBCursor test = collection.find(query);
			System.out.println("size of water data: " + test.size());
			if (test.size() == 0) return null;

			JSONObject toReturn = null;

			while (test.hasNext()) {
				DBObject doc = test.next();
				if (doc.toString() == null || doc.toString().length() == 0) {
					System.out.println("doc was empty");
					return null;
				}
				String intermediate = "";
				if (doc.toString().contains("-eberlein-")) {
					intermediate = doc.toString().replace("-eberlein-", ".");
				}
				toReturn = new JSONObject(intermediate);

				toReturn.remove("_id");
			}
			System.out.println("here: ");
			System.out.println(toReturn.toString());
			return toReturn.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}

	public static String queryMongoDB(String desiredCollection, String identifier, boolean hitThis) {
		try {
			MongoClientURI uri = new MongoClientURI(key);
			MongoClient mongoClient = new MongoClient(uri);

			@SuppressWarnings("deprecation")
			DB database = mongoClient.getDB(databaseName);
			DBCollection collection = database.getCollection(desiredCollection);

			System.out.println("success");

			BasicDBObject query = new BasicDBObject("zipcode", identifier);
			query.toJson();
			DBCursor test = collection.find(query);
			System.out.println("size of water data: " + test.size());
			if (test.size() == 0) return null;

			JSONObject toReturn = null;

			while (test.hasNext()) {
				DBObject doc = test.next();
				if (doc.toString() == null || doc.toString().length() == 0) {
					System.out.println("doc was empty");
					return null;
				}
				toReturn = new JSONObject(JSON.serialize(doc));
			}
			return toReturn.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}
}


