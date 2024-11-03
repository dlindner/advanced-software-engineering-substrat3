package org.movie.manager.plugin.gui;

import org.movie.manager.adapters.Events.EventCommand;
import org.movie.manager.adapters.Events.GUIEvent;
import org.movie.manager.adapters.Events.GUIEventListener;
import org.movie.manager.application.Services.Attribute;
import org.movie.manager.domain.Movie.MovieID;
import org.movie.manager.domain.Persistable;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

public class TableComponent extends ObservableComponent {

    public enum Commands implements EventCommand {

        ROW_SELECTED( "TableComponent.rowSelected", MovieID.class );
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
            return this.payloadType;
        }
    }

    private JTable table;
    private DefaultTableModel tableModel;
    private Class tableClass;
    private String[] columnNames;
    private Collection<Persistable> tableData;
    private ArrayList<MovieID> movieIDs;

    private final GUIEventListener observer;

    public TableComponent(Class tableClass, String[] columnNames, GUIEventListener observer) {
        this.tableClass = tableClass;
        this.columnNames = columnNames;
        this.observer = observer;
        this.addObserver(observer);
        this.tableData = null;

        tableModel = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }

        };

        this.initUI();
    }

    private void initUI() {
        this.table = new JTable(this.tableModel);
        JScrollPane scrollPane = new JScrollPane(this.table);
        this.setLayout(new GridLayout());
        this.add(scrollPane);
        this.initTable();
    }

    private void initTable() {
        //Table editable
        this.table.setDefaultEditor(Object.class, null);

        this.table.getTableHeader().setPreferredSize(new Dimension(0, 50));
        this.table.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));

        //Table selection mode
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel selectionModel = this.table.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                TableComponent.this.handleSelectionEvent(e);
            }
        });

        //Table header
        table.getTableHeader().setReorderingAllowed(false);

        //Row height
        table.setRowHeight(40);

        //Border
        table.setBorder(BorderFactory.createEmptyBorder());
    }

    private void handleSelectionEvent(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            int row = this.table.getSelectedRow();
            if(row != -1) {
                this.fireGUIEvent(new GUIEvent(this, Commands.ROW_SELECTED, movieIDs.get(row)));
            }
        }
    }

    public void removeSelection() {
        this.table.clearSelection();
    }

    public void setData(Vector<Vector<Attribute>> vectorData) {
        //Remove MovieID from visible data
        this.movieIDs = new ArrayList<>();
        vectorData.forEach(v-> {
            this.movieIDs.add((MovieID) v.get(0).getValue());
            v.remove(0);
        });

        Vector<String> vectorColumns = new Vector<>();
        for(String s : this.columnNames) {
            vectorColumns.add(s);
        }



        this.table.setModel(new DefaultTableModel(vectorData, vectorColumns));
        this.table.setDefaultRenderer(Object.class, new AttributeTableCellRenderer());

        int numColumns = this.table.getColumnCount();
        for(int i = 0; i < numColumns; i++) {
            TableColumn tableColumn = this.table.getColumnModel().getColumn(i);
            tableColumn.setMinWidth(200);
            tableColumn.setMaxWidth(200);
        }

        this.table.updateUI();
    }
}
