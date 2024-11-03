package org.movie.manager.plugin.gui;

import org.movie.manager.adapters.Events.*;
import org.movie.manager.adapters.IMDbAPI;
import org.movie.manager.adapters.PropertyManager;
import org.movie.manager.application.Services.Attribute;
import org.movie.manager.domain.Metadata.Metadata;
import org.movie.manager.domain.Movie.Movie;
import org.movie.manager.domain.Persistable;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

public class JavaSwingUI extends ObservableComponent implements GUIEventListener, UpdateEventListener {

    public enum Commands implements EventCommand {

        SET_ALLMOVIES("JavaSwingUI.setAllMovies", null);

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

    private TableComponent tableComponent;
    private JPanel headerPanel, contentPanel, footerPanel, marginLPanel, marginRPanel, filterButtonsPanel;
    private JLabel headlineLabel, dhbwImageLabel;
    private JButton addMovieButton, addFilterButton, resetFilterButton, setAPIKeyButton;

    private PropertyManager propertyManager;
    private IMDbAPI imdBapi;

    private GUIEditMovie guiEditMovieFrame;

    private Border emptyBorder;

    public JavaSwingUI(PropertyManager propertyManager, IMDbAPI imdBapi) {
        this.propertyManager = propertyManager;
        this.imdBapi = imdBapi;
        initUI();
    }

