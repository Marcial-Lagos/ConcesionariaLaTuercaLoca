package cl.pixelandbean.model;

public class User {
    public enum Role { ADMIN, OPERADOR }

    private final String username;
    private String password;
    private String fullName;
    private Role role;
    private boolean active;

    public User(String username, String password, String fullName, Role role, boolean active) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        this.username = username.trim();
        this.password = password;
        this.fullName = fullName == null ? "" : fullName.trim();
        this.role = role == null ? Role.OPERADOR : role;
        this.active = active;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
