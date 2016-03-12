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
        return ServiceManager.getService(MessageProposerSettings.class).state;
    }

    @Nullable
    @Override
    public State getState() {
        return state;
    }

    @Override
    public void loadState(State state) {
        this.state = state;
    }

    public static class State {
        public static final int DEFAULT_MAX_TASKS = 20;
        public static final String DEFAULT_JQL = "assignee in (currentUser()) ORDER BY id ASC";

        public String jiraUrl;
        public String username;
        public String password;
        public int maxTasks = DEFAULT_MAX_TASKS;
        public String customJql = DEFAULT_JQL;

        @Override
        public String toString() {
            return "State{" +
                    "jiraUrl='" + jiraUrl + '\'' +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", maxTasks='" + maxTasks + '\'' +
                    ", customJql='" + customJql + '\'' +
                    '}';
        }
    }

}
