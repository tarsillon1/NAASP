package com.teamrocket.naasp.service.mongo;

import com.mongodb.*;
import com.teamrocket.naasp.service.commons.mongo.MongoCondition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import javax.net.ssl.SSLSocketFactory;

@Configuration
@EnableConfigurationProperties({ MongoParameters.class })
@Conditional(MongoCondition.class)
public class MongoConfiguration extends AbstractMongoConfiguration {
  private MongoClient mongoClient;

  private final MongoParameters mongoParameters;

  public MongoConfiguration(MongoParameters mongoParameters) {
    this.mongoParameters = mongoParameters;
  }

  @Override
  protected String getDatabaseName() {
    return mongoParameters.getDatabase();
  }

  @Bean
  public MongoDbFactory mongoDbFactory() {
    MongoClient mongo = (MongoClient) mongo();
    return new SimpleMongoDbFactory(mongo, getDatabaseName());
  }

  @Override
  public Mongo mongo() {
    if (this.mongoClient != null) {
      return this.mongoClient;
    }

    MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
    if (!mongoParameters.isIgnoreSSL()) {
      builder = builder.socketFactory(SSLSocketFactory.getDefault());
    }

    MongoClientOptions options = builder.socketTimeout(mongoParameters.getSocketTimeout())
            .connectTimeout(mongoParameters.getConnectTimeout())
            .readPreference(ReadPreference.valueOf(mongoParameters.getReadPreference()))
            .writeConcern(WriteConcern.valueOf(mongoParameters.getWriteConcern()))
            .maxWaitTime(mongoParameters.getWaitTimeout())
            .build();

    String host = mongoParameters.getHost();

    if (host == null) {
      throw new IllegalArgumentException("Unknown mongo host");
    }

    int port = mongoParameters.getPort();

    this.mongoClient = new MongoClient(new ServerAddress(host, port), options);

    return mongoClient;
  }

  @Bean
  public MongoTemplate mongoTemplate() throws Exception {
    MappingMongoConverter converter =
        new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory()),
            new MongoMappingContext());
    converter.setTypeMapper(new DefaultMongoTypeMapper(null));

    MongoTemplate template = new MongoTemplate(mongoDbFactory(), converter);
    return template;
  }

  @Bean
  public DB db(MongoTemplate mongoTemplate) {
    return mongoTemplate.getDb();
  }

  @Bean
  public MappingMongoConverter mappingMongoConverter() throws Exception {
    DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(this.mongoDbFactory());
    MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, this.mongoMappingContext());
    converter.setCustomConversions(this.customConversions());
    return converter;
  }

}