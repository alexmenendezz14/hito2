package com.empresa.hitoprogramacion;

import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {

    private MongoClient mongoClient;
    private MongoDatabase database;

    public MongoDBConnection(String connectionString) {
        MongoClientURI uri = new MongoClientURI(connectionString);
        this.mongoClient = MongoClients.create(String.valueOf(uri));
        this.database = mongoClient.getDatabase(uri.getDatabase());
    }

    public MongoDatabase getDatabase() {
        return this.database;
    }

    public void close() {
        if (this.mongoClient != null) {
            this.mongoClient.close();
        }
    }
}
