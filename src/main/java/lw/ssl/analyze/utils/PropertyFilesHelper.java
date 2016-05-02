package lw.ssl.analyze.utils;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by zmushko_m on 22.04.2016.
 */
public class PropertyFilesHelper {

    public static Properties getPropertyByPath(String propertyFilePath, ServletContext servletContext) {
        final Properties props = new Properties();
        InputStream input = null;

        try {
            input = servletContext.getResourceAsStream(propertyFilePath);
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return props;
    }
}
