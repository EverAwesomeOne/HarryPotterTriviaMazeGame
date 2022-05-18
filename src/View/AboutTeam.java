package View;

import javax.swing.*;
import java.awt.*;

class AboutTeam extends JPanel {
    private final JPanel myAboutPanel;

    AboutTeam(final JFrame theMainFrame, JPanel theGamePanel, final JMenuBar theMainMenuBar) {
        myAboutPanel = new JPanel();
        myAboutPanel.setLayout(new BorderLayout());

        final JTextArea textField = new JTextArea("Hodgepodge Trivia Maze Team\n" +
                "Maria Babko ~ Chloe Duncan ~ Edwin Solis-Bruno\nUniversity of Washington\n" +
                "TCSS 360 A, Professor Tom Capaul\nMay 2022");
        textField.setEditable(false);

        final JButton okayButton = new JButton("OK");
        okayButton.addActionListener(
                e -> {
                    myAboutPanel.setVisible(false);
                    theGamePanel.setVisible(true);
                    theMainMenuBar.setVisible(true);
                }
        );

        myAboutPanel.add(textField, BorderLayout.CENTER);
        myAboutPanel.add(okayButton, BorderLayout.SOUTH);

        theMainFrame.add(myAboutPanel);

        myAboutPanel.setVisible(true);
    }
}
