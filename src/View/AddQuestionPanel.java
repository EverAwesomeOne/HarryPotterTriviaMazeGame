package View;

import javax.swing.*;

public class AddQuestionPanel extends JPanel {

    private JPanel adminPanel;

    AddQuestionPanel(JFrame mainFrame) {
        adminPanel = new JPanel();

        JLabel temp = new JLabel("ADD ADMIN FEATURES");

        adminPanel.add(temp);

        mainFrame.add(adminPanel);

        adminPanel.setVisible(true);
    }
}
