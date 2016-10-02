package br.com.nordesti.utils;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class ScreenUtils {
	/**
     *  Pegar a largura da tela/monitor
     *  
     * @return largura da tela/monitor
     */
    static public double getScreenWidth(){
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        return primScreenBounds.getWidth();
    }

    /**
     *  Pegar a altura da tela/monitor
     *  
     * @return altura da tela/monitor
     */
    static public double getScreenHeight(){
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        return primScreenBounds.getHeight();
    }
}
