package org.movie.manager.application.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Attribute {
    private Object dedicatedInstance;
    private String name;
    private Class<?> clazz;
    private Object value;
    private Object defaultValue;
    private boolean visible;

    private Attribute() {
    }

    public Attribute(String name, Object dedicatedInstance, Class<?> clazz, Object value, Object defaultValue, boolean visible) {
        Objects.requireNonNull(dedicatedInstance, "a dedicated object must be given for an attribute");
        Objects.requireNonNull(name, "Name must be given for an attribute");
        Objects.requireNonNull(clazz, "Class must be given for an attribute");
        this.name = name;
        this.clazz = clazz;
        this.value = value;
        this.defaultValue = defaultValue;
        this.visible = visible;
        this.dedicatedInstance = dedicatedInstance;
    }

    public String getName() {
        return this.name;
    }

    public Class<?> getClazz() {
        return this.clazz;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) throws Exception {
            this.value = value;
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    public void setVisible(boolean visible) throws Exception {
        this.visible = visible;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public Object getDedicatedInstance() {
        return this.dedicatedInstance;
    }

    public String toString() {
        return this.getClass().getSimpleName() + ": name=" + this.name + ", dedicated object: [" + this.dedicatedInstance + "], value: " + (this.value == null ? "--" : this.value.toString());
    }

    public static List<Attribute> filterVisibleAttributes(List<Attribute> attributes) {
        return (List)(attributes != null ? (List)attributes.stream().filter((e) -> {
            return e.isVisible();
        }).collect(Collectors.toList()) : new ArrayList());
    }

    public static List<String> extractVisibleAttributeNames(List<Attribute> attributes) {
        List<String> names = new ArrayList();
        if (attributes != null) {
            attributes.forEach((e) -> {
                if (e.isVisible()) {
                    names.add(e.getName());
                }

            });
        }

        return names;
    }

    public static List<String> extractAttributeNames(List<Attribute> attributes) {
        List<String> names = new ArrayList();
        if (attributes != null) {
            attributes.forEach((e) -> {
                names.add(e.getName());
            });
        }

        return names;
    }

}