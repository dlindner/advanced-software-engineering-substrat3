package org.movie.manager.adapters;

import java.util.Map;

public interface IMDbAPI {

    void setApiKeyFromPropertyManager();
    Map<String, String> requestWithTitle(String movieTitle);
    Map<String, String> requestWithIMDBID(String movieTitle);
}
