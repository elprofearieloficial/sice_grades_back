package unpa.entity.user;

import lombok.Data;

@Data
public class PasswordRequest {
    private String passwordActual;
    private String passwordNueva;

    // getters y setters
}

