package org.movie.manager.plugin.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CustomComboBox extends CustomInputField {

    private JLabel field_description;
    private JComboBox comboBox;
    private String[] comboItems;

    public CustomComboBox(String title, String placeholder, String[] comboItems) {
        this.title = title;
        this.placeholder = placeholder;
        this.comboItems = comboItems;
        this.value = "";

        this.setLayout(new BorderLayout(0,0));
        this.setPreferredSize(new Dimension(200,65));
        this.setBorder(new EmptyBorder(5,10,5,10));
        this.setBackground(Color.WHITE);

        field_description = new JLabel(title);
        field_description.setBackground(Color.blue);
        field_description.setPreferredSize(new Dimension(200, 20));
        field_description.setForeground(Color.black);

        comboBox = new JComboBox<String>(comboItems) {
            @Override
            public Object getSelectedItem() {
                Object selected = super.getSelectedItem();

                if (selected == null) {
                    selected = "- Keine Auswahl";

                }
                return selected;
            }
        };
        comboBox.setSelectedIndex(-1);
        comboBox.setPreferredSize(new Dimension(200, 40));
        comboBox.setForeground(Color.black);
        comboBox.setBorder(new EmptyBorder(7,0,0,0));

        comboBox.addActionListener(e -> value = comboBox.getSelectedItem().toString());

        this.add(field_description, BorderLayout.NORTH);
        this.add(comboBox, BorderLayout.CENTER);
    }

    public JComboBox getComboBox() {
        return comboBox;
    }

    @Override
    public void setValue(String value) {
        this.comboBox.setSelectedIndex(Integer.parseInt(value));
        this.comboBox.setEnabled(false);
    }

    @Override
    public void setEnabledState(Boolean enabled) {
        this.comboBox.setEnabled(enabled);
    }

    @Override
    public String getValue() {
        return String.valueOf(this.comboBox.getSelectedIndex());
    }
}
