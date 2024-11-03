//
// Adopted/inspired by the lecture Software Engineering 4th semester DHBW 2022 by Mr. Lutz
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package org.movie.manager.plugin.csvdatabase;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private String csvFileName = null;
    private File csvFile = null;
    private InputStream ioStream = null;


    public CSVReader(String csvFileName) throws IllegalArgumentException {
        this.csvFileName = csvFileName;
        this.csvFile = this.checkFile(this.csvFileName);
    }

    private final File checkFile(String fileName) throws IllegalArgumentException {
        if (fileName != null && fileName.length() != 0) {
            File csvFile = new File(fileName);
            if (!csvFile.exists()) {
                throw new IllegalArgumentException("File '" + fileName + "' does not exist");
            } else {
                return csvFile;
            }
        } else {
            throw new IllegalArgumentException("File name must be given!");
        }
    }

    public final List<String[]> readData() throws IOException {
        return this.readData(0, ';', '#', "UTF-8");
    }

    public final List<String[]> readData(int expectedColumns, char delimiter, char comment, String encodingName) throws IOException {
        String encdngNm = encodingName != null && !encodingName.isEmpty() ? encodingName : "UTF-8";
        List<String[]> allLines = new ArrayList();
        BufferedReader reader = null;
        if (this.csvFile != null) {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.csvFile), encdngNm));
        } else {
            if (this.ioStream == null) {
                throw new IllegalArgumentException("either a file or an IOStream must be given! ");
            }

            reader = new BufferedReader(new InputStreamReader(this.ioStream, encdngNm));
        }

        try {
            String line = "";
            String[] lineElements = null;
            int numLinesRead = 0;

            while(line != null) {
                line = reader.readLine();
                ++numLinesRead;
                if (line == null) {
                    break;
                }

                if (!line.startsWith("" + comment) && line.length() != 0) {
                    lineElements = line.split("" + delimiter);
                    if (expectedColumns > 0 && expectedColumns != lineElements.length) {
                        String[] sArr = new String[expectedColumns];
                        int i;
                        if (expectedColumns <= lineElements.length) {
                            for(i = 0; i < expectedColumns; ++i) {
                                sArr[i] = lineElements[i] == null ? "" : lineElements[i];
                            }
                        } else if (expectedColumns > lineElements.length) {
                            System.arraycopy(lineElements, 0, sArr, 0, lineElements.length);

                            for(i = lineElements.length; i < expectedColumns; ++i) {
                                sArr[i] = "";
                            }
                        }

                        lineElements = sArr;
                    }

                    allLines.add(lineElements);
                }
            }
        } catch (IOException var16) {
            throw var16;
        } finally {
            reader.close();
            if (this.ioStream != null) {
                this.ioStream.close();
            }

        }

        return allLines;
    }

    public final String readFirstCommentLineFromFile(String comment, String encodingName) throws IOException {
        String cmnt = comment != null && !comment.isEmpty() ? comment : "#";
        String encdngNm = encodingName != null && !encodingName.isEmpty() ? encodingName : "UTF-8";

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.csvFile), encdngNm));

            String var7;
            label53: {
                try {
                    String line = "";

                    while(line != null) {
                        line = reader.readLine();
                        if (line == null) {
                            break;
                        }

                        if (!line.isEmpty() && line.startsWith(cmnt)) {
                            var7 = line;
                            break label53;
                        }
                    }
                } catch (Throwable var9) {
                    try {
                        reader.close();
                    } catch (Throwable var8) {
                        var9.addSuppressed(var8);
                    }

                    throw var9;
                }

                reader.close();
                return null;
            }

            reader.close();
            return var7;
        } catch (IOException var10) {
            throw var10;
        }
    }
}

