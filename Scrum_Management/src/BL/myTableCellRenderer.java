/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author johannesriedmueller
 */
public class myTableCellRenderer implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = new JLabel();
        label.setOpaque(true);
        if (value != null) {
            User user = (User) value;
            switch (column) {
                case 0:
                    label.setText(user.getName());
                    break;
                case 1:
                    label.setBackground(user.getColor());
                    break;
                default:
                    label.setText("???");
            }
        }

        if (isSelected && column!=1) {
            label.setBackground(Color.LIGHT_GRAY);
        }

        return label;
    }

}
