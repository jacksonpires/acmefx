package br.com.nordesti.app.main;

import br.com.nordesti.app.registerusers.RegisterUsersScene;
import br.com.nordesti.utils.ApplicationConstants;
import br.com.nordesti.utils.ApplicationMessage;
import br.com.nordesti.utils.ScreenUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainAppController {
	
    private RegisterUsersScene registerUsersScene;

	@FXML
	private MenuBar mainMenuBar;
	
	@FXML
	private TabPane mainTabPane;
	
	@FXML
	private MenuItem menuItemRegisterUsers;

	@FXML
	private MenuItem menuExit;
	
	@FXML
	private VBox mainForm;
	
	@FXML
	private Pane bodyForm;
	
	
	@FXML
	private void handleWindowMaximize(){
		this.mainApp.setMainAppSize(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight());
	}
	
	@FXML
	private void handleWindow1024768(){
		this.mainApp.setMainAppSize(1024.0, 768.0);		
	}
		
	/**
	 * Chamado quando o usuário clica no botão Sistema > Sair.
	 */
	@FXML
	private void handleSystemExitClick()  {
		if (ApplicationMessage.Confirmation("Deseja realmente sair?").get() == ButtonType.OK){
			System.exit(0);
		}	    
	}
	
	/**
	 * Chamado quando o usuário clica no botão Sistema > Usuários.
	 */
	@FXML
	private void handleSystemUsersClick()  {
		registerUsersScene = new RegisterUsersScene(this.mainApp, this, this.mainApp.getObservableApplication());
	}
	
	// Reference to the main application.
    private MainApp mainApp;
    
    /**
     * O construtor.
     * O construtor é chamado antes do método inicialize().
     */
	public MainAppController() {}

	/**
     * Inicializa a classe controller. Este método é chamado automaticamente
     *  após o arquivo fxml ter sido carregado.
     */
    @FXML
    private void initialize() {
    		configureFXMLPrefs();
    }
    
    /**
     * É chamado pela aplicação principal para dar uma referência de volta a si mesmo.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    

    // Efetua configurações específicas dos componentes FXML
    private void configureFXMLPrefs(){
    	
    		// mainMenuBar
    		this.mainMenuBar.setPrefWidth(ScreenUtils.getScreenWidth());
    		
    		// menuItemRegisterUsers
    		Image image = new Image(ApplicationConstants.USERS_ICON);
    		ImageView imageView = new ImageView(image);
    		this.menuItemRegisterUsers.setGraphic(imageView);

    		this.mainTabPane.setLayoutY(0);
    		this.mainTabPane.setLayoutX(0);

    		// mainTabPane
    		//this.bodyForm.prefHeightProperty().bind(this.mainTabPane.prefHeightProperty());
    		//this.bodyForm.prefWidthProperty().bind(this.mainTabPane.prefWidthProperty());
    		this.mainTabPane.setPrefWidth(ScreenUtils.getScreenWidth());
    		this.mainTabPane.setPrefHeight(ScreenUtils.getScreenHeight());
    }
  
	/**
	 * Adiciona uma nova Tab
	 * @param pathImage
	 * @param tabName
	 * @param tabPane
	 */
    public void setNewTab(String pathImage, String tabName, TabPane tabPane){
    	    Tab newTab = new Tab(tabName);
    	    newTab.setContent(tabPane);
    	    newTab.setClosable(true);

    	    Image image = new Image(pathImage);
    		ImageView imageView = new ImageView(image);    		
    		newTab.setGraphic(imageView);
    		newTab.setStyle("-fx-padding: 10; -fx-focus-color: transparent; -fx-background-insets: -1.4, 0, 1, 2;");
    		
    		this.mainTabPane.getTabs().add(newTab);
    }
}
