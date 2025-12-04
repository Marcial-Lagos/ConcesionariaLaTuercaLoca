package cl.pixelandbean.ui;

import cl.pixelandbean.model.User;
import cl.pixelandbean.service.UserService;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginDialog extends JDialog {
    private final UserService userService;
    private User authenticatedUser;

    public LoginDialog(java.awt.Frame parent, UserService userService) {
        super(parent, "Pixel & Bean - Login", true);
        this.userService = userService;
        buildUi();
        pack();
        setLocationRelativeTo(parent);
    }

    private void buildUi() {
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);

        gbc.gridx = 0; gbc.gridy = 0; form.add(new JLabel("Usuario"), gbc);
        gbc.gridx = 1; form.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; form.add(new JLabel("Contraseña"), gbc);
        gbc.gridx = 1; form.add(passwordField, gbc);

        JButton loginButton = new JButton("Ingresar");
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            userService.authenticate(username, password).ifPresentOrElse(user -> {
                authenticatedUser = user;
                dispose();
            }, () -> JOptionPane.showMessageDialog(this, "Credenciales inválidas o usuario inactivo", "Error", JOptionPane.ERROR_MESSAGE));
        });

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> {
            authenticatedUser = null;
            dispose();
        });

        JPanel buttons = new JPanel();
        buttons.add(loginButton);
        buttons.add(cancelButton);

        getRootPane().setDefaultButton(loginButton);
        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    public User getAuthenticatedUser() {
        return authenticatedUser;
    }
}
