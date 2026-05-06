package unpa.entity.user;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String token;
    private String passwordNueva;
}
