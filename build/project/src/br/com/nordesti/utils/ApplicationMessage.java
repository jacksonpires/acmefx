package br.com.nordesti.utils;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class ApplicationMessage {
	
	static public void Error(String message){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erro...");
		alert.setHeaderText(null);
		alert.setContentText(message);

		alert.showAndWait();
	}
	
	static public void Information(String message){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Informação...");
		alert.setHeaderText(null);
		alert.setContentText(message);

		alert.showAndWait();
	}
	
	/**
	 * 
	 * Ex: if (ApplicationMessage.Confirmation("Deseja realmente sair?").get() == ButtonType.OK){
	 *	   	  System.exit(0);
	 *	   }
	 *
	 * @param message
	 * @return Optional<ButtonType>
	 */
	static public Optional<ButtonType> Confirmation(String message){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmação...");
		alert.setHeaderText(null);
		alert.setContentText(message);

		Optional<ButtonType> result = alert.showAndWait();

		return result;
	}

}
