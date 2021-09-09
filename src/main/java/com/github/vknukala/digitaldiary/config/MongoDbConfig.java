package com.github.vknukala.digitaldiary.config;

import com.github.vknukala.digitaldiary.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableMongoAuditing
public class MongoDbConfig  {

     /** Use the standard Mongo driver API to create a com.mongodb.client.MongoClient instance.


    @Bean
    public MongoClient mongoClient(){
        return MongoClients.create("mongodb+srv://m001-student:m001-mongodb-basics@sandbox.hz9gs.mongodb.net");
    }

    @Bean
    public MongoOperations mongoOperations(){
        return new MongoTemplate(mongoClient(),"digital_diary");
    }**/

    /**
     * Enable auditing
     * @return
     */
     @Bean
    public AuditorAware<String> auditProvider(){
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = null;
            if (authentication == null || !authentication.isAuthenticated()) {
                user =  (User) authentication.getPrincipal();
            }

            return Optional.ofNullable(user).map(User::getUsername);
         };
     }

}
