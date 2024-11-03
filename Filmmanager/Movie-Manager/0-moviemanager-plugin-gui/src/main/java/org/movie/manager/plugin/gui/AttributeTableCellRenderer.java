package org.movie.manager.plugin.gui;

import org.movie.manager.application.Services.Attribute;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class AttributeTableCellRenderer implements TableCellRenderer {

    Border border = BorderFactory.createMatteBorder(0,0,1,0, Color.BLACK);
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        if( value == null ) {
            JLabel labelNull = new JLabel("");
            labelNull.setBorder( border );
            return labelNull;
        }

        Component guiComp = new JLabel( ((Attribute)value).getValue().toString() );
        ((JLabel)guiComp).setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.BLACK));
        return guiComp;
    }
}
