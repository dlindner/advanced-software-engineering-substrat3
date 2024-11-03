package org.movie.manager.plugin.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class CustomTextField extends CustomInputField {


    private JLabel field_description;
    private JTextField textfield;

    public CustomTextField(String title, String placeholder) {
        this.title = title;
        this.placeholder = placeholder;
        this.value = "";

        //InitUI
        this.setLayout(new BorderLayout(0,0));
        this.setPreferredSize(new Dimension(250,65));
        this.setBorder(new EmptyBorder(5,10,5,10));
        field_description = new JLabel(title);
        field_description.setPreferredSize(new Dimension(250, 20));
        field_description.setBorder(new EmptyBorder(5,0,7,0));
        field_description.setForeground(Color.black);

        textfield = new JTextField(placeholder);
        textfield.setBorder(BorderFactory.createEmptyBorder(7,12,7,12));
        textfield.setPreferredSize(new Dimension(250, 30));
        textfield.setForeground(Color.lightGray);
        textfield.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textfield.getText().equals(placeholder)) {
                    textfield.setText("");
                    textfield.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                value = textfield.getText();
                if (textfield.getText().isEmpty()) {
                    textfield.setText(placeholder);
                    textfield.setForeground(Color.lightGray);
                    textfield.setText(placeholder);
                }
            }
        });
        this.add(field_description, BorderLayout.NORTH);

        JPanel borderPanel = new JPanel(new BorderLayout(0,0));
        borderPanel.setBorder(new LineBorder(Color.BLACK));
        borderPanel.add(textfield);

        this.add(borderPanel, BorderLayout.SOUTH);
    }

    public JTextField getTextfield() {
        return textfield;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
        this.textfield.setForeground(Color.black);
        this.textfield.setText(value);
        this.textfield.setEnabled(false);
    }

    @Override
    public void setEnabledState(Boolean enabled) {
        this.textfield.setEnabled(enabled);
    }

    @Override
    public String getValue() {
        return this.textfield.getText();
    }
}
