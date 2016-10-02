package br.com.nordesti.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import br.com.nordesti.utils.ApplicationDateUtils;
import br.com.nordesti.utils.ApplicationStates;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserFX {

	private final ObjectProperty<ApplicationStates> state;
	private final StringProperty username;
	private final StringProperty name;
	private final StringProperty password;
	
	public static User toUser(UserFX userFX){
		User user = new User();
		
		user.setUsername(userFX.getUsername());
		user.setName(userFX.getName());
		user.setPassword(userFX.getPassword());
		return user;
	}
    
	public UserFX(User user){
		this.state = new SimpleObjectProperty<ApplicationStates>(ApplicationStates.IDLE);
		this.username = new SimpleStringProperty(user.getUsername());
        this.name = new SimpleStringProperty(user.getName());
		this.password = new SimpleStringProperty(user.getPassword());
	}
		
	public ApplicationStates getState() {
		return state.get();
	}
	
	public void setState(ApplicationStates state) {
		this.state.set(state);
	}
	
	public ObjectProperty<ApplicationStates> state(){
		return state;
	}
	
	
	
	
	public String getUsername() {
		return username.get();
	}
	
	public void setUsername(String username) {
		this.username.set(username);
	}
	
	public StringProperty usernameProperty() {
        return username;
    }
	
	
  
	public String getName() {
		return name.get();
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public StringProperty nameProperty() {
        return name;
    }

	
	
	
	public String getPassword() {
		return password.get();
	}
	
	public void setPassword(String password) {
		this.password.set(password);
	}
	
	public StringProperty passwordProperty() {
        return password;
    }
	
}
