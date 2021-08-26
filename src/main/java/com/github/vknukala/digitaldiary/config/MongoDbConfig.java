/*
package com.github.vknukala.digitaldiary.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoDbConfig  {

    */
/*
     * Use the standard Mongo driver API to create a com.mongodb.client.MongoClient instance.
     *//*

    @Bean
    public MongoClient mongoClient(){
        return MongoClients.create("mongodb+srv://m001-student:m001-mongodb-basics@sandbox.hz9gs.mongodb.net");
    }

    @Bean
    public MongoOperations mongoOperations(){
        return new MongoTemplate(mongoClient(),"digital_diary");
    }

}
*/
