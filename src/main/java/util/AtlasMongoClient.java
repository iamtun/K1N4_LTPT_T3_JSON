package util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class AtlasMongoClient {
	private static AtlasMongoClient instane = null;
	
	private MongoClient mongoClient;
	
	private AtlasMongoClient() {
		try(InputStream stream = AtlasMongoClient.class.getClassLoader().getResourceAsStream("config.properties")){
			Properties prop = new Properties();
			
			prop.load(stream);
			
			String uri = prop.getProperty("db.connect.url");
			
			ConnectionString connectionString = new ConnectionString(uri);
			MongoClientSettings clientSettings = 
					MongoClientSettings
					.builder()
					.applicationName("Exercise")
					.applyConnectionString(connectionString)
					.applyToConnectionPoolSettings(builder -> builder.maxSize(0))
					.applyToSocketSettings(builder -> builder.connectTimeout(5, TimeUnit.SECONDS))
					.build();
			
			mongoClient = MongoClients.create(clientSettings);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static synchronized AtlasMongoClient getInstane() {
		if(instane == null)
			instane = new AtlasMongoClient();
		return instane;
	}
	
	public MongoClient getMongoClient() {
		return mongoClient;
	}
	
}
