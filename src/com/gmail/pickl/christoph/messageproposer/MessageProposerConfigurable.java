package com.gmail.pickl.christoph.messageproposer;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.PersistentStateComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// see HttpConfigurable
public class MessageProposerConfigurable implements PersistentStateComponent<MessageProposerConfigurable>, ApplicationComponent {
    @Override
    public void initComponent() {

    }

    @Override
    public void disposeComponent() {

    }

    @NotNull
    @Override
    public String getComponentName() {
        return null;
    }

    @Nullable
    @Override
    public MessageProposerConfigurable getState() {
        return null;
    }

    @Override
    public void loadState(MessageProposerConfigurable state) {

    }
}
