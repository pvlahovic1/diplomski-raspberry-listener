package hr.foi.raspberry.listener.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    public static final String PURING_TIME_COMMAND = "^D_NAME (ALL|[0-9a-zA-z ]+);PURING_TIME ([1-9][0-9]*);$";
    public static final String DEVICE_NAME_COMMAND = "^D_NAME ([0-9a-zA-z ]+);SET_NAME ([0-9a-zA-z ]+);$";
    
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
