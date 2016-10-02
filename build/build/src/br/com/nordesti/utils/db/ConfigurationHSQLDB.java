package br.com.nordesti.utils.db;

import java.io.IOException;

import br.com.nordesti.init.SystemEnvironment;
import br.com.nordesti.utils.ApplicationFileUtils;

public class ConfigurationHSQLDB implements DBConfigurationDAO {
	Environment environment;
	String dbName;
	String url;
	String appPath;
	String dbProperties;
	String fullUrl;
	
	public enum Environment {
		DEVELOPMENT, TEST, PRODUCTION
	}
	
	public ConfigurationHSQLDB() throws IOException {
		ApplicationFileUtils propertieValue = new ApplicationFileUtils();
		this.dbName = propertieValue.getPropertieValue("application.properties", "db.name");

		this.environment = SystemEnvironment.getEnviroment();		
		this.url = "jdbc:hsqldb:file:";
		this.appPath = System.getProperty("user.dir") + "/db";
		this.dbProperties = ";shutdown=true";
	}
	
	public ConfigurationHSQLDB(Environment environment) throws IOException {
		ApplicationFileUtils propertieValue = new ApplicationFileUtils();
		this.dbName = propertieValue.getPropertieValue("application.properties", "database");
		
		this.environment = environment;
		this.url = "jdbc:hsqldb:file:";
		this.appPath = System.getProperty("user.dir") + "/db";
		this.dbProperties = ";shutdown=true";
	}

	@Override
	public String URLConnection() throws IOException {
		return (this.url + this.appPath + "/" + this.environment + "/" + this.dbName + this.dbProperties);
	}

}
