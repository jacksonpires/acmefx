package br.com.nordesti.app.registerusers;

import br.com.nordesti.app.main.MainApp;
import br.com.nordesti.daos.UserHSQLDB;
import br.com.nordesti.models.User;
import br.com.nordesti.models.UserFX;
import br.com.nordesti.utils.ApplicationMessage;
import br.com.nordesti.utils.ApplicationStates;
import br.com.nordesti.utils.ApplicationUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InsertEditUsersController {
	
    private MainApp mainApp;
    private RegisterUsersController registerUsersController;
	private ApplicationStates currentState;
	private User currentUser;
		
	@FXML
	private TextField textFieldUsername;
	
	@FXML
	private TextField textFieldName;

	@FXML
	private PasswordField textFieldPassword;
	
	@FXML
	private PasswordField textFieldPasswordConfirmation;
	
	@FXML
	private Button buttonSave;
	
	@FXML
	private Button buttonCancel;
	
	@FXML
	private Button buttonNew;
	
	@FXML
	private Button buttonDelete;
	
	@FXML
	private Button buttonOK;
	
	@FXML
	private Tab tabInsertEditUsers;
	
	
	@FXML
	private void handleButtonNewPressed(KeyEvent keyEvent){
		if(keyEvent.getCode() == KeyCode.ENTER){
			handleNewClick();
		}
	}
	
	@FXML
	private void handleNewClick(){
		setCurrentState(ApplicationStates.INSERT);
	}
	
	@FXML
	private void anyKeyPressed(KeyEvent keyEvent)  {
		if (this.currentState.equals(ApplicationStates.IDLE)){
			this.currentState = ApplicationStates.EDIT;
		}
	}
	
	@FXML
	private void handleButtonOKPressed(KeyEvent keyEvent)  {
		if(keyEvent.getCode() == KeyCode.ENTER){
			handleOkClick();
		}
	}
	
	@FXML
	private void handleButtonSavePressed(KeyEvent keyEvent)  {
		if(keyEvent.getCode() == KeyCode.ENTER){
			handleSaveClick();
		}
	}
	
	@FXML
	private void handleButtonCancelPressed(KeyEvent keyEvent)  {
		if(keyEvent.getCode() == KeyCode.ENTER){
			handleCancelClick();
		}
	}
	
	@FXML
	private void handleButtonDeletePressed(KeyEvent keyEvent)  {
		if(keyEvent.getCode() == KeyCode.ENTER){
			handleDeleteClick();
		}
	}

	@FXML
	private void handleCloseTab(Event event){
		if (ApplicationMessage.Confirmation("Deseja realmente cancelar?").get() == ButtonType.OK){
			this.tabInsertEditUsers.getTabPane().getTabs().remove(this.tabInsertEditUsers);
		} else {
			event.consume();
		}
	}
	
	@FXML 
	private void handleCancelClick(){
		if (ApplicationMessage.Confirmation("Deseja realmente cancelar?").get() == ButtonType.OK){
			this.tabInsertEditUsers.getTabPane().getTabs().remove(this.tabInsertEditUsers);
		}
	}
	    
    @FXML
    private void handleSaveClick(){
     	switch (this.currentState) {
     		case INSERT:
			    if (saveInsertState()){
			    	  setCurrentState(ApplicationStates.IDLE);
			    }
			    	break;
			    	
     		case EDIT:
     			if (saveEditState()){
      			    setCurrentState(ApplicationStates.IDLE);
     			}
     			break;
     			
     		case IDLE:
     			if (saveEditState()){
      			    setCurrentState(ApplicationStates.IDLE);
     			}
     			break;
     	}
    }
    
    @FXML
    private void handleOkClick(){
     	switch (this.currentState) {
     		case INSERT:
			    okInsertState();
			    	break;
			    	
     		case EDIT:
     			okEditState();
     			break;
     			
     		case IDLE:
     			okEditState();
     			break;
     	}
    }
    
    @FXML
	private void handleDeleteClick() {
    		if (ApplicationMessage.Confirmation("Deseja apagar o usuário ["+ this.textFieldName.getText() +"] ?").get() == ButtonType.OK){
			this.registerUsersController.getRegisterUsersScene().deleteUser(this.currentUser);
			
			this.registerUsersController.getUserFXData().remove(
				this.registerUsersController.getTableViewUsers().getSelectionModel().getSelectedIndex()
			);
			
			selectAndCloseActualTab();
			
			ApplicationMessage.Information("Usuário excluído com sucesso!");
		}
		
	}

	/**
     * Inicializa a classe controller. Este método é chamado automaticamente
     *  após o arquivo fxml ter sido carregado.
     */
    @FXML
    private void initialize() {
    }
    
	/**
     * O construtor.
     * O construtor é chamado antes do método initialize().
     */
	public InsertEditUsersController() {
	}
    
    /**
     * É chamado pela aplicação para dar uma referência de volta a si mesmo.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp, RegisterUsersController registerUsersController, User currentUser, ApplicationStates currentState) {
        this.mainApp = mainApp;
        this.registerUsersController = registerUsersController;
        this.currentUser = currentUser;
        
        setCurrentState(currentState);
    }
    
    public void setCurrentState(ApplicationStates state){
    		this.currentState = state;
    		changeFormToCurrentState();
    }
    
    public void changeFormToCurrentState(){
	    	switch (this.currentState) {
			case INSERT:
				this.textFieldUsername.setText(null);
				this.textFieldName.setText(null);
				this.textFieldPassword.setText(null);
				this.textFieldPasswordConfirmation.setText(null);
				
				this.textFieldUsername.setEditable(true);
				this.textFieldUsername.setDisable(false);
				this.textFieldUsername.requestFocus();
				
//				this.buttonNew.setDisable(true);
//				this.buttonDelete.setDisable(true);
//				this.buttonSave.setDisable(false);
//				this.buttonCancel.setDisable(false);
				break;
				
			case EDIT:
				this.textFieldUsername.setText(this.currentUser.getUsername());
				this.textFieldName.setText(this.currentUser.getName());
				this.textFieldPassword.setText(null);
				this.textFieldPasswordConfirmation.setText(null);
				
				this.textFieldUsername.setEditable(false);
				this.textFieldUsername.setDisable(true);
				this.textFieldName.requestFocus();
				
//				this.buttonNew.setDisable(true);
//				this.buttonDelete.setDisable(false);
//				this.buttonSave.setDisable(false);
//				this.buttonCancel.setDisable(false);
				break;
			
			case IDLE:
				this.textFieldUsername.setEditable(false);
				this.textFieldUsername.setDisable(true);
				this.buttonOK.requestFocus();

//	    		    this.buttonNew.setDisable(false);
//	    		    this.buttonDelete.setDisable(false);
//				this.buttonSave.setDisable(true);
//				this.buttonCancel.setDisable(true);
				break;
		}
    }
    
    
    public void okInsertState(){
    		if (saveInsertState()) {
    			selectAndCloseActualTab();  			
    		}
    }
    public void okEditState(){
		if (saveEditState()) {
			selectAndCloseActualTab();   			
		}
    }
    
    
    public Boolean saveInsertState(){
	    	if (isValidForm()){
	    		if (isValidInsertPassword()){
	    			try {
					setFormFieldsUser();
					
					this.registerUsersController.getUserFXData().add(new UserFX(this.currentUser));
					this.registerUsersController.getRegisterUsersScene().createUser(this.currentUser);
	
					ApplicationMessage.Information("Usuário salvo com sucesso!");
					
					return true;
				} catch (Exception e) {
					e.printStackTrace();
				}
	    		} else {
	    			ApplicationMessage.Error("Senha inválida! As senhas devem ser digitadas iguais e não podem estar vazias!");
	    			this.textFieldPassword.requestFocus();
	    		}
	    	} else {
	    		ApplicationMessage.Error("Os campos devem ser preenchidos!");
	    	}  
	    	
	    	return false;
    }
    
	private Boolean saveEditState() {
		if (isValidForm()){
	    		if (isValidEditPassword()){
	    			try {
	    				this.currentUser = this.registerUsersController.getRegisterUsersScene().searchByUsername(this.textFieldUsername.getText());
	    				getUserChanges(this.currentUser);
	    				this.registerUsersController.getRegisterUsersScene().updateUser(this.currentUser);
	    									
					ApplicationMessage.Information("Usuário atualizado com sucesso!");

					UserFX userFX = new UserFX(this.currentUser);
					this.registerUsersController.fireSearchClick();
					this.registerUsersController.getTableViewUsers().getSelectionModel().select(userFX);
					
					return true;
				} catch (Exception e) {
					e.printStackTrace();
				}
	    		} else {
	    			ApplicationMessage.Error("Senha inválida! As senhas devem ser digitadas iguais ou deixadas vazias caso não queira alterar!");
	    			this.textFieldPassword.requestFocus();
	    		}
	    	} else {
	    		ApplicationMessage.Error("Verifique o preenchimento dos campos.");
	    	}
		
		return false;
	}
	
    private void getUserChanges(User currentUser) {
		this.currentUser.setName(this.textFieldName.getText());
		
		if (!(this.textFieldPassword.getText() == null)) {
			this.currentUser.setPassword(this.textFieldPassword.getText());
		}
	}

	private boolean isValidEditPassword() {
	    	if (this.textFieldPassword.getText() == null || this.textFieldPasswordConfirmation.getText() == null){
	    		return true;
	    }
	    	
		return this.textFieldPassword.getText().equals(this.textFieldPasswordConfirmation.getText());
	}

	public Boolean isValidInsertPassword(){
    	    if (this.textFieldPassword.getText() == null || this.textFieldPasswordConfirmation.getText() == null){
    	    		return false;
    	    }
    	    
    		return this.textFieldPassword.getText().equals(this.textFieldPasswordConfirmation.getText());
    }
    
    public Boolean isValidForm(){
    	   return !(this.textFieldUsername.getText() == null && this.textFieldName.getText() == null);
    }
    
    public void setFormFieldsUser(){
    		this.currentUser.setUsername(this.textFieldUsername.getText());
    		this.currentUser.setName(this.textFieldName.getText());
    		this.currentUser.setPassword(this.textFieldPassword.getText());
    }
    
    public void textFieldUsernameRequestFocus(){
    		this.textFieldUsername.requestFocus();
    }
    
    public void textFieldNameRequestFocus(){
		this.textFieldName.requestFocus();
    }
    
    public void selectAndCloseActualTab(){
		ApplicationUtils.requestFocusTab(this.registerUsersController.getTabPaneRegisterUsers(), this.registerUsersController.getTabSearch());
		this.tabInsertEditUsers.getTabPane().getTabs().remove(this.tabInsertEditUsers);    			
    }
}
