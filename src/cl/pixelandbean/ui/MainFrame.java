package cl.pixelandbean.ui;

import cl.pixelandbean.model.User;
import cl.pixelandbean.service.AppContext;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class MainFrame extends JFrame {
    private final AppContext context;
    private final User user;
    private final CardLayout cards = new CardLayout();
    private final JPanel container = new JPanel(cards);
    private final JLabel statusLabel = new JLabel();
    private final JLabel clockLabel = new JLabel();

    public MainFrame(AppContext context, User user) {
        super("Pixel & Bean - Panel Principal");
        this.context = context;
        this.user = user;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 650));
        buildUi();
        pack();
    }

    private void buildUi() {
        setLayout(new BorderLayout());
        setJMenuBar(createMenu());

        container.add(new PlaceholderPanel("Bienvenido a Pixel & Bean"), "home");
        container.add(new ProductPanel(context.getProductService()), "productos");
        container.add(new UserPanel(context.getUserService(), user), "usuarios");
        container.add(new PlaceholderPanel("Ventas simplificadas - en desarrollo"), "ventas");
        container.add(new PlaceholderPanel("Reporte diario - pendiente de datos reales"), "reporte-dia");
        container.add(new PlaceholderPanel("Top productos - próximamente"), "reporte-top");
        container.add(new PlaceholderPanel("Eventos y torneos - pronto disponible"), "eventos");

        add(container, BorderLayout.CENTER);
        add(createStatusBar(), BorderLayout.SOUTH);
        cards.show(container, "home");
    }

    private JMenuBar createMenu() {
        JMenuBar bar = new JMenuBar();

        JMenu archivo = new JMenu("Archivo");
        archivo.setMnemonic('A');
        JMenuItem cerrarSesion = new JMenuItem("Cerrar sesión");
        cerrarSesion.addActionListener(e -> { dispose(); new cl.pixelandbean.app.AppLauncher().main(new String[]{}); });
        JMenuItem salir = new JMenuItem("Salir");
        salir.addActionListener(e -> System.exit(0));
        archivo.add(cerrarSesion);
        archivo.add(new JSeparator());
        archivo.add(salir);

        JMenu gestion = new JMenu("Gestión");
        gestion.setMnemonic('G');
        JMenuItem usuarios = new JMenuItem("Usuarios");
        usuarios.setAccelerator(KeyStroke.getKeyStroke('U', java.awt.Event.ALT_MASK));
        usuarios.addActionListener(e -> cards.show(container, "usuarios"));
        usuarios.setEnabled(user.getRole() == User.Role.ADMIN);
        JMenuItem productos = new JMenuItem("Productos");
        productos.addActionListener(e -> cards.show(container, "productos"));
        gestion.add(usuarios);
        gestion.add(productos);

        JMenu operacion = new JMenu("Operación");
        operacion.setMnemonic('O');
        JMenuItem ventas = new JMenuItem("Ventas");
        ventas.addActionListener(e -> cards.show(container, "ventas"));
        operacion.add(ventas);

        JMenu reportes = new JMenu("Reportes");
        reportes.setMnemonic('R');
        JMenuItem ventasDia = new JMenuItem("Ventas del día");
        ventasDia.addActionListener(e -> cards.show(container, "reporte-dia"));
        JMenuItem topProductos = new JMenuItem("Top productos");
        topProductos.addActionListener(e -> cards.show(container, "reporte-top"));
        reportes.add(ventasDia);
        reportes.add(topProductos);

        JMenu eventos = new JMenu("Eventos");
        eventos.setMnemonic('E');
        JMenuItem torneos = new JMenuItem("Torneos");
        torneos.addActionListener(e -> cards.show(container, "eventos"));
        eventos.add(torneos);

        JMenu ayuda = new JMenu("Ayuda");
        ayuda.setMnemonic('Y');
        JMenuItem acerca = new JMenuItem("Acerca de...");
        acerca.addActionListener(e -> javax.swing.JOptionPane.showMessageDialog(this,
                "Pixel & Bean\nVersión demo offline\nConstruido en Java Swing",
                "Acerca de", javax.swing.JOptionPane.INFORMATION_MESSAGE));
        ayuda.add(acerca);

        bar.add(archivo);
        bar.add(gestion);
        bar.add(operacion);
        bar.add(reportes);
        bar.add(eventos);
        bar.add(ayuda);

        return bar;
    }

    private JPanel createStatusBar() {
        JPanel status = new JPanel(new BorderLayout());
        status.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        statusLabel.setText("Usuario: " + user.getUsername() + " | Rol: " + user.getRole());
        clockLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        clockLabel.setOpaque(true);
        clockLabel.setBackground(new Color(245, 245, 245));
        clockLabel.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        startClock();
        status.add(statusLabel, BorderLayout.WEST);
        status.add(clockLabel, BorderLayout.EAST);
        return status;
    }

    private void startClock() {
        Timer timer = new Timer(1000, e -> {
            String now = new SimpleDateFormat("HH:mm:ss").format(new Date());
            clockLabel.setText(now);
        });
        timer.start();
    }
}
