package hr.foi.raspberry.listener.service.authentication;

import java.time.LocalDateTime;

public class Token {

    private String value;
    private LocalDateTime validUtil;

    public Token() {
    }

    public Token(String value, LocalDateTime validUtil) {
        this.value = value;
        this.validUtil = validUtil;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDateTime getValidUtil() {
        return validUtil;
    }

    public void setValidUtil(LocalDateTime validUtil) {
        this.validUtil = validUtil;
    }
}
