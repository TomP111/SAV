package app;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SAVApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Application SAV");
        ImageIcon img = new ImageIcon("C:/Users/jackt/Desktop/com/app/app/logo.png");
        frame.setIconImage(img.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JLabel title = new JLabel("Votre application SAV");

        
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        
        JButton addButton = new JButton("Ajouter une réclamation");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                frame.dispose();
                Reclamation.reclamer();
            }
        });

        
        JButton viewButton = new JButton("Voir les réclamations");
        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                frame.dispose();
                Reclamation.voirReclamations();
            }
        });


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        buttonPanel.add(title, gbc);
        gbc.gridwidth = 1;
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonPanel.add(addButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        buttonPanel.add(viewButton, gbc);

        frame.getContentPane().add(buttonPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
