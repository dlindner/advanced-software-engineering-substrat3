package org.movie.manager.plugin.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class IOUtilities {
    public static JFrame openInJFrame(Container content, int width, int height, int posx, int posy, String title, Color bgColor, boolean exitToOS) {
        if (content == null) {
            throw new IllegalArgumentException("container must be given!");
        } else {
            final JFrame frame = new JFrame(title == null ? content.getClass().getName() : title);
            frame.setBackground(bgColor == null ? Color.white : bgColor);
            content.setBackground(bgColor == null ? Color.white : bgColor);
            frame.setSize(width > 0 ? width : 100, height > 0 ? height : 100);
            frame.setLocation(posx > 0 ? posx : 0, posy > 0 ? posy : 0);
            frame.add(content, "Center");
            if (exitToOS) {
                frame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent event) {
                        System.exit(0);
                    }
                });
            } else {
                frame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent event) {
                        frame.setEnabled(false);
                        frame.setVisible(false);
                        frame.getContentPane().removeAll();
                    }
                });
            }
            frame.setResizable(false);
            frame.setVisible(true);
            return frame;
        }
    }

    public static JDialog openInJDialog(Container content, int width, int height, int posx, int posy, String title, Color bgColor, boolean exitToOS) {
        if (content == null) {
            throw new IllegalArgumentException("container must be given!");
        } else {
            final JDialog dialog = new JDialog();
            dialog.setModal(true);
            dialog.setBackground(bgColor == null ? Color.white : bgColor);
            content.setBackground(bgColor == null ? Color.white : bgColor);
            dialog.setSize(width > 0 ? width : 100, height > 0 ? height : 100);
            dialog.setLocation(posx > 0 ? posx : 0, posy > 0 ? posy : 0);
            dialog.add(content, "Center");
            if (exitToOS) {
                dialog.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent event) {
                        System.exit(0);
                    }
                });
            } else {
                dialog.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent event) {
                        dialog.setEnabled(false);
                        dialog.setVisible(false);
                        dialog.getContentPane().removeAll();
                    }
                });
            }
            dialog.setResizable(false);
            dialog.setVisible(true);
            return dialog;
        }
    }
}
