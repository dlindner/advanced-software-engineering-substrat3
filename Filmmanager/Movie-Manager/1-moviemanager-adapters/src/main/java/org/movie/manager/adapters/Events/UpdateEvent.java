package org.movie.manager.adapters.Events;

public class UpdateEvent extends PayloadEvent {
    public UpdateEvent(Object source, EventCommand cmd) {
        super(source, cmd, (Object)null);
    }

    public UpdateEvent(Object source, EventCommand cmd, Object data) {
        super(source, cmd, data);
    }
}
