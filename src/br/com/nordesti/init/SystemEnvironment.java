package br.com.nordesti.init;

import java.io.IOException;

import br.com.nordesti.utils.ApplicationFileUtils;
import br.com.nordesti.utils.db.ConfigurationHSQLDB.Environment;

public class SystemEnvironment {
	String environment;
	
	static public Environment getEnviroment(){
		Environment result = null;
		
		try {
			ApplicationFileUtils propertieValue = new ApplicationFileUtils();
			String environment = propertieValue.getPropertieValue("application.properties", "db.environment");
			
			switch(environment){
			case "development":
				result = Environment.DEVELOPMENT;
				break;
			case "test":
				result = Environment.TEST;
				break;
			case "production":
				result = Environment.PRODUCTION;
				break;
			default:
				throw new IllegalArgumentException();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
