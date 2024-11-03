/*
Adopted/inspired by the lecture Software Engineering 4th semester DHBW 2022 by Mr. Lutz
 */
package org.movie.manager.adapters;
import org.movie.manager.adapters.Mapper.FilmProfessionalsMapper;
import org.movie.manager.adapters.Mapper.MetadataMapper;
import org.movie.manager.adapters.Mapper.MovieMapper;
import org.movie.manager.domain.FilmProfessional.FilmProfessionalID;
import org.movie.manager.domain.Movie.MovieID;
import org.movie.manager.domain.Persistable;
import org.movie.manager.domain.Metadata.*;
import org.movie.manager.domain.FilmProfessional.FilmProfessional;
import org.movie.manager.domain.Movie.Movie;

import java.util.*;

public class EntityFactory { // for creating a family of objects

    private EntityManager entityManager;
    private Database csvDB;

    private Persistable persistableElement = null;

    public EntityFactory(EntityManager em, Database csvDB) {
        this.entityManager = em;
        this.csvDB = csvDB;
    }

    public void loadData(){
        String filePath;
        List<String[]> csvData;

        //FilmProfessional
        filePath = "FilmProfessional.csv";
        csvData = csvDB.readData(filePath);
        csvData.forEach( e -> {
            try {
                this.createFilmProfessional(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        //Movie
        filePath = "Movie.csv";
        csvData = csvDB.readData(filePath);
        csvData.forEach( e -> {
            try {
                this.createMovie(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        //Metadata
        filePath = "Metadata.csv";
        csvData = csvDB.readData(filePath);
        csvData.forEach( e -> {
            try {
                this.createMetadata(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

    /**
     * @param csvData the data read from a CSV file
     * @return the instance of the element just created
     * @throws Exception
     */
    public Persistable createMovie(String[] csvData) throws Exception {
        MovieID movieID = new MovieID(UUID.fromString(csvData[MovieMapper.Header.MOVIEID.ordinal()]));
        String titel = csvData[MovieMapper.Header.TITEL.ordinal()];
        String genre = csvData[MovieMapper.Header.GENRE.ordinal()];
        int releaseYear = Integer.parseInt(csvData[MovieMapper.Header.RELEASEYEAR.ordinal()]);
        int runningTimeInMin = Integer.parseInt(csvData[MovieMapper.Header.RUNNINGTIMEINMIN.ordinal()]);
        //METADATA
        String metadatenString = csvData[MovieMapper.Header.METADATA.ordinal()];
        MetadataID metadatenID;
        if(metadatenString.length() == 0){
            metadatenID=null;
        }else {
            metadatenID = new MetadataID(UUID.fromString(metadatenString));
        }
        //DIRECTORS
        String listOfDirectorsString = csvData[MovieMapper.Header.DIRECTORS.ordinal()];
        List<FilmProfessionalID> listDirectors;
        if(listOfDirectorsString.length() <= 1){
            listDirectors=null;
        }else {
            listDirectors = new ArrayList<>();
            for (String s : listOfDirectorsString.split(",")) {
                listDirectors.add(new FilmProfessionalID(UUID.fromString(s)));
            }
        }
        //ACTORS
        String listOfActorsString = csvData[MovieMapper.Header.ACTORS.ordinal()];
        List<FilmProfessionalID> listActors;
        if(listOfActorsString.length() <= 1){
            listActors=null;
        }else {
            listActors = new ArrayList<>();
            for (String s : listOfActorsString.split(",")) {
                listActors.add(new FilmProfessionalID(UUID.fromString(s)));
            }
        }
        //SCREENWRITERS
        String listOfScreenwritersString = csvData[MovieMapper.Header.SCREENWRITERS.ordinal()];
        List<FilmProfessionalID> listScreenwriters;
        if(listOfScreenwritersString.length() <= 1){
            listScreenwriters=null;
        }else {
            listScreenwriters = new ArrayList<>();
            for (String s : listOfScreenwritersString.split(",")) {
                listScreenwriters.add(new FilmProfessionalID(UUID.fromString(s)));
            }
        }


        persistableElement = new Movie(movieID, titel, genre, releaseYear, runningTimeInMin, metadatenID, listDirectors, listActors, listScreenwriters);

        entityManager.persist(persistableElement);

        return persistableElement;

    }


    /**
     * @param csvData the data read from a CSV file
     * @return the instance of the element just created
     * @throws Exception
     */
    public Persistable createMetadata(String[] csvData) throws Exception {
        MetadataID metaDataID = new MetadataID(UUID.fromString(csvData[MetadataMapper.Header.METADATAID.ordinal()]));
        //availability
        Ownership ownership = Ownership.valueOf(csvData[MetadataMapper.Header.OWNERSHIP.ordinal()]);
        String nameOrMedium = csvData[MetadataMapper.Header.NAMEORMEDIUM.ordinal()];
        String description = csvData[MetadataMapper.Header.DESCRIPTION.ordinal()];
        Availability availability = new Availability(ownership, nameOrMedium, description);
        //imbDdata
        String iMDBID = csvData[MetadataMapper.Header.IMBDID.ordinal()];
        String iMDBRatingString = csvData[MetadataMapper.Header.IMDBRATING.ordinal()];
        double iMDBRating =-1;
        if(!iMDBRatingString.equals("")){
            iMDBRating = Double.parseDouble(iMDBRatingString);
        }
        String metascoreString = csvData[MetadataMapper.Header.METASCORE.ordinal()];
        int metascore =-1;
        if(!metascoreString.equals("")){
            metascore = Integer.parseInt(metascoreString);
        }
        IMDbData IMDbData = new IMDbData(iMDBID, iMDBRating, metascore );
        //ownRating
        int rating = Integer.parseInt(csvData[MetadataMapper.Header.RATING.ordinal()]);
        Rating ownRating = new Rating(rating);
        //movie
        String movieString = csvData[MetadataMapper.Header.MOVIE.ordinal()];
        MovieID movieID;
        if(movieString.length() == 0){
            movieID=null;
        }else {
            movieID = new MovieID(UUID.fromString(movieString));
        }


        persistableElement = new Metadata(metaDataID, availability, IMDbData, ownRating, movieID);

        entityManager.persist(persistableElement);

        return persistableElement;
    }


    /**
     * @param csvData the data read from a CSV file
     * @return the instance of the element just created
     * @throws Exception
     */
    public Persistable createFilmProfessional(String[] csvData) throws Exception {

        FilmProfessionalID filmProfessionalID = new FilmProfessionalID(UUID.fromString(csvData[FilmProfessionalsMapper.Header.FILMPROFESSIONALID.ordinal()]));
        String firstName = csvData[FilmProfessionalsMapper.Header.FIRSTNAME.ordinal()];
        String secondName = csvData[FilmProfessionalsMapper.Header.SECONDNAME.ordinal()];
        String biography = csvData[FilmProfessionalsMapper.Header.BIOGRAPHY.ordinal()];
        //MOVIES
        String listOfMoviesString = csvData[FilmProfessionalsMapper.Header.MOVIES.ordinal()];
        List<MovieID> listMovies;
        if (listOfMoviesString.length() == 0) {
            listMovies = null;
        } else {
            listMovies = new ArrayList<>();
            for (String s : listOfMoviesString.split(",")) {
                listMovies.add(new MovieID(UUID.fromString(s)));
            }
        }


        persistableElement = new FilmProfessional(filmProfessionalID, firstName, secondName, biography, listMovies);

        entityManager.persist(persistableElement);

        return persistableElement;
    }
}
