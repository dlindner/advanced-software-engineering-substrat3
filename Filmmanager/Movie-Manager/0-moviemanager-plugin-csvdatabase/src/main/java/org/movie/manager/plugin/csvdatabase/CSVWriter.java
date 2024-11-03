//
// Adopted/inspired by the lecture Software Engineering 4th semester DHBW 2022 by Mr. Lutz
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.movie.manager.plugin.csvdatabase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CSVWriter {
    private String csvFileName = null;
    private File csvFile = null;

    public CSVWriter(String csvFileName, boolean createIfNotExists) throws IllegalArgumentException {
        this.csvFileName = csvFileName;
        this.csvFile = this.checkFile(this.csvFileName, createIfNotExists);
    }

    public static final File checkFile(String fileName, boolean create) throws IllegalArgumentException {
        if (fileName != null && fileName.length() != 0) {
            File csvFile = new File(fileName);
            if (!csvFile.exists()) {
                if (!create) {
                    throw new IllegalArgumentException("File does not exist! If it should be created automatically, use argument value create=true");
                }

                try {
                    csvFile.createNewFile();
                } catch (IOException var5) {
                    throw new IllegalArgumentException("File could not be created: " + var5.getMessage());
                }
            }

            return csvFile;
        } else {
            throw new IllegalArgumentException("File name must be given!");
        }
    }

    public final void writeDataToFile(Object[][] data, String[] header) throws IOException, IllegalArgumentException {
        Objects.requireNonNull(data, "Data must be given!");
        this.writeDataToFile(Arrays.asList(data), header);
    }

    public final void writeDataToFile(List<Object[]> data, String[] header) throws IOException, IllegalArgumentException {
        this.writeDataToFile(data, header, ';', '#', "UTF-8");
    }

    public final void writeDataToFile(List<Object[]> data, String[] header, char delimiter, char commentChar, String encodingName) throws IOException, IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException("Data must be given!");
        } else {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.csvFile), encodingName));
            if (data.get(0) == null) {
                writer.close();
                throw new IllegalArgumentException("first data line is not given!");
            } else {
                int arrLen;
                if (header != null && header.length > 0) {
                    try {
                        writer.write(commentChar);
                        arrLen = header.length;

                        for(int i = 0; i < arrLen; ++i) {
                            if (header[i] != null) {
                                writer.write(header[i]);
                            }

                            if (arrLen - i > 1) {
                                writer.write(delimiter);
                            }
                        }

                        writer.newLine();
                    } catch (IOException var15) {
                        writer.flush();
                        writer.close();
                        throw var15;
                    }
                }

                try {
                    arrLen = ((Object[])data.get(0)).length;
                    int finalArrLen = arrLen;
                    data.forEach((e) -> {
                        try {
                            for(int i = 0; i < finalArrLen; ++i) {
                                if (e[i] != null) {
                                    writer.write(e[i].toString());
                                }

                                if (finalArrLen - i > 1) {
                                    writer.write(delimiter);
                                }
                            }

                            writer.newLine();
                        } catch (IOException var5) {
                            var5.printStackTrace();
                        }

                    });
                } catch (Exception var13) {
                    throw var13;
                } finally {
                    writer.flush();
                    writer.close();
                }

            }
        }
    }
}

