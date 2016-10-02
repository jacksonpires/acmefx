package br.com.nordesti.app.login;

import java.io.IOException;

import br.com.nordesti.app.main.MainApp;
import br.com.nordesti.utils.ApplicationConstants;
import br.com.nordesti.utils.ApplicationFileUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LoginStage {
	private MainApp mainApp;
	private Stage stage = new Stage();
    private Pane scene;
	private LoginController controller;
    
	public LoginStage(MainApp mainApp) {
		this.mainApp = mainApp;
		
		try {
			// Load the FXML template 
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource(ApplicationConstants.FXML_LOGIN));
	        scene = (Pane) loader.load();
	
            // Carrega e dá ao Controller acesso à MainApp
            controller = loader.getController();
            controller.setMainApp(this.mainApp);

            // Cria a cena/janela
	        Scene scene = new Scene(this.scene);
	        this.stage.setScene(scene);
	        this.stage.setTitle(mainApp.getApplicationSign() + " - " + mainApp.getApplicationName());
	        
	        controller.setStageForm(this.stage);
           
	        // Adiciona o evento que interrompe o fechamento da janela via teclado
	        this.stage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            	public void handle(WindowEvent windowEvent){
            			windowEvent.consume(); // Consome o evento para não se propagar
            			mainApp.handleExitApplication();	
	            	}
	        });

      	     // Passa o foco inicial para o textFieldUser
	        ApplicationFileUtils propertieValue = new ApplicationFileUtils();
			controller.setTextFieldUsername(propertieValue.getPropertieValue(ApplicationConstants.APPLICATION_PROPERTIES, 
																			ApplicationConstants.APPLICATION_PROPERTIES_APP_LAST_USERNAME));
	        controller.requestFocusTextFieldPassword(); 
	    } catch (IOException e) {
	        e.printStackTrace();
	    }	
	}

	
    /**
     * Mostra o formulário de login
     */
	public void show(){
		stage.show();
	}

}
