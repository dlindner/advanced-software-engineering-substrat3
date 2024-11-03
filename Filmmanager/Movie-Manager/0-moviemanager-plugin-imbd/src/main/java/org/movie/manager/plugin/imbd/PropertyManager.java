//
// Adopted/inspired by the lecture Software Engineering 4th semester DHBW 2022 by Mr. Lutz
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package org.movie.manager.plugin.imbd;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class PropertyManager implements org.movie.manager.adapters.PropertyManager {
    private static final String MSG_PROPERTY_NAME_MUST_NOT_BE_EMPTY_OR_NULL = "Error setting list property! The property name must not be empty or NULL";
    private static final String MSG_PROPERTY_NAME_MUST_BE_GIVEN = "a property name must be given!";
    public static final String DEFAULT_PROPERTIES_FILENAME = "application.properties";
    private String fsPropertiesFileName;
    protected String JarFileName;
    protected String Path;
    private SortedProperties properties;

    private PropertyManager() {
        this.fsPropertiesFileName = System.getProperty("user.home") + File.separator + "application.properties";
        this.JarFileName = "";
        this.Path = "";
        this.properties = new SortedProperties();
    }

    public PropertyManager(String fsPropertiesFileName, Class<?> resourceClass, String resourcePropertiesFileName) throws Exception {
        this.fsPropertiesFileName = System.getProperty("user.home") + File.separator + "application.properties";
        this.JarFileName = "";
        this.Path = "";
        this.properties = new SortedProperties();
        if (this.checkStringExistence(fsPropertiesFileName)) {
            this.fsPropertiesFileName = fsPropertiesFileName;
        }

        this.initialize(fsPropertiesFileName, resourceClass, resourcePropertiesFileName);
    }

    public PropertyManager(HashMap<String, String> properties) {
        this.fsPropertiesFileName = System.getProperty("user.home") + File.separator + "application.properties";
        this.JarFileName = "";
        this.Path = "";
        this.properties = new SortedProperties();
        this.properties.putAll(properties);
    }

    private void initialize(String fsPropertiesFileName, Class<?> applicationClass, String resourcePropertiesFileName) throws Exception {
        Properties props = this.loadPropertiesFromJarFile(applicationClass, resourcePropertiesFileName);
        if (props.size() > 0) {
            this.properties.putAll(props);
        }

        props.clear();
        if (this.checkStringExistence(fsPropertiesFileName)) {
            this.createFSFileIfNotExists(fsPropertiesFileName);
        }

        props = this.loadPropertiesFromOSFile(this.fsPropertiesFileName);
        int[] numOfPropsUpdated = new int[1];
        props.forEach((k, v) -> {
            if (this.properties.put(k, v) != null) {
                int var10002 = numOfPropsUpdated[0]++;
            }

        });
    }

    private void createFSFileIfNotExists(String fsPropertiesFileName) throws IOException {
        File f = new File(fsPropertiesFileName);
        if (!f.exists()) {
            f.createNewFile();
        }

    }

    private Properties loadPropertiesFromJarFile(Class<?> applicationClass, String resourcePropFileName) throws Exception {
        Properties jarFileProps = new Properties();
        if (applicationClass != null && resourcePropFileName != null && resourcePropFileName.length() > 0) {
            jarFileProps.load(applicationClass.getResourceAsStream(resourcePropFileName));
        }

        return jarFileProps;
    }

    private Properties loadPropertiesFromOSFile(String propFileName) throws Exception {
        Properties osFileProps = new Properties();
        if (this.checkStringExistence(propFileName)) {
            osFileProps.load(new FileInputStream(new File(propFileName)));
        }

        return osFileProps;
    }

    public void saveConfiguration() throws IOException {
        this.saveConfiguration(this.fsPropertiesFileName, Optional.empty());
    }

    public void saveConfiguration(String fileName, Optional<String> toolName) throws IOException {
        this.requireExistence(fileName, "a file name must be given!");
        File outFile;
        if (this.Path.length() > 0) {
            outFile = new File(this.Path);
            if (!outFile.exists()) {
                try {
                    outFile.mkdirs();
                } catch (Exception var5) {
                    throw var5;
                }
            }
        }

        try {
            outFile = new File(this.Path + fileName);
            if (!outFile.exists()) {
                outFile.createNewFile();
            }

            this.properties.store(new FileOutputStream(outFile), this.getHeaderLines(toolName));
        } catch (IOException var6) {

            throw var6;
        }

    }

    private String getHeaderLines(Optional<String> appName) {
        return "# ==========================================================================\n# Properties File:  " + this.fsPropertiesFileName + "\n# Application:      " + (String)appName.orElse("unknown") + "\n# ==========================================================================\n# This file contains important settings for the configuration of an application \n# - any property is represented by a leading property name followed by an\n#   equal sign (\"=\") and the values of the entries\n# - button text is represented by a string without embracing quote signs\n# - color is represented by RGB-values, separated by commas (no Blanks!!)\n# - text color is represented by RGB-values, separated by commas (no Blanks!!)\n# - comment lines start with '#'.\n# - blank lines are allowed\n# ==========================================================================\n";
    }

    public void setProperty(String propertyName, String value) {
        this.requireExistence(propertyName, "a property name must be given!");
        this.requireExistence(value, "a value name must be given!");
        this.properties.setProperty(propertyName, value);
    }

    public void printout(PrintStream printStream) {
        PrintStream ps = printStream == null ? System.out : printStream;
        StringBuilder line = new StringBuilder("+-------------------");

        for(int i = 0; i < this.fsPropertiesFileName.length(); ++i) {
            line.append('-');
        }

        line.append("--+");
        String numprops = "" + this.properties.size();
        char[] end = new char[line.length() - 20 - numprops.length()];
        Arrays.fill(end, ' ');
        end[end.length - 1] = ':';
        ps.println(line);
        ps.println(":  Properties File: " + this.fsPropertiesFileName + "  :");
        ps.println(":  #Properties:     " + numprops + new String(end));
        ps.println(line);
    }

    public void printout(PrintStream printStream, boolean allData) {
        this.printout(printStream);
        PrintStream ps = printStream == null ? System.out : printStream;
        if (allData) {
            int[] numOfElement = new int[]{1};
            Collections.list(this.properties.keys()).forEach((k) -> {
                StringBuilder var10001 = (new StringBuilder()).append("(");
                int var10005 = numOfElement[0];
                int var10002 = numOfElement[0];
                numOfElement[0] = var10005 + 1;
                ps.println(var10001.append(var10002).append("): ").append(k).append(" = ").append(this.properties.getProperty((String) k)).toString());
            });
            ps.println("+----------------------------------------------------------------------+");
        }

    }

    private void requireExistence(String propertyName, String message) {
        if (!this.checkStringExistence(propertyName)) {
            throw new IllegalArgumentException(message);
        }
    }

    private boolean checkStringExistence(String s) {
        return s != null && s.length() > 0;
    }

    public String getProperty(String propertyName) {
        return this.getProperty(propertyName, Optional.empty());
    }

    public String getProperty(String propertyName, Optional<String> defaultValue) {
        this.requireExistence(propertyName, "a property name must be given!");
        String value = this.properties.getProperty(propertyName);
        if (value == null) {
            if (!defaultValue.isPresent()) {
                return null;
            } else {
                return (String) defaultValue.get();
            }
        } else {
            return value;
        }
    }

    private class SortedProperties extends Properties {
        private static final long serialVersionUID = 1L;

        private SortedProperties() {
        }

        public Enumeration<Object> keys() {
            return Collections.enumeration((Collection)super.keySet().stream().filter(Objects::nonNull).map(Object::toString).sorted((a, b) -> {
                return a.compareToIgnoreCase(b);
            }).collect(Collectors.toList()));
        }

        public void store(OutputStream out, String comments) throws IOException {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out, "8859_1"));

            try {
                bw.write("#" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                bw.newLine();
                if (comments != null) {
                    bw.write(comments);
                }

                ((List)super.entrySet().stream().filter(Objects::nonNull).sorted((a, b) -> {
                    return a.getKey().toString().compareToIgnoreCase(b.getKey().toString());
                }).collect(Collectors.toList())).forEach((e) -> {
                    try {
                        bw.write(((java.util.Map.Entry<Object, Object>)e).getKey() + "=" + ((java.util.Map.Entry<Object, Object>)e).getValue() + "\n");
                    } catch (IOException var3) {
                        var3.printStackTrace();
                    }

                });
            } catch (IOException var8) {
                throw var8;
            } finally {
                bw.flush();
            }

        }
    }
}
