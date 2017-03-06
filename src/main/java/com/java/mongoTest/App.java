package com.java.mongoTest;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
/**
 * This is a very simple application to learn and test most of the functionality available through the MongoDB Java Driver.
 */
public class App{
	
	private final static String DB_HOST = "localhost";
	
	private final static String DB_NAME = "mongo_learning";
	
	private final static String DB_COLLECTION = "mongo_tests";
	
	private final static int DB_PORT = 27017;
	
	private static ObjectMapper objectMapper;
	
	private static MongoClient client;
	
	private static MongoDatabase database;
	
	private static MongoCollection<Document> collection;
	
	public App(){
		
	}
		
		
    public static void main( String[] args ){
    	objectMapper = new ObjectMapper();
    	client = new MongoClient(DB_HOST, DB_PORT);
    	database = client.getDatabase(DB_NAME);
    	collection = database.getCollection(DB_COLLECTION);    	
    	//TODO call whatever
    	simpleReplaceDocumentNoEntity();
    }
    
    //----------------------------------
    // Methods without entities
    //----------------------------------
    
    /**
     * Insert a document.
     */
    public static void insertDocumentNoEntity(){
    	// Manually build the object because were not using a mapper yet.    	
    	List<Document> properties = new ArrayList<Document>();
    	Document property1 = new Document("name", "prop1")
    			.append("value", "p1value");
    	Document property2 = new Document("name", "prop2")
    			.append("value", "p2value");
    	properties.add(property1);
    	properties.add(property2);
    	Document insertionObject = new Document("test_name", "someName")
    			.append("test_properties", properties);
    	collection.insertOne(insertionObject);
    }
    
    /**
     * Add multiple documents.
     */
    public static void insertManyDocuments(){
    	List<Document> insertionDocuments = new ArrayList<Document>();
    	List<Document> emptyProperties = new ArrayList<Document>();
    	for(int i = 0; i < 15 ; i ++){
    		insertionDocuments.add(new Document("test_name", "someName"+i)
    				.append("test_properties", emptyProperties));
    	}
    	collection.insertMany(insertionDocuments);
    }
    
    /**
     * Simple deletion, removes document matching key and value.
     */
    public static void simpleDeleteDocumentNoEntity(){
    	Document query = new Document("test_name", "someName");
    	collection.deleteOne(query);
    }
    
    /**
     * Replaces the doc, new _id
     */
    public static void simpleReplaceDocumentNoEntity(){
    	//Build a document
    	List<Document> properties = new ArrayList<Document>();
    	Document property1 = new Document("name", "prop1")
    			.append("value", "p1value");
    	Document property2 = new Document("name", "prop2")
    			.append("value", "p2value");
    	Document property3 = new Document("name", "prop3")
    			.append("value", "p3value");
    	properties.add(property1);
    	properties.add(property2);
    	properties.add(property3);
    	Document insertionObject = new Document("test_name", "someOtherOtherName")
    			.append("test_properties", properties);
    	collection.replaceOne(eq("test_name", "someName2"), insertionObject);
    }
    
    /**
     * $set
     * Replaces the value of a field.
     */
    public static void setUpdateDocumentNoEntity(){
    	Document insertionObject = new Document("test_name", "someOtherName");
    	Document update = new Document("$set", insertionObject);
    	Document query = new Document("test_name", "someName");
    	collection.updateOne(query, update);
    }
    
    //----------------------------------
    // Methods using entities
    //----------------------------------
    
    //TODO


}
