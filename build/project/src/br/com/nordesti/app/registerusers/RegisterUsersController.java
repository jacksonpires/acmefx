package br.com.nordesti.app.registerusers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.nordesti.app.main.MainApp;
import br.com.nordesti.daos.UserHSQLDB;
import br.com.nordesti.models.User;
import br.com.nordesti.models.UserFX;
import br.com.nordesti.utils.ApplicationConstants;
import br.com.nordesti.utils.ApplicationMessage;
import br.com.nordesti.utils.ApplicationStates;
import br.com.nordesti.utils.ApplicationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class RegisterUsersController {
	
	private String OPTION_SEARCH_USERNAME = "Usuário";
	private String OPTION_SEARCH_NAME = "Nome";
	
	private MainApp mainApp;	
	private InsertEditUsersController insertEditUsersController;
	private RegisterUsersScene registerUsersScene;
	private TabPane tabPaneInsertEditUsers;
	private UserFX currentUserFX;
	
	private ObservableList<UserFX> userFXData = FXCollections.observableArrayList();
	private ObservableList<String> searchOptions = FXCollections.observableArrayList(OPTION_SEARCH_USERNAME, OPTION_SEARCH_NAME);
	
	@FXML
	private TabPane tabPaneRegisterUsers;
	
	@FXML
	private TableView<UserFX> tableViewUsers;

	@FXML
	private TableColumn<UserFX, String> tableColumnStatus;

	@FXML
	private TableColumn<UserFX, String> tableColumnUsername;

	@FXML
	private TableColumn<UserFX, String> tableColumnName;
	
	@FXML
	private Tab tabSearch;
	
	@FXML
	private TextField textFieldSearch;

	@FXML
	private VBox vboxSearch;
	
	@FXML
	private Button buttonInsert;
	
	@FXML
	private Button buttonDelete;
	
	@FXML
	private Button buttonEdit;
	
	@FXML
	private ComboBox<String> comboBoxSearch;
	
	@FXML
	private Button buttonSearch;

	@FXML
	private void handleButtonDeleteClick(){
		verifySelectedRow();
		
		if (ApplicationMessage.Confirmation("Deseja apagar o usuário ["+ this.currentUserFX.getName() +"] ?").get() == ButtonType.OK){
			this.registerUsersScene.deleteUser(UserFX.toUser(this.currentUserFX));
			this.userFXData.remove(this.currentUserFX);
			verifySelectedRow();
			ApplicationMessage.Information("Usuário excluído com sucesso!");
		}
		
	}
	
	@FXML
	private void handleButtonEditClick()  {
		OpenNewTab("Editar [Usuários]", ApplicationStates.EDIT, UserFX.toUser(this.currentUserFX));
	}
	
	@FXML
	private void handleInsertClick(){
		OpenNewTab("Inserir [Usuários]", ApplicationStates.INSERT, new User());
	}
	
	@FXML
	private void handleTextFieldSearchPressed(KeyEvent keyEvent)  {
		if(keyEvent.getCode() == KeyCode.ENTER){
			handleButtonSearchClick();
		}
	}
	
	@FXML
	private void handleButtonSearchClick(){
		UserHSQLDB userHSQLDB = new UserHSQLDB();
		List<User> users = new ArrayList<User>();
		
		users = userHSQLDB.listByUsername(this.textFieldSearch.getText());
		
		this.userFXData.clear();
		setUsersToUserFXData(users);
	}
	
	@FXML
	private void handleTabPaneKeyPressed(KeyEvent keyEvent)  {
		System.out.println(">>>>>>>> " + keyEvent.getCode());
		if(keyEvent.getCode() == KeyCode.ESCAPE){
			this.registerUsersScene.getTabPane().getTabs().remove(this.registerUsersScene);
			
		}
	}

	/**
     * Inicializa a classe controller. Este método é chamado automaticamente
     *  após o arquivo fxml ter sido carregado.
     */
    @FXML
    private void initialize() {
    		loadModels();
    		configureFXMLPrefs();
    }
        
    /**
     * O construtor.
     * O construtor é chamado antes do método initialize().
     */
	public RegisterUsersController() {
	}

    /**
     * Efetua configurações específicas dos componentes FXML
     */
    private void configureFXMLPrefs(){
    	    
//    		this.tabPaneInsertEditUsers.prefWidthProperty().bind(this.mainApp.getMainStage().widthProperty());
//    		this.tabPaneInsertEditUsers.prefHeightProperty().bind(this.mainApp.getMainStage().heightProperty());
//    		this.vboxSearch.prefWidthProperty().bind(this.mainApp.getMainStage().widthProperty());
//    		this.vboxSearch.prefHeightProperty().bind(this.mainApp.getMainStage().heightProperty());
    	
    		// ComboBox
    		this.comboBoxSearch.getItems().clear();
    		this.comboBoxSearch.setItems(this.searchOptions);
    		this.comboBoxSearch.getSelectionModel().select(0);
    	
    		// Buttons
    		this.buttonInsert.setText(null);
    		this.buttonInsert.setGraphic(ApplicationUtils.getIcon(ApplicationConstants.INSERT_ICON));
    		
    		this.buttonEdit.setText(null);
    		this.buttonEdit.setGraphic(ApplicationUtils.getIcon(ApplicationConstants.EDIT_ICON));
    		
    		this.buttonDelete.setText(null);
    		this.buttonDelete.setGraphic(ApplicationUtils.getIcon(ApplicationConstants.DELETE_ICON));

    	    // TableView
        this.tableViewUsers.setItems(getUserFXData());        
    	    this.tableViewUsers.setRowFactory( tv -> {
	    	    TableRow<UserFX> row = new TableRow<>();

	    	    row.setOnMouseClicked(event -> {
	    	        if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
	    	        	    setCurrentUserFX(row.getItem());
	    	        }
	    	        
	    	        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
	    	        		handleButtonEditClick();
	    	        }
	    	    });
	    	    
	    	    return row;
	    	});
	    
    		// Table Columns
	    	this.tableColumnUsername.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
	    	this.tableColumnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
	    	this.tableColumnName.setPrefWidth(300);
    	
    		// tabSearch
    		this.tabSearch.setGraphic(ApplicationUtils.getIcon(ApplicationConstants.SEARCH_ICON));
    		this.tabSearch.setStyle("-fx-padding: 10; -fx-focus-color: transparent; -fx-background-insets: -1.4, 0, 1, 2;");
    }
	
		
    private void OpenNewTab(String tabTitle, ApplicationStates tabState, User currentUser){
	    	try {
	    		// Carrega o template FXML para scene
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource(ApplicationConstants.FXML_INSERT_EDIT_USERS));
	    		this.tabPaneInsertEditUsers = (TabPane) loader.load();
	
	    		// Carrega e dá ao Controller acesso à MainApp
	        this.insertEditUsersController = loader.getController();
	        this.insertEditUsersController.setMainApp(this.mainApp, this, currentUser, tabState);
		        
	        // Adiciona uma nova tab na aplicação
		    this.tabPaneInsertEditUsers.getTabs().forEach((tab) -> {
	      		setNewTab(ApplicationConstants.INSERT_ICON, tabTitle, tab);
	        });
	
		    	// Requisita o foco para o textFieldName
		    this.insertEditUsersController.textFieldUsernameRequestFocus();	
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Set the mainApp that controls this Controller. Loop reference. 
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;        
    }
    
    
    /**
     * Set the registerUsersScene that controls this Controller. Loop Reference.
     * 
     * @param registerUsersScene
     */
    public void setRegisterUsersScene(RegisterUsersScene registerUsersScene){
    		this.registerUsersScene = registerUsersScene;
    }
    
    /**
     * Resize some components
     * 
     * @param width
     * @param height
     */
    public void setRegisterUsersComponentsResize(Double width, Double height){
		this.tabPaneRegisterUsers.setPrefWidth(width);
		this.tabPaneRegisterUsers.setPrefHeight(height);
		this.vboxSearch.setPrefWidth(width);
		this.vboxSearch.setPrefHeight(height);
    }
     
    
    /**
	 * Load Models for this Controller
	 * 
	 */
	public void loadModels(){
		List<User> users = new ArrayList<User>();
		UserHSQLDB userHSQLDB = new UserHSQLDB();
		
		users = userHSQLDB.listAll();
		
		setUsersToUserFXData(users);
	}
	
	public void setUsersToUserFXData(List<User> users){
		users.forEach((user) -> {
			UserFX userFX = new UserFX(user);			
			getUserFXData().add(userFX);
		});
	}
	
	/**
	 * Returns a observable list of users
	 * 
     * @return ObservableList<UserFX>
     */
	public ObservableList<UserFX> getUserFXData(){
		return this.userFXData;
	}

	/**
	 * Add a new Tab
	 * 
	 * @param pathImage Path to Image
	 * @param tabName Name of tab
	 * @param tabPane Tab that will be added
	 */
    public void setNewTab(String pathImage, String tabName, Tab tab){
	    Image image = new Image(pathImage);
		ImageView imageView = new ImageView(image);    		
		tab.setGraphic(imageView);
		tab.setStyle("-fx-padding: 10; -fx-focus-color: transparent; -fx-background-insets: -1.4, 0, 1, 2;");
		
		tab.setClosable(true);
		tab.setText(tabName);
		this.tabPaneRegisterUsers.getTabs().add(tab);
				
	    SingleSelectionModel<Tab> selectionModel = this.tabPaneRegisterUsers.getSelectionModel();
		selectionModel.select(tab);
    }

    
    public TextField getTextFieldSearch(){
    		return this.textFieldSearch;
    }
    
    public void fireSearchClick(){
    		handleButtonSearchClick();
    }
    
    public void verifySelectedRow(){
    		if (this.tableViewUsers.getSelectionModel().getSelectedItem() == null){
			this.tableViewUsers.getSelectionModel().select(0);
		}
    		
    		setCurrentUserFX(this.tableViewUsers.getSelectionModel().getSelectedItem());
    }
    
    
    /**
     * Getters / Setters for the private variables
     * 
     */
    	public TabPane getTabPaneRegisterUsers(){
    		return this.tabPaneRegisterUsers;
    	}
    	
    	public Tab getTabSearch(){
    		return this.tabSearch;
    	}
    	
    	public TableView<UserFX> getTableViewUsers(){
    		return this.tableViewUsers;
    	}
    	
    	public void setCurrentUserFX(UserFX userFX){
    		this.currentUserFX =  userFX;
    	}
    	
    	public UserFX getCurrentUserFX(){
    		return this.currentUserFX;
    	}
    	
    	public RegisterUsersScene getRegisterUsersScene(){
    		return this.registerUsersScene;
    	}
}
