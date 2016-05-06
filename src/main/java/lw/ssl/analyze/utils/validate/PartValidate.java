package lw.ssl.analyze.utils.validate;

import lw.ssl.analyze.utils.InputStreamConverter;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by zmushko_m on 06.05.2016.
 */
public class PartValidate {
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 1;
    private static final Pattern WHITE_LIST_PATTERN = Pattern.compile("^[a-zA-Z/:,\\-._\\d\\s]{0,20}$");
    private static final int MAX_LINES_AMOUNT = 2000;

    public static boolean isFileNameValid(String fileName) {
        if (StringUtils.isNotBlank(fileName)) {
            //validate fileName
            if (WHITE_LIST_PATTERN .matcher(fileName).matches()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isFileSizeValid(Long fileSize) {
        if (fileSize != null) {
            if (fileSize <= MAX_FILE_SIZE) {
                return true;
            }
        }

        return false;
    }

    public static boolean isFileLinesAmountValid(Part part) {
        try {
            if (InputStreamConverter.getFileLinesCount(part.getInputStream()) <= MAX_LINES_AMOUNT) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    public static String isFileValid(Part part) {
        if (part != null) {
            if (!isFileSizeValid(part.getSize())) {
                return "File size should be less than 1Mb";
            }
            if (!isFileNameValid(part.getSubmittedFileName())) {
                return "File name should consist from a-z A-Z 0-9 : , - . _ and have a length 1-20 symbols";
            }
            if (!isFileLinesAmountValid(part)) {
                return "File should contain less than 2001 lines";
            }
        }
        return null;
    }
}
