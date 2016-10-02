package br.com.nordesti.models;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;

import org.junit.Test;

import br.com.nordesti.daos.UserHSQLDB;
import br.com.nordesti.utils.ApplicationFileUtils;
import br.com.nordesti.utils.db.ConfigurationHSQLDB.Environment;

public class TestUser {

	@Test
	public void testMasterUser() throws IOException {
		UserHSQLDB userHSQLDB = new UserHSQLDB(Environment.TEST);
		User user = new User();
		
		ApplicationFileUtils propertieValue = new ApplicationFileUtils();
		
		try {
			user.setUsername(propertieValue.getPropertieValue("application.properties", "app.master.username"));
			user.setName(propertieValue.getPropertieValue("application.properties", "app.master.name"));
			user.setPassword(propertieValue.getPropertieValue("application.properties", "app.master.password"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		userHSQLDB.create(user);
		user = userHSQLDB.searchByUsername("master");
		assertEquals("Verifica o usuário mestre...", propertieValue.getPropertieValue("application.properties", "app.master.username"), user.getUsername());
	}

}
