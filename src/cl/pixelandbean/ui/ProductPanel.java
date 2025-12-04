package cl.pixelandbean.ui;

import cl.pixelandbean.model.Product;
import cl.pixelandbean.service.ProductService;
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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class ProductPanel extends JPanel {
    private final ProductService productService;
    private final DefaultTableModel model;
    private final JTable table;
    private final JTextField searchField;
    private final JTextField nameField;
    private final JTextField categoryField;
    private final JTextField typeField;
    private final JTextField priceField;
    private final JTextField activeField;

    public ProductPanel(ProductService productService) {
        super(new BorderLayout());
        this.productService = productService;
        this.model = new DefaultTableModel(new Object[]{"ID", "Nombre", "Categoría", "Tipo", "Precio", "Activo"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.table = new JTable(model);
        this.searchField = new JTextField(20);
        this.nameField = new JTextField(15);
        this.categoryField = new JTextField(10);
        this.typeField = new JTextField(10);
        this.priceField = new JTextField(8);
        this.activeField = new JTextField(5);
        buildUi();
        refreshTable(productService.search(""));
    }

    private void buildUi() {
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Buscar:"));
        top.add(searchField);
        JButton searchButton = new JButton("Filtrar");
        searchButton.addActionListener(e -> refreshTable(productService.search(searchField.getText())));
        top.add(searchButton);
        add(top, BorderLayout.NORTH);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y; form.add(new JLabel("Nombre"), gbc);
        gbc.gridx = 1; form.add(nameField, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; form.add(new JLabel("Categoría"), gbc);
        gbc.gridx = 1; form.add(categoryField, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; form.add(new JLabel("Tipo"), gbc);
        gbc.gridx = 1; form.add(typeField, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; form.add(new JLabel("Precio"), gbc);
        gbc.gridx = 1; form.add(priceField, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; form.add(new JLabel("Activo (true/false)"), gbc);
        gbc.gridx = 1; form.add(activeField, gbc); y++;

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton save = new JButton("Guardar/Actualizar");
        save.addActionListener(e -> saveProduct());
        JButton delete = new JButton("Eliminar");
        delete.addActionListener(e -> deleteProduct());
        JButton clear = new JButton("Limpiar");
        clear.addActionListener(e -> clearForm());
        actions.add(save);
        actions.add(delete);
        actions.add(clear);

        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.EAST;
        form.add(actions, gbc);

        JLabel help = new JLabel("Doble clic en la tabla para editar", SwingConstants.LEFT);
        gbc.gridy = ++y; gbc.anchor = GridBagConstraints.WEST;
        form.add(help, gbc);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        nameField.setText(model.getValueAt(row, 1).toString());
                        categoryField.setText(model.getValueAt(row, 2).toString());
                        typeField.setText(model.getValueAt(row, 3).toString());
                        priceField.setText(model.getValueAt(row, 4).toString());
                        activeField.setText(model.getValueAt(row, 5).toString());
                    }
                }
            }
        });

        add(form, BorderLayout.SOUTH);
    }

    private Integer selectedId() {
        int row = table.getSelectedRow();
        if (row < 0) {
            return null;
        }
        Object val = model.getValueAt(row, 0);
        return Integer.parseInt(val.toString());
    }

    private void saveProduct() {
        try {
            String name = nameField.getText();
            String category = categoryField.getText();
            String type = typeField.getText();
            double price = Double.parseDouble(priceField.getText());
            boolean active = Boolean.parseBoolean(activeField.getText());
            productService.save(name, category, type, price, active, selectedId());
            refreshTable(productService.search(searchField.getText()));
            clearForm();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio inválido", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteProduct() {
        Integer id = selectedId();
        if (id == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar producto seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            productService.delete(id);
            refreshTable(productService.search(searchField.getText()));
            clearForm();
        }
    }

    private void clearForm() {
        nameField.setText("");
        categoryField.setText("");
        typeField.setText("");
        priceField.setText("");
        activeField.setText("true");
        table.clearSelection();
    }

    private void refreshTable(List<Product> products) {
        model.setRowCount(0);
        for (Product p : products) {
            model.addRow(new Object[]{p.getId(), p.getName(), p.getCategory(), p.getType(), p.getPrice(), p.isActive()});
        }
    }
}
