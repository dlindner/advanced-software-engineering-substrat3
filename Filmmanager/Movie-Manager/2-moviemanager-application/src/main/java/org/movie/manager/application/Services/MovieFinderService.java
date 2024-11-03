package org.movie.manager.application.Services;

import org.movie.manager.domain.FilmProfessional.FilmProfessionalRepository;
import org.movie.manager.domain.Metadata.Metadata;
import org.movie.manager.domain.Metadata.MetadataID;
import org.movie.manager.domain.Metadata.MetadataRepository;
import org.movie.manager.domain.Movie.Movie;
import org.movie.manager.domain.Movie.MovieID;
import org.movie.manager.domain.Movie.MovieRepository;
import org.movie.manager.domain.Persistable;

import java.util.*;

public class MovieFinderService {

    private final MovieRepository movieRepository;
    private final MetadataRepository metadataRepository;
    private final FilmProfessionalRepository filmProfessionalRepository;

    public MovieFinderService(MovieRepository movieRepository, MetadataRepository metadataRepository, FilmProfessionalRepository filmProfessionalRepository) {
        this.movieRepository = movieRepository;
        this.metadataRepository = metadataRepository;
        this.filmProfessionalRepository = filmProfessionalRepository;
    }

    public Collection<Movie> getAllMovies() {
        return this.movieRepository.getAllMovies();
    }

    public Vector<Vector<Attribute>> getAllMoviesInTableFormat() {
        Collection<Movie> movies = getAllMovies();
        return this.convertAndMapMovies(movies);
    }

    public Optional<Movie> getMovie(UUID movieID) {
        return this.movieRepository.getMovie(movieID);
    }

    public ArrayList<Persistable> getAllMovieData(UUID movieID, UUID metadataID) {
        ArrayList allMovieData = new ArrayList();
        allMovieData.add(this.movieRepository.getMovie(movieID).get());
        allMovieData.add(this.metadataRepository.getMetadata(metadataID).get());
        //TODO: add film professionals
        //allMovieData.add(filmProfessionalRepository.getFilmProfessionalsOfMovie(movieID));
        return allMovieData;
    }

    public Collection<Movie> getMoviesWithFilter(List<Filter> filters) {
        Collection<Movie> filteredMovies = new ArrayList<>();
        Collection<Movie> allMovies = this.getAllMovies();

        for (Movie movie : allMovies) {
            boolean passFilter = true;
            for (Filter filter : filters) {
                Optional<Metadata> metadataOptional = metadataRepository.getMetadata(movie.getMetadataID().getMetadataID());
                Metadata metadata;
                if (metadataOptional != null) {
                    metadata = metadataOptional.get();

                    switch (filter.getName()) {
                        case "ownratingBigger":
                            passFilter &= metadata.getOwnRating().getRating() > (int) filter.getValue();
                            break;
                        case "ownratingSmaller":
                            passFilter &= metadata.getOwnRating().getRating() < (int) filter.getValue();
                            break;
                        case "ownership":
                            passFilter &= metadata.getAvailability().getOwnership().toString() == filter.getValue();
                            break;
                        default:
                            break;
                    }
                    if (!passFilter) {
                        break;
                    }
                }
            }
            if (passFilter) {
                filteredMovies.add(movie);
            }
        }

        return filteredMovies;
    }

    public Vector<Vector<Attribute>> getMoviesWithFilterInTableFormat(List<Filter> filters) {
        Collection<Movie> movies = getMoviesWithFilter(filters);
        return this.convertAndMapMovies(movies);
    }

    private Vector<Vector<Attribute>> convertAndMapMovies(Collection<Movie> tableData) {
        HashMap<Integer, Persistable> persistableElements = new HashMap(tableData.size());
        Vector<Vector<Attribute>> dataVec = new Vector(tableData.size());
        int[] ii = new int[1];
        tableData.forEach((e) -> {
            int var10004 = ii[0];
            int var10001 = ii[0];
            ii[0] = var10004 + 1;
            persistableElements.put(var10001, e);
            Vector<Attribute> vecTmp = new Vector();
            Attribute.filterVisibleAttributes(movieToVector(e)).forEach((a) -> {
                vecTmp.add(a);
            });
            dataVec.add(vecTmp);
        });
        return dataVec;
    }

    private Vector<Attribute> movieToVector(Movie movie) {
        Vector<Attribute> vec = new Vector();
        vec.add(new Attribute("movieID", movie, MovieID.class, movie.getMovieID(), null, true));
        vec.add(new Attribute("title", movie, String.class, movie.getTitel(), null, true));
        vec.add(new Attribute("genre", movie, String.class, movie.getGenre(), null, true));
        vec.add(new Attribute("releaseYear", movie, Integer.class, movie.getReleaseYear(), null, true));
        vec.add(new Attribute("runningTimeInMin", movie, Integer.class, movie.getRunningTimeInMin(), null, true));
        vec.add(new Attribute("metadataID", movie, MetadataID.class, movie.getMetadataID(), null, false));
        vec.add(new Attribute("directorIDs", movie, Collection.class, movie.getDirectorIDs(), null, false));
        vec.add(new Attribute("actorIDs", movie, Collection.class, movie.getActorIDs(), null, false));
        vec.add(new Attribute("screenwriterIDs", movie, Collection.class, movie.getScreenwriter(), null, false));
        return vec;
    }
}
