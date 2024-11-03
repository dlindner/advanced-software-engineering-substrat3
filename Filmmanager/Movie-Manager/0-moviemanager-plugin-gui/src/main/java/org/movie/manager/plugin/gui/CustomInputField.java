package org.movie.manager.plugin.gui;

import org.movie.manager.adapters.Events.EventCommand;

public abstract class CustomInputField extends ObservableComponent {

    public abstract void setValue(String value);
    public abstract void setEnabledState(Boolean enabled);

    public enum Commands implements EventCommand {

        ADD_METADATA("GUIEditMovie.addMetadata", String[].class);
        public final Class<?> payloadType;
        public final String cmdText;

        Commands(String cmdText, Class<?> payloadType) {
            this.cmdText = cmdText;
            this.payloadType = payloadType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getCmdText() {
            return this.cmdText;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Class<?> getPayloadType() {
            return this.payloadType;
        }
    }

    public String title, placeholder, value;

    public String getTitle() {
        return title;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public String getValue() { return value; };
}
