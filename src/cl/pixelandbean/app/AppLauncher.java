package cl.pixelandbean.app;

import cl.pixelandbean.service.AppContext;
import cl.pixelandbean.ui.LoginDialog;
import cl.pixelandbean.ui.MainFrame;
import cl.pixelandbean.model.User;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            AppContext context = new AppContext();
            LoginDialog dialog = new LoginDialog(null, context.getUserService());
            dialog.setVisible(true);

            User authenticated = dialog.getAuthenticatedUser();
            if (authenticated != null) {
                MainFrame frame = new MainFrame(context, authenticated);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } else {
                System.exit(0);
            }
        });
    }
}
