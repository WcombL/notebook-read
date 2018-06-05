package com.zrkj.admin.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.zrkj.infrastructure.mongo.idlink",mongoTemplateRef = IdlinkMongoConfig.MONGO_TEMPLATE)
public class IdlinkMongoConfig {
	protected static final String MONGO_TEMPLATE = "idlinkMongoTemplate";
}
