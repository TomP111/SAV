package app;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.TableModel;
import app.Reclamation.TableModelHolder;

class ButtonRenderer extends JButton implements TableCellRenderer {

  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column) {
    if (isSelected) {
      setForeground(table.getSelectionForeground());
      setBackground(table.getSelectionBackground());
    } else {
      setForeground(table.getForeground());
      setBackground(UIManager.getColor("Button.background"));
    }
    setText((value instanceof JButton) ? ((JButton) value).getText() : "");
    return this;
  }

  public ButtonRenderer() {
    setOpaque(true);
  }
}

/**
 * @version 1.0 11/09/98
 */

class ButtonEditor extends DefaultCellEditor {
  protected JButton button;

  private String label;

  private boolean isPushed;
  private String idticket;
  private int row2;

  public ButtonEditor(JCheckBox checkBox) {
    super(checkBox);
    button = new JButton();
    button.setOpaque(true);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
      }
    });
  }

  public Component getTableCellEditorComponent(JTable table, Object value,
      boolean isSelected, int row, int column) {
    if (isSelected) {
      button.setForeground(table.getSelectionForeground());
      button.setBackground(table.getSelectionBackground());
    } else {
      button.setForeground(table.getForeground());
      button.setBackground(table.getBackground());
    }
    label = ((value instanceof JButton) ? ((JButton) value).getText() : "");
    idticket = table.getValueAt(row, 0).toString();
    row2 = row;

    button.setText(label);

    isPushed = true;
    return button;
  }

  public Object getCellEditorValue() {
    if (isPushed) {
      if (label == "Supprimer") {
        int choix = JOptionPane.showConfirmDialog(
            null,
            "Voulez-vous continuer ?", // Message à afficher
            "Confirmation", // Titre de la boîte de dialogue
            JOptionPane.YES_NO_OPTION // Type de boutons (Oui/Non)
        );

        if (choix == JOptionPane.YES_OPTION) {
          try {
            // étape 1: charger la classe de driver
            Class.forName("com.mysql.jdbc.Driver");

            // étape 2: créer l'objet de connexion
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3308/sav?characterEncoding=UTF-8&useConfigs=maxPerformance",
                "root", "");

            // étape 3: créer l'objet statement
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM problemes WHERE ID_Ticket = " + idticket);
            conn.close();
            DefaultTableModel model = TableModelHolder.tableModel2;
            model.removeRow(row2);
          } catch (Exception d) {
            System.out.println(d);
          }
        } else {
        }
      } else if (label == "Modifier") {
      }

    }
    isPushed = false;
    return button;
  }

  public boolean stopCellEditing() {
    isPushed = false;
    return super.stopCellEditing();
  }

  protected void fireEditingStopped() {
    super.fireEditingStopped();
  }

  public boolean isCellEditable(int row, int column) {
    return false;
  }

}