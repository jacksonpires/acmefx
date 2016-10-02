package br.com.nordesti.app.main;
	
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.nordesti.app.login.LoginStage;
import br.com.nordesti.daos.UserHSQLDB;
import br.com.nordesti.init.VerifyMasterUser;
import br.com.nordesti.models.User;
import br.com.nordesti.models.UserFX;
import br.com.nordesti.utils.ApplicationDateUtils;
import br.com.nordesti.utils.ApplicationConstants;
import br.com.nordesti.utils.ApplicationMessage;
import br.com.nordesti.utils.ApplicationObservable;
import br.com.nordesti.utils.ApplicationFileUtils;
import br.com.nordesti.utils.ScreenUtils;
import br.com.nordesti.utils.TimeUtils;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {
		
	ApplicationObservable observableApplication; 
	private Double APP_HEIGHT;
	private Double APP_WIDTH;

	private String applicationName;
	private String applicationSign;
	private Stage mainStage;
	private Scene mainScene;
	private VBox mainVBox;
	private MainAppController mainController;
    private LoginStage loginForm;
    

    /**
     * Inicializa a aplicação
     */
    @Override
    public void start(Stage primaryStage) {
		APP_HEIGHT = ScreenUtils.getScreenHeight();
		APP_WIDTH = ScreenUtils.getScreenWidth();
		
    		// Seta as variáveis da aplicação
    		setApplicationProperties();
        
    		// Cria janela principal
    	    this.mainStage = primaryStage;
    	    this.mainStage.setWidth(APP_WIDTH);
    	    this.mainStage.setHeight(APP_HEIGHT);
        this.mainStage.setTitle(this.applicationSign + " - " + this.applicationName);

        // Verificar se existe usuário master cadastrado no BD
        VerifyMasterUser verifyMasterUser = new VerifyMasterUser();
        verifyMasterUser.run();
        
        initRootLayout(); // Inicia o layout principal mas não mostra
        
        // Cria e mostra o formulário de login
        loginForm = new LoginStage(this);
        loginForm.show();        
    }
             
    /**
     * Gerencia a saída da aplicação
     */
	public void handleExitApplication(){
		if (ApplicationMessage.Confirmation("Deseja realmente sair?").get() == ButtonType.OK){
			System.exit(0);
		}
	}
	
    /**
     * Retorna o nome da aplicação
     */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * Retorna a SIGLA da aplicação
	 */
	public String getApplicationSign() {
		return applicationSign;
	}

	/**
     * Inicializa as variáveis da aplicação
     */
    private void setApplicationProperties(){
    		ApplicationFileUtils propertieValue = new ApplicationFileUtils();
    		
		try {
			this.applicationName =  propertieValue.getPropertieValue(ApplicationConstants.APPLICATION_PROPERTIES, 
																	ApplicationConstants.APPLICATION_PROPERTIES_APP_NAME);
			
			this.applicationSign = propertieValue.getPropertieValue(ApplicationConstants.APPLICATION_PROPERTIES, 
																   ApplicationConstants.APPLICATION_PROPERTIES_APP_SIGN);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
    }
    
	/**
     * Mostra o root layout (layout base).
     */
    public void showRootLayout(){
    		mainStage.show();
    }

    /**
     * Inicializa o root layout (layout base).
     */
    private void initRootLayout() {
        try {

        	
            // Carrega o root layout do arquivo FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(ApplicationConstants.FXML_MAIN_APP));
            this.mainVBox = (VBox) loader.load();
            
            this.mainVBox.prefWidthProperty().bind(this.mainStage.widthProperty());
            this.mainVBox.prefHeightProperty().bind(this.mainStage.heightProperty());
            
            
            // Constrói e seta a cena/janela da aplicação
            this.mainScene = new Scene(mainVBox);
            this.mainStage.setScene(mainScene);
            
            // Carrega e dá ao controlador acesso à main app
            this.mainController = loader.getController();
            this.mainController.setMainApp(this);
            
	        // Adiciona o evento que interrompe o fechamento da janela através do teclado
            this.mainStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
	            	public void handle(WindowEvent windowEvent){
	            		windowEvent.consume(); // Consome o evento para não se propagar
	            		handleExitApplication();	
	            	}
            });
            
            this.observableApplication = new ApplicationObservable(this.mainVBox.getHeight(), this.mainVBox.getWidth());
            		
            // Adiciona listeners para o resize da janela/cena
            this.mainScene.widthProperty().addListener(new ChangeListener<Number>() {
                @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                		observableApplication.setAppWidth(newSceneWidth);
                }
            });
            
            this.mainScene.heightProperty().addListener(new ChangeListener<Number>() {
                @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                		observableApplication.setAppHeight(newSceneHeight);
                }
            });
     
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public void setMainAppSize(Double width, Double height){
    		this.APP_HEIGHT = height;
    		this.APP_WIDTH = width;
//    		this.mainStage.setWidth(width);
//    		this.mainStage.setHeight(height);
    }

    /**
     * Getters / Setters
     * @return ObservableApplication
     */
    
    public Stage getMainStage() {
		return mainStage;
	}

    public VBox getMainVBox() {
    		return this.mainVBox;
    }
    
	public ApplicationObservable getObservableApplication() {
		return observableApplication;
	}
	
	/**
	 * Inicia a aplicação
	 */
    public static void main(String[] args) {
        launch(args);
    }
        
}
