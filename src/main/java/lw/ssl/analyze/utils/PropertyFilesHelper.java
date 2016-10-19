package lw.ssl.analyze.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by zmushko_m on 22.04.2016.
 */
public class PropertyFilesHelper {

    public static Properties getPropertyByPath(String propertyFilePath) {
        final Properties props = new Properties();
        InputStream input = null;

        try {
            input = PropertyFilesHelper.class.getResourceAsStream(propertyFilePath);
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
