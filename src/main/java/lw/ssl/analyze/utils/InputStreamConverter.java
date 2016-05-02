package lw.ssl.analyze.utils;

import lw.ssl.analyze.pojo.WebResourceDescription;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zmushko_m on 21.04.2016.
 */
public class InputStreamConverter {
    public static final String DEFAULT_TOKENS_SEPARATOR = ",";

    public static List<WebResourceDescription> convertToWebResourceDescriptions(InputStream inputStream, String tokensSeparator) {
        List<WebResourceDescription> webResourceDescriptionList = new ArrayList<WebResourceDescription>();
        BufferedReader br = null;

        String line;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(DEFAULT_TOKENS_SEPARATOR);
                if (tokens != null && tokens.length > 0) {
                    WebResourceDescription webResourceDescription = new WebResourceDescription();

                    if (StringUtils.isNotBlank(tokens[0].trim())) {
                        if (tokens.length == 1) {
                            webResourceDescription.setHost(tokens[0].trim());
                        } else {
                            webResourceDescription.setHost(tokens[0].trim());
                            webResourceDescription.setPort(tokens[1].trim());
                        }
                        webResourceDescriptionList.add(webResourceDescription);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return webResourceDescriptionList;
    }

    public static List<WebResourceDescription> convertToWebResourceDescriptions(InputStream inputStream) {
        return convertToWebResourceDescriptions(inputStream, DEFAULT_TOKENS_SEPARATOR);
    }
}
