package unpa.service.seguridad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class CaptchaService {

    private static final Logger log = LoggerFactory.getLogger(CaptchaService.class);

    @Value("${app.security.recaptcha.enabled:false}")
    private boolean recaptchaEnabled;

    @Value("${app.security.recaptcha.secret:}")
    private String recaptchaSecret;

    @Value("${app.security.recaptcha.verify-url:https://www.google.com/recaptcha/api/siteverify}")
    private String recaptchaVerifyUrl;

    public boolean validarCaptcha(String captchaToken) {
        String token = captchaToken == null ? "" : captchaToken.trim();
        if (token.isBlank()) {
            return false;
        }

        if (!recaptchaEnabled) {
            return true;
        }

        if (recaptchaSecret == null || recaptchaSecret.isBlank()) {
            log.warn("reCAPTCHA habilitado, pero sin secreto configurado");
            return false;
        }

        try {
            String url = UriComponentsBuilder.fromHttpUrl(recaptchaVerifyUrl)
                    .queryParam("secret", recaptchaSecret)
                    .queryParam("response", token)
                    .toUriString();

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.postForEntity(url, null, Map.class);
            Map<String, Object> body = response.getBody();
            if (body == null) {
                return false;
            }
            Object success = body.get("success");
            return Boolean.TRUE.equals(success);
        } catch (RestClientException | IllegalArgumentException ex) {
            log.warn("No se pudo validar reCAPTCHA: {}", ex.getMessage());
            return false;
        }
    }
}
