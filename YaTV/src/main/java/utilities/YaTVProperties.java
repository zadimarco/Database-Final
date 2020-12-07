package utilities;


import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;


/**
 * The Config class for this program. (Follows Singleton pattern)
 * Inspiration from https://stackoverflow.com/questions/4750131/make-java-properties-available-across-classes
 */
public class YaTVProperties extends Properties{
    private static YaTVProperties props = null;

    private YaTVProperties(){

    }

    /**
     * Provides a properties object for YATV app
     *
     * @return The the properties object representing the configurations
     */
    @NotNull
    public static YaTVProperties getConfigs() {
        if (props == null) {
            try {
                props = new YaTVProperties();
                InputStream in = YaTVProperties.class.getClassLoader().getResourceAsStream("config.properties");


                props.load(in);
                in.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return props;
    }

}
