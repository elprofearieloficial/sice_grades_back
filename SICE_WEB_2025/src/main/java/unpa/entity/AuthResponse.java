package unpa.entity;

public class AuthResponse {
    private String token;
    private String campus; // <-- Nuevo campo

    public AuthResponse(String token, String campus) {
        this.token = token;
        this.campus = campus;
    }

    // Getters y Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getCampus() { return campus; }
    public void setCampus(String campus) { this.campus = campus; }
}

