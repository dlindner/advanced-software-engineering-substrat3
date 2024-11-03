package org.movie.manager.plugin.gui;

import org.movie.manager.adapters.Events.EventCommand;
import org.movie.manager.adapters.Events.GUIEvent;
import org.movie.manager.adapters.Events.GUIEventListener;
import org.movie.manager.application.Services.Filter;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUIAddFilter extends ObservableComponent {

    public enum Commands implements EventCommand {

        SET_MOVIESWFILTER("GUIAddFilter.setMoviesWithFilter", List.class);

        public final Class<?> payloadType;
        public final String cmdText;

        private Commands( String cmdText, Class<?> payloadType ) {
            this.cmdText = cmdText;
            this.payloadType = payloadType;
        }

        @Override
        public String getCmdText() {
            return this.cmdText;
        }

        @Override
        public Class<?> getPayloadType() {
            return null;
        }
    }

    CustomTextField orbField, orsField;
    JButton setFilterButton;

    public GUIAddFilter(JavaSwingUI parent, GUIEventListener listener) {
        this.setLayout(new BorderLayout(0,0));
        this.addObserver(listener);
        JPanel panel = new JPanel(new FlowLayout());
        orbField = new CustomTextField("Own rating bigger than ...", "");
        orsField = new CustomTextField("Own rating smaller than ...", "");
        setFilterButton = new JButton("Set Filter");
        setFilterButton.setPreferredSize(new Dimension(300, 70));
        setFilterButton.addActionListener(e -> {
            ArrayList<Filter> filter = new ArrayList<Filter>();
            if(!orbField.getValue().isEmpty()) {
                filter.add(new Filter("ownratingBigger", Integer.parseInt(orbField.getValue())));
            }
            if(!orsField.getValue().isEmpty()) {
                filter.add(new Filter("ownratingSmaller", Integer.parseInt(orsField.getValue())));
            }
            if(filter.size() > 0) {
                fireGUIEvent(new GUIEvent(this, Commands.SET_MOVIESWFILTER, filter));
                parent.changeFilterButtons();
                orsField.getTextfield().setEnabled(false);
                orbField.getTextfield().setEnabled(false);
                setFilterButton.setVisible(false);
            }
        });
        panel.add(orbField);
        panel.add(orsField);
        panel.add(setFilterButton);
        this.add(panel, BorderLayout.CENTER);
    }
}