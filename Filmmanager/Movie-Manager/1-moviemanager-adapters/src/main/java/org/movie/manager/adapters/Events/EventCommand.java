package org.movie.manager.adapters.Events;

public interface EventCommand {
    String getCmdText();

    Class<?> getPayloadType();
}
