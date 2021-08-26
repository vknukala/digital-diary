package com.github.vknukala.digitaldiary;

import com.github.vknukala.digitaldiary.model.User;
import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.util.List;

/**
 * This is MongoDB connection test. It uses {@link SimpleMongoClientDatabaseFactory} to get the
 * {@link com.mongodb.client.MongoDatabase} object with additional {@code username}, {@code password}
 * and {@code dbname}
 *
 */
public class MongoDBConnectionTest {

    static MongoOperations mongoOps;

    @BeforeAll
    public static void init(){
        mongoOps =  new MongoTemplate(new SimpleMongoClientDatabaseFactory(
                MongoClients.create("mongodb+srv://m001-student:m001-mongodb-basics@sandbox.hz9gs.mongodb.net/"),
                "digital_diary"));
    }

    @Test
    public void testUserDetails(){
        List<User> users = mongoOps.findAll(User.class,"users");
        Assertions.assertEquals(3,users.size(),"The total users are");
    }
}
