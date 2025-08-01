package unpa.entity;

public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    // Getter y setter
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}

