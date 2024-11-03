package org.movie.manager.domain.Metadata;

import java.util.ArrayList;
import java.util.List;

public enum Ownership {
    ONLINE("online"), //bought online
    PHYSICALLY("physically"), //bought physically
    LOANED("loaned"), //loaned to a friend
    NOTOWNED("notowned"); //not owned

    String key;

    Ownership(String key) {
        this.key = key;
    }

    public static Ownership fromString(String key) {
        for (Ownership ow : Ownership.values()) {
            if (ow.key.equalsIgnoreCase(key)) {
                return ow;
            }
        }
        return null;
    }

    public static String[] getArray() {
        List<String> list = new ArrayList<>();
        for (Ownership ow : Ownership.values()) {
            list.add(ow.key);
        }
        return list.toArray(new String[list.size()]);
    }
}
