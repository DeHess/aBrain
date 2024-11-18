package ch.zhaw.pm3.aBrain.backend.configuration;

import static ch.zhaw.pm3.aBrain.backend.configuration.UserSettings.Language.*;
import static ch.zhaw.pm3.aBrain.backend.configuration.UserSettings.Theme.*;

/**
 * This class contains all user settings.
 *
 * @author baermlau, hessnat1, jasarard, wilphi01, zimmelu5
 * @version 1.0
 */
public class UserSettings {
    public static enum Theme{
        LIGHT, DARK
    }
    public static enum Language{
        GERMAN, ENGLISH
    }
    
    private static final Language DEFAULT_LANGUAGE = GERMAN;
    private static final Theme DEFAULT_THEME = LIGHT;
    
    public static void UserSettings(){
        language = DEFAULT_LANGUAGE;
        theme = DEFAULT_THEME;
    }
    
    private static Language language = GERMAN;
    private static Theme theme = LIGHT;
    
    public static Language getLanguage() {return language;}
    public static Theme getTheme(){ return theme;}
    
    public static void setLanguage(Language newLanguage){language = newLanguage;}
    public static void setTheme(Theme newTheme){theme = newTheme;}
    
    
}
