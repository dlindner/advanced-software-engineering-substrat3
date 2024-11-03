package org.movie.manager.plugin.gui;

import org.movie.manager.adapters.Events.EventCommand;
import org.movie.manager.adapters.Events.GUIEvent;
import org.movie.manager.adapters.Events.GUIEventListener;
import org.movie.manager.domain.FilmProfessional.FilmProfessional;
import org.movie.manager.domain.Metadata.*;
import org.movie.manager.domain.Movie.Movie;
import org.movie.manager.domain.Movie.MovieID;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class GUIEditMovie extends ObservableComponent {

    public enum Commands implements EventCommand {

        GET_IMBDbT("GUIEditMovie.getIMBDbT", String.class),
        GET_IMBDbID("GUIEditMovie.getIMBDbID", String.class),
        UPDATE_MOVIE("GUIEditMovie.updateMovie", ArrayList.class);

        public final Class<?> payloadType;
        public final String cmdText;

        private Commands(String cmdText, Class<?> payloadType) {
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

    CustomTextField titleField, genreField, releaseYearField, runningTimeInMinField;

    CustomTextField nameOrMediumField, descriptionField, imdbIDField, imbdRatingField, imbdMetascoreField, ownRatingField;

    CustomComboBox ownershipField;

    JPanel moviePanel, metadataPanel, filmProfessionalPanel, headerPanel, footerPanel;

    JButton editSaveButton, imdbButton;

    MovieID movieID;
    MetadataID metadataID;

    public GUIEditMovie(GUIEventListener observer) {
        this.addObserver(observer);
        this.setLayout(new BorderLayout(0, 0));
        movieID = null;
        metadataID = null;
        initInputFields();
        imdbIDField.setValue("not set");
        imbdMetascoreField.setValue("not set");
        imbdRatingField.setValue("not set");
        editSaveButton = new JButton("Save");
        editSaveButton.addActionListener(e -> {
            System.out.println("Save movie");
            saveMovie();
        });
        footerPanel.add(editSaveButton, BorderLayout.CENTER);

        imdbButton = new JButton("IMDb Integration");
        imdbButton.addActionListener(e -> {
            String result = JOptionPane.showInputDialog("Enter movie title or IMDb-ID to search");
            if(result != null) {
                if (result.startsWith("tt")) {
                    this.fireGUIEvent(new GUIEvent(this, Commands.GET_IMBDbID, result));
                } else {
                    this.fireGUIEvent(new GUIEvent(this, Commands.GET_IMBDbT, result));
                }
            }
        });
        headerPanel.add(imdbButton, BorderLayout.CENTER);
    }

    public GUIEditMovie(Movie movie, Metadata metadata, Collection<FilmProfessional> filmProfessionals, GUIEventListener observer) {
        this.addObserver(observer);
        this.setLayout(new BorderLayout(0, 0));
        movieID = movie.getMovieID();
        metadataID = metadata.getMetadataID();
        initInputFields();
        titleField.setValue(movie.getTitel());
        genreField.setValue(movie.getGenre());
        releaseYearField.setValue(String.valueOf(movie.getReleaseYear()));
        runningTimeInMinField.setValue(String.valueOf(movie.getRunningTimeInMin()));
        ownershipField.setValue(String.valueOf(metadata.getAvailability().getOwnership().ordinal())); //TODO Dropdown
        nameOrMediumField.setValue(metadata.getAvailability().getNameOrMedium());
        descriptionField.setValue(metadata.getAvailability().getDescription());
        if(metadata.getImbDdata() != null) {
            imdbIDField.setValue(metadata.getImbDdata().getiMDBID());
            imbdRatingField.setValue(String.valueOf(metadata.getImbDdata().getiMDbRating()));
            imbdMetascoreField.setValue(String.valueOf(metadata.getImbDdata().getMetascore()));
        }else {
            imdbIDField.setValue("not set");
            imbdRatingField.setValue("not set");
            imbdMetascoreField.setValue("not set");
        }
        ownRatingField.setValue(String.valueOf(metadata.getOwnRating().getRating()));
        //TODO fill remaining fields
        editSaveButton = new JButton("Edit");
        editSaveButton.setVisible(true);
        editSaveButton.addActionListener(e -> {
            if (editSaveButton.getText() == "Edit") {
                this.setWindowState(true);
                this.editSaveButton.setText("Save");
                metadataPanel.remove(editSaveButton);
                metadataPanel.updateUI();
                footerPanel.add(editSaveButton, BorderLayout.CENTER);
            } else {
                this.saveMovie();
            }
        });
        editSaveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editSaveButton.setPreferredSize(new Dimension(120,65));
        metadataPanel.add(editSaveButton);
    }

    private void initInputFields() {
        //Movie Panel (west)
        moviePanel = new JPanel();
        moviePanel.setPreferredSize(new Dimension(300, 500));
        titleField = new CustomTextField("Titel", "Title of the movie");
        genreField = new CustomTextField("Genre", "Genre of the movie");
        releaseYearField = new CustomTextField("Year of publication (Integer)", "Release year of the movie");
        runningTimeInMinField = new CustomTextField("Running time in minutes (Integer)", "Running time of the movie");
        moviePanel.add(titleField);
        moviePanel.add(genreField);
        moviePanel.add(releaseYearField);
        moviePanel.add(runningTimeInMinField);

        //Metadata Panel (center)
        metadataPanel = new JPanel();
        metadataPanel.setPreferredSize(new Dimension(300, 500));
        ownershipField = new CustomComboBox("Ownership", "Availability of the movie", Ownership.getArray());
        nameOrMediumField = new CustomTextField("Name or Medium", "Name or Medium of the movie");
        descriptionField = new CustomTextField("Description", "Description of the movie");
        ownRatingField = new CustomTextField("Own rating from 1 to 10 (Integer)", "Own rating of the movie");
        imdbIDField = new CustomTextField("IMDb ID", "IMDb data of the movie");
        imbdRatingField = new CustomTextField("IMDb rating", "IMDb data of the movie");
        imbdMetascoreField = new CustomTextField("IMDb metascore", "IMDb data of the movie");

        moviePanel.add(ownershipField);
        moviePanel.add(nameOrMediumField);
        metadataPanel.add(descriptionField);
        metadataPanel.add(ownRatingField);
        metadataPanel.add(imdbIDField);
        metadataPanel.add(imbdRatingField);
        metadataPanel.add(imbdMetascoreField);


        //FilmProfessional Panel (east)
        filmProfessionalPanel = new JPanel();
        filmProfessionalPanel.setPreferredSize(new Dimension(300, 500));
        filmProfessionalPanel.setBackground(Color.green);

        SimpleListComponent simpleListComponent = new SimpleListComponent("Film Professionals");
        filmProfessionalPanel.add(simpleListComponent, BorderLayout.CENTER);

        headerPanel = new JPanel(new BorderLayout(0, 0));
        headerPanel.setPreferredSize(new Dimension(600, 30));
        this.add(headerPanel, BorderLayout.NORTH);

        footerPanel = new JPanel(new BorderLayout(0, 0));
        footerPanel.setPreferredSize(new Dimension(600, 50));
        this.add(footerPanel, BorderLayout.SOUTH);

        this.add(moviePanel, BorderLayout.WEST);
        this.add(metadataPanel, BorderLayout.CENTER);
        //this.add(filmProfessionalPanel, BorderLayout.EAST);
    }

    private void saveMovie() {
        try {
            Availability availability = new Availability(Ownership.values()[Integer.valueOf(ownershipField.getValue())], nameOrMediumField.getValue(), descriptionField.value);
            IMDbData IMDbData;
            System.out.println(imdbIDField.getValue());
            if(imdbIDField.getValue().contains("not set")) {
                IMDbData = null;
            }else {
                if (imbdMetascoreField.getValue().equals("N/A")) {
                    IMDbData = new IMDbData(imdbIDField.getValue(), Double.valueOf(imbdRatingField.getValue()), -1);
                } else {
                    IMDbData = new IMDbData(imdbIDField.getValue(), Double.valueOf(imbdRatingField.getValue()), Integer.valueOf(imbdMetascoreField.getValue()));
                }
            }

            Rating rating;
            System.out.println(ownRatingField.getValue());
            if (ownRatingField.getValue().contains("Own rating of the movie")) {
                rating = new Rating(-1);
            }else {
                rating = new Rating(Integer.valueOf(ownRatingField.getValue()));
            }
            Metadata metadata = new Metadata(this.metadataID, availability, IMDbData, rating, null);
            Movie movie = new Movie(this.movieID, titleField.getValue(), genreField.getValue(), Integer.valueOf(releaseYearField.getValue()), Integer.valueOf(runningTimeInMinField.getValue()), metadata.getMetadataID(), null, null, null);
            metadata.setMovie(movie.getMovieID());
            ArrayList movieData = new ArrayList();
            movieData.add(movie);
            movieData.add(metadata);
            movieData.add(null);
            this.fireGUIEvent(new GUIEvent(this, Commands.UPDATE_MOVIE, movieData));
            //Disable GUI Window
            if(imdbButton != null) {
                imdbButton.setVisible(false);
            }
            this.setWindowState(false);
        } catch (Exception ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(this, "Incorrect input data");
        }

    }

    private void setWindowState(Boolean editable) {
        if (editable) {
            titleField.setEnabledState(true);
            genreField.setEnabledState(true);
            releaseYearField.setEnabledState(true);
            runningTimeInMinField.setEnabledState(true);
            ownershipField.setEnabledState(true);
            nameOrMediumField.setEnabledState(true);
            descriptionField.setEnabledState(true);
            ownershipField.setEnabledState(true);
            ownRatingField.setEnabledState(true);
            editSaveButton.setVisible(true);
        } else {
            titleField.setEnabledState(false);
            genreField.setEnabledState(false);
            releaseYearField.setEnabledState(false);
            runningTimeInMinField.setEnabledState(false);
            ownershipField.setEnabledState(false);
            nameOrMediumField.setEnabledState(false);
            descriptionField.setEnabledState(false);
            ownershipField.setEnabledState(false);
            ownRatingField.setEnabledState(false);
            editSaveButton.setVisible(false);
        }
    }

    public void setIMBDData(Map<String, String> result) {
        if (result == null) {
            JOptionPane.showMessageDialog(this, "Fehler bei der Anfrage");
        } else {
            titleField.getTextfield().setText(result.get("Title"));
            titleField.getTextfield().setForeground(Color.black);
            genreField.getTextfield().setText(result.get("Genre"));
            genreField.getTextfield().setForeground(Color.black);
            releaseYearField.getTextfield().setText(result.get("Year"));
            releaseYearField.getTextfield().setForeground(Color.black);
            runningTimeInMinField.getTextfield().setText(result.get("Runtime").split(" ")[0]);
            runningTimeInMinField.getTextfield().setForeground(Color.black);
            imdbIDField.getTextfield().setText(result.get("imdbID"));
            imbdRatingField.getTextfield().setText(result.get("imdbRating"));
            imbdMetascoreField.getTextfield().setText(result.get("Metascore"));
        }
    }
}
