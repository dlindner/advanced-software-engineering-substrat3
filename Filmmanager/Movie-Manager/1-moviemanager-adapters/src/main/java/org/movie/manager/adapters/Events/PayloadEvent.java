package org.movie.manager.adapters.Events;

import java.util.EventObject;

public class PayloadEvent extends EventObject {
    private static final long serialVersionUID = 6468958319371635205L;
    private EventCommand cmd;
    private Object data;

    public PayloadEvent(Object source) {
        super(source);
    }

    public PayloadEvent(Object source, EventCommand cmd, Object data) {
        super(source);
        this.cmd = cmd;
        this.data = data;
    }

    public EventCommand getCmd() {
        return this.cmd;
    }

    public String getCmdText() {
        return this.cmd.getCmdText();
    }

    public void setCmd(EventCommand cmd) {
        this.cmd = cmd;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
