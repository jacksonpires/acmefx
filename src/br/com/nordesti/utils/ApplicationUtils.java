package br.com.nordesti.utils;

import br.com.nordesti.app.main.MainApp;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ApplicationUtils {

	static public String getApplicationTitle(MainApp mainApp){
		return (mainApp.getApplicationSign() + " - " + mainApp.getApplicationName());
	}

	static public String getApplicationTitle(String formName, MainApp mainApp){
		return ("[" + formName + "] " + mainApp.getApplicationSign() + " - " + mainApp.getApplicationName());
	}

	static public ImageView getIcon(String pathImage){
		Image image = new Image(pathImage);
		ImageView imageView = new ImageView(image);
		return imageView;
	}
	
	static public void requestFocusTab(TabPane tabPane, Tab tab){
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
		selectionModel.select(tab);
	}
}
