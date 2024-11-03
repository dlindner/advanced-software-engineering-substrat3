package org.movie.manager.adapters.Events;

import java.util.EventListener;

public interface GUIEventListener extends EventListener {
    void processGUIEvent(GUIEvent event);
}
