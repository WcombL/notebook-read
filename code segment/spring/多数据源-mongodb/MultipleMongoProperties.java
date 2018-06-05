package com.zrkj.admin.web.config;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "mongodb")
public class MultipleMongoProperties {
	private MongoProperties idlink = new MongoProperties();
    private MongoProperties analyze = new MongoProperties();
	public MongoProperties getIdlink() {
		return idlink;
	}
	public void setIdlink(MongoProperties idlink) {
		this.idlink = idlink;
	}
	public MongoProperties getAnalyze() {
		return analyze;
	}
	public void setAnalyze(MongoProperties analyze) {
		this.analyze = analyze;
	}
    
}
