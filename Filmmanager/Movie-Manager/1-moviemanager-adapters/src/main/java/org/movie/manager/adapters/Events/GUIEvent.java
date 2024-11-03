package org.movie.manager.adapters.Events;

public class GUIEvent extends PayloadEvent {

    public GUIEvent(Object source) {
        super(source);
    }

    public GUIEvent(Object source, EventCommand cmd, Object data) {
        super(source, cmd, data);
    }
}