    private void initUI() {
        this.setLayout(new BorderLayout(0,0));
        emptyBorder = BorderFactory.createEmptyBorder();

        //Header
        headerPanel = new JPanel(new BorderLayout(0,0));
        headerPanel.setPreferredSize(new Dimension(900, 70));
        headerPanel.setBackground(Color.lightGray);
        headerPanel.setBorder(BorderFactory.createMatteBorder(0,0,3,0, Color.BLACK));
        headlineLabel = new JLabel("Movie Manager");
        headlineLabel.setFont(new Font(Font.SANS_SERIF, 1, 30));
        headlineLabel.setBorder(new EmptyBorder(0,50,0,100));
        dhbwImageLabel = new JLabel(getImage("dhbw.png"));
        dhbwImageLabel.setPreferredSize(new Dimension(200, 70));
        headerPanel.add(dhbwImageLabel, BorderLayout.EAST);
        headerPanel.add(headlineLabel, BorderLayout.WEST);

        filterButtonsPanel = new JPanel();
        filterButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        filterButtonsPanel.setBackground(Color.lightGray);

        addFilterButton = new JButton(getImage("filter.png"));
        addFilterButton.addActionListener(e -> {
            System.out.println("New GUIEvent: JavaSwingUI.filterUI");
            IOUtilities.openInJDialog(new GUIAddFilter(this,this), 300, 500, 350, 250, "Add Filter", null, false);
        });
        addFilterButton.setPreferredSize(new Dimension(110,60));
        addFilterButton.setVisible(true);
        addFilterButton.setBackground(null);
        addFilterButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addFilterButton.setBorder(emptyBorder);
        filterButtonsPanel.add(addFilterButton);

        resetFilterButton = new JButton(getImage("del_filter.png"));
        resetFilterButton.addActionListener(e -> {
            System.out.println("New GUIEvent: JavaSwingUI.filterReset");
            this.fireGUIEvent(new GUIEvent(this, Commands.SET_ALLMOVIES, null));
            changeFilterButtons();
        });
        resetFilterButton.setPreferredSize(new Dimension(110,60));
        resetFilterButton.setVisible(false);
        resetFilterButton.setBackground(null);
        resetFilterButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        resetFilterButton.setBorder(emptyBorder);
        filterButtonsPanel.add(resetFilterButton);

        headerPanel.add(filterButtonsPanel, BorderLayout.CENTER);

        //Content
        contentPanel = new JPanel(new BorderLayout(0,0));
        contentPanel.setPreferredSize(new Dimension(900, 460));
        contentPanel.setBackground(Color.green);
        marginLPanel = new JPanel();
        marginLPanel.setPreferredSize(new Dimension(50, 460));
        marginLPanel.setBackground(Color.lightGray);
        marginLPanel.setBorder(BorderFactory.createMatteBorder(0,0,0,3, Color.BLACK));
        contentPanel.add(marginLPanel, BorderLayout.WEST);
        marginRPanel = new JPanel();
        marginRPanel.setPreferredSize(new Dimension(50, 460));
        marginRPanel.setBackground(Color.lightGray);
        marginRPanel.setBorder(BorderFactory.createMatteBorder(0,3,0,0, Color.BLACK));
        contentPanel.add(marginRPanel, BorderLayout.EAST);

        //Footer
        footerPanel = new JPanel(new BorderLayout(0,0));
        footerPanel.setPreferredSize(new Dimension(900, 70));
        footerPanel.setBackground(Color.lightGray);
        footerPanel.setBorder(BorderFactory.createMatteBorder(3,0,0,0, Color.BLACK));
        addMovieButton = new JButton(getImage("add.png"));
        addMovieButton.addActionListener(e -> {
            System.out.println("New GUIEvent: JavaSwingUI.addMovieUI");
            guiEditMovieFrame = new GUIEditMovie(JavaSwingUI.this);
            IOUtilities.openInJDialog(guiEditMovieFrame, 600, 550, 350, 250, "Movie Manager", null, false);
        });
        addMovieButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMovieButton.setBorder(emptyBorder);
        addMovieButton.setBackground(null);
        footerPanel.add(addMovieButton, BorderLayout.EAST);
        setAPIKeyButton = new JButton(getImage("api.png"));
        setAPIKeyButton.addActionListener(e -> {
            System.out.println("New GUIEvent: JavaSwingUI.APIKeyUI");
            String key = JOptionPane.showInputDialog("Enter the API-key");
            if(key != null) {
                propertyManager.setProperty("API_KEY", key);
                try {
                    propertyManager.saveConfiguration();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                imdBapi.setApiKeyFromPropertyManager();
            }
        });
        setAPIKeyButton.setBackground(null);
        setAPIKeyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        setAPIKeyButton.setBorder(emptyBorder);
        footerPanel.add(setAPIKeyButton, BorderLayout.WEST);

        //Table
        String[] columnNames = {"Title", "Genre", "Jahr", "Laufzeit(min)"};
        tableComponent = new TableComponent(Movie.class, columnNames, this);
        tableComponent.setPreferredSize(new Dimension(800, 460));
        contentPanel.add(tableComponent, BorderLayout.CENTER);

        this.add(headerPanel, BorderLayout.NORTH);
        this.add(contentPanel, BorderLayout.CENTER);
        this.add(footerPanel, BorderLayout.SOUTH);
    }

    private ImageIcon getImage(String fileName) {
        ImageIcon imageIcon = null;
        try {
            BufferedImage myPicture = ImageIO.read(getFileFromResource(fileName));
            imageIcon = new ImageIcon(myPicture);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return imageIcon;
    }

    public void changeFilterButtons() {
        if(resetFilterButton.isVisible()) {
            resetFilterButton.setVisible(false);
            addFilterButton.setVisible(true);
        }else {
            resetFilterButton.setVisible(true);
            addFilterButton.setVisible(false);
        }
    }

    private File getFileFromResource(String fileName) throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return new File(resource.toURI());
        }
    }

    @Override
    public void processGUIEvent(GUIEvent ge) {
        System.out.println("New GUIEvent: "+ ge.getCmdText());
        fireGUIEvent(ge);
    }

    @Override
    public void processUpdateEvent(UpdateEvent event) {
        System.out.println("New UpdateEvent: "+ event.getCmdText());
        if(event.getCmdText().equals("Controller.setMovies")) {
            Vector<Vector<Attribute>> m = (Vector<Vector<Attribute>>) event.getData();
            this.tableComponent.setData(m);
        }else if(event.getCmdText().equals("Controller.setDetailData")) {
            ArrayList<Persistable> allMovieData = (ArrayList<Persistable>)event.getData();
            guiEditMovieFrame = new GUIEditMovie((Movie)allMovieData.get(0), (Metadata) allMovieData.get(1), null, JavaSwingUI.this);
            IOUtilities.openInJDialog(guiEditMovieFrame, 600, 550, 350, 250, "Movie Manager", null, false);
            this.tableComponent.removeSelection();
        }else if(event.getCmdText().equals("Controller.setIMDBData")) {
            guiEditMovieFrame.setIMBDData(((Map<String, String>) event.getData()));
        }

    }
}
