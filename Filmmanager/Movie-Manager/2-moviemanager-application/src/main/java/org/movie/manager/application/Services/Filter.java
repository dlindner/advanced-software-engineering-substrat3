package org.movie.manager.application.Services;

public class Filter {
    private final String name;
    private final Object value;

    public Filter(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
