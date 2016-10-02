package br.com.nordesti.utils.db;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class DBConnection {
	String environment;
	String dbName;
	String url;
	String appPath;
	String dbProperties;
	String fullUrl;
	
	/*
	 * Get a new Hibernate Session
	 */
	public static Session getHibernateSession(DBConfigurationDAO dbConfiguration) throws IOException {
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
		Configuration configuration = new Configuration().configure(); 
		configuration.setProperty("hibernate.connection.url", dbConfiguration.URLConnection());
		configuration.configure();
		
		logger.info(">>>>>>>> [DATABASE URL] " + configuration.getProperty("hibernate.connection.url"));
		
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		SessionFactory factory = configuration.buildSessionFactory(builder.build());
		
		return factory.openSession();
	}
}
