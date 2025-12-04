package cl.pixelandbean.ui;

import cl.pixelandbean.model.User;
import cl.pixelandbean.service.UserService;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class UserPanel extends JPanel {
    private final UserService userService;
    private final User currentUser;
    private final DefaultTableModel model;
    private final JTable table;
    private final JTextField usernameField;
    private final JTextField passwordField;
    private final JTextField nameField;
    private final JTextField roleField;
    private final JTextField activeField;

    public UserPanel(UserService userService, User currentUser) {
        super(new BorderLayout());
        this.userService = userService;
        this.currentUser = currentUser;
        this.model = new DefaultTableModel(new Object[]{"Usuario", "Nombre", "Rol", "Activo"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        this.table = new JTable(model);
        this.usernameField = new JTextField(12);
        this.passwordField = new JTextField(12);
        this.nameField = new JTextField(12);
        this.roleField = new JTextField(8);
        this.activeField = new JTextField(5);
        buildUi();
        refresh();
    }

    private void buildUi() {
        if (currentUser.getRole() != User.Role.ADMIN) {
            add(new JLabel("Acceso restringido: solo ADMIN"), BorderLayout.CENTER);
            return;
        }

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y; form.add(new JLabel("Usuario"), gbc);
        gbc.gridx = 1; form.add(usernameField, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; form.add(new JLabel("Contraseña"), gbc);
        gbc.gridx = 1; form.add(passwordField, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; form.add(new JLabel("Nombre completo"), gbc);
        gbc.gridx = 1; form.add(nameField, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; form.add(new JLabel("Rol (ADMIN/OPERADOR)"), gbc);
        gbc.gridx = 1; form.add(roleField, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; form.add(new JLabel("Activo (true/false)"), gbc);
        gbc.gridx = 1; form.add(activeField, gbc); y++;

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton save = new JButton("Guardar");
        save.addActionListener(e -> saveUser());
        JButton delete = new JButton("Eliminar");
        delete.addActionListener(e -> deleteUser());
        actions.add(save);
        actions.add(delete);

        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        form.add(actions, gbc);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    usernameField.setText(model.getValueAt(row, 0).toString());
                    passwordField.setText("");
                    nameField.setText(model.getValueAt(row, 1).toString());
                    roleField.setText(model.getValueAt(row, 2).toString());
                    activeField.setText(model.getValueAt(row, 3).toString());
                }
            }
        });

        add(form, BorderLayout.SOUTH);
    }

    private void saveUser() {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String fullName = nameField.getText();
            User.Role role = User.Role.valueOf(roleField.getText().toUpperCase());
            boolean active = Boolean.parseBoolean(activeField.getText());
            if (username.isBlank() || password.isBlank()) {
                JOptionPane.showMessageDialog(this, "Usuario y contraseña son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            User user = new User(username, password, fullName, role, active);
            userService.save(user);
            refresh();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteUser() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String username = model.getValueAt(row, 0).toString();
        if (username.equalsIgnoreCase(currentUser.getUsername())) {
            JOptionPane.showMessageDialog(this, "No puede eliminar al usuario conectado", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar usuario " + username + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            userService.delete(username);
            refresh();
        }
    }

    private void refresh() {
        if (currentUser.getRole() != User.Role.ADMIN) {
            return;
        }
        List<User> data = userService.getAll();
        model.setRowCount(0);
        for (User u : data) {
            model.addRow(new Object[]{u.getUsername(), u.getFullName(), u.getRole(), u.isActive()});
        }
    }
}
