package org.movie.manager.plugin.main;

import org.movie.manager.adapters.Mapper.FilmProfessionalsMapper;
import org.movie.manager.adapters.Mapper.MetadataMapper;
import org.movie.manager.adapters.Mapper.MovieMapper;
import org.movie.manager.domain.FilmProfessional.FilmProfessionalID;
import org.movie.manager.domain.Metadata.*;
import org.movie.manager.domain.FilmProfessional.FilmProfessional;
import org.movie.manager.domain.Movie.Movie;
import org.movie.manager.plugin.csvdatabase.CSVDatabaseManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class CreateFiles {

    public static void main(String[] args) {
        createCSVFiles("0-moviemanager-plugin-main/target/classes/CSVFiles/");
    }

    public static void createCSVFiles(final String CSV_PATH_DEFAULT){

        try {
            Object[] dataLevel1;
            Object[][] objectArray2Dem;
            String filePath;
            CSVDatabaseManager writer = new CSVDatabaseManager(CSV_PATH_DEFAULT);;

            File folder = new File(CSV_PATH_DEFAULT);
            if (!folder.mkdir())
                folder.createNewFile();

            //FILMPROFESSIONALID
            FilmProfessional direktor1 = new FilmProfessional(null, "Sidney", "Lumet",null, null);
            FilmProfessional direktor2 = new FilmProfessional(null, "Christopher", "Nolan",null, null);
            FilmProfessional actor1_1 = new FilmProfessional(null, "Henry", "Fonda",null, null);
            FilmProfessional actor1_2 = new FilmProfessional(null, "Martin", "Balsam",null, null);

            Collection<FilmProfessionalID> direktor1List = new ArrayList<>();
            Collection<FilmProfessionalID> actor1List = new ArrayList<>();
            Collection<FilmProfessionalID> direktor2List = new ArrayList<>();
            direktor1List.add(direktor1.getFilmProfessionalID());
            actor1List.add(actor1_1.getFilmProfessionalID());
            actor1List.add(actor1_2.getFilmProfessionalID());
            direktor2List.add(direktor2.getFilmProfessionalID());


            //Movies
            Movie movie1 = new Movie(null, "12 Angry Men", "Drama", 1957, 96, null, direktor1List, actor1List, null);
            Movie movie2 = new Movie(null, "The Dark Knight", "Action", 2008, 152, null, direktor2List, null, null);
            Movie movie3 = new Movie(null, "Fight Club", "Drama", 1999, 139, null, null, null, null);

            direktor1.addMovie(movie1.getMovieID());
            direktor2.addMovie(movie2.getMovieID());
            actor1_1.addMovie(movie1.getMovieID());
            actor1_2.addMovie(movie2.getMovieID());

            //Metadata
            Availability availability1 = new Availability(Ownership.PHYSICALLY, "DVD", "");
            Availability availability2 = new Availability(Ownership.ONLINE, "Amazon", "HD");
            Availability availability3 = new Availability(Ownership.PHYSICALLY, "DVD", "");

            IMDbData IMDbData1 = new IMDbData("tt0050083", 9.0, 97);
            IMDbData IMDbData2 = new IMDbData("tt0468569", 9.0, 84);
            IMDbData IMDbData3 = new IMDbData("tt0137523", 8.8, 66);

            Rating rating9 = new Rating(9);
            Rating rating10 = new Rating(10);

            Metadata metadata1 = new Metadata(null, availability1, IMDbData1, rating10, movie1.getMovieID() );
            Metadata metadata2 = new Metadata(null, availability2, IMDbData2, rating9, movie2.getMovieID() );
            Metadata metadata3 = new Metadata(null, availability3, IMDbData3, rating9, movie3.getMovieID());

            movie1.setMetadataID(metadata1.getMetadataID());
            movie2.setMetadataID(metadata2.getMetadataID());
            movie3.setMetadataID(metadata3.getMetadataID());

            filePath = "FilmProfessional.csv"; // ohne "file:" am Anfang
            objectArray2Dem = new Object[4][];
            FilmProfessionalsMapper FilmProfessionalsMapper = new FilmProfessionalsMapper();
            objectArray2Dem[0]= FilmProfessionalsMapper.mapData(direktor1);
            objectArray2Dem[1]= FilmProfessionalsMapper.mapData(direktor2);
            objectArray2Dem[2]= FilmProfessionalsMapper.mapData(actor1_1);
            objectArray2Dem[3]= FilmProfessionalsMapper.mapData(actor1_2);
            writer.saveData(filePath, Arrays.asList(objectArray2Dem), MetadataMapper.getHeader());

            filePath = "Movie.csv"; // ohne "file:" am Anfang
            objectArray2Dem = new Object[3][];
            MovieMapper CSVMovieMapper = new MovieMapper();
            objectArray2Dem[0]= CSVMovieMapper.mapData(movie1);
            objectArray2Dem[1]= CSVMovieMapper.mapData(movie2);
            objectArray2Dem[2]= CSVMovieMapper.mapData(movie3);
            writer.saveData(filePath, Arrays.asList(objectArray2Dem), MetadataMapper.getHeader());

            filePath = "Metadata.csv"; // ohne "file:" am Anfang
            objectArray2Dem = new Object[3][];
            MetadataMapper MetadataMapper = new MetadataMapper();
            objectArray2Dem[0]= MetadataMapper.mapData(metadata1);
            objectArray2Dem[1]= MetadataMapper.mapData(metadata2);
            objectArray2Dem[2]= MetadataMapper.mapData(metadata3);
            writer.saveData(filePath, Arrays.asList(objectArray2Dem), MetadataMapper.getHeader());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
