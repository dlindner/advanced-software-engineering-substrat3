package org.movie.manager.adapters;

import java.util.List;

public interface Database {
    List<String[]> readData(String filePath);

    void saveData(String fileName, List<Object[]> data, String[] header);

}
