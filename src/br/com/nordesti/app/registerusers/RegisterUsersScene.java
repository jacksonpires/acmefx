package br.com.nordesti.app.registerusers;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import br.com.nordesti.app.main.MainApp;
import br.com.nordesti.app.main.MainAppController;
import br.com.nordesti.daos.UserHSQLDB;
import br.com.nordesti.models.User;
import br.com.nordesti.models.UserFX;
import br.com.nordesti.utils.ApplicationConstants;
import br.com.nordesti.utils.ApplicationUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableRow;
import javafx.stage.Stage;

public class RegisterUsersScene implements Observer {

	private String SCENE_NAME = "Usuários";
	
	private Observable observableApplication;
	private MainApp mainApp;
	private MainAppController mainAppController;
	private Stage stage = new Stage();
    private TabPane scene;
	private RegisterUsersController controller;
	
	private TableRow<UserFX> actualRow = new TableRow<>();
    
	/**
	 * Construtor
	 * @param mainApp
	 * @param mainAppController
	 * @param Observable
	 */
	public RegisterUsersScene(MainApp mainApp, MainAppController mainAppController, Observable observableApplication) {
		
		// Torna a classe principal acessível
		this.mainApp = mainApp;

		// Torna a classe observável
		this.observableApplication = observableApplication;
		observableApplication.addObserver(this);
						
		try {
			// Carrega o template FXML para scene
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource(ApplicationConstants.FXML_LIST_USERS));
	        this.scene = (TabPane) loader.load();
	        
            // Carrega e dá ao Controller acesso à MainApp
	        this.controller = loader.getController();
	        this.controller.setMainApp(this.mainApp);
	        this.controller.setRegisterUsersScene(this);

	        // Configura as preferências iniciaos da scene
	        // É necessário que seja depois da atribuição da controller
	        //   por conta do método setIntialPrefs() se referir à controller
	        this.scene.setLayoutX(0);
	        this.scene.setLayoutY(0);
//	        this.scene.prefWidthProperty().bind(this.mainApp.getMainStage().widthProperty());
//	        this.scene.prefHeightProperty().bind(this.mainApp.getMainStage().heightProperty());
	    		
	        setInitialPrefs(this.mainApp.getMainStage().getWidth(), this.mainApp.getMainStage().getHeight());
	        
		        	        
	        // Adiciona uma nova tab na aplicação
            mainAppController.setNewTab(ApplicationConstants.USERS_ICON, this.SCENE_NAME, scene);
            
	    } catch (IOException e) {
	        e.printStackTrace();
	    }	
	}
	
	/**
	 * Retorna o título do formulário
	 */
	public String getFormTitle(){
		return ApplicationUtils.getApplicationTitle(this.SCENE_NAME, this.mainApp);
	}
	
	/**
	 * Implementação do método update da classe Observable
	 */
	@Override
	public void update(Observable observable, Object obj) {
		setInitialPrefs(this.mainApp.getMainStage().getWidth(), this.mainApp.getMainStage().getHeight());
	}
	
	/**
	 * Configura as preferências iniciais da scene
	 * @param width
	 * @param height
	 */
	public void setInitialPrefs(Double width, Double height){
		this.scene.setPrefWidth(width);
		this.scene.setPrefHeight(height);
		this.controller.setRegisterUsersComponentsResize(width, height);	
	}
	
	
	public TabPane getTabPane(){
		return this.scene;
	}
	/**
	 * Database implementations
	 * 
	 */
	public void createUser(User user){
		this.scene.setCursor(Cursor.WAIT);
		
		try {
			UserHSQLDB userHSQLDB = new UserHSQLDB();
			userHSQLDB.create(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.scene.setCursor(Cursor.DEFAULT);
	}
	
	public void updateUser(User user){
		this.scene.setCursor(Cursor.WAIT);
		
		try {
			UserHSQLDB userHSQLDB = new UserHSQLDB();
			userHSQLDB.update(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.scene.setCursor(Cursor.DEFAULT);
	}
	
	public User searchByUsername(String username){
		this.scene.setCursor(Cursor.WAIT);
		
		try {
			UserHSQLDB userHSQLDB = new UserHSQLDB();
			this.scene.setCursor(Cursor.DEFAULT);
			
			return (userHSQLDB.searchByUsername(username));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.scene.setCursor(Cursor.DEFAULT);
		return null;
	}
	
	public void deleteUser(User user){
		this.scene.setCursor(Cursor.WAIT);
		
		try {
			UserHSQLDB userHSQLDB = new UserHSQLDB();
			userHSQLDB.delete(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.scene.setCursor(Cursor.DEFAULT);
	}
}
