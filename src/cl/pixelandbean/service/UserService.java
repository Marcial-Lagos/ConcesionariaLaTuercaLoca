package cl.pixelandbean.service;

import cl.pixelandbean.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final List<User> users = new ArrayList<>();

    public UserService() {
        users.add(new User("admin", "admin123", "Administrador", User.Role.ADMIN, true));
        users.add(new User("operador", "op123", "Operador de Caja", User.Role.OPERADOR, true));
    }

    public Optional<User> authenticate(String username, String password) {
        return users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .filter(User::isActive)
                .filter(u -> u.getPassword().equals(password))
                .findFirst();
    }

    public List<User> getAll() {
        return new ArrayList<>(users);
    }

    public void save(User user) {
        users.removeIf(u -> u.getUsername().equalsIgnoreCase(user.getUsername()));
        users.add(user);
    }

    public void delete(String username) {
        users.removeIf(u -> u.getUsername().equalsIgnoreCase(username));
    }
}
