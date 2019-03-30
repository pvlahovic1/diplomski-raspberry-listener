package hr.foi.raspberry.listener.service.authentication;

import hr.foi.raspberry.listener.model.device.Device;
import io.jsonwebtoken.Jwts;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
public class AuthenticationService {

    private final RestTemplate restTemplate;
    private Token token;


    public AuthenticationService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Token getValidToken(Device device) {
        if (isTokenValid(this.token)) {
            return this.token;
        } else {
            this.token = getTokenFromServer(device);
            return this.token;
        }
    }

    private Token getTokenFromServer(Device device) {
        StringBuilder authenticationUrl = new StringBuilder(device.getCentralApplicationUrl());
        authenticationUrl.append(device.getCentralApplicationAuthenticationPath()).append("?username=")
                .append(device.getUsername()).append("&password=").append(device.getPassword());

        HttpEntity<String> response = restTemplate
                .exchange(authenticationUrl.toString(), HttpMethod.POST, HttpEntity.EMPTY, String.class);

        List<String> authorizations = response.getHeaders().get("Authorization");

        Token token = null;
        if (authorizations != null && !authorizations.isEmpty()) {
            String tokenValue = authorizations.get(0).replace("Bearer ", "");

            var signingKey = device.getJwtSecret().getBytes();

            Date date = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(tokenValue).getBody().getExpiration();

          LocalDateTime expirationDate = new Timestamp(date.getTime()).toLocalDateTime();

           token = new Token(tokenValue, expirationDate);
        }

        return token;
    }


    private boolean isTokenValid(Token token) {
        boolean valid = false;

        if (token != null) {
            valid = token.getValidUtil().isAfter(LocalDateTime.now());
        }

        return valid;
    }


}
