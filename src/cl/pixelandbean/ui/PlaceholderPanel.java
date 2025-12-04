package cl.pixelandbean.ui;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PlaceholderPanel extends JPanel {
    public PlaceholderPanel(String message) {
        super(new BorderLayout());
        JLabel label = new JLabel(message, SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
