/*
Adopted/inspired by the lecture Software Engineering 4th semester DHBW 2022 by Mr. Lutz
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package org.movie.manager.adapters;

import java.io.IOException;
import java.io.PrintStream;

public interface PropertyManager {
    void setProperty(String var1, String var2);

    String getProperty(String var1);

     void printout(PrintStream var1, boolean var2);

    void saveConfiguration() throws IOException;
}
