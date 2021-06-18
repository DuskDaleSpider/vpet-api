package com.dakota.vpet.configs;

import com.mongodb.ConnectionString;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages = { "com.dakota.vpet.repositories" })
public class ReactiveMongoConfiguration extends AbstractReactiveMongoConfiguration {
    private static String connectionString = System.getenv("MONGO_CONNECTION");

    @Bean
    @Primary
    public ReactiveMongoDatabaseFactory reactiveMongoClientFactory() {
        return new SimpleReactiveMongoDatabaseFactory(new ConnectionString(connectionString));
    }

    @Override
    protected String getDatabaseName() {
        return "vpet";
    }

    
}
