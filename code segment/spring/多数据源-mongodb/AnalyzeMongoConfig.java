package com.zrkj.admin.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.zrkj.infrastructure.mongo.analyze",mongoTemplateRef = AnalyzeMongoConfig.MONGO_TEMPLATE)
public class AnalyzeMongoConfig {
	protected static final String MONGO_TEMPLATE = "analyzeMongoTemplate";
}
