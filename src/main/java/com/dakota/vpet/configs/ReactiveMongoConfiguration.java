package com.dakota.vpet.configs;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;

@Configuration
public class ReactiveMongoConfiguration extends AbstractReactiveMongoConfiguration {
    private static String connectionString = System.getenv("MONGO_CONNECTION");

    @Bean
    public MongoClient reactiveMongoClient() {
        return MongoClients.create(connectionString);
    }

    @Override
    protected String getDatabaseName() {
        return "vpet";
    }

}
