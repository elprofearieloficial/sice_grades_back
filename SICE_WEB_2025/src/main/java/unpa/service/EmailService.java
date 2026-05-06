package unpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String fromAddress;

    public void enviarCorreoRecuperacion(String destino, String enlace) {
        if (mailSender == null || destino == null || destino.isBlank()) {
            throw new IllegalStateException("Servicio de correo no disponible o destino invalido");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        if (fromAddress != null && !fromAddress.isBlank()) {
            message.setFrom(fromAddress);
        }
        message.setTo(destino);
        message.setSubject("Recuperacion de contrasena - UNPA Grades");
        message.setText("""
                Recibimos una solicitud para restablecer tu contrasena.

                Usa el siguiente enlace (vigente por 15 minutos):
                %s

                Si no solicitaste este cambio, ignora este correo.
                """.formatted(enlace));
        mailSender.send(message);
        log.info("Correo de recuperacion enviado a {}", destino);
    }
}
