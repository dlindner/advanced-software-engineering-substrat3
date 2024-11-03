package org.movie.manager.plugin.imbd;

import org.movie.manager.adapters.IMDbAPI;
import org.movie.manager.adapters.PropertyManager;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/*
    - The Open Movie Database API -
    The OMDb API is a RESTful web service to obtain movie information,
    all content and images on the site are contributed and maintained by our users.

    FREE: 1,000 daily limit
 */
public class OMDBapi implements IMDbAPI {

    private String apiKey;

    private PropertyManager propertyManager;

    private final String[] ATTRIBUTES = {
            "Title",
            "Genre",
            "Year",
            "Runtime",
            "Released",
            "Director",
            "Writer",
            "Actors",
            "Metascore",
            "imdbRating",
            "imdbID",
    };


    public OMDBapi(PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
        this.apiKey = propertyManager.getProperty("API_KEY");;
    }

    public void setApiKeyFromPropertyManager() {
        apiKey = propertyManager.getProperty("API_KEY");
    }

    public Map<String, String> requestWithTitle(String movieTitle){
        String nameMovie = "http://www.omdbapi.com/?apikey=" + apiKey + "&t=" + movieTitle;
        return scrapeInstructions(requestFromUrl(nameMovie));
    }

    public Map<String, String> requestWithIMDBID(String imdbID){
        String nameMovie = "http://www.omdbapi.com/?apikey=" + apiKey + "&i=" + imdbID;
        return scrapeInstructions(requestFromUrl(nameMovie));
    }

    public Map<String, String> scrapeInstructions(String resultAPIRow){

        JSONObject json = new JSONObject(resultAPIRow);

        Map<String, String> resultAPI = new HashMap<String, String>();

        for (String attribute : ATTRIBUTES) {
            resultAPI.put(attribute, (String) json.get(attribute));
        }

        return resultAPI;
    }
    public String requestFromUrl(String url) {
        InputStream is;
        try {
            is = new URL(url).openStream();
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                return readAll(rd);
            } finally {
                is.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
