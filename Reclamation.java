package app;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;
import java.util.*;

public class Reclamation {
    public static void reclamer() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Application SAV");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(5, 5, 5, 5);

            JLabel nameLabel = new JLabel("Nom du client :");
            JTextField nameField = new JTextField(15);

            JLabel prenomLabel = new JLabel("Prénom :");
            JTextField prenomField = new JTextField(15);

            JLabel issueLabel = new JLabel("Description :");
            JTextArea issueArea = new JTextArea();
            issueArea.setWrapStyleWord(true);
            issueArea.setLineWrap(true);

            JLabel stateLabel = new JLabel("Etat :");
            String[] etatOptions = {"Nouveau", "En cours", "Fermé"};
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

                    try
                    {
                        //étape 1: charger la classe de driver
                        Class.forName("com.mysql.jdbc.Driver");
            
                        //étape 2: créer l'objet de connexion
                        Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/sav?characterEncoding=latin1&useConfigs=maxPerformance", "root", "");
            
                        //étape 3: créer l'objet statement 
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT ID_Client FROM clients WHERE Nom LIKE '"+clientName+"' AND Prenom LIKE '"+clientPrename+"'");
                        
                        while(rs.next()){
                            idclient = rs.getString("ID_Client");
                        }
            
                        //étape 5: fermez l'objet de connexion
                        conn.close();
                    }
                    catch(Exception d){ 
                        System.out.println(d);
                    }

                    if(idclient.isEmpty()){
                        JOptionPane.showMessageDialog(frame, "Le client n'a pas été trouvé !");
                        frame.dispose();
                    } else{
                        try
                        {
                            //étape 1: charger la classe de driver
                            Class.forName("com.mysql.jdbc.Driver");
                
                            //étape 2: créer l'objet de connexion
                            Connection conn = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/sav?characterEncoding=latin1&useConfigs=maxPerformance", "root", "");
                
                            //étape 3: créer l'objet statement 
                            Statement stmt = conn.createStatement();
                            stmt.executeUpdate("INSERT INTO reclamations(ID_Client,Description,Etat,Date) VALUES ('"+ idclient +"', '"+issue+"','"+ selectedState +"', '"+format.format(date)+"')");
                            
                
                            //étape 5: fermez l'objet de connexion
                            conn.close();
                        }
                        catch(Exception d){ 
                            System.out.println(d);
                        }

                        JOptionPane.showMessageDialog(frame, "Demande soumise avec succès!");
                        frame.dispose();
                        }
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
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel.add(issueArea, gbc);
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel.add(submitButton, gbc);
            frame.getContentPane().add(panel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public static void voirReclamations() {
        // Code pour voir les réclamations
        // Vous pouvez ouvrir une nouvelle fenêtre ou effectuer une action spécifique ici
        System.out.println("Voir les réclamations");
    }
}