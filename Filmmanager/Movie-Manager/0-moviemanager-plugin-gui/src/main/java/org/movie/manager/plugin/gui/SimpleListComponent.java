package org.movie.manager.plugin.gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class SimpleListComponent extends ObservableComponent {

    String title;
    JList list;

    public SimpleListComponent (String title) {
        this.title = title;
        initUI();
    }

    private void initUI() {
        this.setLayout(new GridLayout(1,1));
        this.setBorder(BorderFactory.createTitledBorder(this.title));
        this.list = new JList();
        this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println("Selected!");
            }
        });

        JScrollPane scroll = new JScrollPane(this.list);
        this.add(scroll, "Center");

    }

}
