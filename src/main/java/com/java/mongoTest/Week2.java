package com.java.mongoTest;

import java.util.Arrays;
import java.util.Iterator;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * This class has all the methods tested in the README file but in Java.
 * 
 * @author juaoose
 *
 */
public class Week2 {

	private final static String DB_HOST = "localhost";

	private final static String DB_NAME = "test";

	private final static String DB_COLLECTION = "people";

	private final static int DB_PORT = 27017;

	private final static String NAME = "name";

	private final static String LAST_NAME = "lastname";

	private final static String AGE = "age";

	private final static String BODY = "body";

	private final static String HOBBIES = "hobbies";

	private final static String BODY_LENGTH = "length";

	private final static String BODY_WIDTH = "width";

	private static MongoClient client;

	private static MongoDatabase database;

	private static MongoCollection<Document> collection;

	public static void main(String[] args) {
		client = new MongoClient(DB_HOST, DB_PORT);
		database = client.getDatabase(DB_NAME);
		collection = database.getCollection(DB_COLLECTION);
		// Clear it everytime
		collection.drop();
		// Methods here
	}

	//////////////////////////////////////////
	// Public methods
	//////////////////////////////////////////

	public static void testInsertOne(MongoCollection<Document> collection) {
		Document person = new Document(NAME, "juan")
				.append(AGE, 22)
				.append(BODY, new Document(BODY_LENGTH, 165)
						.append(BODY_WIDTH, 60))
				.append(HOBBIES, Arrays.asList("Videogames", "Tennis", "Cars", "Bikes"));

		collection.insertOne(person);
	}

	public static void testInsertMany(MongoCollection<Document> collection) {
		Document lina = new Document(NAME, "lina").append(LAST_NAME, "rodriguez")
				.append(AGE, 25)
				.append(BODY, new Document(BODY_LENGTH, 165)
						.append(BODY_WIDTH, 60))
				.append(HOBBIES, Arrays.asList("Design", "Tennis", "Art", "Bikes"));
		Document luis = new Document(NAME, "luis")
				.append(AGE, 33)
				.append(BODY, new Document(BODY_LENGTH, 175)
						.append(BODY_WIDTH, 70))
				.append(HOBBIES, Arrays.asList("Guns", "Soccer", "Girls", "Party"));

		collection.insertMany(Arrays.asList(lina, luis));
	}
	
	public static void testFindUsingParams(MongoCollection<Document> collection) {
		Document query = new Document(NAME, "juan");
		FindIterable<Document> response = collection.find(query);
		System.out.println("Find using params:");
		for (Document document : response) {
			System.out.println(document.toJson().toString());
		}
	}

	public static void testFindNestedDocument(MongoCollection<Document> collection) {
		Document query = new Document(BODY.concat(".").concat(BODY_WIDTH), 10);
		FindIterable<Document> response = collection.find(query);
		System.out.println("Find nested document");
		for (Document document : response) {
			System.out.println(document.toJson().toString());
		}
	}
	
	// Array Equality
	public static void testFindEntireArrayEquality(MongoCollection<Document> collection){
		
	}

}
