package com.zrkj.admin.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.MongoClient;

@Configuration
public class MultipleMongoConfig {
	@Autowired
	private MultipleMongoProperties mongoProperties;
	// When you put @EnableMongoAuditing on a configuration class, Spring will create a MappingContext bean.
	// Then you have to make sure the same mappingContext is being used in the MongoTemplate
	@Autowired
	private MongoMappingContext mongoMappingContext;

	@Primary
	@Bean(name = IdlinkMongoConfig.MONGO_TEMPLATE)
	public MongoTemplate idlinkMongoTemplate() throws Exception {
		MongoDbFactory factory = idlinkFactory(this.mongoProperties.getIdlink());
		MongoConverter converter = new MappingMongoConverter(factory, mongoMappingContext);
		return new MongoTemplate(factory, converter);
	}

	@Bean
	@Qualifier(AnalyzeMongoConfig.MONGO_TEMPLATE)
	public MongoTemplate analyzeMongoTemplate() throws Exception {
		MongoDbFactory factory = analyzeFactory(this.mongoProperties.getAnalyze());
		MongoConverter converter = new MappingMongoConverter(factory, mongoMappingContext);
		return new MongoTemplate(factory, converter);
		// return new
		// MongoTemplate(analyzeFactory(this.mongoProperties.getAnalyze()));
	}

	public MongoDbFactory idlinkFactory(MongoProperties mongo) throws Exception {
		return new SimpleMongoDbFactory(new MongoClient(mongo.getHost(), mongo.getPort()), mongo.getDatabase());
	}

	public MongoDbFactory analyzeFactory(MongoProperties mongo) throws Exception {
		return new SimpleMongoDbFactory(new MongoClient(mongo.getHost(), mongo.getPort()), mongo.getDatabase());
	}
}