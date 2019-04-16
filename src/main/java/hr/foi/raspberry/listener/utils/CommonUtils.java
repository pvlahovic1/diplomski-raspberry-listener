package hr.foi.raspberry.listener.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    public static final String INTERVAL_COMMAND = "^DEVICE_ID (ALL|[0-9a-zA-z ]{64});PURING_INTERVAL ([1-9][0-9]*);SEND_INTERVAL ([1-9][0-9]*);$";
    public static final String DEVICE_NAME_COMMAND = "^DEVICE_ID ([0-9a-zA-z ]{64});SET_NAME ([0-9a-zA-z ]+);$";
    
    public static boolean isSyntaxValid(String syntax, String word) {
        Pattern pattern = Pattern.compile(syntax);
        Matcher m = pattern.matcher(word);

        return m.matches();
    }

    public static List<String> dataFromSyntax(String syntax, String word) {
        List<String> data = new ArrayList<>();

        Pattern pattern = Pattern.compile(syntax);
        Matcher m = pattern.matcher(word);

        if (m.matches()) {
            int start = 1;
            int end = m.groupCount();
            for (int i = start; i <= end; i++) {
                data.add(m.group(i));
            }
        }

        return data;
    }

}
