package com.gmail.pickl.christoph.messageproposer.settings;

import com.intellij.openapi.components.*;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

// see official howto: http://www.jetbrains.org/intellij/sdk/docs/basics/persisting_state_of_components.html
// see HttpConfigurable, ExternalDiffSettings, XsltConfigImpl
//@State(
//        name = "MessageProposerSettings", // name of the root tag in XML
//        storages = @Storage(file = StoragePathMacros.WORKSPACE_FILE))

@State(name = "MessageProposerSettings", storages = {@Storage(file = StoragePathMacros.APP_CONFIG + "/messageproperties.xml")})
public class MessageProposerSettings implements PersistentStateComponent<MessageProposerSettings.State> {

    private static final Logger LOG = Logger.getInstance("#com.gmail.pickl.christoph.messageproposer.settings.MessageProposerSettings");

    private State state = new State();

    public static State getInstance() {
        MessageProposerSettings instance = ServiceManager.getService(MessageProposerSettings.class);
        if (instance == null) throw new IllegalStateException("Could not look up service: " + MessageProposerSettings.class.getSimpleName());
        return instance.state;
    }

    @Nullable
    @Override
    public State getState() {
        return state;
    }

    @Override
    public void loadState(State state) {
        LOG.debug("loadState: ", state);
        this.state = state;
    }

    public static class State {
        public String jiraUrl;
    }

}
