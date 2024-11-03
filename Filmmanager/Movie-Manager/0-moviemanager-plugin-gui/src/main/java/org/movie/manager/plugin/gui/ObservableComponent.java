package org.movie.manager.plugin.gui;

import org.movie.manager.adapters.Events.GUIEvent;
import org.movie.manager.adapters.Events.GUIEventListener;
import org.movie.manager.adapters.Events.UpdateEventListener;
import org.movie.manager.adapters.Events.UpdateEvent;

import javax.swing.*;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Iterator;
import java.util.List;

public abstract class ObservableComponent extends JComponent {

    private List<EventListener> listOfAllObservers;

    protected ObservableComponent() {
        this.listOfAllObservers = new ArrayList();
    }

    public synchronized boolean addObserver(EventListener el) {
        if (el == null) {
            throw new IllegalArgumentException("EventListener is null");
        } else {
            return this.listOfAllObservers.add(el);
        }
    }

    public synchronized boolean removeObserver(EventListener el) {
        return this.listOfAllObservers.remove(el);
    }

    public void fireGUIEvent(GUIEvent ge) {
        Iterator var2 = this.listOfAllObservers.iterator();

        while(var2.hasNext()) {
            EventListener el = (EventListener)var2.next();
            if (el instanceof GUIEventListener) {
                ((GUIEventListener)el).processGUIEvent(ge);
            }
        }

    }

    public void fireUpdateEvent(UpdateEvent ue) {
        Iterator var2 = this.listOfAllObservers.iterator();

        while(var2.hasNext()) {
            EventListener el = (EventListener)var2.next();
            if (el instanceof UpdateEventListener) {
                ((UpdateEventListener)el).processUpdateEvent(ue);
            }
        }

    }
}
