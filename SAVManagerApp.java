package app;
import app.Reclamation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SAVManagerApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    static void createAndShowGUI() {
        // Créer la fenêtre principale
        JFrame frame = new JFrame("Gestionnaire SAV");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Ajouter un logo
        ImageIcon logo = new ImageIcon("logo.png");
        JLabel logoLabel = new JLabel(logo);

        // Panneau d'options
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new FlowLayout());

        JButton viewTicketsButton = new JButton("Voir les tickets");
        JButton createTicketButton = new JButton("Créer un nouveau ticket");
        JButton addClientButton = new JButton("Ajouter un nouveau client");

        optionsPanel.add(viewTicketsButton);
        optionsPanel.add(createTicketButton);
        optionsPanel.add(addClientButton);

        // Panel central
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 1));

        // Configuration de la police et des couleurs
        Font titleFont = new Font("Arial", Font.BOLD, 20);
        viewTicketsButton.setFont(titleFont);
        createTicketButton.setFont(titleFont);
        addClientButton.setFont(titleFont);

        viewTicketsButton.setBackground(new Color(100, 200, 100));
        createTicketButton.setBackground(new Color(200, 100, 100));
        addClientButton.setBackground(new Color(100, 100, 200));

        // Actions des boutons
        viewTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reclamation.voirReclamations();
            }
        });

        createTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reclamation.reclamer();
            }
        });

        // Ajouter des composants à la fenêtre principale
        frame.add(logoLabel, BorderLayout.NORTH);
        frame.add(optionsPanel, BorderLayout.SOUTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}