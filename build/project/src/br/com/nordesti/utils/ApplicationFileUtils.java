package br.com.nordesti.utils;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.configuration.PropertiesConfiguration;

public class ApplicationFileUtils {
	
	/**
	 * Get values of a given file
	 */
	public String getPropertieValue(String propertieFileName, String propertie) throws IOException {
		 
		String result = "";
		Properties prop = new Properties();
 
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertieFileName);
		prop.load(inputStream);
		if (inputStream == null) {
			throw new FileNotFoundException("property file '" + propertieFileName + "' not found in the classpath");
		}

		return prop.getProperty(propertie);
	}
	
	/**
	 * Saves last user that logged in on properties file
	 */
	static public void saveLastLoginChanges(String username) {
	    try {
		    	PropertiesConfiguration config = new PropertiesConfiguration(ApplicationConstants.APPLICATION_PROPERTIES);
		    	config.setProperty(ApplicationConstants.APPLICATION_PROPERTIES_APP_LAST_USERNAME, username);
		    	config.save();
	    	} catch (Exception e ) {
	        e.printStackTrace();
	    }
	}

}
