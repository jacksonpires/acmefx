package br.com.nordesti.app.login;

import br.com.nordesti.app.main.MainApp;
import br.com.nordesti.daos.UserHSQLDB;
import br.com.nordesti.models.User;
import br.com.nordesti.utils.ApplicationFileUtils;
import br.com.nordesti.utils.ApplicationMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class LoginController {
	// Referencia para a aplicação principal
    private MainApp mainApp;
    
    // Referencia a janela/form/scene
    private Stage stage;
    
	@FXML
	private ImageView imageLogin;
	
	@FXML
	private TextField textFieldUsername;

	@FXML
	private PasswordField textFieldPassword;

	@FXML
	private Button buttonOK;

	
	/**
	 * Chamado quando o usuário clica no botão OK.
	 * Verifica se usuário existe e se senha é correta.
	 */
	@FXML
	private void handleOKClick()  {
		User user = new User();
		UserHSQLDB userHSQLDB = new UserHSQLDB();
		
		user = userHSQLDB.searchByUsername(textFieldUsername.getText());
		
		if (user == null){
			ApplicationMessage.Error("Usuário não existe!");
			
		} else if ((user.getUsername().equalsIgnoreCase(textFieldUsername.getText())) && (user.getPassword().equals(textFieldPassword.getText()))){
			ApplicationFileUtils.saveLastLoginChanges(user.getUsername());
			this.mainApp.showRootLayout();
			this.stage.close();
		} else {
			ApplicationMessage.Error("Usuário ou senha inválidos!! ");
		}
	}
	
	
	/**
	 * Chamado quando o usuário clica no botão Cancelar.
	 */
	@FXML
	private void handleCancelClick()  {
		mainApp.handleExitApplication();
	}
	
	
	/**
	 * Chamado quando o usuário pressiona uma tecla (ENTER).
	 */
	@FXML
	private void handleOKButtonKeyPressed(KeyEvent keyEvent)  {
		if(keyEvent.getCode() == KeyCode.ENTER){
			handleOKClick();
		}
	}
	
	/**
	 * Chamado quando o usuário pressiona uma tecla (ENTER).
	 */
	@FXML
	private void handleCancelButtonKeyPressed(KeyEvent keyEvent)  {
		if(keyEvent.getCode() == KeyCode.ENTER){
			handleCancelClick();
		}
	}
    
    /**
     * O construtor.
     * O construtor é chamado antes do método initialize().
     */
	public LoginController() {
		// TODO Auto-generated constructor stub
	}

	/**
     * Inicializa a classe controller. Este método é chamado automaticamente
     *  após o arquivo fxml ter sido carregado.
     */
    @FXML
    private void initialize() {
    	    Image image = new Image("/br/com/nordesti/icons/login.png");
    		this.imageLogin.setImage(image);
    }
        
    /**
     * É chamado pela aplicação principal para dar uma referência de volta a si mesmo.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    
    /**
     * Atrela o stage do formulário atual
     * 
     * @param stage
     */
    public void setStageForm(Stage stage){
    		this.stage = stage;
    }
    
    /**
     * Requisita o foco para o textFieldUser
     * 
     */
    public void requestFocusTextFieldUsername(){
    		this.textFieldUsername.requestFocus();
    }
    
    public void requestFocusTextFieldPassword(){
		this.textFieldPassword.requestFocus();
}

    
    public void setTextFieldUsername(String username){
    		this.textFieldUsername.setText(username);
    }
  
}
