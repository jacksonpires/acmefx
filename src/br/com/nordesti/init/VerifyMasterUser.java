package br.com.nordesti.init;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.logging.Logger;

import br.com.nordesti.daos.UserHSQLDB;
import br.com.nordesti.models.User;
import br.com.nordesti.utils.ApplicationFileUtils;

public class VerifyMasterUser {
	private String masterUsername;
	private String masterName;
	private String masterPassword;
	private Date registerDate;
	private Time registerTime;

	public VerifyMasterUser() { 
	}
	
	public void run(){
		ApplicationFileUtils propertieValue = new ApplicationFileUtils();

		UserHSQLDB userHSQLDB = new UserHSQLDB();
		User user = new User();

		try {
			this.masterUsername = propertieValue.getPropertieValue("application.properties", "app.master.username");
		} catch (IOException err) {
			err.printStackTrace();
		}
		
		user = userHSQLDB.searchByUsername(this.masterUsername);
		
		if (user == null) {
			//Usuário NÃO existe. Criando...
						
			try {
				this.masterName = propertieValue.getPropertieValue("application.properties", "app.master.name");
				this.masterPassword = propertieValue.getPropertieValue("application.properties", "app.master.password");
				this.registerDate = new Date(System.currentTimeMillis());
				this.registerTime = new Time(System.currentTimeMillis());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			User newUser = new User();
			
			newUser.setUsername(this.masterUsername);
			newUser.setName(this.masterName);
			newUser.setPassword(this.masterPassword);
			
			userHSQLDB.create(newUser);
		} else {
			//Usuário já existe...
		}
	}

}
