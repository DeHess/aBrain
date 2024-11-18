package ch.zhaw.pm3.aBrain.frontend.controller;

import ch.zhaw.pm3.aBrain.aBrain.Screen;
import ch.zhaw.pm3.aBrain.frontend.ControlledScreens;
import javafx.scene.Parent;
import java.util.HashMap;
import java.util.Map;

/**
 * This class controls the screens and their controllers.
 * It uses the Singleton pattern so that only one instance of the class exists
 *
 * @author baermlau, zimmelu5, jasarard
 * @version 1.0
 */
public class ScreenController {
    private static ScreenController instance = new ScreenController();
    private Map<Screen, Parent> screens;
    private Map<Screen, ControlledScreens> controllers;

    private static final StyleSheet DEFAULT_STYLESHEET = StyleSheet.LIGHT;
    private StyleSheet styleSheet;

    public enum StyleSheet {
        LIGHT("light_theme.css");

        String sheetName;

        StyleSheet(String sheetName) {
            this.sheetName = sheetName;
        }

        @Override
        public String toString() {
            return sheetName;
        }
    }

    /**
     * Instantiates a new Screen controller.
     */
    public ScreenController() {
        screens = new HashMap<>();
        controllers = new HashMap<>();
        styleSheet = DEFAULT_STYLESHEET;
    }

    /**
     * get the only instance of the class available.
     * @return the only available {@link ScreenController} instance
     */
    public static ScreenController getInstance() {
        return instance;
    }

    /**
     * Set parents and controller.
     *
     * @param screenID     the id of the screen
     * @param screen the loadScreen
     * @param controller the controller
     */
    public void setParentsAndController(Screen screenID, Parent screen, ControlledScreens controller){
        screens.put(screenID, screen);
        controllers.put(screenID,controller);
    }

    /**
     * Get controller controlled screens.
     *
     * @param screen the name
     * @return the controlled screens
     */
    public ControlledScreens getController(Screen screen){
        return controllers.get(screen);
    }

    /**
     * Get screen parent.
     *
     * @param screen the name
     * @return the parent
     */
    public Parent getScreen(Screen screen){
        return screens.get(screen);
    }

    /**
     * Sets style sheet.
     *
     * @param styleSheet the style sheet
     */
    public void setStyleSheet(StyleSheet styleSheet) {
        this.styleSheet = styleSheet;
    }

    /**
     * Gets style sheet.
     *
     * @return the style sheet
     */
    public StyleSheet getStyleSheet() {
        return styleSheet;
    }
}
