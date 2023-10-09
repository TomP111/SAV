package app;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.*;
import java.sql.Timestamp;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import app.ButtonRenderer;

public class Reclamation {
    public static void reclamer() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Application SAV");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            ImageIcon img = new ImageIcon("C:/Users/jackt/Desktop/com/app/app/logo.png");
            frame.setIconImage(img.getImage());

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(5, 10, 5, 10);

            JLabel nameLabel = new JLabel("Nom du client :");
            JTextField nameField = new JTextField(15);

            JLabel prenomLabel = new JLabel("Prénom :");
            JTextField prenomField = new JTextField(15);

            JLabel issueLabel = new JLabel("Description :");
            JTextArea issueArea = new JTextArea(8, 8);
            issueArea.setWrapStyleWord(true);
            issueArea.setLineWrap(true);
            issueArea.setEditable(true);
            JScrollPane scrollPane = new JScrollPane(issueArea);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

            JLabel stateLabel = new JLabel("Etat :");
            String[] etatOptions = { "Nouveau", "En cours", "Fermé" };
            JComboBox<String> statebox = new JComboBox<>(etatOptions);

            JButton submitButton = new JButton("Soumettre");
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String clientName = nameField.getText();
                    String issue = issueArea.getText();
                    String clientPrename = prenomField.getText();
                    String idclient = "";
                    DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    String selectedState = (String) statebox.getSelectedItem();

                    try {
                        // étape 1: charger la classe de driver
                        Class.forName("com.mysql.jdbc.Driver");

                        // étape 2: créer l'objet de connexion
                        Connection conn = DriverManager.getConnection(
                                "jdbc:mysql://localhost:3308/sav?characterEncoding=UTF-8&useConfigs=maxPerformance",
                                "root", "");

                        // étape 3: créer l'objet statement
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT ID_Client FROM clients WHERE Nom LIKE '" + clientName
                                + "' AND Prenom LIKE '" + clientPrename + "'");

                        while (rs.next()) {
                            idclient = rs.getString("ID_Client");
                        }

                        // étape 5: fermez l'objet de connexion
                        conn.close();
                    } catch (Exception d) {
                        System.out.println(d);
                    }

                    if (idclient.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Le client n'a pas été trouvé !");
                        frame.dispose();
                    } else {
                        try {
                            // étape 1: charger la classe de driver
                            Class.forName("com.mysql.jdbc.Driver");

                            // étape 2: créer l'objet de connexion
                            Connection conn = DriverManager.getConnection(
                                    "jdbc:mysql://localhost:3308/sav?characterEncoding=UTF-8&useConfigs=maxPerformance",
                                    "root", "");

                            String sql = "INSERT INTO problemes (ID_Client, Description, Statut, Date) VALUES (?, ?, ?, ?)";
                            PreparedStatement pstmt = conn.prepareStatement(sql);
                            pstmt.setString(1, idclient);
                            pstmt.setString(2, issue);
                            pstmt.setString(3, selectedState);
                            pstmt.setTimestamp(4, new Timestamp(date.getTime())); // Utilisez Timestamp pour la date

                            pstmt.executeUpdate();

                            // étape 5: fermez l'objet de connexion
                            conn.close();
                        } catch (Exception d) {
                            System.out.println(d);
                        }

                        JOptionPane.showMessageDialog(frame, "Demande soumise avec succès!");
                        frame.dispose();
                    }
                }
            });
            JButton backButton = new JButton("Retour");
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    SAVApp.createAndShowGUI();
                }
            });

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel.add(nameLabel, gbc);
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel.add(nameField, gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel.add(prenomLabel, gbc);
            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel.add(prenomField, gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel.add(stateLabel, gbc);
            gbc.gridx = 1;
            gbc.gridy = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel.add(statebox, gbc);
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel.add(issueLabel, gbc);
            gbc.gridx = 1;
            gbc.gridy = 3;
            panel.add(scrollPane, gbc);
            gbc.gridx = 1;
            gbc.gridy = 4;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel.add(submitButton, gbc);
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel.add(backButton, gbc);
            frame.getContentPane().add(panel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public static void voirReclamations() {
        class CustomCellEditor extends DefaultCellEditor {
            public CustomCellEditor(JCheckBox checkBox) {
                super(checkBox);
            }
    
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                if (column == 0 || column == 1 || column == 2) { // Remplacez "yourNonEditableColumnIndex" par l'indice de la colonne que vous ne souhaitez pas éditer.
                    return null; // Retourne null pour les cellules que vous ne souhaitez pas éditer.
                } else {
                 return super.getTableCellEditorComponent(table, value, isSelected, row, column);
                }
            }
        }
        JFrame frame = new JFrame("Liste des réclamations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        ImageIcon img = new ImageIcon("C:/Users/jackt/Desktop/com/app/app/logo.png");
        frame.setIconImage(img.getImage());
        JPanel panel = new JPanel();
        

        try {
            // étape 1: charger la classe de driver
            Class.forName("com.mysql.jdbc.Driver");

            // étape 2: créer l'objet de connexion
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3308/sav?characterEncoding=UTF-8&useConfigs=maxPerformance",
                    "root", "");

            // étape 3: créer l'objet statement
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM problemes");
            String columns[] = { "ID_Ticket","ID_Client", "Description", "Statut", "Modifier", "Supprimer" };
            DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
            tableModel.setRowCount(0);
            while (rs.next()) {
                String id1 = rs.getString("ID_Ticket");
                String id2 = rs.getString("ID_Client");
                String description = rs.getString("Description");
                String statut = rs.getString("Statut");
                JButton editButton = new JButton("Modifier");
                JButton deleteButton = new JButton("Supprimer");
                tableModel.addRow(new Object[] { id1, id2, description, statut, editButton, deleteButton });
            }
            JTable jt = new JTable(tableModel);
            jt.getColumnModel().getColumn(1).setCellEditor(new CustomCellEditor(new JCheckBox()));
            jt.getColumnModel().getColumn(2).setCellEditor(new CustomCellEditor(new JCheckBox()));
            jt.getColumnModel().getColumn(0).setCellEditor(new CustomCellEditor(new JCheckBox()));
            jt.getColumnModel().getColumn(3).setCellEditor(new CustomCellEditor(new JCheckBox()));
            jt.getColumn("Modifier").setCellRenderer(new ButtonRenderer());
            jt.getColumn("Modifier").setCellEditor(
                new ButtonEditor(new JCheckBox())
            );
            jt.getColumn("Supprimer").setCellRenderer(new ButtonRenderer());
            jt.getColumn("Supprimer").setCellEditor(
                new ButtonEditor(new JCheckBox())
            );
            JScrollPane scrollPane = new JScrollPane(jt); // Ajoutez votre JTable à un JScrollPane
            panel.add(scrollPane); // Ajoutez le JScrollPane au panel
            conn.close();
        } catch (Exception d) {
            System.out.println(d);
        }



        frame.add(panel);
        frame.setVisible(true);
    }

}