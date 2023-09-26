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

    private static void createAndShowGUI() {
        // Créez la fenêtre principale de l'application
        JFrame frame = new JFrame("Application SAV");
        ImageIcon img = new ImageIcon("C:/Users/jackt/Desktop/com/app/app/logo.png");
        frame.setIconImage(img.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());
        

        // Créez un bouton "Ajouter une réclamation"
        JButton addButton = new JButton("Ajouter une réclamation");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Redirigez vers la classe "Reclamation.java" pour ajouter une réclamation
                frame.dispose();
                Reclamation.reclamer();
            }
        });

        // Créez un bouton "Voir les réclamations"
        JButton viewButton = new JButton("Voir les réclamations");
        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Redirigez vers la classe "Reclamation.java" pour voir les réclamations
                frame.dispose();
                Reclamation.voirReclamations();
            }
        });

        // Ajoutez les boutons à la fenêtre
        frame.add(addButton);
        frame.add(viewButton);

        // Affichez la fenêtre
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}